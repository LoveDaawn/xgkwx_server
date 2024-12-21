import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

    static final String URL
            = System.getProperty("url",
            "ws://127.0.0.1:10001/websocket");

    public static void main(String[] args) throws Exception{
        Logger logger = LoggerFactory.getLogger(Client.class);
        // 创建 EventLoopGroup，处理连接和事件
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建客户端启动器
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)  // 设置线程组
                    .channel(NioSocketChannel.class)  // 使用 NIO 传输
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 客户端处理器
                            ch.pipeline()
                                    .addLast(new StringEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast(new JsonObjectDecoder())
                                    .addLast(new HttpClientCodec())
//                                    .addLast(new HttpServerExpectContinueHandler())
//                                    .addLast(new ChunkedWriteHandler())  // 解码器
                                    .addLast(new ClientHandler());   // 客户端自定义逻辑
                        }
                    });

            // 连接到服务器
            ChannelFuture future = bootstrap.connect("localhost", 10001).syncUninterruptibly();
            future.channel().writeAndFlush("{    \n" +
                    "    \"unifyId\": \"1321331531123531223\",\n" +
                    "    \"messageType\": \"CREATE_ROOM\",\n" +
                    "    \"content\": {\n" +
                    "        \"playsNum\": \"3\",\n" +
                    "        \"nickname\": \"player1\"\n" +
                    "        \"rule\": {\n" +
                    "            \"SK\": \"y\",\n" +
                    "            \"PPH\": \"4\" //碰碰胡 4倍\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"sendTime\": \"2024/11/18 23:22:16.796\"\n" +
                    "}");
//            future.channel().writeAndFlush(Unpooled.copiedBuffer("hello netty again!".getBytes()));
            // 等待直到连接关闭
            future.channel().closeFuture().sync();
        } finally {
            // 关闭线程组
            group.shutdownGracefully();
        }
    }

    public static class ClientHandler extends SimpleChannelInboundHandler {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // 当连接建立时可以发送初始消息，如果需要的话
            System.err.println("client channel active..");
        }

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
            try {
                System.out.println("Client :" + o );
            } finally {
                ReferenceCountUtil.release(o);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            // 异常处理
            cause.printStackTrace();
            ctx.close();
        }
    }
}
