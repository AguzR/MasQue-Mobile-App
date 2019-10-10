package com.official.mq.masque.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.official.mq.masque.R;
import com.official.mq.masque.model.Structure;

import java.util.List;

public class StructureAdapter extends RecyclerView.Adapter<StructureAdapter.StructureViewHolder> {

    private Context mCtx;
    private List<Structure> structureList;
    private OnItemClickListener kListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener klistener){
        kListener = klistener;
    }

    public StructureAdapter(Context mCtx, List<Structure> structureList) {
        this.mCtx = mCtx;
        this.structureList = structureList;
    }

    class StructureViewHolder extends RecyclerView.ViewHolder{

        TextView Jabatan, Nama;

        public StructureViewHolder(View itemView) {
            super(itemView);

            Jabatan = (TextView) itemView.findViewById(R.id.jabatan);
            Nama = (TextView) itemView.findViewById(R.id.nama);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (kListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            kListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public StructureAdapter.StructureViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.structure_list, null);
        return new StructureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StructureAdapter.StructureViewHolder structureViewHolder, int position) {
        Structure structure = structureList.get(position);

        structureViewHolder.Jabatan.setText(structure.getJabatan());
        structureViewHolder.Nama.setText(structure.getNama_anggota());
    }

    @Override
    public int getItemCount() {
        return structureList.size();
    }
}
