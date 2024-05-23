//Panel211
import javax.swing.*;
import java.awt.*;

public class Panel211 extends JPanel {
    private Image backgroundImage;
    
    public Panel211() {
        try {
            backgroundImage = new ImageIcon("bgPanel211.png").getImage();
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
