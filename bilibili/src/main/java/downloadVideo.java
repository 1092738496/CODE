import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Cookie;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import tools.Props;
import tools.ThreadDown;
import tools.httpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Andy
 * @time: 2022/1/28 20:19
 */

public class downloadVideo {
    private static String downLoadPath;
    private static String tempPath;
    private static ObjectMapper objectMapper;
    private static httpUtils httpUtil;
    private static ThreadDown threadDown;
    private static int poolSize;
    private static List<Header> headers;

    static {
        String cookie = Props.getStr("cookie");
        System.out.println(cookie);
        String user_Agent = Props.getStr("User_Agent");

        downLoadPath = Props.getStr("downLoadPath");
        tempPath = Props.getStr("tempPath");
        poolSize = Integer.parseInt(Props.getStr("poolSize"));
        objectMapper = new ObjectMapper();

        headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Cookie", cookie));
        headers.add(new BasicHeader("User-Agent", user_Agent));
        headers.add(new BasicHeader("referer", "https://api.bilibili.com/"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip, deflate, br"));
        headers.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif," +
                "image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"));
        httpUtil = new httpUtils(headers);
        threadDown = new ThreadDown();

    }

    //整合
    public void integrate() {
        Runtime runtime = Runtime.getRuntime();
        try {
            JsonNode favoriteNode = objectMapper.readValue(this.gainVideoPath(), JsonNode.class);
            for (JsonNode favoritenode : favoriteNode) {
                int number = 1;
                String id = favoritenode.get("id").asText();
                String bvid = favoritenode.get("bvid").asText();
                String name = favoritenode.get("name").asText();
                String title = httpUtils.updataFileName(favoritenode.get("title").asText());
                List<List<Map<String, String>>> lists = this.parseVideoPath(bvid);
                for (List<Map<String, String>> list : lists) {
                    for (Map<String, String> stringStringMap : list) {
                        //headers.add(new BasicHeader("Referer", stringStringMap.get("referer")));
                        String namez = number + "." + httpUtils.updataFileName(stringStringMap.get("name"));
                        number++;
                        String pathfile = downLoadPath + "\\" + title;
                        File file1 = new File(pathfile);
                        if (!file1.exists()) {
                            FileUtil.mkdir(pathfile);
                        }

                        File file = new File(downLoadPath + "\\" + title + "\\" + namez + ".mp4");
                        if (!file.exists()) {
                            String video = stringStringMap.get("video");
                            String audio = stringStringMap.get("audio");
                            Map<String, String> videomap = httpUtil.getfileName(video, headers);
                            Map<String, String> audiomap = httpUtil.getfileName(audio, headers);
                            double length = new Double(videomap.get("Length")) + new Double(audiomap.get("Length"));
                            String property = System.getProperty("user.dir") + "\\" + "ffmpeg-master-latest-win64-gpl" +
                                    "-shared\\bin\\ffmpeg.exe";

                            String videopath = tempPath + "\\" + videomap.get("Name") + "." + videomap.get("Type");
                            String audiopath = tempPath + "\\" + audiomap.get("Name") + "." + audiomap.get("Type");
                            String dom =
                                    property + " -loglevel quiet -i \"" + videopath + "\"" + " -i \"" + audiopath +
                                            "\" -c:v copy -c:a copy \"" + downLoadPath + "\\" + title + "\\" + namez + ".mp4\"";
                            System.out.println("[" + name + "]-[" + namez + "]");
                            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
                            scheduledExecutorService.scheduleAtFixedRate(() -> {

                                String fdownsize = String.format("%.2f", threadDown.downSize.doubleValue() / 1048576);
                                double speed = threadDown.downSize.doubleValue() - threadDown.prevSize;
                                double remainSize = length - threadDown.downSize.doubleValue();

                                String fspeed = String.format("%.2f", speed / 1048576);
                                String flength = String.format("%.2f", (length / 1048576));
                              //  String fremainSize = String.format("%.1f", remainSize / speed / 60);
                               /* if ("Infinity".equalsIgnoreCase(fremainSize)) {
                                    fremainSize = "-";
                                }*/
                                System.out.print("↓ " + fdownsize + "/" + flength + "|" + fspeed + "mb\r");
                                // System.out.print("[" + name + "]-[" + namez + "]\r");
                                threadDown.prevSize = threadDown.downSize.doubleValue();
                            }, 0, 1, TimeUnit.SECONDS);
                            CountDownLatch count = new CountDownLatch(2);
                            new Thread(() -> {
                                threadDown.poolDownload(video, poolSize, poolSize, 5, videomap.get("Length"),
                                        videomap.get(
                                                "Name"),
                                        videomap.get("Type"), tempPath, downLoadPath, headers);
                                count.countDown();
                            }).start();
                            new Thread(() -> {
                                threadDown.poolDownload(audio, poolSize / 2, poolSize / 2, 5, audiomap.get("Length"),
                                        audiomap.get(
                                                "Name"),
                                        audiomap.get("Type"), tempPath, downLoadPath, headers);
                                count.countDown();
                            }).start();
                            count.await();

                            Process exec = runtime.exec(dom);
                            if (exec.waitFor() == 0) {
                                FileUtil.del(videopath);
                                FileUtil.del(audiopath);
                                scheduledExecutorService.shutdownNow();
                                threadDown.downSize.set(0);
                                threadDown.prevSize = 0;
                                //   System.out.println("[" + name + "]-[" + namez + "]");
                                // System.out.println("[" + name + "]-[" + namez + "]");
                            } else {
                                scheduledExecutorService.shutdownNow();
                                threadDown.downSize.set(0);
                                threadDown.prevSize = 0;
                                System.out.println("合并失败:[" + name + "]-[" + namez + "]");
                            }
                        } else {
                            System.out.println("[" + name + "]-[" + namez + "]");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        runtime.addShutdownHook(new Thread(() -> {
            new File(tempPath + "\\temp").delete();
           /* for (File listFile : Objects.requireNonNull(file.listFiles())) {
                if (listFile.toString().endsWith(".temp")) {
                    listFile.delete();
                }
            }*/
        }));
    }

    //获取收藏夹当内容
    public String gainVideoPath() {
        String up_mid = Props.getStr("up_mid");
        String fid = "";
        String datapath = null;
        try {
            String s = httpUtil.get("https://api.bilibili.com/x/v3/fav/folder/created/list-all?up_mid=" + up_mid);
            JsonNode jsonNode1 = objectMapper.readValue(s, JsonNode.class).get("data").get("list");
            for (JsonNode jsonNode : jsonNode1) {
                if (jsonNode.get("title").asText().equals("下载")) {
                    fid = jsonNode.get("id").asText();
                }
            }
            String favoriteurl = "https://api.bilibili.com/x/v3/fav/resource/list?media_id=" + fid + "&pn=1&ps=20" +
                    "&keyword" +
                    "=&order" +
                    "=mtime" +
                    "&type=0&tid=0&platform=web";
            String json = httpUtil.get(favoriteurl);
            JsonNode jsonNode = objectMapper.readValue(json, JsonNode.class);
            JsonNode data = jsonNode.get("data").get("medias");
            List<Map<String, String>> dataJsonNode = new ArrayList<Map<String, String>>();
            for (int i = 0; i < data.size(); i++) {
                JsonNode medias = data.path(i);

                Map<String, String> datamap = new HashMap<String, String>();
                datamap.put("title", medias.get("title").asText());
                datamap.put("id", medias.get("id").asText());
                datamap.put("bvid", medias.get("bvid").asText());
                datamap.put("name", medias.get("upper").get("name").asText());
                dataJsonNode.add(datamap);
            }
            datapath = objectMapper.writeValueAsString(dataJsonNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println("获取收藏夹json：\n" + favoriteurl + "\n" + datapath + "\n");
        return datapath;
    }

    //给定一个网址，即可以获取其中所有视频
    public List<List<Map<String, String>>> parseVideoPath(String bvid) {
        List<List<Map<String, String>>> mapList = new ArrayList<List<Map<String, String>>>();
        String json = null;
        try {
            // String url1="https://api.bilibili.com/x/player/pagelist?bvid=" + bvid + "&jsonp=jsonp";
            //判断是否为合集
            String url1 = "https://www.bilibili.com/video/" + bvid;
            String s1 = httpUtil.get(url1);
            Document parse = Jsoup.parse(s1);
            Elements select = parse.select("#app > div.video-container-v1 > div.right-container" +
                    ".is-in-large-ab > div > div.base-video-sections-v1 > div.video-sections-head > div" +
                    ".video-sections-head_second-line > button");
            if (!select.text().equals("")) {
                String name = parse.select("#app > div.video-container-v1 > div.right-container.is-in-large-ab >" +
                        " div " +
                        "> div" +
                        ".base-video-sections-v1 > div.video-sections-head > div.video-sections-head_first-line > div" +
                        ".first-line-left > a").text();

                String[] hrefs = parse.select("#app > div.video-container-v1 > div.right-container.is-in-large-ab >" +
                        " " +
                        "div > div" +
                        ".up-panel-container > div.up-info-container > div.up-info--left > div > a").attr("href").split("/");
                String mid = hrefs[hrefs.length - 1];
                int total;
                int page = 1;
                do {
                    String s =
                            httpUtil.get("https://api.bilibili.com/x/polymer/web-space/seasons_series_list?mid=" + mid +
                                    "&page_num=" + page + "&page_size=20");
                    JsonNode jsonNode = objectMapper.readValue(s, JsonNode.class);
                    JsonNode jsonNode1 = jsonNode.get("data").get("items_lists");
                    total = jsonNode1.get("page").get("total").intValue();
                    JsonNode seasons_list = jsonNode1.get("seasons_list");
                    for (JsonNode node : seasons_list) {
                        JsonNode meta = node.get("meta");
                        String name1 = meta.get("name").asText().replaceAll("合集·", "");
                        if (name.equals(name1)) {
                            int season_id = meta.get("season_id").intValue();
                            int page2 = 1;
                            int total2;
                            do {
                                String s2 = httpUtil.get("https://api.bilibili" +
                                        ".com/x/polymer/web-space/seasons_archives_list?mid" +
                                        "=" + mid + "&season_id=" + season_id + "&sort_reverse=false&page_num=" + page2 +
                                        "&page_size" +
                                        "=100");
                                JsonNode jsonNode2 = objectMapper.readValue(s2, JsonNode.class);
                                total2 = jsonNode2.get("data").get("page").get("total").intValue();
                                JsonNode archives = jsonNode2.get("data").get("archives");
                                for (JsonNode archive : archives) {
                                    String bvid1 = archive.get("bvid").asText();
                                    mapList.add(this.VideoPath(bvid1));
                                }
                                page2++;
                            } while (total2 >= 100);
                            break;
                        }
                    }
                    page++;
                } while (total >= 20);
            } else {
                mapList.add(this.VideoPath(bvid));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapList;
    }

    boolean b = true;

    public List<Map<String, String>> VideoPath(String bvid) {
        //httpUtils.setReqHeaders(headers);
        String url1 = "https://api.bilibili.com/x/player/pagelist?bvid=" + bvid + "&jsonp=jsonp";
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        try {
            String s3 = httpUtil.get(url1);
            JsonNode jsonNode = objectMapper.readValue(s3, JsonNode.class);
            JsonNode data = jsonNode.get("data");

            for (int i = 0; i < data.size(); i++) {
                String cid = data.get(i).get("cid").asText();
                String url2 = "https://api.bilibili.com/x/player/playurl?bvid=" + bvid + "&cid=" + cid + "&qn=80" +
                        "&fnver=0" +
                        "&fnval" +
                        "=2000&otype=json";
                String s = httpUtil.get(url2);
                JsonNode jsonNode1 = objectMapper.readValue(s, JsonNode.class);
                JsonNode data1 = jsonNode1.get("data");
                if (b) {
                    int quality = data1.get("quality").intValue();
                    if (quality < 80) {
                        b = false;
                        int quality2 = data1.get("support_formats").get(0).get("quality").intValue();
                        if (quality < quality2) {
                            System.out.println("当前不是最高画质,手动登入获取最新Cookie");
                            headers.remove(0);
                            String userName = Props.getStr("userName");
                            String passWord = Props.getStr("passWord");
                            StringBuilder login = login(userName, passWord);
                            headers.add(new BasicHeader("Cookie", login));
                            httpUtil.setHeaders(headers);

                            i--;

                            continue;
                        }
                    } else {
                        b = false;
                    }
                }
                JsonNode jsonNode2 = data1.get("dash").get("video").get(0);
                JsonNode jsonNode3 = data1.get("dash").get("audio").get(0);
                // String s1 = data1.get("dash").get("video").get(0).get("id").asText();
                Map<String, String> map = new HashMap<String, String>();
                //page
                map.put("part", data.get(i).get("page").asText());
                //name
                map.put("name", data.get(i).get("part").asText());
                //视频
                map.put("video", jsonNode2.get("base_url").asText());
                //音频
                map.put("audio", jsonNode3.get("base_url").asText());
                //来源
                //  map.put("referer", url1);
                mapList.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapList;
    }

    public StringBuilder login(String username, String password) {
        StringBuilder cookie = new StringBuilder();
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            // 设置要发送的 Cookie
            // 设置拦截规则
            BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions()
                    .setViewportSize(640, 480)
                    .setScreenSize(640, 480)
                    .setIgnoreHTTPSErrors(true));
            // 禁止图片加载
            // browserContext.route("**/*.{png,jpg,jpeg,webp,avif,svg}", Route::abort);
            Page page = browserContext.newPage();
            page.navigate("https://www.bilibili.com/");
            page.evaluate("document.querySelector(\"#i_cecream > div.bili-feed4 > div.bili-header.large-header > div" +
                    ".bili-header__bar > ul.right-entry > li:nth-child(1) > li > div > div > span\").click()");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            page.locator("body > div.bili-mini-mask > div > div.bili-mini-login-right-wp > div.login-pwd-wp > form > " +
                    "div:nth-child(1) > input[type=text]").fill(username);

            page.locator("body > div.bili-mini-mask > div > div.bili-mini-login-right-wp > div.login-pwd-wp > form > " +
                    "div:nth-child(3) > input[type=password]").fill(password);

            page.evaluate("document.querySelector(\"body > div.bili-mini-mask > div > div.bili-mini-login-right-wp > " +
                    "div.login-pwd-wp > div.btn_wp > div.btn_primary\").click()");

            page.waitForSelector("body > div.bili-mini-mask > div.all-icon.play._nO",
                    new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN));
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<Cookie> cookies = browserContext.cookies();
            for (Cookie cookiex : cookies) {
                cookie.append(cookiex.name).append("=").append(cookiex.value).append(";");
            }
        }

        Props.in_file(cookie.toString());
        return cookie;
    }


}
