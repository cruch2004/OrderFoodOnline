package cn.gavinlang.orderfoodonline;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.gavinlang.orderfoodonline.adapter.OrderAdapter;
import cn.gavinlang.orderfoodonline.bean.FoodBean;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = "OrderActivity";
    private OrderAdapter orderAdapter;
    private RecyclerView orderRecyclerView;
    private List<FoodBean> shoppingCartList;
    private TextView tvTitle, tvBack, tvDistributionCost,
            tvCost, tvTotalCost, tvGoPay;
    private EditText etDeliveryAddress;//收货地址
    private BigDecimal totalPrice = BigDecimal.ZERO;//小计
    private BigDecimal deliveryFees = BigDecimal.ZERO;//配送费

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();//绑定控件
        receiveData();//接收数据
        initData();//初始化数据
        setupRecyclerViews();//设置视图
        setupListeners();
    }

    private void setupRecyclerViews() {
        orderAdapter = new OrderAdapter(shoppingCartList, this);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerView.setAdapter(orderAdapter);
    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_title);//订单界面标题
        tvBack = findViewById(R.id.tv_back);
        etDeliveryAddress = findViewById(R.id.et_delivery_address);//收货地址
        tvDistributionCost = findViewById(R.id.tv_distribution_cost);//配送费
        tvCost = findViewById(R.id.tv_cost);//小计
        tvTotalCost = findViewById(R.id.tv_total_cost);//订单总价
        tvGoPay = findViewById(R.id.tv_payment);// “去支付” 按钮

        orderRecyclerView = findViewById(R.id.rv_orderList);
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        tvTitle.setText("订单");
        tvCost.setText("￥" + totalPrice);//设置 “小计” 金额
        tvDistributionCost.setText("￥" + deliveryFees);//设置 “配送费” 金额
        tvTotalCost.setText("￥" + totalPrice.add(deliveryFees));
    }

    private void receiveData() {//专门接收从ShopDetailActivity中传递过来的数据
        shoppingCartList =
                (List<FoodBean>) getIntent().getSerializableExtra("shoppingCartList");
        totalPrice = (BigDecimal) getIntent().getSerializableExtra("totalPrice");
        deliveryFees = (BigDecimal) getIntent().getSerializableExtra("deliveryFees");

        if (shoppingCartList != null) {
            for (FoodBean food : shoppingCartList) {
                Log.i(TAG, "-----------------shoppingCartList 中的数据: " + food.getFoodName() + "-----------------");
            }
        }
    }

    private void setupListeners() {
        // 返回按钮
        tvBack.setOnClickListener(v -> {
            finish();
        });
        // 去支付 按钮
        tvGoPay.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
            LayoutInflater inflater = LayoutInflater.from(OrderActivity.this);
            View dialogView = inflater.inflate(R.layout.payment, null);
            builder.setView(dialogView);
            builder.setCancelable(true); // 可以点外部取消
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });
    }
}

