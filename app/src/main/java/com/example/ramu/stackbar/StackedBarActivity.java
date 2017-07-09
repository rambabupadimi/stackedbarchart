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

        tvX = (TextView) findViewById(R.id.tvXMax);
        tvY = (TextView) findViewById(R.id.tvYMax);

        mSeekBarX = (SeekBar) findViewById(R.id.seekBar1);
        mSeekBarX.setOnSeekBarChangeListener(this);

        mSeekBarY = (SeekBar) findViewById(R.id.seekBar2);
        mSeekBarY.setOnSeekBarChangeListener(this);

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
        mSeekBarX.setProgress(12);
        mSeekBarY.setProgress(100);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

        // mChart.setDrawLegend(false);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("" + (mSeekBarX.getProgress() + 1));
        tvY.setText("" + (mSeekBarY.getProgress()));

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();


       /*
       float[] val1 = {12f,18f};
        float[] val2 = {18f,21f,27f};
        float[] val3 = {19f,26f,32f,36f};
        yVals1.add(new BarEntry(0,val1));
        yVals1.add(new BarEntry(1,val2));
        yVals1.add(new BarEntry(2,val3));
*/
        float values[]=null;

        BarDataSet set1 = null;

        String data =" [\n" +
                "        {\n" +
                "            \"SubmitDate\": \"7/5/2017 12:00:00 AM\",\n" +
                "            \"Sheets\": [\n" +
                "                    {\n" +
                "                          \"Data3Name\": \"10\"\n" +
                "                          },\n" +
                "                  {\n" +
                "                        \"Data3Name\": \"20\"\n" +
                "                   },\n" +


                "                  {\n" +
                "                        \"Data3Name\": \"40\"\n" +
                "                   }\n" +


                "              ]\n" +
                "        }\n" +
                "        ,\n" +
                "        {\n" +
                "            \"SubmitDate\": \"7/5/2018 12:00:00 AM\",\n" +
                "            \"Sheets\": [\n" +
                "                    {\n" +
                "                          \"Data3Name\": \"15\"\n" +
                "                          },\n" +
                "                  {\n" +
                "                        \"Data3Name\": \"30\"\n" +
                "                   },\n" +
                "                                     {\n" +
                "                        \"Data3Name\": \"45\"\n" +
                "                   }\n" +
                "\n" +
                "              ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"SubmitDate\": \"7/5/2019 12:00:00 AM\",\n" +
                "            \"Sheets\": [\n" +
                "                    {\n" +
                "                          \"Data3Name\": \"5\"\n" +
                "                          },\n" +

                "                  {\n" +
                "                        \"Data3Name\": \"25\"\n" +
                "                   },\n" +

                "                  {\n" +
                "                        \"Data3Name\": \"45\"\n" +
                "                   },\n" +

                "                     {\n" +
                "                        \"Data3Name\": \"55\"\n" +
                "                   }\n" +
                "              ]\n" +
                "        }\n" +
                "            \n" +
                "        \n" +
                "        \n" +
                "    ]";


        try {
            JSONArray jsonArray = new JSONArray(data);
            Log.i("tag", "json array data" + jsonArray);


         //   List<Hashtable> hashMapsListHours = new ArrayList<>();
         //   List<Hashtable> hashMapsListDates = new ArrayList<>();

            int maxLength = 0;
           String[] dates = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {


                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String submitdate = jsonObject.get("SubmitDate").toString();
               dates[i] = submitdate;

                JSONArray jsonArray1 = jsonObject.getJSONArray("Sheets");
                values = new float[jsonArray1.length()];
                for (int m = 0; m < jsonArray1.length(); m++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(m);
                    float hours = Float.parseFloat(jsonObject1.get("Data3Name").toString());
                    values[m] = hours;
                    Log.i("tag", "data is hours, " + hours);
                }
                Log.i("tag", "data is hours space");

                yVals1.add(new BarEntry(i,values));
                set1 = new BarDataSet(yVals1, "Statistics Vienna 2014");

                set1.setStackLabels(dates);

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }




        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1.setDrawIcons(false);
            set1.setColors(getColors());
//            set1.setStackLabels(new String[]{"Births", "Divorces", "Marriages"});

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data1 = new BarData(dataSets);
            data1.setValueFormatter(new MyValueFormatter());
            data1.setValueTextColor(Color.WHITE);
            mChart.setData(data1);

        }

        mChart.setFitBars(true);
        mChart.invalidate();
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