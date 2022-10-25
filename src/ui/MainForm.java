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

        SimpleMessageDialog.open("¡Bienvenido a JMap!", "Haz click en los planetas que quieras añadir a tu recorrido.\n\nSi quieres cambiar el planeta de origen, mantén presionado Ctrl y haz click en otro planeta de tu recorrido.\n\nSi quieres seleccionar un planeta que ya esté en tu recorrido, mantén presionado Shift mientras le haces click.");
    }
}
