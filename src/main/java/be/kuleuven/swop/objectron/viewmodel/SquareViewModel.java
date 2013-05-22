package be.kuleuven.swop.objectron.viewmodel;

import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas
 * Date: 20/05/13
 * Time: 19:44
 * To change this template use File | Settings | File Templates.
 */
public class SquareViewModel {
    private Position position;
    private List<Class<?>> effects;
    private List<Class<?>> obstructions;

    public SquareViewModel(Position position, List<Class<?>> effects, List<Class<?>> obstructions){
        this.effects = effects;
        this.position = position;
        this.obstructions = obstructions;
    }

    public Position getPosition() {
        return position;
    }

    public List<Class<?>> getEffectViewModels() {
        return effects;
    }

    public List<Class<?>> getObstructions() {
        return obstructions;
    }
}
