import com.meditation.utils.httpUtils2;
import com.meditation.utils.tools;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    void test6() {


        tools tools = new tools();
        httpUtils2 httpUtils = new httpUtils2();
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"));
        httpUtils.setHeaders(headers);
        try {
            String s = httpUtils.get("https://1x2d.titan007.com/2512338.js", "utf-8");
            String s1 =
                    tools.regexStr1(s, "game=Array\\(.*\\);").replaceAll("game=Array\\" +
                            "(", "").replaceAll("\\)", "").replaceAll(";var gameDetail", "");

            String[] split = s1.split("\",\"");
            Double a = 0.0;
            Double b = 0.0;
            Double c = 0.0;
            for (String s2 : split) {
                a += Double.parseDouble(s2.split("\\|")[10]);
                b += Double.parseDouble(s2.split("\\|")[11]);
                c += Double.parseDouble(s2.split("\\|")[12]);
            }


            System.out.println((Math.round(a / split.length * 100.0) / 100.0) + "-" + (Math.round(b / split.length
                    * 100.0) / 100.0) + "-" + (Math.round(c / split.length * 100.0) / 100.0));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test6a() throws IOException, ParseException {
        httpUtils2 httpUtils = new httpUtils2();
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"));
        httpUtils.setHeaders(headers);
        String s = httpUtils.get("https://1x2d.titan007.com/2610600.js", "utf-8");

        // 创建一个 ScriptEngineManager 实例
        ScriptEngineManager manager = new ScriptEngineManager();

        // 获取 JavaScript 引擎
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        // 定义要执行的 JavaScript 代码
        String script = s;

        try {
            // 执行 JavaScript 代码
            Object result = engine.eval(script);

            // 输出结果
            System.out.println("JavaScript execution result: " + result);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test7() throws IOException, ParseException {
        tools tools = new tools();
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36"));
        headers.add(new BasicHeader("Referer", "https://live.titan007.com/oldIndexall.aspx"));
        long timestampInSeconds = Instant.now().getEpochSecond();
        String sj = "007" + timestampInSeconds + "000";
        String url1 = "https://livestatic.titan007.com/vbsxml/bfdata_ut.js?r=" + sj;
        httpUtils2 httpUtils = new httpUtils2(headers);
        String s = httpUtils.get(url1, "utf-8");
        String[] split = s.split("\\.split\\('\\^'\\);");
        List<String[]> lists = new LinkedList<>();
        for (String s1 : split) {

            System.out.println("----------------------------------------------");
            String s2 = tools.regexStr1(s1, "A\\[\\d*]=.*").replaceAll("A\\[.*\\]=\"", "").replaceAll("\"", "");
            String[] A = s2.split("\\^");
            System.out.println(s2);
            lists.add(A);
        }
    }

    @Test
    public void test8() {
        try {
            tools tools = new tools();
            List<Header> headers = new ArrayList<>();
            headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36"));
            headers.add(new BasicHeader("Referer", "https://live.titan007.com/oldIndexall.aspx"));
            httpUtils2 httpUtils = new httpUtils2(headers);
            long timestampInSeconds = Instant.now().getEpochSecond();
            String sj = "007" + timestampInSeconds + "000";
            String url = "https://livestatic.titan007.com/vbsxml/alias3.txt?r=" + sj;
            String s = httpUtils.get(url, "utf-8");
            String[] split = s.split(",");
            Map<String, String> T = new HashMap<>();
            for (String s1 : split) {
                String[] split1 = s1.split("\\^");
                T.put(split1[0], split1[2]);
            }

        /*System.out.println(T.get("2222"));
        System.out.println(T.get("37754"));*/
            String url1 = "https://livestatic.titan007.com/vbsxml/bfdata_ut.js?r=" + sj;
            String sA = httpUtils.get(url1, "utf-8");
            sA = tools.regexStr1(sA, "A\\[.*\\].*.split\\('\\^'\\);");
            String[] splitA = sA.split("\\.split\\('\\^'\\);");
            System.out.println(splitA.length);
            int i = 0;
            for (String s1 : splitA) {
                String s2 = tools.regexStr1(s1, "A\\[\\d*]=.*").replaceAll("A\\[.*\\]=\"", "").replaceAll("\"", "");
                System.out.println("----------------------------------------------");
                System.out.println(i);
                String[] A = s2.split("\\^");
                System.out.println(A[5] + " - " + A[8]);
                System.out.println(A[37] + " - " + A[38]);
                String Z = T.get(A[37]) == null ? A[5] : T.get(A[37]);
                String K = T.get(A[38]) == null ? A[8] : T.get(A[38]);
                Z = A[5].contains("(中)") ? Z + "(中)" : Z;
                K = A[8].contains("(中)") ? K + "(中)" : K;
                System.out.println(A[0] + " - " + A[2] + " - " + Z + " - " + A[11] + " - " + K + " - " + A[43] + "-" + A[36]);
                i++;

            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test9() throws IOException, ParseException {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36"));
        headers.add(new BasicHeader("Referer", "https://live.titan007.com/oldIndexall.aspx"));
        httpUtils2 httpUtils = new httpUtils2(headers);
        String s = httpUtils.get("https://bf.titan007.com/football/Over_20240727.htm", "utf-8");
        Elements select = Jsoup.parse(s).select("#tr1_238 > td.icons2 > tz");
        System.out.println("aa:"+select.size());
    }


}


