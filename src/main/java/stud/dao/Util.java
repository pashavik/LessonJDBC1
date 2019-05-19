package stud.dao;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {

        private static final String DROP ="DROP TABLE IF EXISTS Student_visits; DROP TABLE IF EXISTS Students; DROP TABLE IF EXISTS Lessons;";

        private static final String CREATE ="CREATE TABLE IF NOT EXISTS Students(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                        "  first_name VARCHAR(50) NOT NULL, last_name VARCHAR(50) NOT NULL);\n" +
                        "\n" +
                        "CREATE TABLE IF NOT EXISTS Lessons(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                        "  title VARCHAR(100) NOT NULL, date TIMESTAMP);\n" +
                        "\n" +
                        "CREATE TABLE IF NOT EXISTS Student_visits( id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                        "  student_id INT, lesson_id INT, CONSTRAINT FK_StudentsStudentsVisit FOREIGN KEY (student_id) REFERENCES Students(id),\n" +
                        "  CONSTRAINT FK_LessonsStudentsVisit FOREIGN KEY (lesson_id) REFERENCES Lessons(id)\n" +
                        ");";


    public static void dropTables(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(DROP);
        }
    }
        public static void createTables(Connection connection) throws SQLException {
            try (Statement statement = connection.createStatement()) {
                statement.execute(CREATE);
            }
        }


    }

