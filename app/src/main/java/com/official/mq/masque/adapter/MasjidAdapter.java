package com.official.mq.masque.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.official.mq.masque.model.Masjid;
import com.official.mq.masque.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MasjidAdapter extends RecyclerView.Adapter<MasjidAdapter.MasjidViewHolder> implements Filterable {

    private Context mCtx;
    private List<Masjid> masjidList;
    private OnItemClickListener mListener;
    private ArrayList<Masjid> masjidsList;

    public static final String EXTRA_ID = "id_masjid";
    public static final String EXTRA_IDU = "id_users";
    public static final String EXTRA_ACTIVATED = "activated_masjid";
    public static final String EXTRA_NAME = "nama_masjid";
    public static final String EXTRA_THN = "tahun_berdiri_masjid";
    public static final String EXTRA_ALAMAT = "alamat_masjid";
    public static final String EXTRA_LUAS = "luas_tanah";
    public static final String EXTRA_STATUS = "status_tanah";
    public static final String EXTRA_DES = "deskripsi_masjid";
    public static final String EXTRA_SEJ = "sejarah_masjid";
    public static final String EXTRA_NOTLP = "nomor_telepon_masjid";
    public static final String EXTRA_IMG = "image_masjid";
    public static final String EXTRA_LATLONG = "latlong";

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public MasjidAdapter(Context mCtx, List<Masjid> masjidList) {
        this.mCtx = mCtx;
        this.masjidList = masjidList;
        this.masjidsList = new ArrayList<>(masjidList);
    }

    @Override
    public MasjidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.masjid_list, null);
        return new MasjidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MasjidViewHolder holder, int position) {
        Masjid masjid = masjidList.get(position);

        Glide.with(mCtx)
                .load(masjid.getImage_masjid())
                .into(holder.imageMasjid);

        holder.nameMasjid.setText(masjid.getNama_masjid());
    }

    @Override
    public int getItemCount() {
        return masjidList.size();
    }

    class MasjidViewHolder extends RecyclerView.ViewHolder {

        TextView nameMasjid;
        ImageView imageMasjid;

        public MasjidViewHolder(View itemView) {
            super(itemView);

            nameMasjid = itemView.findViewById(R.id.nameMasjid);
            imageMasjid = itemView.findViewById(R.id.imageMasjid);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return masjidFilter;
    }

    private Filter masjidFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Masjid> filters = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filters.addAll(masjidsList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Masjid item : masjidsList) {
                    if (item.getNama_masjid().toLowerCase().contains(filterPattern)) {
                        filters.add(item);
                    } else if (item.getJenis_masjid().toLowerCase().contains(filterPattern)) {
                        filters.add(item);
                    } else if (item.getAlamat_masjid().toLowerCase().contains(filterPattern)) {
                        filters.add(item);
                    } else if (item.getStatus_tanah().toLowerCase().contains(filterPattern)) {
                        filters.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filters;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            masjidList.clear();
            masjidList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
