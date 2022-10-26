package jmap.ui.dialogs;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimpleMessageDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel msgTitle;
    private JTextPane msgContent;

    private SimpleMessageDialog(String title, String message) {
        this.setTitle("Mensaje");
        msgTitle.setText(title);
        msgContent.setText(message);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> close());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        contentPane.registerKeyboardAction(e -> close(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void open(String title, String message) {
        var dialog = new SimpleMessageDialog(title, message);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void close() {
        dispose();
    }
}
