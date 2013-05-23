package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.CTFPlayer;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.effect.CtfFinish;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.TooManyPlayersException;
import be.kuleuven.swop.objectron.domain.item.Flag;
import be.kuleuven.swop.objectron.domain.square.Square;
import be.kuleuven.swop.objectron.domain.util.Dimension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Nik Torfs
 *         Date: 20/05/13
 *         Time: 02:46
 */
public class CTFGameBuilder extends GameBuilder {
    public CTFGameBuilder(List<String> playerNames, Dimension dimension) throws GridTooSmallException, TooManyPlayersException {
        super(playerNames, dimension);
    }

    @Override
    protected List<Player> initializePlayers(List<Square> playerPositions) {
        List<Player> players = buildPlayers(playerPositions);

        for (Player player : players) {
            Square currentSquare = player.getCurrentSquare();
            Flag flag = new Flag(player, currentSquare);
            Effect ctfFinish = new CtfFinish(player, players);
            currentSquare.addItem(flag);
            currentSquare.addEffect(ctfFinish);
        }

        return players;
    }


    private List<Player> buildPlayers(List<Square> playerPositions) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playerNames.size(); i++) {
            players.add(new CTFPlayer(playerNames.get(i), playerPositions.get(i)));
        }

        return players;
    }
}
