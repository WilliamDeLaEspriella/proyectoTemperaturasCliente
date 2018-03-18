package edu.temp.udc.proyectotempe.Vista.fragment.fragment_usuario;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import edu.temp.udc.proyectotempe.ApiRest.model.UserDevice;
import edu.temp.udc.proyectotempe.R;
import edu.temp.udc.proyectotempe.Vista.ShareViewActivity;
import edu.temp.udc.proyectotempe.interf.onMoveAndSwipedListener;
import edu.temp.udc.proyectotempe.tempo.Token;

/**
 * Created by Usuario on 16/11/2017.
 */

public class AdapterUserDevice extends RecyclerView.Adapter<AdapterUserDevice.pruevaViewHolder> implements View.OnTouchListener, onMoveAndSwipedListener {
    List<UserDevice> userDevice;
    Context context;
    private int color = 0;

    public AdapterUserDevice(Context context, List<UserDevice> userDevice) {
        this.context = context;
        this.userDevice = userDevice;

    }

    public void setItems(int color) {
        this.color = color;
        notifyDataSetChanged();
    }


    @Override
    public pruevaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false);
        v.setOnTouchListener(this);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_recycler_item_show);
        v.startAnimation(animation);
        return new pruevaViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final pruevaViewHolder holder, final int position) {
        final UserDevice userDevic = this.userDevice.get(position);
        holder.nombre.setText(userDevic.getNombre().toUpperCase());
       // holder.edad.setText(userDevic.getEdad() + " Años");
        holder.device.setText("conectado a: " + userDevic.getDevice());
        holder.dato.setText(userDevic.getDato() + "º");

//        holder.device.setText(userDevic.getDevice().getNombre());
        holder.apellido.setText(userDevic.getApellido().toUpperCase());
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
        holder.mView.startAnimation(animation);
        double dato=Double.parseDouble(userDevic.getDato());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (dato < 36) {
                holder.dato.setTextColor(context.getResources().getColor(R.color.colorFreezer));
                holder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorFreezer)));

            } else if (dato >= 36 && dato < 37) {
                holder.dato.setTextColor(context.getResources().getColor(R.color.colorNormal));
                holder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorNormal)));
            } else if (dato >= 37 && dato < 38) {
                holder.dato.setTextColor(context.getResources().getColor(R.color.colorAdver));
                holder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorAdver)));
            } else if (dato >= 38) {
                holder.dato.setTextColor(context.getResources().getColor(R.color.colorHot));
                holder.rela_round.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorHot)));
            }
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShareViewActivity.class);
                intent.putExtra("color", color);
                intent.putExtra("object", (Serializable) userDevice.get(position));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation
                                ((Activity) context, holder.mView, "shareView").toBundle());
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return userDevice.size();

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(userDevice, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }
    public void updateData(List<UserDevice> viewModels) {
        userDevice.clear();
        userDevice.addAll(viewModels);
        notifyDataSetChanged();
    }
    @Override
    public void onItemDismiss(int position) {
        userDevice.remove(position);
        notifyItemRemoved(position);
    }

    public void setFilter(List<UserDevice> p) {
        userDevice = new ArrayList<>();
        userDevice.addAll(p);
        notifyDataSetChanged();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ObjectAnimator upAnim = ObjectAnimator.ofFloat(view, "translationZ", 16);
                upAnim.setDuration(150);
                upAnim.setInterpolator(new DecelerateInterpolator());
                upAnim.start();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                ObjectAnimator downAnim = ObjectAnimator.ofFloat(view, "translationZ", 0);
                downAnim.setDuration(150);
                downAnim.setInterpolator(new AccelerateInterpolator());
                downAnim.start();
                break;
        }
        return false;
    }

    public static class pruevaViewHolder extends RecyclerView.ViewHolder  {
        TextView nombre;
        TextView apellido;
        //TextView edad;
        TextView device;
        TextView dato;
        View mView;
        RelativeLayout rela_round;
        // RelativeLayout cabecera;

        public pruevaViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombreU);
            apellido = (TextView) itemView.findViewById(R.id.apellidoU);
            //edad = (TextView) itemView.findViewById(R.id.edadU);
            device = (TextView) itemView.findViewById(R.id.deviceU);
            dato = (TextView) itemView.findViewById(R.id.datoU);
            mView = itemView;
            rela_round = (RelativeLayout) itemView.findViewById(R.id.rela_round);

        }


    }


}

