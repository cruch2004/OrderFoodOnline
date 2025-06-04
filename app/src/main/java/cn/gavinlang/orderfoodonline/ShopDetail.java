package cn.gavinlang.orderfoodonline;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import cn.gavinlang.orderfoodonline.adapter.CartAdapter;
import cn.gavinlang.orderfoodonline.adapter.FoodAdapter;
import cn.gavinlang.orderfoodonline.bean.FoodBean;
import cn.gavinlang.orderfoodonline.bean.ShopBean;

public class ShopDetail extends AppCompatActivity {
    private static final String TAG = "ShopDetail";
    private RecyclerView menuRecyclerView, cartRecyclerView;
    private FoodAdapter foodAdapter;
    private CartAdapter cartAdapter;
    private RelativeLayout layoutCart;
    private ShopBean shop;  //用来保存从Intent对象中传递过来的店铺信息
    private TextView shopName, deliveryTime, shopNotice,
            tvBack, tvEmptyCart, tvDeliveryAmount,
            tvSettleAccounts, tvMoney, tvDistributionCost,
            tvFoodCount;
    private ImageView shopImage, ivShopCartImage;
    private final List<FoodBean> shoppingCartList = new ArrayList<>();//购物车商品列表信息
    private BigDecimal deliveryFees = BigDecimal.ZERO;//配送费

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        initViews();
        setupRecyclerViews();
        initShopData();
        setupListeners();
        updateCartView();
    }

    private void initViews() {
        shopName = findViewById(R.id.tv_shop_name);//店铺名
        deliveryTime = findViewById(R.id.tv_time);//店铺配送时间
        shopNotice = findViewById(R.id.tv_notice);//店铺公告
        tvBack = findViewById(R.id.tv_back);//返回按钮
        shopImage = findViewById(R.id.iv_shop_pic);//店铺图片
        layoutCart = findViewById(R.id.rl_cart_list);//购物车区域布局
        tvEmptyCart = findViewById(R.id.tv_clear);//清空购物车按钮
        tvDeliveryAmount = findViewById(R.id.tv_not_enough);//购物车底部 “最低起送金额”
        tvSettleAccounts = findViewById(R.id.tv_settle_accounts);//购物车底部 “去结算" 图片按钮
        tvMoney = findViewById(R.id.tv_money);//购物车底部 “商品的总价”
        tvDistributionCost = findViewById(R.id.tv_distribution_cost);//购物车底部 “另需配送费”
        tvFoodCount = findViewById(R.id.tv_count);//购物车图标右上方显示加入购物车中的商品数量
        ivShopCartImage = findViewById(R.id.iv_shop_car);//购物车图标

        menuRecyclerView = findViewById(R.id.rv_shop_food_list);
        cartRecyclerView = findViewById(R.id.rv_shopping_cart_list);
    }

    private void setupRecyclerViews() {
        // 设置商品菜单列表 RecyclerView
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 设置购物车列表 RecyclerView
        cartAdapter = new CartAdapter(shoppingCartList, this);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(cartAdapter);
    }

    private void initShopData() {
        // 获取intent对象传递的数据
        shop = (ShopBean) getIntent().getSerializableExtra("ShopBean_data");
        String shopLogoUrl = getIntent().getStringExtra("shopLogoUrl");
        if (shop != null) {
            shopName.setText(shop.getShopName());
            deliveryTime.setText(shop.getTime());
            shopNotice.setText(shop.getShopNotice());
            // 加载图片
            if (shopLogoUrl != null && !shopLogoUrl.isEmpty()) {
                Glide.with(this)
                        .load(shopLogoUrl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(shopImage);
            }
            // 初始化商品菜单适配器
            foodAdapter = new FoodAdapter(this, shop);
            menuRecyclerView.setAdapter(foodAdapter);
        }
    }

    private void setupListeners() {

        //购物车图标的点击事件
        ivShopCartImage.setOnClickListener(v -> {
            if (layoutCart.getVisibility() == View.GONE) {
                layoutCart.setVisibility(View.VISIBLE);
            } else if (layoutCart.getVisibility() == View.VISIBLE) {
                layoutCart.setVisibility(View.GONE);
            }
        });

        //“去结算” 按钮点击事件
        tvSettleAccounts.setOnClickListener(v -> {
            Intent intent = new Intent(ShopDetail.this, OrderActivity.class);
            // 注意这里强转成 Serializable 类型
            intent.putExtra("shoppingCartList", (Serializable) shoppingCartList);
            intent.putExtra("totalPrice", calculateTotalPrice());
            intent.putExtra("deliveryFees", deliveryFees);
            startActivity(intent);
            Log.i(TAG, "-----------------tvSettleAccounts: 传递数据过去" + shoppingCartList + "-----------------");
        });

        //清空购物车按钮点击事件
        tvEmptyCart.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("确认清空")
                    .setMessage("你确定要清空购物车吗？")
                    .setPositiveButton("清空", (dialog, which) -> {
                        shoppingCartList.clear();
                        cartAdapter.notifyDataSetChanged();
                        layoutCart.setVisibility(View.GONE);
                        Log.d(TAG, "购物车已清空");
                        // 重新更新购物车视图
                        updateCartView();
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });
        // 返回按钮
        tvBack.setOnClickListener(v -> {
            finish();
        });
        // 加入购物车按钮回调机制
        foodAdapter.setOnAddToCartClickListener(foodBean -> {
            addToCart(foodBean);
        });
        // 为购物车中的每一项商品的 "+" 和 "-" 按钮设置点击事件
        cartAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onIncreaseClick(int position) {
                increaseItemCount(position);
            }

            @Override
            public void onDecreaseClick(int position) {
                decreaseItemCount(position);
            }
        });
    }

    // 增加商品数量方法
    private void increaseItemCount(int position) {
        if (position < 0 || position >= shoppingCartList.size()) {
            return; // 如果索引无效，直接返回
        }
        FoodBean food = shoppingCartList.get(position);
        int currentCount = food.getCount();
        food.setCount(currentCount + 1);
        cartAdapter.notifyItemChanged(position);
        updateCartView();
    }

    // 减少商品数量方法
    private void decreaseItemCount(int position) {
        if (position < 0 || position >= shoppingCartList.size()) {
            return; // 如果索引无效，直接返回
        }
        FoodBean food = shoppingCartList.get(position);
        int currentCount = food.getCount();
        if (currentCount > 0) {
            food.setCount(currentCount - 1);
            if (food.getCount() == 0) {
                shoppingCartList.remove(position);
                cartAdapter.notifyDataSetChanged();
            } else {
                cartAdapter.notifyItemChanged(position);
            }
            updateCartView();
        }
    }

    private void addToCart(FoodBean food) {
        // 检查购物车中是否已存在相同的 food（通过 foodId 判断）
        boolean exists = false;
        for (FoodBean foodBean : shoppingCartList) {
            if (foodBean.getFoodId() == food.getFoodId()) {
                exists = true;
                foodBean.setCount(foodBean.getCount() + 1);
                break;
            }
        }
        if (!exists) {
            food.setCount(1);
            shoppingCartList.add(food);
        }
        cartAdapter.notifyDataSetChanged(); //通知购物车适配器数据更新（不管是新增还是修改）
        updateCartView();// 重新更新购物车视图

        // 显示购物车界面
        if (layoutCart.getVisibility() == View.GONE) {
            layoutCart.setVisibility(View.VISIBLE);
        }
    }

    // 更新底部购物车视图
    private void updateCartView() {
        int foodAllCount = 0;//计算购物车中所有商品添加的数量
        // 如果购物车为空，显示起送金额提示
        if (shoppingCartList.isEmpty()) {
            BigDecimal shopOfferPrice = shop.getOfferPrice();
            String formattedPrice = "¥"
                    + shopOfferPrice.setScale(0, RoundingMode.HALF_UP).toPlainString()
                    + "起送";
            tvMoney.setText("未选购商品");
            tvDeliveryAmount.setText(formattedPrice);  //设置起送金额显示
            tvFoodCount.setVisibility(View.GONE);//隐藏购物车图标右上方数字显示
            tvDeliveryAmount.setVisibility(View.VISIBLE);  //显示起送金额提示
            tvSettleAccounts.setVisibility(View.GONE);  //隐藏“去结算”按钮
            tvDistributionCost.setVisibility(View.GONE);//隐藏店铺另需配送费信息
            ivShopCartImage.setImageResource(R.drawable.shop_car_empty);//改变购物车图标为空的状态
            if (layoutCart.getVisibility() == View.VISIBLE) {
                layoutCart.setVisibility(View.GONE);
            }
        } else {
            for (FoodBean food : shoppingCartList) {
                int count = food.getCount();  // 商品数量
                foodAllCount += count;
            }
            //显示商品总价信息
            String total = "¥" + calculateTotalPrice();
            //最低配送费
            deliveryFees = shop.getDistributionCost();
            String lowestDistributionCost = "另需配送费¥" + deliveryFees;
            tvDistributionCost.setText(lowestDistributionCost);
            tvDistributionCost.setVisibility(View.VISIBLE);
            tvMoney.setText(total);
            //显示所有商品的数量
            tvFoodCount.setText(String.valueOf(foodAllCount));
            tvFoodCount.setVisibility(View.VISIBLE);
            // 更换购物车图标
            ivShopCartImage.setImageResource(R.drawable.shop_car);

            // 判断购物车是否满足起送价格
            int result = calculateTotalPrice().compareTo(shop.getOfferPrice());
            if (result >= 0) {  // 满足起送价格
                tvDeliveryAmount.setVisibility(View.GONE);  // 隐藏起送金额提示
                tvSettleAccounts.setVisibility(View.VISIBLE);  // 显示“去结算”按钮
            } else {  //不满足起送价格
                tvSettleAccounts.setVisibility(View.GONE);  // 隐藏 “去结算” 按钮
                BigDecimal difference = shop.getOfferPrice().subtract(calculateTotalPrice());//还差多少起送
                String formattedPrice = "还差 ¥" + difference + "起送";
                tvDeliveryAmount.setText(formattedPrice);  // 设置还差多少起送
                tvDeliveryAmount.setVisibility(View.VISIBLE);  // 显示起送金额提示
            }
        }
    }

    private BigDecimal calculateTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        // 计算购物车中商品的总价
        for (FoodBean food : shoppingCartList) {
            BigDecimal price = food.getPrice();  // 商品价格
            int count = food.getCount();  // 商品数量
            BigDecimal itemTotal = price.multiply(new BigDecimal(count));  // 单类商品总价
            totalPrice = totalPrice.add(itemTotal);  // 累加所有商品金额
        }
        return totalPrice;//返回购物车中商品的总价
    }

}