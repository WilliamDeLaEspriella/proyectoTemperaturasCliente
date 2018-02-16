package edu.temp.udc.proyectotempe.Vista;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.temp.udc.proyectotempe.ApiRest.adapter.Endpoints;
import edu.temp.udc.proyectotempe.ApiRest.adapter.Rest_adapter;
import edu.temp.udc.proyectotempe.ApiRest.model.Dato;
import edu.temp.udc.proyectotempe.ApiRest.model.UserDevice;
import edu.temp.udc.proyectotempe.R;
import edu.temp.udc.proyectotempe.Vista.Dialog.AddUserDevice;
import edu.temp.udc.proyectotempe.Vista.Dialog.CreateDevice;
import edu.temp.udc.proyectotempe.Vista.Dialog.EditUserDevice;
import edu.temp.udc.proyectotempe.tempo.Token;
import edu.temp.udc.proyectotempe.util.MyPageTransformer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareViewActivity extends AppCompatActivity implements OnChartValueSelectedListener, AdapterView.OnItemSelectedListener {
    private TextView nombre;
    private TextView apellido;
    private TextView edad;
    private TextView device;
    private TextView dato;
    private LineChart mChart;
    private TextView Saño;
    private Button Smes;
    private ProgressBar progressBar;
    private PieChart mChart2;
    LineData data;
    private int[] mColors = ColorTemplate.COLORFUL_COLORS;
    private TextView tv_share_view_tip;
    private FloatingActionButton bottonCreate,fabedit;

    private ViewPager viewPager;
    private List<View> viewList;
    private View view1;
    private View view2;
    protected String[] mParties = new String[]{
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };
    protected Typeface mTfLight;
    protected Typeface mTfRegular;
    private UserDevice obj;
    private Calendar calendar;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_share_view);
        toolbar.setSubtitle("Historial usuarios");
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
calendar=Calendar.getInstance();
        Smes = (Button) findViewById(R.id.spinnerMes);
        Saño = (TextView) findViewById(R.id.spinnerAño);
        Saño.setText( calendar.get(Calendar.DAY_OF_MONTH) + "/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR));
        Smes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ShareViewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        Saño.setText(date);
                        peticion();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });





        progressBar = (ProgressBar) findViewById(R.id.progres);
        initView();
        initView2();
        inicilizateGrapf();
        peticion();

    }

    private void initView() {
        CardView card_share_view = (CardView) findViewById(R.id.card_share_view);
        RelativeLayout rela_round_big = (RelativeLayout) findViewById(R.id.rela_round_big);
        tv_share_view_tip = (TextView) findViewById(R.id.tv_share_view_tip);
        nombre = (TextView) findViewById(R.id.nombreUE);
        apellido = (TextView) findViewById(R.id.apellidoUE);
        edad = (TextView) findViewById(R.id.edadUE);
        dato = (TextView) findViewById(R.id.datoUE);
        device = (TextView) findViewById(R.id.deviceUE);
        bottonCreate = (FloatingActionButton) findViewById(R.id.bottonCreate);
fabedit=(FloatingActionButton) findViewById(R.id.fab);

        obj = (UserDevice) getIntent().getSerializableExtra("object");
        fabedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUserDevice dialog =  EditUserDevice.getInstance(obj);
                dialog.show(getFragmentManager(), "dialog");
            }
        });
        nombre.setText(obj.getNombre());
        apellido.setText(obj.getApellido());
        edad.setText(obj.getEdad() + " Años");
        dato.setText(obj.getDato() + "º");
        device.setText("conectado a: " + obj.getDevice());
        bottonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDevice dialog = new CreateDevice();
                Bundle args = new Bundle();
                args.putString("id", obj.get_id());
                dialog.setArguments(args);
                dialog.show(getFragmentManager(), "dialog");
            }
        });
        double dato = Double.parseDouble(obj.getDato());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (dato < 36) {
                rela_round_big.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorFreezer)));

            } else if (dato >= 36 && dato < 37) {
                rela_round_big.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorNormal)));
            } else if (dato >= 37 && dato < 38) {
                rela_round_big.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAdver)));
            } else if (dato >= 38) {
                rela_round_big.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorHot)));
            }
        }

        card_share_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ObjectAnimator upAnim = ObjectAnimator.ofFloat(view, "translationZ", 0);
                        upAnim.setDuration(200);
                        upAnim.setInterpolator(new DecelerateInterpolator());
                        upAnim.start();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        ObjectAnimator downAnim = ObjectAnimator.ofFloat(view, "translationZ", 20);
                        downAnim.setDuration(200);
                        downAnim.setInterpolator(new AccelerateInterpolator());
                        downAnim.start();
                        break;
                }
                return false;
            }
        });

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setStartOffset(1000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tv_share_view_tip.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        tv_share_view_tip.startAnimation(alphaAnimation);
    }

    private void initView2() {
        view1 = getLayoutInflater().inflate(R.layout.grafica1, null);
        view2 = getLayoutInflater().inflate(R.layout.grafica2, null);

        viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);


    }

    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }
    };
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void inicilizateGrapf() {
        mChart = (LineChart) view1.findViewById(R.id.grafica1);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);

        // add an empty data object
        mChart.setData(new LineData());
