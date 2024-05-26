import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class User {
    private String username;
    private double height;
    private double weight;

    public User(String username, double height, double weight) {
        this.username = username;
        this.height = height;
        this.weight = weight;
    }

    public void updateProfile(String username, double height, double weight) {
        this.username = username;
        this.height = height;
        this.weight = weight;
    }
    
    public void download(String filename) {
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
}
