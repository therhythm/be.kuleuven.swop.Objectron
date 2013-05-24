package be.kuleuven.swop.objectron.handler;


import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;

/**
 * A class of EndTurnHandlers involving a Game.
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:14
 */
public class EndTurnHandler extends Handler {

    /**
     * Initialize this EndTurnHandler with a given Game.
     * @param game
     *        The game for this EndTurnHandler.
     * @post  This EndTurnHandler is initialized with the given Game.
     *        | new.this.game == game
     */
    public EndTurnHandler(Game game) {
        super(game);
    }

    /**
     * End the current player's turn.
     *
     * @throws be.kuleuven.swop.objectron.domain.exception.GameOverException
     *          The game is over.
     *          | turnManager.checkWin() == true
     * @post The current player is switched to a new player.
     * | new.turnManager.getCurrentTurn().getCurrentPlayer() != turnManager.getCurrentTurn().getCurrentPlayer()
     */
    public void endTurn() throws GameOverException {
        TurnManager turnManager = game.getTurnManager();

        turnManager.endTurn();
        if (turnManager.checkWin())
            throw new GameOverException(turnManager.getCurrentTurn().getCurrentPlayer().getName() + ", " +
                    "you win the game!");
    }
}
