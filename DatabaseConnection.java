import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found!", e);
        }
    }
}

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;

// public class DatabaseConnection {
// private static final String USERNAME = "root";

// private static final String PASSWORD = "123456789eE$";

// private static final String URL =
// "jdbc:mysql://localhost:3306/financial_governance";

// public static Connection getConnection() throws SQLException {
// try{
// Class.forName("com.mysql.cj.jdbc.Driver");
// return DriverManager.getConnection(URL, USERNAME, PASSWORD);
// } catch(ClassNotFoundException e){
// throw new SQLException("MySQL Driver not found! Check your /lib folder
// setup.", e);
// }
// }
// }
