package ui;

import ui.dialogs.SimpleMessageDialog;

import javax.swing.*;
import java.awt.*;

public class MainForm {
    private final MapPanel map;
    private JPanel root;
    private JButton clearBtn;
    private JButton dijkstraBtn;
    private JPanel mapHolder;

    public MainForm() {
        map = new MapPanel();
        mapHolder.add(map);

        clearBtn.setBorder(BorderFactory.createEmptyBorder());
        dijkstraBtn.setBorder(BorderFactory.createEmptyBorder());

        clearBtn.addActionListener(e -> map.resetItems());
        dijkstraBtn.addActionListener(e -> map.showResults());
    }

    public static void show() {
        var frame = new JFrame("JMap");
        var form = new MainForm();

        frame.setContentPane(form.root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(600, 466));
        frame.setResizable(false);

        SimpleMessageDialog.open("¡¡¡Bienvenido a JMap!!!", "Crea el grafo desde el planeta donde quieras comenzar, después mueve el cursor creando el grafo hasta el planeta destino.");
    }
}
