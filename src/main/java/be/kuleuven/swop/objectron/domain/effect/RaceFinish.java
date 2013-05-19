package be.kuleuven.swop.objectron.domain.effect;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.effect.EffectVisitor;
import be.kuleuven.swop.objectron.domain.exception.GameOverException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.movement.Movable;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 13/05/13
 * Time: 20:13
 * To change this template use File | Settings | File Templates.
 */
public class RaceFinish implements Effect {

    private Player starter;

    public RaceFinish(Player starter) {
        this.starter = starter;
    }

    @Override
    public void activate(Movable movable, TurnManager manager) throws GameOverException {
        if (movable instanceof Player){
            if (!((Player) movable).equals(this.starter)){
                throw new GameOverException(manager.getCurrentTurn().getCurrentPlayer().getName() + ", you win the game!");
            }
        }
    }

    @Override
    public void accept(EffectVisitor visitor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
