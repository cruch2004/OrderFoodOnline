package cn.gavinlang.orderfoodonline.bean;

import java.io.Serializable;
import java.math.BigDecimal;

// 通过 Intent 传递复杂对象（比如 ShopBean 和 FoodBean 这种自定义的 Java 对象）时
// 必须确保这些对象实现了 Serializable 或 Parcelable 接口
public class FoodBean implements Serializable {

    private int foodId;         //菜的id
    private String foodName;   //菜的名称
    private String taste; //主要原料
    private int saleNum;       //月售量
    private BigDecimal price; //价格
    private int count;         //添加到购物车中的数量
    private String foodPic;   //菜的图片

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFoodPic() {
        return foodPic;
    }

    public void setFoodPic(String foodPic) {
        this.foodPic = foodPic;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
