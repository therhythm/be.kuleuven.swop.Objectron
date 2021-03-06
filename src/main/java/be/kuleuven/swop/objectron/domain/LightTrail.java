package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.movement.Movement;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * A class of LightTrails implementing Obstruction.
 * @author : Nik Torfs
 *         Date: 27/02/13
 *         Time: 22:45
 */
public class LightTrail implements Obstruction {
    private static int MAX_LIGHT_TRAIL_COVERAGE = 3;
    private static int LIGHT_TRAIL_LIFETIME = 3;

    private Square[] trail;
    private int[] remainingActions;

    /**
     * Initialize this LightTrail.
     */
    public LightTrail() {
        trail = new Square[MAX_LIGHT_TRAIL_COVERAGE];
        remainingActions = new int[MAX_LIGHT_TRAIL_COVERAGE];
    }

    /**
     * Expand the lighttrail with a given square.
     * @param newSquare
     *        The square to expand the lighttrail with.
     */
    public void expand(Square newSquare) {
        if (trail[MAX_LIGHT_TRAIL_COVERAGE - 1] != null) {
            trail[MAX_LIGHT_TRAIL_COVERAGE - 1].removeObstruction(this);
        }

        System.arraycopy(trail, 0, trail, 1, MAX_LIGHT_TRAIL_COVERAGE - 1);
        System.arraycopy(remainingActions, 0, remainingActions, 1, MAX_LIGHT_TRAIL_COVERAGE - 1);

        trail[0] = newSquare;
        remainingActions[0] = LIGHT_TRAIL_LIFETIME;
        newSquare.addObstruction(this);
    }

    private void retract() {
        for (int i = 0; i < MAX_LIGHT_TRAIL_COVERAGE; i++) {
            if (remainingActions[i] == 0) {
                trail[i].removeObstruction(this);
                trail[i] = null;
                remainingActions[i] = 0;
            }
        }
    }

    /**
     * Reduce the lighttrail.
     */
    public void reduce() {
        for (int i = 0; i < MAX_LIGHT_TRAIL_COVERAGE; i++) {
            remainingActions[i]--;
        }
        retract();
    }

    /**
     * Return a list of Positions in the lighttrail.
     * @return the Positions in the lighttrail.
     */
    public List<Position> getLightTrailViewModel() {
        List<Position> list = new ArrayList<Position>();

        for (Square s : trail) {
            if (s != null) {
                list.add(s.getPosition());
            }
        }
        return list;
    }

    @Override
    public void hit(Movement movement) throws InvalidMoveException {
        movement.hitLightTrail(this);
    }
}
