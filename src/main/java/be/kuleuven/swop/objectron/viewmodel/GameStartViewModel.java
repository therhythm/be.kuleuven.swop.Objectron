package be.kuleuven.swop.objectron.viewmodel;

import be.kuleuven.swop.objectron.handler.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Nik Torfs
 *         Date: 14/03/13
 *         Time: 23:17
 */
public class GameStartViewModel {
    private HandlerCatalog catalog;
    private int nbHorizontalTiles;
    private int nbVerticalTiles;
    private PlayerViewModel p1;
    private PlayerViewModel p2;
    private List<List<SquareViewModel>> walls;

    public GameStartViewModel(HandlerCatalog catalog,
                              int nbHorizontalTiles,
                              int nbVerticalTiles,
                              PlayerViewModel p1,
                              PlayerViewModel p2,
                              List<List<SquareViewModel>> walls) {
        this.catalog = catalog;
        this.nbHorizontalTiles = nbHorizontalTiles;
        this.nbVerticalTiles = nbVerticalTiles;
        this.p1 = p1;
        this.p2 = p2;
        this.walls = walls;
    }

    public HandlerCatalog getCatalog() {
        return catalog;
    }

    public int getNbHorizontalTiles() {
        return nbHorizontalTiles;
    }

    public int getNbVerticalTiles() {
        return nbVerticalTiles;
    }

    public PlayerViewModel getP1() {
        return p1;
    }

    public PlayerViewModel getP2() {
        return p2;
    }

    public List<List<SquareViewModel>> getWalls() {
        return walls;
    }
}
