package be.kuleuven.swop.objectron.gui;

import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.listener.GameEventListener;
import be.kuleuven.swop.objectron.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 25/02/13
 *         Time: 21:24
 */
public class GameView implements GameEventListener{

    public enum SquareStates{
        WALL, LIGHT_WALL, PLAYER
    }
    private static int HPADDING = 10;
    private static int VPADDING = 50;
    private static int TILEWIDTH = 40;
    private static int TILEHEIGHT = 40;

    private GameController controller;
    private int horizontalTiles;
    private int verticalTiles;
    private String currentPlayer = "blaat";
    private String selectedItem = "no item";
    private int availableMoves = 3;
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
                gui = new SimpleGUI("OBJECTRON", 2 * HPADDING + TILEWIDTH * horizontalTiles, TILEHEIGHT * verticalTiles + 3 * VPADDING) {

                    @Override
                    public void paint(Graphics2D graphics) {
                        for (int i = 0; i < horizontalTiles; i++) {
                            for (int j = 0; j < verticalTiles; j++) {
                                graphics.drawImage(cell, HPADDING + i * TILEWIDTH, VPADDING + j * TILEHEIGHT, TILEWIDTH, TILEHEIGHT, null);
                            }
                        }
                        graphics.drawString(currentPlayer, 20, 20 );
                        graphics.drawString("moves remaining: " + availableMoves, 20, 40);
                        graphics.drawString("selected item: " + selectedItem, 200, 20);
                    }

                    @Override
                    public void handleMouseClick(int x, int y, boolean doubleClick) {
                        System.out.println((doubleClick ? "Doubleclicked" : "Clicked") + " at (" + x + ", " + y + ")");
                    }

                };

                playerRed = gui.loadImage("src/main/resources/player_red.png", TILEWIDTH, TILEHEIGHT);
                playerBlue = gui.loadImage("src/main/resources/player_blue.png", TILEWIDTH, TILEHEIGHT);
                cell = gui.loadImage("src/main/resources/cell.png", TILEWIDTH, TILEHEIGHT);
                cellFinishBlue = gui.loadImage("src/main/resources/cell_finish_blue.png", TILEWIDTH, TILEHEIGHT);
                cellFinishRed = gui.loadImage("src/main/resources/cell_finish_red.png", TILEWIDTH, TILEHEIGHT);

                Map<Direction, Image> directionImageMap = new HashMap<Direction, Image>();
                directionImageMap.put(Direction.UP_LEFT, gui.loadImage("src/main/resources/arrow_NW.png",20,20));
                directionImageMap.put(Direction.UP, gui.loadImage("src/main/resources/arrow_N.png",20,20));
                directionImageMap.put(Direction.UP_RIGHT, gui.loadImage("src/main/resources/arrow_NE.png",20,20));
                directionImageMap.put(Direction.LEFT, gui.loadImage("src/main/resources/arrow_W.png",20,20));
                directionImageMap.put(Direction.RIGHT, gui.loadImage("src/main/resources/arrow_E.png",20,20));
                directionImageMap.put(Direction.DOWN_LEFT, gui.loadImage("src/main/resources/arrow_SW.png",20,20));
                directionImageMap.put(Direction.DOWN, gui.loadImage("src/main/resources/arrow_S.png",20,20));
                directionImageMap.put(Direction.DOWN_RIGHT, gui.loadImage("src/main/resources/arrow_SE.png",20,20));

                int hMultiplier = 0;
                int vMultiplier = 0;
                int vCount = 0;
                // generate direction buttons
                for(final Direction direction : Direction.values()){

                    gui.createButton(HPADDING + hMultiplier * 20,verticalTiles*TILEHEIGHT + VPADDING + 20 + vMultiplier * 20,20,20,new Runnable() {
                        @Override
                        public void run() {
                            try {
                                controller.move(direction);
                            } catch (InvalidMoveException e) {
                                new DialogView("Sorry that is not a valid move");
                            } catch (NotEnoughActionsException e) {
                                new DialogView("You have no actions remaining, end the turn.");
                            }
                            gui.repaint();
                        }
                    }).setImage(directionImageMap.get(direction));
                    vCount ++;
                    if(vCount == 3 || (vMultiplier==1 && vCount==2)){
                        vMultiplier++;
                        vCount = 0;
                        hMultiplier = 0;
                    }else if(vMultiplier==1 && hMultiplier==0){
                        hMultiplier+=2;
                    }else{
                        hMultiplier++;
                    }
                }

                final Button pickupButton = gui.createButton(HPADDING + buttonWidth, verticalTiles * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        final List<Item> items = controller.getAvailableItems();
                        ItemSelectionAction action = new ItemSelectionAction(){

                            @Override
                            public void doAction(int index) {
                                try {
                                    controller.pickUpItem(index);
                                } catch (InventoryFullException e) {
                                    new DialogView("Your inventory is full!");
                                } catch (NotEnoughActionsException e) {
                                    new DialogView("You have no actions remaining, end the turn.");
                                }
                            }
                        };
                        new ItemListView(items, action);
                        gui.repaint();
                    }
                });
                pickupButton.setText("Pickup item");

                final Button inventoryButton = gui.createButton(HPADDING + 2 * buttonWidth, verticalTiles * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        try {
                            final List<Item> items = controller.showInventory();
                            ItemSelectionAction action = new ItemSelectionAction(){

                                @Override
                                public void doAction(int index) {
                                    controller.selectItemFromInventory(index);
                                }
                            };
                            new ItemListView(items, action);
                        } catch (InventoryEmptyException e) {
                            new DialogView("Your inventory is empty");
                        }
                        gui.repaint();
                    }
                });
                inventoryButton.setText("Open inventory");

                final Button endTurnButton = gui.createButton(HPADDING + 3 * buttonWidth, verticalTiles * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        try {
                            controller.endTurn();
                        } catch (GameOverException e) {
                            new DialogView("You lost the game!");
                            gui.dispose();
                        }
                        gui.repaint();
                    }
                });
                endTurnButton.setText("End turn");

            }
        });
    }

    @Override
    public void playerUpdated(int hPosition, int vPosition, int availableActions, String selectedItem) {
        availableMoves = availableActions;
        this.selectedItem = selectedItem;
        gui.repaint();
    }

    @Override
    public void playerChanged(String name, int availableActions) {
        currentPlayer = name;
        availableMoves = availableActions;
        gui.repaint();
    }


}
