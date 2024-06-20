
import java.io.IOException;

public class MenuEmployee {
    int levelMenu = 0;
    final private String[] MENU_TITLES = {"Главное меню:", "Операции:", "Аналитика:", "Отчеты:", "Настройки:"};
    final private String[][] MENU_ITEMS = {
            {
                    "1. Операции",
                    "2. Аналитика",
                    "3. Отчеты",
                    "4. Настройки",
                    "0. Выход"},
            {
                    "1. Прием нового сотрудника",
                    "2. Редактировать данные сотрудника",
                    "3. Увольнение сотрудника",
                    "4. Индексация заработной платы по организации",
                    "5. Индексация заработной платы по отделу",
                    "0. Возврат в главное меню"},
            {
                    "1. Сведения по заработной плате по организации",
                    "2. Сведения по заработной плате в разрезе отделов",
                    "3. Сведения о сотруднике с максимальной заработной платой в организации",
                    "4. Сведения о сотруднике с максимальной заработной платой в отделе",
                    "5. Сведения о сотруднике с минимальной заработной платой в организации",
                    "6. Сведения о сотруднике с минимальной заработной платой в отделе",
                    "0. Возврат в главное меню"},
            {
                    "1. Сведения о сотрудниках организации",
                    "2. Список сотрудников организации",
                    "3. Сведения о сотрудниках отдела",
                    "4. Сведения о сотрудниках с заработной платой меньше заданной",
                    "5. Сведения о сотрудниках с заработной платой не меньше заданной",
                    "6. Сведения о сотруднике по его идентификатору",
                    "0. Возврат в главное меню"},
            {
                    "1. Сведения об организации",
                    "0. Возврат в главное меню"}
    };

    void showMenu() {
        System.out.println(MENU_TITLES[levelMenu]);
        for (int i = 0; i < MENU_ITEMS[levelMenu].length; i++) {
            System.out.println(MENU_ITEMS[levelMenu][i]);
        }
    }

    public boolean validate(byte choiceMenu) {
        return choiceMenu >= 0 && choiceMenu < MENU_ITEMS[levelMenu].length;
    }

    public boolean exit(byte choiceMenu) {
        return levelMenu == 0 && choiceMenu == 0;
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


        if (levelMenu == 0) {
            levelMenu = choiceMenu;
//            this.showMenu();
        } else if (choiceMenu == 0) {
            levelMenu = 0;
//            this.showMenu();
        } else {
//            System.out.println(MENU_ITEMS[levelMenu][choiceMenu - 1]);
            showTitle(choiceMenu);
            switch (levelMenu) {
                case 1:
                    switch (choiceMenu) {
                        case 1: //"1. Прием нового сотрудника"
                            if (empBook.hasVacancies()) {
                                secondName = Input.secondName();
                                firstName = Input.firstName();
                                surname = Input.surName();
                                age = Input.age();
                                depId = Input.departmentId();
                                salary = Input.salary();
                                empBook.addNewEmployee(secondName, firstName, surname, age, depId, salary);
                                Display.string("Добавлен новый сотрудник");
                            } else {
                                Display.string("Отсутствуют свободные вакансии");
                            }
                            pressEnterToContinue();
                            break;
                        case 2: //"2. Редактировать данные сотрудника"
                            empId = Input.employeeId();
                            if (Input.editEmployeeData(empBook, empId)) {
                                Display.stringf("Данные сотрудника с ID=%d обновлены", empId);
                            } else {
                                Display.stringf("Сотрудник с ID=%d отсутсвует в базе", empId);
                            }
                            pressEnterToContinue();
                            break;
                        case 3: //"3. Увольнение сотрудника"
                            empId = Input.employeeId();
                            Display.dismissalOfEmployee(empBook, empId);
                            pressEnterToContinue();
                            break;
                        case 4: //"4. Индексация заработной платы по организации"
                            percentOfIndexing = Input.percentOfIndexing();
                            empBook.IndexingSalary(percentOfIndexing);
                            Display.string("Расчитана индексация заработной платы в целом по организации");
                            pressEnterToContinue();
                            break;
                        case 5: //"5. Индексация заработной платы по отделу"
                            percentOfIndexing = Input.percentOfIndexing();
                            depId = Input.departmentId();
                            empBook.IndexingSalary(percentOfIndexing, depId);
                            Display.stringf("Расчитана индексация заработной платы по отделу ID=%d", depId);
                            pressEnterToContinue();
                    }
                    break;
                case 2:
                    switch (choiceMenu) {
                        case 1: //"1. Сведения по заработной плате по организации"
                            Display.salaryInformation(empBook);
                            pressEnterToContinue();
                            break;
                        case 2: //"2. Сведения по заработной плате в разрезе отделов"
                            depId = Input.departmentId();
                            Display.salaryInformation(empBook, depId);
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
        Display.showTitle(MENU_TITLES[levelMenu], MENU_ITEMS[levelMenu][choiceMenu - 1]);
    }

    private static void pressEnterToContinue() throws IOException {
        System.out.print("\nPress Enter to continue...");
        while ((char) System.in.read() != '\n') ;
    }

}
