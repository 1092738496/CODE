package com.meditation.dao;

import com.meditation.pojo.tong_ke;
import com.meditation.utils.httpUtils;
import com.meditation.utils.tools;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import org.apache.hc.core5.http.ParseException;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2024/7/6 18:49
 * @description:
 */

@Component
public class xin {

    @Autowired
    ApplicationContext context;

    @Autowired
    private BrowserContext browserContext;

    @Autowired
    private Page page;
    @Autowired
    private tools tools;

    @Autowired
    httpUtils httpUtils;
    private final DecimalFormat df = new DecimalFormat("#.##");

    public tong_ke duisai_wangji(String sid) {
        tong_ke tong_ke = new tong_ke();
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        LinkedHashMap<String, List<List<String>>> About_5_shows = new LinkedHashMap<>();

            /*boolean b = Greater_than_13(sid);
            if (b) {*/


        //System.out.println(sid);

        try {
            page.navigate("https://zq.titan007.com/analysis/" + sid + "cn.htm");
        } catch (Exception e) {
            browserContext.close();
            browserContext = (BrowserContext) context.getBean("BrowserContext");
            page = browserContext.newPage();
            page.navigate("https://zq.titan007.com/analysis/" + sid + "cn.htm");
        }


        try {
            page.locator("#vsNew > tbody > tr > td > table:nth-child(2) > tbody > tr.blue_t2 > td > a > font").waitFor();

            String liansai = Jsoup.parse(page.content()).select("body > div.header > div.analyhead.new > div.vs >" +
                    " " +
                    "div:nth-child(1) > .LName").text();

            //System.out.println(liansai);
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
            //System.out.println("全");
            String hometj = "null";
            if (h_index > 2) {
                page.locator("#hn_s").selectOption(h_index.toString()).size();
                hometj = page.evaluate("document.querySelector(\"#table_hn > tbody > tr:last-child >" +
                        " td\").textContent").toString();

                //System.out.println("主:" + hometj);
            }
            Double G = tools.Re(tools.regexStr(hometj, "胜率:([0-9*.])*").replaceAll("胜率:", ""));
            Double H = tools.Re(tools.regexStr(hometj, "赢率:([0-9*.])*").replaceAll("赢率:", ""));
            if (G != null && H != null) {
                map.put("主队不分主客全赛事胜率", G + "%");
                map.put("主队不分主客全赛事赢率", H + "%");
            } else {
                G=0.0;
                H=0.0;
                map.put("主队不分主客全赛事胜率", "");
                map.put("主队不分主客全赛事赢率", "");
            }

            String ktj = "null";
            if (a_index > 2) {
                page.locator("#an_s").selectOption(a_index.toString()).size();
                ktj = page.evaluate("document.querySelector(\"#table_an > tbody > tr:last-child > td\")" +
                        ".textContent").toString();

                //System.out.println("客:" + ktj);

            }
            Double I = tools.Re(tools.regexStr(ktj, "胜率:([0-9*.])*").replaceAll("胜率:", ""));
            Double J = tools.Re(tools.regexStr(ktj, "赢率:([0-9*.])*").replaceAll("赢率:", ""));
            if (I != null && J != null) {
                map.put("客队不分主客全赛事胜率", I + "%");
                map.put("客队不分主客全赛事赢率", J + "%");
            } else {
                map.put("客队不分主客全赛事胜率", "");
                map.put("客队不分主客全赛事赢率", "");
                I = 0.0;
                J = 0.0;
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


            //System.out.println("主客全");
            String hometj2 = "null";
            if (h_index >= 2) {
                page.locator("#hn_s").selectOption(h_index.toString());
                hometj2 = page.evaluate("document.querySelector(\"#table_hn > tbody > tr:last-child >" +
                        " td\").textContent").toString();

                //System.out.println("主:" + hometj2);
            }
            Double K = tools.Re(tools.regexStr(hometj2, "胜率:([0-9*.])*").replaceAll("胜率:", ""));
            Double L = tools.Re(tools.regexStr(hometj2, "赢率:([0-9*.])*").replaceAll("赢率:", ""));
            if (K != null && L != null) {
                map.put("主队同主全赛事胜率", K + "%");
                map.put("主队同主全赛事赢率", L + "%");
            } else {
                map.put("主队同主全赛事胜率", "");
                map.put("主队同主全赛事赢率", "");
                K = 0.0;
                L = 0.0;
            }
            String ktj2 = "null";
            if (a_index >= 2) {
                page.locator("#an_s").selectOption(a_index.toString());
                ktj2 = page.evaluate("document.querySelector(\"#table_an > tbody > tr:last-child > td\")" +
                        ".textContent").toString();

                //System.out.println("客:" + ktj2);
            }
            Double M = tools.Re(tools.regexStr(ktj2, "胜率:([0-9*.])*").replaceAll("胜率:", ""));
            Double N = tools.Re(tools.regexStr(ktj2, "赢率:([0-9*.])*").replaceAll("赢率:", ""));
            if (M != null && N != null) {
                map.put("客队同客全赛事胜率", M + "%");
                map.put("客队同客全赛事赢率", N + "%");
            } else {
                map.put("客队同客全赛事胜率", "");
                map.put("客队同客全赛事赢率", "");
                M = 0.0;
                N = 0.0;
            }
            //-------------------------------------------------------------------

            List<Double> z_listq = new LinkedList<>();
            List<Double> z_listb = new LinkedList<>();
            List<Double> k_listq = new LinkedList<>();
            List<Double> k_listb = new LinkedList<>();
            if (h_index >= 2) {
                // List<Double> list = new ArrayList<>();
                int surplus = 1;
                List<ElementHandle> elementHandles = page.querySelectorAll("#table_hn > tbody > tr");
                if (elementHandles.size() > 9) {
                    surplus = elementHandles.size() - 9 + 1;
                } else if (elementHandles.size() == 9) {
                    surplus += 1;
                }
                List<List<String>> lists = new ArrayList<>();
                for (int i = 2; i < elementHandles.size() - surplus; i++) {
                    List<ElementHandle> tds = elementHandles.get(i).querySelectorAll("td");
                    List<String> list1 = new ArrayList<>();
                    for (int j = 0; j < tds.size(); j++) {
                        String s = tds.get(j).textContent();
                        list1.add(s);
                        if (j == 3) {
                            z_listq.add(Double.parseDouble(String.valueOf(s.charAt(0))));
                            z_listb.add(Double.parseDouble(String.valueOf(s.charAt(4))));
                        }
                    }
                    lists.add(list1);
                           /* String s =
                                    elementHandles.get(i).querySelector("td:nth-child(4) > a").textContent().split
                                    ("-")[0];
                            list.add(Double.parseDouble(s));*/
                }
                About_5_shows.put("home_team", lists);


                        /*map.put("主队同客全赛事近5场平均值",
                                df.format(list.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN)
                                ));*/
            }
            if (a_index >= 2) {

                //   List<Double> list = new ArrayList<>();
                int surplus = 1;
                List<ElementHandle> elementHandles = page.querySelectorAll("#table_an > tbody > tr");
                if (elementHandles.size() > 9) {
                    surplus = elementHandles.size() - 9 + 1;
                } else if (elementHandles.size() == 9) {
                    surplus += 1;
                }
                List<List<String>> lists = new ArrayList<>();
                for (int i = 2; i < elementHandles.size() - surplus; i++) {
                    List<ElementHandle> tds = elementHandles.get(i).querySelectorAll("td");
                    List<String> list1 = new ArrayList<>();
                    for (int j = 0; j < tds.size(); j++) {
                        String s = tds.get(j).textContent();
                        list1.add(s);
                        if (j == 3) {
                            k_listq.add(Double.parseDouble(String.valueOf(s.charAt(2))));
                            k_listb.add(Double.parseDouble(String.valueOf(s.charAt(6))));
                        }
                    }
                    lists.add(list1);
                            /*String s =
                                    elementHandles.get(i).querySelector("td:nth-child(4) > a").textContent().split
                                    ("-")[1];
                            list.add(Double.valueOf(s));*/
                }
                About_5_shows.put("visiting_team", lists);
                      /*  map.put("客队同客全赛事近5场平均值",
                                df.format(list.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN)
                                ));*/
            }

           /* System.out.println(z_listq + "-" + z_listb);
            System.out.println(k_listq + "-" + k_listb);*/
            map.put("全场", "");
            map.put("半场", "");
            String zh = "主近3场：%q球； 客近3场：%w球； 主近5场：%a球； 客近5场：%b球； 近3场：%e球； 近5场：%c球；";
            String kh = "主近3半场：%g球； 客近3半场：%h球； 主近5半场：%z球； 客近5半场：%x球； 近3半场：%j球； 近5半场：%v球；";
            if (z_listq.size() == 5 && k_listq.size() == 5) {
                double z5ping = z_listq.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
                double k5ping = k_listq.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
                double z5pingb = z_listb.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
                double k5pingb = k_listb.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);

                zh = zh.replace("%a", df.format(z5ping));
                zh = zh.replace("%b", df.format(k5ping));
                zh = zh.replace("%c", df.format(z5ping + k5ping));
                kh = kh.replace("%z", df.format(z5pingb));
                kh = kh.replace("%x", df.format(k5pingb));
                kh = kh.replace("%v", df.format(z5pingb + k5pingb));
            } else {
                zh = zh.replace("主近5场：%a球； 客近5场：%b球；", "").replace("近5场：%c球；", "");
                kh = kh.replace("主近5半场：%z球； 客近5半场：%x球；", "").replace("近5半场：%v球；", "");
            }

            while (z_listq.size() > 3) {
                z_listq.remove(z_listq.size() - 1);
                z_listb.remove(z_listb.size() - 1);
            }

            while (k_listq.size() > 3) {
                k_listq.remove(k_listq.size() - 1);
                k_listb.remove(k_listb.size() - 1);
            }

            if ((z_listq.size() == 3 && k_listq.size() == 3)) {
                double z3ping = z_listq.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
                double k3ping = k_listq.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
                double z3pingb = z_listb.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
                double k3pingb = k_listb.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
                zh = zh.replace("%q", df.format(z3ping));
                zh = zh.replace("%w", df.format(k3ping));
                zh = zh.replace("%e", df.format(z3ping + k3ping));
                kh = kh.replace("%g", df.format(z3pingb));
                kh = kh.replace("%h", df.format(k3pingb));
                kh = kh.replace("%j", df.format(z3pingb + k3pingb));
            } else {
                zh = zh.replace("主近3场：%q球； 客近3场：%w球；", "").replace("近3场：%e球；", "");
                kh = kh.replace("主近3半场：%g球； 客近3半场：%h球；", "").replace("近3半场：%j球；", "");

            }
            map.put("全场", zh.trim());
            map.put("半场", kh.trim());

            tong_ke.setAbout_5_shows(About_5_shows);
//近五场
//
            //-------------------------------------------------------------------
            //包含主客自身
            //System.out.println("主客自身");

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

                //System.out.println("主:" + hometj3);
            }
            Double O = tools.Re(tools.regexStr(hometj3, "胜率:([0-9*.])*").replaceAll("胜率:", ""));
            Double P = tools.Re(tools.regexStr(hometj3, "赢率:([0-9*.])*").replaceAll("赢率:", ""));
            if (O != null && P != null) {
                map.put("主队同主本赛事胜率", O + "%");
                map.put("主队同主本赛事赢率", P + "%");
            } else {
                O = 0.0;
                P = 0.0;
                map.put("主队同主本赛事胜率", "");
                map.put("主队同主本赛事赢率", "");
            }

            //--------------------------------------------
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

                //System.out.println("客:" + ktj3);
            }
            Double Q = tools.Re(tools.regexStr(ktj3, "胜率:([0-9*.])*").replaceAll("胜率:", ""));
            Double R = tools.Re(tools.regexStr(ktj3, "赢率:([0-9*.])*").replaceAll("赢率:", ""));
            if (Q != null && R != null) {
                map.put("客队同客本赛事胜率", Q + "%");
                map.put("客队同客本赛事赢率", R + "%");
            } else {
                Q = 0.0;
                R = 0.0;
                map.put("客队同客本赛事胜率", "");
                map.put("客队同客本赛事赢率", "");
            }
            map.put("标注1", "");
            map.put("标注2", "");
            map.put("标注3", "");
            map.put("标注4", "");
            if (G != null && H != null && I != null && J != null) {

                if (G >= 60 && H >= 50 && I <= 40 && J <= 35) {
                    map.put("标注1", "主队热");
                } else if (I >= 60 && J >= 50 && G <= 40 && H <= 35) {
                    map.put("标注1", "客队热");
                }
            }

