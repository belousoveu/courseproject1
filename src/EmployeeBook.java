import java.io.*;
import java.util.ArrayList;

public class EmployeeBook {
    final private String SAVE_FILE = "employees.txt";
    private int freeVacancies;
    private Employee[] employees;

    public EmployeeBook(int numberOfEmployees) {
        employees = new Employee[numberOfEmployees];
        freeVacancies = numberOfEmployees;
    }

    public boolean hasVacancies() {
        return freeVacancies > 0;
    }

    public boolean addNewEmployee(String secondName, String firstName, String surname, int age, byte depId, int salary) {
        if (hasVacancies()) {
            Employee emp = new Employee(secondName, firstName, surname, age, depId, salary,0);
            addEmployee(emp);
            return true;
        }
        return false;
    }

    private void addEmployee(Employee emp) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] == null || employees[i].getId() == 0) {
                employees[i] = emp;
                freeVacancies--;
                break;
            }
        }
    }

    public void deleteEmployee(int id) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null && employees[i].getId() == id) {
                employees[i] = null;
                freeVacancies++;
                break;
            }
        }
    }

    public Employee searchById(int empId) {
        Employee searchResult = null;
        for (Employee person : employees) {
            if (person != null && person.getId() == empId) {
                searchResult = person;
                break;
            }
        }
        return searchResult;
    }

    private int maxID() {
        int max = 0;
        for (Employee person : employees) {
            if (person != null) {
                max = Math.max(max, person.getId());
            }
        }
        return max;
    }

    // Методы сохранения данных в файл и считывания из файла

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(SAVE_FILE, false)) {
            for (Employee person : employees) {
                if (person != null) {
                    writer.write(person.toString());
                }
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readFromFile() {
        String line;
        Employee emp;

        try {
            File file = new File(SAVE_FILE);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                emp = new Employee(line);
                this.addEmployee(emp);
            }
            br.close();
            fr.close();
            Employee.currentId = maxID();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Методы индексации заработной платы
    public void IndexingSalary(double percentOfIndexing) {
        double multiplier = 1 + percentOfIndexing / 100;
        changeSalary(multiplier, (byte) 0);
    }

    public void IndexingSalary(double percentOfIndexing, byte depId) {
        double multiplier = 1 + percentOfIndexing / 100;
        changeSalary(multiplier, depId);
    }

    /* При индексации заработной платы применено округление до 100 рублей
    без округления увеличение/уменьшение на проценты дает "некрасивый" результат.
    Редко когда з/п бывает, например, 85 641 рубль
     */
    private void changeSalary(double multiplier, byte depId) {
        for (Employee employee : employees) {
            if ((employee != null) && (depId == 0 || employee.getDepartmentId() == depId)) {
                employee.setSalary((int) Math.ceil((employee.getSalary() * multiplier) / 100) * 100);
            }
        }
    }

    //Методы формирования списков для вывода в консоль
    public ArrayList<String> getListOfShortNames() {
        ArrayList<String> list = new ArrayList<>();
        for (Employee person : employees) {
            if (person != null) {
                list.add(person.getShortName());
            }
        }
        return list;
    }

    public ArrayList<Employee> getListOfEmployees() {
        return getListOfEmployees((byte) 0);
    }

    public ArrayList<Employee> getListOfEmployees(byte depId) {
        ArrayList<Employee> list = new ArrayList<>();
        for (Employee person : employees) {
            if ((person != null) && (depId == 0 || person.getDepartmentId() == depId)) {
                list.add(person);
            }
        }
        return list;
    }

    public ArrayList<Employee> getListOfEmployees(int targetSalary, boolean option) {
        ArrayList<Employee> list = new ArrayList<>();
        for (Employee person : employees) {
            if (person != null) {
                if (option && person.getSalary() >= targetSalary) {
                    list.add(person);
                } else if (!option && person.getSalary() < targetSalary) {
                    list.add(person);
                }
            }
        }
        return list;
    }
    // Методы аналитики по заработной плате

    /* Метод возвращает массив со сведениями о заработной плате
    [0] - суммарное значение заработной платы
    [1] - количество сотрудников
    [2] - максимальное значение заработной плата
    [3] - минимальное значение заработной плата
     */

    private int[] salary(byte depId) {
        int[] salaryArray = new int[4];
        salaryArray[3] = Integer.MAX_VALUE;
        for (Employee person : employees) {
            if ((person != null) && (depId == 0 || depId == person.getDepartmentId())) {
                salaryArray[0] += person.getSalary();
                salaryArray[1]++;
                salaryArray[2] = Math.max(salaryArray[2], person.getSalary());
                salaryArray[3] = Math.min(salaryArray[3], person.getSalary());
            }
        }
        return salaryArray;
    }

    public int[] salaryInformation() {
        return salary((byte) 0);
    }

    public int[] salaryInformation(byte depId) {
        return salary(depId);
    }

    public Employee[] findMinSalary(byte depId) {
        if (freeVacancies == employees.length) {
            return null;
        }
        ArrayList<Employee> resultArray = new ArrayList<>();
        int minValue = Integer.MAX_VALUE;
        for (Employee employee : employees) {
            if ((employee != null) && (depId == 0 || depId == employee.getDepartmentId())
                    && (employee.getSalary() < minValue)) {
                minValue = employee.getSalary();
            }
        }
        for (Employee employee : employees) {
            if ((employee != null) && (depId == 0 || depId == employee.getDepartmentId())
                    && (employee.getSalary() == minValue)) {
                resultArray.add(employee);
            }
        }
        return resultArray.toArray(new Employee[0]);
    }

    public Employee[] findMaxSalary(byte depId) {
        if (freeVacancies == employees.length) {
            return null;
        }
        ArrayList<Employee> resultArray = new ArrayList<>();
        int maxValue = 0;
        for (Employee value : employees) {
            if ((value != null) && (depId == 0 || depId == value.getDepartmentId())
                    && (value.getSalary() > maxValue)) {
                maxValue = value.getSalary();
            }
        }
        for (Employee employee : employees) {
            if ((employee != null) && (depId == 0 || depId == employee.getDepartmentId())
                    && (employee.getSalary() == maxValue)) {
                resultArray.add(employee);
            }
        }
        return resultArray.toArray(new Employee[0]);
    }
}
