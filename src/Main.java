import data.ReadAndWrite;
import manager.ContactManager;
import view.UserMenu;

public class Main {
    public static void main(String[] args) {
        ReadAndWrite readAndWrite = new ReadAndWrite();
        ContactManager contactManager = new ContactManager(readAndWrite);
        UserMenu menu = new UserMenu(contactManager);
        menu.showMenu();
    }
}