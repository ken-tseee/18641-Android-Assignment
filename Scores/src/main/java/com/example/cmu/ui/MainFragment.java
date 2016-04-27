package com.example.cmu.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cmu.scores.R;

import java.util.List;

import model.ScoresHolder;
import model.Student;
import util.DBUtil;

public class MainFragment extends Fragment implements View.OnClickListener {
    final static int QUIZ_NUM = 5;
    final static String LOG_TAG = MainFragment.class.getSimpleName();

    private ScoresHolder scoresHolder;

    public MainFragment() {
        this.scoresHolder = new ScoresHolder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button button = (Button) getActivity().findViewById(R.id.button_add_score);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_score:
                Log.d(LOG_TAG, "Click the button");
                Student st = updateListScores();
                if (st == null) {
                    break;
                }
                updateDB(st);
                updateStatResult();
                resetInputValues();
                break;
        }
    }

    /**
     * Reset the input.
     */
    private void resetInputValues() {
        View view = getView();

        EditText editID = (EditText) view.findViewById(R.id.edit_id);
        EditText editQ1 = (EditText) view.findViewById(R.id.edit_q1);
        EditText editQ2 = (EditText) view.findViewById(R.id.edit_q2);
        EditText editQ3 = (EditText) view.findViewById(R.id.edit_q3);
        EditText editQ4 = (EditText) view.findViewById(R.id.edit_q4);
        EditText editQ5 = (EditText) view.findViewById(R.id.edit_q5);

        editID.setText("");
        editQ1.setText("");
        editQ2.setText("");
        editQ3.setText("");
        editQ4.setText("");
        editQ5.setText("");
    }

    /**
     * Update the list of scores.
     * @return
     */
    private Student updateListScores() {
        Log.d(LOG_TAG, "Enter updateListScores()");

        Student st = retrieveAScore();
        if (st == null) {
            return null;
        }
        scoresHolder.addStudent(st);

        List<String> scoresList = scoresHolder.getAllScores();
        ArrayAdapter<String> scoresAdapter = new ArrayAdapter<String> (getActivity(),
                R.layout.fragment_list_one_score, R.id.text_list_data_added, scoresList);
        ListView lv = (ListView) getActivity().findViewById(R.id.list_all_scores);
        lv.setAdapter(scoresAdapter);

        return st;
    }

    /**
     * Retrieve a score.
     * @return
     */
    private Student retrieveAScore() {
        Student st = new Student();
        try {
            View view = getView();

            EditText editID = (EditText) view.findViewById(R.id.edit_id);
            EditText editQ1 = (EditText) view.findViewById(R.id.edit_q1);
            EditText editQ2 = (EditText) view.findViewById(R.id.edit_q2);
            EditText editQ3 = (EditText) view.findViewById(R.id.edit_q3);
            EditText editQ4 = (EditText) view.findViewById(R.id.edit_q4);
            EditText editQ5 = (EditText) view.findViewById(R.id.edit_q5);

            st.setsID(Integer.parseInt(editID.getText().toString()));

            int[] scores = new int[QUIZ_NUM];
            scores[0] = Integer.parseInt(editQ1.getText().toString());
            scores[1] = Integer.parseInt(editQ2.getText().toString());
            scores[2] = Integer.parseInt(editQ3.getText().toString());
            scores[3] = Integer.parseInt(editQ4.getText().toString());
            scores[4] = Integer.parseInt(editQ5.getText().toString());
            st.setScores(scores);

            if (st.getsID() > 9999 || st.getsID() < 1000) {
                throw new Exception("Invalid ID!");
            }

            for (int i=0; i<QUIZ_NUM; ++i) {
                if (scores[i] > 100) {
                    throw new Exception("Invalid score: Quiz " + i+1);
                }
            }

            return st;
        } catch (Exception e) {
            Log.i(LOG_TAG, "The input is invalid! " + e.toString());
            displayError();
            return null;
        }
    }

    private void displayError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Error");
        builder.setMessage("Please make sure your input is valid as the followings say:\n"
                + "1. No empty content.\n"
                + "2. Valid ID: 1000 ~ 9999.\n"
                + "3. Valid scores: 0 ~ 99.");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * Update the statistics result.
     */
    private void updateStatResult() {
        Log.d(LOG_TAG, "Enter updateStatResult()");

        int[] lowScores = scoresHolder.getLow();
        int[] highScores = scoresHolder.getHigh();
        double[] avgScores = scoresHolder.getAvg();

        TextView h1 = (TextView) getActivity().findViewById(R.id.text_high_q1);
        TextView h2 = (TextView) getActivity().findViewById(R.id.text_high_q2);
        TextView h3 = (TextView) getActivity().findViewById(R.id.text_high_q3);
        TextView h4 = (TextView) getActivity().findViewById(R.id.text_high_q4);
        TextView h5 = (TextView) getActivity().findViewById(R.id.text_high_q5);

        h1.setText(Integer.toString(highScores[0]));
        h2.setText(Integer.toString(highScores[1]));
        h3.setText(Integer.toString(highScores[2]));
        h4.setText(Integer.toString(highScores[3]));
        h5.setText(Integer.toString(highScores[4]));

        TextView l1 = (TextView) getActivity().findViewById(R.id.text_low_q1);
        TextView l2 = (TextView) getActivity().findViewById(R.id.text_low_q2);
        TextView l3 = (TextView) getActivity().findViewById(R.id.text_low_q3);
        TextView l4 = (TextView) getActivity().findViewById(R.id.text_low_q4);
        TextView l5 = (TextView) getActivity().findViewById(R.id.text_low_q5);

        l1.setText(Integer.toString(lowScores[0]));
        l2.setText(Integer.toString(lowScores[1]));
        l3.setText(Integer.toString(lowScores[2]));
        l4.setText(Integer.toString(lowScores[3]));
        l5.setText(Integer.toString(lowScores[4]));

        TextView a1 = (TextView) getActivity().findViewById(R.id.text_avg_q1);
        TextView a2 = (TextView) getActivity().findViewById(R.id.text_avg_q2);
        TextView a3 = (TextView) getActivity().findViewById(R.id.text_avg_q3);
        TextView a4 = (TextView) getActivity().findViewById(R.id.text_avg_q4);
        TextView a5 = (TextView) getActivity().findViewById(R.id.text_avg_q5);

        a1.setText(String.format("%.1f", avgScores[0]));
        a2.setText(String.format("%.1f", avgScores[1]));
        a3.setText(String.format("%.1f", avgScores[2]));
        a4.setText(String.format("%.1f", avgScores[3]));
        a5.setText(String.format("%.1f", avgScores[4]));
    }

    /**
     * Update the database.
     * @param st
     */
    private void updateDB(Student st) {
        if (st == null) {
            return;
        }
        DBUtil dbUtil = new DBUtil(getActivity());
        dbUtil.addStudentInfo(dbUtil, st.getsID());
        Log.d(LOG_TAG, "After updating, all the students are:\n" + dbUtil.getAllStudentInfo(dbUtil));
        dbUtil.addQuizRecord(dbUtil, st.getsID(), st.getScores());
        Log.d(LOG_TAG, "After updating, all the scores are:\n" + dbUtil.getAllScores(dbUtil));
    }
}
