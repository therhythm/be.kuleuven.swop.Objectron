package be.kuleuven.swop.objectron.model;

import java.util.*;

/**
 * @author : Nik Torfs
 *         Date: 22/02/13
 *         Time: 00:03
 */
public class Square {
    Map<Direction, Square> neighbours = new HashMap<Direction, Square>();
    private boolean isObstructed = false;

    public void addNeighbour(Direction direction, Square neighbour){
        neighbours.put(direction, neighbour);
    }

    public Square getNeighbour(Direction direction){
        return neighbours.get(direction);
    }

    public boolean isObstructed(){
        return isObstructed;
    }

    public void setObstructed(boolean value){
        isObstructed = value;
    }
}
