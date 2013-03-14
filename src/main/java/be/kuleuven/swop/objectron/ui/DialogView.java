package be.kuleuven.swop.objectron.ui;

import javax.swing.*;
import java.awt.*;

/**
 * @author : Nik Torfs
 *         Date: 01/03/13
 *         Time: 03:23
 */
public class DialogView {

    SimpleGUI dialog;

    public DialogView(final String text) {
        dialog = new SimpleGUI("Dialog", 500, 70) {

            @Override
            public void paint(Graphics2D graphics) {
                graphics.drawString(text, 20, 20);
            }


        };
        dialog.setCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialog.setCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.createButton(20, 30, 60, 30, new Runnable() {
            @Override
            public void run() {
                dialog.dispose();
            }
        }).setText("ok");

    }

}
