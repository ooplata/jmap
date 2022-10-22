package ui;

import assets.Resources;
import graphing.Edge;
import graphing.Vertex;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MapPanel extends JPanel {
    private final ArrayList<Vertex> vertices = new ArrayList<>();
    private Vertex selected;
    private Vertex prev;

    private BufferedImage bg;

    public MapPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                if (selected != null) {
                    var curr = vertexAt(x, y);
                    if (curr == null) {
                        addVertex(selected, x, y);
                    } else {
                        if (!curr.parent.equals(selected) || !selected.parent.equals(curr)) {
                            var edge = new Edge(selected, 1);
                            curr.edges.add(edge);
                            System.out.println("added");
                        }
                    }

                    selected = null;
                } else {
                    selected = vertexAt(x, y);
                    if (selected == null) prev = addVertex(prev, x, y);
                }

                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bg == null) {
            try {
                bg = ImageIO.read(Resources.getResource("Background.png"));
            } catch (IOException ignored) {
            }
        }

        g.drawImage(bg, 0, 0, null);

        g.setColor(Color.RED);

        var graphics2D = (Graphics2D) g;
        for (var vertex : vertices) {
            graphics2D.fillOval(vertex.x, vertex.y, 8, 8);
            for (var v : vertex.edges) {
                g.drawLine(v.destination().x + 4, v.destination().y + 4, vertex.x + 4, vertex.y + 4);
            }
        }

        g.setColor(Color.BLUE);
        if (selected != null) {
            drawSquare(g, selected.x, selected.y);
        } else if (prev != null) {
            drawSquare(g, prev.x, prev.y);
        }
    }

    private Vertex vertexAt(int x, int y) {
        for (int xOffset = -8; xOffset <= 8; xOffset++) {
            for (int yOffset = -8; yOffset <= 8; yOffset++) {
                var vertex = new Vertex(null, x + xOffset, y + yOffset);
                int index = vertices.indexOf(vertex);

                if (index >= 0) return vertices.get(index);
            }
        }
        return null;
    }

    private Vertex addVertex(Vertex parent, int x, int y) {
        var vertex = new Vertex(parent, x, y);
        vertices.add(vertex);

        if (parent != null) {
            var edge = new Edge(vertex, 1);
            parent.edges.add(edge);
        }
        return vertex;
    }

    private void drawSquare(Graphics g, int x, int y) {
        g.drawLine(x, y + 8, x + 8, y + 8);
        g.drawLine(x, y, x + 8, y);
        g.drawLine(x, y, x, y + 8);
        g.drawLine(x + 8, y, x + 8, y + 8);
    }
}
