import java.sql.*;

public class DbEmployees {
    public static final String DB_NAME = "db_employees";
    private final String url;
    private final String user;
    private final String password;
    private int freeVacancies;
    private Connection connection;

    public static String organizationName;
    public static int numberOfDepartments;
    public static int numberOfEmployees;

    public DbEmployees(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }


    public void onStart(int limitEmployees) {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE " +
                    "TABLE_SCHEMA = '" + DB_NAME + "' AND TABLE_NAME = 'info'");
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                //Создаем таблицу info и заполняем исходными данными
                if (!(createTableInfo(statement) && saveInfo(statement, Input.setup(), true))) {
                    return;
                }
            }
            resultSet = statement.executeQuery("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE " +
                    "TABLE_SCHEMA = '" + DB_NAME + "' AND TABLE_NAME = 'employees'");
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                createTableEmployees(statement);
                freeVacancies = numberOfEmployees;
                System.out.println("Создаем таблицу employees");
            } else {
                resultSet = statement.executeQuery("SELECT COUNT(*) FROM `db_employees`.`employees` WHERE `active`=1");
                resultSet.next();
                freeVacancies = numberOfEmployees - resultSet.getInt(1);

                // получение количества записей действующих сотрудников в таблице. Присвоение значения freeVacancies
            }

            System.out.println(freeVacancies); /// временная строчка. Нужно будет удалить

            resultSet = statement.executeQuery(" SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE " +
                    "TABLE_SCHEMA = '" + DB_NAME + "' AND TABLE_NAME = 'departments'");
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                createTableDepartments(statement);
                System.out.println("Создаем таблицу departments");
            }


        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении SQL-запроса:");
            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
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
            System.out.println("Ошибка создания таблицы info");
            System.out.println(e.getErrorCode());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean saveInfo(Statement s, String[] info, boolean newRecord) {
        try {
            if (newRecord) {
                s.executeUpdate("INSERT INTO `" + DB_NAME + "`.`info` " +
                        "(`organization_name`, `number_of_departments`,`number_of_employees`)" +
                        "VALUES (" +
                        "'" + info[0] + "'," +
                        Integer.parseInt(info[1]) + "," +
                        Integer.parseInt(info[2]) + ")");
                organizationName = info[0];
                numberOfDepartments = Integer.parseInt(info[1]);
                numberOfEmployees = Integer.parseInt(info[2]);
                return true;
            } else {
                s.executeUpdate("UPDATE info SET organization_name='" + info[0] + "', number_of_departments="
                        + Integer.parseInt(info[1]) + ", number_of_employees=" + Integer.parseInt(info[2])
                        + " WHERE organization_id=1");
                organizationName = info[0];
                numberOfDepartments = Integer.parseInt(info[1]);
                numberOfEmployees = Integer.parseInt(info[2]);
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Ошибка сохранения данных в таблицу info");
            System.out.println(e.getErrorCode());
            e.printStackTrace();
            return false;
        }
    }

    private void createTableEmployees(Statement s) {
        try {
            s.executeUpdate("CREATE TABLE `" + DB_NAME + "`.`employees` (" +
                    "`employee_id` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "`first_name` VARCHAR(30)," +
                    "`second_name` VARCHAR(30)," +
                    "`sur_name` VARCHAR(30)," +
                    "`full_name` VARCHAR(90)," +
                    "`short_name` VARCHAR(35)," +
                    "`age` INT(3)," +
                    "`salary` INT(10)," +
                    "`active` BOOLEAN NOT NULL," +
                    "PRIMARY KEY (`employee_id`))");

        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы employees");
            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
    }

    private void createTableDepartments(Statement s) {
        try {
            s.executeUpdate("CREATE TABLE `" + DB_NAME + "`.`departments` (" +
                    "`department_id` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "`department_name` VARCHAR(25)," +
                    "PRIMARY KEY (`department_id`))");
            for (int i = 1; i <= Input.NUMBER_OF_DEPARTMENTS; i++) {
                s.executeUpdate("INSERT INTO `" + DB_NAME + "`.`departments` (`department_name`) " +
                        "VALUES ('Department ID=" + i + "')");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка создания базы department");
            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }

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

    public static String getOrganizationName() {
        return organizationName;
    }

    public static int getNumberOfDepartments() {
        return numberOfDepartments;
    }

    public static int getNumberOfEmployees() {
        return numberOfEmployees;
    }
}
