import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Route;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.poi.ss.usermodel.BorderStyle;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2024/3/12 15:43
 * @description:
 */

public class Getdata {
    static httpUtils httpUtils = new httpUtils();
    private static String sj;
    public void tabular_data(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要爬取的日期,例如:20240305(,Y打开浏览器):");
        sj = scanner.nextLine();
        boolean y = true;
        if (sj.contains(",")) {
            String[] split = sj.split(",");
            sj = split[0];
            if (split[1].equals("y") || split[1].equals("Y")) {
                y = false;
            }
        }
        String property = System.getProperty("user.dir") + "//" + sj + ".xlsx";
        List<List<Object>> lists = new ArrayList<>();
        if (new File(property).exists()) {
            System.out.println("加载到文件" + sj + ".xlsx,需要一定时间读取,请耐心等待");
            try {
                FileInputStream fis = new FileInputStream(new File(property));
                Workbook workbook = new XSSFWorkbook(fis);
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue;
                    List<Object> list = new ArrayList<>();
                    for (int i = 0; i < 18; i++) {
                        list.add(row.getCell(i).toString());
                    }
                    lists.add(list);
                }
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int listsSize = lists.size();
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(y).
                setArgs(Arrays.asList("--enable-gpu", "--disable-software-compositing"))
        );
        BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(640, 480)
                .setScreenSize(640, 480)
                .setIgnoreHTTPSErrors(true));
        // 禁止图片加载
        browserContext.route("**/*.{png,jpg,jpeg,webp,avif,svg}", Route::abort);
        Page page = browserContext.newPage();

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"));
        httpUtils.setHeaders(headers);
        String html = null;
        try {
            html = httpUtils.get("https://bf.titan007.com/football/Over_" + sj + ".htm", "gbk");
            if (html.equals("")) {
                html = httpUtils.get("https://bf.titan007.com/football/Next_" + sj + ".htm", "gbk");
            }
        } catch (Exception e) {
            System.out.println("出现异常,请检查网络");
            e.printStackTrace();
        }
        Elements select = Jsoup.parse(html).select("#table_live > tbody > tr");
        boolean exception = false;
        int n = 0;
        for (int i = 1; i < select.size(); i++) {
            try {
                Element tr = select.get(i);
                String sid = tr.attr("sid");
                if (lists.size() != n && sid.equals(lists.get(n).get(0))) {
                    n++;
                    continue;
                }
                boolean b = Greater_than_13(sid);
                if (b) {
                    System.out.println(n + 1);
                    n++;

                    String liansai = tr.select("td:nth-child(1)").text();
                    String home = tr.select("td:nth-child(4)").text();
                    String a = tr.select("td:nth-child(6)").text();
                    String bifen = tr.select("td:nth-child(5)").text();
                    List<Object> list = new ArrayList<Object>();
                    list.add(sid);
                    list.add(liansai);
                    list.add(home);
                    list.add(a);


                    System.out.println(sid);
                    System.out.println(liansai);
                    /*System.out.println(time);
                    System.out.println(home);
                    System.out.println(k);*/
                    page.navigate("https://zq.titan007.com/analysis/" + sid + "cn.htm");
                    //page.waitForLoadState();
                    page.locator("#vsNew > tbody > tr > td > table:nth-child(2) > tbody > tr.blue_t2 > td > a > font").waitFor();
                    String time =
                            page.locator("body > div.header > div.analyhead.new > div.vs > div:nth-child(1)").textContent();
                    time = tools.regexStr(time, "\\d{4}(-).*");
                    list.add(time);
                    list.add(bifen);
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
                    //------------------------------------liansai-------------------------------
                    //全


                    System.out.println("全");
                    String hometj = "null";
                    if (h_index > 2) {
                        page.locator("#hn_s").selectOption(h_index.toString()).size();
                        hometj = page.evaluate("document.querySelector(\"#table_hn > tbody > tr:last-child >" +
                                " td\").textContent").toString();

                        System.out.println("主:" + hometj);
                    }
                    list.add(hometj);
                    String ktj = "null";
                    if (a_index > 2) {
                        page.locator("#an_s").selectOption(a_index.toString()).size();
                        ktj = page.evaluate("document.querySelector(\"#table_an > tbody > tr:last-child > td\")" +
                                ".textContent").toString();

                        System.out.println("客:" + ktj);
                    }
                    list.add(ktj);
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
                    list.add(hometj2);
                    String ktj2 = "null";
                    if (a_index >= 2) {
                        page.locator("#an_s").selectOption(a_index.toString());
                        ktj2 = page.evaluate("document.querySelector(\"#table_an > tbody > tr:last-child > td\")" +
                                ".textContent").toString();

                        System.out.println("客:" + ktj2);
                    }
                    list.add(ktj2);

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
                    list.add(hometj3);

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
                    list.add(ktj3);
                    lists.add(list);
                    System.out.println("-----------------------------------");

                }
            } catch (Exception e) {
                playwright.close();
                exception = true;

            }
        }
        playwright.close();


