package be.kuleuven.swop.objectron.domain.util;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/5/13
 * Time: 2:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class Position {
    private final int hIndex;
    private final int vIndex;

    public Position(int hIndex, int vIndex){
        this.hIndex = hIndex;
        this.vIndex = vIndex;
    }

    public int getHIndex(){
        return this.hIndex;
    }

    public int getVIndex(){
        return this.vIndex;
    }
}
