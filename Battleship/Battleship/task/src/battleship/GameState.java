package battleship;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static battleship.UtilityFunctions.printMessage;

/**
 * Created by Brian Smith on 11/6/21.
 * Description: Hold all active game parameters during play.
 */
public class GameState {

    private ShipType[][] gameBoard;
    private boolean playerAircraftCarrierSet = false;
    private boolean playerBattleshipSet = false;
    private boolean playerSubmarineSet = false;
    private boolean playerCruiserSet = false;
    private boolean playerDestroyerSet = false;

    GameState() {
        gameBoard = new ShipType[10][10];

        for (ShipType[] row : gameBoard) {
            Arrays.fill(row, ShipType.NONE);
        }
    }

    public void printBoard() {
        List<String> rowLetters = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J");

        //printMessage("  1 2 3 4 5 6 7 8 9 10");
        System.out.format("%22s%n", "1 2 3 4 5 6 7 8 9 10");


        // print row content in a grid
        for (int i = 0; i < 10; i++) {
            StringBuilder rowStr = new StringBuilder(rowLetters.get(i) + " ");

            for (int j = 0; j < 10; j++) {
                rowStr.append((gameBoard[i][j] == ShipType.NONE) ? "~ " : "O ");
            }

            printMessage(rowStr.toString());
        }

        UtilityFunctions.printNewline();
    }

    public void setPlayerShips() {
        Scanner scanner = new Scanner(System.in);

        while (!playerAircraftCarrierSet) {
            printMessage("Enter the coordinates of the Aircraft Carrier (5 cells):");
            String[] inputs = scanner.next().split(" ");


        }
    }

    public void enterShipLocation(String firstGridSpace, String endGridSpace) {
        
    }
}
