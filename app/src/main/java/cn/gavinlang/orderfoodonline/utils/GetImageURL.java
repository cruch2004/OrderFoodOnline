package cn.gavinlang.orderfoodonline.utils;

public class GetImageURL {
    private static final String SHOP_LOGO_ADDRESS = Constant.SHOP_LOGO_URL;//店铺图像父地址
    private static final String FOOD_LOGO_ADDRESS = Constant.FOOD_LOGO_URL;//商品图像父路径


    public static String extractShopImageNameFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            return "";
        }
        int lastSlashIndex = url.lastIndexOf('/');
        String ImageLastName = ""; // 初始化变量
        if (lastSlashIndex >= 0 && lastSlashIndex < url.length() - 1) {
            ImageLastName = url.substring(lastSlashIndex + 1);
        }
        return SHOP_LOGO_ADDRESS + ImageLastName; // 返回店铺图像的完整路径信息
    }

    public static String extractFoodImageNameFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            return "";
        }
        int lastSlashIndex = url.lastIndexOf('/');
        String ImageLastName = ""; // 初始化变量
        if (lastSlashIndex >= 0 && lastSlashIndex < url.length() - 1) {
            ImageLastName = url.substring(lastSlashIndex + 1);
        }
        return FOOD_LOGO_ADDRESS + ImageLastName; // 返回商品图像的完整路径信息
    }
}
