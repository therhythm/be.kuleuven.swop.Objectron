package be.kuleuven.swop.objectron.domain.effect;

/**
 * @author : Nik Torfs
 *         Date: 01/05/13
 *         Time: 15:17
 */
public interface EffectVisitor {
    void visitTeleporter();

    void visitLightMine();

    void visitForceField();//todo effect or not?
}
