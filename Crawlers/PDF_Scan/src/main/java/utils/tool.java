package utils;

import cn.hutool.setting.dialect.Props;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: Andy
 * @time: 2023-9-14 20:58
 */

public class tool {
    private static Props props;

    static {
        String property = System.getProperty("user.dir");
        props = new Props(property + "//setting.properties");
      /*  String path = Props.class.getClassLoader().getResource("setting.properties").getPath();
        props = new Props(path);*/
    }

    public static String regexStr(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        StringBuilder z = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            if (!matcher.group().equals("")) {
                i++;
                if (i == 1) {
                    z.append(matcher.group());
                }
            }
        }
        return z.toString();
    }

    public static String[] formats(String text) {
        System.out.println(text);
        //匹配收:姓名 手机号
       // String regex = props.get("regex").toString();
        //System.out.println(regex);
        //String sd = regexStr(text, regex);

        //去掉收:
        text = text.replaceAll("收：", "");

        String sd = regexStr(text, "[\\u4E00-\\u9FA5A-Za-z0-9_(\\u3002|\\uff1f|\\uff01|\\uff0c|\\u3001|\\uff1b" +
                "|\\uff1a|\\u201c|\\u201d|\\u2018|\\u2019|\\uff08|\\uff09|\\u300a|\\u300b|\\u3010|\\u3011|\\u007e" +
                "|\\@\\-.\\$^%&',\\:;=?$\\*\\x22\\(\\))" +
                "!#+\\/><·￥{}、；‘’，。、{}|《》？￥@#%……！#：“”【】@#￥%……&*（）——+=\\[\\]·！\\\\]+.([0-9*]{11})");
        //去掉姓名 和 手机号 ,剩余地址
        String s1 = text.replaceAll(sd.replaceAll("\\*", "."), "");
        s1 = s1.replaceAll("\\r|\\n", "").trim();

        //拆分姓名 和 手机号
        String[] s2 = sd.split(" ");

        //获取结果
        String[] x = new String[0];
        try {
            x = new String[]{s2[0], s2[1], s1};
        } catch (Exception e) {
            System.out.println("以下格式无法导出,需要手动填写,按Y继续...");
            System.out.println(text);
            Scanner input=new Scanner(System.in);
            String next = input.next();
            if (!next.equals("y")|!next.equals("Y")) {
                System.out.println(e);
            }
        }

        for (String x1 : x) {
            System.out.println(x1);
        }
        return x;
    }

    public static String getstr(String key) {
        String str = props.getStr(key);
        return str;
    }
}
