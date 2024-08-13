import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.SelectOption;
import dao.pressdao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pojo.press;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2024/1/23 15:37
 * @description:
 */

public class tools {
    public void getPageurl() {
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        pressdao pressdao = sqlSession.getMapper(pressdao.class);


        // String stealth = this.getClass().getClassLoader().getResource("stealth.min.js").getPath();
        Playwright playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(false);
        Browser browser = playwright.chromium().launch(launchOptions);
        BrowserContext Context = browser.newContext();
        Page page = Context.newPage();
        page.navigate("https://login.cnki.net/login/");
        page.evaluate("document.querySelector(\"#TextBoxUserName\").value='13277379523'");
        page.evaluate("document.querySelector(\"#TextBoxPwd\").value='guanzizai3431'");
        page.evaluate("document.querySelector(\"#agreement\").click();");
        page.evaluate("document.querySelector(\"#Button1\").click();");


        page.navigate("https://kns.cnki.net/knavi/newspapers/SJBD/detail?uniplatform=NZKPT");
        //    Context.addInitScript(stealth);
        page.locator("#yearlist").selectOption(new SelectOption().setLabel("2023年"));

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<ElementHandle> elements = page.querySelectorAll("#jcDate > div > li");
        for (int i = 0; i <= elements.size(); i++) {
            String h1 = page.evaluate("document.querySelector(\"#jcDate > div > li:nth-child(" + (i + 1) + ") > h1\")" +
                    ".textContent").toString().replaceAll("月", "");
            //String h1 = elements.get(i).querySelector("h1").innerText().replaceAll("月", "");
            if (Integer.valueOf(h1) >= 5) {
                //elements.get(i).click();
                List<ElementHandle> dds = elements.get(i).querySelectorAll("dl > dd");
                for (int j = 0; j < dds.size(); j++) {
                    // dds.get(j).click();
                    page.evaluate("document.querySelector(\"#jcDate > div > li:nth-child(" + (i + 1) + ") > dl > " +
                            "dd:nth-child" +
                            "(" + (j + 1) + ") > " +
                            "a\").click();");
                    //  String date = dds.get(j).innerText();
                    String date = page.evaluate("document.querySelector(\"#jcDate > div > li:nth-child(" + (i + 1) +
                            ") " +
                            "> dl > " +
                            "dd:nth-child" +
                            "(" + (j + 1) + ") > " +
                            "a\").text").toString();
                    System.out.println(date);
                    page.locator("#articleData > table > tbody > tr:nth-child(1) > td.name").waitFor();
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    List<ElementHandle> element = page.querySelectorAll("#articleData > table > tbody > tr");
                    for (ElementHandle el : element) {
                        press press = new press();
                        press.setDate(date);
                        String title = el.querySelector("td.name").innerText();
                        String url = el.querySelector("td.name > a").getAttribute("href");
                        String author = el.querySelector("td:nth-child(4)").innerText();
                        press.setTitle(title);
                        press.setUrl(url);
                        press.setAuthor(author);

                        Page page1 = Context.newPage();
                        page1.navigate(url);
                        try {
                            page1.locator("#DownLoadParts > div.operate-left > ul > li.btn-html > a").waitFor();
                        } catch (Exception e) {
                            page1.close();
                        }
                        page1.querySelector("#DownLoadParts > div.operate-left > ul > li.btn-html > a").click();

                        Page popup = page1.waitForPopup(() -> {

                        });
                        popup.waitForLoadState();

                        String s = popup.querySelector("#mainBody > div.main > div.content > div" +
                                ".top-title > p").innerText();
                        press.setSubtitle1(s);


                        List<ElementHandle> elementX = popup.querySelectorAll("#mainBody > div.main > " +
                                "div.content " +
                                "> *");
                        String text = "";
                        for (int h = 3; h < elementX.size()-1; h++) {
                            text+=elementX.get(h).innerText();
                        }
                        press.setText(text);
                        popup.close();
                        page1.close();
                        pressdao.insert(press);
                         System.out.println(press);
                        System.out.println();

                    }

                    //  page.locator("#partiallistcount2").waitFor();
                    Integer integer = Integer.valueOf(page.locator("#partiallistcount2").innerText());
                    for (int z = 1; z < integer; z++) {
                        page.evaluate("document.querySelector(\"#rightCatalog > div.listbox.listbox-col > div.pagebox" +
                                " > a.next\").click()");
                        /*page.locator("#rightCatalog > div.optsbox.clearfix.opts-col > div.resultInfo.fr > a" +
                                ".page-next").click();*/
                        page.locator("#articleData > table > tbody > tr:nth-child(1) > td.name").waitFor();
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        List<ElementHandle> element2 = page.querySelectorAll("#articleData > table > tbody > tr");
                        for (ElementHandle el : element2) {
                            press press = new press();
                            press.setDate(date);
                            String title = el.querySelector("td.name").innerText();
                            String url = el.querySelector("td.name > a").getAttribute("href");
                            String author = el.querySelector("td:nth-child(4)").innerText();
                            press.setTitle(title);
                            press.setUrl(url);
                            press.setAuthor(author);

                            Page page1 = Context.newPage();
                            page1.navigate(url);
                            try {
                                page1.locator("#DownLoadParts > div.operate-left > ul > li.btn-html > a").waitFor();
                            } catch (Exception e) {
                                page1.close();
                            }
                            page1.querySelector("#DownLoadParts > div.operate-left > ul > li.btn-html > a").click();

                            Page popup = page1.waitForPopup(() -> {

                            });
                            popup.waitForLoadState();

                            String s = popup.querySelector("#mainBody > div.main > div.content > div" +
                                    ".top-title > p").innerText();
                            press.setSubtitle1(s);


                            List<ElementHandle> elementX = popup.querySelectorAll("#mainBody > div.main > " +
                                    "div.content " +
                                    "> *");
                            String text = "";
                            for (int h = 3; h < elementX.size()-1; h++) {
                                text+=elementX.get(h).innerText();
                            }
                            press.setText(text);
                            popup.close();
                            page1.close();
                            pressdao.insert(press);
                             System.out.println(press);
                            System.out.println();

                        }
                       /* page.evaluate("document.querySelector(\"#jcDate > div > li:nth-child("+i+") > dl >
                       dd:nth-child" +
                                "("+j+") > " +
                                "a\").click()");*/
                    }
                    sqlSession.commit();
                }

            }
        }
    }

  
}
