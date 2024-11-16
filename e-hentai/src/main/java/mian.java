import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.httpUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @time: 2024/10/28 3:59
 * @description:
 */

public class mian {

    public static void main(String[] args) {
        String path = "D:\\Downloads";
        String url = "https://e-hentai.org/g/2123065/9fac542b61/";
        List<Header> headers  = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 " +
                "Safari/537.36"));
        headers.add(new BasicHeader("Cookie", "nw=1; ipb_member_id=7078553; ipb_pass_hash=43d47b7d70ad68a8eae2ae2faba8ab26; sk=re0vbhy2m4ao7nntii53c2t9bu63; tagaccept=1; event=1731239058"));
        httpUtils httpUtils = new httpUtils(headers);
        String html = httpUtils.get(url);
        Document parse = Jsoup.parse(html);
        String title = parse.select("#gn").text();
        parse.select("#gn").text();
        Elements select = parse.select("div.gtb");
        Elements select1 = select.get(0).select("table > tbody > tr > td");
        int page = Integer.parseInt(select1.get(select1.size() - 2).text());
        String paths = path + "\\" + title;
        int pagex = 1;
        new File(paths).mkdir();
        Map<Integer,String> maps = new HashMap<>();
        for (int i = 0; i < page; i++) {
            String pagehtml = httpUtils.get(url+"?p=" + i);
            Elements divs = Jsoup.parse(pagehtml).select("#gdt > a");
            for (Element div : divs) {
                System.out.println(pagex);
                String href = div.attr("href");
                System.out.println("page"+" : "+href);
                maps.put(pagex,href);
                pagex++;
            }
        }
        for (Integer key : maps.keySet()) {
            System.out.println(key);
            System.out.println(maps.get(key));

            String src = Jsoup.parse(httpUtils.get(maps.get(key))).select("#img").attr("src");
            try {
                httpUtils.download(src, paths+"\\"+key+".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
