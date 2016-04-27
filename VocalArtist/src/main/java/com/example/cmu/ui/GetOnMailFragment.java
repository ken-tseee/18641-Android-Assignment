package com.example.cmu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GetOnMailFragment extends Fragment implements View.OnClickListener {
    public GetOnMailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_get_on_mail, container, false);

        Button button = (Button) rootView.findViewById(R.id.buttonSend);

        button.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSend:
                sentMail();
                break;

        }
    }

    /**
     * Send emails.
     */
    private void sentMail() {
        EditText editSubject = (EditText) getActivity().findViewById(R.id.editSubject);
        EditText editContent = (EditText) getActivity().findViewById(R.id.editMailContent);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"junjianx@andrew.cmu.edu"});
        intent.putExtra(Intent.EXTRA_SUBJECT, editSubject.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, editContent.getText().toString());
        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
