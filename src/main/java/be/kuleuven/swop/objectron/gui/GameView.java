package be.kuleuven.swop.objectron.gui;

import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.listener.GameEventListener;
import be.kuleuven.swop.objectron.model.Direction;
import be.kuleuven.swop.objectron.model.exception.*;
import be.kuleuven.swop.objectron.model.item.Item;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;
import be.kuleuven.swop.objectron.viewmodel.SquareViewModel;
import be.kuleuven.swop.objectron.viewmodel.WallViewModel;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Nik Torfs
 *         Date: 25/02/13
 *         Time: 21:24
 */
public class GameView implements GameEventListener {

    public enum SquareStates {
        WALL, P1_LIGHT_WALL, P2_LIGHT_WALL, PLAYER1, PLAYER2, EMPTY, P1_FINISH, P2_FINISH
    }

    private static int HPADDING = 10;
    private static int VPADDING = 50;
    private static int TILEWIDTH = 40;
    private static int TILEHEIGHT = 40;

    private SquareStates gameGrid[][];
    private Map<SquareStates, Image> gridImageMap = new HashMap<SquareStates, Image>();
    private Map<String, SquareStates[]> playerColorMap = new HashMap<String, SquareStates[]>();

    private GameController controller;
    private int horizontalTiles;
    private SimpleGUI gui;
    private int verticalTiles;
    private SquareViewModel p1_finish;
    private SquareViewModel p2_finish;

    PlayerViewModel currentPlayer;

