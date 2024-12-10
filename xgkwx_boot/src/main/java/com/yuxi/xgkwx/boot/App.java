package com.yuxi.xgkwx.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication()
@ComponentScan("com.yuxi")
@MapperScan("com.yuxi.xgkwx.*.*.mapper")
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        Environment env = app.run(args).getEnvironment();
        log.info("启动成功！！");
//        log.info("测试地址: \thttp://127.0.0.1:{}{}", env.getProperty("server.port"), env.getProperty("server.servlet.context-path"));
    }
}
