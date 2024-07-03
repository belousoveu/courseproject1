import java.sql.*;

public class DbEmployees {
    public static final String DB_NAME = "db_employees";
    private final String url;
    private final String user;
    private final String password;
    private int freeVacancies;

    //constructor of DbEmployees
    public DbEmployees(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }


    public boolean onStart(int limitEmployees) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            if (!isTableExists(statement, "info")) {
                //Создаем таблицу info и заполняем исходными данными
                //Если при создании таблицы или при заполнении таблицы возникла ошибка, то выходим
                Display.message("Создание таблицы info...");
                if (createTableInfo(statement)) {
                    Display.message("Таблица info создана");
                } else {
                    return false;
                }
                Input.setup();

                if (saveInfo(statement, true)) {
                    Display.message("Данные записаны");
                } else {
                    return false;
                }
            } else {
                if (!loadInfo(statement)) {
                    return false;
                } else {
                    Display.message("Данные загружены");
                    System.out.println("Const.companyName = " + Const.companyName); //TODO: временная строчка. Нужно будет удалить
                    System.out.println("Const.numberOfDepartments = " + Const.numberOfDepartments); //TODO: временная строчка. Нужно будет удалить
                    System.out.println("Const.numberOfEmployees = " + Const.numberOfEmployees); //TODO: временная строчка. Нужно будет удалить
                }

            }

            //Проверяем наличие таблицы departments. При необходимости создаем таблицу departments
            if (!isTableExists(statement, "departments")) {
                Display.message("Создание таблицы departments...");
                if (createTableDepartments(statement)) {
                    System.out.println("Таблица departments создана");
                } else {
                    return false;
                }
            }


            if (!isTableExists(statement, "employees")) {
                Display.message("Создание таблицы employees...");
                if (createTableEmployees(statement)) {
                    freeVacancies = Const.numberOfEmployees;
                    System.out.println("Таблица employees создана");
                } else {
                    return false;
                }
            } else {
                // получение количества записей действующих сотрудников в таблице. Присвоение значения freeVacancies
                int numberOfActiveEmployees = getNumberOfActiveEmployees(statement);
                if (numberOfActiveEmployees >= 0) {
                    freeVacancies = Const.numberOfEmployees - numberOfActiveEmployees;
                } else {
                    return false;
                }
                System.out.println("resultSet.getInt(1) = " + numberOfActiveEmployees); //TODO: временная строчка. Нужно будет удалить
            }

            System.out.println(freeVacancies); /// TODO: временная строчка. Нужно будет удалить

