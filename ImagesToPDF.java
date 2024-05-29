import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class ImagesToPDF {
    public static void main(String[] args) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));
            document.open();

            String[] images = {"D:\\Test123\\20240523.png", "D:\\Test123\\Image1.png", "D:\\Test123\\MealImage.png"};

            for (String image : images) {
                Image img = Image.getInstance(image);
                document.add(img);
            }

            document.close();
            System.out.println("PDF Created!");
        } catch (DocumentException | IOException e) {
             e.printStackTrace();
        }
    }
}
