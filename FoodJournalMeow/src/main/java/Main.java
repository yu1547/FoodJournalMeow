
public class Main {
    public static void main(String[] args) {
        // 創建並顯示主視窗
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            }
        });
    }
}
