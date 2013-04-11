package be.kuleuven.swop.objectron.ui;

import be.kuleuven.swop.objectron.domain.gamestate.GameObserver;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.*;
import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.viewmodel.GameStartViewModel;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;
import be.kuleuven.swop.objectron.viewmodel.TurnViewModel;


import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Nik Torfs
 *         Date: 25/02/13
 *         Time: 21:24
 */
public class GameView implements GameObserver {

    private static final int HPADDING = 10;
    private static final int VPADDING = 50;
    private static final int TILEWIDTH = 40;
    private static final int TILEHEIGHT = 40;

    private TurnViewModel currentTurn;
    private String selectedItem = "no item";
    private SquareStates gameGrid[][];
    private Map<SquareStates, Image> gridImageMap = new HashMap<SquareStates, Image>();
    private Map<String, SquareStates[]> playerColorMap = new HashMap<String, SquareStates[]>();
    private HandlerCatalog catalog;
    private Dimension dimension;
    private SimpleGUI gui;
    private Position p1Finish;
    private Position p2Finish;


    public GameView(GameStartViewModel vm) {
        this.catalog = vm.getCatalog();
        vm.getObservable().attach(this);
        this.dimension = vm.getDimension();
        this.gameGrid = new SquareStates[dimension.getHeight()][dimension.getWidth()];
        //this.currentPlayer = vm.getP1();
        this.currentTurn = vm.getCurrentTurn();
        for (int i = 0; i < dimension.getHeight(); i++) {
            for (int j = 0; j < dimension.getWidth(); j++) {
                gameGrid[i][j] = SquareStates.EMPTY;
            }
        }
        for (List<Position> sqvm : vm.getWalls()) {
            for (Position sVm : sqvm) {
                gameGrid[sVm.getVIndex()][sVm.getHIndex()] = SquareStates.WALL;
            }
        }
        PlayerViewModel p1 = vm.getP1();
        PlayerViewModel p2 = vm.getP2();
        playerColorMap.put(p1.getName(), new SquareStates[]{SquareStates.PLAYER1, SquareStates.P1_LIGHT_WALL, SquareStates.P1_FINISH});
        playerColorMap.put(p2.getName(), new SquareStates[]{SquareStates.PLAYER2, SquareStates.P2_LIGHT_WALL, SquareStates.P2_FINISH});
        Position p1Pos = p1.getPosition();
        gameGrid[p1Pos.getVIndex()][p1Pos.getHIndex()] = SquareStates.PLAYER1;
        Position p2Pos = p2.getPosition();
        gameGrid[p2Pos.getVIndex()][p2Pos.getHIndex()] = SquareStates.PLAYER2;
        p1Finish = p2.getStartPosition();
        p2Finish = p1.getStartPosition();
    }

    public void run() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            int buttonWidth = dimension.getWidth() * TILEWIDTH / 4;

