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
    private List<Class<?>> effectViewModels;

    public SquareViewModel(Position position, List<Class<?>> effectViewModels){
        this.effectViewModels = effectViewModels;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public List<Class<?>> getEffectViewModels() {
        return effectViewModels;
    }
}
