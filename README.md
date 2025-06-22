1. 这个项目是什么？

这个项目是一个用 Android 开发的简化版外卖程序，旨在模拟实现订餐的基本功能。
作为我在 Android 课程 中的期末项目
我通过这个项目掌握了 Android 应用开发的核心技能，包括界面设计、数据交互、RecyclerView 使用等。
并且将其上传到 GitHub 上，希望能够帮助有类似需求的开发者，或者作为学习参考。

2. 主要功能有哪些？
   
店铺列表展示：解析本地JSON数据，展示出店铺的名称、地址、配送时间、公告等信息。

菜品选择：每个店铺下都有详细的菜品展示，点击Item放大菜品图片信息，用户可以选择菜品点击"加入购物车"按钮加入购物车。

购物车管理：用户可以查看已选择的菜品，并修改数量或删除菜品，实时显示菜品总价与店铺起送价格的差额。

下单功能：用户在购物车页面下单，并能查看订单详情，自定义收货地址信息。

支付功能：暂时使用二维码收款图片替代，后续进行更改，引入微信支付等付款方式。

3. 使用技术栈有哪些？
   
开发语言：Java

开发工具：Android Studio

UI 组件：RecyclerView, ViewPager, CardView, Button,Dialog 等

数据存储：SQLite 用于本地存储订单数据

网络请求：使用 Retrofit 或 OkHttp 实现与服务器的通信

4. 如何使用这个项目？

克隆仓库：

5. 如何贡献？
   
如果你有兴趣为这个项目贡献代码，请按照以下步骤：

Fork 这个仓库。

创建一个新的分支 (git checkout -b feature/your-feature-name)。

提交你的改动 (git commit -am 'Add new feature')。

推送到你的分支 (git push origin feature/your-feature-name)。

提交 Pull Request。


6.本地JSON数据存放位置：

项目结构中的 server_data/order 文件夹存储项目用到的JSON数据文件和.PNG文件

server_data/order 放在Tomcat安装包\webapps\ROOT\路径下

在项目运行前需要先开启Tomcat服务器（bin/startup.bat）


