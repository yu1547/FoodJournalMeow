import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        // 設定視窗標題和大小
        setTitle("貓掌食記");
        setSize(800, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 設定卡片布局
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 加入各個視窗到主面板
        mainPanel.add(new MealPanel(), "MealPanel");
        mainPanel.add(new Panel211(), "Panel211");
        mainPanel.add(new HeightWeightPanel(), "HeightWeightPanel");

        // 設定導覽列
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(1, 3));
        navPanel.setBackground(new Color(255, 228, 225)); // 導覽列背景色

        // 加入貓爪圖片
        ImageIcon icon = new ImageIcon("images/cat.png");

        JButton btn1 = createNavButton("生成食記圖", icon);
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "MealPanel");
            }
        });

        JButton btn2 = createNavButton("211餐盤", icon);
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Panel211");
            }
        });

        JButton btn3 = createNavButton("身高體重紀錄", icon);
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "HeightWeightPanel");
            }
        });

        navPanel.add(btn1);
        navPanel.add(btn2);
        navPanel.add(btn3);

        // 加入主面板和導覽列到視窗
        add(navPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createNavButton(String text, ImageIcon icon) {
        JButton button = new JButton(text);
        button.setIcon(icon);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setBackground(new Color(255, 165, 0)); // 橘色
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Serif", Font.BOLD, 20));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
