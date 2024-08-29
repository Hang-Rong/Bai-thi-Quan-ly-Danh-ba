package manager;

import data.ReadAndWrite;
import model.Contact;

import java.util.List;
import java.util.Scanner;

public class ContactManager {
    private List<Contact> contacts;
    private final ReadAndWrite readAndWrite;
    private static final Scanner scanner = new Scanner(System.in);

    public ContactManager(ReadAndWrite readAndWrite) {
        this.readAndWrite = readAndWrite;
        this.contacts = readAndWrite.readFromFile();
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            contacts.forEach(System.out::println);
        }
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        saveContactsToFile();
    }

    public void updateContact(String phone, Contact updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getPhone().equals(phone)) {
                contacts.set(i, updatedContact);
                saveContactsToFile();
                return;
            }
        }
        System.out.println("Contact number not found.");
    }

    public void deleteContact(String phone) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getPhone().equals(phone)) {
                contacts.remove(i);
                saveContactsToFile();
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    public Contact searchContactByPhone(String phone) {
        for (Contact contact : contacts) {
            if (contact.getPhone().equals(phone)) {
                return contact;
            }
        }
        return null;
    }

    public List<Contact> searchContacts(String keyword) {
        return contacts.stream()
                .filter(contact -> contact.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        contact.getPhone().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    public void reloadContacts() {
        contacts = readAndWrite.readFromFile();
    }

    // New method to save contacts to file
    public void saveContactsToFile() {
        readAndWrite.writeToFile(contacts);
    }
}