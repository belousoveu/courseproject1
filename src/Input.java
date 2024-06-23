import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Scanner;

public class Input {
    final private static byte NUMBER_OF_DEPARTMENTS = 5;

    // Ввод чисел типа byte
    public static byte departmentId() {
        String title = "ID отдела: ";
        return departmentId(title, true);
    }

    public static byte departmentId(byte currentValue) {
        String title = "ID отдела (" + currentValue + "): ";
        byte value = departmentId(title, false);
        return value == 0 ? currentValue : value;
    }

    /* Параметр option - выпоняет проверку на допустимость 0
          true - проверка выполняется (значение 0 запрещено)
          false - проверка не выполнется (можно вернуть 0)
   */
    private static byte departmentId(String title, boolean option) {
        Scanner sc = new Scanner(System.in);
        byte inputValue;
        String errorMessage = "Необходимо ввести целое число в от 1 до " + Input.NUMBER_OF_DEPARTMENTS;
        errorMessage += (option ? "" : "0 - оставить предыдущее значение");

        do {
            System.out.print(title);
            if (sc.hasNextByte()) {
                inputValue = sc.nextByte();
                if ((inputValue >= 1 && inputValue <= Input.NUMBER_OF_DEPARTMENTS) || (inputValue == 0 && !option)) {
                    return inputValue;
                } else {
                    System.out.println(errorMessage);
                }
            } else {
                System.out.println(errorMessage);
                sc.next();
            }

        } while (true);
    }

    // Ввод чисел типа int
    public static int employeeId() {
        String errorMessage = "Необходимо ввести целое число больше 0";
        String title = "ID сотрудника: ";
        return positiveInt(title, errorMessage, true);
    }

    public static int salary() {
        String errorMessage = "Необходимо ввести целое число больше 0";
        String title = "Размер заработной платы: ";
        return positiveInt(title, errorMessage, true);
    }

    public static int salary(int currentValue) {
        String errorMessage = "Необходимо ввести целое число больше 0 (0 - оставить предыдущее значение)";
        String title = "Размер заработной платы (" + currentValue + "): ";
        int value = positiveInt(title, errorMessage, false);
        return value == 0 ? currentValue : value;
    }

    public static int age() {
        String errorMessage = "Необходимо ввести целое число больше 0";
        String title = "Возраст: ";
        return positiveInt(title, errorMessage, true);
    }

    public static int age(int currentValue) {
        String errorMessage = "Необходимо ввести целое число больше 0 (0 - оставить предыдущее значение)";
        String title = "Возраст (" + currentValue + "): ";
        int value = positiveInt(title, errorMessage, false);
        return value == 0 ? currentValue : value;
    }

    /* Параметр option - выпоняет проверку на допустимость 0
            true - проверка выполняется (значение 0 запрещено)
            false - проверка не выполнется (можно вернуть 0)
     */
    private static int positiveInt(String title, String errorMessage, boolean option) {
        int inputValue;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print(title);
            if (sc.hasNextInt()) {
                inputValue = sc.nextInt();
                if ((inputValue > 0) || (inputValue == 0 && !option)) {
                    return inputValue;
                } else {
                    System.out.printf(errorMessage);
                }
            } else {
                System.out.println(errorMessage);
                sc.next();
            }
        } while (true);

    }

    // Ввод строковых значений
    //
    public static String secondName() {
        String title = "Фамилия: ";
        String errorMessage = "Строка содержит недопустимые символы";
        String inputValue = russianPatternString(title, errorMessage, true);
        return toCapitalFirstLetter(inputValue);
    }

    public static String secondName(String currentValue) {
        String title = "Фамилия (" + currentValue + ") : ";
        String errorMessage = "Строка содержит недопустимые символы";
        String inputValue = russianPatternString(title, errorMessage, false);
        inputValue = inputValue.isEmpty() ? currentValue : inputValue;
        return toCapitalFirstLetter(inputValue);
    }


    public static String firstName() {
        String title = "Имя: ";
        String errorMessage = "Строка содержит недопустимые символы";
        String inputValue = russianPatternString(title, errorMessage, true);
        return toCapitalFirstLetter(inputValue);
    }

    public static String firstName(String currentValue) {
        String title = "Имя (" + currentValue + "): ";
        String errorMessage = "Строка содержит недопустимые символы";
        String inputValue = russianPatternString(title, errorMessage, false);
        inputValue = inputValue.isEmpty() ? currentValue : inputValue;
        return toCapitalFirstLetter(inputValue);
    }

    public static String surName() {
        String title = "Отчество: ";
        String errorMessage = "Строка содержит недопустимые символы";
        String inputValue = russianPatternString(title, errorMessage, true);
        return toCapitalFirstLetter(inputValue);
    }

    public static String surName(String currentValue) {
        String title = "Отчество (" + currentValue + "): ";
        String errorMessage = "Строка содержит недопустимые символы";
        String inputValue = russianPatternString(title, errorMessage, false);
        inputValue = inputValue.isEmpty() ? currentValue : inputValue;
        return toCapitalFirstLetter(inputValue);
    }

    private static String toCapitalFirstLetter(String Value) {
        String FirstLetter = Value.toLowerCase().substring(0, 1).toUpperCase(Locale.ROOT);
        return FirstLetter + Value.substring(1).toLowerCase();
    }


    /* Параметр option - выпоняет проверку на пустое значение
            true - проверка выполняется (пустая строка запрещена)
            false - проверка не выполнется (можно вернуть пустую строку)
     */
    private static @NotNull String russianPatternString(String title, String errorMessage, boolean option) {
        String inputValue;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print(title);
            if (sc.hasNextLine()) {
                inputValue = sc.nextLine();
                if (inputValue.matches("[а-яА-Я- ]+") || (!option && inputValue.isEmpty())) {
                    if (option && inputValue.isEmpty()) {
                        System.out.println(errorMessage);
                    } else {
                        return inputValue;
                    }
                } else {
                    System.out.println(errorMessage);
                }
            } else {
                System.out.println(errorMessage);
                sc.next();
            }
        } while (true);
    }

    // Ввод числа типа double
    public static double percentOfIndexing() {
        String errorMessage = "Необходимо ввести вещественное число";
        String title = "Процент индексации заработной платы: ";
        return doubleValue(title, errorMessage);
    }

    private static double doubleValue(String title, String errorMessage) {
        double inputValue;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print(title);
            if (sc.hasNextDouble()) {
                inputValue = sc.nextDouble();
                return inputValue;
            } else {
                System.out.println(errorMessage);
                sc.next();
            }
        } while (true);
    }

    //Методы группового ввода данных
    public static boolean editEmployeeData(EmployeeBook empBook, int empId) {

        Employee person = empBook.searchById(empId);

        if (person != null) {
            String secondName = secondName(person.getSecondName());
            String firstName = firstName(person.getFirstName());
            String surName = surName(person.getSurName());
            int age = age(person.getAge());
            byte depId = Input.departmentId(person.getDepartmentId());
            int salary = Input.salary(person.getSalary());

            person.setProperties(secondName, firstName, surName, age, depId, salary);

            return true;
        } else {
            return false;
        }


    }
}
