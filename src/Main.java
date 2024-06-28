import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
        final String URL = "jdbc:mysql://localhost:3306/"+DbEmployees.DB_NAME+"?createDatabaseIfNotExist=true" +
                "&serverTimezone=Europe/Moscow";
        DbEmployees dbEmp = new DbEmployees(URL, Const.USER, Const.PASSWORD);
        dbEmp.onStart(Const.NUMBER_OF_EMPLOYEES);

        EmployeeBook empBook = new EmployeeBook(Const.NUMBER_OF_EMPLOYEES);
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