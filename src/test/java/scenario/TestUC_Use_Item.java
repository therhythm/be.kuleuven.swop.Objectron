package scenario;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.*;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.LightMine;
import be.kuleuven.swop.objectron.domain.item.effect.Effect;
import be.kuleuven.swop.objectron.domain.item.effect.PowerFailureEffectVisitor;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.PickUpItemHandler;
import be.kuleuven.swop.objectron.handler.UseItemHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Scenario test for UC Use Item
 *
 * @author : Kasper Vervaecke
 *         Date: 22/02/13
 *         Time: 15:59
 */
public class TestUC_Use_Item {
    private UseItemHandler useItemHandler;
    private Player player;
    private Item item;
    private GameState stateMock;
    private TurnManager turnManager;


    @Before
    public void setUp() throws Exception {
        Square square = new Square(new Position(0, 0));
        item = new LightMine();
        square.addItem(item);
        player = new Player("p1", square);
        Turn turn = new Turn(player);
        turnManager = mock(TurnManager.class);
        when(turnManager.getCurrentTurn()).thenReturn(turn);

        stateMock = mock(GameState.class);
        when(stateMock.getTurnManager()).thenReturn(turnManager);

        useItemHandler = new UseItemHandler(stateMock);
    }

    @Test(expected = InventoryEmptyException.class)
    public void showInventoryEmptyTest() throws InventoryEmptyException {
        useItemHandler.showInventory();
    }

    @Test
    public void showInventoryTest() throws InventoryFullException, InventoryEmptyException, NotEnoughActionsException {
        player.pickupItem(0);

        List<Item> inventoryItems = useItemHandler.showInventory();

        assertFalse(inventoryItems.isEmpty());
        assertEquals(player.getInventoryItems(), inventoryItems);
    }

    @Test
    public void selectItemTest() throws InventoryFullException, NotEnoughActionsException {
        player.pickupItem(0);

        String selected = useItemHandler.selectItemFromInventory(0);

        assertEquals(item.getName(), selected);
    }

    @Test
    public void useItemTest() throws InventoryFullException, SquareOccupiedException, NotEnoughActionsException, NoItemSelectedException {
        player.pickupItem(0);
        turnManager.getCurrentTurn().setCurrentItem(item);

        int initialAvailableActions = stateMock.getTurnManager().getCurrentTurn().getActionsRemaining();
        int initialNumberOfItemsInInventory = player.getInventoryItems().size();

        useItemHandler.selectItemFromInventory(0);
        useItemHandler.useCurrentItem();

        assertEquals(initialAvailableActions - 1, stateMock.getTurnManager().getCurrentTurn().getActionsRemaining());
        assertEquals(initialNumberOfItemsInInventory - 1, player.getInventoryItems().size());

        Effect found = null;
        for(Effect effect : player.getCurrentSquare().getEffects()){
            if(effect.equals(item)) {
                found = effect;
                break;
            }
        }
        assertTrue(found != null);
    }

    @Test
    public void cancelItemUsageTest() throws InventoryFullException, NotEnoughActionsException {
        player.pickupItem(0);

        int initialAvailableActions = stateMock.getTurnManager().getCurrentTurn().getActionsRemaining();
        int initialNumberOfItemsInInventory = player.getInventoryItems().size();

        useItemHandler.selectItemFromInventory(0);
        useItemHandler.cancelItemUsage();

        assertEquals(initialAvailableActions, stateMock.getTurnManager().getCurrentTurn().getActionsRemaining());
        assertEquals(initialNumberOfItemsInInventory, player.getInventoryItems().size());

        Effect found = null;
        for(Effect effect : player.getCurrentSquare().getEffects()){
            if(effect.equals(item)) {
                found = effect;
                break;
            }
        }
        assertTrue(found == null);
    }

    @Test(expected = NotEnoughActionsException.class)
    public void test_no_more_actions() throws NotEnoughActionsException, InvalidMoveException, InventoryFullException, SquareOccupiedException, NoItemSelectedException {
        PickUpItemHandler pickUpItemHandler = new PickUpItemHandler(stateMock);
        pickUpItemHandler.pickUpItem(0);
        player.getCurrentSquare().addItem(new LightMine());
        pickUpItemHandler.pickUpItem(0);
        player.getCurrentSquare().addItem(new LightMine());
        pickUpItemHandler.pickUpItem(0);
        turnManager.getCurrentTurn().setCurrentItem(item);
        useItemHandler.selectItemFromInventory(0);
        useItemHandler.useCurrentItem();
    }

    @Test(expected = NoItemSelectedException.class)
    public void test_no_item_selected() throws NotEnoughActionsException, SquareOccupiedException, NoItemSelectedException {
        useItemHandler.useCurrentItem();
    }
}
