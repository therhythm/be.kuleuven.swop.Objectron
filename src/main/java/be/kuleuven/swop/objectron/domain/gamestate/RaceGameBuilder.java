package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.effect.RaceFinish;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.TooManyPlayersException;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;

import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 20/05/13
 *         Time: 02:32
 */
public class RaceGameBuilder extends GameBuilder {
    private static final int MAX_PLAYERS = 2;

    public RaceGameBuilder(List<String> playerNames, Dimension dimension) throws GridTooSmallException, TooManyPlayersException {
        super(playerNames, dimension);
        if(playerNames.size() > MAX_PLAYERS){
            throw new TooManyPlayersException("You can only play with " + MAX_PLAYERS + " on this grid");
        }
    }

    @Override
    protected List<Player> initializePlayers(List<Square> playerPositions) {
        List<Player> players = super.initializePlayers(playerPositions);

        for (Player player : players) {
            Square currentSquare = player.getCurrentSquare();
            Effect raceFinish = new RaceFinish(player);
            currentSquare.addEffect(raceFinish);
        }

        return players;
    }
}
