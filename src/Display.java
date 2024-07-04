import org.jetbrains.annotations.NotNull;

public class Display {


    public static void reportShortDataOfEmployees(Employee @NotNull [] reportArray) {
        System.out.printf("%-7s%-40s\n", "N п/п", "Фамилия И.О. сотрудника");
        for (int i = 0; i < reportArray.length; i++) {
            System.out.printf("%5d  %-40s\n", i + 1, reportArray[i].getShortName());
        }
    }

    public static void reportFullDataOfEmployees(Employee @NotNull [] reportArray) {
        System.out.printf("%-5s%-35s%7s%6s%10s\n", "ID", "Фамилия Имя Отчество", "Возраст", "Отдел", "Зар.плата");
        for (Employee p : reportArray) {
            System.out.printf("%-5d%-35s%7d%6d%,10d\n",
                    p.getId(), p.getFullName(), p.getAge(), p.getDepartmentId(), p.getSalary());
        }
    }

    public static void reportFullDataOfEmployees(Employee @NotNull [] reportArray, byte depId) {
        System.out.printf("%-5s%-35s%7s%10s\n", "ID", "Фамилия Имя Отчество", "Возраст", "Зар.плата");
        for (Employee p : reportArray) {
            System.out.printf("%-5d%-35s%7d%,10d\n",
                    p.getId(), p.getFullName(), p.getAge(), p.getSalary());
        }
    }

    public static void reportEmployeeData(@NotNull Employee person) {
        String formatString = "%-25s %45S\n";

        System.out.printf(formatString, "Фамилия Имя Отчество:", person.getFullName());
        System.out.printf(formatString, "Возраст:", person.getAge());
        System.out.printf(formatString, "Идентификатор отдела:", person.getDepartmentId());
        System.out.printf(formatString, "Размер заработной платы:", String.format("%,d",person.getSalary()));
    }

    public static void reportSalarySummary( int @NotNull [] salaryInfo) {
        System.out.printf("%-40s: %,d\n", "Сумма затрат на заработную плату", salaryInfo[0]);
        System.out.printf("%-40s: %d\n", "Количество сотрудников", salaryInfo[1]);
        System.out.printf("%-40s: %,.2f\n", "Средняя заработная плата", (double) salaryInfo[0] / salaryInfo[1]);
        System.out.printf("%-40s: %,d\n", "Максимальный размер заработной платы", salaryInfo[2]);
        System.out.printf("%-40s: %,d\n", "Минимальный размер заработной платы", salaryInfo[3]);
    }

    // Вспомогательные функции
    public static void message(String string) {
        System.out.println(string);
    }

    public static void formatMessage(String s, Object... value) {
        System.out.printf(s, value);
    }


}