        for (int i = listsSize; i < lists.size(); i++) {
            List<Object> ss = tools.ss(lists.get(i));
            lists.set(i, ss);
        }
        ArrayList<Object> title = new ArrayList<Object>();
        title.add("sid");
        title.add("联赛");
        title.add("主队");
        title.add("客队");
        title.add("比赛时间");
        title.add("比分");
        title.add("主队不分主客全赛事胜率");
        title.add("主队不分主客全赛事赢率");
        title.add("客队不分主客全赛事胜率");
        title.add("客队不分主客全赛事赢率");

        title.add("主队同主全赛事胜率");
        title.add("主队同主全赛事赢率");
        title.add("客队同客全赛事胜率");
        title.add("客队同客全赛事赢率");

        title.add("主队同主本赛事胜率");
        title.add("主队同主本赛事赢率");
        title.add("客队同客本赛事胜率");
        title.add("客队同客本赛事赢率");
        title.add("标注1");
        title.add("标注2");
        title.add("标注3");
        lists.add(0, title);
        filtration(lists);

        if (exception) {
            System.out.println("出现异常" + sj + ".xlsx文件,已保存!!!!!!!!!!!,,按任意键退出");
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        } else {
            System.out.println("表格已导出到:"+property+",请查看,输入任意键退出");
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }
    private  List<List<Object>> filtration(List<List<Object>> lists) {
        String property = System.getProperty("user.dir")+ "\\" + sj + ".xlsx";
        Workbook writer = new XSSFWorkbook();
        Sheet sheet = writer.createSheet("Sheet1");
        CellStyle borderStyle = writer.createCellStyle();
        borderStyle.setBorderTop(BorderStyle.THIN);
        borderStyle.setBorderBottom(BorderStyle.THIN);
        borderStyle.setBorderLeft(BorderStyle.THIN);
        borderStyle.setBorderRight(BorderStyle.THIN);
        // 设置标题行（如果存在）
        Row titleRow = sheet.createRow(0);

        for (int i = 0; i < lists.get(0).size(); i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(lists.get(0).get(i).toString());
            cell.setCellStyle(borderStyle);
        }


       /* StyleSet cellStyle1 = writer.getStyleSet();
        cellStyle1.setBackgroundColor(IndexedColors.YELLOW, false);
        CellStyle style1 = cellStyle1.getCellStyle();

        StyleSet cellStyle2 = writer.getStyleSet();
        cellStyle2.setBackgroundColor(IndexedColors.BLUE, false);
        CellStyle style2 = cellStyle2.getCellStyle();

        StyleSet cellStyle3 = writer.getStyleSet();
        cellStyle3.setBackgroundColor(IndexedColors.GREEN, false);
        CellStyle style3 = cellStyle3.getCellStyle();*/

        byte[] rgb1 = {(byte) 221, (byte) 178, (byte) 43};
        CellStyle style1 = writer.createCellStyle();
        XSSFColor customColor1 = new XSSFColor(rgb1);
        style1.setFillForegroundColor(customColor1);
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        byte[] rgb2 = {(byte) 78, (byte) 134, (byte) 180};
        CellStyle style2 = writer.createCellStyle();
        XSSFColor customColor2 = new XSSFColor(rgb2);
        style2.setFillForegroundColor(customColor2);
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        byte[] rgb3 = {(byte) 78, (byte) 180, (byte) 83};
        CellStyle style3 = writer.createCellStyle();
        XSSFColor customColor3 = new XSSFColor(rgb3);
        style3.setFillForegroundColor(customColor3);
        style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (int i = 1; i < lists.size(); i++) {
            Row row = sheet.createRow(i);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(lists.get(i).get(0).toString());
            Cell cell1 = row.createCell(1);
            cell1.setCellValue(lists.get(i).get(1).toString());
            Cell cell2 = row.createCell(2);
            cell2.setCellValue(lists.get(i).get(2).toString());
            Cell cell3 = row.createCell(3);
            cell3.setCellValue(lists.get(i).get(3).toString());
            Cell cell4 = row.createCell(4);
            cell4.setCellValue(lists.get(i).get(4).toString());
            Cell cell5 = row.createCell(5);
            cell5.setCellValue(lists.get(i).get(5).toString());
            Cell cell6 = row.createCell(6);
            cell6.setCellValue(lists.get(i).get(6).toString());
            Cell cell7 = row.createCell(7);
            cell7.setCellValue(lists.get(i).get(7).toString());
            Cell cell8 = row.createCell(8);
            cell8.setCellValue(lists.get(i).get(8).toString());
            Cell cell9 = row.createCell(9);
            cell9.setCellValue(lists.get(i).get(9).toString());
            Cell cell10 = row.createCell(10);
            cell10.setCellValue(lists.get(i).get(10).toString());
            Cell cell11 = row.createCell(11);
            cell11.setCellValue(lists.get(i).get(11).toString());
            Cell cell12 = row.createCell(12);
            cell12.setCellValue(lists.get(i).get(12).toString());
            Cell cell13 = row.createCell(13);
            cell13.setCellValue(lists.get(i).get(13).toString());
            Cell cell14 = row.createCell(14);
            cell14.setCellValue(lists.get(i).get(14).toString());
            Cell cell15 = row.createCell(15);
            cell15.setCellValue(lists.get(i).get(15).toString());
            Cell cell16 = row.createCell(16);
            cell16.setCellValue(lists.get(i).get(16).toString());
            Cell cell17 = row.createCell(17);
            cell17.setCellValue(lists.get(i).get(17).toString());

            cell1.setCellStyle(borderStyle);
            cell2.setCellStyle(borderStyle);
            cell3.setCellStyle(borderStyle);
            cell4.setCellStyle(borderStyle);
            cell5.setCellStyle(borderStyle);
            cell6.setCellStyle(borderStyle);
            cell7.setCellStyle(borderStyle);
            cell8.setCellStyle(borderStyle);
            cell9.setCellStyle(borderStyle);
            cell10.setCellStyle(borderStyle);
            cell11.setCellStyle(borderStyle);
            cell12.setCellStyle(borderStyle);
            cell13.setCellStyle(borderStyle);
            cell14.setCellStyle(borderStyle);
            cell15.setCellStyle(borderStyle);
            cell16.setCellStyle(borderStyle);
            cell17.setCellStyle(borderStyle);


            //System.out.println(lists.get(i));
            Double G = tools.Re(lists.get(i).get(6).toString());
            Double H = tools.Re(lists.get(i).get(7).toString());
            Double I = tools.Re(lists.get(i).get(8).toString());
            Double J = tools.Re(lists.get(i).get(9).toString());

            Double K = tools.Re(lists.get(i).get(10).toString());
            Double L = tools.Re(lists.get(i).get(11).toString());
            Double M = tools.Re(lists.get(i).get(12).toString());
            Double N = tools.Re(lists.get(i).get(13).toString());

            Double O = tools.Re(lists.get(i).get(14).toString());
            Double P = tools.Re(lists.get(i).get(15).toString());
            Double Q = tools.Re(lists.get(i).get(16).toString());
            Double R = tools.Re(lists.get(i).get(17).toString());

            if (G != null && H != null && I != null && J != null) {

                if (G >= 60 && H >= 50 && I <= 40 && J <= 35) {
                    //lists.get(i).add("主队热");
                    Cell cell18 = row.createCell(18);
                    cell18.setCellValue("主队热");
                    cell18.setCellStyle(borderStyle);
                    cell6.setCellStyle(style1);
                    cell7.setCellStyle(style1);
                } else if (I >= 60 && J >= 50 && G <= 40 && H <= 35) {
                    //lists.get(i).add("主队热");
                    Cell cell18 = row.createCell(18);
                    cell18.setCellValue("客队热");
                    cell18.setCellStyle(borderStyle);
                    cell8.setCellStyle(style1);
                    cell9.setCellStyle(style1);
                } else {
                    Cell cell18 = row.createCell(18);
                    cell18.setCellValue("");
                    cell18.setCellStyle(borderStyle);
                }
            } else {
                Cell cell18 = row.createCell(18);
                cell18.setCellValue("");
                cell18.setCellStyle(borderStyle);
            }

            if (K != null && L != null && M != null && N != null) {
                if (K >= 60 && L >= 50 && M <= 40 && N <= 35) {
                    //lists.get(i).add("主队热");
                    Cell cell19 = row.createCell(19);
                    cell19.setCellValue("主队热");
                    cell19.setCellStyle(borderStyle);
                    cell10.setCellStyle(style2);
                    cell11.setCellStyle(style2);
                } else if (M >= 60 && N >= 50 && K <= 40 && L <= 35) {
                    //lists.get(i).add("客队热");
                    Cell cell19 = row.createCell(19);
                    cell19.setCellValue("客队热");
                    cell19.setCellStyle(borderStyle);
                    cell12.setCellStyle(style2);
                    cell13.setCellStyle(style2);
                } else {
                    Cell cell19 = row.createCell(19);
                    cell19.setCellValue("");
                    cell19.setCellStyle(borderStyle);
                }
            } else {
                Cell cell19 = row.createCell(19);
                cell19.setCellValue("");
                cell19.setCellStyle(borderStyle);
            }

            if (O != null && P != null && Q != null && R != null) {
                if (O >= 60 && P >= 50 && Q <= 40 && R <= 35) {
                    // lists.get(i).add("主队热");
                    Cell cell20 = row.createCell(20);
                    cell20.setCellValue("主队热");
                    cell20.setCellStyle(borderStyle);
                    cell14.setCellStyle(style3);
                    cell15.setCellStyle(style3);
                } else if (Q >= 60 && R >= 50 && O <= 40 && P <= 35) {
                    // lists.get(i).add("客队热");
                    Cell cell20 = row.createCell(20);
                    cell20.setCellValue("客队热");
                    cell20.setCellStyle(borderStyle);
                    cell16.setCellStyle(style3);
                    cell17.setCellStyle(style3);
                } else {
                    Cell cell20 = row.createCell(20);
                    cell20.setCellValue("");
                    cell20.setCellStyle(borderStyle);
                }
            } else {
                Cell cell20 = row.createCell(20);
                cell20.setCellValue("");
                cell20.setCellStyle(borderStyle);
            }
        }
        // writer.flush();
        try (FileOutputStream outputStream = new FileOutputStream(property)) {
            writer.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lists;
    }

    private  boolean Greater_than_13(String sid) throws IOException, ParseException {
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
