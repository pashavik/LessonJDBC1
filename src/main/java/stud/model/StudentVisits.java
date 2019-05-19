package stud.model;

import java.sql.Timestamp;

public class StudentVisits {

        private Timestamp date;
        private String title;
        private String name;

        public StudentVisits() {
        }

        public StudentVisits(Timestamp date, String title, String name) {
            this.date = date;
            this.title = title;
            this.name = name;
        }

        public Timestamp getDate() {
            return date;
        }

        public void setDate(Timestamp date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "StudentVisits{" +
                    ", date=" + date +
                    ", title='" + title + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

    }

