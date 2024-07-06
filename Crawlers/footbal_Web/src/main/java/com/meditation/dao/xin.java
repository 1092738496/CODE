package com.meditation.dao;

import com.meditation.utils.httpUtils;
import com.meditation.utils.tools;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import org.apache.hc.core5.http.ParseException;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2024/7/6 18:49
 * @description:
 */

@Component
public class xin {

    @Autowired
    private Page page;

    @Autowired
    private tools tools;

    @Autowired
    httpUtils httpUtils;

    public LinkedHashMap<String, String> duisai_wangji(String sid) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        try {
            boolean b = Greater_than_13(sid);
            if (b) {


                System.out.println(sid);

                page.navigate("https://zq.titan007.com/analysis/" + sid + "cn.htm");
                page.locator("#vsNew > tbody > tr > td > table:nth-child(2) > tbody > tr.blue_t2 > td > a > font").waitFor();

                String liansai = Jsoup.parse(page.content()).select("body > div.header > div.analyhead.new > div.vs >" +
                        " " +
                        "div:nth-child(1) > .LName").text();

                System.out.println(liansai);
                Integer h_index = 6;
                Integer a_index = 6;
                List<ElementHandle> h_size = page.querySelectorAll("#hn_s > option");
                List<ElementHandle> a_size = page.querySelectorAll("#an_s > option");
                if (h_size.size() <= 6) {
                    h_index = h_size.size();
                }
                if (a_size.size() <= 6) {
                    a_index = a_size.size();
                }
                //-------------------------------------------------------------------
                //全
                System.out.println("全");
                String hometj = "null";
                if (h_index > 2) {
                    page.locator("#hn_s").selectOption(h_index.toString()).size();
                    hometj = page.evaluate("document.querySelector(\"#table_hn > tbody > tr:last-child >" +
                            " td\").textContent").toString();

                    System.out.println("主:" + hometj);
                }
                Double G = tools.Re(tools.regexStr(hometj, "胜率:([0-9*.])*").replaceAll("胜率:", ""));
                Double H = tools.Re(tools.regexStr(hometj, "赢率:([0-9*.])*").replaceAll("赢率:", ""));
                if (G != null && H != null) {
                    map.put("主队不分主客全赛事胜率", G + "%");
                    map.put("主队不分主客全赛事赢率", H + "%");
                } else {
                    map.put("主队不分主客全赛事胜率", "");
                    map.put("主队不分主客全赛事赢率", "");
                }

                String ktj = "null";
                if (a_index > 2) {
                    page.locator("#an_s").selectOption(a_index.toString()).size();
                    ktj = page.evaluate("document.querySelector(\"#table_an > tbody > tr:last-child > td\")" +
                            ".textContent").toString();

                    System.out.println("客:" + ktj);

                }
                Double I = tools.Re(tools.regexStr(ktj, "胜率:([0-9*.])*").replaceAll("胜率:", ""));
                Double J = tools.Re(tools.regexStr(ktj, "赢率:([0-9*.])*").replaceAll("赢率:", ""));
                if (I != null && J != null) {
                    map.put("客队不分主客全赛事胜率", I + "%");
                    map.put("客队不分主客全赛事赢率", J + "%");
                } else {
                    map.put("客队不分主客全赛事胜率", "");
                    map.put("客队不分主客全赛事赢率", "");
                }


                //-------------------------------------------------------------------
                //包含主客全


                page.evaluate("document.querySelector(\"#hn_t\").click()");
                page.evaluate("document.querySelector(\"#an_t\").click()");
                h_size = page.querySelectorAll("#hn_s > option");
                a_size = page.querySelectorAll("#an_s > option");
                if (h_size.size() <= 6) {
                    h_index = h_size.size();
                }
                if (a_size.size() <= 6) {
                    a_index = a_size.size();
                }


                System.out.println("主客全");
                String hometj2 = "null";
                if (h_index >= 2) {
                    page.locator("#hn_s").selectOption(h_index.toString());
                    hometj2 = page.evaluate("document.querySelector(\"#table_hn > tbody > tr:last-child >" +
                            " td\").textContent").toString();

                    System.out.println("主:" + hometj2);

                }
                Double K = tools.Re(tools.regexStr(hometj2, "胜率:([0-9*.])*").replaceAll("胜率:", ""));
                Double L = tools.Re(tools.regexStr(hometj2, "赢率:([0-9*.])*").replaceAll("赢率:", ""));
                if (K != null && L != null) {
                    map.put("主队同主全赛事胜率", K + "%");
                    map.put("主队同主全赛事赢率", L + "%");
                } else {
                    map.put("主队同主全赛事胜率", "");
                    map.put("主队同主全赛事赢率", "");
                }
                String ktj2 = "null";
                if (a_index >= 2) {
                    page.locator("#an_s").selectOption(a_index.toString());
                    ktj2 = page.evaluate("document.querySelector(\"#table_an > tbody > tr:last-child > td\")" +
                            ".textContent").toString();

                    System.out.println("客:" + ktj2);
                }
                Double M = tools.Re(tools.regexStr(ktj2, "胜率:([0-9*.])*").replaceAll("胜率:", ""));
                Double N = tools.Re(tools.regexStr(ktj2, "赢率:([0-9*.])*").replaceAll("赢率:", ""));
                if (M != null && N != null) {
                    map.put("客队同客全赛事胜率", M + "%");
                    map.put("客队同客全赛事赢率", N + "%");
                } else {
                    map.put("客队同客全赛事胜率", "");
                    map.put("客队同客全赛事赢率", "");
                }


                //-------------------------------------------------------------------
                //包含主客自身
                System.out.println("主客自身");

                List<ElementHandle> inputs = page.querySelectorAll("#hn_l > input");
                List<ElementHandle> labels = page.querySelectorAll("#hn_l > label");
                for (int j = 0; j < labels.size(); j++) {
                    if (!liansai.contains(labels.get(j).textContent())) {
                        inputs.get(j).click();
                    }
                }

                h_size = page.querySelectorAll("#hn_s > option");
                if (h_size.size() <= 6) {
                    h_index = h_size.size();
                }
                String hometj3 = "null";
                if (h_index >= 2) {

                    page.locator("#hn_s").selectOption(h_index.toString());
                    hometj3 = page.evaluate("document.querySelector(\"#table_hn > tbody > tr:last-child >" +
                            " td\").textContent").toString();

                    System.out.println("主:" + hometj3);
                }
                Double O = tools.Re(tools.regexStr(hometj3, "胜率:([0-9*.])*").replaceAll("胜率:", ""));
                Double P = tools.Re(tools.regexStr(hometj3, "赢率:([0-9*.])*").replaceAll("赢率:", ""));
                if (O != null && P != null) {
                    map.put("主队同主本赛事胜率", O + "%");
                    map.put("主队同主本赛事赢率", P + "%");
                } else {
                    map.put("主队同主本赛事胜率", "");
                    map.put("主队同主本赛事赢率", "");
                }

                //---
                List<ElementHandle> inputs2 = page.querySelectorAll("#an_l > input");
                List<ElementHandle> labels2 = page.querySelectorAll("#an_l > label");
                for (int j = 0; j < labels2.size(); j++) {
                    if (!liansai.contains(labels2.get(j).textContent())) {
                        inputs2.get(j).click();
                    }
                }
                a_size = page.querySelectorAll("#an_s > option");

                if (a_size.size() <= 6) {
                    a_index = a_size.size();
                }
                String ktj3 = "null";
                if (a_index >= 2) {
                    page.locator("#an_s").selectOption(a_index.toString());
                    ktj3 = page.evaluate("document.querySelector(\"#table_an > tbody > tr:last-child > td\")" +
                            ".textContent").toString();

                    System.out.println("客:" + ktj3);
                }
                Double Q = tools.Re(tools.regexStr(ktj3, "胜率:([0-9*.])*").replaceAll("胜率:", ""));
                Double R = tools.Re(tools.regexStr(ktj3, "赢率:([0-9*.])*").replaceAll("赢率:", ""));
                if (Q != null && R != null) {
                    map.put("客队同客本赛事胜率", Q + "%");
                    map.put("客队同客本赛事赢率", R + "%");
                } else {
                    map.put("客队同客本赛事胜率", "");
                    map.put("客队同客本赛事赢率", "");
                }

                if (G != null && H != null && I != null && J != null) {

                    if (G >= 60 && H >= 50 && I <= 40 && J <= 35) {
                        map.put("标注1", "主队热");
                    } else if (I >= 60 && J >= 50 && G <= 40 && H <= 35) {
                        map.put("标注1", "客队热");
                    } else {
                        map.put("标注1", "");
                    }
                } else {
                    map.put("标注1", "");
                }
                if (K != null && L != null && M != null && N != null) {
                    if (K >= 60 && L >= 50 && M <= 40 && N <= 35) {
                        map.put("标注2", "主队热");
                    } else if (M >= 60 && N >= 50 && K <= 40 && L <= 35) {
                        map.put("标注2", "客队热");
                    } else {
                        map.put("标注2", "");
                    }
                } else {
                    map.put("标注2", "");
                }

                if (O != null && P != null && Q != null && R != null) {
                    if (O >= 60 && P >= 50 && Q <= 40 && R <= 35) {
                        // lists.get(i).add("主队热");
                        map.put("标注3", "主队热");
                    } else if (Q >= 60 && R >= 50 && O <= 40 && P <= 35) {
                        // lists.get(i).add("客队热");
                        map.put("标注3", "客队热");
                    } else {
                        map.put("标注3", "");
                    }
                } else {
                    map.put("标注3", "");
                }
                System.out.println("-----------------------------------");
                System.out.println(map);
            }
        } catch (Exception e) {

        }
        return map;
    }

    private boolean Greater_than_13(String sid) throws IOException, ParseException {
        boolean run = false;
        String s = httpUtils.get("https://1x2d.titan007.com/" + sid + ".js", "utf-8");
        if (!s.equals("")) {
            String s3 = tools.regexStr(s, "game=Array\\(\\\".*\\);");
            String[] split = s3.split("\",\"");
            if (split.length >= 13) {
                run = true;
            }
        } else {
            System.out.println("大于13,获取等为空,等待3秒");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return run;
    }
}
