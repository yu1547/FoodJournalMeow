import java.io.File;

// DownloadTest.java
public class DownloadTest {
    public static void main(String[] args) {
        // 創建 User 物件
        User user = new User("username", 170, 65);

        // 創建 Meals 物件並添加食物項目
        Meals meals = new Meals();
        if(new File("breakfast.png").exists()) {
            meals.addFoodItem("20240523", "早餐", "開心", "breakfast.png");
            meals.printMeals();
        }

        if(new File("lunch.png").exists()) {
            meals.addFoodItem("20240523", "午餐", "滿足", "lunch.png");
            meals.printMeals();
        }

        if(new File("dinner.png").exists()) {
            meals.addFoodItem("20240523", "晚餐", "x", "dinner.png");
            meals.printMeals();
        }

        // 產生並下載圖片
        String imageName = "20240523.png"; // 這裡假設產生的圖片名稱是日期.png
        meals.exportMealImage();
        user.download(imageName); // 下載圖片
    }
}
