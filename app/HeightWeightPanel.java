package ntou.cs.java2024;
import javax.swing.*;

import org.jfree.chart.ChartPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.time.LocalDate;

public class HeightWeightPanel extends JPanel {
    private JTextField heightField;
    private JTextField weightField;
    private JButton addButton;
    private JLabel heightLabel;
    private JLabel weightLabel;
    private JLabel chartLabel;
    private JPanel inputPanel;
    private JPanel radioPanel;
    private JPanel heightPanel;
    private JPanel weightPanel;
    private JPanel mainPanel;
    private JRadioButton sevenDaysRadioButton;
    private JRadioButton thirtyDaysRadioButton;
    private ButtonGroup chartOption = new ButtonGroup();
    private ChartPanel chartPanel;
    public HeightWeightPanel() {
        setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("images/cat.png");
        JLabel titleLabel = new JLabel("身高體重紀錄", icon, JLabel.LEFT);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(new Color(255, 245, 238)); // 背景色

        add(titlePanel, BorderLayout.NORTH);

        //主版面
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 245, 238)); // 背景色
        add(mainPanel, BorderLayout.CENTER);

        //右邊輸入
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(new Color(255, 245, 238)); // 背景色
        inputPanel.setBorder(BorderFactory.createTitledBorder("輸入"));
        mainPanel.add(inputPanel, BorderLayout.EAST);

        //身高
        heightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        heightPanel.setBackground(new Color(255, 245, 238));
        heightLabel = new JLabel("身高：");
        heightField = new JTextField(10);
        heightPanel.add(heightLabel);
        heightPanel.add(heightField);

        //體重
        weightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        weightPanel.setBackground(new Color(255, 245, 238));
        weightLabel = new JLabel("體重：");
        weightField = new JTextField(10);
        weightPanel.add(weightLabel);
        weightPanel.add(weightField);

        //圖表天數
        chartLabel = new JLabel("圖表");
        sevenDaysRadioButton = new JRadioButton("week", true);
        thirtyDaysRadioButton = new JRadioButton("month");
        chartOption.add(sevenDaysRadioButton);
        chartOption.add(thirtyDaysRadioButton);

        radioPanel = new JPanel();
        radioPanel.add(chartLabel);
        radioPanel.add(sevenDaysRadioButton);
        radioPanel.add(thirtyDaysRadioButton);
        radioPanel.setBackground(new Color(255, 245, 238));

        addButton = new JButton("新增");

        inputPanel.add(heightPanel);
        inputPanel.add(weightPanel);
        inputPanel.add(addButton);
        inputPanel.add(radioPanel);

        //圖表區
        chartPanel = new ChartPanel(null);
        chartPanel.setBackground(new Color(255, 245, 238)); // 背景色
        mainPanel.add(chartPanel, BorderLayout.CENTER);

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
    
            // 創建 HealthRecord 並存取輸入的身高與體重
            HealthRecord record = new HealthRecord(LocalDate.now(), height, weight);
            record.saveRecord("health_records.xlsx");

            if (chartPanel != null) {
                mainPanel.remove(chartPanel); // 移除舊的
            }
    
            chartPanel = new GraphGenerator("Height and Weight Change Chart", choose).getChartPanel();
            mainPanel.add(chartPanel, BorderLayout.CENTER);
    
            mainPanel.revalidate();
            mainPanel.repaint();
        }
    }
}
