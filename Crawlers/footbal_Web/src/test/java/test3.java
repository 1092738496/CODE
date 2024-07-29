import com.meditation.utils.httpUtils2;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @time: 2024/7/27 21:42
 * @description:
 */

public class test3 {
    @Test
    public void test1() throws IOException, ParseException {
        httpUtils2 httpUtils = new httpUtils2();
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"));
        httpUtils.setHeaders(headers);
        String sj = "20240720";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate parse = LocalDate.parse(sj, formatter);

        String html = httpUtils.get("https://bf.titan007.com/football/Over_" + sj + ".htm", "GBK");
        Elements trs = Jsoup.parse(html).select("#table_live > tbody > tr");
        LinkedList<LinkedList<String>> lists = new LinkedList<>();
        for (Element tr : trs) {
            String sid = tr.attr("sid");
            if (!sid.equals("")) {
                System.out.println("-----------------------------------------------");
                LinkedList<String> list = new LinkedList<>();
                list.add(sid);
                String zsj = tr.select("td:nth-child(2)").text();
                String[] timeSP = zsj.split("日");
                if (Integer.parseInt(timeSP[0]) != parse.getDayOfMonth()) {
                    LocalDate parse2 = parse.plusDays(1);
                    list.add(parse2.getYear() + "-" + parse2.getMonthValue() + "-" + parse2.getDayOfMonth());
                } else {
                    list.add(parse.getYear() + "-" + parse.getMonthValue() + "-" + parse.getDayOfMonth());
                }
                list.add(tr.select("td:nth-child(1)").text());
                list.add(timeSP[1]);
                list.add(tr.select("td:nth-child(4)").text());
                list.add(tr.select("td:nth-child(5)").text());
                list.add(tr.select("td:nth-child(6)").text());
                list.add(tr.select("td:nth-child(7)").text());
                lists.add(list);
                System.out.println(list);
            }
        }

        for (int i = 0; i < lists.size(); i++) {
            LinkedList<String> list = lists.get(i);

        }

    }

    @Test
    public void test() {
        Map<String, Integer> map = new HashMap<>();
        if (!map.isEmpty()) {
            map.put("a", map.put("a", 1) + 1);
        } else {
            map.put("a", 1);
        }
        if (map.get("aa") != null) {
            System.out.println(map.get("aa") > 2);

        }
        System.out.println(map);
    }
    @Test
    public void test2() {
        double a = -0.01;
        double abs = Math.abs(a);
        System.out.println(abs);

    }
}
