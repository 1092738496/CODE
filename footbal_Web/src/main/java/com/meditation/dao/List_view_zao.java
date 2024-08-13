package com.meditation.dao;

import com.meditation.pojo.data_list;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.Method;
import org.apache.hc.core5.http.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @time: 2024/7/26 15:01
 * @description:
 */

@Component
public class List_view_zao {
    @Autowired
    private com.meditation.utils.tools tools;
    @Autowired
    private ThreadPoolExecutor pool;
    @Autowired
    private com.meditation.utils.httpUtils httpUtils;

    @Autowired
    private CloseableHttpAsyncClient AsyncClient;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public  LinkedList<data_list> List_zao(String date) {
        LinkedList<data_list> lists = new LinkedList<>();
        String html = null;
        try {
            String url = "https://bf.titan007.com/football/Over_" + date.replaceAll("-", "") + ".htm";
            html = httpUtils.get(url, "GBK");
            if (html.equals("")) {
                url = "https://bf.titan007.com/football/Next_" + date.replaceAll("-", "") + ".htm";
                html = httpUtils.get(url, "GBK");
                lists.addAll(Next(html, date));
            } else {
                lists.addAll(Over(html, date));
            }
            System.out.println(url);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


        CountDownLatch latch = new CountDownLatch(lists.size());
        for (int i = 0; i < lists.size(); i++) {
            data_list data_list = lists.get(i);

            String AsyncUrl = "https://1x2d.titan007.com/" + data_list.getId() + ".js";
            SimpleHttpRequest get = SimpleHttpRequest.create(Method.GET.name(), AsyncUrl);
            int finalI = i;
            AsyncClient.execute(get, new FutureCallback<SimpleHttpResponse>() {
                @Override
                public void completed(SimpleHttpResponse result) {
                    String[] move = move1(result.getBodyText());
                    lists.get(finalI).setZ_forthwith_mean(move[0]);
                    lists.get(finalI).setH_forthwith_mean(move[1]);
                    lists.get(finalI).setK_forthwith_mean(move[2]);
                   // System.out.println("count:" + latch.getCount());

                    latch.countDown();
                }

                @Override
                public void failed(Exception ex) {

                }

                @Override
                public void cancelled() {

                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return lists;
    }

    public  LinkedList<data_list> Over(String html, String date) {
        LocalDate parse = LocalDate.parse(date, formatter);
        Elements trs = Jsoup.parse(html).select("#table_live > tbody > tr");
        LinkedList<data_list> lists = new LinkedList<>();
        int i = 0;
        for (Element tr : trs) {
            String sid = tr.attr("sid");
            if (!sid.equals("")) {
                /*LinkedList<String> list = new LinkedList<>();
                list.add(sid);*/
                String zsj = tr.select("td:nth-child(2)").text();
                String[] timeSP = zsj.split("日");
                String localdate = "";
                if (Integer.parseInt(timeSP[0]) != parse.getDayOfMonth()) {
                    LocalDate parse2 = parse.plusDays(1);
                    localdate = parse2.getYear() + "-" + parse2.getMonthValue() + "-" + parse2.getDayOfMonth();
                } else {
                    localdate = parse.getYear() + "-" + parse.getMonthValue() + "-" + parse.getDayOfMonth();
                }
                /*list.add(tr.select("td:nth-child(1)").text());
                list.add(timeSP[1]);
                list.add(tr.select("td:nth-child(4)").text());
                list.add(tr.select("td:nth-child(5)").text());
                list.add(tr.select("td:nth-child(6)").text());
                list.add(tr.select("td:nth-child(7)").text());*/
                data_list data_list = new data_list();
                data_list.setId(sid);
                data_list.setMatchType("完场");
                data_list.setTime(timeSP[1]);
                data_list.setLeague(tr.select("td:nth-child(1)").text());
                data_list.setDate(localdate +" " + timeSP[1]+":00");
                data_list.setHomeTeam(tr.select("td:nth-child(4)").text());
                data_list.setScore(tr.select("td:nth-child(5)").text());
                data_list.setVisitingTeam(tr.select("td:nth-child(6)").text());
                data_list.setBscore(tr.select("td:nth-child(7)").text());
                data_list.setHistoryDate(localdate.replaceAll("-", ""));
                data_list.setOrderNum(i);
                i++;

                lists.add(data_list);
               // lists.add(list);
            }
        }
        return lists;
    }

    public LinkedList<data_list> Next(String html, String date) {
        LocalDate parse = LocalDate.parse(date, formatter);
        LinkedList<data_list> lists = new LinkedList<>();
        Elements trs = Jsoup.parse(html).select("#table_live > tbody > tr");
        int i = 0;
        for (Element tr : trs) {
            String sid = tr.attr("sid");
            if (!sid.equals("")) {
                /*LinkedList<String> list = new LinkedList<>();
                list.add(sid);*/
                String[] timeSP = tr.select("td:nth-child(2)").text().split(" ");
               // System.out.println(timeSP[0]+"-"+timeSP[1]);
                String localdate = "";
                if (Integer.parseInt(timeSP[0].split("-")[1]) != parse.getDayOfMonth()) {
                    LocalDate parse2 = parse.plusDays(1);
                    localdate = parse2.getYear() + "-" + parse2.getMonthValue() + "-" + parse2.getDayOfMonth();
                } else {
                    localdate = parse.getYear() + "-" + parse.getMonthValue() + "-" + parse.getDayOfMonth();
                }
               /* list.add(tr.select("td:nth-child(1)").text());
                list.add(timeSP[1]);
                list.add(tr.select("td:nth-child(4)").text());
                list.add(tr.select("td:nth-child(5)").text());
                list.add(tr.select("td:nth-child(6)").text());
                list.add(tr.select("td:nth-child(7)").text());*/

                data_list data_list = new data_list();
                data_list.setId(sid);
                data_list.setMatchType("完场");
                data_list.setLeague(tr.select("td:nth-child(1)").text());
                data_list.setDate(localdate +" " + timeSP[1]+":00");
                data_list.setTime(timeSP[1]);
                data_list.setHomeTeam(tr.select("td:nth-child(4)").text());
                data_list.setScore(tr.select("td:nth-child(5)").text());
                data_list.setVisitingTeam(tr.select("td:nth-child(6)").text());
                data_list.setBscore(tr.select("td:nth-child(7)").text());
                data_list.setHistoryDate(localdate.replaceAll("-", ""));
                data_list.setOrderNum(i);
                lists.add(data_list);
                i++;
            }
        }

        return lists;
    }

    private String[] move1(String html) {
        String s1 = tools.regexStr1(html, "game=Array\\(.*\\)").replaceAll("game" + "=Array\\" +
                "(", "").replaceAll("\\)", "");
        ;
        String[] Strings = new String[3];
        Strings[0] = "";
        Strings[1] = "";
        Strings[2] = "";
        if (!s1.equals("")) {
            String[] split = s1.split("\",\"");
            Double a = 0.0;
            Double b = 0.0;
            Double c = 0.0;
            for (String s2 : split) {
                a += Double.parseDouble(s2.split("\\|")[10]);
                b += Double.parseDouble(s2.split("\\|")[11]);
                c += Double.parseDouble(s2.split("\\|")[12]);
            }
            String at = String.valueOf(Math.round(a / split.length * 100.0) / 100.0);
            String bt = String.valueOf(Math.round(b / split.length * 100.0) / 100.0);
            String ct = String.valueOf(Math.round(c / split.length * 100.0) / 100.0);
            Strings[0] = at;
            Strings[1] = bt;
            Strings[2] = ct;
        }
        return Strings;
    }
}
