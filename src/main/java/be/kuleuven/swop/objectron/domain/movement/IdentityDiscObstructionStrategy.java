package be.kuleuven.swop.objectron.domain.movement;

import be.kuleuven.swop.objectron.domain.exception.InvalidMoveException;
import be.kuleuven.swop.objectron.domain.gamestate.TurnManager;
import be.kuleuven.swop.objectron.domain.item.IdentityDisc;
import be.kuleuven.swop.objectron.domain.obstruction.LightTrail;
import be.kuleuven.swop.objectron.domain.obstruction.Player;
import be.kuleuven.swop.objectron.domain.obstruction.Wall;

/**
 * @author : Nik Torfs
 *         Date: 30/04/13
 *         Time: 23:29
 */
public class IdentityDiscObstructionStrategy implements ObstructionStrategy{
    IdentityDisc disc;

    public IdentityDiscObstructionStrategy(IdentityDisc disc) {
        this.disc = disc;
    }

    @Override
    public void hitWall(Wall wall, TurnManager manager) throws InvalidMoveException{

    }

    @Override
    public void hitPlayer(Player player, TurnManager manager) {
        disc.playerHit(player, manager);
    }

    @Override
    public void hitLightTrail(LightTrail lightTrail, TurnManager manager) {
        // do nothing
    }
}
