import java.sql.*;

/**
 * Created by 6 on 13.05.2019.
 */
public class ConnectionDB {
    public static void main(String[] args) throws SQLException {
        Connection connection=DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");

       Statement statement= connection.createStatement();
        ResultSet resultSet=statement.executeQuery("select * from user");
        while (resultSet.next()){
            System.out.println(resultSet.getLong(1));
            System.out.println(resultSet.getString("login"));
        }


    }
}
