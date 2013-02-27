package be.kuleuven.swop.objectron.model.listener;

import be.kuleuven.swop.objectron.model.Square;

/**
 * @author : Nik Torfs
 *         Date: 26/02/13
 *         Time: 23:12
 */
public interface GridEventListener {

    void gridUpdated(Square[][] squares);

    void squareUpdated(int hIndex, int vIndex, Square square);

}
