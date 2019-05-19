package stud.dao;

import stud.model.Lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {

        private static final String INSERT ="INSERT INTO Lessons(title, date) VALUES (?, ?);";

        private static final String UPDATE ="UPDATE Lessons SET title=?, date=? WHERE id=?;";

        private static final String DELETE = "DELETE FROM Lessons WHERE id=?";

        private static final String GET_BY_PK ="SELECT id, title, date FROM Lessons WHERE id=?;";

        private static final String GET_ALL = "SELECT id, title, date FROM Lessons;";

        private Connection connection;

        public LessonDao(Connection connection) {
            this.connection = connection;
        }

        public void insert(Lesson lesson) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, lesson.getTitle());
                statement.setTimestamp(2, lesson.getDate());

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new RuntimeException("creating failed");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        lesson.setId(generatedKeys.getInt(1));
                    } else {
                        throw new RuntimeException();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
    public void delete(Lesson lesson) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {

            statement.setInt(1, lesson.getId());

            int affectedRows = statement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

        public void update(Lesson lesson) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {

                statement.setString(1, lesson.getTitle());
                statement.setTimestamp(2, lesson.getDate());
                statement.setInt(3, lesson.getId());

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new RuntimeException("updating failed");
                }

            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }



        public Lesson getByPK(int primaryKey) {
            try (PreparedStatement statement = connection.prepareStatement(GET_BY_PK)) {
                statement.setInt(1, primaryKey);

                try(ResultSet lessonFromDB = statement.executeQuery()){
                    Lesson lesson = new Lesson();
                    lessonFromDB.next();

                    lesson.setId(lessonFromDB.getInt("id"));
                    lesson.setTitle(lessonFromDB.getString("title"));
                    lesson.setDate(lessonFromDB.getTimestamp("date"));

                    return lesson;
                }
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }

        public List<Lesson> getAll() {
            try (PreparedStatement statement = connection.prepareStatement(GET_ALL)) {
                try(ResultSet lessonsFromDB = statement.executeQuery()){

                    List<Lesson> lessonList = new ArrayList<>();

                    while (lessonsFromDB.next()) {
                        Lesson lesson = new Lesson();
                        lesson.setId(lessonsFromDB.getInt("id"));
                        lesson.setTitle(lessonsFromDB.getString("title"));
                        lesson.setDate(lessonsFromDB.getTimestamp("date"));
                        lessonList.add(lesson);
                    }

                    return lessonList;
                }
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
    }