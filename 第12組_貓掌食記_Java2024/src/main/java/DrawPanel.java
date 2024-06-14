
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DrawPanel extends JPanel {
    private List<List<Point>> allPaths = new ArrayList<>();
    private List<Color> allColors = new ArrayList<>();
    private List<Point> currentPath = new ArrayList<>();
    private Color currentColor = Color.BLACK;
    private BufferedImage image;

    public DrawPanel() {
        setBackground(new Color(255, 245, 238));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (image == null) {
                    JOptionPane.showMessageDialog(DrawPanel.this, "請先選擇照片", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                currentPath.clear();//如果有圖像，會清除當前的繪圖路徑並記錄滑鼠按下的起始點
                currentPath.add(e.getPoint());
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (image == null) {
                    return;
                }
                currentPath.add(e.getPoint());//如果有圖像，會記錄滑鼠釋放的結束點
                allPaths.add(new ArrayList<>(currentPath)); // 將當前路徑加到所有路徑中
                allColors.add(currentColor);//記錄當前使用的顏色
                currentPath.clear();//然後清除當前路徑以便下次繪圖使用。
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (image == null) {
                    return;
                }
                currentPath.add(e.getPoint());//如果有圖像，會記錄滑鼠拖動過的每個點，形成一條路徑。
                repaint();
            }
        });
    }

    public void setCurrentColor(Color color) {
        currentColor = color;
    }

    public void setImage(String imagePath) {
        // Load and set image
        try {
            BufferedImage originalImage = javax.imageio.ImageIO.read(new java.io.File(imagePath));
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            double originalWidth = originalImage.getWidth();
            double originalHeight = originalImage.getHeight();
            double aspectRatio = originalWidth / originalHeight;

            int newWidth, newHeight;

            if (panelWidth / aspectRatio <= panelHeight) {
                newWidth = panelWidth;
                newHeight = (int) (panelWidth / aspectRatio);
            } else {
                newHeight = panelHeight;
                newWidth = (int) (panelHeight * aspectRatio);
            }

            image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        repaint();
    }

    public void clear() {
        allPaths.clear();
        allColors.clear();
        repaint();
    }

    private Path2D createPathFromPoints(List<Point> points) {
        Path2D path = new Path2D.Double();//處理雙精度坐標
        if (!points.isEmpty()) {
            path.moveTo(points.get(0).x, points.get(0).y);//移到起始點
            for (Point p : points) {
                path.lineTo(p.x, p.y);//連接每一個點形成路徑
            }
            path.closePath();//要計算面積，所以把路徑封閉
        }
        return path;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            int x = (panelWidth - imageWidth) / 2;
            int y = (panelHeight - imageHeight) / 2;
            g.drawImage(image, x, y, this);
        }
        //創建一個新的 Graphics2D 對象來繪製所有已記錄的路徑，每條路徑使用對應的顏色
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(currentColor);
        g2d.setStroke(new BasicStroke(2));
        for (int i = 0; i < allPaths.size(); i++) {
            g2d.setColor(allColors.get(i)); // 設置颜色
            Path2D path2D = createPathFromPoints(allPaths.get(i));
            g2d.draw(path2D);
        }
        if (!currentPath.isEmpty()) {//如果當前有未完成的路徑，則使用當前顏色繪製這條路徑。
            g2d.setColor(currentColor);
            Path2D currentPath2D = createPathFromPoints(currentPath);
            g2d.draw(currentPath2D);
        }
        g2d.dispose();
    }

    public double calculateArea(List<Point> points) {
        int n = points.size();
        double area = 0.0;
        for (int i = 0; i < n; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % n); // wrap around to the first point
            area += p1.x * p2.y - p2.x * p1.y;
        }
        return Math.abs(area) / 2.0;
    }

    public Map<Color, Double> calculateAreasByColor() {
        Map<Color, Double> areasByColor = new HashMap<>();
        for (int i = 0; i < allPaths.size(); i++) {
            Color color = allColors.get(i);
            List<Point> path = allPaths.get(i);
            double area = calculateArea(path);
            areasByColor.put(color, areasByColor.getOrDefault(color, 0.0) + area);
        }
        return areasByColor;
    }
}
