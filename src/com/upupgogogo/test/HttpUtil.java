package com.upupgogogo.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by upupgogogo on 2018/3/27.下午3:54
 */
public class HttpUtil {
    public static InputStream doGet(String urlstr) throws IOException {
        URL url= new URL(urlstr);
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        InputStream inputStream= conn.getInputStream();
        return inputStream;
    }
}
