package com.example.cmu.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Author: Junjian Xie
 * Email: junjianx@andrew.cmu.edu
 * Date: 15/11/11
 */
public class SongFragment extends Fragment implements View.OnClickListener {
    private MediaPlayer mPlayer1;
    private MediaPlayer mPlayer2;
    private MediaPlayer mPlayer3;

    public SongFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_song, container, false);

        Button button1 = (Button) rootView.findViewById(R.id.button1);
        Button button2 = (Button) rootView.findViewById(R.id.button2);
        Button button3 = (Button) rootView.findViewById(R.id.button3);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

        mPlayer1 = MediaPlayer.create(getActivity(), R.raw.m1);
        mPlayer2 = MediaPlayer.create(getActivity(), R.raw.m2);
        mPlayer3 = MediaPlayer.create(getActivity(), R.raw.m3);


        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                playMp3(1);
                break;
            case R.id.button2:
                playMp3(2);
                break;
            case R.id.button3:
                playMp3(3);
                break;
        }
    }

    /**
     * Play a song.
     * @param i
     */
    private void playMp3(int i) {
        if ( i < 0 || i > 3) { return; }
        if (i == 1) {
            if (mPlayer2.isPlaying())
                mPlayer2.pause();
            if (mPlayer3.isPlaying())
                mPlayer3.pause();
            mPlayer1.start();
        }
        if (i == 2) {
            if (mPlayer1.isPlaying())
                mPlayer1.pause();
            if (mPlayer3.isPlaying())
                mPlayer3.pause();
            mPlayer2.start();
        }
        if (i == 3) {
            if (mPlayer2.isPlaying())
                mPlayer2.pause();
            if (mPlayer1.isPlaying())
                mPlayer1.pause();
            mPlayer3.start();
        }
    }
}
