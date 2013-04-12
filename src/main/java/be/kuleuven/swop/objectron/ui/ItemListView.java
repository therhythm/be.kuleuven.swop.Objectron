package be.kuleuven.swop.objectron.ui;

import be.kuleuven.swop.objectron.domain.item.IdentityDisc;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.LightMine;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ItemListView {

    Map<Class<?>, Image> itemMap = new HashMap<Class<?>, Image>();

    public ItemListView(final java.util.List<Item> items, final ItemSelectionAction action) {
        final SimpleGUI inv = new SimpleGUI("Inventory", 150, 100) {
            @Override
            public void paint(Graphics2D graphics) {
            }
        };

        inv.setCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        itemMap.put(LightMine.class, inv.loadImage("lightgrenade_big.png", 40, 40));
        itemMap.put(IdentityDisc.class, inv.loadImage("identity_disk_big.png", 40, 40));
        int hcount = 0, vcount = 0;
        for (int i = 0; i < items.size(); i++) {
            final int index = i;
            hcount = i % 3;
            vcount = (int)Math.floor((double)i/3.0);
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
