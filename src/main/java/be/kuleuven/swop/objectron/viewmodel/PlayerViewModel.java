package be.kuleuven.swop.objectron.viewmodel;


import java.util.Collections;
import java.util.List;

public class PlayerViewModel {
    private final String name;
    private final SquareViewModel currentSquare;
    private final SquareViewModel initialSquare;
    private final int availableActions;
    private final List<SquareViewModel> lightTrail;

    public PlayerViewModel(String name, SquareViewModel currentSquare, SquareViewModel initialSquare, int availableActions, List<SquareViewModel> lightTrailViewModel) {
        this.name = name;
        this.currentSquare = currentSquare;
        this.initialSquare = initialSquare;
        this.availableActions = availableActions;
        this.lightTrail = lightTrailViewModel;
    }

    public String getName() {
        return name;
    }

    public int getHPosition() {
        return currentSquare.getHIndex();
    }

    public int getVPosition() {
        return currentSquare.getVIndex();
    }

    public int getAvailableActions() {
        return availableActions;
    }

    public List<SquareViewModel> getLightTrail() {
        return Collections.unmodifiableList(lightTrail);
    }
}
