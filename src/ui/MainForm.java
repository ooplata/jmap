package ui;

import javax.swing.*;
import java.awt.*;

public class MainForm {
    private JPanel root;
    private JButton clearBtn;
    private JButton dijkstraBtn;
    private JPanel mapHolder;

    public static void show() {
        var frame = new JFrame("JMap");
        var form = new MainForm();

        frame.setContentPane(form.root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(600, 420));
        frame.setResizable(false);

        form.mapHolder.add(new MapPanel());
    }
}
