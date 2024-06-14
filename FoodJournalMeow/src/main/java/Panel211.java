import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

public class Panel211 extends JPanel {
    private JButton addPhotoButton;
    private JLabel imageLabel;
    private JPanel addPhotoPanel;
    private JPanel resultPanel;
    private JPanel addAndDisplayPanel;
    private JPanel brushPanel;
    private JPanel imagePanel;
    private JPanel paintingPanel;
    private JPanel mainPanel;
    private JButton repaint;
    private JButton calculateOutput;
    private JRadioButton redPen;
    private JRadioButton greenPen;
    private JRadioButton yellowPen;
    private ButtonGroup brushColor;
    private JTextField ratio;
    private JTextField match;
    private JTextArea advice;
    private Color currentColor = Color.BLACK;
    private DrawPanel drawPanel;

    public Panel211() {
        setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("images/cat.png");
        JLabel titleLabel = new JLabel("211餐盤", icon, JLabel.LEFT);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(new Color(255, 245, 238)); // 背景色

        add(titlePanel, BorderLayout.NORTH);
        
        //主面板
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 245, 238)); // 背景色
        add(mainPanel, BorderLayout.CENTER);

        //左邊作畫區
        paintingPanel = new JPanel(new BorderLayout());
        paintingPanel.setBackground(new Color(255, 245, 238));
        mainPanel.add(paintingPanel, BorderLayout.CENTER);

        //畫布面板
        drawPanel = new DrawPanel();
        paintingPanel.add(drawPanel, BorderLayout.CENTER);

        //畫筆區
        brushPanel = new JPanel(new GridLayout(5, 1));
        brushPanel.setBackground(new Color(255, 245, 238));
        brushPanel.setLayout(new BoxLayout(brushPanel, BoxLayout.Y_AXIS));

        // Create buttons with images
        redPen = new JRadioButton("碳水化合物");
        redPen.setForeground(Color.RED); // Set the text color to red
        redPen.setBackground(new Color(255, 245, 238));
        greenPen = new JRadioButton("蔬菜");
        greenPen.setForeground(Color.GREEN); // Set the text color to green
        greenPen.setBackground(new Color(255, 245, 238));
        yellowPen = new JRadioButton("蛋白質");
        yellowPen.setForeground(new Color(255, 215, 0)); // Set the text color to yellow
        yellowPen.setBackground(new Color(255, 245, 238));
        repaint = new JButton("重新繪製");
        calculateOutput = new JButton("計算結果");

        brushColor = new ButtonGroup();
        brushColor.add(redPen);
        brushColor.add(greenPen);
        brushColor.add(yellowPen);

        //處理顏色變換
        ActionListener colorChangeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (redPen.isSelected()) {
                    currentColor = Color.RED;
                } else if (greenPen.isSelected()) {
                    currentColor = Color.GREEN;
                } else if (yellowPen.isSelected()) {
                    currentColor = Color.YELLOW;
                }
                drawPanel.setCurrentColor(currentColor);
            }
        };

        redPen.addActionListener(colorChangeListener);
        greenPen.addActionListener(colorChangeListener);
        yellowPen.addActionListener(colorChangeListener);
        repaint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPanel.clear();
                ratio.setText("");
                match.setText("");
                advice.setText("");
                advice.setVisible(false);
            }
        });
        brushPanel.add(redPen);
        brushPanel.add(greenPen);
        brushPanel.add(yellowPen);
        brushPanel.add(repaint);
        brushPanel.add(calculateOutput);
        paintingPanel.add(brushPanel, BorderLayout.EAST);

        //右邊控制區
        addAndDisplayPanel = new JPanel();
        addAndDisplayPanel.setBackground(new Color(255, 245, 238));
        addAndDisplayPanel.setLayout(new BoxLayout(addAndDisplayPanel, BoxLayout.Y_AXIS));
        mainPanel.add(addAndDisplayPanel, BorderLayout.EAST);

        //新增區
        addPhotoPanel = new JPanel();
        addPhotoPanel.setBackground(new Color(255, 245, 238));
        addPhotoPanel.setLayout(new BoxLayout(addPhotoPanel, BoxLayout.Y_AXIS));
        addPhotoPanel.setBorder(BorderFactory.createTitledBorder("新增照片"));
        addPhotoButton = new JButton("選擇照片");
        addPhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePhoto();
            }
        });
        addPhotoPanel.add(addPhotoButton);

        //顯示結果區
        resultPanel = new JPanel();
        resultPanel.setBackground(new Color(255, 245, 238));
        resultPanel.setBorder(BorderFactory.createTitledBorder("計算結果"));
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        JLabel label1 = new JLabel("蔬菜 : 蛋白質 : 澱粉");
        ratio = new JTextField(10);
        match = new JTextField(10);
        advice = new JTextArea();
        ratio.setEditable(false);
        match.setEditable(false);
        advice.setEditable(false);
        ratio.setBackground(new Color(255, 245, 238));
        match.setBackground(new Color(255, 245, 238));
        advice.setBackground(new Color(255, 245, 238));
        advice.setVisible(false);
        resultPanel.add(label1);
        resultPanel.add(ratio);
        resultPanel.add(match);
        resultPanel.add(advice);

        addAndDisplayPanel.add(addPhotoPanel);
        addAndDisplayPanel.add(resultPanel);

        calculateOutput.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Map<Color, Double> areasByColor = drawPanel.calculateAreasByColor();

        // 計算總面积
            double totalArea = areasByColor.values().stream().mapToDouble(Double::doubleValue).sum();

        // 計算比例
            double greenArea = areasByColor.getOrDefault(Color.GREEN, 0.0);
            double yellowArea = areasByColor.getOrDefault(Color.YELLOW, 0.0);
            double redArea = areasByColor.getOrDefault(Color.RED, 0.0);

            double greenRatio = (greenArea / totalArea)*100;
            double yellowRatio = (yellowArea / totalArea)*100;
            double redRatio = (redArea / totalArea)*100;
            ratio.setText(String.format("%.2f : %.2f : %.2f", greenRatio, yellowRatio, redRatio));
            double redYellowArea = redArea + yellowArea;
            double ratio = greenArea / redYellowArea;
            advice.setVisible(false);

            if ((greenRatio - (yellowRatio*2)) >= 0 && (greenRatio - (redRatio*2)) >= 0 ) 
            {
                // 符合條件
                match.setText("符合2:1:1");
            }
            else if(((greenRatio - (yellowRatio*2)) < 0 && Math.abs(greenRatio - (yellowRatio*2))<=2)
                        || ((greenRatio - (redRatio*2)) < 0 && Math.abs(greenRatio - (redRatio*2))<=2))
            {
                match.setText("符合2:1:1");
            }
            else 
            {
                // 不符合條件
                match.setText("不符合2:1:1 !");
                JLabel label2 = new JLabel("建議：");
                advice.setVisible(true);
                advice.setText(giveAdvice(greenRatio, yellowRatio, redRatio));
                advice.setEditable(false);
                advice.setBackground(new Color(255, 245, 238));
                
            }
        }
});
        
    }

    private void choosePhoto() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            drawPanel.setImage(path);
        }
        else{
            JOptionPane.showMessageDialog(this, "未選擇照片", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String giveAdvice(double green, double yellow, double red) {
        // 計算邏輯
        String str = "建議：\n";
        if((green - yellow*2) < 0 || (green - red*2) < 0)
        {
            str += "增加蔬菜量\n";
        }
        if((green - yellow*2) < 0)
        {
            str += "減少蛋白質\n";
        }
        if((green - red*2) < 0)
        {
            str += "減少澱粉\n";
        }
        return str;
    }
}
