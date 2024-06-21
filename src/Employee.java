import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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
        fullName = secondName + " " + firstName + " " + surName;
        this.age = age;
        departmentId = depId;
        salary = sal;
        shortName = returnShortName(secondName, firstName, surName);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId && age == employee.age && departmentId == employee.departmentId && salary == employee.salary && Objects.equals(firstName, employee.firstName) && Objects.equals(secondName, employee.secondName) && Objects.equals(surName, employee.surName) && Objects.equals(fullName, employee.fullName) && Objects.equals(shortName, employee.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, secondName, surName, fullName, shortName, age, departmentId, salary);
    }

    public String toString() {
        return employeeId + ";" + fullName + ";" + shortName + ";" + firstName + ";" + secondName + ";" + surName + ";"
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

    public String getSurName() {
        return surName;
    }

    // Блок методов set
    public void setProperties(String secondName, String firstName, String surName, int age, byte depId, int salary) {
        this.setFirstName(firstName);
        this.setSecondName(secondName);
        this.setSurName(surName);
        this.setFullName(secondName + " " + firstName + " " + surName);
        this.setShortName(returnShortName(secondName, firstName, surName));
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
