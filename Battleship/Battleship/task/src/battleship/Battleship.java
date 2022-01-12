package battleship;

import battleship.enums.GameState;
import battleship.enums.Ship;
import battleship.gameboard.BattleshipInstance;

import java.util.Scanner;

public class Battleship {
    private final BattleshipInstance BATTLESHIP = new BattleshipInstance();
    private final DrawEngine DRAW_ENGINE = new DrawEngine(BATTLESHIP);
    private final Scanner INPUT_SCANNER = new Scanner(System.in);

    public void run() {
        var newState = BATTLESHIP.getNewState();

        do {
            switch (newState) {
                case P2_SETUP:
                    runEnterDialog();
                case P1_SETUP:
                    runPlayerSetup();
                    newState = BATTLESHIP.getNewState();

                    break;
                case P1_SHELLS:
                case P2_SHELLS:
                    runEnterDialog();
                    runPlayerShell();

                    break;
                default:
                    break;
            }
        } while (newState != GameState.END);
    }

    private void runEnterDialog() {
        System.out.println("Press Enter and pass the move to another player");
        INPUT_SCANNER.nextLine();
    }

    private void runPlayerSetup() {
        System.out.println(BATTLESHIP.getActivePlayer() + ", place your ships on the game field");
        System.out.println(DRAW_ENGINE.drawFogOfWarScreen());
        for (var ship : BATTLESHIP.getBattleShips()) {
            printShipMessage(ship);
            runAddShipToTheBattleField(ship);
            System.out.println(DRAW_ENGINE.drawPlayerScreen());
        }
    }

    private void runPlayerShell() {
        System.out.println(DRAW_ENGINE.drawGameScreen());
        System.out.println(BATTLESHIP.getActivePlayer() + ", it's your turn:");
        while (true) {
            var coordinates = INPUT_SCANNER.nextLine();
            try {
                var shellOutcome = BATTLESHIP.shell(coordinates).toString();
                if (BATTLESHIP.getNewState() == GameState.END) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                } else {
                    System.out.println(shellOutcome);
                }
                break;
            } catch (Exception ex) {
                System.out.printf("%n%s Try again%n%n", ex.getMessage());
            }
        }
    }

    private void printShipMessage(Ship ship) {
        System.out.printf("Enter the coordinates of the %s:%n", ship);
    }

    private void runAddShipToTheBattleField(Ship ship) {
        while (true) {
            var coordinates = INPUT_SCANNER.nextLine().split(" ");
            try {
                BATTLESHIP.addShipToTheBattleField(coordinates[0], coordinates[1], ship);
                break;
            } catch (Exception ex) {
                System.out.printf("%n%s Try again%n%n", ex.getMessage());
            }
        }
    }
}
