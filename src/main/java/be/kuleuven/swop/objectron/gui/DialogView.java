package be.kuleuven.swop.objectron.gui;

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
        dialog = new SimpleGUI("Dialog", 300, 100) {

            @Override
            public void paint(Graphics2D graphics) {
                graphics.drawString(text, 50, 50);

            }


        };
        dialog.setCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.createButton(120, 70, 60, 30, new Runnable() {
            @Override
            public void run() {
                dialog.dispose();
            }
        }).setText("ok");

    }

}
