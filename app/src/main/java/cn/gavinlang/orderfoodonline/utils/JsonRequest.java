package cn.gavinlang.orderfoodonline.utils;

import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonRequest {
    //target_Class: JsonRequest 类用来处理 HTTP 请求并获取 JSON 数据。获取到的数据将传递给 JsonParse 类进行解析

    /**
     * 通过 HTTP GET 请求从指定 URL 获取 JSON 数据的功能
     * 使用了 HttpURLConnection 进行网络请求
     * InputStreamReader 读取响应数据,并通过 StringBuilder 拼接最终的 JSON 字符串
     * 如果请求过程中发生异常,通过 Log.e 打印错误信息
     *
     * @param urlString 要请求的URL地址
     * @return 返回一个 String 类型的 JSON 数据
     */
    public static String fetchShopListJson(String urlString) {

        Log.d("Thread", "Request URL: " + urlString);

        HttpURLConnection urlConnection = null;//与指定 URL 的 HTTP 连接
        StringBuilder result = new StringBuilder();//拼接从网络中读取的数据

        try {
            URL url = new URL(urlString);//将urlString 转换为URL对象

            //打开与指定URL的链接，并转换为HttpURLConnection对象
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");//设置请求方法为GET表示发起一个GET请求
            urlConnection.setConnectTimeout(5000);//设置连接超时为5秒
            urlConnection.setReadTimeout(5000);//设置读取超时为5秒
            urlConnection.setDoInput(true);//设置该连接可以从输入流读取数据

            InputStream inputStream = urlConnection.getInputStream();//获取从连接返回的输入流,用于读取响应数据
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            ;//将输入流对象转换为字符流对象,方便按照字符读取响应数据

            int data;//存储读取的数据

            while ((data = reader.read()) != -1) {//逐个字符读取输入流,每次读取存储在data中
                result.append((char) data);
            }
        } catch (Exception e) {
            Log.e("JsonRequest", "Error fetching JSON", e);
        }finally {
            if (urlConnection!=null){//HTTP已建立连接
                urlConnection.disconnect();//断开与URL的连接，释放资源
            }
        }
        return result.toString();//返回拼接好的字符串
    }
}
