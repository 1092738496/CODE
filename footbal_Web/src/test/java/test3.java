import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meditation.utils.httpUtils2;
import com.meditation.utils.tools;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.zip.GZIPInputStream;

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
    public void test2() throws IOException, ParseException {
        tools tools = new tools();
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"));
        httpUtils2 httpUtil = new httpUtils2();
        httpUtil.setHeaders(headers);
        String sid = "2615095";
        String html = httpUtil.get("https://1x2.titan007.com/oddslist/" + sid + ".htm", "utf-8");
        String src = Jsoup.parse(html).select("body > script:nth-child(1)").attr("src");
        String js = httpUtil.get("https:" + src, "utf-8");


        String host = tools.regexStr1(js, "var hometeam_cn=.*;var guestteam_cn").replaceAll("var hometeam_cn=\"",
                "").replaceAll("\";var guestteam_cn", "");
        String guest = tools.regexStr1(js, "var guestteam_cn=.*;var hometeam_f").replaceAll("var guestteam_cn=\"",
                "").replaceAll("\";var hometeam_f", "");
        System.out.println(host);
        System.out.println(guest);
        String game = tools.regexStr1(js, "game=Array.*;var gameDetail").replaceAll("game=Array\\(", "").replaceAll(
                "\\);var gameDetail", "");

        String gameDetail = tools.regexStr1(js, "var gameDetail=Array\\(.*\\);var jcEuropeOddsData").replaceAll("\\);" +
                        "var " +
                        "jcEuropeOddsData",
                "").replaceAll("var gameDetail=Array\\(", "");
        String[] Detail = gameDetail.split(";\",\"");

        Map<String, String> id_name = new HashMap<>();
        String[] splitz = game.split("\",\"");
        for (String s : splitz) {
            System.out.println("--------------------");
            System.out.println(s);
            String[] split1 = s.split("\\|");
            // List<String> z = new ArrayList<>(Arrays.asList(split1));
            System.out.println(split1[1] + "--" + split1[split1.length - 3]);
            id_name.put(split1[1], split1[split1.length - 3]);
        }

        Map<String, List<List<String>>> gameDetails = new HashMap<>();
        for (int i = 0; i < Detail.length; i++) {
            String t = Detail[i];
            if (i == 0 | i == Detail.length - 1) {
                t = Detail[i].replaceAll("\"", "");
            }
            String[] split = t.split(";");
            String id = "";
            List<List<String>> zs = new ArrayList<>();
            for (int j = 0; j < split.length; j++) {
                String y = split[j];
                if (j == 0) {
                    String[] split1 = split[j].split("\\^");
                    id = split1[0];
                    y = split1[1];
                }
                String[] split1 = y.split("\\|");
                List<String> z = new ArrayList<>(Arrays.asList(split1));
                zs.add(z);
            }
            String name = id_name.get(id);
            gameDetails.put(name, zs);
        }


    }

    public static String decompress(byte[] compressedData) throws IOException {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedData);
             GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }

            return byteArrayOutputStream.toString("UTF-8");
        }
    }

    void aa(List<Integer> arr) {
        arr.set(1, 100);
        arr = new ArrayList<>();
        
    }

    @Test
    public void test7() throws InterruptedException, IOException, ParseException {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                try {
                    a();
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }).start();
        }
        /*while (true){
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"));
        httpUtils2 httpUtil = new httpUtils2();
        httpUtil.setHeaders(headers);
        String s = httpUtil.get("http://125.122.20.253:9527/rateServer/list/ji", "utf-8");
        }*/
        latch.await();




    }

    public void a () throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"));
        httpUtils2 httpUtil = new httpUtils2();
        httpUtil.setHeaders(headers);
        String s = httpUtil.get("http://125.122.20.253:9527/rateServer/list/ji", "utf-8");
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        JsonNode jsonNode = mapper.readValue(s, JsonNode.class);
        for (JsonNode node : jsonNode) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                System.out.println("---------------------------");
                String html = null;
                try {
                    html = httpUtil.get("http://125.122.20.253:9527/rateServer/xin/"+node.get(0).textValue(), "utf-8");
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
                System.out.println(html);
            });
            futures.add(future);
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
