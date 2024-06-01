import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class View1 extends JPanel {
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
    private String photoPath;

    private Meals meals;

    public View1() {
        meals = new Meals();
        setLayout(new BorderLayout());

        // 加入貓爪圖片和標題
        ImageIcon icon = new ImageIcon("cat.png");
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

        // 右邊的控制區
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(new Color(255, 245, 238)); // 背景色
        controlPanel.setBorder(BorderFactory.createTitledBorder("新增照片"));
        mainPanel.add(controlPanel, BorderLayout.EAST);

        // 餐點選擇
        JPanel mealPanel = new JPanel();
        mealPanel.setBackground(new Color(255, 245, 238)); // 背景色
        ButtonGroup mealGroup = new ButtonGroup();
        breakfastButton = new JRadioButton("早餐");
        lunchButton = new JRadioButton("午餐");
        dinnerButton = new JRadioButton("晚餐");
        mealGroup.add(breakfastButton);
        mealGroup.add(lunchButton);
        mealGroup.add(dinnerButton);
        mealPanel.add(breakfastButton);
        mealPanel.add(lunchButton);
        mealPanel.add(dinnerButton);

        controlPanel.add(mealPanel);

        // 照片選擇
        JPanel photoSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        photoSelectionPanel.setBackground(new Color(255, 245, 238)); // 背景色
        JButton choosePhotoButton = new JButton("選擇檔案");
        choosePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePhoto();
            }
        });
        photoSelectionPanel.add(new JLabel("照片："));
        photoSelectionPanel.add(choosePhotoButton);
        controlPanel.add(photoSelectionPanel);

        // 心情輸入
        JPanel moodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        moodPanel.setBackground(new Color(255, 245, 238)); // 背景色
        moodPanel.add(new JLabel("心情："));
        moodField = new JTextField(10);
        moodPanel.add(moodField);
        controlPanel.add(moodPanel);

        // 新增餐點按鈕
        JPanel addPanel = new JPanel();
        addPanel.setBackground(new Color(255, 245, 238)); // 背景色
        addButton = new JButton("新增餐點");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEntry();
            }
        });
        addPanel.add(addButton);
        controlPanel.add(addPanel);

        // 存檔按鈕
        JPanel savePanel = new JPanel();
        savePanel.setBackground(new Color(255, 245, 238)); // 背景色
        saveButton = new JButton("存檔");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEntry();
            }
        });
        savePanel.add(saveButton);
        controlPanel.add(savePanel);

        // 匯出圖片格式選擇
        JPanel exportFormatPanel = new JPanel();
        exportFormatPanel.setBackground(new Color(255, 245, 238)); // 背景色
        exportFormatPanel.add(new JLabel("匯出圖片格式"));

        ButtonGroup exportGroup = new ButtonGroup();
        jpgButton = new JRadioButton("jpg");
        pdfButton = new JRadioButton("pdf");
        exportGroup.add(jpgButton);
        exportGroup.add(pdfButton);
        exportFormatPanel.add(jpgButton);
        exportFormatPanel.add(pdfButton);

        controlPanel.add(exportFormatPanel);

        // 匯出圖片按鈕
        JPanel exportPanel = new JPanel();
        exportPanel.setBackground(new Color(255, 245, 238)); // 背景色
        exportButton = new JButton("匯出圖片");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportImage();
            }
        });
        exportPanel.add(exportButton);
        controlPanel.add(exportPanel);
    }

    // 照片選擇邏輯
    private void choosePhoto() {
        String path = getFilePath();
        if (path != null) {
            photoPath = path;
            ImageIcon photoIcon = new ImageIcon(path);
            photoLabel.setIcon(photoIcon);
        }
    }

    // 新增按鈕的事件處理器
    private void addEntry() {
        String type = "";
        if (breakfastButton.isSelected()) {
            type = "早餐";
        } else if (lunchButton.isSelected()) {
            type = "午餐";
        } else if (dinnerButton.isSelected()) {
            type = "晚餐";
        }
        String mood = moodField.getText();
        String date = java.time.LocalDate.now().toString(); // 使用當前日期
        meals.addFoodItem(date, type, mood, photoPath);
    }

    // 存檔按鈕的事件處理器
    private void saveEntry() {
        meals.exportMealImage();
        String date = java.time.LocalDate.now().toString(); // 使用當前日期
        ImageIcon savedImageIcon = new ImageIcon(date + ".png");
        photoLabel.setIcon(savedImageIcon);
    }

    // 匯出圖片按鈕的事件處理器
    private void exportImage() {
        // 根據選擇的格式進行圖片匯出
        if (jpgButton.isSelected()) {
            System.out.println("匯出成jpg格式");
        } else if (pdfButton.isSelected()) {
            System.out.println("匯出成pdf格式");
        }
    }

    // 從檔案總管選擇一張照片並回傳其路徑
    private String getFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            // 檢查選擇的檔案是否存在
            if (new File(filePath).exists()) {
                return filePath;
            }
        }
        return null;
    }
}
