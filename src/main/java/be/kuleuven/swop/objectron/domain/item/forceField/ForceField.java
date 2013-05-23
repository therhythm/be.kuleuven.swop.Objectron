package be.kuleuven.swop.objectron.domain.item.forceField;

import be.kuleuven.swop.objectron.domain.Obstruction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.ForceFieldHitException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnObserver;
import be.kuleuven.swop.objectron.domain.gamestate.TurnSwitchObserver;
import be.kuleuven.swop.objectron.domain.movement.MovementStrategy;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 28/04/13
 * Time: 9:49
 * To change this template use File | Settings | File Templates.
 */

public class ForceField implements Obstruction {
    public static final int TURNSWITCH = 2;

    private ForcefieldGenerator forceField1;
    private ForcefieldGenerator forceField2;
    private int currentTurnSwitch = TURNSWITCH;
    private List<Square> affectedSquares;
    private boolean active;
    private List<Player> playerHitLastTime;

    public ForceField(ForcefieldGenerator forceField1, ForcefieldGenerator forceField2, List<Square> squaresBetween) {

        this.forceField1 = forceField1;
        this.forceField2 = forceField2;
        this.affectedSquares = squaresBetween;
        this.playerHitLastTime = new ArrayList<Player>();
        deactivate();
    }

    private void activate(TurnObserver observable, List<Player> players) {
        active = true;
        for (Square square : affectedSquares) {
            square.addObstruction(this);
        }
        List<Player> newListPlayerHit = new ArrayList<Player>();

        for (Player player : players) {
            for (Square square : affectedSquares) {
                if (player.getCurrentSquare().equals(square)) {
                    if (playerHitLastTime.contains(player)) {
                        observable.killPlayer(player);
                    }
                    newListPlayerHit.add(player);
                }
            }
        }
        playerHitLastTime = newListPlayerHit;

    }

    private void deactivate() {
        active = false;
        for (Square square : affectedSquares) {
            square.removeObstruction(this);
        }
    }

    private void switchActivation(TurnObserver observable, List<Player> players) {
        currentTurnSwitch = TURNSWITCH;
        if (active)
            deactivate();
        else
            activate(observable, players);

    }

    public void update(TurnObserver observable, List<Player> players) {
        currentTurnSwitch--;
        if (currentTurnSwitch == 0) {
            this.switchActivation(observable, players);
        }


    }

    public void prepareToRemove() {
        this.deactivate();
    }

    public boolean contains(ForcefieldGenerator forcefield) {
        return forcefield.equals(forceField1) || forcefield.equals(forceField2);
    }

    @Override
    public void hit(MovementStrategy strategy) throws InvalidMoveException, ForceFieldHitException {
        strategy.hitForceField(this);
    }
}
