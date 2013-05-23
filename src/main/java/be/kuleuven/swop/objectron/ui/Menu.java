package be.kuleuven.swop.objectron.ui;

import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidFileException;
import be.kuleuven.swop.objectron.domain.util.*;
import be.kuleuven.swop.objectron.handler.StartGameHandler;
import be.kuleuven.swop.objectron.viewmodel.GameStartViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author : Nik Torfs
 *         Date: 22/05/13
 *         Time: 20:00
 */
public class Menu {

    private SimpleGUI gui;

    public Menu(final StartGameHandler handler) {
        gui = new SimpleGUI("Objectron", 300, 100) {
            @Override
            public void paint(Graphics2D graphics) {
            }
        };


        gui.repaint();

        gui.createButton(20, 20, 260, 20, new Runnable() {
            @Override
            public void run() {
                new StartGameMenu(handler, GameType.RACEGAME);
                gui.dispose();
            }
        }).setText("Race game");

        gui.createButton(20, 50, 260, 20, new Runnable() {
            @Override
            public void run() {
                new StartGameMenu(handler, GameType.CTFGAME);
                gui.dispose();
            }
        }).setText("Capture the flag game");
    }
}
