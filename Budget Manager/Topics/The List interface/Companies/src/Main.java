import java.util.ArrayList;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> vals = new ArrayList<>();

        while (scanner.hasNext()) {
            vals.add(scanner.next());
        }

        System.out.println(vals);
    }
}