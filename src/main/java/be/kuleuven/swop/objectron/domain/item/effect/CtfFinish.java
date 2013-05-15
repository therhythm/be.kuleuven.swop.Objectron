package be.kuleuven.swop.objectron.domain.item.effect;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Flag;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.movement.Movable;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 13/05/13
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
public class CtfFinish implements Effect {
    private List<Player> players;
    private Player starter;
    private Set<Flag> collectedFlags;

    public CtfFinish(Player starter,List<Player> players) {
        this.starter = starter;
        collectedFlags = new HashSet<Flag>();
        this.players = players;
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
        if(collectedFlags.size()==players.size())
            return true;
        return false;
    }

    private void collectFlag(Flag flag) {
        collectedFlags.add(flag);
    }

    @Override
    public void accept(EffectVisitor visitor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
