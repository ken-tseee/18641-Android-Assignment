package model;

/**
 * Author: Junjian Xie
 * Email: junjianx@andrew.cmu.edu
 * Date: 15/11/10
 */
public class Student {
    final int QUIZ_NUM = 5;

    private int sID;
    private int[] scores = new int[5];

    /**
     * Constructors
     */
    public Student() {
        this.scores = new int[] {0,0,0,0,0};
    }

    public Student(int sID, int[] scores) {
        this.sID = sID;
        this.scores = new int[] {0,0,0,0,0};
    }

    /**
     * Getters and setters.
     * @return
     */
    public int getsID() {
        return sID;
    }

    public void setsID(int sID) {
        this.sID = sID;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        int scoresSize = scores.length;
        if (scoresSize > QUIZ_NUM) {
            scoresSize = QUIZ_NUM;
        }
        for (int i=0; i<scoresSize; ++i) {
            this.scores[i] = scores[i];
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(sID);
        sb.append("\t");
        for (int i=0; i<scores.length; ++i) {
            sb.append(scores[i]);
            sb.append("\t");
        }
        return sb.toString();
    }

    /**
     * Print out the data.
     */
    public void print() {
        System.out.println();
    }
}
