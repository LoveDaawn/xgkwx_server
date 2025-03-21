import cn.hutool.core.date.DateTime;
import com.yuxi.xgkwx.common.utils.RuleUtil;
import org.junit.Test;

import java.text.DateFormat;

public class IsWinTest {

    @Test
    public void test() {
        short[] cards = {0,0,0,0,0,0,0,0,0,0,0,
                        1,1,1,1,1,1,1,1,1,0,
                        0,0,3,2,0,0,0,0,0,0,
                        0,0,0,0,0,0,0,0,0,0};
                       /*{0,0,0,0,0,0,0,0,0,0,0,
                        -1,0,-1,0,-1,0,0,0,0,0,
                        0,0,0,0,0,0,0,0,0,0,
                        2,0,0,0,0,0,0,0,0,0};*/

        long l = System.currentTimeMillis();
        boolean win = RuleUtil.winCheck(cards, true);
        long l1 = System.currentTimeMillis();
        System.out.println("游戏结果：" + win + "，所需时间" + (l1 - l) + "ms");
    }

    @Test
    public void testDate() {
        DateTime now = DateTime.now();
        System.out.println(now.toString(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)));
    }
}
