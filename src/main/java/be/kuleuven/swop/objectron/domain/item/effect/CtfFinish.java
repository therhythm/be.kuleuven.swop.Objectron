package be.kuleuven.swop.objectron.domain.item.effect;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Flag;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.movement.Movable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 13/05/13
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
public class CtfFinish implements Effect {
    private Player starter;
    private Map<Player, Flag> collectedFlags;

    public CtfFinish(Player starter,List<Player> players) {
        this.starter = starter;
        collectedFlags = new HashMap<Player,Flag>();
        initializeCollectedFlags(players);
    }

    private void initializeCollectedFlags(List<Player> players) {
        for(Player player : players){
            collectedFlags.put(player,null);
        }
    }

    @Override
    public void activate(Movable movable, TurnManager manager) throws GameOverException {
        if (movable instanceof Player){
            Player player = (Player) movable;
            List<Item> items =player.getInventoryItems();
            for(Item item : items){
                if (item instanceof Flag){
                    collectFlag((Flag)item);
                    if(checkWin())
                        throw new GameOverException(manager.getCurrentTurn().getCurrentPlayer().getName() + ", you win the game!");
                }
            }
        }
    }

    private boolean checkWin() {
        for(Player player : collectedFlags.keySet()){
            if(collectedFlags.get(player)==null){
                return false;
            }
        }
        return true;
    }

    private void collectFlag(Flag flag) {
        collectedFlags.put(flag.getOwner(),flag);
    }

    @Override
    public void accept(EffectVisitor visitor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
