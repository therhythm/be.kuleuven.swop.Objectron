package be.kuleuven.swop.objectron.domain.item.effect;

import be.kuleuven.swop.objectron.domain.Player;
import be.kuleuven.swop.objectron.domain.gamestate.Turn;
import be.kuleuven.swop.objectron.domain.square.Square;

/**
 * @author : Kasper Vervaecke
 *         Date: 09/04/13
 *         Time: 16:32
 */
public class Teleporter implements Effect {
    private Teleporter destination;
    private Square location;

    public Teleporter(Square location) {
        this.location = location;
    }

    @Override
    public void activate(Turn currentTurn) {
        Player currentPlayer = currentTurn.getCurrentPlayer();
        if (currentPlayer != null) {
            if (!currentPlayer.isTeleporting() && !destination.getLocation().isObstructed()) {
                currentPlayer.teleport(destination.getLocation());
            }
        }
    }

    @Override
    public void accept(EffectVisitor visitor) {
        visitor.visitTeleporter();
    }

    public Teleporter getDestination() {
        return destination;
    }

    public void setDestination(Teleporter destination) {
        this.destination = destination;
    }

    public Square getLocation() {
        return location;
    }
}
