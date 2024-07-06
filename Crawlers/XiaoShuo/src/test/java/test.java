import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.net.URLEncodeUtil;
import com.github.stuxuhai.jpinyin.ChineseHelper;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @time: 2023/12/18 2:08
 * @description:
 */

public class test {
    @Test
    public void test1() {
        try {
            System.out.println(URLDecoder.decode("撒大大", "utf-8"));
            System.out.println(URLEncodeUtil.encode("ASDASDasdasd是"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        File file = new File("C:\\Users\\Quiet\\Desktop\\催眠系列\\催眠眼镜（1-13+2番外）");
        for (File File : file.listFiles()) {
            if (File.toString().endsWith(".doc") | File.toString().endsWith(".docx")) {
                try {
                    OutputStream stream = new FileOutputStream(File.toString().replaceAll("(.docx)|(.doc)", ".txt"));
                    String s = wordTxt(File.toString());
                    stream.write(s.getBytes());
                    stream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test3() {
        File file = new File("C:\\Users\\Quiet\\Desktop\\新建文件夹");
        for (File listFile : file.listFiles()) {
            if (listFile.toString().endsWith("txt")) {
                this.updataFileName(listFile.toString());
            }
        }

    }

    public static String wordTxt(String path) {
        String text = "";
        try {
            if (path.endsWith(".doc")) {
                FileInputStream fileInputStream = new FileInputStream(new File(path));
                WordExtractor wordExtractor = new WordExtractor(fileInputStream);
                text = wordExtractor.getText();
                wordExtractor.close();
            } else if (path.endsWith(".docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(path);
                POIXMLTextExtractor textExtractor = new XWPFWordExtractor(opcPackage);
                text = textExtractor.getText();
            }
        } catch (Exception e) {
            System.out.println(path);
            System.out.println(e);
        }
        return text;
    }

    public String traditionalToSimplified(String traditionalChinese) {
        String tempStr = null;
        try {
            tempStr = ChineseHelper.convertToSimplifiedChinese(traditionalChinese);
        } catch (Exception e) {
            tempStr = traditionalChinese;
            e.printStackTrace();
        }

        return tempStr;
    }

    public void updataFileName(String path) {
        Map<String, String> map = new HashMap<>();


        map.put("操干", "捅");
        map.put("抽插", "草");
        map.put("抽草", "草");
        map.put("欠肏", "欠草");
        map.put("口交", "吃鸡吧");
        map.put("欠干", "欠日");
        map.put("干死你", "草死你");
        map.put("日屄", "日逼");
        map.put("肏屄", "操逼");
        map.put("揉屄", "揉逼");
        map.put("挨肏", "挨操");
        map.put("肏迷糊", "操迷糊");
        map.put("淫弄", "日");
        map.put("弄死", "草死");
        map.put("插死", "草死");
        map.put("碧血洗银枪", "逼血洗鸡巴");

//屄
        map.put("肉棒棒", "鸡巴");
        map.put("肉棒", "鸡巴");
        map.put("巨根", "巨大鸡巴");
        map.put("龟头", "鸡巴头");
        map.put("阴茎", "鸡巴");
        map.put("性能力", "操逼能力");
        map.put("粗屌", "粗鸡巴");
        map.put("肉屌", "鸡巴");
        map.put("肥根", "鸡巴");
        map.put("肥棒", "鸡巴");
        map.put("肉棍", "鸡巴");


        map.put("乳房", "奶子");
        map.put("奶奶", "奶子");
        map.put("乳球", "奶子");
        map.put("乳头", "奶头");
        map.put("乳尖", "奶尖");
        map.put("肥乳", "肥奶子");
        map.put("乳汁", "奶水");
        map.put("乳罩", "奶罩");
        map.put("胸罩", "奶罩");
        map.put("失禁", "尿");
        map.put("阴屄", "骚逼");
        map.put("阴户", "阴屄");


        /*map.put("屄", "逼");
        map.put("小穴", "逼");*/
        map.put("穴", "屄");
        map.put("花瓣", "逼唇");
        map.put("阴毛", "逼毛");
        map.put("嫩屄", "嫩逼");
        map.put("阴唇", "逼唇");
        map.put("花芯", "逼芯");
        map.put("花心", "逼芯");
        map.put("淫屄", "淫逼");
        map.put("骚屄", "骚逼");
        map.put("肥屄", "肥逼");
        map.put("臭屄", "臭逼");
        map.put("贱屄", "贱逼");
        map.put("屄水", "逼水");
        map.put("肉洞", "逼洞");
        map.put("香屄", "骚逼");
        map.put("屄屄", "逼逼");
        map.put("肉唇", "逼唇");
        map.put("小洞", "小屄");
        map.put("屄肉", "逼肉");
        map.put("肉屄", "逼屄");
        map.put("露屄", "露逼");
        map.put("踩屄", "踩逼");
        map.put("小屄", "骚屄");
        map.put("蜜屄", "湿屄");

        map.put("肛蕾", "屁蕾");
        map.put("屁穴", "屁眼");
        map.put("腚眼", "屁眼");
        map.put("后庭", "屁眼");
        map.put("肛屄", "屁屄");
        map.put("肛门", "屁眼");
        map.put("菊花", "屁眼");
        map.put("后门", "屁眼");
        map.put("大鸡巴插", "大鸡巴操");

        map.put("乳交", "插奶子");
        map.put("肛交", "操屁眼");
        map.put("屄交", "操逼");
        FileReader fileReader = new FileReader(path,"utf-8");
        if (fileReader.readString().contains("�")) {
            fileReader = new FileReader(path ,"gbk");
        }

        String s = this.traditionalToSimplified(fileReader.readString());

        for (String s1 : map.keySet()) {
            s = s.replaceAll(s1, map.get(s1));
        }

        FileWriter writer = new FileWriter(path);
        writer.write(s);
    }


    public static boolean isGarbled(String str, Charset charset) {
        return !new String(str.getBytes(charset), charset).equals(str);
    }

    @Test
    public void laowang() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"));
        httpUtils httpUtils = new httpUtils(headers);
        String name = URLEncodeUtil.encode("国产");

        int i = 1;
        int size = 0;
        do {
            String s = httpUtils.get("https://laowang508frh.xyz/forum.php?mod=forumdisplay&fid=65&page=" + i);
            //https://laowang508frh.xyz/search/s.php?q=国人&m=&f=subject&s=dateline_DESC&fid=&p=2
            Elements select = Jsoup.parse(s).select("#threadlisttableid > tbody");
            size = select.size();
            System.out.println(size);
            for (Element element : select) {
                Elements select1 = element.select("tbody > tr > th > a.s.xst");
                if (!select1.text().contains("已失效")) {
                    String text = element.select("tbody > tr > th > a.s.xst").text();
                    System.out.println(text);
                    System.out.println(select1.attr("href"));

                }

            }
            i++;
        } while (size == 30);
    }

    @Test
    public void test5() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"));
        httpUtils httpUtils = new httpUtils(headers);
        String name = URLEncodeUtil.encode("国产");

        int i = 1;
        int size = 0;
        do {
            String s = httpUtils.get("https://laowang.vip/forum-65-"+i+".html");
            Elements select = Jsoup.parse(s).select("#moderate > table > tbody");
            size = select.size();
            for (Element element : select) {
                Elements select1 = element.select("tr > th > a.s.xst");
                if (!select1.text().contains("已失效")) {
                    if (select1.text().contains("涟漪")) {
                        System.out.println(select1.text());
                        System.out.println(select1.attr("href"));

                    }
                }

            }
            i++;
        } while (size > 30);
    }
}
