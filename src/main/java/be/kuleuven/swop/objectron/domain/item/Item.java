package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Direction;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Nik Torfs
 *         Date: 21/02/13
 *         Time: 23:52
 */
public interface Item {

    String getName();

    void place(Square targetSquare) throws SquareOccupiedException;  //TODO remove exception when effects are in place

    void throwMe(Square sourceSquare, Direction targetDirection, TurnManager turnManager);
}
