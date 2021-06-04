package numbers;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        outputMessage("Enter a natural number:");

        int num = scanner.nextInt();

        if (verifyNatural(num)) {
            outputMessage("This number is not natural!");
        } else {
            outputProperties(num, verifyEven(num), verifyBuzz(num), verifyDuck(num));
        }
    }

    private static boolean verifyNatural(int number) {
        return number <= 0;
    }

    private static boolean verifyEven(int number) {
        return number % 2 == 0;
    }

    private static boolean verifyBuzz(int number) {
        String numStr = String.valueOf(number);
        boolean isDivisibleBy7 = false;
        boolean endsWith7 = false;

        if (number % 7 == 0) {
            isDivisibleBy7 = true;
        }

        if (numStr.charAt(numStr.length() - 1) == '7') {
            endsWith7 = true;
        }

        return isDivisibleBy7 || endsWith7;
    }

    private static boolean verifyDuck(int number) {
        String numStr = String.valueOf(number);

        return numStr.lastIndexOf("0") > 0;
    }

    private static void outputProperties(int number, boolean isEven, boolean isBuzz, boolean isDuck) {
        outputMessage("Properties of " + number);
        outputMessage("        even: " + isEven);
        outputMessage("        odd: " + !isEven);
        outputMessage("        buzz: " + isBuzz);
        outputMessage("        duck: " + isDuck);

    }

    private static void outputMessage(String message) {
        System.out.println(message);
    }
}
