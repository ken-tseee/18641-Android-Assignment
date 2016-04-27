package model;

import java.util.ArrayList;
import java.util.List;

public class ScoresHolder {
    final int QUIZ_NUM = 5;
    List<Student> studentList;

    /**
     * Constructor
     */
    public ScoresHolder() {
        this.studentList = new ArrayList<Student>();
    }

    /**
     * Add the instances of students into a list.
     * @param student
     */
    public void addStudent(Student student) {
        if (student != null) {
            int newID = student.getsID();
            boolean isNew = true;
            /**
             * Detect whether the student has been added into the list.
             */
            for (int i=0; i<studentList.size(); ++i) {
                if (studentList.get(i).getsID() == newID) {
                    isNew = false;
                }
            }
            /**
             * At most 40 students in the list.
             */
            if (isNew && (studentList.size() < 40)) {
                studentList.add(student);
            }
        }
    }

    /**
     * Parse the scores to String type and add them into a list.
     * @return
     */
    public List<String> getAllScores() {
        final String s = "STUD         Q1         Q2         Q3         Q4         Q5";
        List<String> result = new ArrayList<String>();
        result.add(s);
        for (int i=0; i<studentList.size(); ++i) {
            StringBuilder sb = new StringBuilder();
            Student st = studentList.get(i);
            sb.append(st.getsID());
            sb.append("          ");
            int[] sc = st.getScores();
            for (int j=0; j<sc.length; ++j) {
                sb.append(sc[j]);
                sb.append("          ");
            }
            result.add(sb.toString());
        }
        return result;
    }

    /**
     * Get the lowest score in each quiz.
     * @return
     */
    public int[] getLow() {
        int[] lowScores = new int[QUIZ_NUM];
        for (int i=0; i<lowScores.length; ++i) {
            lowScores[i] = 100;
        }

        for (int i=0; i<studentList.size(); ++i) {
            int[] stSc = studentList.get(i).getScores();
            for (int j=0; j<QUIZ_NUM; ++j) {
                int temp = stSc[j];
                if (temp > 100 || temp < 0) {
                    continue;
                }
                if (temp < lowScores[j]) {
                    lowScores[j] = temp;
                }
            }
        }
        return lowScores;
    }

    /**
     * Get the highest score in each quiz.
     * @return
     */
    public int[] getHigh() {
        int[] highScores = new int[QUIZ_NUM];
        for (int i=0; i<highScores.length; ++i) {
            highScores[i] = 100;
        }

        for (int i=0; i<studentList.size(); ++i) {
            int[] stSc = studentList.get(i).getScores();
            for (int j=0; j<QUIZ_NUM; ++j) {
                int temp = stSc[j];
                if (temp > 100 || temp < 0) {
                    continue;
                }
                if (temp < highScores[j]) {
                    highScores[j] = temp;
                }
            }
        }
        return highScores;
    }

    /**
     * Calculate the average score in each quiz.
     * @return
     */
    public double[] getAvg() {
        double[] avgScores = new double[QUIZ_NUM];
        for (int i=0; i<avgScores.length; ++i) {
            avgScores[i] = 0;
        }
        if (studentList.size() == 0) {
            return avgScores;
        }

        for (int i=0; i<studentList.size(); ++i) {
            int[] stSc = studentList.get(i).getScores();
            for (int j=0; j<QUIZ_NUM; ++j) {
                int temp = stSc[j];
                if (temp > 100 || temp < 0) {
                    continue;
                }
                avgScores[j] += temp;
            }
        }

        for (int i=0; i<avgScores.length; ++i) {
            avgScores[i] = avgScores[i] / studentList.size();
        }
        return avgScores;
    }
}
