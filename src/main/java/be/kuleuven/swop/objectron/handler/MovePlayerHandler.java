package be.kuleuven.swop.objectron.handler;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.Game;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * A class of MovePlayerHandlers involving a Game.
 * @author : Nik Torfs
 *         Date: 12/03/13
 *         Time: 01:11
 */
public class MovePlayerHandler extends Handler {

    /**
     * Initialize this new MovePlayerHandler with a given Game.
     * @param game
     *        The game for this new MovePlayerHandler.
     * @post  This MovePlayerHandler is initialized with the given Game.
     *        | new.this.game == game
     */
    public MovePlayerHandler(Game game) {
        super(game);
    }

    /**
     * Move the player in a given direction.
     *
     * @param direction
     *        The direction the player wants to move in.
     * @throws be.kuleuven.swop.objectron.domain.exception.InvalidMoveException
     *          This is an invalid move.
     *          | !game.buildGrid().validPosition(
     *          |  player.getCurrentSquare().getNeighbour(direction))
     * @throws be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException
     *          The player has not enough actions remaining.
     *          | currentTurn.getAvailableActions() == 0
     * @throws GameOverException
     *         The game is finished.
     *         | game.getTurnManager().checkWin() == true
     * @post The player is moved in the chosen direction.
     * | new.game.getCurrentPlayer().getCurrentSquare()
     * |  != game.getCurrentPlayer().getCurrentSquare()
     */
    public void move(Direction direction) throws InvalidMoveException, NotEnoughActionsException, GameOverException,
            SquareOccupiedException {
        TurnManager turnManager = game.getTurnManager();
        Turn currentTurn = turnManager.getCurrentTurn();
        currentTurn.checkEnoughActions();
        Player current = currentTurn.getCurrentPlayer();
        Square newSquare = game.getGrid().makeMove(direction, current.getCurrentSquare());
        current.move(direction, turnManager);
        currentTurn.setMoved();
        currentTurn.reduceAction();
    }
}