//        mChart.getXAxis().setDrawLabels(false);
//        mChart.getXAxis().setDrawGridLines(false);

        mChart.invalidate();

        mChart2 = (PieChart) view2.findViewById(R.id.grafica2);
        mChart2.setBackgroundColor(Color.WHITE);

        //moveOffScreen();

        mChart2.setUsePercentValues(true);
        mChart2.getDescription().setEnabled(false);

        mChart2.setCenterTextTypeface(mTfLight);
        mChart2.setCenterText(generateCenterSpannableText());

        mChart2.setDrawHoleEnabled(true);
        mChart2.setHoleColor(Color.WHITE);

        mChart2.setTransparentCircleColor(Color.WHITE);
        mChart2.setTransparentCircleAlpha(110);

        mChart2.setHoleRadius(58f);
        mChart2.setTransparentCircleRadius(61f);

        mChart2.setDrawCenterText(true);

        mChart2.setRotationEnabled(false);
        mChart2.setHighlightPerTapEnabled(true);

        mChart2.setMaxAngle(180f); // HALF CHART
        mChart2.setRotationAngle(180f);
        mChart2.setCenterTextOffset(0, -20);

        setData(4, 100);

        mChart2.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart2.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart2.setEntryLabelColor(Color.WHITE);
        mChart2.setEntryLabelTypeface(mTfRegular);
        mChart2.setEntryLabelTextSize(12f);


    }

    private void setData(int count, float range) {

        ArrayList<PieEntry> values = new ArrayList<PieEntry>();

        for (int i = 0; i < count; i++) {
            values.add(new PieEntry((float) ((Math.random() * range) + range / 5), mParties[i % mParties.length]));
        }

        PieDataSet dataSet = new PieDataSet(values, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTfLight);
        mChart2.setData(data);

        mChart2.invalidate();
    }

    private void addEntry(Double num) {
        data = mChart.getData();

        ILineDataSet set = data.getDataSetByIndex(0);

        // set.addEntry(...); // can be called as well

        if (set == null) {
            set = createSet(data);

            if (Token.getInstance().getUserDevices().size() != 0) {
                set.setLabel("Temperatura ");
            } else {
                set.setLabel("Temperatura ");
            }

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
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void moveOffScreen() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        int height; // deprecated
        try {
            display.getRealSize(size);
            height = size.y;
        } catch (NoSuchMethodError e) {
            height = display.getHeight();
        }
        int offset = (int) (height * 0.65); /* percent to move */

        RelativeLayout.LayoutParams rlParams =
                (RelativeLayout.LayoutParams) mChart2.getLayoutParams();
        rlParams.setMargins(0, 0, 0, -offset);
        mChart2.setLayoutParams(rlParams);
    }

    public void peticion() {
        progressBar.setVisibility(View.VISIBLE);
        Saño.setEnabled(false);
        Smes.setEnabled(false);
        Rest_adapter rest_adapter = new Rest_adapter();
        Endpoints endpoints = rest_adapter.establecerConexionRest();
        Call<List<Dato>> res = endpoints.HISTO(String.valueOf(calendar.get(Calendar.MONTH)),String.valueOf(calendar.get(Calendar.YEAR)),String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) , obj.getHistorial());
        res.enqueue(new Callback<List<Dato>>() {
            @Override
            public void onResponse(Call<List<Dato>> call, Response<List<Dato>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    removeDataSet();
                    if (!response.body().isEmpty()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            addEntry(response.body().get(i).getTemperatura());
                        }
                    } else {
                        Toast.makeText(ShareViewActivity.this, "No hay datos que mostrar", Toast.LENGTH_SHORT).show();
                        addEntry(0.0);
                    }
                    Saño.setEnabled(true);
                    Smes.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<List<Dato>> call, Throwable t) {
                Toast.makeText(ShareViewActivity.this, "Favor revise su conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        removeDataSet();
        switch (parent.getId()) {
            case R.id.spinnerAño:
               // Naño = (String) Saño.getSelectedItem();
                //peticion();
                break;
            case R.id.spinnerMes:
                //Nmes = String.valueOf(position);
                Toast.makeText(ShareViewActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                //peticion();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
