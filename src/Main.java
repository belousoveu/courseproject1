import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
        final int NUMBER_OF_EMPLOYEES = 10;
        final String URL = "jdbc:mysql://localhost:3306/db_employees?createDatabaseIfNotExist=true" +
                "&serverTimezone=Europe/Moscow";
        final String USER = "root";
        final String PASSWORD = "root";
        DbEmployees dbEmp = new DbEmployees(URL, USER, PASSWORD);
        dbEmp.onStart(NUMBER_OF_EMPLOYEES);

        EmployeeBook empBook = new EmployeeBook(NUMBER_OF_EMPLOYEES);
        empBook.readFromFile();
        byte choiceMenu;
        Scanner scMenu = new Scanner(System.in);
        MenuEmployee menuObj = new MenuEmployee();

        while (true) {

            do {
                menuObj.showMenu();
                if (scMenu.hasNextByte()) {
                    choiceMenu = scMenu.nextByte();
                    if (menuObj.validate(choiceMenu)) {
                        break;
                    }
                } else {
                    scMenu.next();
                }
            } while (true);

            if (menuObj.exit(choiceMenu)) {
                break;
            }
            menuObj.executeChoice(choiceMenu, empBook);

        }
        scMenu.close();
        empBook.saveToFile();
    }


}