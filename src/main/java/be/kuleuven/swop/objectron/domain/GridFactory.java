package be.kuleuven.swop.objectron.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 26/03/13
 * Time: 20:46
 * To change this template use File | Settings | File Templates.
 */
public interface GridFactory {
    public void buildGrid(int horizontalPositionPlayer1, int verticalPositionPlayer1, int horizontalPositionPlayer2, int verticalPositionPlayer2);

    public Grid getGameGrid();
    }
