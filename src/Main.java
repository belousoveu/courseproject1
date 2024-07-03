import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {

        DbEmployees dbEmp = new DbEmployees(Const.URL, Const.USER, Const.PASSWORD);
        dbEmp.onStart(Const.numberOfEmployees);

        EmployeeBook empBook = new EmployeeBook(Const.numberOfEmployees);
        empBook.readFromFile();
        TwoLevelMenu mainMenu = new TwoLevelMenu(Const.MAIN_MENU_TITLES, Const.MAIN_MENU_ITEMS);

        while (true) {

            if (mainMenu.runMenu()) {
                eventChoiceMenu(mainMenu.getLevel(), mainMenu.getChoice(), dbEmp);
                pressEnterToContinue();
            } else {
                System.out.println("Выход из программы");
                break;
            }


        }
        empBook.saveToFile();
    }

    private static void eventChoiceMenu(int level, int choice, DbEmployees dbEmp) {
        switch (level) {
            case 1:
                switch (choice) {
                    case 1: // Создать нового сотрудника
                        if (dbEmp.hasVacancies()) {
                            Employee newEmployee = Input.newEmployee();
                            if (dbEmp.addEmployee(newEmployee)) {
                                Display.formatMessage("Принят новый сотрудник: %s", newEmployee.getShortName());
                            }
                        } else {
                            Display.formatMessage("Нет вакансий");
                        }
                        break;
                    case 2: // Редактировать данные сотрудника
                    {
                        int empId = Input.employeeId();
                        Employee selectedEmployee = dbEmp.getEmployeeById(empId);
                        if (selectedEmployee != null) {
                            if (Input.editEmployeeData(selectedEmployee)) {
                                System.out.println("selectedEmployee.toString() = " + selectedEmployee.toString()); // TODO: удалить
                                if (dbEmp.updateEmployee(selectedEmployee)) {
                                    Display.formatMessage("Данные сотрудника %s обновлены", selectedEmployee.getShortName());
                                }
                            }
                        } else {
                            Display.formatMessage("Сотрудник с ID=%d отсутсвует в базе", empId);
                        }
                        break;
                    }
                    case 3: // Уволить сотрудника
                        int empId = Input.employeeId();
                        Employee selectedEmployee = dbEmp.getEmployeeById(empId);
                        if (selectedEmployee != null) {
                            if (dbEmp.deleteEmployee(selectedEmployee)) {
                                Display.formatMessage("Сотрудник %s уволен", selectedEmployee.getShortName());
                            }
                        } else {
                            Display.formatMessage("Сотрудник с ID=%d отсутсвует в базе", empId);
                        }
                        break;
                    case 4: //Индексация заработной платы по организации
                    {
                        double percentOfIndexing = Input.percentOfIndexing();
                        int recordNumber = dbEmp.indexingSalary(percentOfIndexing);
                        if (recordNumber > 0) {
                            Display.formatMessage("Заработная плата по организации %s проинксирована на %.2f%%\n" +
                                    "Индексация затронула %d сотрудников(а)", Const.companyName, percentOfIndexing, recordNumber);
                        } else if (recordNumber == 0) {
                            Display.message("Индексация не проведена. В организации нет действующих сотрудников");
                        }
                        break;
                    }
                    case 5: //Индексация заработной платы по отделу
                    {
                        byte departmentId = Input.departmentId();
                        double percentOfIndexing = Input.percentOfIndexing();
                        int recordNumber = dbEmp.indexingSalary(percentOfIndexing, departmentId);
                        if (recordNumber > 0) {
                            Display.formatMessage("Заработная плата по отделу %s проинксирована на %.2f%%\n" +  //TODO: сделать класс department. Брать оттуда наименования отделов
                                    "Индексация затронула %d сотрудников(а)", Const.companyName, percentOfIndexing, recordNumber); //TODO: заменить название компании на название отдела
                        } else if (recordNumber == 0) {
                            Display.message("Индексация не проведена. В отделе нет действующих сотрудников");
                        }
                        break;
                    }
                }
            case 2:
                switch (choice) {
                    case 1:
//                        empBook.addEmployee();
                        break;
                    case 2:
//                        empBook.deleteEmployee();
                        break;
                    case 3:
//                        empBook.editEmployee();
                        break;
                    case 4:
//                        empBook.showEmployees();
                        break;
                    case 5:
//                        empBook.showEmployeesByDepartment();
                        break;
                    case 6:
//                        empBook.showEmployeesByPosition();
                        break;
                    case 7:
                }
        }

    }


    private static void pressEnterToContinue() throws IOException {
        System.out.print("\nPress Enter to continue...");
        while ((char) System.in.read() != '\n') ;
    }

}