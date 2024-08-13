import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;
import pojo.yaTre;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2024/3/7 17:40
 * @description:
 */

public class test {


    @Test
    public void test2() {
        boolean run = false;
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"));
        httpUtils httpUtils = new httpUtils(headers);
        String s = null;
        try {
            s = httpUtils.get("https://1x2.titan007.com/oddslist/2544730.htm", "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Element script = Jsoup.parse(s).select("script").first();
        String s1 = "https:" + tools.regexStr(script.toString(), "//[\\d.\\S].* ").replaceAll("\"", "").trim();

        String s2 = null;
        try {
            s2 = httpUtils.get(s1, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!s2.equals("")) {
            String s3 = tools.regexStr(s2, "game=Array\\(\\\".*\\);");
            String[] split = s3.split("\",\"");
            if (split.length >= 13) {
                run = true;
            }
        }
        System.out.println(run);
    }

    @Test
    public void test3() {
        yaTre getdata = new yaData().getdata("2579169");
        System.out.println(null == getdata);

    }

    @Test
    public void test4() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).
                setArgs(Arrays.asList("--enable-gpu", "--disable-software-compositing"))
        );
        BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(640, 480)
                .setScreenSize(640, 480)
                .setIgnoreHTTPSErrors(true)
        );
        // 禁止图片加载
        browserContext.route("**/*.{png,jpg,jpeg,webp,avif,svg}", route -> {
            System.out.println("Intercepted and aborted image request: " + route.request().url());
            route.abort();
        });
        Page page = browserContext.newPage();

        page.navigate("https://www.bilibili.com/");
        try {
            TimeUnit.SECONDS.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test5() {
        String time1Str = "3-10 09:34";
        String time2Str = "3-10 17:08";

        // 创建一个格式器来解析字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M HH:mm");

        // 将字符串转换为LocalTime对象
        LocalTime time1 = LocalTime.parse(time1Str, formatter);
        LocalTime time2 = LocalTime.parse(time2Str, formatter);

        // 比较两个时间是否相同
        boolean isSameTime = time1.equals(time2);

        System.out.println("这两个时间是否相同？ " + (isSameTime ? "是的" : "不，它们不同"));
    }

    @Test
    public void test6() {
        LocalDate date1 = LocalDate.of(2024, 5, 1);
        boolean isAfter = date1.isAfter(LocalDate.now()); // false
        System.out.println(isAfter);
    }

    @Test
    public void test7() throws IOException, ParseException {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36"));
        httpUtils utils = new httpUtils(headers);
        String s = "";
        for (int i = 0; i < 1000; i++) {
            /*s = utils.get(
                    "https://www.okooo.com/soccer/match/1257623/ah/line/14/",
                    "gbk");*/
            s = utils.get(
                    "https://www.okooo.com/soccer/match/1257623/ah/ajax/?page=0&trnum=0&companytype=BaijiaBooks",
                    "utf-8");
            System.out.println(i);
        }
        System.out.println(s);

        //https://www.okooo.com/soccer/match/1257623/ah/ajax/?page=2&trnum=60&companytype=BaijiaBooks
    }
}
