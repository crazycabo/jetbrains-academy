package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<String> knownTypes = Arrays.asList("BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY", "EVEN", "ODD");

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean loopCheck = true;

        outputMessage("Welcome to Amazing Numbers!\n");

        outputMessage("Supported requests:");
        outputMessage("- enter a natural number to know its properties.");
        outputMessage("- enter two natural numbers to obtain the properties of the list:");
        outputMessage("  * the first parameter represents a starting number.");
        outputMessage("  * the second parameters show how many consecutive numbers are to be processed.");
        outputMessage("- two natural numbers and a property to search for.");
        outputMessage("- two natural numbers and two properties to search for.");
        outputMessage("- separate the parameters with one space.");
        outputMessage("- enter 0 to exit.\n");

        while (loopCheck) {
            outputMessage("Enter a request: ");
            String[] inputs = scanner.nextLine().split(" ");
            outputMessage("");

            long num = Long.parseLong(inputs[0]);

            if (num == 0) {
                outputMessage("Goodbye!");
                loopCheck = false;
            }

            if (verifyNatural(num)) {
                outputMessage("The first parameter should be a natural number or zero.");
                continue;
            }

            int count;

            if (inputs.length > 1) {
                count = Integer.parseInt(inputs[1]);

                if (verifyNatural(count)) {
                    outputMessage("The second parameter should be a natural number.");
                    continue;
                }

                if (inputs.length > 2) {
                    int loopCount = count;
                    String type = inputs[2];

                    if (inputs.length > 3) {
                        String type2 = inputs[3];

                        boolean type1Valid = !checkType(type);
                        boolean type2Valid = !checkType(type2);

                        if (type1Valid || type2Valid) {
                            if (type1Valid && type2Valid) {
                                outputMessage("The properties of [" + type.toUpperCase() + ", " + type2.toUpperCase() + "] are wrong.");
                            } else {
                                String invalidType = (type1Valid) ? type : type2;
                                outputMessage("The property of [" + invalidType + "] is wrong.");
                            }

                            outputMessage("Available properties: " + knownTypes.toString());
                            continue;
                        }

                        if (checkMutuallyExclusive(List.of(type, type2))) {
                            outputMessage("The request contains mutually exclusive properties: []");
                            outputMessage("There are no numbers with these properties.");
                            continue;
                        }

                        while (loopCount > 0) {
                            if (verifyType(type, num) && verifyType(type2, num)) {
                                outputProperties(true, num, verifyEven(num), verifyBuzz(num), verifyDuck(num), verifyPalindromic(num), verifyGapful(num), verifySpy(num), verifySquare(num), verifySunny(num));
                                loopCount--;
                            }

                            num++;
                        }
                    } else {
                        if (!checkType(type)) {
                            outputMessage("The property of [" + type.toUpperCase() + "] is wrong.");
                            outputMessage("Available properties: " + knownTypes.toString());
                            continue;
                        }

                        while (loopCount > 0) {
                            if (verifyType(type, num)) {
                                outputProperties(true, num, verifyEven(num), verifyBuzz(num), verifyDuck(num), verifyPalindromic(num), verifyGapful(num), verifySpy(num), verifySquare(num), verifySunny(num));
                                loopCount--;
                            }

                            num++;
                        }
                    }
                } else {
                    for (int i = 0; i < count; i++) {
                        outputProperties(true, num+i, verifyEven(num+i), verifyBuzz(num+i), verifyDuck(num+i), verifyPalindromic(num+i), verifyGapful(num+i), verifySpy(num+i), verifySquare(num+i), verifySunny(num+i));
                    }
                }
            } else {
                outputProperties(false, num, verifyEven(num), verifyBuzz(num), verifyDuck(num), verifyPalindromic(num), verifyGapful(num), verifySpy(num), verifySquare(num), verifySunny(num));
            }

            outputMessage(""); // Create a new line after every request.
        }
    }

    private static boolean checkType(String type) {
        return knownTypes.contains(type.toUpperCase());
    }

    private static boolean checkMutuallyExclusive(List<String> types) {
        return (types.contains("even") && types.contains("odd")) || (types.contains("duck") && types.contains("spy")) || (types.contains("sunny") && types.contains("square"));
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

    private static boolean verifySpy(long number) {
        String[] stringNums = String.valueOf(number).split("");
        long sumOfNums = 0;
        long productOfNums = Long.parseLong(stringNums[0]);

        for (String s : stringNums) {
            sumOfNums += Long.parseLong(s);
        }

        for (int i = 1; i < stringNums.length; i++) {
            productOfNums *= Long.parseLong(stringNums[i]);
        }

        return sumOfNums == productOfNums;
    }

    private static boolean verifySquare(long number) {
        double s = Math.sqrt((double) number);

        return s == Math.floor(s);
    }

    private static boolean verifySunny(long number) {
        return verifySquare(number + 1);
    }

    private static boolean verifyType(String type, long number) throws Exception {
        switch (type.toUpperCase()) {
            case "ODD":
                return !verifyEven(number);
            case "EVEN":
                return verifyEven(number);
            case "BUZZ":
                return verifyBuzz(number);
            case "DUCK":
                return verifyDuck(number);
            case "PALINDROMIC":
                return verifyPalindromic(number);
            case "GAPFUL":
                return verifyGapful(number);
            case "SPY":
                return verifySpy(number);
            case "SQUARE":
                return verifySquare(number);
            case "SUNNY":
                return verifySunny(number);
            default:
                throw new Exception("No valid type entered for verification.");
        }
    }

    private static void outputProperties(boolean isSimple, long number, boolean isEven, boolean isBuzz, boolean isDuck, boolean isPalindromic, boolean isGapful, boolean isSpy, boolean isSquare, boolean isSunny) {
        if (isSimple) {
            List<String> types = new ArrayList<>();
            StringBuilder message = new StringBuilder(number + " is ");

            types.add((isEven) ? "even" : "odd");

            if (isBuzz) {
                types.add("buzz");
            }

            if (isDuck) {
                types.add("duck");
            }

            if (isPalindromic) {
                types.add("palindromic");
            }

            if (isGapful) {
                types.add("gapful");
            }

            if (isSpy) {
                types.add("spy");
            }

            if (isSquare) {
                types.add("square");
            }

            if (isSunny) {
                types.add("sunny");
            }

            outputMessage(message.append(String.join(", ", types)).toString());
        } else {
            outputMessage("\nProperties of " + number);
            outputMessage("        buzz: " + isBuzz);
            outputMessage("        duck: " + isDuck);
            outputMessage(" palindromic: " + isPalindromic);
            outputMessage("      gapful: " + isGapful);
            outputMessage("         spy: " + isSpy);
            outputMessage("      square: " + isSquare);
            outputMessage("       sunny: " + isSunny);
            outputMessage("        even: " + isEven);
            outputMessage("         odd: " + !isEven);
        }
    }

    private static void outputMessage(String message) {
        System.out.println(message);
    }
}
