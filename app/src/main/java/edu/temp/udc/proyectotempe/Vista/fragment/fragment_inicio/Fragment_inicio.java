package edu.temp.udc.proyectotempe.Vista.fragment.fragment_inicio;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import edu.temp.udc.proyectotempe.ApiRest.model.Dato;
import edu.temp.udc.proyectotempe.ApiRest.model.UserDevice;
import edu.temp.udc.proyectotempe.R;
import edu.temp.udc.proyectotempe.tempo.Token;

/**
 * Created by Usuario on 14/11/2017.
 */

public class Fragment_inicio extends Fragment implements OnChartValueSelectedListener, Observer {
    private View view;
    UserDevice userDevice;
    private List<Dato> listData;
    private LineChart mChart;
    LineData data;
    private int[] mColors = ColorTemplate.COLORFUL_COLORS;
    private RecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private byte con = Byte.MIN_VALUE;
    private LinearLayout linear;
    TextView device;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inicio, container, false);

        String[] names;
        userDevice = Token.getInstance().getUserDevices().isEmpty()? new UserDevice():Token.getInstance().getUserDevices().get(0);
        //Appbar page filter
        Spinner cmbToolbar = (Spinner) view.findViewById(R.id.CmbToolbar);
        names = new String[Token.getInstance().getUserDevices().size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = Token.getInstance().getUserDevices().get(i).getNombre();
        }
        view.findViewById(R.id.relative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final ArrayAdapter<String> adapter2;
        if (Token.getInstance().getUserDevices().size() != 0) {
            adapter2 = new ArrayAdapter<>(view.getContext(),
                    R.layout.appbar_filter_title, names);
        } else {
            adapter2 = new ArrayAdapter<>(view.getContext(),
                    R.layout.appbar_filter_title, new String[]{"Vacío"});
        }
        linear = (LinearLayout) view.findViewById(R.id.linear);
        adapter2.setDropDownViewResource(R.layout.appbar_filter_list);
        device = (TextView) view.findViewById(R.id.deviceD);
        cmbToolbar.setAdapter(adapter2);
        cmbToolbar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View v, int i, long l) {
                if (Token.getInstance().getUserDevices().size() != 0) {
                    userDevice = Token.getInstance().getUserDevices().get(i);
                    mRecyclerView.setVisibility(View.GONE);
                    linear.setVisibility(View.VISIBLE);
                    adapter.removeItem();
                    addEntry(0.0);
                    device.setText(Token.getInstance().getUserDevices().get(i).getDevice());
                } else {
                    mRecyclerView.setVisibility(View.GONE);
                    linear.setVisibility(View.VISIBLE);
                    adapter.removeItem();
                    addEntry(0.0);
                    device.setText("NINGUNO");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (Token.getInstance().getUserDevices().size() != 0) {
                    userDevice = Token.getInstance().getUserDevices().get(0);
                }
            }
        });

        listData = new ArrayList<>();
        Token.getInstance().addObserver(this);
        initView();

        return view;
    }

    private void initView() {
        inicilizateGrapf();

        adapter = new RecyclerViewAdapter(view.getContext());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL_LIST));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(adapter);


    }

    public void inicilizateGrapf() {
        mChart = (LineChart) view.findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);

        // add an empty data object
        mChart.setData(new LineData());
//        mChart.getXAxis().setDrawLabels(false);
//        mChart.getXAxis().setDrawGridLines(false);

        mChart.invalidate();


    }

    private void addEntry(Double num) {
        if (num == 0.0) {
            con = Byte.MAX_VALUE;
            removeDataSet();
            adapter.removeItem();
        } else if (con == Byte.MAX_VALUE) {
            con = Byte.MIN_VALUE;
            removeDataSet();
            linear.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            adapter.addItem(0, num, new Date());
            listData.add(new Dato(num, new Date()));
        } else {
            adapter.addItem(0, num, new Date());
            listData.add(new Dato(num, new Date()));
        }
        data = mChart.getData();

        ILineDataSet set = data.getDataSetByIndex(0);

        // set.addEntry(...); // can be called as well

        if (set == null) {
            set = createSet(data);
            set.setLabel("Temperatura ");


            data.addDataSet(set);
        }

        // choose a random dataSet
        int randomDataSetIndex = (int) (Math.random() * data.getDataSetCount());


        data.addEntry(new Entry(data.getDataSetByIndex(randomDataSetIndex).getEntryCount(), Float.parseFloat(String.valueOf(num))), randomDataSetIndex);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        mChart.notifyDataSetChanged();

        mChart.setVisibleXRangeMaximum(10);
        //mChart.setVisibleYRangeMaximum(15, AxisDependency.LEFT);
//
//            // this automatically refreshes the chart (calls invalidate())
        mChart.moveViewTo(data.getEntryCount() - 7, 50f, YAxis.AxisDependency.LEFT);

    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

        Toast.makeText(view.getContext(),!listData.isEmpty()? new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(listData.get((int) e.getX()).getDate()):"0", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }

    private LineDataSet createSet(LineData data) {

        LineDataSet set = new LineDataSet(null, "DataSet 1");
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        int count = (data.getDataSetCount() + 1);
        int color = mColors[count % mColors.length];

        set.setColor(color);
        set.setCircleColor(color);
        set.setHighLightColor(color);
        set.setValueTextSize(8f);
        set.setValueTextColor(color);

        return set;
    }


    @Override
    public void update(Observable o, final Object arg) {

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                final JSONObject jsonObject = (JSONObject) arg;
                // 1. Instantiate an AlertDialog.Builder with its constructor
                String message = "";
                String id = "";
                try {
                    id = jsonObject.getString("data");
                    message = jsonObject.getString("usuario");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (message.equals(userDevice.getDevice())) {
                    addEntry(Double.parseDouble(id));

                } else {

                }
            }

        });
    }

    private void removeLastEntry() {

        LineData data = mChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);

            if (set != null) {

                Entry e = set.getEntryForXValue(set.getEntryCount() - 1, Float.NaN);

                data.removeEntry(e, 0);
                // or remove by index
                // mData.removeEntryByXValue(xIndex, dataSetIndex);
                data.notifyDataChanged();
                mChart.notifyDataSetChanged();
//                mChart.invalidate();
            }
        }
    }

    private void removeDataSet() {

        LineData data = mChart.getData();

        if (data != null) {

            data.removeDataSet(data.getDataSetByIndex(data.getDataSetCount() - 1));

            mChart.notifyDataSetChanged();
            mChart.invalidate();
        }
    }
}
