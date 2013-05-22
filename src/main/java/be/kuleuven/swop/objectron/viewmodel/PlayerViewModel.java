package be.kuleuven.swop.objectron.viewmodel;


import be.kuleuven.swop.objectron.domain.util.Position;

import java.util.List;

public class PlayerViewModel {
    private final String name;
    private final Position currentPosition;
    private final List<Position> lightTrail;

    public PlayerViewModel(String name, Position currentPosition, List<Position> lightTrailViewModel) {
        this.name = name;
        this.currentPosition = currentPosition;
        this.lightTrail = lightTrailViewModel;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return currentPosition;
    }

    public List<Position> getLightTrail() {
        return lightTrail;
    }
}
