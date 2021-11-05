package bullscows;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static String secretCode = generateNewCode();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (scanner.hasNext()) {
            processUserCode(scanner.next());
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

    private static String generateNewCode() {
        double d = Math.floor(Math.random() * (9999 - 1000) + 1);
        return String.valueOf((int) d);
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }
}
