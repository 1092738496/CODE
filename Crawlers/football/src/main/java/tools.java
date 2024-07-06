import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @time: 2024/3/6 14:16
 * @description:
 */

public class tools {
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

    public static String regexStr1(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        StringBuilder z = new StringBuilder();
        while (matcher.find()) {
            if (!matcher.group().equals("")) {
                z.append(matcher.group());
            }
        }

        return z.toString();
    }

    public static List<List<String>> JsValue(String js, String args) {
        args = "(var " + args + " = \\[\\[)";
        String arr = regexStr(js, args + ".*;").replaceAll(args, "").replaceAll("\\]\\];", "").replaceAll("'", "");
        List<List<String>> lists = new LinkedList<>();
        String[] split = arr.split("\\],\\[");
        for (int i = 0; i < 6; i++) {
            List<String> list = new ArrayList<>();
            for (String s1 : split[i].split(",")) {
                list.add(s1);

            }
            lists.add(list);
        }
        return lists;
    }

    public static List<Object> ss(List<Object> list) {
        ArrayList<Object> listx = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i).toString();
            if (i > 5) {
                if (!s.equals("")) {
                    String shengnv = regexStr(s, "胜率:([0-9*.])*%").replaceAll("胜率:", "");
                    listx.add(shengnv);
                    String yingnv = regexStr(s, "赢率:([0-9*.])*%").replaceAll("赢率:", "");
                    listx.add(yingnv);
                } else {
                    listx.add(s);
                }
            } else {
                listx.add(s);
            }
        }
        return listx;
    }

    public static Double Re(String value) {
        Double index = null;
        if (!value.equals("")) {
            index = Double.valueOf(value.replaceAll("%", ""));
        }
        return index;
    }


}
