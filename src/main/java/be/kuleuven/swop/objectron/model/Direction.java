package be.kuleuven.swop.objectron.model;

/**
 * @author : Nik Torfs
 *         Date: 27/02/13
 *         Time: 21:48
 */
public enum Direction {
    LEFT(-1,0), RIGHT(+1,0), UP(0,-1), DOWN(0,+1), UP_LEFT(-1,-1), UP_RIGHT(+1,-1), DOWN_LEFT(-1,+1), DOWN_RIGHT(+1,+1);

    private int hOperation;
    private int vOperation;

    private Direction(int hOperation, int vOperation){
        this.hOperation = hOperation;
        this.vOperation = vOperation;
    }

    public int applyHorizontalOperation(int horizontalIndex){
        return horizontalIndex + hOperation;
    }

    public int applyVerticalOperation(int verticalIndex){
        return verticalIndex + vOperation;
    }
}
