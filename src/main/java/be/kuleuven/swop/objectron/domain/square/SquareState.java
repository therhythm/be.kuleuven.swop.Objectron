package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Player;

/**
 * @author : Nik Torfs
 *         Date: 13/03/13
 *         Time: 02:57
 */
public interface SquareState {
    void newTurn(Player player, boolean currentSquare, Transitionable<SquareState> context);

    void stepOn(Player player);

    void powerFailure(Transitionable<SquareState> context);
}
