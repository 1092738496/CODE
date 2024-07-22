import com.meditation.application;
import com.meditation.service.Da_service;
import com.meditation.service.Ya_service;
import com.meditation.utils.httpUtils;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
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
    private Map<String,String> Headers;

    @Autowired
    Page page;

    @Autowired
    httpUtils httpUtil;

    @Autowired
    Playwright playwright;
    @Value("${Playwright.Headless}")
    private String Headless;


    @Autowired
    com.meditation.dao.xin xin;

    @Autowired
    com.meditation.dao.ya ya;

    @Autowired
    com.meditation.dao.da da;

    @Autowired
    Ya_service ya_service;

    @Autowired
    Da_service da_service;

    @Test
    public void test4(){
        System.out.println(ya_service.time_filtrate("2545223", "2024-07-10 03:00", 2));

        /*da_service.Da_compute("2523056");*/
    }

    @Test
    public void test1(){
       /* try {
            String s = httpUtil.get("https://zq.titan007.com/analysis/2536566sb.htm", "utf-8");
            System.out.println(Jsoup.parse(s).select("body > div.header > div.analyhead.new > div.vs > div:nth-child" +
                    "(1) > a.LName"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
*/
       xin.duisai_wangji("2511948");

        playwright.close();
    }

    @Test
    public void test2(){
        ya.xiang_tongji("2591883");
       /* ya.ji_zao("https://vip.titan007.com/changeDetail/handicap.aspx?id=2545220&companyID=19&l=0");*/
    }

    @Test
    public void test3(){
        List<List<String>> lists = new ArrayList<>();
        lists.add(Arrays.asList("12","12","12","12-15 12:45"));
        lists.add(Arrays.asList("11","11","11","11-15 12:45"));
        lists.add(Arrays.asList("10","10","10","10-15 12:45"));
        lists.add(Arrays.asList("9","9","9","9-15 12:45"));
        lists.add(Arrays.asList("8","8","8","8-15 12:45"));
        lists.add(Arrays.asList("7","7","7","7-8 19:25"));
        lists.add(Arrays.asList("6","6","6","6-15 12:45"));
        lists.add(Arrays.asList("5","5","5","5-15 12:45"));
        lists.add(Arrays.asList("4","4","4","4-15 12:45"));
        lists.add(Arrays.asList("3","3","3","3-15 12:45"));
        lists.add(Arrays.asList("2","2","2","2-15 12:45"));
        lists.add(Arrays.asList("1","1","1","1-15 12:45"));
        LocalDateTime localDateTime = LocalDateTime.of(2024, 7, 8, 23, 5);

        int i = ya_service.binary_search(lists, localDateTime);
        System.out.println(i);
    }
}
