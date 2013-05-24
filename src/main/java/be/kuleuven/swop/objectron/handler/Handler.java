package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.gamestate.Game;

/**
 * An abstract class of Handlers involving a Game.
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:14
 */
public abstract class Handler {
    protected Game game;

    /**
     * Initialize a new Handler with the given Game.
     * @param game
     *        The game for this new Handler.
     * @post  This Handler is initialized with the given Game.
     *        | new.this.game == game
     */
    protected Handler(Game game) {
        this.game = game;
    }
}
