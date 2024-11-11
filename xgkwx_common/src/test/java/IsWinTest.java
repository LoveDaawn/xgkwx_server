import com.yuxi.xgkwx.common.utils.RuleUtil;
import org.junit.jupiter.api.Test;

public class IsWinTest {

    @Test
    public void test() {
        int[] cards = {0,0,0,0,0,0,0,0,0,0,0,
                        -1,1,1,1,1,1,1,1,1,0,
                        1,1,1,2,2,2,1,1,1,0,
                        3,0,2,0,0,0,0,0,0,0};
                       /*{0,0,0,0,0,0,0,0,0,0,0,
                        -1,0,-1,0,-1,0,0,0,0,0,
                        0,0,0,0,0,0,0,0,0,0,
                        2,0,0,0,0,0,0,0,0,0};*/

        long l = System.currentTimeMillis();
        boolean win = RuleUtil.isWin(cards);
        long l1 = System.currentTimeMillis();
        System.out.println("游戏结果：" + win + "，所需时间" + (l1 - l) + "ms");
    }
}
