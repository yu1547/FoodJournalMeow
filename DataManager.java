import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class DataManager {
    private List<BufferedImage> imageList;
    private String directoryPath;

    public DataManager(String directoryPath) {
        this.directoryPath = directoryPath;
        this.imageList = new ArrayList<>();
    }

    public void saveImage(BufferedImage image, String imageName) {
        this.imageList.add(image);
        saveToFile(image, imageName);
    }

    public List<BufferedImage> loadImages() {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    imageList.add(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imageList;
    }

    public void deleteImage(String imageName) {
        File file = new File(directoryPath + "\\" + imageName + ".png");
        if (file.delete()) {
            System.out.println("Successfully deleted the image: " + imageName + ".png!");
        } else {
            System.out.println("Failed to delete the image: " + imageName + ".png!");
        }
    }

    private void saveToFile(BufferedImage image, String imageName) {
        try {
            File outputfile = new File(directoryPath + "\\" + imageName + ".png");
            ImageIO.write(image, "png", outputfile);
            System.out.println("Successfully saved the image to the file: " + imageName + ".png!");
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            BufferedImage image1 = ImageIO.read(new File("D:\\MealImage.png"));
            BufferedImage image2 = ImageIO.read(new File("D:\\Image2.png"));

            DataManager dataManager = new DataManager("D:\\TEST123");
            dataManager.saveImage(image1, "Image1");
            dataManager.saveImage(image2, "Image2");

            List<BufferedImage> imageList = dataManager.loadImages();

            dataManager.deleteImage("Image2");

            imageList = dataManager.loadImages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
