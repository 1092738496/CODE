package com.meditation.utils;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: Andy
 * @time: 2023-9-4 16:07
 */

@Component
public class httpUtils {
    @Autowired
    private CloseableHttpClient httpclient;


    public String get(String url, String Charset) throws IOException, ParseException {
        String html = "";
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = httpclient.execute(httpGet);

        // 读取整个响应体
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),
                Charset))) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            html = responseBody.toString();
        }


        //html = EntityUtils.toString(response.getEntity());
        return html;
    }

    public String get(String url) throws IOException, ParseException {
        String html = "";
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = httpclient.execute(httpGet);

        // 读取整个响应体
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            html = responseBody.toString();
        }
        response.close();
        return html;
    }

    public Map<String, String> getfileName(String url) {
        Map<String, String> hashMap = new HashMap<String, String>();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            String Length = response.getHeaders("Content-Length")[0].getValue();
            String fileName = null;
            String Type = null;
            Header[] Content_Disposition = response.getHeaders("Content-Disposition");
            httpGet.abort();
            if (Content_Disposition.length != 0) {
                String Name = new String(Content_Disposition[0].getValue().getBytes("ISO-8859-1"), "utf8");
                Pattern p = Pattern.compile("(?<=\").*?(?=\")");
                Matcher m = p.matcher(Name);
                StringBuilder s = new StringBuilder();
                while (m.find()) {
                    s.append(m.group());
                }
                String[] split = s.toString().split("\\.");
                fileName = split[0];
                Type = split[1];
            } else {
                String[] split = url.split("/");
                String name = split[split.length - 1];
                if (name.contains("?")) {
                    String[] split1 = name.split("\\?");
                    fileName = split1[0];
                    String[] split2 = split1[split1.length - 1].split("\\.");
                    Type = split2[split2.length - 1];
                } else {
                    String[] split1 = name.split("\\.");
                    Type = split1[split1.length - 1];
                    fileName = name.replaceAll("." + Type, "");
                }
            }
            hashMap.put("fileName", fileName);
            hashMap.put("Type", Type);
            hashMap.put("Length", Length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    public String[] splitFileLength(long fileSize, int number) {
        long l = fileSize / number;
        String[] Strings = new String[number];
        for (int i = 1; i <= number; i++) {
            long x = (i - 1) * l;
            long y;
            if (i == number) {
                y = 0;
            } else {
                y = x + l;
            }

            if (x != 0) {
                x++;
            }
            Strings[i - 1] = x + "-" + y;
        }
        return Strings;
    }


}
