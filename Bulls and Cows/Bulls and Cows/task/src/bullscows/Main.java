package bullscows;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static String secretCode;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (scanner.hasNext()) {
            generateNewCode(scanner.nextInt());
        }
    }

    private static void processUserCode(String code) {
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

        if (bullCount + cowCount == 0) {
            printMessage("Grade: None. The secret code is " + secretCode);
        } else {
            printMessage("Grade: " +
                    ((bullCount > 0) ? bullCount + " bull(s)" : "") +
                    ((cowCount > 0) ? cowCount + " cow(s)" : "") +
                    ". The secret code is " + secretCode);
        }
    }

    private static void generateNewCode(int length) {
        if (length > 10) {
            printMessage("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.");
            return;
        }

        List<Integer> allNums = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = (int) Math.floor(Math.random() * (allNums.size() - 1));
            int randomNum = allNums.remove(randomIndex == 0 && i == 0 ? randomIndex + 1 : randomIndex);

            code.append(randomNum);
        }

        printMessage("The random secret number is " + code);
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }
}
