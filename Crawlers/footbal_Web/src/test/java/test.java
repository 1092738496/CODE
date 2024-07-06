import com.meditation.application;
import com.meditation.utils.httpUtils;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

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
        //ya.xiang_tongji("2562034");
        ya.ji_zao("https://vip.titan007.com/changeDetail/overunder.aspx?id=2536566&companyID=47&l=0");
    }
}
