package be.kuleuven.swop.objectron.listener;

/**
 * @author : Nik Torfs
 *         Date: 01/03/13
 *         Time: 10:03
 */
public interface GameEventListener {
    void playerUpdated(int hPosition, int vPosition, int availableActions);
    void playerChanged(String name, int availableActions);

}
