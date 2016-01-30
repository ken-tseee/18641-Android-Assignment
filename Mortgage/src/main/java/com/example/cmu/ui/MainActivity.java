package com.example.cmu.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import model.MortgageCal;
import util.DBUtil;

public class MainActivity extends AppCompatActivity {
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * Get data.
         */
        final EditText editPurchasePrice = (EditText) findViewById(R.id.editPurchasePrice);
        final EditText editDownPayment = (EditText) findViewById(R.id.editDownPayment);
        final EditText editMortgageTerm = (EditText) findViewById(R.id.editMortgageTerm);
        final EditText editInterestRate = (EditText) findViewById(R.id.editInterestRate);
        final EditText editPropertyTax = (EditText) findViewById(R.id.editPropertyTax);
        final EditText editPropertyInsurance = (EditText) findViewById(R.id.editPropertyInsurance);
        final EditText editZipCode = (EditText) findViewById(R.id.editZipCode);
        /**
         * Button.
         */
        Button buttonCalculate = (Button) findViewById(R.id.buttonCalculate);
        /**
         * Spinner.
         */
        final Spinner spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        final Spinner spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        /**
         * Output.
         */
        final EditText editMonthlyPayment = (EditText) findViewById(R.id.editMonthlyPayment);
        final EditText editTotalPayment = (EditText) findViewById(R.id.editTotalPayment);
        final EditText editPayoffDate = (EditText) findViewById(R.id.editPayoffDate);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MortgageCal mc = new MortgageCal();
                DBUtil db = new DBUtil(ctx);

                try{
                    double purchasePrice = Double.parseDouble(editPurchasePrice.getText().toString());
                    double downPayment = Double.parseDouble(editDownPayment.getText().toString());
                    int mortgageTerm = Integer.parseInt(editMortgageTerm.getText().toString());
                    double interestRate = Double.parseDouble(editInterestRate.getText().toString());
                    int propertyTax = Integer.parseInt(editPropertyTax.getText().toString());
                    int propertyInsurance = Integer.parseInt(editPropertyInsurance.getText().toString());
                    int zipCode = Integer.parseInt(editZipCode.getText().toString());
                    int selectedMonth = mc.convertStrMonToIntMon(spinnerMonth.getSelectedItem().toString());
                    int selectedYear = Integer.parseInt(spinnerYear.getSelectedItem().toString());

                    double realAmount = purchasePrice * (100 - downPayment) / 100;

                    String payoffDate = mc.getPayoffDate(selectedMonth, selectedYear, mortgageTerm);
                    db.addPayoffDate(db, selectedMonth, selectedYear, mortgageTerm, payoffDate);

                    String monthlyPayment = mc.getMonthlyPayment(realAmount, mortgageTerm,
                            interestRate, propertyTax, propertyInsurance);
                    String totalPayment = mc.getTotalPayment(realAmount, mortgageTerm, interestRate,
                            propertyTax, propertyInsurance);
                    db.addPayment(db, realAmount, mortgageTerm, interestRate,propertyTax,
                            propertyInsurance, monthlyPayment, totalPayment);

                    editMonthlyPayment.setText(monthlyPayment);
                    editTotalPayment.setText(totalPayment);
                    editPayoffDate.setText(payoffDate);

                } catch(Exception e) {
                    editMonthlyPayment.setText("Invalid input or blank input");
                    editTotalPayment.setText("Please check the input correctness");
                    editPayoffDate.setText("");
                    Log.e("Click Calculate","In valid or blank input");
                    Log.e("Click Calculate", e.toString());
                }
                Log.i("Click Calculate", "Calculation Done here");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
