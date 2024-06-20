import java.io.*;
import java.util.ArrayList;

public class EmployeeBook {
    final private String SAVE_FILE = "employees.txt";
    private int freeVacancies;
    private Employee[] employees;
//    private String organizationName = "Рога и Копыта";

    public EmployeeBook(int numberOfEmployees) {
        employees = new Employee[numberOfEmployees];
        freeVacancies=numberOfEmployees;
    }

    public boolean hasVacancies() {
        return freeVacancies > 0;
    }

    public boolean hasEmployee() {
        return freeVacancies != employees.length;
    }

    public void addNewEmployee(String secondName, String firstName, String surname, int age, byte depId, int salary) {
        if (hasVacancies()) {
            Employee emp = new Employee(secondName, firstName, surname, age, depId, salary);
            addEmployee(emp);
        }
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
        String line;
        try (FileWriter writer = new FileWriter(SAVE_FILE, false)) {
            for (Employee person : employees) {
                if (person != null) {
                    line = person.getId() + ";" + person.getFullName() + ";" + person.getShortName() + ";"
                            + person.getFirstName() + ";" + person.getSecondName() + ";" + person.getSurName() + ";"
                            + person.getAge() + ";" + person.getDepartmentId() + ";" + person.getSalary() + "\n";
                    writer.write(line);
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
        double mult = 1 + percentOfIndexing / 100;
        changeSalary(mult, (byte) 0);
    }

    public void IndexingSalary(double percentOfIndexing, byte depId) {
        double mult = 1 + percentOfIndexing / 100;
        changeSalary(mult, depId);
    }

    /* При индексации заработной платы применено округление до 100 рублей
    без округления увеличение/уменьшение на проценты дает "некрасивый" результат.
    Редко когда з/п бывает, например, 85 641 рубль
     */
    private void changeSalary(double mult, byte depId) {
        for (int i = 0; i < employees.length; i++) {
            if ((employees[i] != null) && (depId == 0 || employees[i].getDepartmentId() == depId)) {
                employees[i].setSalary((int) Math.ceil((employees[i].getSalary() * mult) / 100) * 100);
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
}
