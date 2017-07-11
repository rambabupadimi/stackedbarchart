package com.example.ramu.stackbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class StackedBarActivity extends DemoBase implements SeekBar.OnSeekBarChangeListener, OnChartValueSelectedListener {

    private BarChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_barchart);

        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(40);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(false);
        mChart.setHighlightFullBarEnabled(false);

       // mChart.getXAxis().setCenterAxisLabels(true);

        // change the position of the y-labels
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new MyAxisValueFormatter());
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        mChart.getAxisRight().setEnabled(false);

        XAxis xLabels = mChart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.TOP);

        // mChart.setDrawXLabels(false);
        // mChart.setDrawYLabels(false);

        // setting data

        Legend l = mChart.getLegend();
     //   l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
       // l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
       // l.setFormToTextSpace(4f);
       // l.setXEntrySpace(6f);




        // mChart.setDrawLegend(false);
        String data ="[\n" +
                "  {\n" +
                "  \"SubmitDate\":\"7/5/2017\",\n" +
                "  \"Sheets\":[\n" +
                "    {\n" +
                "    \"Data3Name\":\"12\",\n" +
                "    \"Colorcode\":\"#F4511E\",\n" +
                "    \"Name\":\"p1\"\n" +
                "    },\n" +
                "  {\n" +
                "    \"Data3Name\":\"18\",\n" +
                "    \"Colorcode\":\"#FF5722\",\n" +
                "    \"Name\":\"p2\"\n" +

                "    },\n" +
                "    {\n" +
                "    \"Data3Name\":\"29\",\n" +
                "    \"Colorcode\":\"#FF7043\",\n" +
                "    \"Name\":\"p3\"\n" +

                "    },\n" +
                "    {\n" +
                "    \"Data3Name\":\"42\",\n" +
                "    \"Colorcode\":\"#FF8A65\",\n" +
                "    \"Name\":\"p4\"\n" +

                "    }\n" +
                "  ]\n" +
                "  },\n" +
                "  {\n" +
                "  \"SubmitDate\":\"8/5/2017\",\n" +
                "  \"Sheets\":[\n" +
                "    {\n" +
                "    \"Data3Name\":\"34\",\n" +
                "    \"Colorcode\":\"#43A047\",\n" +
                "    \"Name\":\"m1\"\n" +

                "    },\n" +
                "  {\n" +
                "    \"Data3Name\":\"23\",\n" +
                "    \"Colorcode\":\"#4CAF50\",\n" +
                "    \"Name\":\"m5\"\n" +

                "    },\n" +
                "    {\n" +
                "    \"Data3Name\":\"10\",\n" +
                "    \"Colorcode\":\"#66BB6A\",\n" +
                "    \"Name\":\"m6\"\n" +

                "    }\n" +
                "  ]\n" +
                "  },\n" +
                "  {\n" +
                "  \"SubmitDate\":\"9/5/2017\",\n" +
                "  \"Sheets\":[\n" +
                "    {\n" +
                "    \"Data3Name\":\"25\",\n" +
                "    \"Colorcode\":\"#1E88E5\",\n" +
                "    \"Name\":\"k1\"\n" +


                "    },\n" +
                "  {\n" +
                "    \"Data3Name\":\"40\",\n" +
                "    \"Colorcode\":\"#2196F3\",\n" +
                "    \"Name\":\"k2\"\n" +

                "    }\n" +
                "  ]\n" +
                "  }\n" +
                "\n" +
                "  \n" +
                "]";

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();

        try {
            JSONArray jsonArray = new JSONArray(data);
            Log.i("tag", "json array data" + jsonArray);
            //   List<Hashtable> hashMapsListHours = new ArrayList<>();
            //   List<Hashtable> hashMapsListDates = new ArrayList<>();
            int maxLength = 0;
            String[] dates = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                int[] Colors=null;
                float values[]=null;
                String[] names=null;
                BarDataSet set1 = null;
                ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String submitdate = jsonObject.get("SubmitDate").toString();
                dates[i] = submitdate;

                JSONArray jsonArray1 = jsonObject.getJSONArray("Sheets");
                values = new float[jsonArray1.length()];
                Colors = new int[jsonArray1.length()];
                names = new String[jsonArray1.length()];
                for (int m = 0; m < jsonArray1.length(); m++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(m);
                    float hours = Float.parseFloat(jsonObject1.get("Data3Name").toString());
                    values[m] = hours;
                    Colors[m] = Color.parseColor(jsonObject1.get("Colorcode").toString());
                    names[m] = jsonObject1.get("Name").toString();
                    Log.i("tag", "data is hours, " + hours);
                }
                Log.i("tag", "data is hours space");

                yVals1.add(new BarEntry(i,values));
                set1 = new BarDataSet(yVals1,submitdate);
                set1.setColors(Colors);
               set1.setStackLabels(names);
                dataSets.add(set1);

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }




        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
           // set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
           // set1.setValues(yVals1);

            BarData data1 = new BarData(dataSets);
            data1.setValueFormatter(new MyValueFormatter());
            data1.setValueTextColor(Color.WHITE);
            mChart.setData(data1);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            //        set1.setDrawIcons(false);
            //          set1.setColors(getColors());
//            set1.setStackLabels(new String[]{"Births", "Divorces", "Marriages"});


            BarData data1 = new BarData(dataSets);
            data1.setValueFormatter(new MyValueFormatter());
            data1.setValueTextColor(Color.WHITE);
            mChart.setData(data1);

        }

        mChart.setFitBars(true);
        mChart.invalidate();

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("" + (mSeekBarX.getProgress() + 1));
        tvY.setText("" + (mSeekBarY.getProgress()));

    }
        @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        BarEntry entry = (BarEntry) e;

        if (entry.getYVals() != null)
            Log.i("VAL SELECTED", "Value: " + entry.getYVals()[h.getStackIndex()]);
        else
            Log.i("VAL SELECTED", "Value: " + entry.getY());
    }

    @Override
    public void onNothingSelected() {
        // TODO Auto-generated method stub

    }

    private int[] getColors() {

        int stacksize = 3;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = ColorTemplate.MATERIAL_COLORS[i];
        }

        return colors;
    }
}