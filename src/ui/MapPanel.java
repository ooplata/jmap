package ui;

import assets.Resources;
import graphing.ShortestPathAlgorithm;
import graphing.ShortestPathHelper;
import graphing.SolarSystemHelper;
import graphing.SolarSystemItem;
import ui.dialogs.SimpleMessageDialog;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapPanel extends JPanel {
    private final ArrayList<SolarSystemItem> items = new ArrayList<>();
    private final ArrayList<SolarSystemItem> shortestPath = new ArrayList<>();

    private SolarSystemItem start;
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

        if (!items.isEmpty()) {
            int index = getItemIndexFromPoint(point);
            var itm = new SolarSystemItem(index);

            boolean hasItm = items.contains(itm);
            if (hasItm && e.isControlDown()) {
                start = itm;
                SimpleMessageDialog.open("Planeta inicial actualizado", "Tu recorrido comenzará desde " + itm.getLabel() + ".");
                return;
            } else if (hasItm && e.isShiftDown()) {
                selected = itm;
                repaint();
                return;
            }
        }

        var itm = addItem(selected, point);
        if (itm == null) {
            SimpleMessageDialog.open("Área no permitida", "Solo puedes seleccionar planetas o la luna.");
            return;
        }

        selected = itm;
        if (start == null) {
            start = selected;
        }
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
        if (items.size() < 2) {
            SimpleMessageDialog.open("No hay suficientes destinos", "Añade más destinos antes de continuar.");
            return;
        } else if (selected == null) {
            SimpleMessageDialog.open("Seleccione un planeta", "Selecciona un planeta antes de continuar.");
            return;
        }

        if (selected.equals(start)) {
            SimpleMessageDialog.open("Seleccione otro planeta", "Vas a comenzar desde el planeta seleccionado actualmente, selecciona un nuevo destino.");
            return;
        }

        var result = ShortestPathHelper.getShortestPath(ShortestPathAlgorithm.FLOYD_WARSHALL, items, start, selected);
        shortestPath.clear();
        result.path().forEach(shortestPath::add);

        repaint();

        var builder = new StringBuilder();
        builder.append("El camino más corto desde ").append(start.getLabel()).append(" hasta ").append(selected.getLabel()).append(" es:\n\n");

        for (var itm : result.path()) {
            builder.append(itm.getLabel()).append(" -> ");
        }

        // Remove hanging arrow after the loop
        builder.delete(builder.length() - 4, builder.length());

        builder.append("\n\nDistancia recorrida en total: ").append(result.totalCost()).append("x10³km");
        SimpleMessageDialog.open("Resultado", builder.toString());
    }

    public void resetItems() {
        selected = null;

        items.clear();
        shortestPath.clear();

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);

        var graphics2D = (Graphics2D) g;
        drawGraph(graphics2D, items);

        if (!shortestPath.isEmpty()) {
            drawPath(graphics2D, shortestPath);
            shortestPath.clear();
        }

        if (selected != null) {
            graphics2D.setColor(Color.YELLOW);
            graphics2D.fillOval((int) selected.getBounds().getCenterX() - 2, (int) selected.getBounds().getCenterY() - 2, 12, 12);
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

    private void drawGraph(Graphics2D g, List<SolarSystemItem> items) {
        var prev = g.getColor();
        g.setColor(Color.ORANGE);

        for (var vertex : items) {
            int centerX = (int) vertex.getBounds().getCenterX();
            int centerY = (int) vertex.getBounds().getCenterY();
            g.fillOval(centerX, centerY, 8, 8);

            for (var v : vertex.connections) {
                int connCenterX = (int) v.getBounds().getCenterX();
                int connCenterY = (int) v.getBounds().getCenterY();
                drawLine(g, connCenterX, connCenterY, centerX, centerY, 1);
            }
        }

        g.setColor(prev);
    }

    private void drawPath(Graphics2D g, List<SolarSystemItem> items) {
        var prev = g.getColor();
        g.setColor(Color.RED);

        SolarSystemItem prevVertex = null;
        for (var vertex : items) {
            int centerX = (int) vertex.getBounds().getCenterX();
            int centerY = (int) vertex.getBounds().getCenterY();
            g.fillOval(centerX, centerY, 8, 8);

            if (prevVertex != null) {
                int connCenterX = (int) prevVertex.getBounds().getCenterX();
                int connCenterY = (int) prevVertex.getBounds().getCenterY();
                drawLine(g, connCenterX, connCenterY, centerX, centerY, 3);
            }
            prevVertex = vertex;
        }

        g.setColor(prev);
    }

    private void drawLine(Graphics2D g, int x1, int y1, int x2, int y2, int thickness) {
        var prev = g.getStroke();
        g.setStroke(new BasicStroke(thickness));

        g.drawLine(x1 + 4, y1 + 4, x2 + 4, y2 + 4);
        g.setStroke(prev);
    }
}
