import java.util.Scanner;

/**
 * @time: 2024/3/6 19:05
 * @description:
 */

public class main {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        System.out.println("1,数据获取请输入 2,统计请输入 3,亚盘时段数据");
        String ru = scanner.nextLine();
        if (ru.equals("1")) {
            new Getdata().tabular_data();

        } else if (ru.equals("2")) {
            new timedata().export();

        }else if (ru.equals("3")) {
            new yaData().export();

        }
    }
    //2551368
    //2519336


}