            if (K != null && L != null && M != null && N != null) {
                if (K >= 60 && L >= 50 && M <= 40 && N <= 35) {
                    map.put("标注2", "主队热");
                    if (O >= 50 && P >= 50 && Q < 50 && R < 50) {
                        map.put("标注4", "主双");
                    }
                } else if (M >= 60 && N >= 50 && K <= 40 && L <= 35) {
                    map.put("标注2", "客队热");
                    if (O < 50 && P < 50 && Q >= 50 && R >= 50) {
                        map.put("标注4", "客双");
                    }
                }
            }

            if (O != null && P != null && Q != null && R != null) {
                if (O >= 60 && P >= 50 && Q <= 40 && R <= 35) {
                    // lists.get(i).add("主队热");
                    map.put("标注3", "主队热");
                    if (K >= 50 && L >= 50 && M < 50 && N < 50) {
                        map.put("标注4", "主双");
                    }
                } else if (Q >= 60 && R >= 50 && O <= 40 && P <= 35) {
                    // lists.get(i).add("客队热");
                    map.put("标注3", "客队热");
                    if (K < 50 && L < 50 && M >= 50 && N >= 50) {
                        map.put("标注4", "客双");
                    }
                }
            }
            if (K != null && L != null && M != null && N != null && O != null && P != null && Q != null && R != null) {
                if (K > 60 && L < 40 && M < 40 && N < 40 && O > 60 && P < 40 && Q < 40 && R < 40) {
                    map.put("标注4", "主1内");
                } else if (K < 40 && L < 40 && M > 60 && N < 40 && O < 40 && P < 40 && Q > 60 && R < 40) {
                    map.put("标注4", "客1内");
                }
            }

            tong_ke.setMaps(map);
        } catch (NumberFormatException e) {
            browserContext.close();
            browserContext = (BrowserContext) context.getBean("BrowserContext");
            page = browserContext.newPage();
        }

        return tong_ke;
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
