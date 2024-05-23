// MainFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel contentPanel;
    
    public MainFrame() {
        setTitle("貓掌食記");
        setSize(1280, 800); // 根據 bgFoodLogPanel.png 的大小設置窗口尺寸
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);

        // 上方導航列
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridLayout(1, 3)); // 使用 GridLayout 來平均分配按鈕
        navPanel.setPreferredSize(new Dimension(getWidth(), 80)); // 調整導航列高度
        navPanel.setBackground(new Color(0, 0, 0, 0)); // 透明背景

        JButton btnFoodLog = createInvisibleButton(); // "生成食記圖"按鈕
        JButton btn211 = createInvisibleButton(); // "211餐盤"按鈕
        JButton btnHeightWeight = createInvisibleButton(); // "身高體重紀錄"按鈕

        navPanel.add(btnFoodLog);
        navPanel.add(btn211);
        navPanel.add(btnHeightWeight);

        add(navPanel, BorderLayout.NORTH);

        // 內容面板
        contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());
        add(contentPanel, BorderLayout.CENTER);

        // 添加不同的內容面板
        contentPanel.add(new FoodLogPanel(), "FoodLog");
        contentPanel.add(new Panel211(), "211");
        contentPanel.add(new HeightWeightPanel(), "HeightWeight");

        // 導航按鈕事件處理
        btnFoodLog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel("FoodLog");
            }
        });
        
        btn211.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel("211");
            }
        });
        
        btnHeightWeight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchPanel("HeightWeight");
            }
        });

        setPreferredSize(new Dimension(1280, 800));
        pack(); // 使窗口尺寸適應內容
    }

    // 創建隱形按鈕的方法
    private JButton createInvisibleButton() {
        JButton button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

    // 切換面板方法
    private void switchPanel(String panelName) {
        CardLayout cl = (CardLayout)(contentPanel.getLayout());
        cl.show(contentPanel, panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
