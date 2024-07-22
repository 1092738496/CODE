import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @time: 2024/7/7 10:29
 * @description:
 */

public class test2 {
    @Test
    public void test() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m");

        String timeStr = "2024-8-10 10:05";
// 定义格式器，假设月份和日期在此上下文中无关紧要，仅处理时间
        LocalDateTime convertedTime = LocalDateTime.parse(timeStr, formatter);
        String timeStr2 = "2024-8-10 10:06";
        LocalDateTime Time2 = LocalDateTime.parse(timeStr2, formatter);
        System.out.println(convertedTime);
        try {
            // 解析字符串为LocalTime，这里假设月份和日期是当前的，因此不直接体现在转换结果中

            // 进行比较，例如与当前时间比较
            System.out.println(Time2);
            if (convertedTime.isBefore(Time2)) {
                System.out.println("指定时间在当前时间之前");
            } else if (convertedTime.isAfter(Time2)) {
                System.out.println("指定时间在当前时间之后");
            } else {
                System.out.println("指定时间与当前时间相同");
            }
        } catch (Exception e) {
            System.err.println("解析时间字符串失败: " + e.getMessage());
        }
    }

    @Test
    public void test1() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m");

        String timeStr = "2024-07-10 03:00";
        LocalDateTime thisTime = LocalDateTime.parse(timeStr, formatter);
        LocalDateTime minusTime = thisTime.minusHours(2);
        System.out.println("当前时间:" + timeStr);
        System.out.println("减少后的时间:" + minusTime);


        String time = "2024-7-9 23:34";
        LocalDateTime parse = LocalDateTime.parse(time, formatter);

        System.out.println((thisTime.isEqual(parse) || thisTime.isAfter(parse)) & (minusTime.isEqual(parse) || minusTime.isBefore(parse)));
    }

    @Test
    public void test2() {
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(50, 100, 60, TimeUnit.SECONDS, taskQueue);

        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 100000; i++) {
            final int a = i;
            threadPool.submit(() -> {
                list.add(a);
            });
        }
        System.out.println(list);

    }


    @Test
    void test4() {
        System.out.println("小白进入餐厅");
        System.out.println("小白点餐 番茄炒蛋 + 一碗米饭");
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println("厨师炒菜");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("厨师炒菜");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("厨师打饭");
                return "番茄炒饭 + 米饭";
            }
        });

        System.out.println("小白在打王者");
        System.out.println("小白开吃" + cf1.join());
    }

    @Test
    public void test3() {

        System.out.println("小白进入餐厅");
        System.out.println("小白点餐 番茄炒蛋 + 一碗米饭");
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println(Thread.currentThread().getName() + "厨师炒菜");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "番茄炒饭";
            }
        }).thenCompose(dis -> CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "服务员打饭");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return dis + "米饭";
        }));

        System.out.println("小白在打王者");
        System.out.println("小白开吃" + cf1.join());
    }

    @Test
    void test5() {
        try {
            //System.setProperty("javax.net.debug", "all");
            //证书全部信任 不做身份鉴定
            StringBuilder stringBuffer = new StringBuilder();

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
            // SSLConnectionSocketFactory  sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslConnectionSocketFactory)
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .build();
            //连接池

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test6(){
        String aa = "aaa%b";

    }


}


