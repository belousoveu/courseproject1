import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Scanner;

public class MenuEmployee {
    int levelMenu = 0;
    final String[] MENU_TITLES = {"Главное меню:", "Операции:", "Аналитика:", "Отчеты:", "Настройки:"};
    final String[][] MENU_ITEMS = {
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
                    "1. Сведения по заработной плате по оганизации",
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
                    "4. Сведения о сотрудниках с заработной платой не меньше заданной",
                    "5. Сведения о сотруднике по его идентификатору",
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
        if (levelMenu == 0) {
            levelMenu = choiceMenu;
            this.showMenu();
        } else if (choiceMenu == 0) {
            levelMenu = 0;
            this.showMenu();
        } else {
            System.out.println(MENU_ITEMS[levelMenu][choiceMenu - 1]);
            switch (levelMenu) {
                case 1: {
                    switch (choiceMenu) {
                        case 1:
                            inputEmployeeData(empBook);
                            pressEnterToContinue();
                            break;
                        case 2:
                            EditByEmployeeId(empBook);
                            pressEnterToContinue();
                            break;
                        case 3:
                            DeleteByEmployeeID(empBook);
                            pressEnterToContinue();
                            break;
                        case 4:
                            indexingSalary(empBook);
                            pressEnterToContinue();
                            break;
                        case 5:
                            indexingSalaryInDepartment(empBook);
                            pressEnterToContinue();
                    }
                    break;
                }
                case 2:
                case 3: {
                    switch (choiceMenu) {
                        case 1:
                            empBook.displayFullData();
                            pressEnterToContinue();
                            break;
                    }
                    break;
                }
                case 4: {
                    setup();
                    break;
                }
            }
        }
    }

    private void setup() {
    }


    // Методы обработки Меню "Операции"
    //
    private void inputEmployeeData(@NotNull EmployeeBook empBook) {
        if (empBook.hasVacancies()) {
            System.out.println("Добавление нового сотрудника\n");
            System.out.print("Полное имя сотрудника: ");
            String fullName = new Scanner(System.in).nextLine();
            System.out.print("Возраст: ");
            int age = new Scanner(System.in).nextInt();
            System.out.print("Идентификатор отдела: ");
            byte depId = new Scanner(System.in).nextByte();
            if (!validateDepartmentId(depId)) {
                System.out.println("Операция не выполнена. Введен некорректный Id отдела.");
                return;
            }
            System.out.print("Заработная плата: ");
            int salary = new Scanner(System.in).nextInt();
            Employee emp = new Employee(fullName, age, depId, salary);
            empBook.addNewEmployee(emp);
            System.out.println("Принят новый сотрудник: " + emp.getShortName());
        } else {
            System.out.println("Остутствуют свободные вакансии");
        }
    }

    private void EditByEmployeeId(EmployeeBook empBook) {
        System.out.println("Редактирование данных сотрудника\n");
        System.out.print("Введите идентификатор сотрудника: ");
        int empId = new Scanner(System.in).nextInt();
        Employee person = empBook.searchById(empId);
        if (person != null) {
            String formatString = "%-25s%-35s";
            System.out.printf(formatString + "Новые данные\n", "ID=" + person.getId(), "Текущие данные");
            System.out.printf(formatString, "Полное имя:", person.getFullName());
            String fullName = new Scanner(System.in).nextLine();
            fullName = fullName.isEmpty() ? person.getFullName() : fullName;
            System.out.printf(formatString, "Возраст   :", person.getAge());
            int age = new Scanner(System.in).nextInt();
            age = (age == 0) ? person.getAge() : age;
            System.out.printf(formatString, "Идентификатор отдела:", person.getDepartmentId());
            byte depId = new Scanner(System.in).nextByte();
            depId = (depId == 0) ? person.getDepartmentId() : depId;
            if (!validateDepartmentId(depId)) {
                System.out.println("Операция не выполнена. Введен некорректный Id отдела.");
                return;
            }
            System.out.printf(formatString, "Заработная плата:", person.getSalary());
            int salary = new Scanner(System.in).nextInt();
            salary = (salary == 0) ? person.getSalary() : salary;
            person.setProperties(fullName, age, depId, salary);
        } else {
            System.out.printf("Сотрудник с ID=%d отсутсвует в базе", empId);
        }
    }

    private void DeleteByEmployeeID(@NotNull EmployeeBook empBook) {
        if (empBook.hasEmployee()) {
            System.out.println("Увольнение сотрудника\n");
            System.out.print("Введите идентификатор сотрудника: ");
            int empId = new Scanner(System.in).nextInt();
            Employee person = empBook.searchById(empId);
            if (person != null) {
                System.out.printf("Уволен сотрудник: %s\n", person.getShortName());
                empBook.deleteEmployee(empId);
            } else {
                System.out.printf("Сотрудник с ID=%d отсутсвует в базе", empId);
            }
        }
    }

    private void indexingSalary(EmployeeBook empBook) {
        System.out.println("Индексация заработной платы по организации \n");
        System.out.print("Введите процент индексации заработной платы: ");
        double percentOfIndexing = new Scanner(System.in).nextDouble();
        empBook.IndexingSalary(percentOfIndexing);
        System.out.println("Расчитана индексация заработной платы в целом по организации");
    }

    private void indexingSalaryInDepartment(EmployeeBook empBook) {
        System.out.println("Индексация заработной платы по оотделу \n");
        System.out.print("Укажите идентификатор отдела: ");
        byte depId = new Scanner(System.in).nextByte();
        if (validateDepartmentId(depId)) {
            System.out.print("Введите процент индексации заработной платы: ");
            double percentOfIndexing = new Scanner(System.in).nextDouble();
            empBook.IndexingSalary(percentOfIndexing, depId);
            System.out.printf("Расчитана индексация заработной платы по отделу ID=%d",depId);
        } else {
            System.out.println("Операция прервана. Указан некорректный Id отдела");
        }

    }

    // Вспомогательные и проверочные методы

    private static void pressEnterToContinue() throws IOException {
        System.out.print("\nPress Enter to continue...");
        while ((char) System.in.read() != '\n') ;
    }

    private boolean validateDepartmentId(byte depId) {
        return (depId >= 1 && depId <= 3);
    }
}
