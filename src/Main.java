import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        final int NUMBER_OF_EMPLOYEES = 10;

        //Инициализация массива сотрудников
        EmployeeBook empBook = new EmployeeBook(NUMBER_OF_EMPLOYEES);
        //Заполнение массива сотрудников сохраненными данными из файла
        empBook.readFromFile();

        //Подготовка главного меню
        String[] mainMenuTitles = createMenuTitles();
        String[][] maniMenuItems = createMenuItems();
        TwoLevelMenu mainMenu = new TwoLevelMenu(mainMenuTitles, maniMenuItems);

        while (true) {
            if (mainMenu.runMenu()) {
                eventChoiceMenu(mainMenu.getLevel(), mainMenu.getChoice(), empBook);
                pressEnterToContinue();
            } else {
                System.out.println("Выход из программы");
                break;
            }
        }
        //Сохранение массива сотрудников в текстовый файл
        empBook.saveToFile();
    }

    //Обработка выбора меню
    private static void eventChoiceMenu(int level, int choice, EmployeeBook empBook) {
        switch (level) {
            case 1: //Операции
                switch (choice) {
                    case 1: //Создание нового сотрудника
                        createEmployee(empBook);
                        break;
                    case 2: //Редактирование данных о сотруднике
                        editEmployee(empBook);
                        break;
                    case 3: //Удаление сотрудника
                        deleteEmployee(empBook);
                        break;
                    case 4: //Индексация заработной платы по организации
                        indexingSalary(empBook, 0);
                        break;
                    case 5: //Индексация заработной платы по отделу
                        indexingSalary(empBook, 1);
                }
                break;
            case 2: // Аналитика
                switch (choice) {
                    case 1: // Сведения о заработной плате в организации
                        salaryInformation(empBook, (byte) 0);
                        break;
                    case 2: // Сведения о заработной плате в разрезе отдела
                        salaryInformation(empBook, Input.departmentId());
                        break;
                    case 3: // Сведения о сотруднике с максимальной заработной платой в организации
                        employeeInformation(empBook, (byte) 0, 1);
                        break;
                    case 4:  // Сведения о сотруднике с максимальной заработной платой в отделе
                        employeeInformation(empBook, Input.departmentId(), 1);
                        break;
                    case 5:  // Сведения о сотруднике с минимальной заработной платой в организации
                        employeeInformation(empBook, (byte) 0, 0);
                        break;
                    case 6:  // Сведения о сотруднике с минимальной заработной платой в отделе
                        employeeInformation(empBook, Input.departmentId(), 0);
                }
                break;
            case 3: // Отчеты
                switch (choice) {
                    case 1: // Сведения о сотрудниках организации
                        generateEmployeeReport(empBook);
                        break;
                    case 2: // Список сотрудников организации
                        generateEmployeeList(empBook);
                        break;
                    case 3: // Сведения о сотрудниках отдела
                        generateEmployeeReportByDepartment(empBook);
                        break;
                    case 4: // Сведения о сотрудниках с заработной платой меньше заданной
                        generateEmployeeReportBySalary(empBook, 0);
                        break;
                    case 5: // Сведения о сотрудниках с заработной платой не меньше заданной
                        generateEmployeeReportBySalary(empBook, 1);
                        break;
                    case 6: // Сведения о сотруднике
                        reportEmployeeData(empBook);

                }

        }
    }


    // Методы обработки раздела "Операции"
    private static void createEmployee(EmployeeBook empBook) {
        if (empBook.hasVacancies()) {
            String secondName = Input.secondName();
            String firstName = Input.firstName();
            String middleName = Input.middleName();
            int age = Input.age();
            byte depId = Input.departmentId();
            int salary = Input.salary();
            Employee newEmployee = new Employee(secondName, firstName, middleName, age, depId, salary);
            empBook.addEmployee(newEmployee);
            Display.formatMessage("Принят новый сотрудник с ID=%d", newEmployee.getId());
        } else {
            Display.message("Отсутствуют свободные вакансии");
        }
    }

    private static void editEmployee(EmployeeBook empBook) {
        int empId = Input.employeeId();
        try {
            Employee selectedEmployee = empBook.getEmployeeById(empId);
            String secondName = Input.secondName(selectedEmployee.getSecondName());
            String firstName = Input.firstName(selectedEmployee.getFirstName());
            String surName = Input.middleName(selectedEmployee.getMiddleName());
            int age = Input.age(selectedEmployee.getAge());
            byte depId = Input.departmentId(selectedEmployee.getDepartmentId());
            int salary = Input.salary(selectedEmployee.getSalary());

            selectedEmployee.update(secondName, firstName, surName, age, depId, salary);
            Display.formatMessage("Данные сотрудника с ID=%d обновлены", empId);
        } catch (Exception e) {
            Display.message(e.getMessage());
        }
    }

    private static void deleteEmployee(EmployeeBook empBook) {
        int empId = Input.employeeId();
        try {
            Employee selectedEmployee = empBook.getEmployeeById(empId);
            Display.formatMessage("Сотрудник %s уволен", selectedEmployee.getShortName());
            empBook.deleteEmployee(empId);
        } catch (Exception e) {
            Display.message(e.getMessage());
        }
    }

    /**
     * @param mode - 0 - по организации, 1 - по отделу
     */
    private static void indexingSalary(EmployeeBook empBook, int mode) {
        byte depId = 0;
        String reportMessage = "Рассчитана индексация заработной платы в целом по организации";
        double percentOfIndexing = Input.percentOfIndexing();
        if (mode == 1) {
            depId = Input.departmentId();
            reportMessage = "Рассчитана индексация заработной платы по отделу ID=" + depId;
        }
        int numberOfEmployees = empBook.IndexingSalary(percentOfIndexing, depId);
        if (numberOfEmployees > 0) {
            Display.message(reportMessage);
            Display.formatMessage("Заработная плата проиндексирована %d %s", numberOfEmployees,
                    numberOfEmployees == 1 ? "сотруднику" : "сотрудникам");

        } else {
            Display.message("Индексация не проведена. Нет действующих сотрудников");
        }
    }

    // Методы обработки раздела "Аналитика"
    private static void salaryInformation(EmployeeBook empBook, byte deptID) {
        int[] salarySummary = empBook.getSalarySummary(deptID);
        if (deptID == 0) {
            Display.message("Сведения о заработной плате в организации:");
        } else {
            Display.message("Сведения о заработной плате в отделе ID=" + deptID + ":");
        }
        Display.reportSalarySummary(salarySummary);
    }

    /**
     * Информация о сотруднике
     *
     * @param empBook - массив сотрудников
     * @param depId   - Id отдела (0 - в целом по организации)
     * @param mode    - 0 - минимальная заработная плата, 1 - максимальная заработная плата
     */
    private static void employeeInformation(EmployeeBook empBook, byte depId, int mode) {
        Employee[] empArray = switch (mode) {
            case 0 -> empBook.getEmployeeWithMinSalary(depId);
            case 1 -> empBook.getEmployeeWithMaxSalary(depId);
            default -> throw new IllegalArgumentException("Invalid value of 'option'");
        };
        if (depId == 0) {
            Display.formatMessage("Сведения о сотруднике с %s заработной платой в организации:\n",
                    mode == 0 ? "минимальной" : "максимальной");
            Display.reportFullDataOfEmployees(empArray);
        } else {
            Display.formatMessage("Сведения о сотруднике с  %s заработной платой в отделе ID=%d:\n",
                    mode == 0 ? "минимальной" : "максимальной", depId);
            Display.reportFullDataOfEmployees(empArray, depId);
        }
    }

    // Методы обработки раздела "Отчеты"
    private static void generateEmployeeReport(EmployeeBook empBook) {
        Employee[] reportArray = empBook.getActualEmployees();
        if (reportArray.length > 0) {
            Display.message("Сведения о сотрудниках организации:");
            Display.reportFullDataOfEmployees(reportArray);
        } else {
            Display.message("Отсутствуют сотрудники в организации");
        }
    }

    private static void generateEmployeeList(EmployeeBook empBook) {
        Employee[] reportArray = empBook.getActualEmployees();
        if (reportArray.length > 0) {
            Display.message("Список сотрудников организации:");
            Display.reportShortDataOfEmployees(reportArray);
        } else {
            Display.message("Отсутствуют сотрудники в организации");
        }
    }

    /**
     * @param mode - 0 - выборка по з/п меньше заданной, 1 - по з/п больше или равной заданной
     */
    private static void generateEmployeeReportBySalary(EmployeeBook empBook, int mode) {
        int salary = Input.salary();
        Employee[] reportArray = empBook.getActualEmployees(salary, mode);
        if (reportArray.length > 0) {
            Display.message("Сведения о сотрудниках с заработной платой " + (mode == 0 ? "меньше" : "больше или равной") + " " + salary);
            Display.reportFullDataOfEmployees(reportArray);
        } else {
            Display.message("Отсутствуют сотрудники с заработной платой " + (mode == 0 ? "меньше" : "больше или равной") + " " + salary);
        }
    }

    private static void generateEmployeeReportByDepartment(EmployeeBook empBook) {
        byte depId = Input.departmentId();
        Employee[] reportArray = empBook.getActualEmployees(depId);
        if (reportArray.length > 0) {
            Display.message("Сведения о сотрудниках отдела ID=" + depId);
            Display.reportFullDataOfEmployees(reportArray, depId);
        } else {
            Display.message("Отсутствуют сотрудники в отделе");
        }
    }

    private static void reportEmployeeData(EmployeeBook empBook) {
        int empId = Input.employeeId();
        try {
            Employee selectedEmployee = empBook.getEmployeeById(empId);
            Display.message("Сведения о сотруднике ID=" + selectedEmployee.getId());
            Display.reportEmployeeData(selectedEmployee);
        } catch (Exception e) {
            Display.message(e.getMessage());
        }
    }


    // Вспомогательные методы
    private static void pressEnterToContinue() throws IOException {
        System.out.print("\nPress Enter to continue...");
        while ((char) System.in.read() != '\n') ;
    }

    @Contract(value = " -> new", pure = true)
    private static String @NotNull [] createMenuTitles() {
        return new String[]{"Главное меню:", "Операции:", "Аналитика:", "Отчеты:"};
    }

    @Contract(value = " -> new", pure = true)
    private static String[] @NotNull [] createMenuItems() {
        return new String[][]{
                {
                        "1. Операции",
                        "2. Аналитика",
                        "3. Отчеты",
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
                        "0. Возврат в главное меню"}
        };
    }


}