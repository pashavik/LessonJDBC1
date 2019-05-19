package stud.dao;

import stud.model.StudentVisits;
import stud.model.Visits;

import java.sql.*;
import java.util.*;

public class VisitDao {

    private static final String SQL_INSERT ="INSERT INTO Student_visits(student_id, lesson_id) VALUES (?, ?);";

    private static final String SQL_UPDATE = "UPDATE Student_visits SET student_id=?, lesson_id=? WHERE id=?;";

    private static final String SQL_DELETE = "DELETE FROM Student_visits WHERE id=?";

    private static final String SQL_GET_BY_PK ="SELECT id, student_id, lesson_id FROM Student_visits WHERE id=?;";

    private static final String SQL_GET_ALL ="SELECT id, student_id, lesson_id FROM Student_visits;";

    private static String SQL_GET_ALL_JOIN ="SELECT l.\"DATE\", l.TITLE, CONCAT(s.FIRST_NAME, ' ', s.LAST_NAME) AS NAME FROM STUDENT_VISITS sv\n" +
                    "JOIN STUDENTS s ON s.ID = sv.STUDENT_ID\n" +
                    "JOIN LESSONS l ON l.ID = sv.LESSON_ID;";

    private Connection connection;

    public VisitDao(Connection connection) {
        this.connection = connection;
    }

    public void insert(Visits visits) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, visits.getStudentId());
            statement.setInt(2, visits.getLessonId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("creating failed");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    visits.setId(generatedKeys.getInt(1));
                } else {
                    throw new RuntimeException("");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void update(Visits visits) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {

            statement.setInt(1, visits.getStudentId());
            statement.setInt(2, visits.getLessonId());
            statement.setInt(3, visits.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("updating failed");
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void delete(Visits visits) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {

            statement.setInt(1, visits.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("deleting failed");
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public Visits getByPK(int primaryKey) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_BY_PK)) {
            statement.setInt(1, primaryKey);

            try(ResultSet studentVisitFromDB = statement.executeQuery()){
                Visits visits = new Visits();
                studentVisitFromDB.next();

                visits.setId(studentVisitFromDB.getInt("id"));
                visits.setStudentId(studentVisitFromDB.getInt("student_id"));
                visits.setLessonId(studentVisitFromDB.getInt("lesson_id"));

                return visits;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Getting studentVisit failed, sql exception: " + e.getMessage());
        }
    }

    public List<Visits> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL)) {
            try(ResultSet studentsVisitFromDB = statement.executeQuery()){

                List<Visits> visitsList = new ArrayList<>();

                while (studentsVisitFromDB.next()) {
                    Visits visits = new Visits();
                    visits.setId(studentsVisitFromDB.getInt("id"));
                    visits.setStudentId(studentsVisitFromDB.getInt("student_id"));
                    visits.setLessonId(studentsVisitFromDB.getInt("lesson_id"));
                    visitsList.add(visits);
                }

                return visitsList;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<StudentVisits> getAllJoin() {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_JOIN)) {
            try(ResultSet studentsVisitFromDB = statement.executeQuery()){

                List<StudentVisits> studentVisitsList = new ArrayList<>();

                while (studentsVisitFromDB.next()) {
                    StudentVisits studentVisits = new StudentVisits();
                    studentVisits.setDate(studentsVisitFromDB.getTimestamp("date"));
                    studentVisits.setTitle(studentsVisitFromDB.getString("title"));
                    studentVisits.setName(studentsVisitFromDB.getString("name"));
                    studentVisitsList.add(studentVisits);
                }

                return studentVisitsList;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}