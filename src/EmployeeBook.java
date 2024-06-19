import java.io.*;

public class EmployeeBook {
    final private String SAVE_FILE = "employees.txt";
    private static int freeVacancies = 10;
    private Employee[] employees;
    private String organizationName = "Рога и Копыта";

    public EmployeeBook(int numberOfEmployees) {
        employees = new Employee[numberOfEmployees];
    }

    public boolean hasVacancies() {
        return freeVacancies > 0;
    }

    public boolean hasEmployee() {
        return freeVacancies != employees.length;
    }

    public void addNewEmployee(Employee emp) {
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

    public void displayFullData() {
        System.out.println("Сведения о сотрудниках организации\n");
        String formatString = "%-5s%-35s%7s%6s%10s\n";
        System.out.printf(formatString, "ID", "Фамилия Имя Отчество", "Возраст", "Отдел", "Зар.плата");
        for (Employee person : employees) {
            if (person != null) {
                System.out.printf(formatString, person.getId(), person.getFullName(), person.getAge(),
                        person.getDepartmentId(), person.getSalary());
            }
        }
    }

    public void saveToFile() {
        String line;
        try (FileWriter writer = new FileWriter(SAVE_FILE, false)) {
            for (Employee person : employees) {
                if (person != null) {
                    line = person.employeeId + ";" + person.getFullName() + ";" + person.getShortName() + ";"
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
                this.addNewEmployee(emp);
            }
            br.close();
            fr.close();
            Employee.currentId = maxID();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int maxID() {
        int max = 0;
        for (Employee person : employees) {
            if (person != null) {
                max = Math.max(max, person.employeeId);
            }
        }
        return max;
    }

    public Employee searchById(int empId) {
        Employee searchResult = null;
        for (Employee person : employees) {
            if (person != null && person.employeeId == empId) {
                searchResult = person;
                break;
            }
        }
        return searchResult;
    }

    public void IndexingSalary(double percentOfIndexing) {
        double mult = 1 + percentOfIndexing/100;
        changeSalary(mult, (byte) 0);
    }

    public void IndexingSalary(double percentOfIndexing, byte depId) {
        double mult = 1 + percentOfIndexing/100;
        changeSalary(mult, depId);
    }
/* При индексации заработной платы применено округление до 100 рублей
без округления увеличение/уменьшение на проценты дает "некрасивый" результат. Редко когда з/п бывает например 85 641 рубль
 */
    private void changeSalary(double mult, byte depId) {
        for (int i=0;i<employees.length;i++) {
            if ((employees[i]!=null)&&(depId==0 || employees[i].getDepartmentId()==depId)) {
                employees[i].setSalary((int) Math.ceil((employees[i].getSalary()*mult)/100)*100);
            }
        }
    }
}
