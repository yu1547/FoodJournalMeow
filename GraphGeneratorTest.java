import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class GraphGeneratorTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphGenerator example = new GraphGenerator("Height and weight change chart", "week");
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
