package com.meditation.dao;

import com.meditation.pojo.sfp;
import com.meditation.utils.httpUtils;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.core5.http.ParseException;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.GZIPInputStream;

/**
 * @time: 2024/7/30 14:39
 * @description:
 */

@Component
public class Ya_sfp {
    @Autowired
    ThreadPoolExecutor pool;

    @Autowired
    private CloseableHttpAsyncClient AsyncClient;
    @Autowired
    com.meditation.utils.tools tools;
    @Autowired
    httpUtils httpUtil;

    public sfp sfpData(String sid) {
        sfp sfp = new sfp();
        try {
            String html = httpUtil.get("https://1x2.titan007.com/oddslist/" + sid + ".htm", "utf-8");
            String src = Jsoup.parse(html).select("body > script:nth-child(1)").attr("src");
            String js = httpUtil.get("https:" + src, "utf-8");

            String host = tools.regexStr1(js, "var hometeam_cn=.*;var guestteam_cn").replaceAll("var hometeam_cn=\"",
                    "").replaceAll("\";var guestteam_cn", "");
            String guest = tools.regexStr1(js, "var guestteam_cn=.*;var hometeam_f").replaceAll("var guestteam_cn=\"",
                    "").replaceAll("\";var hometeam_f", "");
            sfp.setHost(host);
            sfp.setGuest(guest);
            String game = tools.regexStr1(js, "game=Array.*;var gameDetail").replaceAll("game=Array\\(", "").replaceAll(
                    "\\);var gameDetail", "");

            String gameDetail = tools.regexStr1(js, "var gameDetail=Array\\(.*\\);var jcEuropeOddsData").replaceAll("\\);" +
                            "var " +
                            "jcEuropeOddsData",
                    "").replaceAll("var gameDetail=Array\\(", "");
            String[] Detail = gameDetail.split(";\",\"");

            Map<String, String> id_name = new HashMap<>();
            String[] splitz = game.split("\",\"");
            for (String s : splitz) {
                String[] split1 = s.split("\\|");
                // List<String> z = new ArrayList<>(Arrays.asList(split1));
                id_name.put(split1[1],split1[split1.length-3]);
            }

            Map<String, List<List<String>>> gameDetails = new HashMap<>();
            for (int i = 0; i < Detail.length; i++) {
                String t = Detail[i];
                if (i == 0 | i == Detail.length - 1) {
                    t = Detail[i].replaceAll("\"", "");
                }
                String[] split = t.split(";");
                String id = "";
                List<List<String>> zs = new ArrayList<>();
                for (int j = 0; j < split.length; j++) {
                    String y = split[j];
                    if (j == 0) {
                        String[] split1 = split[j].split("\\^");
                        id = split1[0];
                        y = split1[1];
                    }
                    String[] split1 = y.split("\\|");
                    List<String> z = new ArrayList<>(Arrays.asList(split1));
                    zs.add(z);
                }
                String name = id_name.get(id);
                gameDetails.put(name, zs);
            }
            sfp.setDs(gameDetails);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return sfp;
    }

    public String decompress(byte[] compressedData) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedData);
        GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;
        while ((len = gzipInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toString("UTF-8");

    }
}
