import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        String url = "https://e-hentai.org/g/2776964/361e23317a/";
        String title = "";
        List<String> list = new ArrayList<>();

        int size = 0;
        int n = 0;
        do {
            String url2 = url;
            if (n != 0) {
                url2 = url + "?p=" + n;
            }
            String html = httpUtils.get(url2);
            Document parse = Jsoup.parse(html);
            title = parse.title();
            Elements select = parse.select("#gdt > div");
            size = select.size();
            for (Element element : select) {
                String href = element.select("div > div > a").attr("href");
                if (!href.equals("")) {
                    list.add(href);
                }
            }
            n++;

        } while (size > 40);

        new File("E://" + title).mkdir();
        int m = 1;
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1));
            String s = httpUtils.get(list.get(i));
            String src = Jsoup.parse(s).select("#img").attr("src");
            try {
                httpUtils.download(src, "E:\\" + title + "\\" + (i + 1) + ".png");
            } catch (IOException e) {
                if (m != 5) {
                    i--;
                }else{
                    m=0;
                }
                m++;
            }
        }

    }


}
