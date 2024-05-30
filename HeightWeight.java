import javax.swing.*;

import org.jfree.chart.ChartPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.time.LocalDate;

public class HeightWeight extends JPanel {
    private Image backgroundImage;
    private JTextField heightField;
    private JTextField weightField;
    private JButton addButton;
    private JLabel heightLabel;
    private JLabel weightLabel;
    private JLabel chartLabel;
    private JPanel inputPanel;
    private JPanel radioPanel;
    private JRadioButton sevenDaysRadioButton;
    private JRadioButton thirtyDaysRadioButton;
    private ButtonGroup chartOption = new ButtonGroup();
    private ChartPanel chartPanel;
    
    public HeightWeight() {

        try {
            backgroundImage = new ImageIcon("bgHeightWeightPanel.png").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null)));

        heightLabel = new JLabel("身高：");
        heightField = new JTextField();
        weightLabel = new JLabel("體重：");
        weightField = new JTextField();
        addButton = new JButton("新增");

        chartLabel = new JLabel("圖表");
        sevenDaysRadioButton = new JRadioButton("7天", true);
        thirtyDaysRadioButton = new JRadioButton("30天");

        chartOption.add(sevenDaysRadioButton);
        chartOption.add(thirtyDaysRadioButton);

        inputPanel = new JPanel(new GridLayout(5, 2)); // 使用GridLayout排列元素
        inputPanel.setBorder(BorderFactory.createTitledBorder("輸入")); // 添加Panel的title
        inputPanel.setBackground(Color.LIGHT_GRAY);

        radioPanel = new JPanel(new GridLayout(3, 1));
        inputPanel.setBorder(BorderFactory.createTitledBorder("圖表"));
        radioPanel.add(sevenDaysRadioButton);
        radioPanel.add(thirtyDaysRadioButton);

        inputPanel.add(heightLabel);
        inputPanel.add(heightField);
        inputPanel.add(weightLabel);
        inputPanel.add(weightField);
        inputPanel.add(addButton);

        inputPanel.add(radioPanel);

        setLayout(new BorderLayout()); // 使用BorderLayout排列Panel
        add(inputPanel, BorderLayout.EAST);

        JPanel emptyPanel = new JPanel();
        add(emptyPanel, BorderLayout.CENTER);

        addButton.addActionListener(new MyEventListener());
        heightField.addActionListener(new MyEventListener());
        weightField.addActionListener(new MyEventListener());
    }

    private class MyEventListener implements ActionListener, ItemListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            updateChart();
        }
    
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateChart();
            }
        }
    
        private void updateChart() {
            double height = heightField.getText().isEmpty() ? 0.0 : Double.parseDouble(heightField.getText());
            double weight = weightField.getText().isEmpty() ? 0.0 : Double.parseDouble(weightField.getText());
    
            String choose;
            if (sevenDaysRadioButton.isSelected()) {
                choose = "week";
            } else {
                choose = "month";
            }
    
            // 创建 HealthRecord 对象并存取输入的身高和体重
            HealthRecord record = new HealthRecord(LocalDate.now(), height, weight);
            record.saveRecord("health_records.xlsx");
    
            JPanel chartContainer = new JPanel(new BorderLayout());
            chartPanel = new GraphGenerator("Height and Weight Change Chart", choose).getChartPanel();
            chartContainer.add(chartPanel, BorderLayout.CENTER);
    
            removeAll();
            add(inputPanel, BorderLayout.EAST);
            add(chartContainer, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Height and Weight");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new HeightWeight()); // 將 HeightWeight 實例添加到 JFrame 的內容面板中
                frame.pack(); // 設置 JFrame 的大小以容納其內容
                frame.setVisible(true); // 使 JFrame 可見
            }
        });
    }
}


