package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<String> knownTypes = Arrays.asList(
            "BUZZ",
            "DUCK",
            "PALINDROMIC",
            "GAPFUL",
            "SPY",
            "SQUARE",
            "SUNNY",
            "JUMPING",
            "HAPPY",
            "SAD",
            "EVEN",
            "ODD"
    );

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean loopCheck = true;

        outputMessage("Welcome to Amazing Numbers!\n");

        outputMessage("Supported requests:");
        outputMessage("- enter a natural number to know its properties.");
        outputMessage("- enter two natural numbers to obtain the properties of the list:");
        outputMessage("  * the first parameter represents a starting number.");
        outputMessage("  * the second parameters show how many consecutive numbers are to be processed.");
        outputMessage("- two natural numbers and properties to search for.");
        outputMessage("- a property preceded by minus must not be present in numbers.");
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
                        String[] types = Arrays.copyOfRange(inputs, 2, inputs.length);
                        List<String> validTypes = new ArrayList<>();
                        List<String> wrongTypes = new ArrayList<>();

                        for (String t : types) {
                            if (!checkType(t)) {
                                wrongTypes.add(t);
                            } else {
                                validTypes.add(t);
                            }
                        }

                        if (wrongTypes.size() > 0) {
                            if (wrongTypes.size() > 1) {
                                outputMessage("The properties of " + wrongTypes + " are wrong.");
                            } else {
                                outputMessage("The property of " + wrongTypes + " is wrong.");
                            }

                            outputMessage("Available properties: " + knownTypes.toString());
                            continue;
                        }

                        if (checkMutuallyExclusive(validTypes)) {
                            outputMessage("The request contains mutually exclusive properties: []");
                            outputMessage("There are no numbers with these properties.");
                            continue;
                        }

                        while (loopCount > 0) {
                            if (verifyAllTypes(validTypes, num)) {
                                outputProperties(true, num);
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
                                outputProperties(true, num);
                                loopCount--;
                            }

                            num++;
                        }
                    }
                } else {
                    for (int i = 0; i < count; i++) {
                        outputProperties(true, num+i);
                    }
                }
            } else {
                outputProperties(false, num);
            }

            outputMessage(""); // Create a new line after every request.
        }
    }

    private static boolean checkType(String type) {
        return knownTypes.contains(type.toUpperCase().replaceFirst("-", ""));
    }

    private static boolean checkMutuallyExclusive(List<String> types) {
        List<String> sanitizedTypes = new ArrayList<>();

        // Todo: Check if a property exists with its direct opposite. Return true if so.

        for (String type : types) {
            sanitizedTypes.add(type.replaceFirst("-", ""));
        }

        boolean evenOdd = sanitizedTypes.contains("even") && sanitizedTypes.contains("odd");
        boolean duckSpy = sanitizedTypes.contains("duck") && sanitizedTypes.contains("spy");
        boolean sunnySquare = sanitizedTypes.contains("sunny") && sanitizedTypes.contains("square");
        boolean happySad = sanitizedTypes.contains("happy") && sanitizedTypes.contains("sad");

        return evenOdd || duckSpy || sunnySquare || happySad;
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

    private static boolean verifyJumping(long number) {
        String[] numberStrings = String.valueOf(number).split("");

        if (numberStrings.length == 1) {
            return true;
        }

        for (int i = 0; i < numberStrings.length - 1; i++) {
            int num1 = Integer.parseInt(numberStrings[i]);
            int num2 = Integer.parseInt(numberStrings[i + 1]);

            if (num1 + 1 != num2 && num1 - 1 != num2) {
                return false;
            }
        }

        return true;
    }

    private static boolean verifyHappy(long number) {
        if (number == 1) {
            return true;
        }

        long currentNum = number;
        int count = 0;

        while (count <= 1000) {
            String[] numberStrings = String.valueOf(currentNum).split("");

            int total = 0;
            for (String numStr : numberStrings) {
                int num = Integer.parseInt(numStr);

                total += (long) num * num;
            }

            currentNum = total;
            count++;

            if (currentNum == 1) {
                return true;
            }
        }

        return false;
    }

    private static boolean verifyType(String type, long number) throws Exception {
        boolean isNegative = false;

        if (type.startsWith("-")) {
            type = type.replaceFirst("-", "");
            isNegative = true;
        }

        boolean validation;

        switch (type.toUpperCase()) {
            case "ODD":
                validation = !verifyEven(number);
                break;
            case "EVEN":
                validation = verifyEven(number);
                break;
            case "BUZZ":
                validation = verifyBuzz(number);
                break;
            case "DUCK":
                validation = verifyDuck(number);
                break;
            case "PALINDROMIC":
                validation = verifyPalindromic(number);
                break;
            case "GAPFUL":
                validation = verifyGapful(number);
                break;
            case "SPY":
                validation = verifySpy(number);
                break;
            case "SQUARE":
                validation = verifySquare(number);
                break;
            case "SUNNY":
                validation = verifySunny(number);
                break;
            case "JUMPING":
                validation = verifyJumping(number);
                break;
            case "HAPPY":
                validation = verifyHappy(number);
                break;
            case "SAD":
                validation = !verifyHappy(number);
                break;
            default:
                throw new Exception("No valid type entered for verification.");
        }

        return isNegative != validation;
    }

    private static boolean verifyAllTypes(List<String> types, long number) throws Exception {
        for (String type : types) {
            if (!verifyType(type, number)) {
                return false;
            }
        }

        return true;
    }

    private static void outputProperties(boolean isSimple, long number) {
        if (isSimple) {
            List<String> types = new ArrayList<>();
            StringBuilder message = new StringBuilder(number + " is ");

            types.add((verifyEven(number)) ? "even" : "odd");

            if (verifyBuzz(number)) {
                types.add("buzz");
            }

            if (verifyDuck(number)) {
                types.add("duck");
            }

            if (verifyPalindromic(number)) {
                types.add("palindromic");
            }

            if (verifyGapful(number)) {
                types.add("gapful");
            }

            if (verifySpy(number)) {
                types.add("spy");
            }

            if (verifySquare(number)) {
                types.add("square");
            }

            if (verifySunny(number)) {
                types.add("sunny");
            }

            if (verifyJumping(number)) {
                types.add("jumping");
            }

            if (verifyHappy(number)) {
                types.add("happy");
            }

            if (!verifyHappy(number)) {
                types.add("sad");
            }

            outputMessage(message.append(String.join(", ", types)).toString());
        } else {
            outputMessage("\nProperties of " + number);
            outputMessage("        buzz: " + verifyBuzz(number));
            outputMessage("        duck: " + verifyDuck(number));
            outputMessage(" palindromic: " + verifyPalindromic(number));
            outputMessage("      gapful: " + verifyGapful(number));
            outputMessage("         spy: " + verifySpy(number));
            outputMessage("      square: " + verifySquare(number));
            outputMessage("       sunny: " + verifySunny(number));
            outputMessage("     jumping: " + verifyJumping(number));
            outputMessage("       happy: " + verifyHappy(number));
            outputMessage("         sad: " + !verifyHappy(number));
            outputMessage("        even: " + verifyEven(number));
            outputMessage("         odd: " + !verifyEven(number));
        }
    }

    private static void outputMessage(String message) {
        System.out.println(message);
    }
}
