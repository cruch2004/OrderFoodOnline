package cn.gavinlang.orderfoodonline.utils;

//target_Class:存放常量 目的是集中管理项目中可能会用到的静态常量 避免硬编码和重复定义
public class Constant {
    /**
     * "http://10.0.2.2:8080/order/";主机IP地址
     *    "shop_list_data.json";店铺列表JSON文件名
     *    "img/shop/";店铺LOGO图片文件夹
     */

    public static final String SHOP_INFO_URL = "http://10.0.2.2:8080/order/shop_list_data.json";//店铺JSON信息路径地址
    public static final String SHOP_LOGO_URL = "http://10.0.2.2:8080/order/img/shop/";//店铺图像父路径
    public static final String FOOD_LOGO_URL = "http://10.0.2.2:8080/order/img/food/";//商品图像父路径
}
