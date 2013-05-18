package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.item.Flag;
import be.kuleuven.swop.objectron.domain.item.effect.CtfFinish;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 5/17/13
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class CTFGame extends Game {
    public CTFGame(List<String> playerNames, Grid gameGrid){
        super(playerNames, gameGrid);
    }

    @Override
    protected void initialize(List<Player> players) {
        for(Player player : players){
            Square currentSquare = player.getCurrentSquare();
            Flag flag = new Flag(player,currentSquare);
            Effect ctfFinish = new CtfFinish(player,players);
            currentSquare.addItem(flag);
            currentSquare.addEffect(ctfFinish);
        }
    }
}
