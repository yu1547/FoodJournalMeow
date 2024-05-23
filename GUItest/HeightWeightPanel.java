//HeightWeightPanel
import javax.swing.*;
import java.awt.*;

public class HeightWeightPanel extends JPanel {
    private Image backgroundImage;
    
    public HeightWeightPanel() {
        try {
            backgroundImage = new ImageIcon("bgHeightWeightPanel.png").getImage();
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
