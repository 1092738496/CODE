package com.meditation.dao;

import org.apache.hc.core5.http.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @time: 2024/7/6 18:49
 * @description:
 */
@Component
public class da {
    @Autowired
    private com.meditation.utils.tools tools;
    @Autowired
    ThreadPoolExecutor pool;
    @Autowired
    com.meditation.utils.httpUtils httpUtils;
    public LinkedHashMap<String,List<List<String>>> xiang_tongji(String id) {
        LinkedHashMap<String,List<List<String>>> maps_s = new LinkedHashMap<>();
        String html = null;
        try {
            html = httpUtils.get("https://vip.titan007.com/OverDown_n.aspx?id=" + id, "utf-8");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        Elements trs = Jsoup.parse(html).select("#odds > tbody > tr");
        //System.out.println("--------------");
        List<CompletableFuture<List<List<String>>>> futures = new ArrayList<>();
        for (int i = 0; i < trs.size(); i++) {
            Element tr = trs.get(i);
            String companyID = tr.select("td:nth-child(2) > span").attr("companyid");
            if (!companyID.equals("")) {
                CompletableFuture future =
                        CompletableFuture.runAsync(()->{
                            String name = tr.select("td:nth-child(1)").text().replaceAll("封", "");
                            String url = "https://vip.titan007.com" + tr.select("td:last-child > a:nth-child(1)").attr(
                                    "href");
                            // System.out.println(name);
                            //System.out.println(url);
                            List<List<String>> lists = ji_zao(url);
                            maps_s.put(name, lists);
                        },pool);
                futures.add(future);
            }
            CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

            // 阻塞主线程，等待所有任务及其后续处理完成
            combinedFuture.join();

        }
        //System.out.println("--------------");
        return maps_s;
    }

    public  List<List<String>> ji_zao(String url) {
        List<List<String>> lists = new ArrayList<>();
        String html = null;
        try {
            html = httpUtils.get(url, "gb2312");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        Elements select = Jsoup.parse(html).select("#odds2 > table > tbody > tr");
        for (int i = 1; i < select.size(); i++) {
            Element trs = select.get(i);
            Elements tds = trs.select("td");
            if (!tds.get(tds.size() - 1).text().equals("滚")) {
                List<String> list = new ArrayList<>();
                list.add(tds.get(tds.size() - 5).text());
                list.add(tds.get(tds.size() - 4).text());
                list.add(tds.get(tds.size() - 3).text());
                list.add(tds.get(tds.size() - 2).text());
                list.add(tds.get(tds.size() - 1).text());
                lists.add(list);
            }
        }
        return lists;
    }
}
