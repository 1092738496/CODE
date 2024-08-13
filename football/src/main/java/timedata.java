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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @time: 2024/3/12 15:50
 * @description:
 */

public class timedata {
    static httpUtils httpUtils = new httpUtils();

    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"));
        httpUtils.setHeaders(headers);
    }

    public ArrayList<IdentityHashMap<String, ArrayList<ArrayList<Object>>>> tabular_data(String sid) {

        ArrayList<IdentityHashMap<String, ArrayList<ArrayList<Object>>>> lists = new ArrayList<>();
        try {
            String s = httpUtils.get("https://vip.titan007.com/AsianOdds_n.aspx?id=" + sid, "utf-8");
            Document parse = Jsoup.parse(s);
            Elements select = parse.selectXpath("//*[@id=\"odds\"]/tbody/tr[(@bgcolor and @bgcolor != '') and not(@companyid)]");
           /*         LocalDate date1 = LocalDate.of(2024, 4, 20);
            boolean isAfter = date1.isAfter(LocalDate.now());
            if (isAfter) {
                select = parse.selectXpath("//*[@id=\"odds\"]/tbody/tr[(@bgcolor and @bgcolor != '') and not" +
                        "(@companyid)]");
            } else {
                select = parse.select("//*[@id=\"odds\"]/tbody/tr[(@bgcolor and @bgcolor != '') and not" +
                        "(@companyid)]");
            }*/
            for (Element element : select) {
                IdentityHashMap<String, ArrayList<ArrayList<Object>>> map = new IdentityHashMap<>();
                String text = new String(element.select("tr > td").first().text().replaceAll("封", ""));
                String href = "https://vip.titan007.com" + element.select("tr > td:last-child > a:first-child").attr(
                        "href");
                String jidata = httpUtils.get(href, "utf-8");
                Elements select1 = Jsoup.parse(jidata).select("#odds2 > table > tbody > tr");
                ArrayList<ArrayList<Object>> list1 = new ArrayList<>();
                for (Element element1 : select1) {
                    if (element1.select("tr > td:last-child").text().equals("即")||element1.select("tr > td:last-child").text().equals("早")) {
                        ArrayList<Object> list2 = new ArrayList<>();
                        Elements select2 = element1.select("tr > td");
                        if (select2.size() == 7) {
                            for (int i = 2; i < select2.size(); i++) {
                                list2.add(select2.get(i).text());
                            }
                        } else {
                            for (Element element2 : select2) {
                                list2.add(element2.text());
                            }

                        }
                        list1.add(list2);
                    }
                }
                if (list1.size() != 0) {
                    map.put(text, list1);
                    lists.add(map);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return lists;
    }

    public ArrayList<ArrayList<String>> Be_identical(ArrayList<ArrayList<ArrayList<Object>>> listss,
                                                     String timeStr) {
        ArrayList<ArrayList<String>> lists = new ArrayList<>();
        for (int i = 0; i < listss.size(); i++) {
            for (int j = 0; j < listss.get(i).size(); j++) {
                if (listss.get(i).get(j) != null && i != 0) {
                    if (listss.get(i).get(j).get(3) != null) {
                        if (listss.get(i).get(j).get(3).toString().equals(timeStr)) {
                            ArrayList<String> list = new ArrayList<>();
                            list.add(listss.get(0).get(j).get(0).toString());
                            list.add(String.valueOf(i));
                            list.add(String.valueOf(j));
                            lists.add(list);
                        }
                    }
                }
            }
        }


        return lists;
    }

    private ArrayList<ArrayList<Object>> index_value(ArrayList<IdentityHashMap<String, ArrayList<ArrayList<Object>>>> listsMaps,
                                                     Integer index) {
        ArrayList<ArrayList<Object>> listss = new ArrayList<>();
        //遍历每个map
        for (int i = 0; i < listsMaps.size(); i++) {
            Object[] keys = listsMaps.get(i).keySet().toArray();
            //获取每个map的key
            for (int j = 0; j < keys.length; j++) {
                ArrayList<ArrayList<Object>> lists = listsMaps.get(i).get(keys[j].toString());
                try {
                    listss.add(lists.get(index));
                } catch (IndexOutOfBoundsException e) {
                    ArrayList<Object> list = new ArrayList<>();
                    list.add(null);
                    list.add(null);
                    list.add(null);
                    list.add(null);
                    list.add(null);
                    listss.add(list);
                }

            }
        }
        return listss;
    }

    public ArrayList<ArrayList<ArrayList<Object>>> fanzhuanList(ArrayList<IdentityHashMap<String,
            ArrayList<ArrayList<Object>>>> listsMaps) {

        ArrayList<ArrayList<ArrayList<Object>>> lists = new ArrayList<>();
        ArrayList<ArrayList<Object>> titles = new ArrayList<>();
        ArrayList<Integer> listmax = new ArrayList<Integer>();
        for (int i = 0; i < listsMaps.size(); i++) {
            Object[] keys = listsMaps.get(i).keySet().toArray();
            //获取每个map的key
            ArrayList<Object> title = new ArrayList<Object>();
            for (int j = 0; j < keys.length; j++) {
                title.add(keys[j].toString());
                title.add(null);
                title.add(null);
                title.add(null);
                title.add(null);
                ArrayList<ArrayList<Object>> listss = listsMaps.get(i).get(keys[j].toString());
                listmax.add(listss.size());
            }
            titles.add(title);
        }
        lists.add(titles);
        Integer max = Collections.max(listmax);
        for (Integer i = 0; i < max; i++) {
            ArrayList<ArrayList<Object>> listss = this.index_value(listsMaps, i);
            // System.out.println(listss);
            lists.add(listss);
        }
        return lists;
    }

    public byte[] getrgb() {
        // 限制颜色通道值的范围
        int minChannelValue = 50;
        int maxChannelValue = 150;

        // 生成随机的 RGB 颜色
        Random random = new Random();
        int red = random.nextInt(maxChannelValue - minChannelValue + 1) + minChannelValue;
        int green = random.nextInt(maxChannelValue - minChannelValue + 1) + minChannelValue;
        int blue = random.nextInt(maxChannelValue - minChannelValue + 1) + minChannelValue;

        // 确保每个颜色通道值不同
        int diff1 = random.nextInt(100) + 1;
        int diff2 = random.nextInt(100) + 1;
        int diff3 = random.nextInt(100) + 1;
        red = Math.min(255, red + diff1);
        green = Math.min(255, green + diff2);
        blue = Math.min(255, blue + diff3);
        return new byte[]{(byte) red, (byte) green, (byte) blue};
    }

    public void export() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要统计的sid,例如:2526310:");
        String sid = scanner.nextLine();
        String property = "";
        try {
            String html = httpUtils.get("https://vip.titan007.com/AsianOdds_n.aspx?id=" + sid, "utf-8");
            Elements select = Jsoup.parse(html).select("body > div.header > div.analyhead");
            String home = select.select("div > div.home").text();
            String guest = select.select("div > div.guest").text();

            property = System.getProperty("user.dir") + "\\" + home + "-" + guest + ".xlsx";
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        ArrayList<IdentityHashMap<String, ArrayList<ArrayList<Object>>>> listsMaps = this.tabular_data(
                sid);
        ArrayList<ArrayList<ArrayList<Object>>> listss = this.fanzhuanList(listsMaps);

        HashMap<String, ArrayList<ArrayList<String>>> map = new HashMap<>();
        for (ArrayList<ArrayList<Object>> lists : listss) {
            for (ArrayList<Object> list : lists) {
                if (list.get(3) != null) {
                    ArrayList<ArrayList<String>> arrayLists = this.Be_identical(listss,
                            list.get(3).toString());
                    map.put(list.get(3).toString(), arrayLists);
                }
            }
        }
        TreeMap<String, ArrayList<ArrayList<String>>> sortedMap = new TreeMap<>(new Comparator<String>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("M-dd HH:mm");

            @Override
            public int compare(String o1, String o2) {
                try {
                    return dateFormat.parse(o2).compareTo(dateFormat.parse(o1));
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        sortedMap.putAll(map);
        Workbook writer = new XSSFWorkbook();
        Sheet sheet = writer.createSheet("Sheet1");
        for (int i = 0; i < listss.size(); i++) {
            Row row = sheet.createRow(i);
            ArrayList<ArrayList<Object>> lists = listss.get(i);
            int x = 6;
            for (int j = 0; j < lists.size(); j++) {
                ArrayList<Object> list = lists.get(j);
                for (int n = 0; n < list.size(); n++) {
                    Cell cell = row.createCell(x);
                    if (list.get(n) != null) {
                        cell.setCellValue(list.get(n).toString());
                    } else {
                        cell.setCellValue("");
                    }
                    x++;
                }
                x++;
            }
        }
        Row modifyRow = sheet.getRow(1);
        Cell modifyCell1 = modifyRow.createCell(0);
        Cell modifyCell2 = modifyRow.createCell(1);
        Cell modifyCell3 = modifyRow.createCell(2);
        modifyCell1.setCellValue("统计时间节点");
        modifyCell2.setCellValue("公司数量");
        modifyCell3.setCellValue("公司名称");

        /*byte[] rgb1 = {(byte) 221, (byte) 178, (byte) 43};
        CellStyle style1 = writer.createCellStyle();
        XSSFColor customColor1 = new XSSFColor(rgb1);
        style1.setFillForegroundColor(customColor1);
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        byte[] rgb2 = {(byte) 78, (byte) 134, (byte) 180};
        CellStyle style2 = writer.createCellStyle();
        XSSFColor customColor2 = new XSSFColor(rgb2);
        style2.setFillForegroundColor(customColor2);
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // Color color = new Color(78, 180, 83);*/


        int q = 0;
        boolean t = false;
        int x = 2;
        Object[] keys = sortedMap.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            //byte[] rgb3 = {(byte) 78, (byte) 180, (byte) 83};
            CellStyle style3 = writer.createCellStyle();
            XSSFColor customColor3 = new XSSFColor(this.getrgb());
            style3.setFillForegroundColor(customColor3);
            style3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            ArrayList<ArrayList<String>> Lists = map.get(keys[i]);
            Row vmodifyRow = sheet.getRow(x);
            if (vmodifyRow == null) {
                vmodifyRow = sheet.createRow(x);
            }
            Cell vmodifyCell1 = vmodifyRow.createCell(0);
            vmodifyCell1.setCellValue(keys[i].toString());
            Cell vmodifyCell2 = vmodifyRow.createCell(1);
            vmodifyCell2.setCellValue(Lists.size());
            Cell vmodifyCell3 = vmodifyRow.createCell(2);


            if (Lists.size() >= 2) {
                t = true;
                for (int j = 0; j < Lists.size(); j++) {
                    Row vmodifyRow4 = sheet.getRow(x);
                    if (vmodifyRow4 == null) {
                        vmodifyRow4 = sheet.createRow(x);
                    }
                    Cell vmodifyCell4 = vmodifyRow4.createCell(2);
                    vmodifyCell4.setCellValue(Lists.get(j).get(0));

                    x++;


                    int y = Integer.parseInt(Lists.get(j).get(1));
                    int s = Integer.parseInt(Lists.get(j).get(2));
                    // System.out.println("s:" + s);
                    int n = 0;

                    for (int z = 0; z < s + 1; z++) {
                        if (s == 0) {
                            n = n + 4;
                        } else {
                            n = n + 5;
                        }
                    }
                    //System.out.println("n:" + n);
                    Row row = sheet.getRow(y);
                    if (s != 0) {
                        s = s - 1;
                    }
                    Cell cell1 = row.getCell(n + s + 2);
                    Cell cell2 = row.getCell(n + s + 2 + 1);
                    Cell cell3 = row.getCell(n + s + 2 + 2);
                    Cell cell4 = row.getCell(n + s + 2 + 3);
                    Cell cell5 = row.getCell(n + s + 2 + 4);

                    String a1 = cell1.getStringCellValue();
                    String a3 = cell3.getStringCellValue();

                    Row row2 = sheet.getRow(y + 1);
                    Cell cell1b1 = row2.getCell(n + s + 2);
                    Cell cell1b3 = row2.getCell(n + s + 2 + 2);
                    Cell cella1 = vmodifyRow4.createCell(3);
                    Cell cella3 = vmodifyRow4.createCell(4);
                    if (cell1b1 != null) {
                        String b1 = cell1b1.getStringCellValue();
                        String b3 = cell1b3.getStringCellValue();
                        if (!b1.equals("") & b1 != null) {
                            cella1.setCellValue(Double.parseDouble(a1) - Double.parseDouble(b1));
                            cella3.setCellValue(Double.parseDouble(a3) - Double.parseDouble(b3));
                        } else {
                            cella1.setCellValue("无上一数据");
                        }
                    } else {
                        cella1.setCellValue("无上一数据");
                    }
                    if (q < 5) {
                        vmodifyCell1.setCellStyle(style3);
                        vmodifyCell2.setCellStyle(style3);
                        vmodifyCell4.setCellStyle(style3);
                        cell1.setCellStyle(style3);
                        cell2.setCellStyle(style3);
                        cell3.setCellStyle(style3);
                        cell4.setCellStyle(style3);
                        cell5.setCellStyle(style3);
                    }
                }
            } else {
                t = false;
                vmodifyCell3.setCellValue(Lists.get(0).get(0));
                int y = Integer.parseInt(Lists.get(0).get(1));
                int s = Integer.parseInt(Lists.get(0).get(2));
                Row row = sheet.getRow(y);
                int n = 0;

                for (int z = 0; z < s + 1; z++) {
                    if (s == 0) {
                        n = n + 4;
                    } else {
                        n = n + 5;
                    }
                }
                if (s != 0) {
                    s = s - 1;
                }
                Cell cell1 = row.getCell(n + s + 2);
                Cell cell3 = row.getCell(n + s + 2 + 2);
                String a1 = cell1.getStringCellValue();
                String a3 = cell3.getStringCellValue();
                Row row2 = sheet.getRow(y + 1);
                Cell cell1b1 = row2.getCell(n + s + 2);
                Cell cell1b3 = row2.getCell(n + s + 2 + 2);
                Cell cella1 = vmodifyRow.createCell(3);
                Cell cella3 = vmodifyRow.createCell(4);
                if (cell1b1 != null) {
                    String b1 = cell1b1.getStringCellValue();
                    String b3 = cell1b3.getStringCellValue();
                    if (!b1.equals("") & b1 != null) {
                        cella1.setCellValue(Double.parseDouble(a1) - Double.parseDouble(b1));
                        cella3.setCellValue(Double.parseDouble(a3) - Double.parseDouble(b3));
                    } else {
                        cella1.setCellValue("无上一数据");
                    }
                } else {
                    cella1.setCellValue("无上一数据");
                }
                x++;
            }
            if (t) {
                q++;
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(property)) {
            writer.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("表格已导出到:" + property + ",请查看,输入任意键退出");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);

    }
}
