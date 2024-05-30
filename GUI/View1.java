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

    public View1() {
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
        moodField = new JTextField(10);
        moodPanel.add(moodField);
        controlPanel.add(moodPanel);

        // 新增按鈕
        addButton = new JButton("新增");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEntry();
            }
        });
        controlPanel.add(addButton);

        // 存檔按鈕
        saveButton = new JButton("存檔");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEntry();
            }
        });
        controlPanel.add(saveButton);

        // 匯出圖片選項
        JPanel exportPanel = new JPanel();
        exportPanel.setBackground(new Color(255, 245, 238)); // 背景色
        exportPanel.add(new JLabel("匯出圖片"));

        ButtonGroup exportGroup = new ButtonGroup();
        jpgButton = new JRadioButton("jpg");
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
        controlPanel.add(exportButton);
    }

    // 照片選擇邏輯
    private void choosePhoto() {
        String path = getFilePath();
        if(path != null) {
        }
    }

    // 新增按鈕的事件處理器
    private void addEntry() {
        // 新增按鈕的事件處理器
    }

    // 存檔按鈕的事件處理器
    private void saveEntry() {
        // 存檔按鈕的事件處理器
    }

    // 匯出圖片按鈕的事件處理器
    private void exportImage() {
        // 匯出圖片按鈕的事件處理器
    }

    // 從檔案總管選擇一張照片並回傳其路徑
    private String getFilePath() {
        JFileChooser fileChooser = new JFileChooser();
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
