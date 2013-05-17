package be.kuleuven.swop.objectron.domain.gamestate.gamemode;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.item.effect.RaceFinish;
import be.kuleuven.swop.objectron.domain.square.Square;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 15/05/13
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
public class RaceMode implements  GameMode {
    public RaceMode() {
    }

    @Override
    public void initialize(List<Player> players) {
        for(Player player : players){
            Square currentSquare = player.getCurrentSquare();
            Effect raceFinish = new RaceFinish(player);
            currentSquare.addEffect(raceFinish);
        }
    }
}
