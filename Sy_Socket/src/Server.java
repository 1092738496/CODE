import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @time: 2023/12/15 20:37
 * @description:
 */

public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8080)) {
            System.out.println("等待客户端连接");
            Socket socket = server.accept();
            System.out.println("客户端已连接,IP地址为:" + socket.getInetAddress().getHostAddress());
            InputStream istm = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = istm.read(bytes)) != -1) {
                String data = new String(bytes,0,len);
                System.out.println(data);
            }

            OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            String response = "收到请求!!!!";
            String httpResponse = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/plain; charset=UTF-8\r\n" +
                    "Content-Length: " + response.length() + "\r\n" +
                    "Referrer-Policy: strict-origin-when-cross-origin\r\n" + // 设置 Referrer-Policy
                    "Access-Control-Allow-Origin: *\r\n"+
                    "\r\n" +
                    response;
            writer.write( httpResponse);
            writer.flush();

            writer.close();
            istm.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
