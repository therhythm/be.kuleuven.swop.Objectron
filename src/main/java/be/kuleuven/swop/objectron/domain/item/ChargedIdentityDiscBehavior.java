package be.kuleuven.swop.objectron.domain.item;

/**
 * A class of ChargedIdentityDiscBehaviors implementing IdentityDiscBehavior.
 * User: Peter
 * Date: 11/04/13
 * Time: 14:37
 */
public class ChargedIdentityDiscBehavior implements IdentityDiscBehavior {
    @Override
    public int getRemainingRange() {
        return 1;
    }

    @Override
    public void moved() {
        // do nothing the range doesn't get reduced here
    }

    @Override
    public String getName() {
        return "Charged Identity Disc";
    }

    @Override
    public void reset() {
        // do nothing
    }
}
