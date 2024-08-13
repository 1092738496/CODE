import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Route;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2024/5/19 2:03
 * @description:
 */

public class test3 {

    public static void main(String[] args) {
        int currentYear = LocalDate.now().getYear();
        System.out.println(currentYear);
    }

    public void aa(ArrayList<HashMap<String, String>> datesplit) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            httpUtils httpUtils = new httpUtils();
            List<Header> headers = new ArrayList<>();
            headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"));
            httpUtils.setHeaders(headers);
            // 设置拦截规则
            List<Header> headerss = new ArrayList<>();
            headerss.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36"));

            BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions()
                    .setViewportSize(640, 480)
                    .setScreenSize(640, 480)
                    .setIgnoreHTTPSErrors(true));
            // 禁止图片加载
            browserContext.route("**/*.{png,jpg,jpeg,webp,avif,svg}", Route::abort);
            Page page = browserContext.newPage();


            // 跳转到页面
            page.navigate("https://live.titan007.com/oldIndexall.aspx");
            page.waitForLoadState();
            page.locator("#table_live > tbody > tr[1]");
            page.locator("#button6").click();
            TimeUnit.SECONDS.sleep(1);
            DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");

            Elements trs = Jsoup.parse(page.content()).select("#table_live > tbody > tr");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            DateTimeFormatter thisformatter = DateTimeFormatter.ofPattern("yyyyMM-dd HH:mm");
            LocalDateTime endTime = null;
            LocalDateTime startTime = null;

            LocalDateTime endTime1 = null;
            LocalDateTime startTime1 = null;
            if (datesplit.size() == 2) {
                endTime1 = LocalDateTime.parse(datesplit.get(0).get("miao"),
                        timeformatter);
                startTime1 = LocalDateTime.parse(datesplit.get(1).get("miao"),
                        timeformatter);
                String currentYear = String.valueOf(LocalDate.now().getYear());
                endTime =
                        LocalDateTime.parse(datesplit.get(0).get("date") + "-" + datesplit.get(0).get("day") + " " + datesplit.get(0).get("miao"),
                                thisformatter);
                startTime =
                        LocalDateTime.parse(datesplit.get(1).get("date") + "-" + datesplit.get(1).get("day") + " " + datesplit.get(1).get("miao"),
                                thisformatter);
            } else {
                endTime1 = LocalDateTime.parse(datesplit.get(0).get("miao"),
                        timeformatter);
                startTime1 = LocalDateTime.parse(datesplit.get(0).get("miao"),
                        timeformatter);
                endTime =
                        LocalDateTime.parse(datesplit.get(0).get("date") + "-" + datesplit.get(0).get("day") + " " + datesplit.get(0).get("miao"),
                                thisformatter);
                startTime =
                        LocalDateTime.parse(datesplit.get(0).get("date") + "-" + datesplit.get(0).get("day") + " " + datesplit.get(0).get("miao"),
                                thisformatter);
            }

            for (Element tr : trs) {
                String align = tr.attr("align");
                if (!align.equals("")) {
                    String text = tr.select("td:nth-child(2) > span").text();
                    if (!text.equals("")) {
                        String id = tr.attr("id").split("_")[1];
                        String timed = tr.select("td:nth-child(3)").text();
                        LocalDateTime dateTime1 = LocalDateTime.parse(timed, timeformatter);
                        boolean isInBetween1 =
                                dateTime1.isBefore(startTime) | dateTime1.isEqual(startTime) && dateTime1.isAfter(endTime) | dateTime1.isEqual(endTime);
                        if (isInBetween1) {
                            String s = httpUtils.get("https://vip.titan007.com/AsianOdds_n.aspx?id=" + id, "utf-8");
                            String time = Jsoup.parse(s).select("body > div.header > div.analyhead > div.vs > " +
                                    "div:nth-child(1) > span.time").text();

                            String trim = tools.regexStr1(time, "[0-9- :]").trim();


                            LocalDateTime dateTime = null;
                            try {
                                dateTime = LocalDateTime.parse(trim, formatter);
                            } catch (Exception e) {
                                System.out.println(text);
                                System.out.println(trim);
                                e.printStackTrace();
                            }

                            boolean isInBetween =
                                    dateTime.isBefore(startTime) | dateTime.isEqual(startTime) && dateTime.isAfter(endTime) | dateTime.isEqual(endTime);

                            if (isInBetween) {
                                System.out.println(text);
                                System.out.println(id);
                                System.out.println(trim);
                            } else {
                                System.out.println(text);
                            }
                        }
                    }
                }
            }
        } catch (InterruptedException | ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private String Bytes2String(byte[] bytes) {
        Charset cs = StandardCharsets.UTF_8;
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes).flip();
        CharBuffer cb = cs.decode(bb);
        String res = new String(cb.array());
        return res;
    }

}

