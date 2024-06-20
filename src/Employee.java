import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Employee {
    static int currentId = 0;

    private int employeeId;
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
    public Employee(String secondName, String firstName, String surName, int age, byte depId, int sal) {
        this.secondName = secondName;
        this.firstName = firstName;
        this.surName = surName;
        fullName = secondName+" "+firstName+" "+surName;
        this.age = age;
        departmentId = depId;
        salary = sal;
        shortName = returnShortName(secondName,firstName,surName);
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

//    public boolean isEmpty() {
//        return this=null;
//    }

// локальные вспомогательные методы
    private String returnShortName(String a,String b, String c) {
        return a + " " + b.charAt(0) + "." + c.charAt(0) + ".";
    }

    @Contract(pure = true)
    private String @NotNull [] fullNameToArray(@NotNull String name) {
        return name.split(" ");
    }

    @Contract(pure = true)
    private String @NotNull [] lineToArray(@NotNull String line) {
        return line.split(";");
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
//        this.setShortName(this.returnShortName(nameArray));
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
