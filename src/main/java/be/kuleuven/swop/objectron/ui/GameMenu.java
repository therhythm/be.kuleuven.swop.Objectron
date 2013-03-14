package be.kuleuven.swop.objectron.ui;

import be.kuleuven.swop.objectron.handler.StartGameHandler;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.viewmodel.GameStartViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 02:13
 */
public class GameMenu {

    private SimpleGUI gui;

    public GameMenu(final StartGameHandler handler){
        gui = new SimpleGUI("Objectron", 300, 300) {
            @Override
            public void paint(Graphics2D graphics) {
                graphics.drawString("Player 1 name:", 20, 20);
                graphics.drawString("Player 2 name:", 20, 80);
                graphics.drawString("Number of horizontal tiles (min 10)", 20, 140);
                graphics.drawString("Number of vertical tiles (min 10)", 20, 200);
            }
        };

        final JTextField p1NameField = gui.createTextField(20, 30, 260, 20);
        final JTextField p2NameField = gui.createTextField(20, 90, 260, 20);
        final JTextField nbHorizontalTilesField = gui.createTextField(20, 150, 260, 20);
        final JTextField nbVerticalTilesField = gui.createTextField(20, 210, 260, 20);
        gui.repaint();

        gui.createButton(20,260,260,20,new Runnable() {
            @Override
            public void run() {
                if(nbHorizontalTilesField.getText().isEmpty()
                        || nbVerticalTilesField .getText().isEmpty()
                        || p1NameField.getText().isEmpty()
                        || p2NameField.getText().isEmpty()){
                    new DialogView("Please fill in all textfields", false);
                }else{
                    try{
                        int nbHorizontalTiles = Integer.parseInt(nbHorizontalTilesField.getText());
                        int nbVerticalTiles = Integer.parseInt(nbVerticalTilesField.getText());
                        if(nbHorizontalTiles < 0 || nbVerticalTiles < 1){
                            new DialogView("Please enter (positive) numbers for the horizontal and vertical tiles!", false);
                        }
                        GameStartViewModel vm = handler.startNewGame(p1NameField.getText(), p2NameField.getText(), nbHorizontalTiles, nbVerticalTiles);
                        new GameView(vm).run();
                        gui.dispose();
                    }catch(NumberFormatException ex){
                        new DialogView("Please enter (positive) numbers for the horizontal and vertical tiles!", false);
                    } catch (GridTooSmallException e) {
                        new DialogView("The given grid dimensions are too small (min 10x10)", false);
                    }
                }
            }
        }).setText("Start game");
    }
}
