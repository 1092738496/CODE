import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @time: 2024/2/18 12:58
 * @description:
 */

public class main {
    public static void main(String[] args) throws IOException, ParseException {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36"));
        headers.add(new BasicHeader("Cookie", "HN_currency" +
                "=eyJuYW1lIjoiXHU0ZWJhXHU2YzExXHU1ZTAxIiwic2hvcnQiOiJcdTUxNDMiLCJzeW1ib2wiOiJcdTAwYTUiLCJjb2RlIjoiUk1CIiwicmF0ZSI6IjEiLCJhcmVhbmFtZSI6Ilx1NWU3M1x1NjViOVx1N2M3MyIsImFyZWFzeW1ib2wiOiJcdTMzYTEifQ%3D%3D; HN_lang=zh-CN; PHPSESSID=uvniq42teom6qf0da1jadpvpkv; HN_siteCityInfo=%7B%22cityid%22%3A%222876%22%2C%22name%22%3A%22%E5%85%AC%E5%AE%89%22%2C%22pinyin%22%3A%22gongan%22%2C%22url%22%3A%22https%3A%5C%2F%5C%2Fwww.gashw.com%22%2C%22domain%22%3A%22gongan%22%2C%22type%22%3A%222%22%2C%22default%22%3A%220%22%2C%22count%22%3A1%2C%22lng%22%3A%22112.153618%22%2C%22lat%22%3A%2229.957130%22%7D; HN_cr=MjA2ODE1MzEwN3x8UkdKd1VUTkdjVVZWY21OUGEyZGxTRUpNUmxocVVXRkdXR1U1VW5sQlIwcENjakZTZG5kTFlWVXJUVTR6UmxRMlZpdG9XRGRCVTJGWVltUlNiRlpUZGxZM1NsaHBkMkV3VnpjeFVYUXhaU3RXTjBwV2RWRXpRMVZNY0dGelJreExSSFZqU0hWUlZEUldLMEZIZG13eUwxVmlSVUoyUVdKVFZXVlpRMm94VDFaRVltaFZlRVpsU0ZZM2MwVjFWak5GVldWd1ZXcEdabUpXSzFsSGRFWjJSVlZQTVZoblJtWnBWbVZSVG5oc1JIQlhkVkpUTjBFMlowSXJRVVUzUm1WNVFuVk9aR2N4U0ZWQlpWbEhlbXhIYzBGMVdsUnRRVE5WVmt4b1dHMUdaa1ZDVDBKa01qRkhkVlpQU2xneU1XVTN8fDE3MDgyMjk4MjM%3D; HN_userid=56182"));
        httpUtils httpUtils = new httpUtils(headers);
        int index = 1;
        int pagesize = 0;
        do {
            String url = "https://job.gashw.com/general.html?page=" + index;
            String html = httpUtils.get(url);
            Elements lis = Jsoup.parse(html).select("body > div.detailContent > div.d-list > div.dl-left > ul > li");
            pagesize = lis.size();
            for (Element li : lis) {
                Elements select = li.select("a > div.left");
                String title = select.select("div.title").text();
                String text = select.select("div.salary").text();
                String label = select.select("div.label").text();
                String time = li.select("div.right > div.details > div.time").text();
                System.out.println(title);
                System.out.println(text);
                System.out.println(time);
                System.out.println(label);
                if (title.contains("中粮")) {
                    System.out.println(url);
                    return;
                }
                System.out.println("-----------------------------------------------------");
            }
            index++;
        } while (pagesize == 10);
    }
}
