package be.kuleuven.swop.objectron.handler;


import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.game.Game;

/**
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:14
 */
public class EndTurnHandler extends Handler {


    public EndTurnHandler(Game state) {
        super(state);
    }

    /**
     * End the current player's turn.
     *
     * @throws be.kuleuven.swop.objectron.domain.exception.GameOverException
     *          The player hasn't moved during this turn and loses the game.
     *          | !state.getCurrentPlayer().hasMoved()
     * @post The current player is switched to a new player.
     * | new.state.getCurrentPlayer() != state.getCurrentPlayer()
     */
    public void endTurn() throws GameOverException {
        if (!state.getCurrentTurn().hasMoved()) {
            throw new GameOverException("You haven't moved the previous turn and therefore you have lost the game");
        }

        state.endTurn();
    }
}
