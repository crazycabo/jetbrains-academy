package battleship;

/**
 * Created by Brian Smith on 11/6/21.
 * Description:
 */
public enum ShipType {
    AIRCRAFT_CARRIER (5),
    BATTLESHIP (4),
    SUBMARINE (3),
    CRUISER (3),
    DESTROYER (2),
    NONE (0);

    private final int cellCount;

    ShipType(int cellCount) {
        this.cellCount = cellCount;
    }

    public int getCellCount() {
        return this.cellCount;
    }
}
