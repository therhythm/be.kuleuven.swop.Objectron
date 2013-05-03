package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.square.Square;

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
     * @throws be.kuleuven.swop.objectron.domain.exception.InvalidMoveException
     *          This is an invalid move.
     *          | !state.getGrid().validPosition(
     *          |  player.getCurrentSquare().getNeighbour(direction))
     * @throws be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException
     *          The player has not enough actions remaining.
     *          | state.getCurrentPlayer().getAvailableActions() == 0
     * @post The player is moved in the chosen direction.
     * | new.state.getCurrentPlayer().getCurrentSquare()
     * |  != state.getCurrentPlayer().getCurrentSquare()
     */
    public void move(Direction direction) throws InvalidMoveException, NotEnoughActionsException, GameOverException {
        TurnManager turnManager = state.getTurnManager();
        Turn currentTurn = turnManager.getCurrentTurn();
        try {

            currentTurn.checkEnoughActions();
            Player current = currentTurn.getCurrentPlayer();
            Square newSquare = state.getGrid().makeMove(direction, current.getCurrentSquare());
            current.move(newSquare, turnManager);
            //newSquare.stepOn(turnManager);
            currentTurn.setMoved();
            if (state.checkWin())
                throw new GameOverException(current.getName() + ", you win the game!");

            currentTurn.reduceRemainingActions(1);
            state.endAction();
            state.notifyObservers();
        } catch (InvalidMoveException e) {
            logger.log(Level.INFO, currentTurn.getCurrentPlayer().getName() + " tried to do an invalid move!");
            throw e;
        } catch (NotEnoughActionsException e) {
            logger.log(Level.INFO, currentTurn.getCurrentPlayer().getName() + " tried to do a move when he had no actions remaining.");
            throw e;
        }
    }
}
