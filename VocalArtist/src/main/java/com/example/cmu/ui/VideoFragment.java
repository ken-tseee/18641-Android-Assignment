package com.example.cmu.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

public class VideoFragment extends Fragment implements View.OnClickListener {
    private VideoView v1;
    private VideoView v2;
    public VideoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);

        Button button1 = (Button) rootView.findViewById(R.id.button1);
        Button button2 = (Button) rootView.findViewById(R.id.button2);


        Button buttonS1 = (Button) rootView.findViewById(R.id.buttonStop1);
        Button buttonS2 = (Button) rootView.findViewById(R.id.buttonStop2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        buttonS1.setOnClickListener(this);
        buttonS2.setOnClickListener(this);

        v1 = (VideoView) rootView.findViewById(R.id.videoView1);
        v2 = (VideoView) rootView.findViewById(R.id.videoView2);
        v1.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.v1));
        v2.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.v2));

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                playVideo(1);
                break;
            case R.id.button2:
                playVideo(2);
                break;
            case R.id.buttonStop1:
                stopVideo(1);
                break;
            case R.id.buttonStop2:
                stopVideo(2);
                break;
        }
    }

    /**
     * Start playing the video.
     * @param i
     */
    private void playVideo(int i) {
        if ( i < 0 || i > 2) { return; }
        if (i == 1) {
            v1.seekTo(0);
            v1.start();
        }
        if (i == 2) {
            v2.seekTo(0);
            v2.start();
        }
    }

    /**
     * Stop the video when playing.
     * @param i
     */
    private void stopVideo(int i) {
        if ( i < 0 || i > 2) { return; }
        if (i == 1) {
            if (v1.isPlaying())
                v1.pause();
        }
        if (i == 2) {
            if (v2.isPlaying())
                v2.pause();
        }
    }
}
