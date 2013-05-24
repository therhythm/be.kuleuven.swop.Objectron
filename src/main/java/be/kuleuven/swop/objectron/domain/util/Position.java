package be.kuleuven.swop.objectron.domain.util;

/**
 * A class of Positions involving a horizontal and vertical index.
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/5/13
 * Time: 2:36 AM
 */
public class Position {
    private final int hIndex;
    private final int vIndex;

    /**
     * Initialize this Position with a given horizontal and vertical index.
     * @param hIndex
     *        The horizontal index for this Position.
     * @param vIndex
     *        The vertical index for this Position.
     */
    public Position(int hIndex, int vIndex) {
        this.hIndex = hIndex;
        this.vIndex = vIndex;
    }

    public int getHIndex() {
        return this.hIndex;
    }

    public int getVIndex() {
        return this.vIndex;
    }

    public String toString() {
        String result = "";
        result += "horizontal: " + hIndex + "\t" + "vertical: " + vIndex;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (hIndex != position.hIndex) return false;
        if (vIndex != position.vIndex) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hIndex;
        result = 31 * result + vIndex;
        return result;
    }
}
