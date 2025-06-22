1. What is this project?

This project is a simplified version of a food delivery program developed with Android to simulate the basic functions of ordering food.
As my final project in the Android course
Through this project, I have mastered the core skills of Android app development, including interface design, data interaction, and the use of RecyclerView.
And upload it to GitHub, hoping to help developers with similar needs, or as a reference for learning.

2. What are the main features?

Store list display: Parse local JSON data to display the store's name, address, delivery time, announcement, and other information.

Dish selection: There is a detailed display of dishes under each store, click Item to enlarge the picture information of the dishes, and users can select the dishes and click the "Add to Cart" button to add to the cart.

Shopping cart management: Users can view the selected dishes, modify the quantity or delete the dishes, and display the difference between the total price of the dishes and the starting price of the store in real time.

Order function: The user places an order on the shopping cart page, and can view the order details and customize the delivery address information.

Payment function: temporarily use the QR code to receive the picture instead, and then make changes to introduce payment methods such as WeChat payment.

3. What are the tech stacks to use?

Development language: Java

Development Tools: Android Studio

UI components: RecyclerView, ViewPager, CardView, Button, Dialog, etc

Data storage: SQLite is used to store order data locally

Network Request: Use Retrofit or OkHttp to communicate with the server

4. How to use this item?

Clone repository: https://github.com/cruch2004/OrderFoodOnline.git

5. How to contribute?

If you are interested in contributing code to this project, please follow these steps:

Fork this repository.

Create a new branch (git checkout -b feature/your-feature-name).

Commit your changes (git commit -am 'Add new feature').

Push to your branch (git push origin feature/your-feature-name).

Submit a pull request.

6. Local JSON data storage location:

The server_data/order folder in the project structure stores the JSON data files and .PNG files used by the project

server_data/order is located in the Tomcat packagewebappsROOT directory

Before the project runs, you need to start the Tomcat server (bin/startup.bat)
