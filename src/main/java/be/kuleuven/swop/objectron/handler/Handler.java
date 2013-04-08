package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.game.Game;

/**
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:14
 */
public abstract class Handler {
    protected Game state;

    protected Handler(Game state) {
        this.state = state;
    }
}
