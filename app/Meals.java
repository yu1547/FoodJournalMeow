package ntou.cs.java2024;

import java.util.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Meals {
    private Map<String, Meal> meals;
    private final String DEFAULT_IMAGE_PATH1 = "images/default1.png";
    private final String DEFAULT_IMAGE_PATH2 = "images/default2.png";

    public Meals() {
        this.meals = new LinkedHashMap<>();
    }

    public void removeFoodItem(String type) {
        meals.remove(type);
        System.out.println("已移除" + type);
    }

    public void addFoodItem(String date, String type, String mood, String imagePath) {
        if (meals.containsKey(type)) {
            removeFoodItem(type);
            System.out.println("已添加過" + type + "!請先刪除再新增");
        }
        Meal meal = new Meal(date, type, mood, imagePath);
        meals.put(type, meal);
        System.out.println("添加了" + type);
    }

    public void exportMealImage() {
        try {
            BufferedImage background = ImageIO.read(new File("images/background.png"));
            Graphics2D g = background.createGraphics();

            // 開啟抗鋸齒和高質量渲染
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 設定字型顏色和大小
            g.setColor(Color.BLACK);
            Font font = new Font("標楷體", Font.BOLD, 20);
            g.setFont(font);

            // 繪製日期
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            g.drawString(date, 10, 30);

            // 绘制浮水印文字
            String watermarkText = "貓掌食記";
            Font watermarkFont = new Font("標楷體", Font.BOLD, 20);
            g.setFont(watermarkFont);
            FontMetrics fm = g.getFontMetrics(watermarkFont);
            int textWidth = fm.stringWidth(watermarkText);
            int textHeight = fm.getHeight();
            int textX = background.getWidth() - textWidth - 60; // 向左移动一些
            int textY = textHeight ;
            g.drawString(watermarkText, textX, textY);

            // 加载并绘制 cat.png
            BufferedImage catImage = ImageIO.read(new File("images/cat.png"));
            int catImageWidth = (int)(catImage.getWidth()*0.5);
            int catImageHeight = (int)(catImage.getHeight()*0.5);
            int catX = textX + textWidth ;
            int catY = 10;
            g.drawImage(catImage, catX, catY, catImageWidth,catImageHeight,null);

            int mealWidth = (int) (background.getWidth() * 0.45);
            int mealHeight = (int) (background.getHeight() * 0.2);
            int x, y;

            String[] types = {"早餐", "午餐", "晚餐"};
            for (int i = 0; i < types.length; i++) {
                Meal meal;
                if (meals.containsKey(types[i])) {
                    meal = meals.get(types[i]);
                    if (meal.getImagePath() == null || meal.getImagePath().isEmpty()) {
                        meal.setImagePath(DEFAULT_IMAGE_PATH2);
                    }
                } else {
                    meal = new Meal(date, types[i], "我沒有吃" + types[i], DEFAULT_IMAGE_PATH1);
                }

                BufferedImage foodImage = ImageIO.read(new File(meal.getImagePath()));
                // 縮放食物圖片以適應背景的一部分
                BufferedImage scaledFoodImage = new BufferedImage(mealWidth, mealHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = scaledFoodImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.drawImage(foodImage, 0, 0, mealWidth, mealHeight, null);
                g2d.dispose();
                // 將食物圖片繪製到背景上的正確位置
                x = (i % 2) == 0 ? (int) (background.getWidth() * 0.1) : (int) (background.getWidth() * 0.1+mealWidth);
                y = (int) (background.getHeight() * 0.2) * (i + 1);
                g.drawImage(scaledFoodImage, x, y, null);
                // 如果有心情，將心情繪製到圖片上
                if (!meal.getMood().isEmpty()) {
                    int moodX;
                    int moodY;
                    switch (types[i]) {
                        case "早餐":
                            moodX = (int) (x+mealWidth);
                            moodY = (int) (background.getHeight() * 0.25); // 距離上面20%
                            break;
                        case "午餐":
                            moodX = (int) (background.getWidth() * 0.1); // 距離左邊10%
                            moodY = (int) (background.getHeight() * 0.45); // 距離上面40%
                            break;
                        case "晚餐":
                            moodX = (int) (x+mealWidth);
                            moodY = (int) (background.getHeight() * 0.65); // 距離上面60%
                            break;
                        default:
                            moodX = 0;
                            moodY = 0;
                    }
                    g.setFont(new Font("標楷體", Font.PLAIN, 20)); // 设置心情的字体大小
                    g.drawString(meal.getMood(), moodX, moodY);
                }
            }

            g.dispose();

            // 將結果寫入到新的圖片檔案
            ImageIO.write(background, "png", new File("results/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMeals() {
        for (Meal meal : meals.values()) {
            System.out.println(meal);
        }
    }
}
