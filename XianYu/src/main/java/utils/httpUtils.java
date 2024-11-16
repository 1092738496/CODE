package utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.setting.dialect.Props;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;

import javax.net.ssl.SSLContext;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: Andy
 * @time: 2023-9-4 16:07
 */

public class httpUtils {
    private CloseableHttpClient httpclient;
    private static HttpClientBuilder httpClientBuilder = null;
    private static PoolingHttpClientConnectionManager pool;

    static {
        try {
            System.setProperty("java.net.useSystemProxies", "true");
            //证书全部信任 不做身份鉴定
            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            SSLContext sslContext = sslContextBuilder.loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            //使用谷歌浏览器查看网页使用的是哪一个SSL协议，SSLv2Hello需要删掉，不然会报握手失败，具体原因还不知道
            SSLConnectionSocketFactory sslConnectionSocketFactory =
                    new SSLConnectionSocketFactory(sslContext,
                            new String[]{"SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"}, null,
                            NoopHostnameVerifier.INSTANCE);

            // SSLConnectionSocketFactory  sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslConnectionSocketFactory)
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .build();
            //连接池
            pool = new PoolingHttpClientConnectionManager(registry);
            pool.setMaxTotal(32);
            pool.setDefaultMaxPerRoute(32);
            //连接参数
            RequestConfig requestConfig = RequestConfig.custom()
                    .setResponseTimeout(10, TimeUnit.SECONDS)
                    .setConnectTimeout(5, TimeUnit.SECONDS)
                    .setConnectionRequestTimeout(10, TimeUnit.SECONDS)
                    .build();

            httpClientBuilder = HttpClients.custom().setProxy(new HttpHost("localhost",7890));
            httpClientBuilder
                    .setConnectionManager(pool);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public httpUtils(List<Header> headers) {
        httpclient = httpClientBuilder.setDefaultHeaders(headers)
                .setProxy(new HttpHost("localhost",7890))
                .build();
    }


    public void download(String url, String path, String... start_end) throws IOException {
        HttpGet httpGet = new HttpGet(url);

        String[] clone = start_end.clone();
        if (clone.length == 2) {
            String x = clone[0];
            String y = clone[1];
            if (y.equals("0")) {
                y = "";
            }
            String contentRange =
                    "bytes=" + x + "-" + y;
            System.out.println(contentRange);
            httpGet.setHeader("Range", contentRange);
        }
        CloseableHttpResponse response = null;

        response = httpclient.execute(httpGet);

        InputStream content = response.getEntity().getContent();
        BufferedInputStream bis = new BufferedInputStream(content);
        BufferedOutputStream outputStream = FileUtil.getOutputStream(path);
        byte[] bytes = new byte[1024];
        int len = -1;
        while ((len = bis.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
        outputStream.close();
        bis.close();
        content.close();

    }

    public String get(String url) {
        String html = "";
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            html = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return html;
    }

    public String post(String url, List<NameValuePair> formParams) {
        String html = "";
        HttpPost httpost = new HttpPost(url);

        httpost.setEntity(new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8));

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpost);
            HttpEntity entity = response.getEntity();
            html = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return html;
    }

    public String doPost(String url, Map<String, Object> map) {
        String strResult = "";
        // 1. 获取默认的client实例
        CloseableHttpClient client = HttpClients.createDefault();
        // 2. 创建httppost实例
        HttpPost httpPost = new HttpPost(url);
        // 3. 创建参数队列（键值对列表）
        List<NameValuePair> paramPairs = new ArrayList<>();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            Object val = map.get(key);
            paramPairs.add(new BasicNameValuePair(key, val.toString()));
        }
        UrlEncodedFormEntity entity;
        try {
            // 4. 将参数设置到entity对象中
            entity = new UrlEncodedFormEntity(paramPairs);
            // 5. 将entity对象设置到httppost对象中
            httpPost.setEntity(entity);
            httpPost.setHeader("Cookie", "cookie2=103cf0d127438c3bad1af93bb983daf4; _samesite_flag_=true; " +
                    "t=1d7c57ad5ab1ff5591169aac262b0ba5; _tb_token_=f5f6e558859e8; " +
                    "sgcookie=E100ZSYKZDiSroTEjDWAZL9W0AIKyK6QRJL50%2BnfdYxgk%2BWXv8YTwGYWMJymfcIiepvVfD6L3P3XU" +
                    "%2FRD3bkUYMV2Q2Dv31xXJVqTgegtdg7Wunk%3D; csg=dfda9921; unb=2456556960; " +
                    "mtop_partitioned_detect=1; _m_h5_tk=74c789721156a03d35df0bec08f6c441_1730316593547; " +
                    "_m_h5_tk_enc=54c458aa0924a5a71c64993f6749db9e; " +
                    "isg=BHx8i8cQNy00YAPxKlCX34cRTRoudSCfWtHcpFb9sWdKIRyrfobeL_KYBUlZaVj3");
            // 6. 发送请求并回去响应
            CloseableHttpResponse resp = client.execute(httpPost);
            try {
                // 7. 获取响应entity
                HttpEntity respEntity = resp.getEntity();
                strResult = EntityUtils.toString(respEntity, "UTF-8");
            } finally {
                // 9. 关闭响应对象
                resp.close();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            // 10. 关闭连接，释放资源
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strResult;
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

    public void fileMerge(String inputPath, String filetempPath, String name, String type, String fileLength,
                          int fileNumber) {
        RandomAccessFile write = null;
        try {
            byte[] bytes = new byte[1024 * 1024];
            write = new RandomAccessFile(inputPath + "\\" + name + "." + type, "rw");
            for (int i = 1; i <= fileNumber; i++) {
                String filepath = filetempPath + name + "_" + i + ".temp";
                RandomAccessFile read = new RandomAccessFile(filepath, "r");
                int len = -1;
                while ((len = read.read(bytes)) != -1) {
                    write.write(bytes, 0, len);
                }
                read.close();
                FileUtil.del(filepath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert write != null;
                write.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getStr(String key) {
        /*String property = System.getProperty("user.dir");
        cn.hutool.setting.dialect.Props props = new cn.hutool.setting.dialect.Props(property + "\\Settings
        .properties");*/

        String path = Props.class.getClassLoader().getResource("Settings.properties").getPath();
        Props props = new Props(path);
        return props.getStr(key);
    }

    public static void in_file(String cookie) {
        /*String Path = System.getProperty("user.dir")+ "\\Settings.properties";
        cn.hutool.setting.dialect.Props props = new cn.hutool.setting.dialect.Props(Path);*/

        String path = Props.class.getClassLoader().getResource("Settings.properties").getPath();
        Props props = new Props(path);

        Properties properties = props.toProperties();
        properties.setProperty("cookie", cookie);
        try {
            properties.store(new FileOutputStream(path), null); // 【存储的是汉字的unicode编码】
            //properties.store(new FileOutputStream(Path), null); // 【存储的是汉字的unicode编码】
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
