import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
        final int NUMBER_OF_EMPLOYEES = 10;
        EmployeeBook empBook = new EmployeeBook(NUMBER_OF_EMPLOYEES);
        empBook.readFromFile();
        byte choiceMenu;
        Scanner scMenu = new Scanner(System.in);
        MenuEmployee menuObj = new MenuEmployee();

        while (true) {
            do {
                menuObj.showMenu();
                choiceMenu = scMenu.nextByte();

            } while (!menuObj.validate(choiceMenu));
            if (menuObj.exit(choiceMenu)) {
                break;
            }
            menuObj.executeChoice(choiceMenu, empBook);
        }
        scMenu.close();
        empBook.saveToFile();
    }


}