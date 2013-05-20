package be.kuleuven.swop.objectron.domain.effect;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Flag;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.deployer.ReturnFlagToBaseDeployer;
import be.kuleuven.swop.objectron.domain.movement.Movable;
import be.kuleuven.swop.objectron.viewmodel.EffectViewModel;

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
        collectedFlags = new HashSet<Flag>();
        this.players = players;
    }


    @Override
    public void activate(Movable movable, TurnManager manager) throws GameOverException, NotEnoughActionsException,
            SquareOccupiedException {
        if (movable instanceof Player) {
            Player player = (Player) movable;
            if (player.equals(starter)) {
                List<Item> items = new ArrayList<Item>();
                items.addAll(player.getInventoryItems());
                for (Item item : items) {
                    if (item instanceof Flag) {
                        collectFlag((Flag) item, player);
                        if (checkWin())
                            throw new GameOverException(manager.getCurrentTurn().getCurrentPlayer().getName() + ", " +
                                    "you win the game!");
                    }
                }
                //Todo remove this when useless
                System.out.println("aantal verzamelde vlaggen: " + this.collectedFlags.size());
            }
        }
    }

    @Override
    public EffectViewModel getViewModel() {
        return new EffectViewModel("ctffinish");
    }

    private boolean checkWin() {
        if (collectedFlags.size() == (players.size() - 1))
            return true;
        return false;
    }

    private void collectFlag(Flag flag, Player player) throws SquareOccupiedException, NotEnoughActionsException,
            GameOverException {
        ReturnFlagToBaseDeployer returnFlagToBaseDeployer = new ReturnFlagToBaseDeployer();
        collectedFlags.add(flag);
        player.useItem(flag, returnFlagToBaseDeployer);
    }

    @Override
    public void accept(EffectVisitor visitor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
