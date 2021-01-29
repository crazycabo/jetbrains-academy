package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    static Scanner scanner;
    static double balance = 0;
    static ArrayList<Item> purchases = new ArrayList<>();
    static SortedMap<Integer, String> purchaseTypes = new TreeMap<>();
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        
        purchaseTypes.put(1, "Food");
        purchaseTypes.put(2, "Clothes");
        purchaseTypes.put(3, "Entertainment");
        purchaseTypes.put(4, "Other");

        printMenu();

        while (scanner.hasNextInt()) {
            
            switch(scanner.nextInt()) {
                case 1:
                    addIncome();
                    break;
                case 2:
                    addPurchase();
                    break;
                case 3:
                    listPurchases();
                    break;
                case 4:
                    showBalance();
                    break;
                case 5:
                    saveData();
                    break;
                case 6:
                    loadData();
                    break;
                case 7:
                    analyzePurchases();
                    break;
                default:
                    System.out.println("\nBye!");
                    System.exit(0);
            }

            System.out.println("");
            
            printMenu();
        }
    }
    
    private static void printMenu() {
        System.out.println("Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "7) Analyze (Sort)\n" +
                "0) Exit");
    }
    
    private static void addIncome() {
        System.out.println("\nEnter Income:");

        int incomeToAdd = scanner.nextInt();
        balance += incomeToAdd;
        
        System.out.println("Income was added!\n");
    }

    private static void addPurchase() {
        int purchaseTypeId = choosePurchaseTypeMenu(0);
        
        while (purchaseTypeId != 5) {
            System.out.println("\nEnter purchase name:");

            scanner.nextLine();
            String itemName = scanner.nextLine();

            System.out.println("Enter its price:");
            double itemPrice = scanner.nextDouble();

            purchases.add(new Item(itemName, purchaseTypeId, itemPrice));
            balance -= itemPrice;

            System.out.println("Purchase was added!");
            
            purchaseTypeId = choosePurchaseTypeMenu(0);
        }
        
        System.out.println("");
    }
    
    protected static int choosePurchaseTypeMenu(int listType) {
        String message = (listType == 0) ? "Choose the type of purchase:" : "Choose the type of purchases:";
        System.out.println("\n" + message);
        
        purchaseTypes.forEach((id, type) -> System.out.println(id + ") " + type));
        
        if (listType == 1 && purchases.size() > 0) {
            System.out.println("5) All\n6) Back");
        } else {
            System.out.println("5) Back");
        }
        
        return scanner.nextInt();
    }

    private static void listPurchases() {
        int purchaseTypeId = choosePurchaseTypeMenu(1);
        
        while (purchaseTypeId != 6) {
            Double sum = 0.0;

            System.out.println("");
            
            ArrayList<Item> printedPurchases = new ArrayList<>();
            
            if (purchaseTypeId == 5) {
                printedPurchases = purchases;
            } else {
                for (Item item : purchases) {
                    if (item.purchaseTypeId == purchaseTypeId) {
                        printedPurchases.add(item);
                    }
                }
            }

            for (Item item : printedPurchases) {
                System.out.printf("%s $%.2f\n", item.name, item.cost);
                sum += item.cost;
            }

            if (printedPurchases.size() > 0) {
                System.out.printf("Total sum: $%.2f\n", sum);
            } else {
                System.out.println("Purchase list is empty");
            }
            
            purchaseTypeId = choosePurchaseTypeMenu(1);
        }
    }

    private static void showBalance() {
        System.out.printf("\nBalance: $%.2f\n", balance);
    }

    private static void saveData() {
        File file = new File("purchases.txt");

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(balance);

            for (Item item : purchases) {
                writer.println(item.name + "," + item.purchaseTypeId + "," + item.cost);
            }

            System.out.println("\nPurchases were saved!");
        } catch (FileNotFoundException e) {
            System.out.println("A file write error occurred.");
        }
    }

    private static void loadData() {
        File file = new File("purchases.txt");

        try (Scanner scanner = new Scanner(file)) {
            balance = Double.parseDouble(scanner.nextLine());
            purchases = new ArrayList<>();

            while (scanner.hasNext()) {
                String[] itemProperties = scanner.nextLine().split(",");

                Item item = new Item(itemProperties[0], Integer.parseInt(itemProperties[1]), Double.parseDouble(itemProperties[2]));

                purchases.add(item);
            }

            System.out.println("\nPurchases were loaded!");
        } catch (FileNotFoundException e) {
            System.out.println("A file read error occurred.");
        }
    }

    private static int printAnalyePurchasesMenu() {
        System.out.println("\nHow do you want to sort?\n" +
                "1) Sort all purchases\n" +
                "2) Sort by type\n" +
                "3) Sort certain type\n" +
                "4) back");

        return scanner.nextInt();
    }

    private static void analyzePurchases() {
        int choice = printAnalyePurchasesMenu();

        while (choice != 4) {
            PurchaseSorter sorter = new PurchaseSorter();

            switch (choice) {
                case 1:
                    sorter.setStrategy(new TopExpenseSort());
                    sorter.sort(purchases);
                    break;
                case 2:
                    sorter.setStrategy(new AllTypesSort(purchaseTypes));
                    sorter.sort(purchases);
                    break;
                case 3:
                    sorter.setStrategy(new TypeSort(purchaseTypes));
                    sorter.sort(purchases);
                    break;
                default:
                    // Return to main menu
            }

            choice = printAnalyePurchasesMenu();
        }
    }
}

