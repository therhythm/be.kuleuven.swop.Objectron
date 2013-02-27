package be.kuleuven.swop.objectron.model;

/**
 * @author : Nik Torfs
 *         Date: 27/02/13
 *         Time: 22:45
 */
public class LightTrail {
    private static int MAX_TRAIL_COVERAGE = 3;
    private Square[] trail;

    public LightTrail(){
        trail = (Square[]) new Object[MAX_TRAIL_COVERAGE];
    }

    public void expand(Square newSquare){
        if(trail[MAX_TRAIL_COVERAGE-1] != null){
            trail[MAX_TRAIL_COVERAGE-1].setObstructed(false);
        }

        System.arraycopy(trail, 0, trail, 1, MAX_TRAIL_COVERAGE - 1);

        trail[0] = newSquare;
        newSquare.setObstructed(true);
    }

    public void retract(){
        for(int i = MAX_TRAIL_COVERAGE - 1; i>-1; i--){
            if(trail[i] != null){
                trail[i].setObstructed(false);
                trail[i] = null;
                break;
            }
        }
    }

    //TODO method checking if going through trails
}
