package be.kuleuven.swop.objectron;

import be.kuleuven.swop.objectron.handler.StartGameHandler;
import be.kuleuven.swop.objectron.ui.Menu;
import be.kuleuven.swop.objectron.ui.StartGameMenu;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 01:47
 */
public class Main {

    public static void main(String[] args) {
        new Menu(new StartGameHandler());
    }
}
