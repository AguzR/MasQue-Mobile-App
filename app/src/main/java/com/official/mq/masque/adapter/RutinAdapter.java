package com.official.mq.masque.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.official.mq.masque.R;
import com.official.mq.masque.model.Rutin;

import java.util.List;

public class RutinAdapter extends RecyclerView.Adapter<RutinAdapter.RutinViewHolder>{

    private Context mCtx;
    private List<Rutin> rutinList;

    public RutinAdapter(Context mCtx, List<Rutin> rutinList) {
        this.mCtx = mCtx;
        this.rutinList = rutinList;
    }

    class RutinViewHolder extends RecyclerView.ViewHolder{

        TextView Day, Name, Tempat, Waktu;

        public RutinViewHolder(View itemView) {
            super(itemView);

            Day = itemView.findViewById(R.id.day);
            Name = itemView.findViewById(R.id.name);
            Tempat = itemView.findViewById(R.id.tempat);
            Waktu = itemView.findViewById(R.id.waktu);
        }
    }

    @Override
    public RutinViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rutin_list, null);
        return new RutinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RutinViewHolder rutinViewHolder, int position) {
        Rutin rutin = rutinList.get(position);

        rutinViewHolder.Day.setText(rutin.getHari());
        rutinViewHolder.Name.setText(rutin.getNama_jadwal());
        rutinViewHolder.Tempat.setText(rutin.getTempat());
        rutinViewHolder.Waktu.setText("Dari pukul " + rutin.getDari() + "sampai pukul " + rutin.getSampai());
    }

    @Override
    public int getItemCount() {
        return rutinList.size();
    }
}
