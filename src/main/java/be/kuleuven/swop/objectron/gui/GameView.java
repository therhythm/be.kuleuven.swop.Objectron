package be.kuleuven.swop.objectron.gui;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.awt.*;

/**
 * @author : Nik Torfs
 *         Date: 25/02/13
 *         Time: 21:24
 */
public class GameView {

    @Parameter(names="-htiles", description = "number of horizontal tiles")
    private int horizontalTiles = 10;

    @Parameter(names="-vtiles", description = "number of vertical tiles")
    private int verticalTiles = 10;

    private static int HPADDING = 10;
    private static int VPADDING = 50;
    private static int TILEWIDTH = 40;
    private static int TILEHEIGHT = 40;

    public void parseArgs(String[] args){
        new JCommander(this, args);
    }

    public void runGame(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            Image playerRed, playerBlue, cell, cellFinishBlue, cellFinishRed;
            int buttonWidth = horizontalTiles*TILEWIDTH / 4;

            public void run() {
                final SimpleGUI gui = new SimpleGUI("OBJECTRON", 2*HPADDING + TILEWIDTH * horizontalTiles, TILEHEIGHT * verticalTiles + 2*VPADDING) {

                    @Override
                    public void paint(Graphics2D graphics) {
                        for(int i = 0; i < horizontalTiles; i++){
                            for(int j = 0; j < verticalTiles; j++){
                                graphics.drawImage(cell, HPADDING + i * TILEWIDTH, VPADDING + j * TILEHEIGHT, TILEWIDTH, TILEHEIGHT, null);
                            }
                        }
                    }

                    @Override
                    public void handleMouseClick(int x, int y, boolean doubleClick) {
                        System.out.println((doubleClick ? "Doubleclicked" : "Clicked") + " at (" + x + ", " + y + ")");
                    }

                };

                playerRed = gui.loadImage("src/main/resources/player_red.png", TILEWIDTH, TILEHEIGHT);
                playerBlue = gui.loadImage("src/main/resources/player_blue.png", TILEWIDTH, TILEHEIGHT);
                cell = gui.loadImage("src/main/resources/cell.png", TILEWIDTH, TILEHEIGHT);
                cellFinishBlue = gui.loadImage("src/main/resource/cell_finish_blue.png", TILEWIDTH, TILEHEIGHT);
                cellFinishRed = gui.loadImage("src/main/resource/cell_finish_red.png", TILEWIDTH, TILEHEIGHT);

                final Button moveButton = gui.createButton(HPADDING, verticalTiles * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        gui.repaint();
                    }
                });
                moveButton.setText("Make a move");

                final Button pickupButton = gui.createButton(HPADDING + buttonWidth, verticalTiles * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        gui.repaint();
                    }
                });
                pickupButton.setText("Pickup item");

                final Button inventoryButton = gui.createButton(HPADDING + 2*buttonWidth, verticalTiles * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        gui.repaint();
                    }
                });
                inventoryButton.setText("Open inventory");

                final Button endTurnButton = gui.createButton(HPADDING + 3*buttonWidth, verticalTiles * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        gui.repaint();
                    }
                });
                endTurnButton.setText("End turn");
            }
        });
    }

    public static void main(String[] args) {
        GameView g = new GameView();
        g.parseArgs(args);
        g.runGame();
    }
}
