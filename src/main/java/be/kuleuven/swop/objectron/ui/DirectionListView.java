package be.kuleuven.swop.objectron.ui;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.LightMine;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 11/04/13
 * Time: 22:51
 * To change this template use File | Settings | File Templates.
 */
public class DirectionListView {


    Map<Direction, Image> directionImageMap = new HashMap<Direction, Image>();

    SimpleGUI gui;


    public DirectionListView(final ItemSelectionAction action) {
        gui = new SimpleGUI("Inventory", 150, 100) {
            @Override
            public void paint(Graphics2D graphics) {

            }
        };
        directionImageMap.put(Direction.UP, gui.loadImage("arrow_N.png", 20, 20));
        directionImageMap.put(Direction.LEFT, gui.loadImage("arrow_W.png", 20, 20));
        directionImageMap.put(Direction.RIGHT, gui.loadImage("arrow_E.png", 20, 20));
        directionImageMap.put(Direction.DOWN, gui.loadImage("arrow_S.png", 20, 20));

        //directionImageMap.put(Direction.class, gui.loadImage("lightgrenade_big.png", 40, 40));
        int hcount = 0, vcount = 0;

        for (int i = 0; i < 4; i++) {
            final int index = i;
            gui.createButton(10 + hcount * 40, 10 + vcount * 40, 40, 40, new Runnable() {
                @Override
                public void run() {
                    action.doAction(index);
                    gui.dispose();
                }
            }).setImage(directionImageMap.get(i));
        }
    }
}
