package cn.gavinlang.orderfoodonline.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import cn.gavinlang.orderfoodonline.R;

import cn.gavinlang.orderfoodonline.bean.FoodBean;
import cn.gavinlang.orderfoodonline.bean.ShopBean;
import cn.gavinlang.orderfoodonline.utils.GetImageURL;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private final Context context;
    private final List<FoodBean> shopFoodList;//店铺商品详情列表
    private OnAddToCartClickListener listener;//定义成员变量 保存外部传进来的监听器对象


    public FoodAdapter(Context context, ShopBean shopBean) {
        this.context = context;
        this.shopFoodList = shopBean.getFoodList();
    }

    /**
     * 接口回调机制
     * 用在 Adapter 或其他组件中，用来把“添加到购物车”的点击事件传递给外部（通常是 Activity 或 Fragment）来处理 保持 Adapter 的纯粹性（只管显示）
     */
    public interface OnAddToCartClickListener {//定义接口

        //谁想监听 "添加到购物车" 这个动作 就去实现这个接口
        void onAddToCartClick(FoodBean foodBean);//添加点击"加入购物车"按钮的商品
    }

    public void setOnAddToCartClickListener(OnAddToCartClickListener listener) {//提供实现接口的方法
        //可以通过这个方法传进来一个监听器 实现你点击"加入购物车"按钮时该做什么事
        this.listener = listener;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.menu_item, parent, false);
        return new FoodViewHolder(view);

    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {

        FoodBean foodBean = shopFoodList.get(position);

        BigDecimal price = foodBean.getPrice();//获取json数据中food价格
        String formattedPrice = "¥" + price.setScale(2, RoundingMode.HALF_UP).toPlainString();//保留两位小数 四舍五入
        String saleNum = "月销售: " + foodBean.getSaleNum();
        String foodImgUrl = foodBean.getFoodPic();//菜品图片url
        String foodLogoUrl = GetImageURL.extractFoodImageNameFromUrl(foodImgUrl);

        holder.tvFoodName.setText(foodBean.getFoodName());//绑定菜名
        holder.tvFoodIngredients.setText(foodBean.getTaste());//绑定原料介绍
        holder.tvFoodSaleNum.setText(saleNum);//绑定月销售数量
        holder.tvFoodPrice.setText(formattedPrice);//绑定商品价格

        if (foodImgUrl != null && !foodImgUrl.isEmpty()) {
            Glide.with(context)
                    .load(foodLogoUrl)  // 加载完整的图片 URL
                    .placeholder(R.drawable.ic_launcher_background)  // 占位符
                    .error(R.drawable.ic_launcher_foreground)      // 加载错误时显示的图片
                    .into(holder.ivFoodLogo);
        } else {
            holder.ivFoodLogo.setImageResource(R.drawable.ic_launcher_foreground);
        }

        Log.i("foodAdapter", "{\n-----------------foodName: " + foodBean.getFoodName() + "-----------------");
        Log.i("foodAdapter", "-----------------taste: " + foodBean.getTaste() + "-----------------");
        Log.i("foodAdapter", "-----------------saleNum: " + foodBean.getSaleNum() + "-----------------");
        Log.i("foodAdapter", "-----------------price: " + foodBean.getPrice() + "-----------------");
        Log.i("foodAdapter", "-----------------food_logo_address: " + foodLogoUrl + "-----------------\n}");
        //--------------------------------------------------------------------//


        //-------------------------加入购物车点击事件-------------------------//
        holder.btnAddCart.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddToCartClick(foodBean);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            String foodDetailPic = GetImageURL.extractFoodImageNameFromUrl(foodBean.getFoodPic());
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_food_detail, null);
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 可选美化
            dialog.show();


            TextView tvDialogFoodName = dialogView.findViewById(R.id.tv_dialog_food_name);//商品对话框商品名字
            TextView tvDialogSaleNum = dialogView.findViewById(R.id.tv_dialog_sale_num);//商品对话框商品月销量
            TextView tvDialogPrice = dialogView.findViewById(R.id.tv_dialog_price);//商品对话框商品价格
            ImageView ivDialogFoodPic = dialogView.findViewById(R.id.iv_dialog_food_pic);//商品对话框商品图片

            tvDialogFoodName.setText(foodBean.getFoodName());
            tvDialogSaleNum.setText("月售 " + foodBean.getSaleNum());
            tvDialogPrice.setText("￥" + foodBean.getPrice());

            Glide.with(context)
                    .load(foodDetailPic)
                    .into(ivDialogFoodPic);
        });
    }

    @Override
    public int getItemCount() {
        return shopFoodList != null ? shopFoodList.size() : 0;
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoodLogo;
        TextView tvFoodName, tvFoodIngredients, tvFoodSaleNum, tvFoodPrice;
        Button btnAddCart;

        public FoodViewHolder(View itemView) {
            super(itemView);
            ivFoodLogo = itemView.findViewById(R.id.iv_food_pic);//商品照片
            tvFoodName = itemView.findViewById(R.id.tv_food_name);//商品名
            tvFoodIngredients = itemView.findViewById(R.id.tv_food_ingredients);//商品成分原料介绍
            tvFoodSaleNum = itemView.findViewById(R.id.tv_food_sale_num);//商品销售数量
            tvFoodPrice = itemView.findViewById(R.id.tv_food_price);//商品价格
            btnAddCart = itemView.findViewById(R.id.btn_add_cart);//加入购物车按钮
        }
    }


}
