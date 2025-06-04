package cn.gavinlang.orderfoodonline.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.gavinlang.orderfoodonline.R;
import cn.gavinlang.orderfoodonline.bean.FoodBean;
import cn.gavinlang.orderfoodonline.utils.GetImageURL;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.math.BigDecimal;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final Context context;
    private final List<FoodBean> foodOrderList;//商品订单列表

    public OrderAdapter(List<FoodBean> foodOrderList, Context context) {
        this.foodOrderList = foodOrderList;
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {

        FoodBean food = foodOrderList.get(position);

        BigDecimal price = food.getPrice();
        String foodImgUrl = food.getFoodPic();
        String foodLogoUrl = GetImageURL.extractFoodImageNameFromUrl(foodImgUrl);

        holder.tvOrderFoodName.setText(food.getFoodName());//绑定商品名
        holder.tvCount.setText(String.valueOf(food.getCount()));//绑定商品添加到购物车的数量
        holder.tvMoney.setText("￥" + price);

        if (foodImgUrl != null && !foodImgUrl.isEmpty()) {
            Glide.with(context)
                    .load(foodLogoUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)// 加载错误时显示的图片
                    .into(holder.ivOrderFoodPic);
        } else {
            Log.e("exception", "-----------------foodImgUrl为空-----------------");
            holder.ivOrderFoodPic.setImageResource(R.drawable.ic_launcher_foreground);
        }

        Log.i("orderAdapter", "{\n-----------------foodName: " + food.getFoodName() + "-----------------");
        Log.i("orderAdapter", "-----------------foodCount: " + food.getCount() + "-----------------");
        Log.i("orderAdapter", "-----------------foodMoney: " + food.getPrice() + "-----------------");
        Log.i("orderAdapter", "-----------------food_logo_address: " + foodLogoUrl + "-----------------\n}");


    }

    @Override
    public int getItemCount() {
        return foodOrderList != null ? foodOrderList.size() : 0;
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView ivOrderFoodPic;
        TextView tvCount, tvMoney, tvOrderFoodName;

        public OrderViewHolder(View itemView) {
            super(itemView);
            ivOrderFoodPic = itemView.findViewById(R.id.iv_food_pic);//订单列表商品图片
            tvCount = itemView.findViewById(R.id.tv_count);//订单列表商品数量
            tvMoney = itemView.findViewById(R.id.tv_money);//订单列表商品单价
            tvOrderFoodName = itemView.findViewById(R.id.tv_food_name);//订单列表商品名称
        }
    }
}
