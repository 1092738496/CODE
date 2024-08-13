package com.meditation.dao;

import com.meditation.pojo.data_list;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.Method;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @time: 2024/7/26 15:01
 * @description:
 */

@Component
public class List_view_ji {
    @Autowired
    private com.meditation.utils.tools tools;
    @Autowired
    private ThreadPoolExecutor pool;
    @Autowired
    private com.meditation.utils.httpUtils httpUtils;

    @Autowired
    private CloseableHttpAsyncClient AsyncClient;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    public  LinkedList<data_list> List_ji() {

        LinkedList<data_list> linkedLists = new LinkedList<>();
        try {

            long timestampInSeconds = Instant.now().getEpochSecond();
            String sj = "007" + timestampInSeconds + "000";
            String url = "https://livestatic.titan007.com/vbsxml/alias3.txt?r=" + sj;
            String s = httpUtils.get(url, "utf-8");
            String[] split = s.split(",");
            Map<String, String> T = new HashMap<>();
            for (String s1 : split) {
                String[] split1 = s1.split("\\^");
                T.put(split1[0], split1[2]);
            }


            String url1 = "https://livestatic.titan007.com/vbsxml/bfdata_ut.js?r=" + sj;
            String sA = httpUtils.get(url1, "utf-8");
            sA = tools.regexStr1(sA, "A\\[.*\\].*;B\\[1\\]").replaceAll("B\\[1\\]", "");
            String[] splitA = sA.split(".split\\('\\^'\\);");
            CountDownLatch latch = new CountDownLatch(splitA.length);
            for (int i = 0; i < splitA.length; i++) {
                //LinkedList<String> list = new LinkedList<>();
                String s2 = tools.regexStr1(splitA[i], "A\\[\\d*]=.*").replaceAll("A\\[.*\\]=\"", "").replaceAll("\"",
                        "");
                String[] A = s2.split("\\^");
                String Z = T.get(A[37]) == null ? A[5].replaceAll("<font color=#.*>\\(中\\)</font>", "") : T.get(A[37]);
                String K = T.get(A[38]) == null ? A[8].replaceAll("<font color=#.*>\\(中\\)</font>", "") : T.get(A[38]);
                Z = A[5].contains("(中)") ? Z + "(中)" : Z;
                K = A[8].contains("(中)") ? K + "(中)" : K;

                /*list.add(A[0]);
                list.add(A[43] + "-" + A[36]);
                list.add(A[2]);
                list.add(A[11]);
                list.add(Z);
                list.add(A[14] + "-" + A[15]);
                list.add(K);
                list.add(A[16] + "-" + A[17]);*/

                data_list data_list = new data_list();
                data_list.setId(A[0]);

                /*LocalTime time = LocalTime.parse(A[11], formatter);
                String thistime = LocalTime.now().toString().replaceAll(":(\\d{2}\\..*)", "");
                LocalTime parse = LocalTime.parse(thistime, formatter);
                int x = time.compareTo(parse);*/
                int x = Integer.parseInt(A[13]);
                data_list.setTime(A[11]);
                data_list.setDate(A[43] + "-" + A[36] +" "+A[11]+":00");
                data_list.setLeague(A[2]);
                data_list.setHomeTeam(Z);
                data_list.setVisitingTeam(K);
                data_list.setScore(x > 0 ? A[14] + "-" + A[15]:"-");
                data_list.setBscore(A[16] + "-" + A[17]);
                data_list.setMatchType("即时");
                data_list.setOrderNum(i);
                linkedLists.add(data_list);


                String AsyncUrl = "https://1x2d.titan007.com/" + A[0] + ".js";
                SimpleHttpRequest get = SimpleHttpRequest.create(Method.GET.name(), AsyncUrl);
                int finalI = i;
                AsyncClient.execute(get,
                        new FutureCallback<SimpleHttpResponse>() {
                            @Override
                            public void completed(SimpleHttpResponse result) {
                                String[] move = move1(result.getBodyText());
                                linkedLists.get(finalI).setZ_forthwith_mean(move[0]);
                                linkedLists.get(finalI).setH_forthwith_mean(move[1]);
                                linkedLists.get(finalI).setK_forthwith_mean(move[2]);

                                latch.countDown();

                            }

                            @Override
                            public void failed(Exception ex) {
                                System.out.println(ex.getMessage());
                                latch.countDown();
                            }

                            @Override
                            public void cancelled() {
                                latch.countDown();
                            }
                        });
            }
            latch.await();
        } catch (IOException | ParseException | InterruptedException e) {
            e.printStackTrace();
        }
        return linkedLists;
    }

    private String[] move1(String html) {
        String s1 = tools.regexStr1(html, "game=Array\\(.*\\)").replaceAll("game" + "=Array\\" +
                "(", "").replaceAll("\\)", "");

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

    private String[] move2(String html) {
        String[] Strings = new String[3];
        Strings[0] = "";
        Strings[1] = "";
        Strings[2] = "";
        String s1 =
                tools.regexStr1(html, "game=Array.*;var gameDetail").replaceAll("game=Array\\" +
                        "(", "").replaceAll("\\)", "").replaceAll(";var gameDetail", "");

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
