package battleship;

public class BattleshipInstance {
    private final static int ROWS = 10;
    private static final int MAX_BOATS = 5;
    private final BattleshipGrid PLAYER_GRID;
    private final BattleshipGrid AUTO_GRID;

    public BattleshipInstance() {
        PLAYER_GRID = new BattleshipGrid(ROWS, MAX_BOATS);
        AUTO_GRID = new BattleshipGrid(ROWS, MAX_BOATS);
    }
}
