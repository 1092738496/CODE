package com.meditation.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @time: 2024/3/6 14:16
 * @description:
 */

@Component
public class tools {
    public  String regexStr(String text, String regex) {
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

    public   String regexStr1(String text, String regex) {
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


    public  Double Re(String value) {
        Double index = null;
        if (!value.equals("")) {
            index = Double.valueOf(value.replaceAll("%", ""));
        }
        return index;
    }


}
