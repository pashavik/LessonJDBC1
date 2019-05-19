package stud.dao;

import stud.model.Student;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentDao {
    private Connection connection;
    private static final String SQL_INSERT ="INSERT INTO Students(first_name, last_name) VALUES (?, ?);";

    private static final String SQL_UPDATE ="UPDATE Students SET first_name=?, last_name=? WHERE id=?;";
    //?
    private static final String SQL_GET_BY_PK = "SELECT id, first_name, last_name FROM Students WHERE id=?;";

    private static final String SQL_GET_ALL ="SELECT id, first_name, last_name FROM Students;";

    private static final String SQL_DELETE ="DELETE FROM Students WHERE id=?";





    public StudentDao(Connection connection) {
        this.connection = connection;
    }

    public void insert(Student student) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());

            int affectedRows = statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    student.setId(generatedKeys.getInt(1));
                } else {
                    throw new RuntimeException();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void update(Student student) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {

            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("updating failed");
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<Student> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL)) {
            try(ResultSet studentsFromDB = statement.executeQuery()){

                List<Student> studentList = new ArrayList<>();

                while (studentsFromDB.next()) {
                    Student student = new Student();
                    student.setId(studentsFromDB.getInt("id"));
                    student.setFirstName(studentsFromDB.getString("first_name"));
                    student.setLastName(studentsFromDB.getString("last_name"));
                    studentList.add(student);
                }

                return studentList;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    public void delete(Student student) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {

            statement.setInt(1, student.getId());

            int affectedRows = statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public Student getByPK(int primaryKey) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_PK)) {
            statement.setInt(1, primaryKey);

            try(ResultSet studentFromDB = statement.executeQuery()){
                Student student = new Student();
                studentFromDB.next();

                student.setId(studentFromDB.getInt("id"));
                student.setFirstName(studentFromDB.getString("first_name"));
                student.setLastName(studentFromDB.getString("last_name"));

                return student;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


}