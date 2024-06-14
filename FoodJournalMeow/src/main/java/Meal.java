// Meal.java
public class Meal {
    private String date;
    private String type;
    private String mood;
    private String imagePath;

    public Meal(String date, String type, String mood, String imagePath) {
        this.date = date;
        this.type = type;
        this.mood = mood.equals("x") ? "" : mood;
        this.imagePath = imagePath.equals("x") ? "images/default.png" : imagePath;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getMood() {
        return mood;
    }

    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath( String imagePath) {
        this.imagePath=imagePath;
        return;
    }

    @Override
    public String toString() {
        return "日期: " + date + ", 種類: " + type + ", 心情: " + mood + ", 圖片檔名: " + imagePath;
    }
}
