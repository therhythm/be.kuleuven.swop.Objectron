package be.kuleuven.swop.objectron.gui;

import be.kuleuven.swop.objectron.model.item.Item;
import be.kuleuven.swop.objectron.model.item.LightMine;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ItemListView {

    Map<Class<?>, Image> itemMap = new HashMap<Class<?>, Image>();

    SimpleGUI inv;


    public ItemListView(final java.util.List<Item> items, final ItemSelectionAction action) {
        inv = new SimpleGUI("Inventory", 150, 100) {
            @Override
            public void paint(Graphics2D graphics) {

            }
        };

        itemMap.put(LightMine.class, inv.loadImage("src/main/resources/player_red.png", 40, 40));
        int hcount = 0, vcount = 0;
        for (int i = 0; i < items.size(); i++) {
            final int index = i;
            inv.createButton(10 + hcount * 40, 10 + vcount * 40, 40, 40, new Runnable() {
                @Override
                public void run() {
                    action.doAction(index);
                    inv.dispose();
                }
            }).setImage(itemMap.get(items.get(index).getClass()));
        }
    }

}
