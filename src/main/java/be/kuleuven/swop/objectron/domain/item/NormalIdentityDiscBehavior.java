package be.kuleuven.swop.objectron.domain.item;

/**
 * A class of NormalIdentityDiscBehaviors implementing IdentityDiscBehavior.
 * Created with IntelliJ IDEA.
 * User: Piet
 * Date: 11/04/13
 * Time: 14:37
 */
public class NormalIdentityDiscBehavior implements IdentityDiscBehavior {

    public static final int MAX_RANGE = 4;

    private int remainingRange;

    /**
     * Initialize this NormalIdentityDiscBehavior.
     */
    public NormalIdentityDiscBehavior() {
        this.remainingRange = MAX_RANGE;
    }

    @Override
    public int getRemainingRange() {
        return remainingRange;
    }

    @Override
    public void moved() {
        remainingRange--;
    }

    @Override
    public String getName() {
        return "Uncharged Identity Disc";
    }

    @Override
    public void reset() {
        remainingRange = MAX_RANGE;
    }
}
