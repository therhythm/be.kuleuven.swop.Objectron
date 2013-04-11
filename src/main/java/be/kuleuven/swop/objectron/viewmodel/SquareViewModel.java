package be.kuleuven.swop.objectron.viewmodel;

/**
 * @author : Nik Torfs
 *         Date: 01/03/13
 *         Time: 16:21
 */
public class SquareViewModel {
    private final int hIndex;
    private final int vIndex;

    public SquareViewModel(int hIndex, int vIndex) {
        this.hIndex = hIndex;
        this.vIndex = vIndex;
    }

    public int getHIndex() {
        return hIndex;
    }

    public int getVIndex() {
        return vIndex;
    }
}
