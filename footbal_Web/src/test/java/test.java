import com.meditation.application;
import com.meditation.dao.List_view_ji;
import com.meditation.dao.List_view_zao;
import com.meditation.service.Da_service;
import com.meditation.service.Ya_service;
import com.meditation.utils.httpUtils;
import com.meditation.utils.tools;
import com.microsoft.playwright.Page;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.Method;
import org.apache.hc.core5.http.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.GZIPInputStream;

/**
 * @time: 2024/7/6 15:54
 * @description:
 */
@SpringBootTest
@ContextConfiguration(classes = {application.class})

public class test {
    @Autowired
    private CloseableHttpClient client;

    @Value("#{${headers:{}}}")
    private Map<String, String> Headers;

    @Autowired
    Page page;
    @Autowired
    ThreadPoolExecutor pool;


    @Autowired
    tools tools;
    @Autowired
    httpUtils httpUtil;
    @Autowired
    private CloseableHttpAsyncClient AsyncClient;


    @Value("${Playwright.Headless}")
    private String Headless;


    @Autowired
    com.meditation.dao.xin xin;

    @Autowired
    com.meditation.dao.Ya ya;

    @Autowired
    com.meditation.dao.Da da;

    @Autowired
    Ya_service ya_service;

    @Autowired
    Da_service da_service;

    @Autowired
    List_view_ji list_view_ji;


    @Autowired
    private List_view_zao list_view_zao;


    @Test
    public void test4() {
        /* list_view_ji.List_ji();*/
        list_view_zao.List_zao("2024-06-30");
    }

    @Test
    public void test1() {
        CountDownLatch latch = new CountDownLatch(100);
        SimpleHttpRequest get = SimpleHttpRequest.create(Method.GET.name(), "http://125.122.20.253:9527/rateServer/xin/2642337");
        Future<SimpleHttpResponse> execute = AsyncClient.execute(get, new FutureCallback<SimpleHttpResponse>() {
            @Override
            public void completed(SimpleHttpResponse result) {
                System.out.println(latch.getCount());
                latch.countDown();
                System.out.println(result.getBodyText());
            }

            @Override
            public void failed(Exception ex) {
                latch.countDown();
            }

            @Override
            public void cancelled() {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        ya.xiang_tongji("2591883");
        /* Ya.ji_zao("https://vip.titan007.com/changeDetail/handicap.aspx?id=2545220&companyID=19&l=0");*/
    }

    @Test
    public void test3() {
        List<List<String>> lists = new ArrayList<>();
        lists.add(Arrays.asList("12", "12", "12", "12-15 12:45"));
        lists.add(Arrays.asList("11", "11", "11", "11-15 12:45"));
        lists.add(Arrays.asList("10", "10", "10", "10-15 12:45"));
        lists.add(Arrays.asList("9", "9", "9", "9-15 12:45"));
        lists.add(Arrays.asList("8", "8", "8", "8-15 12:45"));
        lists.add(Arrays.asList("7", "7", "7", "7-8 19:25"));
        lists.add(Arrays.asList("6", "6", "6", "6-15 12:45"));
        lists.add(Arrays.asList("5", "5", "5", "5-15 12:45"));
        lists.add(Arrays.asList("4", "4", "4", "4-15 12:45"));
        lists.add(Arrays.asList("3", "3", "3", "3-15 12:45"));
        lists.add(Arrays.asList("2", "2", "2", "2-15 12:45"));
        lists.add(Arrays.asList("1", "1", "1", "1-15 12:45"));
        LocalDateTime localDateTime = LocalDateTime.of(2024, 7, 8, 23, 5);

        int i = ya_service.binary_search(lists, localDateTime);
        System.out.println(i);
    }

    @Test
    public void test5() throws IOException, ParseException {

        String sid = "2615095";
        String html = httpUtil.get("https://1x2.titan007.com/oddslist/" + sid + ".htm", "utf-8");
        String src = Jsoup.parse(html).select("body > script:nth-child(1)").attr("src");
        String js = httpUtil.get("https:" + src, "utf-8");


        String host = tools.regexStr1(js, "var hometeam_cn=.*;var guestteam_cn").replaceAll("var hometeam_cn=\"",
                "").replaceAll("\";var guestteam_cn", "");
        String guest = tools.regexStr1(js, "var guestteam_cn=.*;var hometeam_f").replaceAll("var guestteam_cn=\"",
                "").replaceAll("\";var hometeam_f", "");
        System.out.println(host);
        System.out.println(guest);
        String game = tools.regexStr1(js, "game=Array.*;var gameDetail").replaceAll("game=Array\\(", "").replaceAll(
                "\\);var gameDetail", "");

        String gameDetail = tools.regexStr1(js, "var gameDetail=Array\\(.*\\);var jcEuropeOddsData").replaceAll("\\);var " +
                        "jcEuropeOddsData",
                "").replaceAll("var gameDetail=Array\\(", "");
        String[] Detail = gameDetail.split(";\",\"");

        Map<String,List<List<String>>> maplist = new HashMap<>();
        for (int i = 0; i < Detail.length; i++) {
            String t = Detail[i];
            if (i==0 | i == Detail.length-1) {
                t = Detail[i].replaceAll("\"", "");
            }
            String[] split = t.split(";");
            String id = "";
            List<List<String>> zs = new ArrayList<>();
            for (int j = 0; j < split.length; j++) {
                String y = split[j];
                if (j == 0) {
                    String[] split1 = split[j].split("\\^");
                    id = split1[0];
                    y = split1[1];
                }
                String[] split1 = y.split("\\|");
                List<String> z = new ArrayList<>(Arrays.asList(split1));
                zs.add(z);
            }
            maplist.put(id,zs);
        }

        Map<String, List<List<String>>> Ds = new HashMap<>();
        String[] split = game.split("\",\"");
        for (String s : split) {
            System.out.println("--------------------");
            String[] split1 = s.split("\\|");
            System.out.println(split1[1] + "--" + split1[split1.length - 3]);
            String corporation = split1[split1.length - 3];
            String id = split1[1];
            List<List<String>> lists = maplist.get(id);


        }
    }

    /**
     * 解压
     */
    public String decompress(byte[] compressedData) throws IOException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedData);
             GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[100 * 1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }

            return byteArrayOutputStream.toString("UTF-8");
        }
    }

    public boolean isGzipCompressed(byte[] data) {
        if (data == null || data.length < 2) {
            return false;
        }
        return (data[0] == (byte) 0x1F) && (data[1] == (byte) 0x8B);
    }


    @Test
    public void test6() throws IOException, ParseException {
        SimpleHttpRequest url = SimpleHttpRequest.create(Method.GET.name(), "https://1x2.titan007.com/OddsHistory" +
                ".aspx?id=136912409&sid=2574001&cid=281&l=0");


        Future<SimpleHttpResponse> td = AsyncClient.execute(url, new FutureCallback<SimpleHttpResponse>() {
            @Override
            public void completed(SimpleHttpResponse result) {


                Document parse = Jsoup.parse(result.getBodyText());
                Elements trs = parse.select("#odds > table > tbody > tr");
                System.out.println(trs);
                List<List<String>> Data_body = new ArrayList<>();
                for (int i = 1; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");
                    List<String> Data_column = new ArrayList<>();
                    for (Element td : tds) {
                        Data_column.add(td.text());
                    }
                    Data_body.add(Data_column);
                }
                System.out.println(Data_body);
            }

            @Override
            public void failed(Exception ex) {

            }

            @Override
            public void cancelled() {

            }
        });
        try {
            td.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


}
