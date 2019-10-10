package com.official.mq.masque;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_ALAMAT;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_DES;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_ID;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_IDU;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_IMG;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_LATLONG;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_LUAS;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_NAME;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_NOTLP;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_SEJ;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_STATUS;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_THN;

public class DetailMasjidActivity extends AppCompatActivity {

    private Button linkLokasi;
    private Toolbar toolbar;
    private TextView idMasjid, idUsers, NameMasjid, KetMasjid, sejMas, tahunMas, AlamatMas, TanahMas, StatusMas, NoMas;
    private ImageView imgMasjid, linkPesan, linkEvent, linkUang, linkStructure;
    int getId, getIdU;
    String nameMas, imgMas, latLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_masjid);

        idMasjid = findViewById(R.id.idMasjid);
        idUsers = findViewById(R.id.idUsers);
        NameMasjid = findViewById(R.id.nameMasjid);
        KetMasjid = findViewById(R.id.ketMasjid);
        sejMas = findViewById(R.id.sejarah);
        tahunMas = findViewById(R.id.berdiriTahun);
        AlamatMas = findViewById(R.id.alamat);
        imgMasjid = findViewById(R.id.imgMasjid);
        TanahMas = findViewById(R.id.luasTanah);
        StatusMas = findViewById(R.id.statusTanah);
        NoMas = findViewById(R.id.nomorTlp);
        linkPesan = findViewById(R.id.menuPesan);
        linkEvent = findViewById(R.id.menuEvent);
        linkUang = findViewById(R.id.menuUang);
        linkStructure = findViewById(R.id.menuStruktur);
        linkLokasi = findViewById(R.id.btnLokasi);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_back_dark);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linkEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iKegiatanList = new Intent(DetailMasjidActivity.this, MasjidKegiatanActivity.class);
                iKegiatanList.putExtra(EXTRA_ID, getId);
                iKegiatanList.putExtra(EXTRA_NAME, nameMas);
                iKegiatanList.putExtra(EXTRA_IMG, imgMas);

                startActivity(iKegiatanList);
            }
        });

        linkPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pesan = new Intent(DetailMasjidActivity.this, PesanActivity.class);
                pesan.putExtra(EXTRA_ID, getId);
                pesan.putExtra(EXTRA_IDU, getIdU);
                pesan.putExtra(EXTRA_NAME, nameMas);

                startActivity(pesan);
            }
        });

        linkStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent structure = new Intent(DetailMasjidActivity.this, StructureActivity.class);
                structure.putExtra(EXTRA_ID, getId);
                structure.putExtra(EXTRA_NAME, nameMas);
                structure.putExtra(EXTRA_IMG, imgMas);

                startActivity(structure);
            }
        });

        linkUang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keuangan = new Intent(DetailMasjidActivity.this, KeuanganActivity.class);
                keuangan.putExtra(EXTRA_ID, getId);
                keuangan.putExtra(EXTRA_NAME, nameMas);
                keuangan.putExtra(EXTRA_IMG, imgMas);

                startActivity(keuangan);
            }
        });

        linkLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lokMas = new Intent(DetailMasjidActivity.this, LokasiMasjidActivity.class);
                lokMas.putExtra(EXTRA_ID, getId);
                lokMas.putExtra(EXTRA_LATLONG, latLong);

                startActivity(lokMas);
            }
        });

        Intent intent = getIntent();
        int id = intent.getIntExtra(EXTRA_ID, 0);
        int idU = intent.getIntExtra(EXTRA_IDU, 0);
        String name = intent.getStringExtra(EXTRA_NAME);
        String ket = intent.getStringExtra(EXTRA_DES);
        String sej = intent.getStringExtra(EXTRA_SEJ);
        String alamat = intent.getStringExtra(EXTRA_ALAMAT);
        String tahun = intent.getStringExtra(EXTRA_THN);
        String luas = intent.getStringExtra(EXTRA_LUAS);
        String status = intent.getStringExtra(EXTRA_STATUS);
        String no = intent.getStringExtra(EXTRA_NOTLP);
        String img = intent.getStringExtra(EXTRA_IMG);
        String latlong = intent.getStringExtra(EXTRA_LATLONG);

        getId = id;
        getIdU = idU;
        nameMas = name;
        imgMas = img;
        latLong = latlong;

        idMasjid.setText("" + id);
        idMasjid.setVisibility(View.GONE);
        idUsers.setText(""+ idU);
        idUsers.setVisibility(View.GONE);
        Glide.with(this)
                .load(img)
                .into(imgMasjid);
        NameMasjid.setText(name);
        KetMasjid.setText(ket);
        AlamatMas.setText(alamat);
        tahunMas.setText(tahun);
        TanahMas.setText(luas);
        StatusMas.setText(status);

        String NoTlp = no;
        if (NoTlp.isEmpty()) {
            NoMas.setText("Nomor Tidak Ada");
        } else {
            NoMas.setText(no);
        }

        if (sej.equals("")) {
            sejMas.setText("Tidak ada data");
        } else {
            sejMas.setText(sej);
        }
    }
}
