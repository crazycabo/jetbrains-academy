package bullscows;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static String secretCode;
    static boolean continueLoop = true;
    static int loopCount = 0;

    public static void main(String[] args) {

        printMessage("Please, enter the secret code's length:");

        Scanner scanner = new Scanner(System.in);

        int length = scanner.nextInt();
        while (length > 10) {
            printMessage("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.");
            length = scanner.nextInt();
        }

        secretCode = generateNewCode(length);

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

    private static String generateNewCode(int length) {
        List<Integer> allNums = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = (int) Math.floor(Math.random() * (allNums.size() - 1));
            int randomNum = allNums.remove(randomIndex == 0 && i == 0 ? randomIndex + 1 : randomIndex);

            code.append(randomNum);
        }

        return code.toString();
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }
}
