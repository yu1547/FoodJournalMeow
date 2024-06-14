
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Exporter {

    public void exportPng(String filename) {
        // 取得當日日期作為預設檔名
        String defaultFileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".png";
        
        // 顯示檔案選擇器以選擇儲存位置
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("選擇儲存的地點");
        fileChooser.setSelectedFile(new File(defaultFileName));  // 設定預設檔名

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // 加上檔案副檔名如果未提供
            if (!fileToSave.getAbsolutePath().endsWith(".png")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
            }
            try {
                // 載入圖片
                BufferedImage image = ImageIO.read(new File(filename));
                // 儲存圖片
                ImageIO.write(image, "png", fileToSave);
                JOptionPane.showMessageDialog(null, "圖片儲存成功: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "儲存圖片失敗: " + e.getMessage());
            }
        }
    }

    public void imagesToPDF() {
    JFileChooser fileChooser = new JFileChooser(new File("./results"));
    fileChooser.setDialogTitle("選擇圖片(可複選)");
    fileChooser.setMultiSelectionEnabled(true);
    int returnValue = fileChooser.showOpenDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
        File[] selectedFiles = fileChooser.getSelectedFiles();
        int fileCount = selectedFiles.length;
        
        // 取得當日日期作為預設檔名的一部分
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String defaultFileName = fileCount + "日食記圖" + currentDate + ".pdf";

        // 顯示檔案選擇器以選擇 PDF 儲存位置
        JFileChooser pdfFileChooser = new JFileChooser();
        pdfFileChooser.setDialogTitle("選擇儲存的地點");
        pdfFileChooser.setSelectedFile(new File(defaultFileName));  // 設定預設檔名

        int userSelection = pdfFileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File pdfFileToSave = pdfFileChooser.getSelectedFile();
            // 加上檔案副檔名如果未提供
            if (!pdfFileToSave.getAbsolutePath().endsWith(".pdf")) {
                pdfFileToSave = new File(pdfFileToSave.getAbsolutePath() + ".pdf");
            }

            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(pdfFileToSave));
                document.open();

                for (File file : selectedFiles) {
                    String filePath = file.getAbsolutePath();
                    if (new File(filePath).exists()) {
                        Image img = Image.getInstance(filePath);
                        document.add(img);
                    }
                }

                document.close();
                System.out.println("PDF 已建立: " + pdfFileToSave.getAbsolutePath());
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}


}
