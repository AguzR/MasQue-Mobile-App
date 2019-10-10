package com.official.mq.masque.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.official.mq.masque.R;
import com.official.mq.masque.model.Keuangan;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KeuanganAdapter extends RecyclerView.Adapter<KeuanganAdapter.KeuanganViewHolder> implements Filterable {

    private Context mCtx;
    private List<Keuangan> keuanganList;
    private OnItemClickListener kListener;
    private ArrayList<Keuangan> keuangansList;

    public static final String EXTRA_IDK = "id_keuangan";
    public static final String EXTRA_IDM = "id_masjid";
    public static final String EXTRA_IDU = "id_users";
    public static final String EXTRA_BULAN = "bulan";
    public static final String EXTRA_TAHUN = "tahun";
    public static final String EXTRA_SALDO = "saldo";
    public static final String EXTRA_PEMASUKAN = "pemasukan";
    public static final String EXTRA_KPEMASUKAN = "ket_pemasukan";
    public static final String EXTRA_PENGELUARAN = "pengeluaran";
    public static final String EXTRA_KPENGELUARAN = "ket_pengeluaran";
    public static final String EXTRA_JUMLAH = "jumlah";


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener klistener){
        kListener = klistener;
    }

    public KeuanganAdapter(Context mCtx, List<Keuangan> keuanganList){
        this.mCtx = mCtx;
        this.keuanganList = keuanganList;
        this.keuangansList = new ArrayList<>(keuanganList);
    }

    class KeuanganViewHolder extends RecyclerView.ViewHolder{

        TextView nameBulan, tTahun, tJenis, tKet, tDana, tJml, tTgl;

        public KeuanganViewHolder(View itemView) {
            super(itemView);

            nameBulan = itemView.findViewById(R.id.nameBulan);
            tTahun = itemView.findViewById(R.id.tahun);
            tJenis = itemView.findViewById(R.id.jenis);
            tKet = itemView.findViewById(R.id.keterangan);
            tDana = itemView.findViewById(R.id.dana);
            tJml = itemView.findViewById(R.id.jumlah);
            tTgl = itemView.findViewById(R.id.tanggal);

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
    public KeuanganAdapter.KeuanganViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.keuangan_list, null);
        return new KeuanganViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KeuanganAdapter.KeuanganViewHolder keuanganViewHolder, int position) {
        Keuangan keuangan = keuanganList.get(position);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        int Danna = Integer.parseInt(keuangan.getDana_keuangan());
        int Jummlah = Integer.parseInt(keuangan.getJumlah());

        keuanganViewHolder.nameBulan.setText(keuangan.getBulan());
        keuanganViewHolder.tTahun.setText(keuangan.getTahun());
        keuanganViewHolder.tJenis.setText(keuangan.getJenis_keuangan());

        if (keuangan.getJenis_keuangan().equals("Pemasukan")) {
            keuanganViewHolder.tKet.setText("Dari " + keuangan.getKet_keuangan());
        } else {
            keuanganViewHolder.tKet.setText("Untuk " + keuangan.getKet_keuangan());
        }

        keuanganViewHolder.tDana.setText("Sebesar " + formatRupiah.format((double) Danna));
        keuanganViewHolder.tJml.setText("Keuangan saat ini " + formatRupiah.format((double) Jummlah));
        keuanganViewHolder.tTgl.setText("Pada hari " + keuangan.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return keuanganList.size();
    }

    @Override
    public Filter getFilter() {
        return keuanganFilter;
    }

    private Filter keuanganFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Keuangan> filters = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filters.addAll(keuangansList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Keuangan item : keuangansList) {
                    if (item.getBulan().toLowerCase().contains(filterPattern) || item.getTahun().toLowerCase().contains(filterPattern)) {
                        filters.add(item);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filters;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            keuanganList.clear();
            keuanganList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
