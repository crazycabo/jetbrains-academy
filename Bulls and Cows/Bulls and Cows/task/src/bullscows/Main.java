package bullscows;

public class Main {
    public static void main(String[] args) {
        // Secret code is 0101
        printMessage("The secret code is prepared: ****.\n");

        printMessage("Turn 1. Answer:");
        printMessage("0000");
        printMessage("Grade: 2 cows.");

        printMessage("Turn 1. Answer:");
        printMessage("0101");
        printMessage("Grade: 4 bulls.");

        printMessage("Congrats! The secret code is 0101.");
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }
}
