package com.example.cmu.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {
    /**
     * Constructor.
     */
    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Button buttonSendGeoSMS = (Button) rootView.findViewById(R.id.button_send_location);
        buttonSendGeoSMS.setOnClickListener(new SendGeoSMSOnClickListener(getActivity()));

        return rootView;
    }
}
