package be.kuleuven.swop.objectron.gui;

import be.kuleuven.swop.objectron.controller.GameController;
import be.kuleuven.swop.objectron.model.InventoryFullException;
import be.kuleuven.swop.objectron.model.Item;
import be.kuleuven.swop.objectron.model.LightMine;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ItemListView {

    Map<Class<?>, Image> itemMap = new HashMap<Class<?>, Image>();

    SimpleGUI inv;

    public ItemListView(final java.util.List<Item> items, final GameController controller) {
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
                    try {
                        controller.pickUpItem(index);
                    } catch (InventoryFullException e) {
                        new DialogView("Your inventory is full!");
                    }
                    inv.dispose();
                }
            }).setImage(itemMap.get(items.get(index).getClass()));
        }
    }
}
