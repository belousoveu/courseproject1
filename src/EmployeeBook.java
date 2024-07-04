import java.io.*;
import java.util.ArrayList;

public class EmployeeBook {
    final private String SAVE_FILE = "employees.txt";
    private int freeVacancies;
    private final Employee[] employees;

    //Constructor
    public EmployeeBook(int numberOfEmployees) {
        employees = new Employee[numberOfEmployees];
        freeVacancies = numberOfEmployees;
    }

    public boolean hasVacancies() {
        return freeVacancies > 0;
    }

    //Add Employee
    public void addEmployee(Employee emp) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] == null || employees[i].getId() == 0) {
                employees[i] = emp;
                freeVacancies--;
                break;
            }
        }
    }

    //Delete Employee
    public void deleteEmployee(int id) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null && employees[i].getId() == id) {
                employees[i] = null;
                freeVacancies++;
                break;
            }
        }
    }

    public Employee getEmployeeById(int empId) {
        for (Employee person : employees) {
            if (person != null && person.getId() == empId) {
                return person;
            }
        }
        return null;
    }

    private int maxId() {
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
            Employee.currentId = maxId();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Метод индексации заработной платы

    public int IndexingSalary(double percentOfIndexing, byte depId) {
        double multiplier = 1 + percentOfIndexing / 100;
        return changeSalary(multiplier, depId);
    }

    private int changeSalary(double multiplier, byte depId) {
        return changeSalary(multiplier, depId, 2);
    }

    /**
     * Метод индексации заработной платы
     *
     * @param multiplier     - коэффициент индексации заработной платы
     * @param depId          - Id отдела (при depId==0 - в целом по организации)
     * @param roundingDegree степень округления (по умолчанию 2, т.е. округление до сотен рублей)
     * @return количество сотрудников, которым была проиндексирована заработная плата
     */
    private int changeSalary(double multiplier, byte depId, int roundingDegree) {
        int counter = 0;
        double scale = Math.pow(10, roundingDegree);
        for (Employee employee : employees) {
            if ((employee != null) && (depId == 0 || employee.getDepartmentId() == depId)) {
                employee.setSalary((int) (Math.round((employee.getSalary() * multiplier) / scale) * scale));
                counter++;
            }
        }
        return counter;
    }

    //Методы выборки сотрудников по различным параметрам

    public Employee[] getActualEmployees() {
        return getActualEmployees((byte) 0);
    }

    public Employee[] getActualEmployees(byte depId) {
        ArrayList<Employee> list = new ArrayList<>();
        for (Employee person : employees)
            if ((person != null) && (depId == 0 || person.getDepartmentId() == depId)) {
                list.add(person);
            }
        return list.toArray(new Employee[0]);
    }

    /**
     * Метод возвращает выборку сотрудников по заработной плате
     *
     * @param targetSalary - целевой размер заработной платы
     * @param mode         - 0 - меньше указанной заработной платы, 1 - больше или равно указанной заработной платы
     * @return массив сотрудников соответствующих условиям выборки по заработной плате
     */
    public Employee[] getActualEmployees(int targetSalary, int mode) {
        ArrayList<Employee> list = new ArrayList<>();
        for (Employee person : employees) {
            if (person != null) {
                if (mode == 1 && person.getSalary() >= targetSalary) {
                    list.add(person);
                } else if (mode == 0 && person.getSalary() < targetSalary) {
                    list.add(person);
                }
            }
        }
        return list.toArray(new Employee[0]);
    }

    /**
     * Метод возвращает массив со сведениями о заработной плате
     *
     * @param depId - Id отдела (при depId==0 - в целом по организации)
     * @return массив со сведениями о заработной плате
     * [0] - суммарное значение заработной платы
     * [1] - количество сотрудников
     * [2] - максимальное значение заработной платы
     * [3] - минимальное значение заработной платы
     */
    public int[] getSalarySummary(byte depId) {
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

    public Employee[] getEmployeeWithMinSalary(byte depId) {
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

    public Employee[] getEmployeeWithMaxSalary(byte depId) {
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
