package be.kuleuven.swop.objectron.domain.item;

/**
 * Created with IntelliJ IDEA.
 * User: Peter
 * Date: 11/04/13
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
public interface IdentityDiscBehavior {

    int getRemainingRange();

    void moved();

    String getName();

    void reset();
}
