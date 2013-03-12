package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.GameState;

/**
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:14
 */
public abstract class Handler {
    protected GameState state;

    protected Handler(GameState state) {
        this.state = state;
    }
}
