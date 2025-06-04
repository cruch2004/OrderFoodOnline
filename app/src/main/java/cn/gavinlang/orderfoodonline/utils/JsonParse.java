package cn.gavinlang.orderfoodonline.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import cn.gavinlang.orderfoodonline.bean.ShopBean;

public class JsonParse {
    // 单例模式 确保 JsonParse 只会被创建一次
    private static JsonParse instance;//存储唯一的JsonParse实例

    public JsonParse() {
    }

    public static JsonParse getInstance() {

        if (instance == null) {

            instance = new JsonParse();
        }
        return instance;
    }

    public List<ShopBean> getShopList(String json){

        Gson gson = new Gson();//创建Gson对象,用于解析JSON
        Type listType = new TypeToken<List<ShopBean>>(){}.getType();//创建Type对象指定泛型
        //由于泛型擦除,Java 编译时会丢失泛型信息,这里通过 TypeToken 保留泛型结构

        //用 fromJson 方法将 JSON 字符串反序列化为 List<ShopBean> 对象
        return gson.fromJson(json, listType);
    }
}
