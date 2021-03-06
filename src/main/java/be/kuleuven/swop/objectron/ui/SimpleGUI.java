package be.kuleuven.swop.objectron.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class SimpleGUI {

    private JFrame frame;
    private JPanel panel;

    public SimpleGUI(String title, final int width, final int height) {
        frame = new JFrame(title);
        panel = new JPanel(null) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                SimpleGUI.this.paint((Graphics2D) g);
            }

            public Dimension getPreferredSize() {
                return new Dimension(width, height);
            }
        };
        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY(), e.getClickCount() == 2);
            }

        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public abstract void paint(Graphics2D graphics);

    public void handleMouseClick(int x, int y, boolean doubleClick) {
    }

    public final void repaint() {
        panel.repaint();
    }

    public final Button createButton(int x, int y, int width, int height, Runnable clickHandler) {
        JButton button = new JButton();
        button.setLocation(x, y);
        button.setSize(width, height);
        panel.add(button);
        Button b = new Button(button);
        b.setClickHandler(clickHandler);
        return b;
    }

    public final JTextField createTextField(int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setLocation(x, y);
        textField.setSize(width, height);
        panel.add(textField);
        textField.setVisible(true);
        return textField;
    }

    public final Image loadImage(String url, int width, int height) {
        Image image = panel.getToolkit().createImage(ClassLoader.getSystemClassLoader().getResource(url));
        MediaTracker tracker = new MediaTracker(panel);
        tracker.addImage(image, 1, width, height);
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    public void setCloseOperation(int operation) {
        frame.setDefaultCloseOperation(operation);
    }

    public void dispose() {
        frame.dispose();

    }

    public void setVisible(boolean value) {
        frame.setVisible(value);
    }
}
