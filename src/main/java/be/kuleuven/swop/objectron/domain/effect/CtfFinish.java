package be.kuleuven.swop.objectron.domain.effect;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.exception.NotEnoughActionsException;
import be.kuleuven.swop.objectron.domain.exception.SquareOccupiedException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.Flag;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.item.deployer.ReturnFlagToBaseDeployCommand;
import be.kuleuven.swop.objectron.domain.movement.Movable;

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

    /**
     * Initiates a CTFFinish of a certain player and a given list of players
     * @param starter the player that starts on this finish
     * @param players a list of players that can finish here
     */
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

    /**
     * Check if anyone won
     */
    private boolean checkWin() {
        if (collectedFlags.size() == (players.size() - 1))
            return true;
        return false;
    }

    /**
     * Collect a flag on this finish
     * @param flag the flag to collect
     * @param player the player that has the flag
     * @throws SquareOccupiedException
     *         there is something blocking this square
     * @throws NotEnoughActionsException
     *         player hasn't got enough actions left
     * @throws GameOverException
     *         Player wins the game
     */
    private void collectFlag(Flag flag, Player player) throws SquareOccupiedException, NotEnoughActionsException,
            GameOverException {
        ReturnFlagToBaseDeployCommand returnFlagToBaseDeployer = new ReturnFlagToBaseDeployCommand();
        collectedFlags.add(flag);
        player.useItem(flag, returnFlagToBaseDeployer);
    }

    @Override
    public void accept(EffectVisitor visitor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
