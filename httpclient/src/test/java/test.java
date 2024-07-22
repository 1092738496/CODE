import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.impl.routing.DefaultProxyRoutePlanner;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.nio.ssl.TlsStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2024/7/19 16:00
 * @description:
 */

public class test {
    @Test
    public void test1() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet("https://www.ximalaya.com/");
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                " Chrome/123.0.0.0 Safari/537.36");

        try {
            CloseableHttpResponse execute = httpClient.execute(get);
            String s = EntityUtils.toString(execute.getEntity());
            System.out.println("结果:" + s);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        // 创建异步HTTP客户端实例
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();

        try {
            // 初始化客户端
            client.start();

            // 构建GET请求
            SimpleHttpRequest request = SimpleRequestBuilder.get("https://www.ximalaya.com/")
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, " +
                            "like " +
                            "Gecko) Chrome/123.0.0.0 Safari/537.36")
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

    @Test
    public void test3() {
        CloseableHttpAsyncClient client = null;
        try {
            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            SSLContext sslContext = sslContextBuilder.loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            //使用谷歌浏览器查看网页使用的是哪一个SSL协议，SSLv2Hello需要删掉，不然会报握手失败，具体原因还不知道
            SSLConnectionSocketFactory sslConnectionSocketFactory =
                    new SSLConnectionSocketFactory(sslContext,
                            new String[]{"SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"}, null,
                            NoopHostnameVerifier.INSTANCE);
            // 创建连接池管理器
            PoolingAsyncClientConnectionManager connectionManager = new PoolingAsyncClientConnectionManager();

            // 设置连接池参数
            connectionManager.setMaxTotal(100);
            connectionManager.setDefaultMaxPerRoute(20);

            // 构建异步HTTP客户端
            client = HttpAsyncClients.custom()
                    .setConnectionManager(connectionManager)
                    // 可以根据需要设置请求配置，如超时等
                    .setDefaultRequestConfig(RequestConfig.custom().build())
                    .build();

            // 初始化客户端
            client.start();

            // 发送异步请求示例（与之前的示例相同）
            SimpleHttpRequest request = SimpleRequestBuilder.get("https://www.bilibili.com/")
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, " +
                            "like " +
                            "Gecko) Chrome/123.0.0.0 Safari/537.36")
                    .build();

            Future<SimpleHttpResponse> execute = client.execute(request, new FutureCallback<SimpleHttpResponse>() {
                @Override
                public void completed(SimpleHttpResponse result) {
                    System.out.println(result.getBodyText());
                }

                @Override
                public void failed(Exception ex) {

                }

                @Override
                public void cancelled() {

                }
                // 回调处理逻辑同上
            });

            // 等待一段时间，实际应用中可能需要更复杂的同步机制

            execute.get();
        } catch (InterruptedException | ExecutionException | KeyStoreException | KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            // 关闭客户端
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void test4() {
        try {
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(new TrustStrategy() {
                        @Override
                        public boolean isTrusted(
                                final X509Certificate[] chain,
                                final String authType) throws CertificateException {
                            // Trust all certs with CN equal `httpbin.org`

                            return true;
                        }
                    })
                    .build();
            TlsStrategy tlsStrategy = ClientTlsStrategyBuilder.create()
                    .setSslContext(sslcontext)
                    .build();


            PoolingAsyncClientConnectionManager pool = PoolingAsyncClientConnectionManagerBuilder.create()
                    .setTlsStrategy(tlsStrategy)
                    .build();
            pool.setMaxTotal(100);
            pool.setDefaultMaxPerRoute(20);


            CloseableHttpAsyncClient client = HttpAsyncClients.custom()
                    .setConnectionManager(pool)
                    // 可以根据需要设置请求配置，如超时等
                    .build();

            // 初始化客户端
            client.start();

            // 发送异步请求示例（与之前的示例相同）
            SimpleHttpRequest request = SimpleRequestBuilder.get("https://www.bilibili.com/")
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, " +
                            "like " +
                            "Gecko) Chrome/123.0.0.0 Safari/537.36")
                    .build();


            Future<SimpleHttpResponse> execute = client.execute(request, new FutureCallback<SimpleHttpResponse>() {
                @Override
                public void completed(SimpleHttpResponse result) {
                    //System.out.println(result.getBodyText());
                }

                @Override
                public void failed(Exception ex) {

                }

                @Override
                public void cancelled() {

                }
                // 回调处理逻辑同上
            });

            // 等待一段时间，实际应用中可能需要更复杂的同步机制

            TimeUnit.SECONDS.sleep(10);
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test5() {
        String url = "https://api.openai.com/v1/chat/completions";
        HttpHost proxy = new HttpHost("127.0.0.1", 7890);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

        HttpPost httpPost = new HttpPost(url);
        try (CloseableHttpClient client = HttpClients.custom().setRoutePlanner(routePlanner).build()) {

            client.execute(new HttpHost("api.openai.com"), httpPost, response -> {
                System.out.println(response);
                return null;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test6() {

        try {
            // 加载您的信任存储（Truststore），这里假设是一个JKS文件
            char[] password = "yourKeystorePassword".toCharArray(); // 你的密钥库密码
            KeyStore trustStore = KeyStore.getInstance("JKS");
            trustStore.load(new FileInputStream("path/to/your/truststore.jks"), password);

            // 定义信任策略，这里采用信任所有证书仅作示例，实际应用中应根据安全需求调整
            TrustStrategy trustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true; // 或实现适当的证书验证逻辑
                }
            };

            // 使用custom方法配置SSLContext
            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial(trustStore, trustStrategy)
                    // 如果您还有自己的密钥材料要加载，可以使用loadKeyMaterial方法
                    // .loadKeyMaterial(keyStore, keyPassword)
                    .build();

            // 接下来，您可以将此sslContext应用于您的HttpClient或其它需要SSL上下文的地方

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Test
    public void test7() {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        int i = this.binarySearch2(a, 11);
        System.out.println(i);
    }



    public int binarySearch2(int[] array, int target) {
        int low = 0;
        int max = array.length - 1;

        for (; low <= max; ) {
            int mid = low + (max - low) / 2;
            if (array[mid] == target) {
                return mid;
            }else if(array[mid] < target){
                low = mid;
            }else{
                max = mid;
            }

        }
        return -1; // 目标值不在数组中
    }

}
