package ui;

import assets.Resources;
import graphing.DijkstraHelper;
import graphing.SolarSystemHelper;
import graphing.SolarSystemItem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MapPanel extends JPanel {
    private final ArrayList<SolarSystemItem> items = new ArrayList<>();
    private SolarSystemItem selected;

    private BufferedImage bg;

    public MapPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MapPanel.this.onClick(e);
            }
        });
    }

    private void onClick(MouseEvent e) {
        var point = e.getPoint();
        selected = addItem(selected, point);

        repaint();
    }

    private SolarSystemItem addItem(SolarSystemItem parent, Point bounds) {
        int index = getItemIndexFromPoint(bounds);
        if (index == -1) return null;

        var item = new SolarSystemItem(index);
        int listIndex = items.indexOf(item);

        if (listIndex != -1) {
            item = items.get(listIndex);
        } else {
            items.add(item);
        }

        if (parent != null) {
            parent.addConnection(item);
        }
        return item;
    }

    private int getItemIndexFromPoint(Point point) {
        for (int i = 0; i < 10; i++) {
            if (SolarSystemHelper.getItemBounds(i).contains(point)) {
                return i;
            }
        }

        return -1;
    }

    public void showResults() {
        if (selected == null) {
            return;
        }

        var start = items.get(0);
        var path = DijkstraHelper.getShortestPath(items, start, selected);

        for (var itm : path) {
            System.out.print(itm.getLabel() + " -> ");
        }
        System.out.println(selected.getLabel());
    }

    public void resetItems() {
        selected = null;
        items.clear();

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBackground(g);
        g.setColor(Color.RED);

        var graphics2D = (Graphics2D) g;
        for (var vertex : items) {
            int centerX = (int) vertex.getBounds().getCenterX();
            int centerY = (int) vertex.getBounds().getCenterY();
            graphics2D.fillOval(centerX, centerY, 8, 8);

            for (var v : vertex.connections) {
                int connCenterX = (int) v.getBounds().getCenterX();
                int connCenterY = (int) v.getBounds().getCenterY();
                g.drawLine(connCenterX + 4, connCenterY + 4, centerX + 4, centerY + 4);
            }
        }

        g.setColor(Color.BLUE);
        if (selected != null) {
            drawSquare(g, (int) selected.getBounds().getCenterX(), (int) selected.getBounds().getCenterY());
        }
    }

    private void drawBackground(Graphics g) {
        if (bg == null) {
            try {
                bg = ImageIO.read(Resources.getResource("Background.png"));
            } catch (IOException ignored) {
            }
        }

        g.drawImage(bg, 0, 0, null);
    }

    private void drawSquare(Graphics g, int x, int y) {
        g.drawLine(x, y + 8, x + 8, y + 8);
        g.drawLine(x, y, x + 8, y);
        g.drawLine(x, y, x, y + 8);
        g.drawLine(x + 8, y, x + 8, y + 8);
    }
}
