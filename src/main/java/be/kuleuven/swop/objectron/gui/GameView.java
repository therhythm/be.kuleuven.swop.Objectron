package be.kuleuven.swop.objectron.gui;

import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.model.Square;
import be.kuleuven.swop.objectron.model.listener.*;

import java.awt.*;

/**
 * @author : Nik Torfs
 *         Date: 25/02/13
 *         Time: 21:24
 */
public class GameView implements GameEventListener, GridEventListener, PlayerEventListener {
    private static int HPADDING = 10;
    private static int VPADDING = 50;
    private static int TILEWIDTH = 40;
    private static int TILEHEIGHT = 40;

    private GameController controller;
    private int horizontalTiles;
    private int verticalTiles;
    private SimpleGUI gui;

    public GameView(GameController controller, int horizontalTiles, int verticalTiles) {
        this.controller = controller;
        this.horizontalTiles = horizontalTiles;
        this.verticalTiles = verticalTiles;
    }

    public void run() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            Image playerRed
                    ,
                    playerBlue
                    ,
                    cell
                    ,
                    cellFinishBlue
                    ,
                    cellFinishRed;
            int buttonWidth = horizontalTiles * TILEWIDTH / 4;

            public void run() {
                gui = new SimpleGUI("OBJECTRON", 2 * HPADDING + TILEWIDTH * horizontalTiles, TILEHEIGHT * verticalTiles + 2 * VPADDING) {

                    @Override
                    public void paint(Graphics2D graphics) {
                        for (int i = 0; i < horizontalTiles; i++) {
                            for (int j = 0; j < verticalTiles; j++) {
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
                        //TODO controller.selectDirection();
                        gui.repaint();
                    }
                });
                moveButton.setText("Make a move");

                final Button pickupButton = gui.createButton(HPADDING + buttonWidth, verticalTiles * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        //TODO controller.pickUpItem();
                        gui.repaint();
                    }
                });
                pickupButton.setText("Pickup item");

                final Button inventoryButton = gui.createButton(HPADDING + 2 * buttonWidth, verticalTiles * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        //TODO controller.showInventory();
                        gui.repaint();
                    }
                });
                inventoryButton.setText("Open inventory");

                final Button endTurnButton = gui.createButton(HPADDING + 3 * buttonWidth, verticalTiles * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        //TODO controller.endTurn();
                        gui.repaint();
                    }
                });
                endTurnButton.setText("End turn");
            }
        });
    }

    @Override
    public void gameEnding(String winner, String loser) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void gridUpdated(Square[][] squares) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void squareUpdated(int hIndex, int vIndex, Square square) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void nbActionsUpdated(int nbActions) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void playerChanged(String newPlayer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
