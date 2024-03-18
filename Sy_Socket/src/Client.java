import java.io.IOException;
import java.net.Socket;

/**
 * @time: 2023/12/15 20:36
 * @description:
 */

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost",8080);
            System.out.println("连接成功");
        } catch (IOException e) {
            System.out.println("连接失败");
            e.printStackTrace();
        }

    }
}
