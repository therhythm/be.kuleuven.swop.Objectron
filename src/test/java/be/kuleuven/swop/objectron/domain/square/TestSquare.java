package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.RacePlayer;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.LightMine;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.movement.Movement;
import be.kuleuven.swop.objectron.domain.util.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 13/03/13
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public class TestSquare {
    private Square square;
    private Item item;
    private TurnManager turnManager;

    @Before
    public void setUp() throws GridTooSmallException, SquareOccupiedException {
        square = new Square(new Position(5, 5));
        item = new LightMine();
        item.place(square);
        Player player = new RacePlayer("test", new Square(new Position(5, 4)));
        turnManager = mock(TurnManager.class);
        Turn turn = new Turn(player);
        when(turnManager.getCurrentTurn()).thenReturn(turn);
    }


    @Test
    public void test_step_on_active_item() throws InvalidMoveException, ForceFieldHitException, WallHitException,
            PlayerHitException, GameOverException, NotEnoughActionsException, SquareOccupiedException {
        Effect found = null;
        for (Effect effect : square.getEffects()) {
            if (effect.equals(item)) {
                found = effect;
                break;
            }
        }
        assertTrue(found != null);

        square.stepOn(mock(Movement.class), turnManager);

        found = null;
        for (Effect effect : square.getEffects()) {
            if (effect.equals(item)) {
                found = effect;
                break;
            }
        }
        assertNotNull(found);
        assertFalse(((LightMine) found).isActive());
    }
}
