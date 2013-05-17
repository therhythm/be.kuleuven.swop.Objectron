package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.gamestate.Game;

/**
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:14
 */
public abstract class Handler {
    protected Game game;

    protected Handler(Game game) {
        this.game = game;
    }
}
