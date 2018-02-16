package edu.temp.udc.proyectotempe.Vista.fragment.fragment_inicio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.temp.udc.proyectotempe.ApiRest.model.Dato;
import edu.temp.udc.proyectotempe.R;

/**
 * Created by zhang on 2016.08.07.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private Context context;
    private List<Dato> mItems;
    private int color = 0;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        this.mItems = new ArrayList<>();
    }


    public void addItem(int position, double tempe, Date date) {

        mItems.add(position, new Dato(tempe, date));
        notifyItemInserted(position);
    }

    public void removeItem() {
        mItems.clear();

        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_dato, parent, false);

        Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_recycler_item_show);
        v.startAnimation(animation);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        Dato dato = mItems.get(position);
        holder.textDato.setText(String.valueOf(dato.getTemperatura())+"°");
        holder.textFecha.setText(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(dato.getDate()));
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
        holder.mView.startAnimation(animation);


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView textDato;
        private TextView textFecha;

        private RecyclerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            textDato = (TextView) itemView.findViewById(R.id.textdato);
            textFecha = (TextView) itemView.findViewById(R.id.textFecha);
        }
    }

}
