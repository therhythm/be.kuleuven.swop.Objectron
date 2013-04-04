package be.kuleuven.swop.objectron.viewmodel;

import be.kuleuven.swop.objectron.domain.gamestate.GameObservable;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Position;
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
    private Dimension dimension;
    private PlayerViewModel p1;
    private PlayerViewModel p2;
    private List<List<Position>> walls;
    private GameObservable observable;

    public GameStartViewModel(HandlerCatalog catalog,
                              Dimension dimension,
                              PlayerViewModel p1,
                              PlayerViewModel p2,
                              List<List<Position>> walls,
                              GameObservable observable) {
        this.catalog = catalog;
        this.dimension = dimension;
        this.p1 = p1;
        this.p2 = p2;
        this.walls = walls;
        this.observable = observable;
    }

    public HandlerCatalog getCatalog() {
        return catalog;
    }

    public Dimension getDimension(){
        return this.dimension;
    }

    public PlayerViewModel getP1() {
        return p1;
    }

    public PlayerViewModel getP2() {
        return p2;
    }

    public List<List<Position>> getWalls() {
        return walls;
    }

    public GameObservable getObservable() {
        return observable;
    }
}