class Item {
    String name;
    int purchaseTypeId;
    Double cost;

    Item (String name, int purchaseTypeId, Double cost) {
        this.name = name;
        this.purchaseTypeId = purchaseTypeId;
        this.cost = cost;
    }

    public Double getCost() {
        return cost;
    }

    public int getPurchaseTypeId() {
        return purchaseTypeId;
    }
}

interface PurchaseSortStrategy {
    void sort(ArrayList<Item> purchases);
}

class PurchaseSorter {
    private PurchaseSortStrategy strategy;

    public void setStrategy(PurchaseSortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort(ArrayList<Item> purchases) {
        this.strategy.sort(purchases);
    }
}

class TopExpenseSort implements PurchaseSortStrategy {

    @Override
    @SuppressWarnings("unchecked")
    public void sort(ArrayList<Item> purchases) {

        if (purchases.size() > 0) {
            ArrayList<Item> sortedPurchases = (ArrayList<Item>) purchases.clone();

            sortedPurchases.sort(Comparator.comparing(Item::getCost).reversed());

            System.out.println("\nAll:");

            Double totalCost = 0.0;
            for (Item item : sortedPurchases) {
                totalCost += item.cost;
                System.out.printf("%s $%.2f\n", item.name, item.cost);
            }

            System.out.printf("Total: $%.2f\n", totalCost);

        } else {
            System.out.println("\nThe purchase list is empty!");
        }
    }
}

class AllTypesSort implements PurchaseSortStrategy {

    private final SortedMap<Integer, String> purchaseTypes;

    AllTypesSort(SortedMap<Integer, String> purchaseTypes) {
        this.purchaseTypes = purchaseTypes;
    }

    @Override
    public void sort(ArrayList<Item> purchases) {

        Map<Integer, Double> purchaseTypeTotals = new TreeMap<>();
        LinkedHashMap<Integer, Double> sortedPurchaseTypeTotals = new LinkedHashMap<>();

        purchaseTypes.forEach((key, name) -> purchaseTypeTotals.put(key, 0.0));

        for (Item item : purchases) {

            Double currentTotal = purchaseTypeTotals.getOrDefault(item.purchaseTypeId, 0.0);
            purchaseTypeTotals.replace(item.purchaseTypeId, currentTotal + item.cost);
        }

        // Sort map by value
        purchaseTypeTotals
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedPurchaseTypeTotals.put(x.getKey(), x.getValue()));

        System.out.println("\nTypes:");

        sortedPurchaseTypeTotals.forEach((key, value)-> System.out.printf(purchaseTypes.get(key) + " - $%.2f\n", value));

        AtomicReference<Double> totalCost = new AtomicReference<>(0.0);
        sortedPurchaseTypeTotals.forEach((key, cost)-> totalCost.updateAndGet(v -> v + cost));

        System.out.printf("Total sum: $%.2f\n", totalCost.getPlain());
    }
}

class TypeSort implements PurchaseSortStrategy {

    private final SortedMap<Integer, String> purchaseTypes;

    TypeSort(SortedMap<Integer, String> purchaseTypes) {
        this.purchaseTypes = purchaseTypes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(ArrayList<Item> purchases) {

        int choice = Main.choosePurchaseTypeMenu(0);

        if (purchases.size() > 0) {

            ArrayList<Item> sortedPurchases = (ArrayList<Item>) purchases.clone();
            sortedPurchases.removeIf(item -> item.purchaseTypeId != choice);

            sortedPurchases.sort(Comparator.comparing(Item::getCost).reversed());

            System.out.println("\n" + purchaseTypes.get(choice) + ":");

            Double totalCost = 0.0;
            for (Item item : sortedPurchases) {
                totalCost += item.cost;
                System.out.printf("%s $%.2f\n", item.name, item.cost);
            }

            System.out.printf("Total sum: $%.2f\n", totalCost);

        } else {
            System.out.println("\nThe purchase list is empty!");
        }
    }
}