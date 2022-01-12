package battleship.gameboard;

import battleship.enums.Ship;
import battleship.models.ShipFleet;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BattleshipGrid {
    private final Cell[][] BOARD;
    private int shipCount = 0;
    private final int MAX_SHIPS;
    private final ShipFleet shipFleet;

    public BattleshipGrid(int rows, int maxShipCount) {
        this.BOARD = new Cell[rows][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                this.BOARD[i][j] = new Cell(i, j);
            }
        }

        MAX_SHIPS = maxShipCount;

        shipFleet = new ShipFleet(this);
    }

    public int getSize() {
        return BOARD.length;
    }

    public Optional<Cell> findCell(int row, int column) {
        try {
            return Optional.of(BOARD[row][column]);
        } catch (IndexOutOfBoundsException ex) {
            return Optional.empty();
        }
    }

    public void takeCellRange(Cell beginCell, Cell endCell) {
        cellRange(beginCell, endCell).forEach(Cell::setOccupied);

        shipFleet.addNewShip(beginCell, endCell);

        shipCount++;
    }

    public Stream<Cell> rowCells(int numRow) {
        return Arrays.stream(BOARD[numRow]);
    }

    public boolean isFull() {
        return this.shipCount == MAX_SHIPS;
    }

    public boolean cellRangeDoesNotExist(Cell beginCell, Cell endCell) {
        return cellRange(beginCell, endCell).findAny().isEmpty();
    }

    public boolean shipDoesNotFit(Cell beginCell, Cell endCell, Ship ship) {
        return cellRange(beginCell, endCell).count() != ship.getSize();
    }

    public boolean isRangeCloseToAnotherShip(Cell beginCell, Cell endCell) {
        return isRangeOccupied(beginCell, endCell) || anyAdjacentCellOccupied(beginCell, endCell);
    }

    private boolean anyAdjacentCellOccupied(Cell beginCell, Cell endCell) {
        return adjacentCellsToRange(beginCell, endCell).anyMatch(Predicate.not(Cell::isFree));
    }

    private Stream<Cell> adjacentCellsToRange(Cell beginCell, Cell endCell) {
        return Arrays.stream(BOARD).flatMap(Arrays::stream)
                .filter(cell -> cellRange(beginCell, endCell).anyMatch(cell::isAdjacent));
    }

    private boolean isRangeOccupied(Cell beginCell, Cell endCell) {
        return cellRange(beginCell, endCell).anyMatch(Predicate.not(Cell::isFree));
    }

    private Stream<Cell> cellRange(Cell beginCell, Cell endCell) {
        var sortedCells = sortCellsIterator(beginCell, endCell);

        beginCell = sortedCells.next();
        endCell = sortedCells.next();

        return (beginCell.isAtSameColumn(endCell)) ? cellsColumnRange(beginCell, endCell) :
                (beginCell.isAtSameRow(endCell)) ? cellsRowRange(beginCell, endCell) : Stream.empty();
    }

    private Stream<Cell> cellsColumnRange(Cell beginCell, Cell endCell) {
        return Arrays.stream(BOARD).map(row -> row[beginCell.getColumn()])
                .filter( cell -> cell.compareTo(beginCell) >= 0 && cell.compareTo(endCell) <= 0);
    }

    private Stream<Cell> cellsRowRange(Cell beginCell, Cell endCell) {
        return Arrays.stream(BOARD[beginCell.getRow()])
                .filter( cell -> cell.compareTo(beginCell) >= 0 && cell.compareTo(endCell) <= 0);
    }

    private Iterator<Cell> sortCellsIterator(Cell beginCell, Cell endCell) {
        return Arrays.stream(new Cell[]{beginCell, endCell}).sorted().iterator();
    }

    public boolean shipSunk(Cell target) {
        Ship targetedShip = shipFleet.getTargetedShip(target);
        var shipSunk = targetedShip == null;

        if (!shipSunk) {
            Cell stern = shipFleet.getStern(targetedShip);
            Cell bow = shipFleet.getBow(targetedShip);

            shipSunk = cellRange(stern, bow).allMatch(Cell::isHit);

            if (shipSunk) {
                removeShip(targetedShip);
            }

        }
        return shipSunk;
    }

    private void removeShip(Ship targetedShip) {
        for (var field : shipFleet.getClass().getDeclaredFields()) {
            removeShipFromNavy(field, targetedShip);
        }
    }

    private void removeShipFromNavy(Field field, Ship targetedShip) {
        if (field.getName().contains("ships")){
            try {
                field.setAccessible(true);

                var fieldClass = (Map<Ship, Cell>) field.get(shipFleet);

                fieldClass.remove(targetedShip);

                shipCount = fieldClass.size();
            } catch (IllegalAccessException ignored) {
                // Nothing to act on
            }
        }
    }
}
