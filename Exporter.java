import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Exporter {
    private String fileType;

    public Exporter(String fileType) {
        this.fileType = fileType;
    }

    public void exportToFile(BufferedImage image, String filePath) {
        try {
            if (fileType.equalsIgnoreCase("png")) {
                File outputfile = new File(filePath + ".png");
                ImageIO.write(image, "png", outputfile);
                System.out.println("Successfully saved the image to the file " + filePath + ".png!");
            } else if (fileType.equalsIgnoreCase("pdf")) {
                // Implement the method to save the BufferedImage object as a PDF here
                // You may need to use some third-party libraries, such as Apache PDFBox or iText
            } else {
                System.out.println("Unsupported file type: " + fileType);
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

}
