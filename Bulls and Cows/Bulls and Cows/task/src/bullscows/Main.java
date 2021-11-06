package bullscows;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static String secretCode;
    static boolean continueLoop = true;
    static int loopCount = 0;
    static int length;
    static int symbolCount;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            printMessage("Input the length of the secret code:");
            length = scanner.nextInt();

            printMessage("Input the number of possible symbols in the code:");
            symbolCount = scanner.nextInt();
        } catch (InputMismatchException e) {
            printMessage("Error: length or symbol count input must be a number.");
            return;
        }

        // todo: verify valid numbers
        if (length <= 0 || symbolCount <= 0) {
            printMessage("Error: length and symbol count cannot equal zero or less.");
            return;
        }

        if (length > symbolCount) {
            printMessage("Error: it's not possible to generate a code with a length of " +
                    length + " with " + symbolCount + " unique symbols.");
            return;
        }

        if (length > 36 || symbolCount > 36) {
            printMessage("Error: maximum length or number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        secretCode = generateNewCode(length, symbolCount);

        printMessage("Okay, let's start a game!");

        while (continueLoop) {
            loopCount++;

            if (scanner.hasNext()) {
                printMessage("Turn " + loopCount + ":");
                continueLoop = processUserCode(scanner.next());
            }
        }
    }

    private static boolean processUserCode(String code) {
        int bullCount = 0;
        int cowCount = 0;
        List<Integer> inputCowDigits = new ArrayList<>();
        List<Integer> secretCodeCowDigits = new ArrayList<>();

        for (int i = 0; i < code.length(); i++) {
            if (secretCode.charAt(i) == code.charAt(i)) {
                bullCount++;
            } else {
                inputCowDigits.add(Character.getNumericValue(code.charAt(i)));
                secretCodeCowDigits.add(Character.getNumericValue(secretCode.charAt(i)));
            }
        }

        for (Integer digit : inputCowDigits) {
            if (secretCodeCowDigits.contains(digit)) {
                cowCount++;
                secretCodeCowDigits.remove(digit);
            }
        }

        if (bullCount == code.length()) {
            printMessage("Grade: " + code.length() + " bulls");
            printMessage("Congratulations! The secret code is " + secretCode + ".");
            return false;
        } else if (bullCount + cowCount == 0) {
            printMessage("Grade: None.");
            return true;
        } else {
            printMessage(
                    "Grade: " +
                    ((bullCount > 0) ? bullCount + " bull(s)" : "") +
                    ((cowCount > 0) ? cowCount + " cow(s)" : "")
            );
            return true;
        }
    }

    private static String generateNewCode(int length, int symbolCount) {
        List<Character> staticCharList = new ArrayList<>(List.of(
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

        List<Character> mutableCharList = new ArrayList<>(List.copyOf(staticCharList));
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = (int) Math.floor(Math.random() * (symbolCount - 1));
            int randomChar = mutableCharList.remove(randomIndex);

            code.append(Character.toString(randomChar));
        }

        StringBuilder message = new StringBuilder("The secret is prepared: ");
        message.append("*".repeat(Math.max(0, code.length())));

        // todo: append the range of characters appearing in the secret
        if (length <= 10) {
            message.append(" (0-").append(staticCharList.get(symbolCount - 1)).append(")");
        } else {
            message.append(" (0-9, a-").append(staticCharList.get(symbolCount - 1)).append(")");
        }

        printMessage(message.toString());

        return code.toString();
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }
}
