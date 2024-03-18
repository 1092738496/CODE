import cn.hutool.core.io.file.FileReader;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Cookie;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Props;
import util.httpUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Andy
 * @time: 2023-8-6 20:30
 */

public class Data_tool {
    public void auto_login(String url) {
       /* String property = System.getProperty("user.dir");
        String stealth = new FileReader(new File(property+"\\"+"stealth.min.js")).readString();*/

        String path = this.getClass().getClassLoader().getResource("stealth.min.js").getPath();
        String stealth = new FileReader(new File(path)).readString();

        Playwright playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(false);
        Browser browser = playwright.chromium().launch(launchOptions);
        BrowserContext Context = browser.newContext();
        Context.addInitScript(stealth);
        Page page = Context.newPage();
        page.navigate(url);

        page.locator("#header_menu_top_login > a:nth-child(1)").waitFor();
        page.locator("#header_menu_top_login > a:nth-child(1)").click();
        page.locator("#dialogUsername").waitFor();
        page.locator("#dialogUsername").fill(util.Props.getStr("userName"));
       // page.evaluate("document.querySelector(\"#dialogUsername\").value=\""+util.Props.getStr("userName")+"\";");
        page.locator("#dialogPassword").fill(util.Props.getStr("passWord"));
       // page.evaluate("document.querySelector(\"#dialogPassword\").value=\""+util.Props.getStr("passWord")+"\";");
        page.locator("#dialogRemberId").click();
        page.locator("#smsValid").click();
        page.evaluate("document.querySelector(\"#smsValid\").click();");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Locator.WaitForOptions waitForOptions = new Locator.WaitForOptions();

        page.locator("#header_menu_top_login > span > span > a:nth-child(2)").waitFor();
        List<Cookie> cookies = Context.cookies();
        String cookie = "";
        for (Cookie cookiex : cookies) {
            cookie += cookiex.name+"="+cookiex.value+";";

        }
        System.out.println(cookie);
        Props.in_file(cookie);
        page.close();
        playwright.close();

    }


    public LinkedList<LinkedList<String>> reqdata(String url) {
        String cookie = Props.getStr("cookie");
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Cookie", cookie));
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like " +
                "Gecko) Chrome/115.0.0.0 " +
                "Safari/537.36"));
        util.httpUtils httpUtils = new httpUtils(headers);
        LinkedList<LinkedList<String>> linkedLists = new LinkedList<LinkedList<String>>();
        try {
            String html = httpUtils.get(url);
            Document parse = Jsoup.parse(html);
            Elements ths = parse.select("body > div.containerList.line-height22 > div > div:nth-child(1) > div" +
                    ".tabCar > div" +
                    ".tableList.shows > div > table > tbody > tr:nth-child(1) > th");
            LinkedList<String> List = new LinkedList<String>();
            for (int i = 1; i < 13; i++) {
                List.add(ths.get(i).text());
            }
            linkedLists.add(List);
            Elements select = parse.select("body > div.containerList.line-height22 > div > div");
            //System.out.println(select);
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            String dateTimeString = sdf.format(System.currentTimeMillis());
            String[] sa = dateTimeString.split("-");
            for (Element element : select) {
                String date = element.select("div.tabCar > div.tableList.shows > div > table > tbody > " +
                        "tr:nth-child(1) > th:nth-child(9)").text();
                System.out.println(date);
                String[] sc = date.split("日")[0].split("月");

                if (sa[0].equals(sc[0]) & sa[1].equals(sc[1])) {
                    Elements trs = element.select("div.tabCar > div.tableList.shows > div > table > tbody > tr");
                    for (int i = 1; i < trs.size(); i++) {
                        Elements tds = trs.get(i).select("td");
                        LinkedList<String> ret_List = new LinkedList<String>();

                        for (int j = 1; j <= 3; j++) {
                            Element element1 = tds.get(j);
                            ret_List.add(element1.text());
                        }
                        Elements a = trs.get(i).select("td.redColor.priceSum," +
                                "td.redColor.tip-text.priceSum," +
                                "td.blackColor.priceSum," +
                                "td.greenColor.tip-text.priceSum," +
                                "td.greenColor.priceSum");
                        if (a.size() == 0) {
                            this.auto_login(url);
                            return reqdata(url);
                        }
                        for (Element element1 : a) {
                            ret_List.add(element1.ownText());
                        }

                        ret_List.add(trs.get(i).select("td:nth-child(10)").text());
                        ret_List.add(trs.get(i).select("td:nth-child(11)").text());
                        ret_List.add(trs.get(i).select("td:nth-child(12)").text());
                        ret_List.add(trs.get(i).select("td:nth-child(13)").attr("remark"));

                        System.out.println(ret_List);
                        linkedLists.add(ret_List);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linkedLists;
    }
}
