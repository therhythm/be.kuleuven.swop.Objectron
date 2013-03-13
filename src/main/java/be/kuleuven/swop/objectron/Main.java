package be.kuleuven.swop.objectron;

import be.kuleuven.swop.objectron.gui.GameMenu;
import be.kuleuven.swop.objectron.handler.StartGameHandler;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 01:47
 */
public class Main {

    public static void main(String[] args) {
        new GameMenu(new StartGameHandler());
    }
}
