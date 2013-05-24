package be.kuleuven.swop.objectron.domain.item;

/**
 * An interface for IdentityDiscBehaviors.
 * User: Peter
 * Date: 11/04/13
 * Time: 14:49
 */
public interface IdentityDiscBehavior {

    int getRemainingRange();

    /**
     * The identity disc has moved.
     */
    void moved();

    String getName();

    /**
     * Reset this IdentityDiscBehavior.
     */
    void reset();
}
