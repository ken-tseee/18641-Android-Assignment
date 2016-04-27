package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public class DBUtil extends SQLiteOpenHelper {
    public static final int data_base_version = 1;

    public DBUtil(Context context) {
        super(context, SQLCmd.DATABASE_NAME, null, data_base_version);
        Log.d("DBUtil", "Database is successfully created!");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLCmd.CREATE_PAYOFF_DATE);
        db.execSQL(SQLCmd.CREATE_PAYMENT);
        Log.d("DBUtil", "Tables are successfully created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Add data of payoff date into database.
     * @param dbUtil
     * @param startMonth
     * @param startYear
     * @param totalYear
     * @param payoffDate
     */
    public void addPayoffDate(DBUtil dbUtil, int startMonth, int startYear,
                              int totalYear, String payoffDate) {
        SQLiteDatabase db = dbUtil.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("start_month", startMonth);
        cv.put("start_Year", startYear);
        cv.put("total_year", totalYear);
        cv.put("payoff_date", payoffDate);

        db.insert(SQLCmd.TB_PAYOFF_DATE, null, cv);

        Log.d("DBUtil", "Payoff Date is successfully added!");
    }

    /**
     * Add data of payment into database.
     * @param dbUtil
     * @param amount
     * @param years
     * @param rate
     * @param propertyTax
     * @param propertyInsurance
     * @param monthlyPayment
     * @param totalPayment
     */
    public void addPayment(DBUtil dbUtil, double amount, int years, double rate, int propertyTax,
                           int propertyInsurance, String monthlyPayment, String totalPayment) {
        SQLiteDatabase db = dbUtil.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SQLCmd.AMOUNT, amount);
        cv.put(SQLCmd.YEARS, years);
        cv.put(SQLCmd.RATE, rate);
        cv.put(SQLCmd.PROPERTY_TAX, propertyTax);
        cv.put(SQLCmd.PROPERTY_INSURANCE, propertyInsurance);
        cv.put(SQLCmd.MONTHLY_PAYMENT, monthlyPayment);
        cv.put(SQLCmd.TOTAL_PAYMENT, totalPayment);

        db.insert(SQLCmd.TB_PAYMENT, null, cv);

        Log.d("DBUtil", "Payment is successfully added!");
    }
}
