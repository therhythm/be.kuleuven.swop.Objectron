package be.kuleuven.swop.objectron.model.item;

/**
 * @author : Nik Torfs
 *         Date: 01/03/13
 *         Time: 12:45
 */
public class ItemSpecification {
    private final String name;
    private final String description;

    public ItemSpecification(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
}
