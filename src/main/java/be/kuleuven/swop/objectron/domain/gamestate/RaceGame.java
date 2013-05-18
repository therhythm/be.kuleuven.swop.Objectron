package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.grid.Grid;
import be.kuleuven.swop.objectron.domain.item.effect.RaceFinish;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 5/17/13
 * Time: 4:29 PM
 */
public class RaceGame extends Game {

    public RaceGame(List<String> playerNames, Grid gameGrid) {
        super(playerNames, gameGrid);
    }

    @Override
    protected void initialize(List<Player> players) {
        for(Player player : players){
            Square currentSquare = player.getCurrentSquare();
            Effect raceFinish = new RaceFinish(player);
            currentSquare.addEffect(raceFinish);
        }
    }
}
