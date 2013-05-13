package be.kuleuven.swop.objectron.ui;

import be.kuleuven.swop.objectron.domain.exception.GameOverException;

/**
 * @author : Nik Torfs
 *         Date: 01/03/13
 *         Time: 10:00
 */
public interface ItemSelectionAction {
    void doAction(int index) throws GameOverException;
}
