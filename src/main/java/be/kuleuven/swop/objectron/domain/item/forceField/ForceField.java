package be.kuleuven.swop.objectron.domain.item.forceField;

import be.kuleuven.swop.objectron.domain.Obstruction;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.exception.ForceFieldHitException;
import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
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
 * A class of ForceFields involving 2 ForcefieldGenerators and a list of Squares implementing Obstruction.
 * User: Peter
 * Date: 28/04/13
 * Time: 9:49
 */

public class ForceField implements Obstruction {
    public static final int TURNSWITCH = 2;

    private ForcefieldGenerator forceField1;
    private ForcefieldGenerator forceField2;
    private int currentTurnSwitch = TURNSWITCH;
    private List<Square> affectedSquares;
    private boolean active;
    private List<Player> playerHitLastTime;

    /**
     * Initialize this ForceField with 2 given ForcefieldGenerators and a list of Squares.
     * @param forceField1
     *        The first ForceFieldGenerator for this ForceField.
     * @param forceField2
     *        The second ForceFieldGenerator for this ForceField.
     * @param squaresBetween
     *        The list of Squares between the 2 generators.
     */
    public ForceField(ForcefieldGenerator forceField1, ForcefieldGenerator forceField2, List<Square> squaresBetween) {
        this.forceField1 = forceField1;
        this.forceField2 = forceField2;
        this.affectedSquares = squaresBetween;
        this.playerHitLastTime = new ArrayList<>();
        deactivate();
    }

    private void activate(TurnManager turnManager) {
        active = true;
        List<Player> newListPlayerHit = new ArrayList<>();
        for (Square square : affectedSquares) {
            square.addObstruction(this);

            for (Player player : turnManager.getPlayers()) {
                if (player.getCurrentSquare().equals(square)) {
                    player.setIncapacitated(true);
                    if (playerHitLastTime.contains(player)) {
                        turnManager.killPlayer(player);
                    } else {
                        newListPlayerHit.add(player);
                    }
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

        for (Player player : playerHitLastTime) {
                player.setIncapacitated(false);
        }
    }

    private void switchActivation(TurnManager turnManager) {
        currentTurnSwitch = TURNSWITCH;
        if (active)
            deactivate();
        else
            activate(turnManager);

    }

    /**
     * Update this ForceField.
     * @param turnManager
     *        The TurnManager to execute the update with.
     */
    public void update(TurnManager turnManager) {
        currentTurnSwitch--;
        if (currentTurnSwitch == 0) {
            this.switchActivation(turnManager);
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
