import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Employee {
    static int currentId = 0;

    private int employeeId;
    private String firstName;
    private String secondName;
    private String middleName;
    private String fullName;
    private String shortName;
    private int age;
    private byte departmentId;
    private int salary;

    // Конструкторы класса
    // Вариант 1. используется при вводе нового сотрудника. Параметры передаются в явном виде
    public Employee(String secondName, String firstName, String middleName, int age, byte depId, int sal) {
        this.secondName = secondName;
        this.firstName = firstName;
        this.middleName = middleName;
        fullName = secondName + " " + firstName + " " + middleName;
        this.age = age;
        departmentId = depId;
        salary = sal;
        shortName = returnShortName(secondName, firstName, middleName);
        currentId++;
        employeeId = currentId;
    }

    // Вариант 2. параметры передаются одной форматированной строкой. Используется для чтения сохраненных данных из файла
    public Employee(String line) {
        String[] em = lineToArray(line);
        employeeId = Integer.parseInt(em[0]);
        fullName = em[1];
        shortName = em[2];
        firstName = em[3];
        secondName = em[4];
        middleName = em[5];
        age = Integer.parseInt(em[6]);
        departmentId = Byte.parseByte(em[7]);
        salary = Integer.parseInt(em[8]);
    }

    public void update(String secondName, String firstName, String surName, int age, byte depId, int salary) {
        this.setFirstName(firstName);
        this.setSecondName(secondName);
        this.setMiddleName(surName);
        this.setFullName(secondName + " " + firstName + " " + surName);
        this.setShortName(returnShortName(secondName, firstName, surName));
        this.setAge(age);
        this.setDepartmentId(depId);
        this.setSalary(salary);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId && age == employee.age && departmentId == employee.departmentId &&
                salary == employee.salary && firstName.equals(employee.firstName) && secondName.equals(employee.secondName) &&
                middleName.equals(employee.middleName) && fullName.equals(employee.fullName) && shortName.equals(employee.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, secondName, middleName, fullName, shortName, age, departmentId, salary);
    }

    public String toString() {
        return employeeId + ";" + fullName + ";" + shortName + ";" + firstName + ";" + secondName + ";" + middleName + ";"
                + age + ";" + departmentId + ";" + salary + "\n";
    }
    // локальные вспомогательные методы

    private @NotNull String returnShortName(String a, @NotNull String b, @NotNull String c) {
        return a + " " + b.charAt(0) + "." + c.charAt(0) + ".";
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

    public String getMiddleName() {
        return middleName;
    }

    // Блок методов set
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

    public void setMiddleName(String s) {
        this.middleName = s;
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
