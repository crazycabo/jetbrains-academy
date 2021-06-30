package contacts;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Contact contact = new Contact();

        outputMessage("Enter the name of the person:");
        contact.setName(scanner.next());

        outputMessage("Enter the surname of the person:");
        contact.setSurname(scanner.next());

        outputMessage("Enter the number:");
        contact.setPhoneNumber(scanner.next());

        outputMessage("A record created!");
        outputMessage("A phone book with a single record created!");

        ContactsApp contactsApp = new ContactsApp();
        contactsApp.setContact(contact);
    }

    static private void outputMessage(String message) {
        System.out.println(message);
    }
}
