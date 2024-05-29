import javax.swing.*;
import java.awt.*;

public class View1 extends JPanel {
    public View1() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("生成食記圖", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 40));
        add(label, BorderLayout.CENTER);
        setBackground(new Color(255, 245, 238)); // 背景色
    }
}
