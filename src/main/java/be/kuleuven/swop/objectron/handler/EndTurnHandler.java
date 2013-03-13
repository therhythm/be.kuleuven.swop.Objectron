package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.Square;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

/**
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:14
 */
public class EndTurnHandler extends Handler {


    public EndTurnHandler(GameState state) {
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
    public PlayerViewModel endTurn() throws GameOverException {
        if (!state.getCurrentPlayer().hasMoved()) {
            throw new GameOverException("You haven't moved the previous turn and therefore you have lost the game");
        }
        state.getCurrentPlayer().endTurn();
        state.nextPlayer();



        return state.getCurrentPlayer().getPlayerViewModel();
    }
}
