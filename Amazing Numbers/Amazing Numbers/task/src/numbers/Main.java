package numbers;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean loopCheck = true;

        outputMessage("Welcome to Amazing Numbers!\n");

        outputMessage("Supported requests:");
        outputMessage("- enter a natural number to know its properties.");
        outputMessage("- enter two natural numbers to obtain the properties of the list:");
        outputMessage("  * the first parameter represents a starting number.");
        outputMessage("  * the second parameters show how many consecutive numbers are to be processed.");
        outputMessage("- separate the parameters with one space.");
        outputMessage("- enter 0 to exit.\n");

        while (loopCheck) {
            outputMessage("Enter a request: ");
            long num = scanner.nextLong();

            if (num == 0) {
                outputMessage("Goodbye!");
                loopCheck = false;
            } else if (verifyNatural(num)) {
                outputMessage("The first parameter should be a natural number or zero.");
            } else {
                outputProperties(num, verifyEven(num), verifyBuzz(num), verifyDuck(num), verifyPalindromic(num), verifyGapful(num));
            }
        }
    }

    private static boolean verifyNatural(long number) {
        return number <= 0;
    }

    private static boolean verifyEven(long number) {
        return number % 2 == 0;
    }

    private static boolean verifyBuzz(long number) {
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

    private static boolean verifyDuck(long number) {
        String numStr = String.valueOf(number);

        return numStr.lastIndexOf("0") > 0;
    }

    private static boolean verifyPalindromic(long number) {
        String numStr = String.valueOf(number);

        return numStr.equals(new StringBuilder(numStr).reverse().toString());
    }

    private static boolean verifyGapful(long number) {
        String numStr = String.valueOf(number);

        if (numStr.length() < 3) {
            return false; // Number is too short
        }

        String firstChar = Character.toString(numStr.charAt(0));
        String lastChar = Character.toString(numStr.charAt(numStr.length() - 1));

        long divisor = Integer.parseInt(firstChar + lastChar);

        return number % divisor == 0;
    }

    private static void outputProperties(long number, boolean isEven, boolean isBuzz, boolean isDuck, boolean isPalindromic, boolean isGapful) {
        outputMessage("\nProperties of " + number);
        outputMessage("        buzz: " + isBuzz);
        outputMessage("        duck: " + isDuck);
        outputMessage(" palindromic: " + isPalindromic);
        outputMessage("      gapful: " + isGapful);
        outputMessage("        even: " + isEven);
        outputMessage("         odd: " + !isEven + "\n");
    }

    private static void outputMessage(String message) {
        System.out.println(message);
    }
}
