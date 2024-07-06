import java.io.IOException;
import java.util.Scanner;

public class TwoLevelMenu {
    private int level;
    private int choice;
    private final String[] titles;
    private final String[][] items;

    public TwoLevelMenu(String[] titles, String[][] items) {
        this.titles = titles;
        this.items = items;
    }

    public int getLevel() {
        return level;
    }

    public int getChoice() {
        return choice;
    }

    public String[] getTitles() {
        return titles;
    }

    public String[][] getItems() {
        return items;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public boolean runMenu() {
        showMenu();
        choice = getChoiceMenu();
        if (choice == 0 && level == 0) {
            return false;
        }
        if (choice == 0) {
            level = 0;
            return runMenu();
        }
        if (level == 0) {
            level = choice;
            return runMenu();
        }
        return true;
    }

    private int getChoiceMenu() {
        System.out.print("Выберете пункт меню: ");
        Scanner scMenu = new Scanner(System.in);
        if (scMenu.hasNextByte()) {
            byte choiceMenu = scMenu.nextByte();
            if (validate(choiceMenu)) {
                return choiceMenu;
            }
        }
        System.out.println("Некорректный выбор");
        return getChoiceMenu();
    }


    private void showMenu() {
        System.out.println(titles[level]);
        for (int i = 0; i < items[level].length; i++) {
            System.out.println(items[level][i]);
        }
    }

    private boolean validate(byte choiceMenu) {
        return choiceMenu >= 0 && choiceMenu < items[level].length;
    }


    public void executeChoice(byte choiceMenu, EmployeeBook empBook) throws IOException {
        int empId;
        String firstName;
        String secondName;
        String surname;
        int age;
        int salary;
        byte depId;
        double percentOfIndexing;


        if (level == 0) {
            level = choiceMenu;
//            this.showMenu();
        } else if (choiceMenu == 0) {
            level = 0;
//            this.showMenu();
        } else {
//            System.out.println(MENU_ITEMS[levelMenu][choiceMenu - 1]);
            showTitle(choiceMenu);
            switch (level) {
                case 1:
//                    switch (choiceMenu) {
//                        case 1: //"1. Прием нового сотрудника"
//                            if (empBook.hasVacancies()) {
//                                secondName = Input.secondName();
//                                firstName = Input.firstName();
//                                surname = Input.middleName();
//                                age = Input.age();
//                                depId = Input.departmentId();
//                                salary = Input.salary();
//                                empBook.addNewEmployee(secondName, firstName, surname, age, depId, salary);
//                                Display.message("Добавлен новый сотрудник");
//                            } else {
//                                Display.message("Отсутствуют свободные вакансии");
//                            }
//                            pressEnterToContinue();
//                            break;
//                        case 2: //"2. Редактировать данные сотрудника"
//                            empId = Input.employeeId();
//                            if (Input.editEmployeeData(empBook, empId)) {
//                                Display.formatMessage("Данные сотрудника с ID=%d обновлены", empId);
//                            } else {
//                                Display.formatMessage("Сотрудник с ID=%d отсутсвует в базе", empId);
//                            }
//                            pressEnterToContinue();
//                            break;
//                        case 3: //"3. Увольнение сотрудника"
//                            empId = Input.employeeId();
//                            Display.dismissalOfEmployee(empBook, empId);
//                            pressEnterToContinue();
//                            break;
//                        case 4: //"4. Индексация заработной платы по организации"
//                            percentOfIndexing = Input.percentOfIndexing();
//                            empBook.IndexingSalary(percentOfIndexing);
//                            Display.message("Расчитана индексация заработной платы в целом по организации");
//                            pressEnterToContinue();
//                            break;
//                        case 5: //"5. Индексация заработной платы по отделу"
//                            percentOfIndexing = Input.percentOfIndexing();
//                            depId = Input.departmentId();
//                            empBook.IndexingSalary(percentOfIndexing, depId);
//                            Display.formatMessage("Расчитана индексация заработной платы по отделу ID=%d", depId);
//                            pressEnterToContinue();
//                    }
                    break;
                case 2:
                    switch (choiceMenu) {
                        case 1: //"1. Сведения по заработной плате по организации"
//                            Display.salaryInformation(empBook);
                            pressEnterToContinue();
                            break;
                        case 2: //"2. Сведения по заработной плате в разрезе отделов"
                            depId = Input.departmentId();
//                            Display.salaryInformation(empBook, depId);
                            pressEnterToContinue();
                            break;
                        case 3: //"3. Сведения о сотруднике с максимальной заработной платой в организации"
                            Display.employeeInformation(empBook, 1);
                            pressEnterToContinue();
                            break;
                        case 4: //"4. Сведения о сотруднике с максимальной заработной платой в отделе"
                            depId = Input.departmentId();
                            Display.employeeInformation(empBook, depId, 1);
                            pressEnterToContinue();
                            break;
                        case 5: //"5. Сведения о сотруднике с минимальной заработной платой в организации"
                            Display.employeeInformation(empBook, 0);
                            pressEnterToContinue();
                            break;
                        case 6: //"6. Сведения о сотруднике с минимальной заработной платой в отделе"
                            depId = Input.departmentId();
                            Display.employeeInformation(empBook, depId, 0);
                            pressEnterToContinue();
                            break;
                    }
                    break;
                case 3:
                    switch (choiceMenu) {
                        case 1: //"1. Сведения о сотрудниках организации"
                            Display.showFullDataOfEmployees(empBook);
                            pressEnterToContinue();
                            break;
                        case 2: //"2. Список сотрудников организации"
                            Display.showListOfEmployees(empBook);
                            pressEnterToContinue();
                            break;
                        case 3: //"3. Сведения о сотрудниках отдела"
                            depId = Input.departmentId();
                            Display.showFullDataOfEmployees(empBook, depId);
                            pressEnterToContinue();
                            break;
                        case 4: //"4. Сведения о сотрудниках с заработной платой меньше заданной"
                            salary = Input.salary();
                            Display.showFullDataOfEmployees(empBook, salary, false);
                            pressEnterToContinue();
                            break;
                        case 5: //"5. Сведения о сотрудниках с заработной платой не меньше заданной"
                            salary = Input.salary();
                            Display.showFullDataOfEmployees(empBook, salary, true);
                            pressEnterToContinue();
                            break;
                        case 6: //"6. Сведения о сотруднике по его идентификатору"
                            empId = Input.employeeId();
                            Display.ShowEmployeeData(empBook, empId);
                            pressEnterToContinue();
                            break;
                    }
                    break;
                case 4:
                    break;
            }
        }
    }

    // Вспомогательные и проверочные методы
    private void showTitle(int choiceMenu) {
        Display.showTitle(Const.MAIN_MENU_TITLES[level], Const.MAIN_MENU_ITEMS[level][choiceMenu - 1]);
    }

    private static void pressEnterToContinue() throws IOException {
        System.out.print("\nPress Enter to continue...");
        while ((char) System.in.read() != '\n') ;
    }

}
