package be.kuleuven.swop.objectron.domain.gamestate.gamemode;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.item.Flag;
import be.kuleuven.swop.objectron.domain.item.effect.CtfFinish;
import be.kuleuven.swop.objectron.domain.item.effect.Effect;
import be.kuleuven.swop.objectron.domain.square.Square;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 15/05/13
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
public class CtfMode implements GameMode {
    public CtfMode() {

    }

    @Override
    public void initialize(List<Player> players) {
        for(Player player : players){
            Square currentSquare = player.getCurrentSquare();
            Flag flag = new Flag(player,currentSquare);
            Effect ctfFinish = new CtfFinish(player,players);
            currentSquare.addItem(flag);
            currentSquare.addEffect(ctfFinish);
        }
    }
}
