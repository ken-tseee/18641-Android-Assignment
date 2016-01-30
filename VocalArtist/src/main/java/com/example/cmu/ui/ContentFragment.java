package com.example.cmu.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Author: Junjian Xie
 * Email: junjianx@andrew.cmu.edu
 * Date: 15/11/11
 */
public class ContentFragment extends Fragment implements View.OnClickListener {
    public ContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content, container, false);

        Button buttonSong = (Button) rootView.findViewById(R.id.buttonSong);
        Button buttonVideo = (Button) rootView.findViewById(R.id.buttonVideo);
        Button buttonWallPaper = (Button) rootView.findViewById(R.id.buttonWallPaper);
        Button buttonGetOnMailList = (Button) rootView.findViewById(R.id.buttonGetOnMail);

        buttonSong.setOnClickListener(this);
        buttonVideo.setOnClickListener(this);
        buttonWallPaper.setOnClickListener(this);
        buttonGetOnMailList.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonGetOnMail:
                startActivity(new Intent(getActivity(),GetOnMailActivity.class));
                break;
            case R.id.buttonSong:
                startActivity(new Intent(getActivity(),SongActivity.class));
                break;
            case R.id.buttonVideo:
                startActivity(new Intent(getActivity(),VideoActivity.class));
                break;
            case R.id.buttonWallPaper:
                startActivity(new Intent(getActivity(),WallPaperActivity.class));
                break;
        }
    }

}
