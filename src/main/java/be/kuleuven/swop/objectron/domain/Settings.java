package be.kuleuven.swop.objectron.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/4/13
 * Time: 1:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class Settings {
    // Grid settings
    public static final double MAX_WALL_COVERAGE_PERCENTAGE = 0.2;
    public static final int MIN_WALL_LENGTH = 2;
    public static final double MAX_WALL_LENGTH_PERCENTAGE = 0.5;
    public static final double PERCENTAGE_OF_ITEMS = 0.05;
    public static final int MIN_GRID_WIDTH = 10;
    public static final int MIN_GRID_HEIGHT = 10;

    //Lightmine settings
    public static final int LIGHTMINE_NB_ACTIONS_BLINDED = 3;

    //Square settings
    public static final int SQUARE_TURNS_WITHOUT_POWER = 3;
    public static final int SQUARE_ACTIONS_TO_REDUCE = 1;
    public static final int POWER_FAILURE_CHANCE = 5;

    //Inventory settings
    public static final int INVENTORY_LIMIT = 6;

    //Lighttrail settings
    public static int MAX_LIGHT_TRAIL_COVERAGE = 3;
    public static int LIGHT_TRAIL_LIFETIME = 3;

    //Player settings
    public static final int PLAYER_ACTIONS_EACH_TURN = 3;
}
