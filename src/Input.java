import java.util.Locale;
import java.util.Scanner;

public class Input {


    public static byte departmentId() {
        return departmentId(true);
    }

    // Ввод чисел типа byte
    public static byte departmentId(boolean checkEmpty) {
        Scanner sc = new Scanner(System.in);
        byte inputValue;
        String errorMessage = "Необходимо ввести целое число в от 1 до 3";
        errorMessage += (checkEmpty ? "" : "0 - оставить предыдущее значение");

        do {
            System.out.print("ID отдела: ");
            if (sc.hasNextByte()) {
                inputValue = sc.nextByte();
                if ((inputValue >= 1 && inputValue <= 3) || (inputValue == 0 && !checkEmpty)) {
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
        return positiveInt(title, errorMessage);
    }

    public static int salary() {
        String errorMessage = "Необходимо ввести целое число больше 0";
        String title = "Размер заработной платы: ";
        return positiveInt(title, errorMessage);

    }

    public static int age() {
        String errorMessage = "Необходимо ввести целое число больше 0";
        String title = "Возраст: ";
        return positiveInt(title, errorMessage);
    }

    private static int positiveInt(String title, String errorMessage) {
        int inputValue;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print(title);
            if (sc.hasNextInt()) {
                inputValue = sc.nextInt();
                if (inputValue > 0) {
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
        String title = "Фамилия:";
        String errorMessage = "Строка содержит недопустимые символы";
        String inputValue=russianPatternString(title, errorMessage);
        return toCapitalFirstLetter(inputValue);
    }

    public static String firstName() {
        String title = "Имя:";
        String errorMessage = "Строка содержит недопустимые символы";
        String inputValue=russianPatternString(title, errorMessage);
        return toCapitalFirstLetter(inputValue);
    }

    public static String surName() {
        String title = "Отчество:";
        String errorMessage = "Строка содержит недопустимые символы";
        String inputValue=russianPatternString(title, errorMessage);
        return toCapitalFirstLetter(inputValue);
    }

    private static String toCapitalFirstLetter(String Value) {
        String FirstLetter=Value.toLowerCase().substring(0,1).toUpperCase(Locale.ROOT);
        return FirstLetter+Value.substring(1).toLowerCase();
    }

    private static String russianPatternString(String title, String errorMessage) {
        String inputValue;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print(title);
            if (sc.hasNextLine()) {
                inputValue = sc.nextLine();
                if (inputValue.matches("[а-яА-Я- ]+")) {
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
}
