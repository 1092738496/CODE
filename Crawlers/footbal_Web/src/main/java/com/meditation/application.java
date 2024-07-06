package com.meditation;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Route;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2024/7/6 14:54
 * @description:
 */

@SpringBootApplication
@Configuration
public class application {

    @Value("#{${headers:{}}}")
    private Map<String, String> HeadersMap;

    @Value("${Playwright.Headless}")
    private String Headless;

    @Bean
    public CloseableHttpClient ClientBuilder() {

        HttpClientBuilder httpClientBuilder = null;
        try {
            //System.setProperty("javax.net.debug", "all");
            //证书全部信任 不做身份鉴定
            StringBuilder stringBuffer = new StringBuilder();

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
            PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager(registry);
            pool.setMaxTotal(32);
            pool.setDefaultMaxPerRoute(32);
            //连接参数
            RequestConfig requestConfig = RequestConfig.custom()
                    .setResponseTimeout(10, TimeUnit.SECONDS)
                    .setConnectTimeout(10, TimeUnit.SECONDS)
                    .setConnectionRequestTimeout(20, TimeUnit.SECONDS)
                    .build();

            httpClientBuilder = HttpClients.custom();
            httpClientBuilder.setConnectionManager(pool);
            // .setDefaultRequestConfig(requestConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Header> headers = new ArrayList<>();
        for (String key : HeadersMap.keySet()) {
            headers.add(new BasicHeader(key, HeadersMap.get(key)));

        }
        return httpClientBuilder.setDefaultHeaders(headers).build();
    }

    @Bean
    public Playwright PlaywrightClient() {
        return Playwright.create();
    }

    @Bean
    public Page page(Playwright playwright) {
        boolean isHeadless = Boolean.parseBoolean(Headless);
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless).
                setArgs(Arrays.asList("--enable-gpu", "--disable-software-compositing"))
        );
        BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(640, 480)
                .setScreenSize(640, 480)
                .setIgnoreHTTPSErrors(true));
        // 禁止图片加载
        browserContext.route("**/*.{png,jpg,jpeg,webp,avif,svg}", Route::abort);
        Page page = browserContext.newPage();
        return page;
    }

    public static void main(String[] args) {
        SpringApplication.run(application.class, args);
    }
}