    public GameView(GameController controller, int horizontalTiles, int verticalTiles, PlayerViewModel p1, PlayerViewModel p2, List<WallViewModel> walls) {
        this.controller = controller;
        this.horizontalTiles = horizontalTiles;
        this.verticalTiles = verticalTiles;
        this.gameGrid = new SquareStates[verticalTiles][horizontalTiles];
        this.currentPlayer = p1;
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[0].length; j++) {
                gameGrid[i][j] = SquareStates.EMPTY;
            }
        }
        for (WallViewModel vm : walls) {
            for (SquareViewModel sVm : vm.getSquares()) {
                gameGrid[sVm.getVIndex()][sVm.getHIndex()] = SquareStates.WALL;
            }
        }
        playerColorMap.put(p1.getName(), new SquareStates[]{SquareStates.PLAYER1, SquareStates.P1_LIGHT_WALL, SquareStates.P1_FINISH});
        playerColorMap.put(p2.getName(), new SquareStates[]{SquareStates.PLAYER2, SquareStates.P2_LIGHT_WALL, SquareStates.P2_FINISH});
        gameGrid[p1.getVPosition()][p1.getHPosition()] = SquareStates.PLAYER1;
        gameGrid[p2.getVPosition()][p2.getHPosition()] = SquareStates.PLAYER2;
        p1_finish = new SquareViewModel(p1.getHPosition(), p1.getVPosition());
        p2_finish = new SquareViewModel(p2.getHPosition(), p2.getVPosition());
    }


    public void run() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            int buttonWidth = horizontalTiles * TILEWIDTH / 4;

            public void run() {
                gui = new SimpleGUI("OBJECTRON", 2 * HPADDING + TILEWIDTH * horizontalTiles, TILEHEIGHT * verticalTiles + 3 * VPADDING) {

                    @Override
                    public void paint(Graphics2D graphics) {
                        for (int i = 0; i < horizontalTiles; i++) {
                            for (int j = 0; j < verticalTiles; j++) {
                                if (gameGrid[j][i] == SquareStates.EMPTY){
                                    if(p1_finish.getHIndex() == i && p1_finish.getVIndex() == j){
                                        graphics.drawImage(gridImageMap.get(SquareStates.P1_FINISH), HPADDING + i * TILEWIDTH, VPADDING + j * TILEHEIGHT, TILEWIDTH, TILEHEIGHT, null);

                                    }else if(p2_finish.getHIndex() == i && p2_finish.getVIndex() == j) {
                                        graphics.drawImage(gridImageMap.get(SquareStates.P2_FINISH), HPADDING + i * TILEWIDTH, VPADDING + j * TILEHEIGHT, TILEWIDTH, TILEHEIGHT, null);
                                    }else{
                                        graphics.drawImage(gridImageMap.get(gameGrid[j][i]), HPADDING + i * TILEWIDTH, VPADDING + j * TILEHEIGHT, TILEWIDTH, TILEHEIGHT, null);
                                    }
                                }else{
                                    graphics.drawImage(gridImageMap.get(gameGrid[j][i]), HPADDING + i * TILEWIDTH, VPADDING + j * TILEHEIGHT, TILEWIDTH, TILEHEIGHT, null);
                                }
                            }
                        }
                        graphics.drawString(currentPlayer.getName(), 20, 20);
                        graphics.drawString("moves remaining: " + currentPlayer.getAvailableActions(), 20, 40);
                        graphics.drawString("selected item: " + currentPlayer.getSelectedItem().getName(), 200, 20);
                    }

                    @Override
                    public void handleMouseClick(int x, int y, boolean doubleClick) {
                        System.out.println((doubleClick ? "Doubleclicked" : "Clicked") + " at (" + x + ", " + y + ")");
                    }

                };


                gridImageMap.put(SquareStates.PLAYER1, gui.loadImage("player_red.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.PLAYER2, gui.loadImage("player_blue.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.EMPTY, gui.loadImage("cell.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.P1_FINISH, gui.loadImage("cell_finish_red.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.P2_FINISH, gui.loadImage("cell_finish_blue.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.P1_LIGHT_WALL, gui.loadImage("cell_lighttrail_red.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.P2_LIGHT_WALL, gui.loadImage("cell_lighttrail_blue.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.WALL, gui.loadImage("wall.png", TILEWIDTH, TILEHEIGHT));


                Map<Direction, Image> directionImageMap = new HashMap<Direction, Image>();
                directionImageMap.put(Direction.UP_LEFT, gui.loadImage("arrow_NW.png", 20, 20));
                directionImageMap.put(Direction.UP, gui.loadImage("arrow_N.png", 20, 20));
                directionImageMap.put(Direction.UP_RIGHT, gui.loadImage("arrow_NE.png", 20, 20));
                directionImageMap.put(Direction.LEFT, gui.loadImage("arrow_W.png", 20, 20));
                directionImageMap.put(Direction.RIGHT, gui.loadImage("arrow_E.png", 20, 20));
                directionImageMap.put(Direction.DOWN_LEFT, gui.loadImage("arrow_SW.png", 20, 20));
                directionImageMap.put(Direction.DOWN, gui.loadImage("arrow_S.png", 20, 20));
                directionImageMap.put(Direction.DOWN_RIGHT, gui.loadImage("arrow_SE.png", 20, 20));

                int hMultiplier = 0;
                int vMultiplier = 0;
                int vCount = 0;
                // generate direction buttons
                for (final Direction direction : Direction.values()) {

                    gui.createButton(HPADDING + hMultiplier * 20, verticalTiles * TILEHEIGHT + VPADDING + 20 + vMultiplier * 20, 20, 20, new Runnable() {
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
                    vCount++;
                    if (vCount == 3 || (vMultiplier == 1 && vCount == 2)) {
                        vMultiplier++;
                        vCount = 0;
                        hMultiplier = 0;
                    } else if (vMultiplier == 1 && hMultiplier == 0) {
                        hMultiplier += 2;
                    } else {
                        hMultiplier++;
                    }
                }

                final Button pickupButton = gui.createButton(HPADDING + buttonWidth, verticalTiles * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        final List<Item> items = controller.getAvailableItems();
                        ItemSelectionAction action = new ItemSelectionAction() {

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
                            ItemSelectionAction action = new ItemSelectionAction() {

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
    public void playerUpdated(PlayerViewModel playerViewModel) {
        if (currentPlayer.getName().equals(playerViewModel.getName())) {
            gameGrid[currentPlayer.getVPosition()][currentPlayer.getHPosition()] = SquareStates.EMPTY;
            for (SquareViewModel svm : currentPlayer.getLightTrail()) {
                gameGrid[svm.getVIndex()][svm.getHIndex()] = SquareStates.EMPTY;
            }
        }
        currentPlayer = playerViewModel;
        gameGrid[currentPlayer.getVPosition()][currentPlayer.getHPosition()] = playerColorMap.get(currentPlayer.getName())[0];
        for (SquareViewModel svm : currentPlayer.getLightTrail()) {
            gameGrid[svm.getVIndex()][svm.getHIndex()] = playerColorMap.get(currentPlayer.getName())[1];
        }
        gui.repaint();
    }
}
