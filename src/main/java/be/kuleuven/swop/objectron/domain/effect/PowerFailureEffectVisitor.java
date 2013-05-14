package be.kuleuven.swop.objectron.domain.effect;

/**
 * @author : Nik Torfs
 *         Date: 01/05/13
 *         Time: 15:18
 */
public class PowerFailureEffectVisitor implements EffectVisitor {
    boolean hasLightMine = false;

    public boolean hasLightMine() {
        return hasLightMine;
    }

    @Override
    public void visitTeleporter() {
        // do nothing
    }

    @Override
    public void visitLightMine() {
        hasLightMine = true;
    }

    @Override
    public void visitForceField() {
        // do nothing
    }
}
