/**
 * @time: 2024/1/11 11:14
 * @description:
 */

public class test {

    public static void main(String[] args) {
        String aa = "收：刘** 137****2738 江苏省淮安市清江浦区水渡口街道宏辉首馥5号楼603室";
        String bb = "收：刘** 137****2738";

        String s1 = bb.replaceAll("\\*", ".");
        System.out.println(s1);

        String s = aa.replaceAll(s1, "");
        System.out.println(s);
    }

}