            statement.close();
            connection.close();

        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при выполнении SQL-запроса.");
        }

        return true;
    }


    //Методы работы с таблицей `info`
    private boolean createTableInfo(Statement s) {
        try {
            s.executeUpdate("CREATE TABLE `" + DB_NAME + "`.`info` (" +
                    "`organization_id` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "`organization_name` VARCHAR(45) NOT NULL," +
                    "`number_of_departments` INT(3) UNSIGNED," +
                    "`number_of_employees` INT(4) UNSIGNED," +
                    "PRIMARY KEY (`organization_id`))");
            return true;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при создании таблицы info.");
            return false;
        }
    }

    public static boolean saveInfo(Statement s, boolean newRecord) {
        try {
            if (newRecord) {
                s.executeUpdate("INSERT INTO `" + DB_NAME + "`.`info` " +
                        "(`organization_name`, `number_of_departments`,`number_of_employees`)" +
                        "VALUES (" +
                        "'" + Const.companyName + "'," +
                        Const.numberOfDepartments + "," +
                        Const.numberOfEmployees + ")");
            } else {
                s.executeUpdate("UPDATE info SET organization_name='" + Const.companyName + "', number_of_departments="
                        + Const.numberOfDepartments + ", number_of_employees=" + Const.numberOfEmployees
                        + " WHERE organization_id=1");
            }
            return true;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка сохранения данных в таблицу info.");
            return false;
        }
    }

    private boolean loadInfo(Statement s) {
        try {
            ResultSet rs = s.executeQuery("SELECT * FROM info");
            if (rs.next()) {
                Const.companyName = rs.getString("organization_name");
                Const.numberOfDepartments = rs.getInt("number_of_departments");
                Const.numberOfEmployees = rs.getInt("number_of_employees");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при загрузке данных из таблицы info.");
            return false;
        }
    }

    //Методы работы с таблицей `employees`

    private boolean createTableEmployees(Statement s) {
        try {
            s.executeUpdate("CREATE TABLE `" + DB_NAME + "`.`employees` (" +
                    "`employee_id` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "`first_name` VARCHAR(30)," +
                    "`second_name` VARCHAR(30)," +
                    "`middle_name` VARCHAR(30)," +
                    "`full_name` VARCHAR(90)," +
                    "`short_name` VARCHAR(35)," +
                    "`age` INT(3)," +
                    "`department_id` INT(3) UNSIGNED," +
                    "`salary` INT(10)," +
                    "`active` BOOLEAN NOT NULL," +
                    "PRIMARY KEY (`employee_id`))");
            return true;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка создания таблицы employees");
            return false;
        }
    }

    private int getNumberOfActiveEmployees(Statement s) {
        try {
            ResultSet resultSet = s.executeQuery("SELECT COUNT(*) FROM `db_employees`.`employees` WHERE `active`=1");
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка получения количества активных сотрудников");
            return -1;
        }
    }

    public boolean addEmployee(Employee newEmployee) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO `" + DB_NAME + "`.`employees`  " +
                    "(`first_name`,`second_name`,`middle_name`,`full_name`,`short_name`,`age`,`department_id`,`salary`,`active`)  " +
                    "VALUES" + "('" + newEmployee.getFirstName() + "', '"
                    + newEmployee.getSecondName() + "', '"
                    + newEmployee.getMiddleName() + "', '"
                    + newEmployee.getFullName() + "', '"
                    + newEmployee.getShortName() + "', "
                    + newEmployee.getAge() + " , "
                    + newEmployee.getDepartmentId() + " , "
                    + newEmployee.getSalary() + " , 1)";

            statement.executeUpdate(sql);

            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка добавления нового сотрудника");
            return false;
        }
    }

    public Employee getEmployeeById(int empId) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM `" + DB_NAME + "`.`employees` WHERE `employee_id`=" + empId + " AND `active`=1";
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            Employee employee = new Employee(rs.getString("second_name"),
                    rs.getString("first_name"),
                    rs.getString("middle_name"),
                    rs.getInt("age"),
                    rs.getByte("department_id"),
                    rs.getInt("salary"),
                    rs.getInt("employee_id"));
            statement.close();
            connection.close();
            return employee;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка получения сотрудника по id");
            return null;
        }

    }

    public boolean updateEmployee(Employee Employee) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String sql = "UPDATE `" + DB_NAME + "`.`employees` SET " +
                    "`first_name`='" + Employee.getFirstName() + "', " +
                    "`second_name`='" + Employee.getSecondName() + "', " +
                    "`middle_name`='" + Employee.getMiddleName() + "', " +
                    "`full_name`='" + Employee.getFullName() + "',  " +
                    "`short_name`='" + Employee.getShortName() + "',  " +
                    "`age`=" + Employee.getAge() + ", " +
                    "`department_id`=" + Employee.getDepartmentId() + ",  " +
                    "`salary`=" + Employee.getSalary() + " WHERE `employee_id`=" + Employee.getId();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
            return true;

        } catch (SQLException e) {
            handleSQLException(e, "Ошибка обновления данных сотрудника");
            return false;
        }
    }

    public boolean deleteEmployee(Employee Employee) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            String sql = "UPDATE `" + DB_NAME + "`.`employees` SET `active`=0 WHERE `employee_id`=" + Employee.getId();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка увольнения сотрудника");
            return false;
        }
    }

    public int indexingSalary(double percentOfIndexing, int departmentId) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            String sql = "UPDATE " + DB_NAME + ".employees SET salary=salary*" + (1 + percentOfIndexing / 100) +" WHERE active=1";
            if (departmentId!= 0)  {
                sql+=" AND department_id = "+ departmentId;
            }
            System.out.println("sql = " + sql); //TODO: удалить строку
            int numberOfRecords = statement.executeUpdate(sql);
            return numberOfRecords;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка подключения к таблице employees");
            return -1;
        }
    }

    public int indexingSalary(double percentOfIndexing) {
        return indexingSalary(percentOfIndexing, 0);
    }

    // Методы работы с таблицей `departments`

    private boolean createTableDepartments(Statement s) {
        try {
            s.executeUpdate("CREATE TABLE `" + DB_NAME + "`.`departments` (" +
                    "`department_id` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "`department_name` VARCHAR(25)," +
                    "PRIMARY KEY (`department_id`))");
            for (int i = 1; i <= Const.numberOfDepartments; i++) {
                s.executeUpdate("INSERT INTO `" + DB_NAME + "`.`departments` (`department_name`) " +
                        "VALUES ('Department ID=" + i + "')");
            }
            return true;
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка создания таблицы departments");
            return false;
        }

    }

    private static boolean isTableExists(Statement s, String tableName) {
        try {
            ResultSet resultSet = s.executeQuery("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE " +
                    "TABLE_SCHEMA = '" + DB_NAME + "' AND TABLE_NAME = '" + tableName + "'");
            resultSet.next();
            return (resultSet.getInt(1) != 0);
        } catch (SQLException e) {
            handleSQLException(e, "Ошибка при выполнении SQL-запроса.");
            return false;
        }
    }

    private static void handleSQLException(SQLException e, String message) {
        System.out.println(message);
        System.out.println("Код ошибки: " + e.getErrorCode());
        e.printStackTrace();
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getFreeVacancies() {
        return freeVacancies;
    }

    public boolean hasVacancies() {
        return freeVacancies > 0;
    }
}
