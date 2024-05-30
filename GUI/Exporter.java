import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.*;

public class Exporter {

   public void exportPng(String filename) {
        // Show file chooser to select save location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Save Location");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // Add file extension if not provided
            if (!fileToSave.getAbsolutePath().endsWith(".png")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
            }
            try {
                // Load the image
                BufferedImage image = ImageIO.read(new File(filename));
                // Save the image
                ImageIO.write(image, "png", fileToSave);
                JOptionPane.showMessageDialog(null, "Image saved successfully: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to save image: " + e.getMessage());
            }
        }
    }

    public void imagesToPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();

            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));
                document.open();

                for (File file : selectedFiles) {
                    String filePath = file.getAbsolutePath();
                    if(new File(filePath).exists()) {
                        Image img = Image.getInstance(filePath);
                        document.add(img);
                    }
                }

                document.close();
                System.out.println("PDF Created!");
            } catch (DocumentException | IOException e) {
                 e.printStackTrace();
            }
        }
    }
}