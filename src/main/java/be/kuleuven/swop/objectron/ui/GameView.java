package be.kuleuven.swop.objectron.ui;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.effect.Teleporter;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.GameObserver;
import be.kuleuven.swop.objectron.domain.item.IdentityDisc;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.LightMine;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.*;
import be.kuleuven.swop.objectron.viewmodel.*;

import java.awt.*;
import java.util.*;
import java.util.List;

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
    private Map<Integer, SquareStates> gameGrid[][];
    private Map<SquareStates, Image> gridImageMap = new HashMap<SquareStates, Image>();
    private Map<String, SquareStates[]> playerColorMap = new HashMap<String, SquareStates[]>();
    private HandlerCatalog catalog;
    private Dimension dimension;
    private SimpleGUI gui;
    private Map<Position, List<Item>> items;   //TODO itemviewmodels
    private Map<Position, List<Effect>> effects; //TODO viewmodel??
    private List<PlayerViewModel> players;
    private Map<String, Position[]> lastPositions = new HashMap<String, Position[]>();


    public GameView(GameStartViewModel vm) {
        this.catalog = vm.getCatalog();
        vm.getObservable().attach(this);
        this.dimension = vm.getDimension();
        this.gameGrid = new HashMap[dimension.getHeight()][dimension.getWidth()];
        this.currentTurn = vm.getCurrentTurn();
        this.items = vm.getItems();
        this.effects = vm.getEffects();
        for (int i = 0; i < dimension.getHeight(); i++) {
            for (int j = 0; j < dimension.getWidth(); j++) {
                gameGrid[i][j] = new HashMap<Integer, SquareStates>();
                gameGrid[i][j].put(SquareStates.EMPTY.zIndex, SquareStates.EMPTY);
            }
        }
        for (List<Position> sqvm : vm.getWalls()) {
            for (Position sVm : sqvm) {
                gameGrid[sVm.getVIndex()][sVm.getHIndex()].put(SquareStates.WALL.zIndex, SquareStates.WALL);
            }
        }
        for (Position pos : items.keySet()) {
            for (Item item : items.get(pos)) {
                SquareStates state = getItemSquareState(item);
                gameGrid[pos.getVIndex()][pos.getHIndex()].put(state.zIndex, state);
            }
        }
        for (Position pos : effects.keySet()) {
            for (Effect effect : effects.get(pos)) {
                SquareStates state = getEffectSquareState(effect);
                gameGrid[pos.getVIndex()][pos.getHIndex()].put(state.zIndex, state);
            }
        }
        PlayerViewModel p1 = vm.getP1();
        PlayerViewModel p2 = vm.getP2();
        players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        playerColorMap.put(p1.getName(), new SquareStates[]{SquareStates.PLAYER1, SquareStates.P1_LIGHT_WALL,
                SquareStates.P1_FINISH});
        playerColorMap.put(p2.getName(), new SquareStates[]{SquareStates.PLAYER2, SquareStates.P2_LIGHT_WALL,
                SquareStates.P2_FINISH});
        Position p1Pos = p1.getPosition();
        gameGrid[p1Pos.getVIndex()][p1Pos.getHIndex()].put(SquareStates.PLAYER1.zIndex, SquareStates.PLAYER1);
        Position p2Pos = p2.getPosition();
        lastPositions.put(p1.getName(), new Position[0]);
        lastPositions.put(p2.getName(), new Position[0]);
        gameGrid[p2Pos.getVIndex()][p2Pos.getHIndex()].put(SquareStates.PLAYER2.zIndex, SquareStates.PLAYER2);
        gameGrid[p1.getStartPosition().getVIndex()][p1.getStartPosition().getHIndex()].put(SquareStates.P2_FINISH
                .zIndex, SquareStates.P2_FINISH);
        gameGrid[p2.getStartPosition().getVIndex()][p2.getStartPosition().getHIndex()].put(SquareStates.P1_FINISH
                .zIndex, SquareStates.P1_FINISH);

    }

    private SquareStates getItemSquareState(Item item) {
        if (item instanceof LightMine) {
            return SquareStates.LIGHT_MINE;
        } else if (item instanceof IdentityDisc) {
            return SquareStates.IDENTITY_DISK;
        } else if (item instanceof Teleporter) {
            return SquareStates.TELEPORTER;
        } else {
            return SquareStates.EMPTY;
        }
    }

    private SquareStates getEffectSquareState(Effect effect) {
        // no need for lightmine, the effects are invisible
        if (effect instanceof Teleporter) {
            return SquareStates.TELEPORTER;
        } else {
            return SquareStates.EMPTY;
        }
    }

    public void run() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            int buttonWidth = dimension.getWidth() * TILEWIDTH / 4;

            public void run() {
                gui = new SimpleGUI("OBJECTRON", 2 * HPADDING + TILEWIDTH * dimension.getWidth(),
                        TILEHEIGHT * dimension.getHeight() + 3 * VPADDING) {

                    @Override
                    public void paint(Graphics2D graphics) {
                        for (int i = 0; i < dimension.getWidth(); i++) {
                            for (int j = 0; j < dimension.getHeight(); j++) {
                                List<Integer> keys = new ArrayList<Integer>(gameGrid[j][i].keySet());
                                Collections.sort(keys);
                                for (Integer key : keys) {
                                    graphics.drawImage(gridImageMap.get(gameGrid[j][i].get(key)),
                                            HPADDING + i * TILEWIDTH, VPADDING + j * TILEHEIGHT, TILEWIDTH,
                                            TILEHEIGHT, null);
                                }
                            }
                        }
                        graphics.drawString(currentTurn.getPlayerViewModel().getName(), 20, 20);
                        graphics.drawString("moves remaining: " + currentTurn.getRemainingActions(), 20, 40);
                        graphics.drawString("selected item: " + currentTurn.getCurrentItem(), 200, 20);
                    }
                };


                gridImageMap.put(SquareStates.PLAYER1, gui.loadImage("player_red.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.PLAYER2, gui.loadImage("player_blue.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.EMPTY, gui.loadImage("cell.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.P1_FINISH, gui.loadImage("cell_finish_red.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.P2_FINISH, gui.loadImage("cell_finish_blue.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.P1_LIGHT_WALL, gui.loadImage("cell_lighttrail_red.png", TILEWIDTH,
                        TILEHEIGHT));
                gridImageMap.put(SquareStates.P2_LIGHT_WALL, gui.loadImage("cell_lighttrail_blue.png", TILEWIDTH,
                        TILEHEIGHT));
                gridImageMap.put(SquareStates.WALL, gui.loadImage("wall.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.LIGHT_MINE, gui.loadImage("lightgrenade.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.IDENTITY_DISK, gui.loadImage("identity_disk.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.CHARGED_IDENTITY_DISK, gui.loadImage("identity_disk_charged.png",
                        TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.POWERFAILURE, gui.loadImage("cell_unpowered.png", TILEWIDTH, TILEHEIGHT));
                gridImageMap.put(SquareStates.TELEPORTER, gui.loadImage("teleporter.png", TILEWIDTH, TILEHEIGHT));


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

                    gui.createButton(HPADDING + hMultiplier * 20, dimension.getHeight() * TILEHEIGHT + VPADDING + 20
                            + vMultiplier * 20, 20, 20, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                MovePlayerHandler movePlayerHandler = (MovePlayerHandler) catalog.getHandler
                                        (MovePlayerHandler.class);
                                movePlayerHandler.move(direction);
                            } catch (SquareOccupiedException e) {
                                new DialogView("The square is already occupied.");

                            } catch (InvalidMoveException e) {
                                new DialogView("Sorry that is not a valid move");
                            } catch (NotEnoughActionsException e) {
                                new DialogView("You have no actions remaining, end the turn.");
                            } catch (GameOverException e) {

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

                final Button pickupButton = gui.createButton(HPADDING + buttonWidth,
                        dimension.getHeight() * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {


                        try {
                            final PickUpItemHandler pickUpItemHandler = (PickUpItemHandler) catalog.getHandler
                                    (PickUpItemHandler.class);
                            final List<Item> items = pickUpItemHandler.getAvailableItems();
                            ItemSelectionAction action = new ItemSelectionAction() {
                                @Override
                                public void doAction(int index) {
                                    try {
                                        Position currentPosition = currentTurn.getPlayerViewModel().getPosition();
                                        gameGrid[currentPosition.getVIndex()][currentPosition.getHIndex()].remove
                                                (getItemSquareState(items.get(index)).zIndex);
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

                final Button inventoryButton = gui.createButton(HPADDING + 2 * buttonWidth,
                        dimension.getHeight() * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        try {
                            final UseItemHandler useItemHandler = (UseItemHandler) catalog.getHandler(UseItemHandler
                                    .class);
                            final List<Item> currentItems = useItemHandler.showInventory();
                            ItemSelectionAction action = new ItemSelectionAction() {

                                @Override
                                public void doAction(int index) {
                                    selectedItem = useItemHandler.selectItemFromInventory(index);
                                    gui.repaint();
                                }
                            };
                            new ItemListView(currentItems, action);
                        } catch (InventoryEmptyException e) {
                            new DialogView("Your inventory is empty");
                        }
                        gui.repaint();
                    }
                });
                inventoryButton.setText("Open inventory");

                final Button useItemButton = gui.createButton(HPADDING + 2 * buttonWidth,
                        dimension.getHeight() * TILEHEIGHT + VPADDING + 40, buttonWidth, 20, new Runnable() {
                    public void run() {
                        try {
                            final UseItemHandler useItemHandler = (UseItemHandler) catalog.getHandler(UseItemHandler
                                    .class);


                            if (selectedItem.contains("Identity Disc")) {
                                gui.repaint();
                                ItemSelectionAction action = new ItemSelectionAction() {

                                    @Override
                                    public void doAction(int index) throws GameOverException {
                                        Direction direction = Direction.values()[index];
                                        try {
                                            useItemHandler.useCurrentIdentityDisc(direction);
                                        } catch (SquareOccupiedException e) {
                                            new DialogView("The square is already occupied.");
                                        } catch (NotEnoughActionsException e) {
                                            new DialogView("You have no actions remaining, end the turn.");
                                        } catch (NoItemSelectedException e) {
                                            new DialogView("You don't have an item selected");
                                        }
                                    }
                                };
                                new DirectionListView(action);
                            } else {
                                useItemHandler.useCurrentItem();
                            }
                            selectedItem = "No item";
                            gui.repaint();
                        } catch (SquareOccupiedException e) {
                            new DialogView("The square is already occupied.");
                        } catch (NotEnoughActionsException e) {
                            new DialogView("You have no actions remaining, end the turn.");
                        } catch (NoItemSelectedException e) {
                            new DialogView("You don't have an item selected");
                        } catch (GameOverException e) {
                            new DialogView(e.getMessage());
                        }

                        gui.repaint();
                    }
                });
                useItemButton.setText("Use Item");

                final Button cancelItemButton = gui.createButton(HPADDING + 2 * buttonWidth,
                        dimension.getHeight() * TILEHEIGHT + VPADDING + 60, buttonWidth, 20, new Runnable() {
                    public void run() {
                        final UseItemHandler useItemHandler = (UseItemHandler) catalog.getHandler(UseItemHandler.class);
                        useItemHandler.cancelItemUsage();
                        selectedItem = "No item";
                        gui.repaint();
                        gui.repaint();
                    }
                });
                cancelItemButton.setText("Unselect item");

                final Button endTurnButton = gui.createButton(HPADDING + 3 * buttonWidth,
                        dimension.getHeight() * TILEHEIGHT + VPADDING + 20, buttonWidth, 20, new Runnable() {
                    public void run() {
                        try {
                            final EndTurnHandler endTurnHandler = (EndTurnHandler) catalog.getHandler(EndTurnHandler
                                    .class);
                            selectedItem = "no item";
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


    private void updatePlayer(PlayerViewModel playerViewModel) {
        Position currentPos = playerViewModel.getPosition();
        SquareStates state = playerColorMap.get(playerViewModel.getName())[0];
        gameGrid[currentPos.getVIndex()][currentPos.getHIndex()].put(state.zIndex, state);
        clearLastPositions(playerViewModel.getName());
        Position[] positions = new Position[playerViewModel.getLightTrail().size()];
        int index = 0;
        for (Position svm : playerViewModel.getLightTrail()) {
            SquareStates lightTrailState = playerColorMap.get(playerViewModel.getName())[1];
            gameGrid[svm.getVIndex()][svm.getHIndex()].put(lightTrailState.zIndex, lightTrailState);
            positions[index++] = svm;
        }
        lastPositions.put(playerViewModel.getName(), positions);
        gui.repaint();
    }

    private void clearLastPositions(String name) {
        for (Position p : lastPositions.get(name)) {
            gameGrid[p.getVIndex()][p.getHIndex()].remove(playerColorMap.get(name)[0].zIndex);
            gameGrid[p.getVIndex()][p.getHIndex()].remove(playerColorMap.get(name)[1].zIndex);
        }
    }

    @Override
    public void update(TurnViewModel vm, List<PlayerViewModel> players, GridViewModel gridModel) {
        currentTurn = vm;
        for (PlayerViewModel p : players) {
            updatePlayer(p);
        }

        for(SquareViewModel sq: gridModel.getSquareViewModels()){
            gameGrid[sq.getPosition().getVIndex()][sq.getPosition().getHIndex()].remove(SquareStates.POWERFAILURE.zIndex);
            for(EffectViewModel e:sq.getEffectViewModels()){
                 if(e.getEffect().equals("powerfailure")){
                     gameGrid[sq.getPosition().getVIndex()][sq.getPosition().getHIndex()].put(SquareStates.POWERFAILURE.zIndex, SquareStates.POWERFAILURE);
                 }
            }
        }

        gui.repaint();
    }


    @Override
    public void noPower(Position position) {
//nothing
    }

    @Override
    public void regainedPower(Position position) {
    //nothing
    }

    @Override
    public void itemPlaced(Item item, Position position) {
        SquareStates state = getItemSquareState(item);
        gameGrid[position.getVIndex()][position.getHIndex()].put(state.zIndex, state);
    }

    public enum SquareStates {
        WALL(100), P1_LIGHT_WALL(99), P2_LIGHT_WALL(98), PLAYER1(97), PLAYER2(96), EMPTY(0), POWERFAILURE(1), P1_FINISH(90), P2_FINISH(91), LIGHT_MINE(50), TELEPORTER(49), IDENTITY_DISK(48), CHARGED_IDENTITY_DISK(47);

        private int zIndex;

        SquareStates(int zIndex) {
            this.zIndex = zIndex;
        }
    }
}
