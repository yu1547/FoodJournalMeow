package ntou.cs.java2024;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.image.BufferedImage;

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
                currentPath.clear();
                currentPath.add(e.getPoint());
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentPath.add(e.getPoint());
                allPaths.add(new ArrayList<>(currentPath)); // 將當前路徑加到所有路徑中
                allColors.add(currentColor);
                currentPath.clear();
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentPath.add(e.getPoint());
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
            Image originalImage = javax.imageio.ImageIO.read(new java.io.File(imagePath));
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            image = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(originalImage, 0, 0, panelWidth, panelHeight, null);
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
        Path2D path = new Path2D.Double();
        if (!points.isEmpty()) {
            path.moveTo(points.get(0).x, points.get(0).y);
            for (Point p : points) {
                path.lineTo(p.x, p.y);
            }
            path.closePath();
        }
        return path;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
        Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(currentColor);
            g2d.setStroke(new BasicStroke(2));
            for (int i = 0; i < allPaths.size(); i++) {
                g2d.setColor(allColors.get(i)); // 設置颜色
                Path2D path2D = createPathFromPoints(allPaths.get(i));
                g2d.draw(path2D);
            }
            if (!currentPath.isEmpty()) {
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
