package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 27/02/13
 *         Time: 22:45
 */
public class LightTrail {

    private Square[] trail;
    private int[] remainingActions;

    public LightTrail() {
        trail = new Square[Settings.MAX_LIGHT_TRAIL_COVERAGE];
        remainingActions = new int[Settings.MAX_LIGHT_TRAIL_COVERAGE];
    }

    public void expand(Square newSquare) {
        if (trail[Settings.MAX_LIGHT_TRAIL_COVERAGE - 1] != null) {
            trail[Settings.MAX_LIGHT_TRAIL_COVERAGE - 1].setObstructed(false);
        }

        System.arraycopy(trail, 0, trail, 1, Settings.MAX_LIGHT_TRAIL_COVERAGE - 1);
        System.arraycopy(remainingActions, 0, remainingActions, 1, Settings.MAX_LIGHT_TRAIL_COVERAGE - 1);

        trail[0] = newSquare;
        remainingActions[0] = Settings.LIGHT_TRAIL_LIFETIME;
        newSquare.setObstructed(true);
    }

    private void retract() {
        for (int i = 0; i < Settings.MAX_LIGHT_TRAIL_COVERAGE; i++) {
            if (remainingActions[i] == 0) {
                trail[i].setObstructed(false);
                trail[i] = null;
                remainingActions[i] = 0;
            }
        }
    }

    public void reduce() {
        for (int i = 0; i < Settings.MAX_LIGHT_TRAIL_COVERAGE; i++) {
            remainingActions[i]--;
        }
        retract();
    }

    public List<Position> getLightTrailViewModel() {
        List<Position> list = new ArrayList<Position>();

        for (Square s : trail) {
            if (s != null) {
                list.add(s.getPosition());
            }
        }
        return list;
    }
}
