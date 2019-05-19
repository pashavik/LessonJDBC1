package classWork;

import java.sql.*;

/**
 * Created by 6 on 13.05.2019.
 */
public class ConnectionDB {
    public static void main(String[] args) throws SQLException {
        Connection connection=DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");

       Statement statement= connection.createStatement();


//        ResultSet resultSet=statement.executeQuery("select * from user");
//        while (resultSet.next()){
//            System.out.println(resultSet.getLong(1));
//            System.out.println(resultSet.getString("login"));
//        }

//        statement.addBatch("update user set blocked='1' where login='first'");
//        statement.addBatch("update user set org_id='1' where login='third'");
//        statement.addBatch("delete from user  where login='second'");
//        statement.executeBatch();

        connection.setAutoCommit(false);
        Savepoint savepoint=connection.setSavepoint();
        try(Statement statement1=connection.createStatement()){
            statement.execute("update account set balance=balance+100 where id=1");
            statement.execute("update account set balance=balance-100 where id=2");

            connection.commit();
        } catch (SQLException e){
            connection.rollback(savepoint);
            throw new RuntimeException();
            
        }



    }
}
