import org.junit.Test;
import util.Props;

/**
 * @time: 2024/10/14 21:28
 * @description:
 */

public class test {
    @Test
    public void test(){
        System.out.println(Props.getStr("ChromePath"));

    }

    public static void main(String[] args) {
        System.out.println(Props.getStr("ChromePath"));
    }
}
