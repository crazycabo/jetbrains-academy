package battleship;

import battleship.gameboard.BattleshipGrid;
import battleship.gameboard.BattleshipInstance;
import battleship.gameboard.Cell;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DrawEngine {
    private final BattleshipInstance BATTLESHIP;
    private final String BATTLEFIELD_HEADER;

    public DrawEngine(BattleshipInstance instance) {
        BATTLESHIP = instance;
        BATTLEFIELD_HEADER = IntStream.
                rangeClosed(1, 10)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(" "));
    }

    public String drawFogOfWarScreen() {
        return drawBattleField(drawFogOfWarRows());
    }

    public String drawPlayerScreen() {
        var activeBattlefield = BATTLESHIP.getActiveBattlefield();

        return drawBattleField(drawBattleshipRows(activeBattlefield));
    }

    public String drawGameScreen() {
        return String.format("%s%s%n%s%n",drawFogOfWarScreen(), drawBorder(), drawPlayerScreen());
    }

    private String drawFogOfWarRows() {
        return IntStream.rangeClosed(0, 9)
                .mapToObj(this::drawFogOfWarRow)
                .collect(Collectors.joining("\n"));
    }

    private String drawFogOfWarRow(int numRow) {
        return drawRow(numRow, " ~".repeat(10));
    }

    private String drawRow(int numRow, String cellsRow) {
        return String.format("%c %s", 'A' + numRow, cellsRow);
    }

    private String drawBattleField(String battleFieldRows) {
        return String.format("  %s%n%s%n", BATTLEFIELD_HEADER, battleFieldRows);
    }

    private String drawBattleshipRows(BattleshipGrid activeBattleField) {
        return IntStream.rangeClosed(0, 9)
                .mapToObj(numRow -> drawBattleshipRow(numRow, activeBattleField))
                .collect(Collectors.joining("\n"));
    }

    private String drawBattleshipRow(int numRow, BattleshipGrid activeBattleField) {
        String battleshipFieldRow = activeBattleField.rowCells(numRow)
                .map(Cell::toString)
                .collect(Collectors.joining(" "));

        return drawRow(numRow, battleshipFieldRow);
    }

    private static String drawBorder() {
        return "----------------------";
    }
}
