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

import androidx.recyclerview.widget.RecyclerView;

import java.math.RoundingMode;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final Context context;
    private final List<FoodBean> cartList;//购物车列表

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onIncreaseClick(int position);

        void onDecreaseClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public CartAdapter(List<FoodBean> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.shopping_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartAdapter.CartViewHolder holder, int position) {
        FoodBean cartFood = cartList.get(position);//购物车中的商品
        String formattedPrice = "¥" + cartFood.getPrice().setScale(2, RoundingMode.HALF_UP).toPlainString();

        holder.tvFoodName.setText(cartFood.getFoodName());//绑定购物车商品名
        holder.tvFoodPrice.setText(formattedPrice);//绑定购物车商品价格
        holder.tvAddCartCount.setText(String.valueOf(cartFood.getCount()));//添加到购物车中的数量

        Log.d("cartAdapter", "-----------------foodName: " + cartFood.getFoodName() + "-----------------");
        Log.d("cartAdapter", "-----------------foodPrice: " + formattedPrice + "-----------------");
        Log.d("cartAdapter", "-----------------addCartCount: " + cartFood.getCount() + "-----------------");

        // 为 "+" 按钮设置点击事件
        holder.ivAdd.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onIncreaseClick(position);
            }
        });
        // 为 "-" 按钮设置点击事件
        holder.ivMinus.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onDecreaseClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList != null ? cartList.size() : 0;
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvFoodPrice, tvAddCartCount;
        ImageView ivAdd, ivMinus;

        public CartViewHolder(View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tv_food_name);//购物车商品名称
            tvFoodPrice = itemView.findViewById(R.id.tv_food_price);//购物城商品价格
            tvAddCartCount = itemView.findViewById(R.id.tv_food_count);//添加到购物车中的数量
            ivAdd = itemView.findViewById(R.id.iv_add);//购物车区域中的"-"图片按钮
            ivMinus = itemView.findViewById(R.id.iv_minus);//购物车区域中的"+"图片按钮
        }
    }
}
