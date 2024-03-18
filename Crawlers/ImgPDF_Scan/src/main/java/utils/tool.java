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
        System.out.println(property);
        props = new Props(property + "//setting.properties");
       /* String path = Props.class.getClassLoader().getResource("setting.properties").getPath();
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
        // System.out.println(text);
        //匹配收:姓名 手机号
        String sd = regexStr(text, "收：.[\\u4E00-\\u9FA5A-Za-z0-9_(\\u3002|\\uff1f|\\uff01|\\uff0c|\\u3001|\\uff1b" +
                "|\\uff1a|\\u201c|\\u201d|\\u2018|\\u2019|\\uff08|\\uff09|\\u300a|\\u300b|\\u3010|\\u3011|\\u007e" +
                "|\\@\\-.\\$^%&',\\:;=?$\\*\\x22\\(\\))" +
                "!#+\\/><·￥{}、；‘’，。、{}|《》？￥@#%……！#：“”【】@#￥%……&*（）——+=\\[\\]·！\\\\]*.([0-9]{11})*");
        System.out.println(sd);
        //去掉姓名 和 手机号 ,剩余地址
        String s1 = text.replaceAll(sd, "");
        s1 = s1.replaceAll("\\r|\\n", "").trim();
        //去掉 "收:"字,剩余 姓名 手机号
        String s = sd.replaceAll("收：", "");
        //拆分姓名 和 手机号
        String[] s2 = s.split(" ");

        //获取结果
        String[] x = new String[0];
        try {
            x = new String[]{s2[0], s2[1], s1};
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("以下格式无法导出,需要手动填写,按Y继续...");
            System.out.println(text);
            Scanner input = new Scanner(System.in);
            String next = input.next();
            if (!next.equals("y") | !next.equals("Y")) {
                System.out.println(e);
            }
        }

        for (String x1 : x) {
            System.out.println(x1);
        }
        return x;
    }

    public static String[] imgformats(String text) {
        text = text.replaceAll("收：", "");
        // System.out.println(text);
        //匹配收:手机号
        String sj = regexStr(text, "([0-9*]{11,})*").trim();
        String aa = "";//前面多余的数
        if (sj.length() != 11) {
            String b = sj.subSequence(sj.length() - 11, sj.length()).toString();
            aa = sj.substring(0, sj.length() - 11);
            sj = b;
        }
        //去掉姓名 和 手机号 ,剩余地址
        String[] split = text.split(sj);

        String name = (split[0].replaceAll(" ", "") + aa).trim();
        String dz = "";
        if (!sj.equals(name)) {
            dz = split[1].replaceAll(" ", "").replaceAll("\n", "").trim();
        }else{
            dz = split[2].replaceAll(" ", "").replaceAll("\n", "").trim();
        }
        System.out.println(name);
        System.out.println(sj);
        System.out.println(dz);
        String[] x = {name, sj, dz};
        return x;
    }

    public static String getstr(String key) {
        String str = props.getStr(key);
        return str;
    }
}
