import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExportTest {
    public static void main(String[] args) {
        // 創建 Meals 物件並添加食物項目
        Meals meals = new Meals();
        if (new File("breakfast.png").exists()) {
            meals.addFoodItem("20240523", "早餐", "開心", "breakfast.png");
            meals.printMeals();
        }

        if (new File("lunch.png").exists()) {
            meals.addFoodItem("20240523", "午餐", "滿足", "lunch.png");
            meals.printMeals();
        }

        if (new File("dinner.png").exists()) {
            meals.addFoodItem("20240523", "晚餐", "x", "dinner.png");
            meals.printMeals();
        }

        // 產生並下載圖片
        String imageName = "20240523.png"; // 這裡假設產生的圖片名稱是日期.png
        meals.exportMealImage();

        // 載入生成的圖片
        BufferedImage image;
        try {
            image = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            System.out.println("Failed to load the image: " + e.getMessage());
            return;
        }

        // 創建 Exporter 物件並儲存圖片
        Exporter exporter = new Exporter("png");
        exporter.exportToFile(image, "D:\\Test123\\" + imageName.substring(0, imageName.lastIndexOf('.')));
    }
}