            public void run() {
                gui = new SimpleGUI("OBJECTRON", 2 * HPADDING + TILEWIDTH * dimension.getWidth(), TILEHEIGHT * dimension.getHeight() + 3 * VPADDING) {

                    @Override
                    public void paint(Graphics2D graphics) {
                        for (int i = 0; i < dimension.getWidth(); i++) {
                            for (int j = 0; j < dimension.getHeight(); j++) {
                                if (gameGrid[j][i] == SquareStates.EMPTY) {
                                    if (p1Finish.getHIndex() == i && p1Finish.getVIndex() == j) {
                                        graphics.drawImage(gridImageMap.get(SquareStates.P1_FINISH), HPADDING + i * TILEWIDTH, VPADDING + j * TILEHEIGHT, TILEWIDTH, TILEHEIGHT, null);

                                    } else if (p2Finish.getHIndex() == i && p2Finish.getVIndex() == j) {
                                        graphics.drawImage(gridImageMap.get(SquareStates.P2_FINISH), HPADDING + i * TILEWIDTH, VPADDING + j * TILEHEIGHT, TILEWIDTH, TILEHEIGHT, null);
                                    } else {
                                        graphics.drawImage(gridImageMap.get(gameGrid[j][i]), HPADDING + i * TILEWIDTH, VPADDING + j * TILEHEIGHT, TILEWIDTH, TILEHEIGHT, null);
                                    }
                                } else {
                                    graphics.drawImage(gridImageMap.get(gameGrid[j][i]), HPADDING + i * TILEWIDTH, VPADDING + j * TILEHEIGHT, TILEWIDTH, TILEHEIGHT, null);
                                }
                            }
                        }
                        graphics.drawString(currentTurn.getPlayerViewModel().getName(), 20, 20);
                        graphics.drawString("moves remaining: " + currentTurn.getRemainingActions(), 20, 40);
                        graphics.drawString("selected item: " + selectedItem, 200, 20);
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

                    gui.createButton(HPADDING + hMultiplier * 20, dimension.getHeight() * TILEHEIGHT + VPADDING + 20 + vMultiplier * 20, 20, 20, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                MovePlayerHandler movePlayerHandler = (MovePlayerHandler) catalog.getHandler(MovePlayerHandler.class);
                                movePlayerHandler.move(direction);
                            } catch (InvalidMoveException e) {
                                new DialogView("Sorry that is not a valid move");
                            } catch (NotEnoughActionsException e) {
                                new DialogView("You have no actions remaining, end the turn.");
                            }  catch(GameOverException e){

                                new DialogView(e.getMessage());

                               gui.dispose();
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

                final Button pickupButton = gui.createButton(HPADDING + buttonWidth, dimension.getHeight() * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {


                        try {
                            final PickUpItemHandler pickUpItemHandler = (PickUpItemHandler) catalog.getHandler(PickUpItemHandler.class);
                            final List<Item> items = pickUpItemHandler.getAvailableItems();
                            ItemSelectionAction action = new ItemSelectionAction() {
                                @Override
                                public void doAction(int index) {
                                    try {
                                        pickUpItemHandler.pickUpItem(index);
                                        gui.repaint();

                                    } catch (InventoryFullException e) {
                                        new DialogView("Your inventory is full!");
                                    } catch (NotEnoughActionsException e) {
                                        new DialogView("You have no actions remaining, end the turn.");
                                    }
                                }
                            };
                            new ItemListView(items, action);
                        } catch (SquareEmptyException e) {
                            new DialogView("The current square is empty");
                        } catch (NotEnoughActionsException e) {
                            new DialogView("You have no actions remaining, end the tun.");
                        }
                        gui.repaint();
                    }
                });
                pickupButton.setText("Pickup item");

                final Button inventoryButton = gui.createButton(HPADDING + 2 * buttonWidth, dimension.getHeight() * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        try {
                            final UseItemHandler useItemHandler = (UseItemHandler) catalog.getHandler(UseItemHandler.class);
                            final List<Item> items = useItemHandler.showInventory();
                            ItemSelectionAction action = new ItemSelectionAction() {

                                @Override
                                public void doAction(int index) {
                                    selectedItem = useItemHandler.selectItemFromInventory(index);
                                    gui.repaint();
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

                final Button useItemButton = gui.createButton(HPADDING + 2 * buttonWidth,dimension.getHeight() * TILEHEIGHT + VPADDING + 40, buttonWidth, 20, new Runnable() {
                    public void run() {
                        try {
                            final UseItemHandler useItemHandler = (UseItemHandler) catalog.getHandler(UseItemHandler.class);
                            useItemHandler.useCurrentItem();
                            selectedItem = "No item";
                            gui.repaint();
                        } catch (SquareOccupiedException e) {
                            new DialogView("The square is already occupied.");
                        } catch (NotEnoughActionsException e) {
                            new DialogView("You have no actions remaining, end the turn.");
                        } catch (NoItemSelectedException e){
                            new DialogView("You don't have an item selected");
                        }

                        gui.repaint();
                    }
                });
                useItemButton.setText("Use Item");

                final Button cancelItemButton = gui.createButton(HPADDING + 2 * buttonWidth,dimension.getHeight() * TILEHEIGHT + VPADDING + 60, buttonWidth, 20, new Runnable() {
                    public void run() {
                        final UseItemHandler useItemHandler = (UseItemHandler) catalog.getHandler(UseItemHandler.class);
                        useItemHandler.cancelItemUsage();
                        selectedItem = "No item";
                        gui.repaint();
                        gui.repaint();
                    }
                });
                cancelItemButton.setText("Unselect item");

                final Button endTurnButton = gui.createButton(HPADDING + 3 * buttonWidth, dimension.getHeight() * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        try {
                            final EndTurnHandler endTurnHandler = (EndTurnHandler) catalog.getHandler(EndTurnHandler.class);
                            endTurnHandler.endTurn();

                        } catch (GameOverException e) {
                            new DialogView(e.getMessage());
                            gui.dispose();
                        }
                        gui.repaint();
                    }
                });
                endTurnButton.setText("End turn");

            }
        });
    }


    //TODO cleanup or not GUI stuff...
    private void updatePlayer(PlayerViewModel playerViewModel) {
        PlayerViewModel currentPlayer = currentTurn.getPlayerViewModel();
        if (currentPlayer.getName().equals(playerViewModel.getName())) {
            Position currentPos = currentPlayer.getPosition();
            gameGrid[currentPos.getVIndex()][currentPos.getHIndex()] = SquareStates.EMPTY;
            for (Position svm : currentPlayer.getLightTrail()) {
                gameGrid[svm.getVIndex()][svm.getHIndex()] = SquareStates.EMPTY;
            }
        }
        currentPlayer = playerViewModel;
        Position currentPos = currentPlayer.getPosition();
        gameGrid[currentPos.getVIndex()][currentPos.getHIndex()] = playerColorMap.get(currentPlayer.getName())[0];
        for (Position svm : currentPlayer.getLightTrail()) {
            gameGrid[svm.getVIndex()][svm.getHIndex()] = playerColorMap.get(currentPlayer.getName())[1];
        }
        gui.repaint();
    }

    @Override
    public void playerUpdated(PlayerViewModel vm) {
        updatePlayer(vm);
    }

    public enum SquareStates {
        WALL, P1_LIGHT_WALL, P2_LIGHT_WALL, PLAYER1, PLAYER2, EMPTY, P1_FINISH, P2_FINISH
    }
}
