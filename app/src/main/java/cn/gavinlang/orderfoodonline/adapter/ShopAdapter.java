package cn.gavinlang.orderfoodonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.gavinlang.orderfoodonline.R;
import cn.gavinlang.orderfoodonline.ShopDetail;
import cn.gavinlang.orderfoodonline.bean.ShopBean;
import cn.gavinlang.orderfoodonline.utils.GetImageURL;

//主菜单店铺RecyclerView适配器
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    private final Context context;
    private final List<ShopBean> shopList;//店铺列表

    public ShopAdapter(Context context, List<ShopBean> shopList) {
        this.context = context;
        this.shopList = shopList;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.shop_item, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        ShopBean shop = shopList.get(position);

        //--------------------------------绑定店铺信息----------------------------//
        String info = "起送￥" + shop.getOfferPrice()
                + " 配送费￥" + shop.getDistributionCost();
        String saleNum = "月销售: " + shop.getSaleNum();
        String shopImgUrl = shop.getShopPic();
        String shopLogoUrl = GetImageURL.extractShopImageNameFromUrl(shopImgUrl);

        holder.tvShopName.setText(shop.getShopName());//店铺名
        holder.tvShopSaleNum.setText(saleNum);//店铺月销售
        holder.tvShopCost.setText(info);//店铺起送配送成本
        holder.tvShopWelfare.setText(shop.getWelfare());//店铺福利
        holder.tvShopTime.setText(shop.getTime());//店铺配送时间

        if (shopImgUrl != null && !shopImgUrl.isEmpty()) {
            // 使用 Glide 库加载图片并显示到 ImageView 控件
            Glide.with(context)
                    .load(shopLogoUrl)  // 加载完整的图片 URL
                    .placeholder(R.drawable.ic_launcher_background)  // 占位符
                    .error(R.drawable.ic_launcher_foreground)      // 加载错误时显示的图片
                    .into(holder.ivShopLogo);
        } else {
            Log.e("exception", "<-----shopImgUrl为空----->");
            holder.ivShopLogo.setImageResource(R.drawable.ic_launcher_foreground);
        }

        Log.i("shopInformation", "{\n-----------------shopName: " + shop.getShopName() + "-----------------");
        Log.i("shopInformation", "-----------------shopSaleNum: " + shop.getSaleNum() + "-----------------");
        Log.i("shopInformation", "-----------------shopCost: " + info + "-----------------");
        Log.i("shopInformation", "-----------------shopWelfare: " + shop.getWelfare() + "-----------------");
        Log.i("shopInformation", "-----------------shop_logo_address: " + shopLogoUrl + "-----------------\n}");
        //--------------------------------------------------------------------//


        //-----------------------------item点击事件-------------------------------//
        holder.itemView.setOnClickListener(v -> {
            Log.d("setOnClickListener", "shop_item执行点击事件");
            Intent intent = new Intent(context, ShopDetail.class);//在 ShopDetail.class 里可以通过 getIntent().getStringExtra("title") 获取这个值
            intent.putExtra("ShopBean_data", shop);//传递单个店铺信息
            intent.putExtra("shopLogoUrl", shopLogoUrl);//传递店铺图像URL
            Log.d("setOnClickListener", "MainActivity界面将跳转到ShopDetail界面");
            context.startActivity(intent);//启动下一个Activity
        });
        //--------------------------------------------------------------------//
    }

    @Override
    public int getItemCount() {
        return shopList != null ? shopList.size() : 0;
    }

    static class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView ivShopLogo;
        TextView tvShopName, tvShopSaleNum, tvShopCost, tvShopWelfare, tvShopTime;

        public ShopViewHolder(View itemView) {
            super(itemView);
            ivShopLogo = itemView.findViewById(R.id.iv_logo);//店铺logo
            tvShopName = itemView.findViewById(R.id.tv_shop_name);//店铺名称
            tvShopSaleNum = itemView.findViewById(R.id.tv_sale_num);//店铺月销售数量
            tvShopCost = itemView.findViewById(R.id.tv_cost);//店铺起送配送成本信息
            tvShopWelfare = itemView.findViewById(R.id.tv_welfare);//店铺福利信息
            tvShopTime = itemView.findViewById(R.id.tv_time);//店铺配送时间
        }
    }
}
