import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @time: 2024/7/17 22:00
 * @description:
 */

public class main {
    public static void main(String[] args) {
        // 创建异步HTTP客户端实例
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();

        try {
            // 初始化客户端
            client.start();

            // 构建GET请求
            SimpleHttpRequest request = SimpleRequestBuilder.get("https://www.ximalaya.com/")
                    .build();

            // 发送异步请求并设置回调处理响应
            Future<SimpleHttpResponse> Future = client.execute(request,
                    new FutureCallback<SimpleHttpResponse>() {

                @Override
                public void completed(SimpleHttpResponse response) {
                    System.out.println("Response received: " + response.getCode() + " " + response.getVersion());
                    System.out.println("Response Body: " + response.getBodyText());
                }

                @Override
                public void failed(Exception ex) {
                    System.err.println("Request failed: " + ex.getMessage());
                }

                @Override
                public void cancelled() {
                    System.out.println("Request cancelled");
                }
            });

            // 为了演示，这里简单等待一段时间让请求有机会完成
            // 实际应用中可能需要更复杂的同步机制或使用CountDownLatch等工具
            SimpleHttpResponse simpleHttpResponse = Future.get();

            System.out.println(simpleHttpResponse.getBody());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // 关闭客户端释放资源
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
