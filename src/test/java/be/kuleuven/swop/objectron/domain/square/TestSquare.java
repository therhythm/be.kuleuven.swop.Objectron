package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.LightMine;
import be.kuleuven.swop.objectron.domain.item.effect.Effect;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.domain.exception.ForceFieldHitException;
import be.kuleuven.swop.objectron.domain.exception.PlayerHitException;
import be.kuleuven.swop.objectron.domain.exception.WallHitException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
        Player player = new Player("test", new Square(new Position(5, 4)));
        turnManager = mock(TurnManager.class);
        Turn turn = new Turn(player);
        when(turnManager.getCurrentTurn()).thenReturn(turn);
    }


    @Test
    public void test_step_on_active_item() throws InvalidMoveException, ForceFieldHitException, WallHitException, PlayerHitException {
        Effect found = null;
        for(Effect effect : square.getEffects()){
            if(effect.equals(item)) {
                found = effect;
                break;
            }
        }
        assertTrue(found != null);

        square.stepOn(mock(Movable.class), turnManager);

        found = null;
        for(Effect effect : square.getEffects()){
            if(effect.equals(item)) {
                found = effect;
                break;
            }
        }
        assertFalse(((LightMine) found).isActive());
    }
}
