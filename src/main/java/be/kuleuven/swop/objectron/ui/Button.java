package be.kuleuven.swop.objectron.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class Button {

    final JButton button;
    Runnable clickHandler;

    Button(JButton button) {
        this.button = button;
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (clickHandler != null)
                    clickHandler.run();
            }

        });
    }

    public void setText(String text) {
        button.setText(text);
    }

    public void setImage(Image image) {
        button.setIcon(image == null ? null : new ImageIcon(image));
    }

    public void setClickHandler(Runnable clickHandler) {
        this.clickHandler = clickHandler;
    }

    public void setEnabled(boolean enabled) {
        button.setEnabled(enabled);
    }

}
