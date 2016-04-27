package com.example.cmu.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cmu.scores.R;

import java.util.Arrays;
import java.util.List;

public class ListScoresFragment extends Fragment {
    final String s = "STUD         Q1         Q2         Q3         Q4         Q5";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_all_scores, container, false);
        String[] mockData = {s};
        List<String> scoreList = Arrays.asList(mockData);
        ArrayAdapter<String> scoresAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.fragment_list_one_score, R.id.text_list_data_added, scoreList);
        ListView lv = (ListView) rootView.findViewById(R.id.list_all_scores);
        lv.setAdapter(scoresAdapter);

        return rootView;
    }
}
