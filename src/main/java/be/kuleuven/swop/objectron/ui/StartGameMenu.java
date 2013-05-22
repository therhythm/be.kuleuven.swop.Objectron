package be.kuleuven.swop.objectron.ui;

import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidFileException;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.handler.StartGameHandler;
import be.kuleuven.swop.objectron.viewmodel.GameStartViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 02:13
 */
public class StartGameMenu {

    private java.util.List<String> playerNames = new ArrayList<>();
    private SimpleGUI gui;

    public StartGameMenu(final StartGameHandler handler, final GameType gameType) {
        gui = new SimpleGUI("Objectron", 300, 500) {
            @Override
            public void paint(Graphics2D graphics) {
                graphics.drawString("file: ", 20, 30);
                graphics.drawString("width: ", 20, 80);
                graphics.drawString("height: ", 20, 130);
                graphics.drawString("players:", 20, 180);
                for (int i = 0; i < playerNames.size(); i++) {
                    String playerName = playerNames.get(i);
                    graphics.drawString("player " + i+1 + ": " + playerName,20,270+ 20*i);
                }

            }
        };

        final JTextField fileField = gui.createTextField(20, 40, 260, 20);
        final JTextField widthField = gui.createTextField(20, 90, 260,20);
        final JTextField heightField = gui.createTextField(20, 140, 260,20);
        final JTextField playerField = gui.createTextField(20, 190, 260, 20);

        gui.createButton(20,220,260,20,new Runnable() {
            @Override
            public void run() {
                if(!playerField.getText().isEmpty()){
                    playerNames.add(playerField.getText());
                    playerField.setText("");
                    gui.repaint();
                }
            }
        }).setText("add player");

        gui.repaint();

        gui.createButton(20, 470, 260, 20, new Runnable() {
            @Override
            public void run() {
                if (
                        (
                        (widthField.getText().isEmpty() || heightField.getText().isEmpty())
                                && fileField.getText().isEmpty()
                        )
                        || playerNames.isEmpty()) {

                    new DialogView("Please fill in all textfields");
                } else {
                    try {
                        int nbHorizontalTiles = 10;
                        int nbVerticalTiles = 10;

                        if(!widthField.getText().isEmpty() && !heightField.getText().isEmpty()){
                            nbHorizontalTiles = Integer.parseInt(widthField.getText());
                            nbVerticalTiles = Integer.parseInt(heightField.getText());

                            if (nbHorizontalTiles < 0 || nbVerticalTiles < 1) {
                                new DialogView("Please enter (positive) numbers for the horizontal and vertical tiles!");
                            }
                        }

                        GameStartViewModel vm;
                        switch (gameType){
                            case RACEGAME:
                                vm = handler.startNewRaceGame(playerNames,
                                        new Dimension(nbHorizontalTiles, nbVerticalTiles), fileField.getText());
                                break;
                            case CTFGAME:
                                vm = handler.startNewRaceGame(playerNames,
                                        new Dimension(nbHorizontalTiles, nbVerticalTiles), fileField.getText());
                                break;
                            default:
                                vm = handler.startNewRaceGame(playerNames,
                                        new Dimension(nbHorizontalTiles, nbVerticalTiles), fileField.getText());
                                break;
                        }

                        new GameView(vm).run();
                        gui.dispose();
                    } catch (NumberFormatException ex) {
                        new DialogView("Please enter (positive) numbers for the horizontal and vertical tiles!");
                    } catch (GridTooSmallException e) {
                        new DialogView("The given grid dimensions are too small (min 10x10)");
                    } catch (InvalidFileException e) {
                        new DialogView(e.getMessage());
                    }
                }
            }
        }).setText("Start game");
    }
}
