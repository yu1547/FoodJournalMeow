//FoodLogPanel
import javax.swing.*;
import java.awt.*;

public class FoodLogPanel extends JPanel {
    private Image backgroundImage;
    
    public FoodLogPanel() {
        try {
            backgroundImage = new ImageIcon("bgFoodLogPanel.png").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
