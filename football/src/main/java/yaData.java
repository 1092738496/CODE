import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Route;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pojo.yaTre;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2024/5/12 15:56
 * @description:
 */

public class yaData {
    httpUtils httpUtils = new httpUtils();

    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"));
        httpUtils.setHeaders(headers);

    }

    public void export() {
        System.out.println("请输入时间间隔,例如(5月08日08：00--5月08日15：00)");
        ArrayList<HashMap<String, String>> datesplit = datesplit();
        String filedate = "";
        if (datesplit.size() == 2) {
            HashMap<String, String> map1 = datesplit.get(0);
            HashMap<String, String> map2 = datesplit.get(1);
            filedate =
                    map1.get("date") + "月" + map1.get("day") + "日" + map1.get("miao") + "-" + map2.get("date") + "月" + map2.get("day") + "日" + map2.get("miao");
        } else {
            HashMap<String, String> map1 = datesplit.get(0);
            filedate =
                    map1.get("date") + "月" + map1.get("day") + "日" + map1.get("miao");
        }
        System.out.println(filedate);
        ArrayList<ArrayList<String>> lists = new ArrayList<>();
        ArrayList<LinkedHashMap<String, String>> listmap = start1(datesplit);
        Workbook writer = new XSSFWorkbook();
        Sheet sheet = writer.createSheet("Sheet1");
        byte[] rgb3 = {(byte) 173, (byte) 216, (byte) 230};
        CellStyle style3 = writer.createCellStyle();
        XSSFColor customColor3 = new XSSFColor(rgb3);
        style3.setFillForegroundColor(customColor3);
        style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        int z = 1;
        int Tresize = 0;
        for (LinkedHashMap<String, String> map : listmap) {
            Row row = sheet.createRow(z);
            String id = map.get("id");
            String title = map.get("title");
            yaTre yaTre = getdata(id);
            // System.out.println(map);
            /*ArrayList<String> list = new ArrayList<>();
            list.add(id);
            list.add(title);
            list.add(yaTre.getHome());
            list.add(yaTre.getTime());
            list.add(yaTre.getScore());
            list.add(yaTre.getGuest());*/
            if (null != yaTre) {
                row.createCell(0).setCellValue(id);
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(title);
                row.createCell(2).setCellValue(yaTre.getTime());
                row.createCell(3).setCellValue(yaTre.getHome());
                row.createCell(4).setCellValue(yaTre.getScore());
                row.createCell(5).setCellValue(yaTre.getGuest());

                ArrayList<ArrayList<String>> yaTreList = yaTre.getLists();
                int x = 5;
                for (int i = 0; i < yaTreList.size(); i++) {
                    if (null != yaTreList.get(i)) {
                        //   list.addAll(yaTreList.get(i));
                        int size = yaTreList.size();
                        if (size > Tresize) {
                            Tresize = size;
                        }
                        for (int m = 0; m < yaTreList.get(i).size(); m++) {
                            x++;
                            //5月11日10：30-5月11日10：30
                            Cell cell = row.createCell(x);
                            Integer integer;

                            try {
                                integer = Integer.valueOf(yaTreList.get(i).get(m));
                                if (integer >= 100) {
                                    cell.setCellStyle(style3);
                                    cell1.setCellStyle(style3);
                                }
                            } catch (NumberFormatException e) {

                            }
                            cell.setCellValue(yaTreList.get(i).get(m));
                        }
                    }
                }
                //System.out.println(list);
                // lists.add(list);
                z++;

            }
            Row row1 = sheet.createRow(0);
            row1.createCell(0).setCellValue("id");
            row1.createCell(1).setCellValue("赛事名称");
            row1.createCell(2).setCellValue("赛事时间");
            row1.createCell(3).setCellValue("主队");
            row1.createCell(4).setCellValue("比分");
            row1.createCell(5).setCellValue("客队");
            int p = 0;
            for (int i = 5; i < Tresize * 3 + 5; i += 3) {
                p++;
                row1.createCell(i + 1).setCellValue("公司" + p);
                row1.createCell(i + 2).setCellValue("主/客");
                row1.createCell(i + 3).setCellValue("客/主");
            }
        }
        try (FileOutputStream outputStream = new FileOutputStream("E:\\" + (filedate.replaceAll(":", "：")) +
                ".xlsx")) {
            writer.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public yaTre getdata(String id) {
        System.out.println("---------------------------------------------------------------");
        yaTre yaTre = new yaTre();

        String html = "";
        try {
            String url = "https://vip.titan007.com/AsianOdds_n.aspx?id=" + id;
            html = httpUtils.get(url, "utf-8");
            if (html.equals("")) {
                return null;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        String home = Jsoup.parse(html).select("body > div.header > div.analyhead > div.home > a").text();
        String guest = Jsoup.parse(html).select("body > div.header > div.analyhead > div.guest > a").text();
        String time =
                Jsoup.parse(html).select("body > div.header > div.analyhead > div.vs > div:nth-child(1) > span.time").text();
        System.out.println(id + " | " + time + " | " + home + " | " + guest);
        String homef = Jsoup.parse(html).select("#headVs > div > div:nth-child(1)").text();
        String guestf = Jsoup.parse(html).select("#headVs > div > div.score.gt").text();
        yaTre.setHome(home);
        yaTre.setGuest(guest);
        yaTre.setTime(time);
        if (!homef.equals("")) {
            yaTre.setScore(homef + "-" + guestf);
        }

        Elements select = Jsoup.parse(html).select("#odds > tbody > tr");
        ArrayList<ArrayList<String>> lists = new ArrayList<>();
        if (!select.last().text().equals("暂无公司开盘")) {
            for (int i = 2; i < select.size(); i++) {
                String style = select.get(i).attr("style");
                String idx = select.get(i).attr("class");
                if (!style.equals("display: none;") & !idx.equals("yellow_bg")) {
                    ArrayList<String> list = new ArrayList<>();
                    Elements select1 = select.get(i).select("td:nth-child(1)");
                    select1.select("span").remove();
                    //System.out.println(select1.text());
                    list.add(select1.text());
                    String href =
                            "https://vip.titan007.com" + select.get(i).select("td:nth-child(12) > a:nth-child(1)").attr(
                                    "href");
                    try {
                        String html2 = httpUtils.get(href, "utf-8");
                        Elements select2 = Jsoup.parse(html2).select("#odds2 > table > tbody > tr");
                        /*if (!select2.isEmpty()) {*/
                        int number1 = 0;
                        int number = 0;
                        for (Element element : select2) {
                            String z = element.select("td").last().text();

                            if (z.equals("早") || z.equals("即")) {
                                int tdsize = element.select("td").size();
                                String zhu = "";
                                String ke = "";
                                if (tdsize == 7) {
                                    zhu = element.select("td:nth-child(3) > font > b").text();
                                    ke = element.select("td:nth-child(5) > font > b").text();
                                } else {
                                    zhu = element.select("td:nth-child(1) > font > b").text();
                                    ke = element.select("td:nth-child(3) > font > b").text();
                                }
                                double s = Double.parseDouble(zhu) / Double.parseDouble(ke);
                                DecimalFormat df = new DecimalFormat("#.##");
                                double v = Double.parseDouble(df.format(s));
                                if (v >= 1) {
                                    number1++;
                                } else {
                                    number++;
                                }

                            }
                        }
                        if (number1 == 0 & number == 0) {

                        } else {
                            double n = number1 + number;
                            double m = number1 / n;
                            DecimalFormat df = new DecimalFormat("#.##");
                            double zhuv = Double.parseDouble(df.format(m));
                            double kev = Double.parseDouble(df.format(1 - zhuv));

                            String zhub = String.valueOf(Math.round(zhuv * 100));
                            String keb = String.valueOf(Math.round(kev * 100));

                            list.add(zhub);
                            list.add(keb);
                            lists.add(list);
                        }
                        //  }
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        yaTre.setLists(lists);
        return yaTre;
    }

    public ArrayList<LinkedHashMap<String, String>> start1(ArrayList<HashMap<String, String>> datesplit) {
        System.out.println("获取即使数据,请稍等...");
        ArrayList<LinkedHashMap<String, String>> list = this.aa(datesplit);
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("获取历史数据,请稍等...");
        //ArrayList<LinkedHashMap<String, String>> list = new ArrayList<>(aa);

        if (datesplit.size() == 2) {
            HashMap<String, String> datamap1 = datesplit.get(0);
            HashMap<String, String> datamap2 = datesplit.get(1);
            int jiange = Integer.parseInt(datamap2.get("day")) - Integer.parseInt(datamap1.get("day"));
            //  System.out.println(jiange);
            //5月08日08：00--5月08日15：00
            if (jiange == 0 & datamap1.get("miao") != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime miao = LocalTime.parse(datamap2.get("miao"), formatter);
                ArrayList<LinkedHashMap<String, String>> listmap = getfootball(datamap1);
                for (LinkedHashMap<String, String> map : listmap) {
                    LocalTime date = LocalTime.parse(map.get("date"), formatter);
                    if (miao.isAfter(date) || miao.equals(date)) {
                        list.add(map);
                    }
                }
                //5月08日--5月08日
            } else if (jiange == 0 & datamap1.get("miao") == null) {
                list.addAll(getfootball(datamap1));
                //5月08日08：00--5月9日15：00
            } else if (jiange != 0 & datamap1.get("miao") != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                //当前
                ArrayList<LinkedHashMap<String, String>> thismap = getfootball(datamap1);
                list.addAll(thismap);
                //中间
                for (int i = Integer.parseInt(datamap1.get("day")) + 1; i <= Integer.parseInt(datamap2.get("day")) - 1; i++) {
                    String miao = null;
                    if (i < 10) {
                        miao = "0" + i;
                    } else {
                        miao = String.valueOf(i);
                    }
                    datamap1.put("day", miao);
                    list.addAll(getfootball(datamap1));
                }

                //最后
                String miao1 = datamap2.get("miao");
                LocalTime miao = LocalTime.parse(miao1, formatter);
                datamap2.put("miao", "01:00");
                ArrayList<LinkedHashMap<String, String>> endmap = getfootball(datamap2);
                for (LinkedHashMap<String, String> map : endmap) {
                    LocalTime date = LocalTime.parse(map.get("date"), formatter);
                    if (miao.isAfter(date) || miao.equals(date)) {
                        list.add(map);
                    }
                }


                //5月09日--5月15日
            } else if (jiange != 0 & datamap1.get("miao") == null) {
                //System.out.println(datamap1);
                // System.out.println(datamap2);
                for (int i = Integer.parseInt(datamap1.get("day")); i <= Integer.parseInt(datamap2.get("day")); i++) {
                    String miao = null;
                    if (i < 10) {
                        miao = "0" + i;
                    } else {
                        miao = String.valueOf(i);
                    }
                    datamap1.put("day", miao);
                    list.addAll(getfootball(datamap1));
                }
            }
        } else {
            HashMap<String, String> datamap = datesplit.get(0);
            list.addAll(getfootball(datamap));
        }
        return list;
    }


    public ArrayList<LinkedHashMap<String, String>> aa(ArrayList<HashMap<String, String>> datesplit) {

        ArrayList<LinkedHashMap<String, String>> list = new ArrayList<>();
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            // 设置拦截规则
            BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions()
                    .setViewportSize(640, 480)
                    .setScreenSize(640, 480)
                    .setIgnoreHTTPSErrors(true));
            // 禁止图片加载
            browserContext.route("**/*.{png,jpg,jpeg,webp,avif,svg}", Route::abort);
            Page page = browserContext.newPage();


            // 跳转到页面
            page.navigate("https://live.titan007.com/oldIndexall.aspx");
            page.waitForLoadState();
            page.locator("#table_live > tbody > tr[1]");
            page.locator("#button6").click();
            TimeUnit.SECONDS.sleep(1);
            //int i = Integer.valueOf(datesplit.get(0).get("day")) - Integer.valueOf(datesplit.get(1).get("day"));
            DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");
            Elements trs = Jsoup.parse(page.content()).select("#table_live > tbody > tr");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            DateTimeFormatter thisformatter = DateTimeFormatter.ofPattern("yyyyMM-dd HH:mm");
            LocalDateTime endTime = null;
            LocalDateTime startTime = null;

            LocalTime endTime1 = null;
            LocalTime startTime1 = null;
            if (datesplit.size() == 2) {
                endTime1 = LocalTime.parse(datesplit.get(0).get("miao"),
                        timeformatter);
                startTime1 = LocalTime.parse(datesplit.get(1).get("miao"),
                        timeformatter);
                endTime =
                        LocalDateTime.parse(datesplit.get(0).get("date") + "-" + datesplit.get(0).get("day") + " " + datesplit.get(0).get("miao"),
                                thisformatter);
                startTime =
                        LocalDateTime.parse(datesplit.get(1).get("date") + "-" + datesplit.get(1).get("day") + " " + datesplit.get(1).get("miao"),
                                thisformatter);
            } else {
                endTime1 = LocalTime.parse(datesplit.get(0).get("miao"),
                        timeformatter);
                startTime1 = LocalTime.parse(datesplit.get(0).get("miao"),
                        timeformatter);
                endTime =
                        LocalDateTime.parse(datesplit.get(0).get("date") + "-" + datesplit.get(0).get("day") + " " + datesplit.get(0).get("miao"),
                                thisformatter);
                startTime =
                        LocalDateTime.parse(datesplit.get(0).get("date") + "-" + datesplit.get(0).get("day") + " " + datesplit.get(0).get("miao"),
                                thisformatter);
            }

            for (Element tr : trs) {
                String align = tr.attr("align");
                if (!align.equals("")) {
                    String text = tr.select("td:nth-child(2) > span").text();
                    if (!text.equals("")) {
                        String id = tr.attr("id").split("_")[1];
                        String timed = tr.select("td:nth-child(3)").text();
                        LocalTime dateTime1 = null;
                        boolean isInBetween1 = false;

                        try {
                            dateTime1 = LocalTime.parse(timed, timeformatter);
                            isInBetween1 =
                                    dateTime1.isBefore(startTime1) | dateTime1.equals(startTime1) && dateTime1.isAfter(endTime1) | dateTime1.equals(endTime1);
                        } catch (Exception e) {
                            isInBetween1 = true;
                        }

                        /*if (isInBetween1) {*/
                        String s = httpUtils.get("https://vip.titan007.com/AsianOdds_n.aspx?id=" + id, "utf-8");
                        String time = Jsoup.parse(s).select("body > div.header > div.analyhead > div.vs > " +
                                "div:nth-child(1) > span.time").text();

                        String trim = tools.regexStr1(time, "[0-9- :]").trim();


                        LocalDateTime dateTime = LocalDateTime.parse(trim, formatter);


                        boolean isInBetween =
                                dateTime.isBefore(startTime) | dateTime.isEqual(startTime) && dateTime.isAfter(endTime) | dateTime.isEqual(endTime);

                        if (isInBetween) {
                            LinkedHashMap<String, String> map = new LinkedHashMap<>();
                            System.out.println(text);
                            System.out.println(id);
                            System.out.println(trim);

                            map.put("data", (dateTime.getMinute() + 1) + ":" + (dateTime.getSecond() + 1));
                            map.put("id", id);
                            map.put("title", text);
                            list.add(map);
                        } else {
                            System.out.println(text);
                        }
                    }
                }
                // }
            }
        } catch (InterruptedException | ParseException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<LinkedHashMap<String, String>> getfootball(HashMap<String, String> datamap) {
        ArrayList<LinkedHashMap<String, String>> listmap = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = null;
        if (datamap.get("miao") != null) {
            startTime = LocalTime.parse(datamap.get("miao"), formatter);
        }

        String html = "";
        String biaoshi = "";
        try {
            html = httpUtils.get("https://bf.titan007.com/football/Over_" + datamap.get("date") + datamap.get("day") +
                    ".htm", "gbk");
            biaoshi = "Over";

            if (html.equals("")) {
                html = httpUtils.get("https://bf.titan007.com/football/Next_" + datamap.get("date") + datamap.get(
                        "day") + ".htm", "gbk");
                biaoshi = "Next";
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        Elements select = Jsoup.parse(html).select("#table_live");
        Elements trs = select.select("tbody > tr");
        for (int i = 1; i < trs.size(); i++) {
            Elements td2 = trs.get(i).select("td:nth-child(2)");
            String td2text = td2.text();
            if (!td2text.equals("")) {
                //5-12 10:30
                //11日13:00
                String[] dates = null;
                if (biaoshi.equals("Over")) {
                    dates = td2.text().split("日");
                } else if (biaoshi.equals("Next")) {
                    String[] split = td2.text().split("-");
                    dates = split[1].split(" ");
                }
                if (Integer.valueOf(dates[0]).equals(Integer.valueOf(datamap.get("day")))) {
                    if (startTime == null) {
                        String title = trs.get(i).select("td:nth-child(1)").text();
                        /*String id = tools.regexStr1(trs.get(i).select("td:nth-child(10) > a:nth-child(2)").attr(
                                "onclick"), "\\d");*/
                        String id = trs.get(i).attr("sid");
                        LinkedHashMap<String, String> map = new LinkedHashMap<>();
                        map.put("date", dates[1]);
                        map.put("id", id);
                        map.put("title", title);
                        listmap.add(map);
                        System.out.println(map);
                        //   System.out.println(dates[1]+":"+title+":"+id);
                    } else {
                        LocalTime endTime = LocalTime.parse(dates[1], formatter);
                        if (startTime.isBefore(endTime) || startTime.equals(endTime)) {
                            String title = trs.get(i).select("td:nth-child(1)").text();
                            /*String id = tools.regexStr1(trs.get(i).select("td:nth-child(10) > a:nth-child(2)").attr(
                                    "onclick"), "\\d");*/
                            String id = trs.get(i).attr("sid");
                            LinkedHashMap<String, String> map = new LinkedHashMap<>();
                            map.put("date", dates[1]);
                            map.put("id", id);
                            map.put("title", title);
                            listmap.add(map);
                            System.out.println(map);
                        }
                    }
                }
            }
        }
        return listmap;
    }

    public ArrayList<HashMap<String, String>> datesplit() {
        ArrayList<HashMap<String, String>> mapArrays = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        Scanner scanner = new Scanner(System.in);
        String ru = scanner.nextLine();
        String s = ru.replaceAll("：", ":").replaceAll("--", "-").replaceAll(" ", "");

        if (!s.contains("-") && !s.contains(":")) {
            HashMap<String, String> map = new HashMap<>();
            String s1 = s.replaceAll("日", "");
            String[] date = s1.split("月");
            for (int i = 0; i < date.length; i++) {
                if (Integer.parseInt(date[i]) < 10) {
                    date[i] = "0" + date[i].replaceAll("0", "");
                }
            }
            //System.out.println(currentYear+date[0]+date[1]);
            map.put("date", currentYear + date[0]);
            map.put("day", date[1]);
            mapArrays.add(map);
        }

        if (s.contains(":") && !s.contains("-")) {
            HashMap<String, String> map = new HashMap<>();
            String s1 = s.replaceAll("日", "-").replaceAll("月", "-");
            String[] date = s1.split("-");

            for (int i = 0; i <= 1; i++) {
                if (Integer.parseInt(date[i]) < 10) {
                    date[i] = "0" + date[i].replaceAll("0", "");
                }
            }
            // System.out.println(currentYear+date[0]+date[1]);
            map.put("date", currentYear + date[0]);
            map.put("day", date[1]);

            String[] split = date[2].split(":");

            for (int i = 0; i < split.length; i++) {
                if (Integer.valueOf(split[i]) < 10 & Integer.valueOf(split[i]) != 0) {
                    split[i] = "0" + split[i].replaceAll("0", "");
                }
            }
            map.put("miao", split[0] + ":" + split[1]);

            mapArrays.add(map);

        }

        if (s.contains("-") && s.contains(":")) {
            String[] split = s.split("-");
            String s1 = split[0].replaceAll("日", "-").replaceAll("月", "-");
            String s2 = split[1].replaceAll("日", "-").replaceAll("月", "-");
            String[] date1 = s1.split("-");

            for (int i = 0; i <= 1; i++) {
                if (Integer.parseInt(date1[i]) < 10) {
                    date1[i] = "0" + date1[i].replaceAll("0", "");
                }
            }
            String[] date2 = s2.split("-");
            for (int i = 0; i <= 1; i++) {
                if (Integer.parseInt(date2[i]) < 10) {
                    date2[i] = "0" + date2[i].replaceAll("0", "");
                }
            }
            HashMap<String, String> map1 = new HashMap<>();
            HashMap<String, String> map2 = new HashMap<>();
            //System.out.println(currentYear+date1[0]+date1[1]);
            map1.put("date", currentYear + date1[0]);
            map1.put("day", date1[1]);

            String[] split1 = date1[2].split(":");

            for (int i = 0; i < split.length; i++) {
                if (Integer.valueOf(split1[i]) < 10 & Integer.valueOf(split1[i]) != 0) {
                    split1[i] = "0" + split1[i].replaceAll("0", "");
                }
            }
            map1.put("miao", split1[0] + ":" + split1[1]);

            //map1.put("miao", date1[2]);
            //System.out.println(currentYear+date2[0]+date2[1]);
            map2.put("date", currentYear + date2[0]);
            map2.put("day", date2[1]);
            String[] split2 = date2[2].split(":");

            for (int i = 0; i < split.length; i++) {
                if (Integer.valueOf(split2[i]) < 10 & Integer.valueOf(split2[i]) != 0) {
                    split2[i] = "0" + split2[i].replaceAll("0", "");
                }
            }
            map2.put("miao", split2[0] + ":" + split2[1]);
            mapArrays.add(map1);
            mapArrays.add(map2);
        }
        //5月8日--5月48日
        if (s.contains("-") && !s.contains(":")) {
            String[] split = s.replaceAll("日", "").replaceAll("日", "").split("-");
            String[] s1 = split[0].split("月");
            for (int i = 0; i < s1.length; i++) {
                if (Integer.valueOf(s1[i]) < 10) {
                    s1[i] = "0" + s1[i].replaceAll("0", "");
                }
            }
            String[] s2 = split[1].split("月");
            for (int i = 0; i < s2.length; i++) {
                if (Integer.valueOf(s2[i]) < 10) {
                    s2[i] = "0" + s2[i].replaceAll("0", "");
                }
            }
            HashMap<String, String> map1 = new HashMap<>();
            map1.put("date", currentYear + s1[0]);
            map1.put("day", s1[1]);
            HashMap<String, String> map2 = new HashMap<>();
            map2.put("date", currentYear + s2[0]);
            map2.put("day", s2[1]);

            mapArrays.add(map1);
            mapArrays.add(map2);
        }
        return mapArrays;
    }
}
