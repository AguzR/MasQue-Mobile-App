package com.official.mq.masque.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.official.mq.masque.R;
import com.official.mq.masque.model.Kegiatan;

import java.util.ArrayList;
import java.util.List;

public class KegiatanAdapter extends RecyclerView.Adapter<KegiatanAdapter.KegiatanViewHolder> implements Filterable {

    private Context mCtx;
    private List<Kegiatan> kegiatanList;
    private OnItemClickListener mListener;
    private ArrayList<Kegiatan> kegiatansList;

    public static final String EXTRA_IDJ = "id_jadwal";
    public static final String EXTRA_IDD = "id_dkm";
    public static final String EXTRA_IDM = "id_masjid";
    public static final String EXTRA_NAME = "nama_jadwal";
    public static final String EXTRA_DESCIPTION = "deskripsi_jadwal";
    public static final String EXTRA_TEMPAT = "tempat";
    public static final String EXTRA_TANGGAL = "tanggal";
    public static final String EXTRA_DARIWAK = "dari_jam";
    public static final String EXTRA_SAMPAIWAK = "sampai_jam";
    public static final String EXTRA_IMG = "foto";
    public static final String EXTRA_POSTED = "posted";
    public static final String EXTRA_LATLONG = "latlong";
    public static final String EXTRA_NAMEM = "nama_masjid";
    public static final String EXTRA_IMGMASK = "image_masjid";

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public KegiatanAdapter(Context mCtx, List<Kegiatan> kegiatanList) {
        this.mCtx = mCtx;
        this.kegiatanList = kegiatanList;
        this.kegiatansList = new ArrayList<>(kegiatanList);
    }

    class KegiatanViewHolder extends RecyclerView.ViewHolder{
        ImageView imageKegiatan, imageMasjid;
        TextView nameKegiatan, ketKegiatan, tempatKegiatan, Posted, NameMasjid;


        public KegiatanViewHolder(View itemView) {
            super(itemView);

            imageKegiatan = itemView.findViewById(R.id.imageKegiatan);
            nameKegiatan = itemView.findViewById(R.id.nameKegiatan);
            ketKegiatan = itemView.findViewById(R.id.ketKegiatan);
            Posted = itemView.findViewById(R.id.posted);
            imageMasjid = itemView.findViewById(R.id.imageMasjid);
            NameMasjid = itemView.findViewById(R.id.nameMasjid);
//            tanggalKegiatan = itemView.findViewById(R.id.tanggalKegiatan);
            tempatKegiatan = itemView.findViewById(R.id.tempatKegiatan);

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
    public KegiatanViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.kegiatan_list, null);
        return new KegiatanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KegiatanViewHolder holder, int position) {
        Kegiatan kegiatan = kegiatanList.get(position);

        if (kegiatan.getFoto().equals("http://192.168.43.69/masqueapp/assets/img/jadwal/")) {
            holder.imageKegiatan.setVisibility(View.GONE);
        } else {
            Glide.with(mCtx)
                    .load(kegiatan.getFoto())
                    .into(holder.imageKegiatan);
        }

        holder.nameKegiatan.setText(kegiatan.getNama_jadwal());
        holder.ketKegiatan.setText(kegiatan.getDeskripsi_jadwal());
        holder.NameMasjid.setText(kegiatan.getName());
        Glide.with(mCtx)
                .load(kegiatan.getImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imageMasjid);
//        holder.tanggalKegiatan.setText(kegiatan.getTanggal());
        holder.tempatKegiatan.setText(kegiatan.getTempat());

        String Time = kegiatan.getPosted();
        String[] dtimeSplit = Time.split("\\s");

        String date = dtimeSplit[0];
        String time = dtimeSplit[1];

        String[] Date = date.split("\\-");
        String Tahun = Date[0];
        String Bulan = Date[1];
        String Tanggal = Date[2];

        String[] waktu = time.split("\\:");
        String jam = waktu[0];
        String menit = waktu[1];
        String detik = waktu[2];

//        int nameBulan = Integer.valueOf(Bulan);
        if (Bulan.equals("01")) {
            Bulan = "Jan";
        } else if (Bulan.equals("02")) {
            Bulan = "Feb";
        } else if (Bulan.equals("03")) {
            Bulan = "Mar";
        } else if (Bulan.equals("04")) {
            Bulan = "Apr";
        } else if (Bulan.equals("05")) {
            Bulan = "Mei";
        } else if (Bulan.equals("06")) {
            Bulan = "Jun";
        } else if (Bulan.equals("07")) {
            Bulan = "Jul";
        } else if (Bulan.equals("08")) {
            Bulan = "Agu";
        } else if (Bulan.equals("09")) {
            Bulan = "Sep";
        } else if (Bulan.equals("10")) {
            Bulan = "Okt";
        } else if (Bulan.equals("11")) {
            Bulan = "Nov";
        } else if (Bulan.equals("12")) {
            Bulan = "Des";
        }

        holder.Posted.setText(Tanggal + " " + Bulan + " " + Tahun + " pukul " + jam + ":" + menit);
    }

    @Override
    public int getItemCount() {
        return kegiatanList.size();
    }

    @Override
    public Filter getFilter() {
        return masjidFilter;
    }

    private Filter masjidFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Kegiatan> filters = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filters.addAll(kegiatansList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Kegiatan item : kegiatansList) {
                    if (item.getNama_jadwal().toLowerCase().contains(filterPattern)) {
                        filters.add(item);
                    } else if (item.getTempat().toLowerCase().contains(filterPattern)) {
                        filters.add(item);
                    } else if (item.getTanggal().toLowerCase().contains(filterPattern)) {
                        filters.add(item);
                    } else if (item.getDari().toLowerCase().contains(filterPattern)) {
                        filters.add(item);
                    } else if (item.getName().toLowerCase().contains(filterPattern)) {
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
            kegiatanList.clear();
            kegiatanList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
