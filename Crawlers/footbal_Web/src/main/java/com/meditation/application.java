package com.meditation;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Route;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.nio.ssl.TlsStrategy;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.hc.core5.util.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2024/7/6 14:54
 * @description:
 */

@SpringBootApplication
public class application {
    private static final Logger logger = LoggerFactory.getLogger(application.class);

    @Value("#{${headers:{}}}")
    private Map<String, String> HeadersMap;

    @Value("${Playwright.Headless}")
    private String Headless;

    @Value("${Clientpool.MaxTotal}")
    private int MaxTotal;

    @Value("${Threadpool.codepoolsize}")
    private int codepoolsize;

    @Value("${Threadpool.maximunpoolsize}")
    private int maximunpoolsize;

    @Bean
    public CorsFilter corsFilter() {
        // 创建CorsConfiguration对象，用于配置CORS规则
        CorsConfiguration config = new CorsConfiguration();

        // 设置允许的源列表，这里使用通配符"*"表示允许任何源
        // 实际生产环境应根据需求设置具体源地址，例如"http://example.com"
        config.addAllowedOriginPattern("*");

        // 设置允许的HTTP方法
        config.addAllowedMethod("*");

        // 设置允许的HTTP请求头
        config.addAllowedHeader("*");

        // 是否允许发送Cookie
        config.setAllowCredentials(true);

        // 设置暴露的HTTP响应头
        config.addExposedHeader("*");

        // 配置源，添加映射路径，对所有路径应用CORS配置
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        // 返回CorsFilter实例
        return new CorsFilter(source);
    }

    @Bean
    public CloseableHttpClient ClientBuilder() {
        logger.info("初始化Httpclient...");
        HttpClientBuilder httpClientBuilder = null;
        try {
            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            SSLContext sslContext = sslContextBuilder.loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    new String[]{"SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"}, null,
                    NoopHostnameVerifier.INSTANCE);
            //连接池
            PoolingHttpClientConnectionManager pool =
                    PoolingHttpClientConnectionManagerBuilder.create().setSSLSocketFactory(sslConnectionSocketFactory).build();
            pool.setMaxTotal(MaxTotal);
            pool.setDefaultMaxPerRoute(MaxTotal);
            logger.info("当前客户端连接次数:" + MaxTotal);

            //连接参数
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(3, TimeUnit.SECONDS)
                    .setResponseTimeout(3, TimeUnit.SECONDS)
                    .build();

            DefaultHttpRequestRetryStrategy retryStrategy = new DefaultHttpRequestRetryStrategy(5,
                    TimeValue.ofSeconds(200));
            httpClientBuilder = HttpClients.custom().setConnectionManager(pool).setDefaultRequestConfig(config)
                    .setRetryStrategy(retryStrategy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Header> headers = new ArrayList<>();
        for (String key : HeadersMap.keySet()) {
            headers.add(new BasicHeader(key, HeadersMap.get(key)));
        }
        logger.info("Httpclient初始化完成");
        return httpClientBuilder.setDefaultHeaders(headers).build();
    }

    //异步client
    @Bean
    public CloseableHttpAsyncClient AsyncClient() {
        CloseableHttpAsyncClient AsyncClient = null;
        try {

            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            TlsStrategy tlsStrategy = ClientTlsStrategyBuilder.create().setSslContext(sslContext).build();

            PoolingAsyncClientConnectionManager pool = PoolingAsyncClientConnectionManagerBuilder.create()
                    .setMaxConnTotal(200)
                    .setMaxConnPerRoute(100)
                    .setTlsStrategy(tlsStrategy)
                    .build();

            List<Header> headers = new ArrayList<>();
            for (String key : HeadersMap.keySet()) {
                headers.add(new BasicHeader(key, HeadersMap.get(key)));
            }

            IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                    .setSndBufSize(100 * 1024 * 1024)
                    .setIoThreadCount(100)
                    .build();
            RequestConfig.custom();
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(3, TimeUnit.SECONDS)
                    .setResponseTimeout(3, TimeUnit.SECONDS)
                    .build();

            DefaultHttpRequestRetryStrategy retryStrategy = new DefaultHttpRequestRetryStrategy(5,
                    TimeValue.ofSeconds(200));

            AsyncClient = HttpAsyncClients.custom()
                    .setConnectionManager(pool)
                    .setIOReactorConfig(ioReactorConfig)
                    .setDefaultHeaders(headers)
                    .setDefaultRequestConfig(config)
                    .setRetryStrategy(retryStrategy)
                    .build();
            AsyncClient.start();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
        return AsyncClient;
    }

    @Bean
    public ThreadPoolExecutor pool() {
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

        return new ThreadPoolExecutor(codepoolsize, maximunpoolsize, 60, TimeUnit.SECONDS, taskQueue);
    }


    @Bean("BrowserContext")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public BrowserContext browserContext() {
        logger.info("初始化playwright...");
        Playwright playwright = Playwright.create();
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
        /* Page page = browserContext.newPage();*/
        logger.info("playwright初始化完成");
        return browserContext;
    }

    @Bean("Page")
    public Page page(BrowserContext browserContext) {

        return browserContext.newPage();
    }


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(application.class, args);

    }
}
