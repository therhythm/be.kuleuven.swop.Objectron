package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.GameState;
import be.kuleuven.swop.objectron.model.Direction;
import be.kuleuven.swop.objectron.model.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.model.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.viewmodel.PlayerViewModel;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:11
 */
public class MovePlayerHandler extends Handler {
    private static Logger logger = Logger.getLogger(MovePlayerHandler.class.getCanonicalName());

    public MovePlayerHandler(GameState state) {
        super(state);
    }

    /**
     * Move the player in a given direction.
     *
     * @param direction The direction the player wants to move in.
     * @throws be.kuleuven.swop.objectron.model.exception.InvalidMoveException
     *          This is an invalid move.
     *          | !state.getGrid().validPosition(
     *          |  player.getCurrentSquare().getNeighbour(direction))
     * @throws be.kuleuven.swop.objectron.model.exception.NotEnoughActionsException
     *          The player has not enough actions remaining.
     *          | state.getCurrentPlayer().getAvailableActions() == 0
     * @post The player is moved in the chosen direction.
     * | new.state.getCurrentPlayer().getCurrentSquare()
     * |  != state.getCurrentPlayer().getCurrentSquare()
     */
    public PlayerViewModel move(Direction direction) throws InvalidMoveException, NotEnoughActionsException {
        try {
            state.getGrid().makeMove(direction, state.getCurrentPlayer());
            return state.getCurrentPlayer().getPlayerViewModel();
        } catch (InvalidMoveException e) {
            logger.log(Level.INFO, state.getCurrentPlayer().getName() + " tried to do an invalid move!");
            throw e;
        } catch (NotEnoughActionsException e) {
            logger.log(Level.INFO, state.getCurrentPlayer().getName() + " tried to do a move when he had no actions remaining.");
            throw e;
        }
    }
}
