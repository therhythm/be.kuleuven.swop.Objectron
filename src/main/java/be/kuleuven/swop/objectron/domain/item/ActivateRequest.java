package be.kuleuven.swop.objectron.domain.item;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.gamestate.GameState;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;

/**
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 11/04/13
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public class ActivateRequest {
    private GameState gamestate;
    private Player playerHit;

    public ActivateRequest(Player playerHit,GameState gamestate) {
        this.gamestate = gamestate;
        this.playerHit = playerHit;
    }

    public ActivateRequest(GameState gamestate) {
        this.gamestate = gamestate;
        this.playerHit = null;
    }

    public GameState getGamestate() {
        return gamestate;
    }

    public Player getPlayerHit() {
        return playerHit;
    }

    public Turn getCurrentTurn(){
        return gamestate.getCurrentTurn();
    }

    public Player getCurrentPlayer(){
        return gamestate.getCurrentPlayer();
    }


}