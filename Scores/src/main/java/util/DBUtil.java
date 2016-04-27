package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBUtil extends SQLiteOpenHelper {
    public static final int data_base_version = 1;

    /**
     * Constructor
     * @param context
     */
    public DBUtil(Context context) {
        super(context, SQLCmd.DATABASE_NAME, null, data_base_version);
        Log.d("DBUtil", "Database is successfully created!");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLCmd.CREATE_TB_STUDENT);
        db.execSQL(SQLCmd.CREATE_TB_QUIZ_RECORD);
        Log.d("DBUtil", "Tables are successfully created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Drop tables.
     */
    public void dropTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS" + SQLCmd.TB_QUIZ_RECORD);
        db.execSQL("DROP TABLE IF EXISTS" + SQLCmd.TB_STUDENT);
    }

    /**
     * Add the student into the table.
     * @param dbUtil
     * @param sID
     */
    public void addStudentInfo(DBUtil dbUtil, int sID) {
        SQLiteDatabase db = dbUtil.getWritableDatabase();
        ContentValues cv = new ContentValues();

        onCreate(db);

        cv.put(SQLCmd.STUDENT_ID, sID);

        db.insertWithOnConflict(SQLCmd.TB_STUDENT, null, cv, SQLiteDatabase.CONFLICT_IGNORE);

        Log.d("DBUtil", "Student information is successfully added!");
    }

    /**
     * Add the scores in the table.
     * @param dbUtil
     * @param sID
     * @param scores
     */
    public void addQuizRecord(DBUtil dbUtil, int sID, int[] scores) {
        if (scores.length != 5) {
            return;
        }

        SQLiteDatabase db = dbUtil.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SQLCmd.FOREIGN_SID, sID);
        cv.put(SQLCmd.Q1, scores[0]);
        cv.put(SQLCmd.Q2, scores[1]);
        cv.put(SQLCmd.Q3, scores[2]);
        cv.put(SQLCmd.Q4, scores[3]);
        cv.put(SQLCmd.Q5, scores[4]);

        db.insert(SQLCmd.TB_QUIZ_RECORD, null, cv);

        Log.d("DBUtil", "Scores are successfully added!");
    }

    /**
     * Get the student information.
     * @param dbUtil
     * @return
     */
    public String getAllStudentInfo(DBUtil dbUtil) {
        StringBuilder sb = new StringBuilder();
        SQLiteDatabase db = dbUtil.getReadableDatabase();;
        String[] columns = {SQLCmd.STUDENT_ID};

        Cursor cr = db.query(SQLCmd.TB_STUDENT, columns, null, null, null, null, null, null);
        cr.moveToFirst();
        while (!cr.isAfterLast()) {
            sb.append("SID: " + cr.getString(cr.getColumnIndexOrThrow(SQLCmd.STUDENT_ID)) + "\n");
            cr.moveToNext();
        }
        cr.close();

        return sb.toString();
    }

    public String getAllScores(DBUtil dbUtil) {
        StringBuilder sb = new StringBuilder();
        SQLiteDatabase db = dbUtil.getReadableDatabase();
        String[] columns = {SQLCmd.FOREIGN_SID, SQLCmd.Q1, SQLCmd.Q2, SQLCmd.Q3, SQLCmd.Q4, SQLCmd.Q5};

        Cursor cr = db.query(SQLCmd.TB_QUIZ_RECORD, columns, null, null, null, null, null, null);
        cr.moveToFirst();
        while (!cr.isAfterLast()) {
            sb.append("SID: " + cr.getString(cr.getColumnIndexOrThrow(SQLCmd.FOREIGN_SID)) + " ");
            sb.append("Q1: " + cr.getString(cr.getColumnIndexOrThrow(SQLCmd.Q1)) + " ");
            sb.append("Q2: " + cr.getString(cr.getColumnIndexOrThrow(SQLCmd.Q2)) + " ");
            sb.append("Q3: " + cr.getString(cr.getColumnIndexOrThrow(SQLCmd.Q3)) + " ");
            sb.append("Q4: " + cr.getString(cr.getColumnIndexOrThrow(SQLCmd.Q4)) + " ");
            sb.append("Q5: " + cr.getString(cr.getColumnIndexOrThrow(SQLCmd.Q5)) + " ");
            sb.append("\n");
            cr.moveToNext();
        }
        cr.close();

        return sb.toString();
    }
}
