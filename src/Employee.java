import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Employee {
    static int currentId = 0;

    int employeeId;
    private String firstName;
    private String secondName;
    private String surName;
    private String fullName;
    private String shortName;
    private int age;
    private byte departmentId;
    private int salary;

    // Конструкторы класса
    // вариант 1. используется при вводе нового сотрудника. Параметры передаются в явном виде
    public Employee(String name, int age, byte depId, int sal) {
        fullName = name;
        this.age = age;
        departmentId = depId;
        salary = sal;
        String[] nameArray = this.fullNameToArray(name);
        secondName = nameArray[0];
        firstName = nameArray[1];
        surName = nameArray[2];
        shortName = this.returnShortName(nameArray);
        currentId++;
        employeeId = currentId;

    }

    // вариант 2. параметры передаются одной форматированной строкой. используется для чтения сохраненных данных из файла
    public Employee(String line) {
        String[] em = lineToArray(line);
        employeeId = Integer.parseInt(em[0]);
        fullName = em[1];
        shortName = em[2];
        firstName = em[3];
        secondName = em[4];
        surName = em[5];
        age = Integer.parseInt(em[6]);
        departmentId = Byte.parseByte(em[7]);
        salary = Integer.parseInt(em[8]);
    }

// локальные вспомогательные методы
    private @NotNull String returnShortName(String @NotNull [] array) {
        return array[0] + " " + array[1].charAt(0) + "." + array[2].charAt(0) + ".";
    }

    @Contract(pure = true)
    private String @NotNull [] fullNameToArray(@NotNull String name) {
        return name.split(" ");
    }

    @Contract(pure = true)
    private String @NotNull [] lineToArray(@NotNull String line) {
        return line.split(";");
    }

    //Методы вывода в консоль
    public void displayEmployeeData() {
        System.out.println("Вывод сведений о сотруднике:\n");
        System.out.printf("Идентификатор:\t%d\n", employeeId);
        System.out.printf("Фамилия:\t\t\t%s\n", secondName);
        System.out.printf("Имя:\t\t\t\t%s\n", firstName);
        System.out.printf("Отчество:\t\t\t%s\n", surName);
        System.out.printf("Возраст:\t\t\t%d\n", age);
        System.out.printf("ФИО:\t\t\t\t%s\n", shortName);
        System.out.printf("Заработная плата:\t%d\n", salary);
        System.out.println();
    }

    // Блок методов get
    public int getId() {
        return employeeId;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public int getSalary() {
        return salary;
    }

    public byte getDepartmentId() {
        return departmentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getSurName() {
        return surName;
    }

    // Блок методов set
    public void setProperties(String fullName, int age, byte depId, int salary) {
        String[] nameArray = this.fullNameToArray(fullName);
        this.setFullName(fullName);
        this.setShortName(this.returnShortName(nameArray));
        this.setFirstName(nameArray[1]);
        this.setSecondName(nameArray[0]);
        this.setSurName(nameArray[2]);
        this.setAge(age);
        this.setDepartmentId(depId);
        this.setSalary(salary);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setShortName(String s) {
        this.shortName = s;

    }

    public void setFirstName(String s) {
        this.firstName = s;
    }

    public void setSecondName(String s) {
        this.secondName = s;
    }

    public void setSurName(String s) {
        this.surName = s;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDepartmentId(byte depId) {
        this.departmentId = depId;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
