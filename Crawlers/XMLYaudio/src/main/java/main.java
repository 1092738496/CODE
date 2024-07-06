import cn.hutool.core.io.FileUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Route;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2024/2/13 7:18
 * @description:
 */

public class main {
    static String Title;
    static String name;

    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
            // 设置要发送的 Cookie
            String cookie = "_xmLog=h5&3bb06efb-b776-4367-ba6c-ad798e25495e&process.env.sdkVersion; " +
                    "xm-page-viewid=ximalaya-web; impl=www.ximalaya.com.login; " +
                    "x_xmly_traffic=utm_source%253A%2526utm_medium%253A%2526utm_campaign%253A%2526utm_content%253A" +
                    "%2526utm_term%253A%2526utm_from%253A; wfp=ACM5MGViYmMyODJiMDEyOGJiJXvmT4HtbJt4bXdlYl93d3c; " +
                    "fds_otp=7191038833642905774; 1&remember_me=y; " +
                    "1&_token=239804814" +
                    "&5F837B90140ND9CF586F0363BE1B5F61F9B5F159D77C9004338E6E4D37C4C7A2EC646684F31042M035CACB18601B74_; 1_l_flag=239804814&5F837B90140ND9CF586F0363BE1B5F61F9B5F159D77C9004338E6E4D37C4C7A2EC646684F31042M035CACB18601B74__2024-05-1504:57:30; login_type=code_mobile; web_login=1715720576401";

            Map<String, String> headers = Collections.singletonMap("Cookie", cookie);
            // 设置拦截规则
            List<Header> headerss = new ArrayList<>();
            headerss.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36"));
            httpUtils httpUtils = new httpUtils(headerss);

            BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions()
                    .setViewportSize(640, 480)
                    .setScreenSize(640, 480)
                    .setIgnoreHTTPSErrors(true));
            // 禁止图片加载
            browserContext.route("**/*.{png,jpg,jpeg,webp,avif,svg}", Route::abort);
            Page page = browserContext.newPage();
            page.setExtraHTTPHeaders(headers);
            page.onResponse(response -> {
                String url = response.url();
                if (url.contains(".m4a")) {
                    System.out.println(name);
                    System.out.println("Intercepted response URL: " + url);
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    httpUtils.download(url, "D:\\" + Title + "\\" + name + ".m4a");
                }
            });
            // 跳转到页面
            page.navigate("https://www.ximalaya.com/album/74236462");
            page.waitForLoadState();
            Title = page.locator("#award > main > div.album-detail > div.clearfix > div.detail.layout-main > div" +
                    ".detail-wrapper.z_i > div.album-info.clearfix.z_i > div.info.z_i > div:nth-child(1) > h1").textContent().replaceAll("\\|", " ");
            System.out.println(Title);
            FileUtil.mkdir("D:\\" + Title);
            int pagesize = 0;
            int pagedata = 1;
            do {
                page.locator("#anchor_sound_list > div.sound-list.H_g > ul > li:nth-child(1) > div.text._nO > a > " +
                        "span").waitFor();
                String liscss = "#anchor_sound_list > div.sound-list.H_g > ul > li";
                List<ElementHandle> lis = page.querySelectorAll(liscss);
                pagesize = lis.size();
                //页数
                if (pagedata >= 0) {
                    for (int i = 0; i < lis.size(); i++) {
                        int b = i + 1;
                        System.out.println(b);
                        name = page.evaluate("document.querySelector(\"#anchor_sound_list > div.sound-list.H_g > " +
                                "ul " +
                                ">" + " li:nth-child(" + b + ") > div.text._nO > a > span\").textContent").toString();
                        System.out.println(name);
                        if (name.equals("")) {
                            i--;
                            continue;
                        }
                        String liclick = "document.querySelector(\"#anchor_sound_list > div.sound-list.H_g > ul > " +
                                "li:nth-child(" + b + ") > div.icon-wrapper._nO > div > div.defaultDOM._nO\").click()";

                        while (!page.locator("#anchor_sound_list > div.sound-list.H_g > ul > li:nth-child(" + (i + 1) + ") > div" +
                                ".icon-wrapper._nO > div.all-icon.playing._nO").isVisible()) {
                            page.evaluate(liclick);
                            TimeUnit.SECONDS.sleep(1);
                        }
                        if (page.locator("body > div:nth-child(18) > div > div.xui-modal-wrap.zj_ > div").isVisible()) {

                            page.evaluate("document.querySelector(\"body > div:nth-child(18) > div > div" +
                                    ".xui-modal-wrap.zj_ > div > div > span > i\").click()");
                            TimeUnit.SECONDS.sleep(2);
                        }
                        page.locator("#anchor_sound_list > div.sound-list.H_g > ul > li:nth-child(" + (i + 1) + ") > " +
                                "div" +
                                ".icon-wrapper._nO > div.all-icon.playing._nO").waitFor();
                    }
                }
                page.evaluate("document.querySelectorAll(\"#anchor_sound_list > div.sound-list.H_g > div > nav > ul >" +
                        " li\")[document.querySelectorAll(\"#anchor_sound_list > div.sound-list.H_g > div > nav > ul " +
                        "> li\").length-1].querySelector(\"a\").click()");
                pagedata++;
                TimeUnit.SECONDS.sleep(2);
            } while (pagesize == 30);

            // 关闭浏览器
            browser.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
