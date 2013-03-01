package be.kuleuven.swop.objectron.gui;

import be.kuleuven.swop.objectron.controller.GameController;
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
public class GameView {
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
                gui = new SimpleGUI("OBJECTRON", 2 * HPADDING + TILEWIDTH * horizontalTiles, TILEHEIGHT * verticalTiles + 3 * VPADDING) {

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
                cellFinishBlue = gui.loadImage("src/main/resources/cell_finish_blue.png", TILEWIDTH, TILEHEIGHT);
                cellFinishRed = gui.loadImage("src/main/resources/cell_finish_red.png", TILEWIDTH, TILEHEIGHT);

                Image NWArrow = gui.loadImage("src/main/resources/arrow_NW.png",20,20);
                Image NArrow = gui.loadImage("src/main/resources/arrow_N.png",20,20);
                Image NEArrow = gui.loadImage("src/main/resources/arrow_NE.png",20,20);
                Image WArrow = gui.loadImage("src/main/resources/arrow_W.png",20,20);
                Image EArrow = gui.loadImage("src/main/resources/arrow_E.png",20,20);
                Image SWArrow = gui.loadImage("src/main/resources/arrow_SW.png",20,20);
                Image SArrow = gui.loadImage("src/main/resources/arrow_S.png",20,20);
                Image SEArrow = gui.loadImage("src/main/resources/arrow_SE.png",20,20);


            /*    final Button moveButton = gui.createButton(HPADDING, verticalTiles * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        //TODO controller.selectDirection();
                        gui.repaint();
                    }
                });
                moveButton.setText("Make a move");*/
                final Button NWButton = gui.createButton(HPADDING,verticalTiles*TILEHEIGHT + VPADDING + 20,20,20,new Runnable() {
                    @Override
                    public void run() {
                        //TODO controller.move
                        gui.repaint();
                    }
                });
                NWButton.setImage(NWArrow);

                final Button NButton = gui.createButton(HPADDING + 20,verticalTiles*TILEHEIGHT + VPADDING + 20,20,20,new Runnable() {
                    @Override
                    public void run() {
                        //TODO controller.move
                        gui.repaint();
                    }
                });
                NButton.setImage(NArrow);

                final Button NEButton = gui.createButton(HPADDING + 40,verticalTiles*TILEHEIGHT + VPADDING + 20,20,20,new Runnable() {
                    @Override
                    public void run() {
                        //TODO controller.move
                        gui.repaint();
                    }
                });
                NEButton.setImage(NEArrow);

                final Button WButton = gui.createButton(HPADDING ,verticalTiles*TILEHEIGHT + VPADDING + 40,20,20,new Runnable() {
                    @Override
                    public void run() {
                        //TODO controller.move
                        gui.repaint();
                    }
                });
                WButton.setImage(WArrow);

                final Button EButton = gui.createButton(HPADDING + 40 ,verticalTiles*TILEHEIGHT + VPADDING + 40,20,20,new Runnable() {
                    @Override
                    public void run() {
                        //TODO controller.move
                        gui.repaint();
                    }
                });
                EButton.setImage(EArrow);

                final Button SWButton = gui.createButton(HPADDING ,verticalTiles*TILEHEIGHT + VPADDING + 60,20,20,new Runnable() {
                    @Override
                    public void run() {
                        //TODO controller.move
                        gui.repaint();
                    }
                });
                SWButton.setImage(SWArrow);

                final Button SButton = gui.createButton(HPADDING +20 ,verticalTiles*TILEHEIGHT + VPADDING + 60,20,20,new Runnable() {
                    @Override
                    public void run() {
                        //TODO controller.move
                        gui.repaint();
                    }
                });
                SButton.setImage(SArrow);

                final Button SEButton = gui.createButton(HPADDING +40 ,verticalTiles*TILEHEIGHT + VPADDING + 60,20,20,new Runnable() {
                    @Override
                    public void run() {
                        //TODO controller.move
                        gui.repaint();
                    }
                });
                SEButton.setImage(SEArrow);






                final Button pickupButton = gui.createButton(HPADDING + buttonWidth, verticalTiles * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        final List<Item> items = controller.getAvailableItems();

                        gui.repaint();
                    }
                });
                pickupButton.setText("Pickup item");

                final Button inventoryButton = gui.createButton(HPADDING + 2 * buttonWidth, verticalTiles * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        try {
                            final List<Item> items = controller.showInventory();
                            new Inventory(items, controller);
                        } catch (InventoryEmptyException e) {
                            new Dialog("Your inventory is empty");
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
                            new Dialog("You lost the game!");
                        }
                        gui.repaint();
                    }
                });
                endTurnButton.setText("End turn");
            }
        });
    }

    private class Dialog{
        SimpleGUI dialog;

        Dialog(final String text){
            dialog = new SimpleGUI("Dialog", 300, 100){

                @Override
                public void paint(Graphics2D graphics) {
                    graphics.drawString(text, 50, 50);

                }


            };
            dialog.setCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dialog.createButton(120,70,60,30, new Runnable() {
                @Override
                public void run() {
                    dialog.dispose();
                }
            }).setText("ok");

        }
    }

    private class Inventory{
        Map<Class<?>, Image> itemMap = new HashMap<Class<?>, Image>();

        SimpleGUI inv;
        Inventory(final List<Item> items,final GameController controller){
            inv = new SimpleGUI("Inventory",150, 100) {
                @Override
                public void paint(Graphics2D graphics) {

                }
            };

            itemMap.put(LightMine.class, inv.loadImage("src/main/resources/player_red.png", 40, 40));
            int hcount = 0, vcount = 0;
            for(int i = 0; i< items.size(); i++){
                final int index = i;
                inv.createButton(10 + hcount * 40, 10 + vcount * 40, 40, 40, new Runnable() {
                    @Override
                    public void run() {
                        controller.selectItemFromInventory(index);
                        inv.dispose();
                    }
                }).setImage(itemMap.get(items.get(index).getClass()));

            }
        }
    }

    private class ItemList{
        Map<Class<?>, Image> itemMap = new HashMap<Class<?>, Image>();

        SimpleGUI inv;
        ItemList(final List<Item> items,final GameController controller){
            inv = new SimpleGUI("Inventory",150, 100) {
                @Override
                public void paint(Graphics2D graphics) {

                }
            };

            itemMap.put(LightMine.class, inv.loadImage("src/main/resources/player_red.png", 40, 40));
            int hcount = 0, vcount = 0;
            for(int i = 0; i< items.size(); i++){
                final int index = i;
                inv.createButton(10 + hcount * 40, 10 + vcount * 40, 40, 40, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            controller.pickUpItem(index);
                        } catch (InventoryFullException e) {
                            new Dialog("Your inventory is full!");
                        }
                        inv.dispose();
                    }
                }).setImage(itemMap.get(items.get(index).getClass()));

            }
        }
    }
}
