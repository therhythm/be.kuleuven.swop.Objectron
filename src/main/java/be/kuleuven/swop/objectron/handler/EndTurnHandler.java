package be.kuleuven.swop.objectron.handler;


import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;

/**
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:14
 */
public class EndTurnHandler extends Handler {


    public EndTurnHandler(Game game) {
        super(game);
    }

    /**
     * End the current player's turn.
     *
     * @throws be.kuleuven.swop.objectron.domain.exception.GameOverException
     *          The player hasn't moved during this turn and loses the gamestate.
     *          | !game.getCurrentPlayer().hasMoved()
     * @post The current player is switched to a new player.
     * | new.game.getCurrentPlayer() != game.getCurrentPlayer()
     */
    public void endTurn() throws GameOverException {
        TurnManager turnManager = game.getTurnManager();

        turnManager.endTurn();
        if(turnManager.checkWin())
            throw new GameOverException(turnManager.getCurrentTurn().getCurrentPlayer().getName() + ", you win the game!");
    }
}
