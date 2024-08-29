package view;

import manager.ContactManager;
import model.Contact;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class UserMenu {
    private ContactManager contactManager;
    private Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public UserMenu(ContactManager contactManager) {
        this.contactManager = contactManager;
    }

    public void showMenu() {
        while (true) {
            System.out.println("---- The Contact Menu ---- \n");
            System.out.println("1. View list of contacts");
            System.out.println("2. Add new contact");
            System.out.println("3. Update an existing contact");
            System.out.println("4. Delete an existing contact");
            System.out.println("5. Search an existing contact");
            System.out.println("6. Read from file");
            System.out.println("7. Save to file");
            System.out.println("8. Exit \n");
            System.out.print("-- Choose an option: -- \n ==>  ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewContacts();
                    break;
                case 2:
                    addNewContact();
                    break;
                case 3:
                    updateContact();
                    break;
                case 4:
                    deleteContact();
                    break;
                case 5:
                    searchContacts();
                    break;
                case 6:
                    readFromFile();
                    break;
                case 7:
                    saveToFile();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewContacts() {
        contactManager.viewContacts();
    }

    private void addNewContact() {
        System.out.println("--- Add new contact interface ---");
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter group: ");
        String group = scanner.nextLine();
        System.out.print("Enter birthdate (yyyy-MM-dd): ");
        String birthdateInput = scanner.nextLine();
        System.out.print("Enter gender (Male/Female/Other): ");
        String gender = scanner.nextLine();

        if (isValidContact(name, phone, email, birthdateInput, gender)) {
            LocalDate birthdate = LocalDate.parse(birthdateInput, DATE_FORMATTER);
            Contact contact = new Contact(name, phone, address, email, group, birthdate, gender);
            contactManager.addContact(contact);
            System.out.println("-- Contact added successfully. --\n");
        }
    }

    private void updateContact() {
        System.out.println("--- Update contact interface ---");
        System.out.print("Enter the phone number of the contact to update: ");
        String phone = scanner.nextLine();
        Contact contact = contactManager.searchContactByPhone(phone);

        if (contact != null) {
            System.out.println("Leave field blank to keep current value.");
            updateContactDetails(contact);
            contactManager.updateContact(phone, contact);
            System.out.println("-- Contact updated successfully. --\n");
        } else {
            System.out.println("-- Contact not found. --\n");
        }
    }

    private void updateContactDetails(Contact contact) {
        System.out.println("--- Contact details update--- \n (leave blank to keep current)\n");
        System.out.print("Enter new name: ");
        String input = scanner.nextLine();
        if (!input.isBlank()) {
            contact.setName(input);
        }

        System.out.print("Enter new address : ");
        input = scanner.nextLine();
        if (!input.isBlank()) {
            contact.setAddress(input);
        }

        System.out.print("Enter new email: ");
        input = scanner.nextLine();
        if (!input.isBlank()) {
            contact.setEmail(input);
        }

        System.out.print("Enter new group: ");
        input = scanner.nextLine();
        if (!input.isBlank()) {
            contact.setGroup(input);
        }

        System.out.print("Enter new birthdate (yyyy-MM-dd) : ");
        input = scanner.nextLine();
        if (!input.isBlank()) {
            try {
                contact.setBirthdate(LocalDate.parse(input, DATE_FORMATTER));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Birthdate not updated.\n");
            }
        }

        System.out.print("Enter new gender (or leave blank to keep current): ");
        input = scanner.nextLine();
        if (!input.isBlank()) {
            contact.setGender(input);
        }
        System.out.println("-- Contact details updated successfully. --\n");
    }

    private void deleteContact() {
        System.out.println("--- Delete contact interface ---");
        System.out.print("Enter the phone number of the contact to delete: ");
        String phone = scanner.nextLine();
        contactManager.deleteContact(phone);
        System.out.println("-- Contact deleted --\n");
    }

    private void searchContacts() {
        System.out.println("--- Search contact interface ---");
        System.out.print("Enter search keyword (phone or name): ");
        String keyword = scanner.nextLine();
        List<Contact> results = contactManager.searchContacts(keyword);

        if (results.isEmpty()) {
            System.out.println("No contacts found.\n");
        } else {
            results.forEach(System.out::println);
        }
    }

    private void readFromFile() {
        System.out.println("--- Read from file ---");
        System.out.print("Are you sure you want to reload contacts from file? This will replace all current contacts. (yes/no): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            contactManager.reloadContacts();
            System.out.println("Contacts reloaded from file.\n");
        } else {
            System.out.println("Operation canceled.\n");
        }
    }

    private void saveToFile() {
        System.out.println("--- Save to file ---");
        System.out.print("Are you sure you want to save contacts to file? This will overwrite the current file. (yes/no): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            contactManager.saveContactsToFile();
            System.out.println("Contacts saved to file.\n");
        } else {
            System.out.println("Operation canceled.\n");
        }
    }

    private boolean isValidContact(String name, String phone, String email, String birthdateInput, String gender) {
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || birthdateInput.isEmpty() || gender.isEmpty()) {
            System.out.println("All fields are required.\n");
            return false;
        }

        if (!isValidPhoneNumber(phone)) {
            System.out.println("Invalid phone number format.\n");
            return false;
        }

        if (!isValidEmail(email)) {
            System.out.println("Invalid email format.\n");
            return false;
        }

        if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female") && !gender.equalsIgnoreCase("Other")) {
            System.out.println("Invalid gender. Use 'Male', 'Female', or 'Other'.");
            return false;
        }

        return true;
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\d{10}");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
}