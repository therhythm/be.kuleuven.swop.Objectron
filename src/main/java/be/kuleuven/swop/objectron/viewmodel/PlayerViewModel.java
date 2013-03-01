package be.kuleuven.swop.objectron.viewmodel;

import be.kuleuven.swop.objectron.model.item.ItemSpecification;

import java.util.Collections;
import java.util.List;

public class PlayerViewModel {
    private final String name;
    private final int hPosition;
    private final int vPosition;
    private final int availableActions;
    private final ItemSpecification selectedItem;
    private final List<SquareViewModel> lightTrail;

    public PlayerViewModel(String name, int hPosition, int vPosition, int availableActions, ItemSpecification selectedItem, List<SquareViewModel> lightTrailViewModel) {
        this.name = name;
        this.hPosition = hPosition;
        this.vPosition = vPosition;
        this.availableActions = availableActions;
        this.selectedItem = selectedItem;
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

    public ItemSpecification getSelectedItem() {
        return selectedItem;
    }

    public List<SquareViewModel> getLightTrail() {
        return Collections.unmodifiableList(lightTrail);
    }
}
