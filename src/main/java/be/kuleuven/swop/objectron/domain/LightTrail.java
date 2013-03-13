package be.kuleuven.swop.objectron.domain;

import be.kuleuven.swop.objectron.viewmodel.SquareViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 27/02/13
 *         Time: 22:45
 */
public class LightTrail {
    private static int MAX_TRAIL_COVERAGE = 3;
    private static int LIGHT_TRAIL_LIFETIME = 3;

    private Square[] trail;
    private int[] remainingActions;

    public LightTrail() {
        trail = new Square[MAX_TRAIL_COVERAGE];
        remainingActions = new int[MAX_TRAIL_COVERAGE];
    }

    public void expand(Square newSquare) {
        if (trail[MAX_TRAIL_COVERAGE - 1] != null) {
            trail[MAX_TRAIL_COVERAGE - 1].setObstructed(false);
        }

        System.arraycopy(trail, 0, trail, 1, MAX_TRAIL_COVERAGE - 1);
        System.arraycopy(remainingActions, 0, remainingActions, 1, MAX_TRAIL_COVERAGE - 1);

        trail[0] = newSquare;
        remainingActions[0] = LIGHT_TRAIL_LIFETIME;
        newSquare.setObstructed(true);
    }

    private void retract() {
        for (int i = 0; i < MAX_TRAIL_COVERAGE; i++) {
            if (remainingActions[i] == 0) {
                trail[i].setObstructed(false);
                trail[i] = null;
                remainingActions[i] = 0;
            }
        }
    }

    public void reduce() {
        for (int i = 0; i < MAX_TRAIL_COVERAGE; i++) {
            remainingActions[i]--;
        }
        retract();
    }

    public List<SquareViewModel> getLightTrailViewModel() {
        List<SquareViewModel> list = new ArrayList<SquareViewModel>();

        for (Square s : trail) {
            if (s != null) {
                list.add(s.getSquareViewModel());
            }
        }
        return list;
    }
}
