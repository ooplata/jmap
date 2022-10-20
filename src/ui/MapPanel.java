package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

public class MapPanel extends JPanel {
    private final HashSet<Point> points = new HashSet<>();
    private Point prev = null;

    public MapPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                var point = new Point(e.getX(), e.getY());
                if (!points.contains(point)) {
                    points.add(point);
                    repaint();

                    if (prev != null) {
                        //TODO: ask user for the cost, draw line
                        prev = null;
                    } else {
                        prev = point;
                    }
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.RED);

        for (Point p : points) {
            graphics2D.fillOval(p.x, p.y, 5, 5);
        }
    }
}
