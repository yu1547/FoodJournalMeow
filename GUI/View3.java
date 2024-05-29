import javax.swing.*;
import java.awt.*;

public class View3 extends JPanel {
    public View3() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("身高體重紀錄", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 40));
        add(label, BorderLayout.CENTER);
        setBackground(new Color(255, 245, 238)); // 背景色
    }
}
