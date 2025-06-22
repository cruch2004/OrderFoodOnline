1. 这个项目是什么？

    该项目是使用 Android 开发的送餐程序的简化版本，用于模拟点餐的基本功能。作为我在 Android 课程的最后一个项目，通过这个项目，我掌握了 Android 应用开发的核心技能，包括界面设计、数据交互以及 RecyclerView 的使用。并上传到 GitHub，希望能帮助到有相似需求的开发者，或者作为学习的参考。

2. 主要功能有哪些？
   
    商店列表显示：解析本地 JSON 数据以显示商店的名称、地址、送货时间、公告和其他信息。
   
    菜品选择：每个门店下都有菜品的详细展示，点击项目放大菜品的图片信息，用户可以选择菜品后点击“加入购物车”按钮加入购物车。
   
    购物车管理：用户可以查看所选菜品，修改数量或删除菜品，并实时显示菜品总价与店铺起售价的差额。
   
    订单功能：用户在购物车页面下单，可以查看订单详情，自定义收货地址信息。
   
    支付功能：暂时使用二维码接收图片，然后进行更改，引入微信支付等支付方式。


3. 使用技术栈有哪些？

    开发语言：Java

    开发工具：Android Studio

    UI 组件：RecyclerView、ViewPager、CardView、Button、Dialog 等

    数据存储：SQLite 用于在本地存储订单数据

    网络请求：使用 Retrofit 或 OkHttp 与服务器通信
   
4. 如何使用此项目？

   克隆存储库：https://github.com/cruch2004/OrderFoodOnline.git4. How to use this item?

5. 本地 JSON 数据存储位置：

    项目结构中的 server_data/order 文件夹存储项目使用的 JSON 数据文件和 .PNG 文件

    server_data/order 位于 Tomcat packagewebappsROOT 目录中

    在项目运行之前，您需要启动 Tomcat 服务器 （bin/startup.bat）
