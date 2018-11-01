package com.tearvandroid.tearvandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AnalyticsFragment extends Fragment {

    LineGraphSeries <DataPoint> series;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        JSONObject data1 = new JSONObject();
        try{
            data1.put("id", 1);
            data1.put("temp", 1);
            data1.put("humidity", 80);
            data1.put("time", 1);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JSONObject data2 = new JSONObject();
        try{
            data2.put("id", 2);
            data2.put("temp", 5);
            data2.put("humidity", 60);
            data2.put("time", 2);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JSONObject data3 = new JSONObject();
        try{
            data3.put("id", 3);
            data3.put("temp", 2);
            data3.put("humidity", 80);
            data3.put("time", 3);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JSONObject data4 = new JSONObject();
        try{
            data4.put("id", 4);
            data4.put("temp", 10);
            data4.put("humidity", 50);
            data4.put("time", 4);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JSONObject data5 = new JSONObject();
        try{
            data5.put("id", 5);
            data5.put("temp", 7);
            data5.put("humidity", 60);
            data5.put("time", 5);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JSONArray results = new JSONArray();
        results.put(data1);
        results.put(data2);
        results.put(data3);
        results.put(data4);
        results.put(data5);

        int y, x;
        x = -5;
        y = 0;

        View view = inflater.inflate(R.layout.fragment_analytics, container, false);
        GraphView graphView = (GraphView) view.findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject currentEntry;
            try{
                currentEntry = results.getJSONObject(i);
                x = currentEntry.getInt("time");
                y = currentEntry.getInt("temp");
            }
            catch(JSONException e){
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            series.appendData(new DataPoint(x, y), true, 5);
        }


        graphView.addSeries(series);
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Time (sec)");
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Temperature (degree C)");
        graphView.getGridLabelRenderer().setLabelVerticalWidth(70);
        graphView.getGridLabelRenderer().setLabelHorizontalHeight(30);

        return view;

    }
}
