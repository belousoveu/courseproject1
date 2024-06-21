import java.util.ArrayList;

public class Display {

    public static void dismissalOfEmployee(EmployeeBook empBook, int empId) {

        Employee person = empBook.searchById(empId);
        String isEmpty = "Сотрудник с ID=%d отсутствует в базе\n";

        if (person != null) {
            System.out.printf("Уволен сотрудник: %s\n", person.getShortName());
            empBook.deleteEmployee(empId);
        } else {
            System.out.printf(isEmpty, empId);
        }
    }

    public static void showListOfEmployees(EmployeeBook empBook) {
        ArrayList<String> outputStrings = empBook.getListOfShortNames();

        System.out.printf("%-7s%-40s\n", "N п/п", "Фамилия И.О. сотрудника");
        displayList(outputStrings, "%5d  %-40s\n", "Список сотрудников пуст.");

    }

    public static void showFullDataOfEmployees(EmployeeBook empBook) {
        ArrayList<Employee> list = empBook.getListOfEmployees();
        String isEmpty = "Список сотрудников пуст.";

        System.out.printf("%-5s%-35s%7s%6s%10s\n", "ID", "Фамилия Имя Отчество", "Возраст", "Отдел", "Зар.плата");
        if (!list.isEmpty()) {
            for (Employee p : list) {
                System.out.printf("%-5d%-35s%7d%6d%,10d\n",
                        p.getId(), p.getFullName(), p.getAge(), p.getDepartmentId(), p.getSalary());
            }
        } else {
            System.out.println(isEmpty);
        }

    }

    public static void showFullDataOfEmployees(EmployeeBook empBook, byte depId) {

        ArrayList<Employee> list = empBook.getListOfEmployees(depId);
        System.out.printf("Отдел ID=%d\n", depId);
        String isEmpty = "Список сотрудников пуст.";

        System.out.printf("%-5s%-35s%7s%10s\n", "ID", "Фамилия Имя Отчество", "Возраст", "Зар.плата");
        if (!list.isEmpty()) {
            for (Employee p : list) {
                System.out.printf("%-5d%-35s%7d%,10d\n",
                        p.getId(), p.getFullName(), p.getAge(), p.getSalary());
            }
        } else {
            System.out.println(isEmpty);
        }
    }

    /* option - вариант отбора сотрудников по размеру з/п
        false   - отбор сотрудников с з/платой меньше чем targetSalary
        true    - отбор сотрудников с з/платой большей или равной targetSalary
     */

    public static void showFullDataOfEmployees(EmployeeBook empBook, int targetSalary, boolean option) {

        ArrayList<Employee> list = empBook.getListOfEmployees(targetSalary, option);
        if (option) {
            System.out.printf("Выборка сотрудников с заработной платой большей или равной %,d рублей\n", targetSalary);
        } else {
            System.out.printf("Выборка сотрудников с заработной платой меньшей %,d рублей\n", targetSalary);
        }
        String isEmpty = "Список сотрудников пуст.";

        System.out.printf("%-5s%-35s%7s%6s%10s\n", "ID", "Фамилия Имя Отчество", "Возраст", "Отдел", "Зар.плата");
        if (!list.isEmpty()) {
            for (Employee p : list) {
                System.out.printf("%-5d%-35s%7d%6d%,10d\n",
                        p.getId(), p.getFullName(), p.getAge(), p.getDepartmentId(), p.getSalary());
            }
        } else {
            System.out.println(isEmpty);
        }
    }

    public static void ShowEmployeeData(EmployeeBook empBook, int empId) {
        Employee person = empBook.searchById(empId);
        System.out.printf("Сотрудник ID=%d\n", empId);
        String isEmpty = "Отсутствует сотрудник с таким ID";
        String formatString = "%-25s %45S\n";

        if (person != null) {
            System.out.printf(formatString, "Фамилия Имя Отчество:", person.getFullName());
            System.out.printf(formatString, "Возраст:", person.getAge());
            System.out.printf(formatString, "Идентификатор отдела:", person.getDepartmentId());
            System.out.printf(formatString, "Размер заработной платы:", person.getSalary());
        } else {
            System.out.println(isEmpty);
        }
    }

    public static void string(String string) {
        System.out.println(string);
    }

    public static void stringf(String s, int value) {
        System.out.printf(s, value);
    }

    // Вспомогательные функции

    public static void showTitle(String section, String title) {
        System.out.printf("\n%-15s%s\n\n", section, title);
    }

    private static void displayList(ArrayList<String> list, String formatString, String isEmpty) {
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                System.out.printf(formatString, i + 1, list.get(i));
            }
        } else {
            System.out.println(isEmpty);
        }
    }

    /* option - параметр опредеяющий тип запрашиваемых данных
        0 - минимальная заработная плата
        1 - максимальная заработная плата
     */
    public static void employeeInformation(EmployeeBook empBook, int option) {
        Employee emp = switch (option) {
            case 0 -> empBook.findMinSalary((byte) 0);
            case 1 -> empBook.findMaxSalary((byte) 0);
            default -> throw new IllegalArgumentException("Invalid value of 'option'");
        };
        if (emp!=null) {
            ShowEmployeeData(empBook, emp.getId());
        } else {
            System.out.println("Список сотруников пуст.");
        }

    }

    public static void employeeInformation(EmployeeBook empBook, byte depId, int option) {
        Employee emp = switch (option) {
            case 0 -> empBook.findMinSalary(depId);
            case 1 -> empBook.findMaxSalary(depId);
            default -> throw new IllegalArgumentException("Invalid value of 'option'");
        };
        if (emp!=null) {
            ShowEmployeeData(empBook, emp.getId());
        } else {
            System.out.println("Список сотруников пуст.");
        }
    }


    public static void salaryInformation(EmployeeBook empBook) {
        int[] salaryInfo = empBook.salaryInformation();
        String title = "Сведения о заработной плате по организации";
        salaryReport(title, salaryInfo);
    }

    public static void salaryInformation(EmployeeBook empBook, byte depId) {
        int[] salaryInfo = empBook.salaryInformation(depId);
        String title = "Сведения о заработной плате по отделу ID=" + depId;
        salaryReport(title, salaryInfo);
    }

    private static void salaryReport(String title, int[] salaryInfo) {
        System.out.println(title);
        System.out.printf("%-40s: %,d\n", "Сумма затрат на заработную плату", salaryInfo[0]);
        System.out.printf("%-40s: %d\n", "Количество сотрудников", salaryInfo[1]);
        System.out.printf("%-40s: %,.2f\n", "Средняя заработная плата", (double) salaryInfo[0] / salaryInfo[1]);
        System.out.printf("%-40s: %,d\n", "Максимальный размер заработной платы", salaryInfo[2]);
        System.out.printf("%-40s: %,d\n", "Минимальный размер зарабтной платы", salaryInfo[3]);
    }
}
