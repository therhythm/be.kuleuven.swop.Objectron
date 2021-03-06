package be.kuleuven.swop.objectron.domain.gamestate;

import be.kuleuven.swop.objectron.domain.CTFPlayer;
import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.effect.CtfFinish;
import be.kuleuven.swop.objectron.domain.effect.Effect;
import be.kuleuven.swop.objectron.domain.exception.GridTooSmallException;
import be.kuleuven.swop.objectron.domain.exception.NumberOfPlayersException;
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

    /**
     * Inatialize a new CTFGameBuilder with given list of player names and dimension
     *
     * @param playerNames A list of player names
     * @param dimension The dimension for the size of the grid
     * @throws GridTooSmallException
     *         The dimension is too small
     * @throws NumberOfPlayersException
     *         There are too many players in the given List
     */
    public CTFGameBuilder(List<String> playerNames, Dimension dimension) throws GridTooSmallException, NumberOfPlayersException {
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

    /**
     *
     * @param playerPositions adds players to the game on the given position
     * @return A list of CTFPlayers with a name and a startingposition
     */

    private List<Player> buildPlayers(List<Square> playerPositions) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < playerNames.size(); i++) {
            players.add(new CTFPlayer(playerNames.get(i), playerPositions.get(i)));
        }

        return players;
    }
}
