package be.kuleuven.swop.objectron.ui;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.LightMine;

import javax.swing.*;
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

    public DirectionListView(final ItemSelectionAction action) {
        SimpleGUI gui = new SimpleGUI("Choose direction", 140, 140) {
            @Override
            public void paint(Graphics2D graphics) {

            }
        };
        gui.setCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        gui.createButton(50, 10, 40, 40, new Runnable(){

            @Override
            public void run() {
                action.doAction(Direction.UP.ordinal());
            }
        }).setImage(gui.loadImage("arrow_N.png", 20, 20));

        gui.createButton(90, 50, 40, 40, new Runnable(){
            @Override
            public void run() {
                action.doAction(Direction.RIGHT.ordinal());
            }
        }).setImage(gui.loadImage("arrow_E.png", 20, 20));

        gui.createButton(50, 90, 40, 40, new Runnable(){
            @Override
            public void run() {
                action.doAction(Direction.DOWN.ordinal());
            }
        }).setImage(gui.loadImage("arrow_S.png", 20, 20));

        gui.createButton(10, 50, 40, 40, new Runnable(){
            @Override
            public void run() {
                action.doAction(Direction.LEFT.ordinal());
            }
        }).setImage(gui.loadImage("arrow_W.png", 20, 20));
    }
}
