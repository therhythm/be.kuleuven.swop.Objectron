package be.kuleuven.swop.objectron.domain.square;

import be.kuleuven.swop.objectron.domain.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 3/05/13
 * Time: 10:13
 * To change this template use File | Settings | File Templates.
 */
public class PowerFailure {     /*

    public static final int PF_PRIMARY_TURNS = 3;
    public static final int PF_PRIMARY_ACTIONS = 0;

    public static final int PF_SECONDARY_TURNS = 0;
    public static final int PF_SECONDARY_ACTIONS = 2;

    public static final int PF_TERTIARY_TURNS = 0;
    public static final int PF_TERTIARY_ACTIONS = 1;

    public static final String DIRECTION_CLOCKWISE = "CLOCKWISE";
    public static final String DIRECTION_COUNTER_CLOCKWISE = "COUNTERCLOCKWISE";

    private String rotateDirection = "";
    private Direction prevDirection;
    private int rotateCounter;

    private Square currentSquare;
    private List<Direction> directions;

    public PowerFailure(Square currentSquare, ArrayList<Direction> directions) {
        this.currentSquare = currentSquare;
        this.directions = directions;

    }


    public PowerFailure(Square currentSquare) {
        this.currentSquare = currentSquare;
        this.directions = new ArrayList<Direction>();

        for (Direction direction : Direction.values()) {
            directions.add(direction);
        }
    }

    public void receivePrimaryPowerFailure() {
        currentSquare.receivePowerFailure(PF_PRIMARY_TURNS, PF_PRIMARY_ACTIONS);
        initiateSecondaryPowerfailure();
    }

    public void rotate() {
        rotateCounter++;
        if (rotateCounter >= 2) {
            rotateCounter = 0;
            if (this.rotateDirection == DIRECTION_CLOCKWISE)
                this.prevDirection = prevDirection.next();
            else
                this.prevDirection = prevDirection.previous();

            Square mustReceiveFailure = currentSquare.getNeighbour(prevDirection);
            if (mustReceiveFailure != null) {
                passSecondaryPowerFailure(mustReceiveFailure, prevDirection);
            }

        }
    }

    private void initiateSecondaryPowerfailure() {
        double random = Math.random();
        if (random < 0.5) {
            this.rotateDirection = DIRECTION_CLOCKWISE;
        } else {
            this.rotateDirection = DIRECTION_COUNTER_CLOCKWISE;
        }

        int startSquare = (int) Math.floor(Math.random() * directions.size());
        int i = 0;
        for (Direction d : directions) {
            if (i == startSquare) {
                passSecondaryPowerFailure(currentSquare.getNeighbour(d), d);

            }

            i++;
        }
    }

    private void passSecondaryPowerFailure(Square secondarySquare, Direction direction) {
        ArrayList<Direction> secondaryNeighbours = new ArrayList<Direction>();
        secondaryNeighbours.add(direction);
        secondaryNeighbours.add(direction.previous());
        secondaryNeighbours.add(direction.next());


        PowerFailure secondaryFailure = new PowerFailure(secondarySquare, secondaryNeighbours);
        secondaryFailure.receiveSecondaryPowerFailure(direction);

        this.prevDirection = direction;
    }

    public void receiveSecondaryPowerFailure(Direction d) {
        if (currentSquare != null) {
            currentSquare.receivePowerFailure(PF_SECONDARY_TURNS, PF_SECONDARY_ACTIONS);
            passTertiaryPowerFailure(d);
        }
    }


    private void passTertiaryPowerFailure(Direction d) {
        List<Direction> possibleDirections = new ArrayList<Direction>();
        possibleDirections.add(d);
        possibleDirections.add(d.next());
        possibleDirections.add(d.previous());

        int index = (int) Math.floor(Math.random() * possibleDirections.size());
        Square neighbour = currentSquare.getNeighbour((possibleDirections.get(index)));
        if (neighbour != null) {
            PowerFailure tertiaryFailure = new PowerFailure(neighbour);
            tertiaryFailure.receiveTertiaryPowerFailure();
        }
    }

    public void receiveTertiaryPowerFailure() {
        currentSquare.receivePowerFailure(PF_TERTIARY_TURNS, PF_TERTIARY_ACTIONS);
    }      */
}
