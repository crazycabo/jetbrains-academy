package battleship;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class BattleshipInstance {
    private final static int ROWS = 10;
    private static final int MAX_BOATS = 5;
    private final BattleshipGrid PLAYER_GRID;
    private final BattleshipGrid AI_GRID;
    private GameState gameState = GameState.P1_SETUP;

    public BattleshipInstance() {
        PLAYER_GRID = new BattleshipGrid(ROWS, MAX_BOATS);
        AI_GRID = new BattleshipGrid(ROWS, MAX_BOATS);
    }

    public GameState getNewState() {
        switch (gameState){
            case P1_SETUP:
                if (PLAYER_GRID.isFull()) {
                    gameState = GameState.P2_SETUP;
                }

                break;
            case P2_SETUP:
                if (AI_GRID.isFull()) {
                    gameState = GameState.P1_SHELLS;
                }

                break;
            case P1_SHELLS:
                gameState = (isFinished()) ? GameState.END : GameState.P2_SHELLS;

                break;
            case P2_SHELLS:
                gameState = (isFinished()) ? GameState.END : GameState.P1_SHELLS;

                break;
            case END:
            default:
                break;
        }

        return gameState;
    }

    private boolean isFinished() {
        return Arrays.stream(getInactiveBattlefield().getClass().getDeclaredFields())
                .filter(field -> field.getName().equals("shipCount"))
                .mapToInt(this::getShipCount)
                .allMatch(shipCount -> shipCount == 0);
    }

    public BattleshipGrid getActiveBattlefield() {
        return (gameState == GameState.P1_SETUP || gameState == GameState.P1_SHELLS) ? PLAYER_GRID : AI_GRID;
    }

    private BattleshipGrid getInactiveBattlefield() {
        return (PLAYER_GRID == getActiveBattlefield()) ? AI_GRID : PLAYER_GRID;
    }

    private int getShipCount(Field field) {
        try {
            field.setAccessible(true);

            return field.getInt(getInactiveBattlefield());
        } catch (IllegalAccessException ignored) {
            return -1;
        }
    }

    public Ship[] getBattleShips() {
        return Ship.values();
    }

    public String getActivePlayer() {
        return (gameState == GameState.P1_SETUP || gameState == GameState.P1_SHELLS) ? "Player 1" : "Player 2";
    }

    public void addShipToTheBattleField(String beginPosition, String endPosition, Ship ship) throws Exception {
        var activeBattleField = getActiveBattlefield();
        var shipCoordinates = parseShipCoordinates(beginPosition, endPosition);

        if (activeBattleField.isFull()) {
            throw new Exception("Error! You cannot put more boats!");
        }

        if (activeBattleField.shipDoesNotFit(shipCoordinates[0], shipCoordinates[1], ship)) {
            throw new Exception(String.format("Error! Wrong length of the %s!", ship.getShipName()));
        }

        if (activeBattleField.isRangeCloseToAnotherShip(shipCoordinates[0], shipCoordinates[1])){
            throw new Exception("Error! You placed it too close to another one.");
        }

        activeBattleField.takeCellRange(shipCoordinates[0], shipCoordinates[1]);
    }

    private Cell[] parseShipCoordinates(String beginPosition, String endPosition) throws Exception {
        var beginCellOpt = parseCell(beginPosition, getActiveBattlefield());
        var endCellOpt = parseCell(endPosition, getActiveBattlefield());

        if (beginCellOpt.isEmpty() || endCellOpt.isEmpty() || getActiveBattlefield().cellRangeDoesNotExist(beginCellOpt.get(), endCellOpt.get())) {
            throw new Exception("Error! Wrong ship location!");
        }

        return new Cell[] {beginCellOpt.get(), endCellOpt.get()};
    }

    private Optional<Cell> parseCell(String position, BattleshipGrid battleshipField){
        int row;
        int column;
        Optional<Cell> result;

        try {
            row = Character.toUpperCase(position.charAt(0)) - 'A';
            column = Integer.parseInt(position.substring(1)) - 1;

            result = battleshipField.findCell(row, column);
        }catch (NumberFormatException | NullPointerException exception ) {
            result = Optional.empty();
        }

        return result;
    }

    public ShellState shell(String position) throws Exception {
        var cellPosition = parseCell(position, getInactiveBattlefield());

        if (cellPosition.isEmpty()) {
            throw new  Exception("Error! You entered the wrong coordinates!");
        }

        return getShotOutcome(cellPosition.get());
    }

    private ShellState getShotOutcome(Cell target) {
        ShellState outcome;
        var shot = target.shot();

        if (shot && getInactiveBattlefield().shipSunk(target)) {
            outcome = ShellState.SUNK;
        } else if (shot) {
            outcome = ShellState.HIT;
        } else {
            outcome = ShellState.MISSED;
        }

        return  outcome;
    }
}
