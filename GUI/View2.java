package ntou.cs.java2024;
import javax.swing.*;
import java.awt.*;

public class View2 extends JPanel {
    public View2() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("211餐盤", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 40));
        add(label, BorderLayout.CENTER);
        setBackground(new Color(255, 245, 238)); // 背景色
    }
}
