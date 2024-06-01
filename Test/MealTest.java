// MealTest.java
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
public class MealTest {
    public static void main(String[] args) {
        Meals meals = new Meals();
        String date = new SimpleDateFormat("yy-MM-dd").format(new Date());
        // 測試添加
        if(new File("breakfast.png").exists()) {
            meals.addFoodItem(date, "早餐", "開心", "breakfast.png");
            meals.printMeals();
        }

        if(new File("lunch.png").exists()) {
            meals.addFoodItem(date, "午餐", "滿足", "lunch.png");
            meals.printMeals();
        }

        if(new File("dinner.png").exists()) {
            meals.addFoodItem(date, "晚餐", "x", "dinner.png");
            meals.printMeals();
        }

        // 測試重複添加
        meals.addFoodItem(date, "早餐", "開心", "breakfast.png");
        meals.printMeals();

        // 測試移除
        meals.removeFoodItem("早餐");
        meals.printMeals();

        // // 測試再次添加
        // meals.addFoodItem(date, "早餐", "開心", "breakfast.png");
        // meals.printMeals();
        meals.exportMealImage();

    }
}
