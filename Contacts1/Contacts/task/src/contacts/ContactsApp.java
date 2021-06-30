package contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian Smith on 6/30/21.
 * Description:
 */
public class ContactsApp {
    List<Contact> contacts = new ArrayList<>();

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContact(Contact contact) {
        this.contacts.add(contact);
    }
}
