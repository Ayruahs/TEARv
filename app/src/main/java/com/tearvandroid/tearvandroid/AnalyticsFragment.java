package com.tearvandroid.tearvandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class AnalyticsFragment extends Fragment {

    LineGraphSeries <DataPoint> series;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        double y, x;
        x = -5.0;

        View view = inflater.inflate(R.layout.fragment_analytics, container, false);
        GraphView graphView = (GraphView) view.findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < 500; i++) {
            x = x + 0.1;
            y = Math.sin(x);
            series.appendData(new DataPoint(x, y), true, 500);
        }

        graphView.addSeries(series);

        return view;

    }
}
