package be.kuleuven.swop.objectron.viewmodel;

import java.util.Collections;
import java.util.List;

public class PlayerViewModel {
    private final String name;
    private final int hPosition;
    private final int vPosition;
    private final int availableActions;
    private final List<SquareViewModel> lightTrail;

    public PlayerViewModel(String name, int hPosition, int vPosition, int availableActions, List<SquareViewModel> lightTrailViewModel) {
        this.name = name;
        this.hPosition = hPosition;
        this.vPosition = vPosition;
        this.availableActions = availableActions;
        this.lightTrail = lightTrailViewModel;
    }

    public String getName() {
        return name;
    }

    public int getHPosition() {
        return hPosition;
    }

    public int getVPosition() {
        return vPosition;
    }

    public int getAvailableActions() {
        return availableActions;
    }

    public List<SquareViewModel> getLightTrail() {
        return Collections.unmodifiableList(lightTrail);
    }
}
