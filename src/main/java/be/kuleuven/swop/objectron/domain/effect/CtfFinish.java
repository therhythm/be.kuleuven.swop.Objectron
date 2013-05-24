package be.kuleuven.swop.objectron.domain.effect;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Flag;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.domain.movement.Movement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public CtfFinish(Player starter, List<Player> players) {
        this.starter = starter;
        collectedFlags = new HashSet<>();
        this.players = players;
    }


    @Override
    public void activate(Movable movable, TurnManager manager) {
        if (movable instanceof Player) {
            Player player = (Player) movable;
            if (player.equals(starter)) {
                List<Item> items = new ArrayList<>();
                items.addAll(player.getInventoryItems());
                for (Item item : items) {
                    if (item instanceof Flag) {
                        collectFlag((Flag) item, player);
                        if (checkWin()){
                            manager.gameWon();
                        }
                    }
                }
            }
        }
    }

    private boolean checkWin() {
        return collectedFlags.size() == (players.size() - 1);
    }

    private void collectFlag(Flag flag, Player player) {
        collectedFlags.add(flag);
        flag.returnToBase();
        player.removeItem(flag);
    }

    @Override
    public void accept(EffectVisitor visitor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void activate(Movement movement, TurnManager manager) {
        Movable movable = movement.getMovable();
        if (movable instanceof Player) {
            Player player = (Player) movable;
            if (player.equals(starter)) {
                List<Item> items = new ArrayList<>();
                items.addAll(player.getInventoryItems());
                for (Item item : items) {
                    if (item instanceof Flag) {
                        collectFlag((Flag) item, player);
                        if (checkWin()){
                            manager.gameWon();
                        }
                    }
                }
            }

        }
    }
}
