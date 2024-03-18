import cn.hutool.core.net.URLEncodeUtil;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @time: 2023/12/17 23:51
 * @description:
 */

public class mian {
    public static void main(String[] args) {

        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/120.0.0.0 " +
                "Safari/537.36"));
        httpUtils httpUtils = new httpUtils(headers);
        LinkedList<String> list = new LinkedList<String>();
        System.out.println("请输入要搜索的小说:");
        LinkedMap<String, String> maps = new LinkedMap<String, String>();
        try {
            String html = httpUtils.get("https://crxs.me/");
            Elements as = Jsoup.parse(html).select("body > div.main > div > div.aside > div:nth-child(3) > a");
            int i = 1;
            for (Element a : as) {
                String href = a.attr("href");
                System.out.println(i + "." + a.text());
                i++;
                list.add(href);
            }

            Scanner input = new Scanner(System.in);
            int x = input.nextInt();
            String href = "https://crxs.me" + list.get(x - 1);
            String decode = URLEncodeUtil.encode(href);
            String s = httpUtils.get(decode);
            Elements select = Jsoup.parse(s).select("body > div.main > div > div.body > div:nth-child(3) > " +
                    "div:nth-child(3) > div:nth-child(1) > a");
            int page = Integer.parseInt(select.get(select.size() - 2).text());


            String s1 = decode.split(".html")[0];


            for (int j = 1; j <= page; j++) {
                String s2 = httpUtils.get(s1 + "/" + j + ".html");
                Elements selects = Jsoup.parse(s2).select("body > div.main > div > div.body > div:nth-child(3) > div" +
                        ".lists " +
                        "> a");
                for (Element element : selects) {
                    String text = element.select("div > div.title").text();
                    System.out.println(text+":"+"https://crxs.me/" + element.attr("href"));
                    if (text.contains("催眠")) {
                        maps.put(text, "https://crxs.me/" + element.attr("href"));
                    }
                }
            }
            System.out.println("~~~~~~~~~~正常结果~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            for (String s2 : maps.keySet()) {
                System.out.println(s2 + ":" + maps.get(s2));
            }

        } catch (Exception e) {
            System.out.println("~~~~~~~~~~异常结果~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            for (String s2 : maps.keySet()) {
                System.out.println(s2 + ":" + maps.get(s2));
            }
            e.printStackTrace();
        }
    }


}
