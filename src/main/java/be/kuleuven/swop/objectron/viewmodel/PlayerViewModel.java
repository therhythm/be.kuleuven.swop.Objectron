package be.kuleuven.swop.objectron.viewmodel;


import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.List;

public class PlayerViewModel {
    private final String name;
    private final Position currentSquare;
    private final Position initialSquare;
    private final int availableActions;
    private final List<Position> lightTrail;

    public PlayerViewModel(String name, Position currentSquare, Position initialSquare, int availableActions, List<Position> lightTrailViewModel) {
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

    public List<Position> getLightTrail() {
        return lightTrail;
    }
}
