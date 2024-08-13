import org.apache.commons.compress.utils.Lists;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @time: 2024/5/2 18:53
 * @description:
 */

public class test {
    @Test
    public void test() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"));

        httpUtils httpUtils = new httpUtils(headers);
        String s = null;
        try {
            s = httpUtils.post("https://www.okooo.com/soccer/match/1255125/okoooexponent/xmlData/", "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(s);
    }

    @Test
    public void test1() {


        try {
            for (int i = 0; i < 200; i++) {

                CloseableHttpClient httpClient = HttpClients.custom()
                        .setProxy(new org.apache.hc.core5.http.HttpHost("127.0.0.1",7890))
                        .build();

                HttpPost httpPost = new HttpPost("https://www.okooo.com/soccer/match/1255125/okoooexponent/xmlData/");

                httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                        "(KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
                List<NameValuePair> params = Lists.newArrayList();
                params.add(new BasicNameValuePair("type", "okoooexponent"));
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                httpPost.setHeader("Cookie",
                        "acw_sc__v3=663382251fe5673567f6934e12cbc20f4a5078b8");
                CloseableHttpResponse response = null;
                response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                //System.out.println(pool.getTotalStats().toString());
                String html = EntityUtils.toString(entity, "utf-8");
                System.out.println(html);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
