import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MealPanel extends JPanel {
    private JRadioButton breakfastButton;
    private JRadioButton lunchButton;
    private JRadioButton dinnerButton;
    private JTextField moodField;
    private JButton addButton;
    private JButton saveButton;
    private JButton exportButton;
    private JRadioButton jpgButton;
    private JRadioButton pdfButton;
    private JLabel photoLabel;
    private Exporter exporter;

    private String selectedPhotoPath;
    private Meals meals;
    private final String MOOD_HINT = "最多36字";

    public MealPanel() {
        exporter = new Exporter();
        meals = new Meals();
        setLayout(new BorderLayout());
        selectedPhotoPath = "images/default2.png";


        // 加入貓爪圖片和標題
        ImageIcon icon = new ImageIcon("images/cat.png");
        JLabel titleLabel = new JLabel("生成食記圖", icon, JLabel.LEFT);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(255, 245, 238)); // 背景色
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);

        // 中間的主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 245, 238)); // 背景色
        add(mainPanel, BorderLayout.CENTER);

        // 左邊的照片區
        JPanel photoPanel = new JPanel();
        photoPanel.setBackground(new Color(255, 245, 238)); // 背景色
        photoLabel = new JLabel();
        photoPanel.add(photoLabel);
        mainPanel.add(photoPanel, BorderLayout.CENTER);
        ImageIcon photoIcon = new ImageIcon("images/background.png");
        photoLabel.setIcon(photoIcon);

        // 右邊的控制區
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(new Color(255, 245, 238)); // 背景色
        controlPanel.setBorder(BorderFactory.createTitledBorder("新增照片"));
        mainPanel.add(controlPanel, BorderLayout.EAST);

        // 餐點選擇
        ButtonGroup mealGroup = new ButtonGroup();
        breakfastButton = new JRadioButton("早餐");
        lunchButton = new JRadioButton("午餐");
        dinnerButton = new JRadioButton("晚餐");

        JPanel mealPanel = new JPanel();
        mealPanel.setBackground(new Color(255, 245, 238)); // 背景色
        mealPanel.add(breakfastButton);
        mealPanel.add(lunchButton);
        mealPanel.add(dinnerButton);

        mealGroup.add(breakfastButton);
        mealGroup.add(lunchButton);
        mealGroup.add(dinnerButton);

        controlPanel.add(mealPanel);

        // 照片選擇
        JButton choosePhotoButton = new JButton("選擇檔案");
        choosePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePhoto();
            }
        });

        JPanel photoSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        photoSelectionPanel.setBackground(new Color(255, 245, 238)); // 背景色
        photoSelectionPanel.add(new JLabel("照片："));
        photoSelectionPanel.add(choosePhotoButton);
        controlPanel.add(photoSelectionPanel);

        // 心情輸入
        JPanel moodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        moodPanel.setBackground(new Color(255, 245, 238)); // 背景色
        moodPanel.add(new JLabel("心情："));
        moodField = new JTextField(MOOD_HINT, 10);
        moodField.setForeground(Color.GRAY);

        moodField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (moodField.getText().equals(MOOD_HINT)) {
                    moodField.setText("");
                    moodField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (moodField.getText().isEmpty()) {
                    moodField.setForeground(Color.GRAY);
                    moodField.setText(MOOD_HINT);
                }
            }
        });

        moodField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (moodField.getText().length() > 36) {
                    moodField.setText(moodField.getText().substring(0, 36));
                }
            }
        });

        moodPanel.add(moodField);
        controlPanel.add(moodPanel);

        // 新增餐點按鈕
        addButton = new JButton("新增餐點");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEntry();
            }
        });
        JPanel addButtonPanel = new JPanel();
        addButtonPanel.add(addButton);
        addButtonPanel.setBackground(new Color(255, 245, 238)); // 背景色
        controlPanel.add(addButtonPanel);

        // 生成每日三餐圖按鈕
        saveButton = new JButton("生成每日三餐圖");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEntry();
            }
        });
        JPanel saveButtonPanel = new JPanel();
        saveButtonPanel.add(saveButton);
        saveButtonPanel.setBackground(new Color(255, 245, 238)); // 背景色
        controlPanel.add(saveButtonPanel);

        // 匯出圖片選項
        JPanel exportPanel = new JPanel();
        exportPanel.setBackground(new Color(255, 245, 238)); // 背景色
        exportPanel.add(new JLabel("匯出圖片"));

        ButtonGroup exportGroup = new ButtonGroup();
        jpgButton = new JRadioButton("png");
        pdfButton = new JRadioButton("pdf");

        exportGroup.add(jpgButton);
        exportGroup.add(pdfButton);

        exportPanel.add(jpgButton);
        exportPanel.add(pdfButton);
        controlPanel.add(exportPanel);

        // 匯出圖片按鈕
        exportButton = new JButton("匯出圖片");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportImage();
            }
        });
        JPanel exportButtonPanel = new JPanel();
        exportButtonPanel.add(exportButton);
        exportButtonPanel.setBackground(new Color(255, 245, 238)); // 背景色
        controlPanel.add(exportButtonPanel);
    }

    // 照片選擇邏輯
    private void choosePhoto() {
        String path = getFilePath2();
        if (path != null) {
            selectedPhotoPath = path;
            ImageIcon photoIcon = new ImageIcon(path);
            photoLabel.setIcon(photoIcon);
        }
        else{
            selectedPhotoPath = "images/default2.png";
        }
    }

    // 新增按鈕的事件處理器
    private void addEntry() {
        String type = getSelectedMealType();
        if (type == null || selectedPhotoPath == null || selectedPhotoPath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請選擇餐點類型");
            return;
        }

        String mood = moodField.getText();
        if (mood.equals(MOOD_HINT)) {
            mood = "";
        }
        if (mood.length() > 36) {
            mood = mood.substring(0, 36);
        }
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        meals.addFoodItem(date, type, mood, selectedPhotoPath);
        JOptionPane.showMessageDialog(this, "餐點已新增");
        selectedPhotoPath = "images/default2.png";

        // 清空心情輸入框
        moodField.setText(MOOD_HINT);
        moodField.setForeground(Color.GRAY);
    }

    // 存檔按鈕的事件處理器
    private void saveEntry() {
        meals.exportMealImage();
        JOptionPane.showMessageDialog(this, "圖片已存檔");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("results/"+date+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon photoIcon = new ImageIcon(img);
        photoLabel.setIcon(null);
        photoLabel.setIcon(photoIcon);
    }

    // 獲取選中的餐點類型
    private String getSelectedMealType() {
        if (breakfastButton.isSelected()) {
            return "早餐";
        } else if (lunchButton.isSelected()) {
            return "午餐";
        } else if (dinnerButton.isSelected()) {
            return "晚餐";
        }
        return null;
    }

    private void exportImage() {
        if (jpgButton.isSelected()) {
            String filePath = getFilePath();  // 從檔案總管選擇一張照片並獲取其路徑
            if (filePath != null) {  // 如果成功選擇了一張照片
                exporter.exportPng(filePath);  // 使用 Exporter 的實例來調用 exportPng 方法
            }
        } else if (pdfButton.isSelected()) {
            exporter.imagesToPDF();  // 使用 Exporter 的實例來調用 imagesToPDF 方法
        }
    }

    // 從檔案總管選擇一張照片並回傳其路徑
    private String getFilePath() {
        JFileChooser fileChooser = new JFileChooser(new File("./results"));
        fileChooser.setDialogTitle("選擇圖片");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            // 檢查選擇的檔案是否存在
            if(new File(filePath).exists()) {
                return filePath;
            }
        }
        return null;
    }

    private String getFilePath2() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("選擇圖片");
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            // 檢查選擇的檔案是否存在
            if(new File(filePath).exists()) {
                return filePath;
            }
        }
        return null;
    }
}
