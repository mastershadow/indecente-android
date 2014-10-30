package com.roccatello.indecente.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roccatello.indecente.R;
import com.roccatello.indecente.view.PillView;

/**
 * Created by Eduard on 23/09/2014.
 */
public class GameFragment extends Fragment {
    private PillView pillView;

    public GameFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);
        this.pillView = (PillView)v.findViewById(R.id.pillView);
        v.setBackgroundColor(Color.argb(255, 0, 0, 0));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
