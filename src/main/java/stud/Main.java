package stud;

import stud.dao.LessonDao;
import stud.dao.StudentDao;
import stud.dao.Util;
import stud.dao.VisitDao;
import stud.model.Lesson;
import stud.model.Student;
import stud.model.Visits;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/cache", "sa", "")) {

            Util.dropTables(connection);
            Util.createTables(connection);


            StudentDao studentsDao = new StudentDao(connection);
            Student student1 = new Student(0, "student1", "student1");
            Student student2 = new Student(0, "student2", "student2");
            Student student3 = new Student(0, "student3", "student3");

            System.out.println("creating students");
            studentsDao.insert(student1);
            studentsDao.insert(student2);
            studentsDao.insert(student3);

            System.out.println(student1);
            System.out.println(student2);
            System.out.println(student3);

            System.out.println("get student1");
            System.out.println(studentsDao.getByPK(student1.getId()));

            System.out.println("get students");
            studentsDao.getAll().forEach(System.out::println);

            System.out.println("update students");
            student1.setFirstName("STUDENT1");
            student1.setLastName("STUDENT1");
            studentsDao.update(student1);
            System.out.println(studentsDao.getByPK(student1.getId()));

            System.out.println("delete student");
            studentsDao.delete(student3);
            for (Student student : studentsDao.getAll()) {
                System.out.println(student);
            }


            LessonDao lessonsDao = new LessonDao(connection);


            Lesson firstLesson = new Lesson(0, "lesson1", Timestamp.valueOf("2019-05-01 12:00:00"));
            Lesson secondLesson = new Lesson(0, "lesson2", Timestamp.valueOf("2019-05-02 13:00:00"));
            Lesson thirdLesson = new Lesson(0, "lesson3", Timestamp.valueOf("2017-05-03 14:00:00"));

            System.out.println("create lesson");
            lessonsDao.insert(firstLesson);
            lessonsDao.insert(secondLesson);
            lessonsDao.insert(thirdLesson);

            System.out.println("get lesson");
            System.out.println(lessonsDao.getByPK(firstLesson.getId()));


            System.out.println("get lessons");
            lessonsDao.getAll().forEach(System.out::println);

            System.out.println("update lesson");
            secondLesson.setTitle("CHANGEDLESSON");
            secondLesson.setDate(Timestamp.valueOf("2019-05-10 20:35:00"));
            lessonsDao.update(secondLesson);
            System.out.println(lessonsDao.getByPK(secondLesson.getId()));

            System.out.println("delete lesson");
            lessonsDao.delete(thirdLesson);
            for (Lesson lesson : lessonsDao.getAll()) {
                System.out.println(lesson);
            }


            VisitDao studentVisitsDao = new VisitDao(connection);


            Visits visit1 = new Visits(0, 2, 1);
            Visits visit2 = new Visits(0, 1, 2);
            Visits visit3 = new Visits(0, 1, 1);
            Visits visit4 = new Visits(0, 2, 2);

            System.out.println("create visits");
            studentVisitsDao.insert(visit1);
            studentVisitsDao.insert(visit2);
            studentVisitsDao.insert(visit3);
            studentVisitsDao.insert(visit4);

            System.out.println("get visit");
            System.out.println(studentVisitsDao.getByPK(visit1.getId()));

            System.out.println("get visits");
            for (Visits visits1 : studentVisitsDao.getAll()) {
                System.out.println(visits1);
            }


            visit3.setStudentId(3);
            visit3.setLessonId(1);
            studentVisitsDao.update(visit3);
            System.out.println(studentVisitsDao.getByPK(visit3.getId()));


            studentVisitsDao.delete(visit3);
            for (Visits visits : studentVisitsDao.getAll()) {
                System.out.println(visits);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}