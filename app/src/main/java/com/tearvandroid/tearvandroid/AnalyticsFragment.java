package com.tearvandroid.tearvandroid;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class AnalyticsFragment extends Fragment/* implements AsyncInterface*/ {

    LineGraphSeries <DataPoint> tempSeries;
    LineGraphSeries <DataPoint> humiditySeries;
    String responseFromServer = "";
    JSONArray jsonArray;
    GraphView graphView;
    GraphView humidityGraph;
    String result;
    private Runnable mTimer;
    View view;
    int in;
    private Runnable mTimer1;
    private final Handler mHandler = new Handler();
    Timer autoUpdate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_analytics, container, false);
        graphView = (GraphView) view.findViewById(R.id.graph);
        humidityGraph = (GraphView) view.findViewById(R.id.humidityGraph);
        tempSeries = new LineGraphSeries<DataPoint>();
        humiditySeries = new LineGraphSeries<DataPoint>();

//        Timer timer  = new Timer();
//
//        autoUpdate = new Timer();
//        autoUpdate.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        new LongOperation().execute("");
//                    }
//                };
//
//            }
//        }, 0, 10000);

//        void timerMethod()
//        {
//            timer.schedule(new TimerTask() {
//                public void run() {
//                    new LongOperation().execute("");
//                }
//            }, 10000, 10000);
//
//        };

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                new LongOperation().execute("");
//                for (int i = 1; i <= 30; i++) {
//                    tempSeries.appendData(new DataPoint(i, i), true, 30);
//                    humiditySeries.appendData(new DataPoint(i, 1), true, 30);
//                }
            }
        };

        handler.postDelayed(runnable, 2000);


//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                //new LongOperation().execute("");
//                for (int i = 1; i <= 30; i++) {
//                    tempSeries.appendData(new DataPoint(i, i), true, 30);
//                    humiditySeries.appendData(new DataPoint(i, 1), true, 30);
//                }
//            }
//        },1000);


        //response(tempSeries);
        //return view;
//        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
//        String url = "http://192.168.43.190:8000/api/getSensorData";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        responseFromServer = response;
//                        Log.d("intro", responseFromServer);
//
//                        try {
//                            Log.d("response", responseFromServer);
//                            JSONObject jsonObject = new JSONObject(responseFromServer);
//                            jsonArray = jsonObject.getJSONArray("results");
//
////                            Log.d("json", jsonArray.toString());
//
//                            for (int i = 1; i <= 30; i++) {
//
//                                try {
//                                    JSONObject currentEntry = jsonArray.getJSONObject(i);
//                                    JSONArray firstEntry = currentEntry.getJSONArray("" + i);
//                                    JSONObject firstObject = firstEntry.getJSONObject(0);
////                                    Log.d("humidity", firstObject.toString());
//
//                                    double currentTemp = firstObject.getDouble("temperature");
//                                    double currentHumidity = firstObject.getDouble("humidity");
//
////                                    Log.d("temp", ""+currentTemp);
////                                    Log.d("humidity", ""+currentHumidity);
//                                    tempSeries.appendData(new DataPoint(i, i), true, 30);
////                                    humiditySeries.appendData(new DataPoint(currentTemp, i), true, 30);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            graphView.addSeries(tempSeries);
//                            humidityGraph.addSeries(humiditySeries);
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                error.toString();
//                //                        textToChange.setText("That didn't work!");
//            }
//        });
//        queue.add(stringRequest);
//
//            }
//        },0,5000);

//        if (responseFromServer == "") {

//        }

//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject currentEntry;
//            try{
//                currentEntry = jsonArray.getJSONObject(i);
//                x = currentEntry.getInt("time");
//                y = currentEntry.getInt("temp");
//            }
//            catch(JSONException e){
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            tempSeries.appendData(new DataPoint(x, y), true, results.length());
//            humiditySeries.appendData(new DataPoint(x, y), true, results.length());
//        }
//
//
//        graphView.addSeries(tempSeries);
//        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Time (sec)");
//        graphView.getGridLabelRenderer().setVerticalAxisTitle("Temperature (degree C)");
//        graphView.getGridLabelRenderer().setLabelVerticalWidth(70);
//        graphView.getGridLabelRenderer().setLabelHorizontalHeight(30);
//
//        humidityGraph.addSeries(humiditySeries);
//        humidityGraph.getGridLabelRenderer().setHorizontalAxisTitle("Time (sec)");
//        humidityGraph.getGridLabelRenderer().setVerticalAxisTitle("Humidity");
//        humidityGraph.getGridLabelRenderer().setLabelVerticalWidth(70);
//        humidityGraph.getGridLabelRenderer().setLabelHorizontalHeight(30);
//
//        Log.d("oncreate", jsonArray.toString());
        graphView.onDataChanged(false, false);
        humidityGraph.onDataChanged(false, false);
        return view;
    }



//    @Override
//    public void onResume() {
//        super.onResume();
//        autoUpdate = new Timer();
//        autoUpdate.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        updateUI();
//                    }
//                };
////                runOnUiThread(new Runnable() {
////                    public void run() {
////                        updateHTML();
////                    }
////                });
//            }
//        }, 0, 10000);
//    }

    public void updateUI(){
        new LongOperation().execute("");
//        graphView.onDataChanged(false, false);
//        humidityGraph.onDataChanged(false, false);

    }


    private class LongOperation extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "http://192.168.43.190:8000/api/getSensorData";

            RequestFuture<String> future = RequestFuture.newFuture();
            StringRequest request = new StringRequest(Request.Method.GET, url, future, future);
            queue.add(request);

            try{
                result = future.get();
            } catch(InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e){
                e.printStackTrace();
            }

//            Log.d("delayedResult", result);
            return result;
//            return "";
        }

        protected void onPostExecute(String result) {

            try{
                JSONObject allEntries = new JSONObject(result);
                jsonArray = allEntries.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }


//            Log.d("afterExecution", jsonArray.toString());
            Log.d("length", "length = " + jsonArray.length());
            int startPoint = jsonArray.length() - 200 - 1;
            for (int i = startPoint; i < jsonArray.length(); i++) {

                try {
                    JSONObject currentEntry = jsonArray.getJSONObject(i);
//                    Log.d("currentEntry", currentEntry.toString());
                    Log.d("afterExecution", currentEntry.toString());
                    JSONArray firstEntry = currentEntry.getJSONArray("" + (i+ 1));

                    Log.d("firstEntry", firstEntry.toString());
                    JSONObject firstObject = firstEntry.getJSONObject(0);
                    Log.d("humidity", firstObject.toString());

                    double currentTemp = firstObject.getDouble("temperature");
                    double currentHumidity = firstObject.getDouble("humidity");
                    Log.d("currentTemp", ""+currentTemp);
                    Log.d("currentHumidity", ""+currentHumidity);

//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

                    tempSeries.appendData(new DataPoint(i, currentTemp), true, 200);
                    humiditySeries.appendData(new DataPoint(i, currentHumidity), true, 200);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            graphView.addSeries(tempSeries);
            humidityGraph.addSeries(humiditySeries);
        }

    }
}