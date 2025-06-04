package cn.gavinlang.orderfoodonline;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.gavinlang.orderfoodonline.adapter.ShopAdapter;
import cn.gavinlang.orderfoodonline.bean.ShopBean;
import cn.gavinlang.orderfoodonline.utils.Constant;
import cn.gavinlang.orderfoodonline.utils.JsonParse;
import cn.gavinlang.orderfoodonline.utils.JsonRequest;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShopAdapter shopAdapter;
    private final String SHOP_INFO_ADDRESS = Constant.SHOP_INFO_URL;//店铺JSON信息地址


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_shop_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Log.d("Thread", "onCreate: 开始执行后台线程");
        new Thread(new Runnable() {
            @Override
            public void run() {

                String jsonData = JsonRequest.fetchShopListJson(SHOP_INFO_ADDRESS);
                Log.i("shop_list_data.json", "-----------------JsonData: " + jsonData + "-----------------");//打印json信息

                if (!jsonData.isEmpty()) {
                    List<ShopBean> shopList = JsonParse.getInstance().getShopList(jsonData);

                    //更新recyclerView适配器
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (shopList != null && !shopList.isEmpty()) {
                                shopAdapter = new ShopAdapter(MainActivity.this, shopList);
                                recyclerView.setAdapter(shopAdapter);
                                Log.d("Adapter", "run: 适配器设置成功");
                            } else {
                                Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Log.e("exception", '[' + jsonData + "为空]");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

}