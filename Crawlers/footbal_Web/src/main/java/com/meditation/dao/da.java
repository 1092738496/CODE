package com.meditation.dao;

import org.apache.hc.core5.http.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @time: 2024/7/6 18:49
 * @description:
 */
@Component
public class da {
    @Autowired
    private com.meditation.utils.tools tools;

    @Autowired
    com.meditation.utils.httpUtils httpUtils;
    public void xiang_tongji(String id){
        String html = null;
        try {
            html = httpUtils.get("https://vip.titan007.com/AsianOdds_n.aspx?id=" + id, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Elements trs = Jsoup.parse(html).select("#odds > tbody > tr");
        System.out.println("--------------");
        for (int i = 0; i < trs.size(); i++) {
            Element tr = trs.get(i);
            String companyID = tr.select("td:nth-child(2) > span").attr("companyid");
            if (!companyID.equals("")){
                String name = tr.select("td:nth-child(1)").text().replaceAll("封", "");
                String url = "https://vip.titan007.com" + tr.select("td:last-child > a:nth-child(1)").attr("href");
                System.out.println(name);
                System.out.println(url);
                try {
                    String s = httpUtils.get(url, "utf-8");

                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        System.out.println("--------------");
    }
    public void ji_zao(String url) {
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
                String text1 = tds.get(tds.size() - 5).text();
                String text2 = tds.get(tds.size() - 4).text();
                String text3 = tds.get(tds.size() - 3).text();
                String text4 = tds.get(tds.size() - 2).text();
                System.out.println(text1+"  "+text2+"  "+text3+"  "+text4);
            }
        }
    }
}
