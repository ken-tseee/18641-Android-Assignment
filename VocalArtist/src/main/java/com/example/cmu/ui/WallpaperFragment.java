package com.example.cmu.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class WallpaperFragment extends Fragment implements View.OnClickListener {
    public WallpaperFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallpaper, container, false);

        Button button1 = (Button) rootView.findViewById(R.id.button1);
        Button button2 = (Button) rootView.findViewById(R.id.button2);
        Button button3 = (Button) rootView.findViewById(R.id.button3);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                openWallpaper(1);
                break;
            case R.id.button2:
                openWallpaper(2);
                break;
            case R.id.button3:
                openWallpaper(3);
                break;
        }
    }

    /**
     * Open a wall paper.
     * @param i
     */
    private void openWallpaper(int i) {
        if ( i < 0 || i > 3) { return; }

        ImageView im = (ImageView) getView().findViewById(R.id.imageView);

        if (i == 1) { im.setImageResource(R.drawable.wp1); }
        if (i == 2) { im.setImageResource(R.drawable.wp2); }
        if (i == 3) { im.setImageResource(R.drawable.wp3); }
    }
}
