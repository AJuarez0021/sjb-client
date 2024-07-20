package com.work.client.config;

import feign.Request;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedTrustManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 *
 * @author linux
 */
@Configuration
@Slf4j
public class AppConfig {

    private static final int TIMEOUT = 30;

    private final boolean VERIFYING_SSL = false;

    @Value("${jks_path}")
    private String path;

    @Value("${jks_pwd}")
    private String pwd;

    @Value("${jks_alias}")
    private String alias;

    @Value("${jks_file}")
    private String fileName;

    @Bean
    public RestTemplate restTemplate() {
        SSLConnectionSocketFactory sslConFactory = new SSLConnectionSocketFactory(getSSL(VERIFYING_SSL));
        HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder
                .create()
                .setSSLSocketFactory(sslConFactory)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);
        factory.setConnectTimeout(Duration.ofSeconds(TIMEOUT));
        factory.setConnectionRequestTimeout(Duration.ofSeconds(TIMEOUT));
        return new RestTemplate(factory);
    }

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(Duration.ofSeconds(TIMEOUT),
                Duration.ofSeconds(TIMEOUT),
                true);
    }

    private TrustManager[] trustCertificate() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509ExtendedTrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) {

            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }

        }};
        return trustAllCerts;
    }

    private TrustManager[] loadJks()
            throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, URISyntaxException {
        KeyStore store = KeyStore.getInstance("JKS");
        log.info("FileName: {}", fileName);
        URI uri = AppConfig.class.getClassLoader().getResource(path + fileName).toURI();
        store.load(new FileInputStream(new File(uri)), pwd == null ? "".toCharArray() : pwd.toCharArray());
        X509Certificate ca = (X509Certificate) store.getCertificate(alias);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry(Integer.toString(1), ca);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        return tmf.getTrustManagers();
    }

    private SSLContext getSSL(boolean verifyingSsl) {
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            if (verifyingSsl) {
                sc.init(null, loadJks(), new java.security.SecureRandom());
            } else {
                sc.init(null, trustCertificate(), new java.security.SecureRandom());
            }
            return sc;
        } catch (IOException | URISyntaxException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            log.error("Error: ", e);
            return null;
        }
    }
}
