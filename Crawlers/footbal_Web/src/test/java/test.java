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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    httpUtils httpUtil;


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
    private CloseableHttpAsyncClient AsyncClient;


    @Autowired
    private List_view_zao list_view_zao;



    @Test
    public void test4() {
       /* list_view_ji.List_ji();*/
        list_view_zao.List_zao("2024-06-30");
    }

    @Test
    public void test1() {
        AsyncClient.start();
        tools tools = new tools();
        SimpleHttpRequest get = SimpleHttpRequest.create(Method.GET.name(), "https://1x2d" +
                ".titan007.com/" + 2586473 + ".js");
        Future<SimpleHttpResponse> execute = AsyncClient.execute(get, new FutureCallback<SimpleHttpResponse>() {
            @Override
            public void completed(SimpleHttpResponse result) {
                String s1 = tools.regexStr1(result.getBodyText(), "game=Array\\(.*\\)").replaceAll("game=Array\\(",
                        "").replaceAll("\\)", "");
                System.out.println("--------------------------------------------");
                String[] split = s1.split("\",\"");
                System.out.println(split.length);
                Double a = 0.0;
                Double b = 0.0;
                Double c = 0.0;
                for (String s2 : split) {
                    a += Double.parseDouble(s2.split("\\|")[10]);
                    b += Double.parseDouble(s2.split("\\|")[11]);
                    c += Double.parseDouble(s2.split("\\|")[12]);
                }
                System.out.println((Math.round(a / split.length * 100.0) / 100.0) + "-" + (Math.round(b / split.length * 100.0) / 100.0) + "-" + (Math.round(c / split.length * 100.0) / 100.0));
            }

            @Override
            public void failed(Exception ex) {

            }

            @Override
            public void cancelled() {

            }
        });
        try {
            execute.get();
        } catch (InterruptedException | ExecutionException e) {
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
}
