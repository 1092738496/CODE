import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import com.github.stuxuhai.jpinyin.ChineseHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @time: 2023/12/17 23:51
 * @description:
 */

public class mian {
    public static void main(String[] args) {
        String property = System.getProperty("user.dir");
        File file = new File(property+"//小说");
        for (File listFile : file.listFiles()) {
            if (listFile.toString().endsWith("txt")) {
                updataFileName(listFile.toString());
            }
        }

    }
    public static void updataFileName(String path) {
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

        String s = traditionalToSimplified(fileReader.readString());

        for (String s1 : map.keySet()) {
            s = s.replaceAll(s1, map.get(s1));
        }

        FileWriter writer = new FileWriter(path);
        writer.write(s);
    }
    public static String traditionalToSimplified(String traditionalChinese) {
        String tempStr = null;
        try {
            tempStr = ChineseHelper.convertToSimplifiedChinese(traditionalChinese);
        } catch (Exception e) {
            tempStr = traditionalChinese;
            e.printStackTrace();
        }

        return tempStr;
    }
}
