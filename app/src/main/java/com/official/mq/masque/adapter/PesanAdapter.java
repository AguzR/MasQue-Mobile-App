package com.official.mq.masque.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.official.mq.masque.R;
import com.official.mq.masque.model.PesanMas;

import java.util.List;

public class PesanAdapter extends RecyclerView.Adapter<PesanAdapter.PesanViewHolder> {

    private Context mCtx;
    private List<PesanMas> pesanMasList;

    public PesanAdapter(Context mCtx, List<PesanMas> pesanMasList){
        this.mCtx = mCtx;
        this.pesanMasList = pesanMasList;
    }

    class PesanViewHolder extends RecyclerView.ViewHolder {

        TextView nameUser, isiPesan, nameMas, mBalasan, statusPsn, vTime;
        ImageView imagePesan;

        public PesanViewHolder(View itemView) {
            super(itemView);

            nameUser = itemView.findViewById(R.id.nameUsers);
            isiPesan = itemView.findViewById(R.id.pesan);
            nameMas = itemView.findViewById(R.id.nameMasjid);
            mBalasan = itemView.findViewById(R.id.balasan);
            imagePesan = itemView.findViewById(R.id.imagePsn);
            statusPsn = itemView.findViewById(R.id.status);
            vTime = itemView.findViewById(R.id.time);
        }
    }

    @Override
    public PesanAdapter.PesanViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.pesanmas_list, null);
        return new PesanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PesanAdapter.PesanViewHolder pesanViewHolder, int i) {
        PesanMas pesanMas = pesanMasList.get(i);

        Glide.with(mCtx)
                .load(pesanMas.getFoto())
                .into(pesanViewHolder.imagePesan);

        pesanViewHolder.nameUser.setText(pesanMas.getDari());
        pesanViewHolder.isiPesan.setText(pesanMas.getPesan());
        pesanViewHolder.nameMas.setText(pesanMas.getKepada());

        if (pesanMas.getBalasan().equals("null")) {
            pesanViewHolder.mBalasan.setText("Belum ada balasan");
        } else {
            pesanViewHolder.mBalasan.setText(pesanMas.getBalasan());
        }

        if (pesanMas.getAc_pm().equals("0")) {
            pesanViewHolder.statusPsn.setText("Belum di Baca");
        } else if (pesanMas.getAc_pm().equals("1")) {
            pesanViewHolder.statusPsn.setText("Sudah di Baca");
        }

        String Time = pesanMas.getTanggal();
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

        pesanViewHolder.vTime.setText(Tanggal+"/"+Bulan+"/"+Tahun);
    }

    @Override
    public int getItemCount() {
        return pesanMasList.size();
    }
}
