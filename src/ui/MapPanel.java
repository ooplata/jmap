package ui;

import graphing.Edge;
import graphing.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MapPanel extends JPanel {
    private final ArrayList<Vertex> vertices = new ArrayList<>();
    private Vertex prev;

    public MapPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                if (!canAdd(x, y)) return;

                var vertex = new Vertex(x, y);
                vertices.add(vertex);

                if (prev != null) prev.edges.add(new Edge(vertex, 1));
                prev = vertex;

                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);

        var graphics2D = (Graphics2D) g;
        for (var vertex : vertices) {
            graphics2D.fillOval(vertex.x, vertex.y, 8, 8);
            for (var v : vertex.edges) {
                g.drawLine(v.destination().x + 4, v.destination().y + 4, vertex.x + 4, vertex.y + 4);
            }
        }
    }

    private boolean canAdd(int x, int y) {
        for (int xOffset = -8; xOffset <= 8; xOffset++) {
            for (int yOffset = -8; yOffset <= 8; yOffset++) {
                var vertex = new Vertex(x + xOffset, y + yOffset);
                if (vertices.contains(vertex)) {
                    return false;
                }
            }
        }

        return true;
    }
}
