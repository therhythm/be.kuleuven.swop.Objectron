package be.kuleuven.swop.objectron.viewmodel;


import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.gamestate.GameObserver;
import be.kuleuven.swop.objectron.domain.item.Item;
import be.kuleuven.swop.objectron.domain.util.Dimension;
import be.kuleuven.swop.objectron.domain.util.Observable;
import be.kuleuven.swop.objectron.domain.util.Position;
import be.kuleuven.swop.objectron.handler.HandlerCatalog;

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
    private TurnViewModel currentTurn;
    private List<List<Position>> walls;

    private Map<Position, List<Item>> items;

    private Observable<GameObserver> observable;
    private Map<Position, List<Effect>> effects;


    public GameStartViewModel(HandlerCatalog catalog,
                              Dimension dimension,
                              PlayerViewModel p1,
                              PlayerViewModel p2,
                              TurnViewModel currentTurn,
                              List<List<Position>> walls,
                              Map<Position, List<Item>> items,
                              Map<Position, List<Effect>> effects,
                              Observable<GameObserver> observable) {

        this.catalog = catalog;
        this.dimension = dimension;
        this.p1 = p1;
        this.p2 = p2;
        this.currentTurn = currentTurn;
        this.walls = walls;
        this.items = items;
        this.effects = effects;
        this.observable = observable;
    }

    public HandlerCatalog getCatalog() {
        return catalog;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public PlayerViewModel getP1() {
        return p1;
    }

    public PlayerViewModel getP2() {
        return p2;
    }

    public TurnViewModel getCurrentTurn() {
        return this.currentTurn;
    }

    public List<List<Position>> getWalls() {
        return walls;
    }


    public Map<Position, List<Item>> getItems() {
        return items;
    }


    public Observable<GameObserver> getObservable() {

        return observable;
    }

    public Map<Position, List<Effect>> getEffects() {
        return effects;
    }
}
