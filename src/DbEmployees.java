import java.sql.*;

public class DbEmployees {
    private final String url;
    private final String user;
    private final String password;
    private int freeVacancies;
    Connection connection;

    public DbEmployees(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

//    public Connection connection() {
//
//    }

    public void onStart(int limitEmployees) {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE " +
                    "TABLE_SCHEMA = 'db_employees' AND TABLE_NAME = 'employees'");
            resultSet.next();
            if (resultSet.getInt(1)==0) {
                //создание таблицы employees
                System.out.println("Создаем таблицу employees");
            } else {
                // получение количества записей действующих сотрдуников в таблице. Присвоение значения freeVacancies
            }
            resultSet = statement.executeQuery(" SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE " +
                    "TABLE_SCHEMA = 'db_employees' AND TABLE_NAME = 'departments'");
            resultSet.next();
            if (resultSet.getInt(1)==0) {
                createTableDepartments(statement);
                System.out.println("Создаем таблицу departments");
            }


        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении SQL-запроса:");
            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
    }

    private void createTableDepartments(Statement s) {
        try {
            s.executeUpdate("CREATE TABLE `db_employees`.`departments` (" +
                    "`department_id` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "`department_name` VARCHAR(25)," +
                    "PRIMARY KEY (`department_id`))");
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
}
