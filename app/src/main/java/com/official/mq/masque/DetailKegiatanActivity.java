package com.official.mq.masque;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;

import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_DARIWAK;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_DESCIPTION;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_IMG;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_IMGMASK;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_LATLONG;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_NAME;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_NAMEM;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_POSTED;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_SAMPAIWAK;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_TANGGAL;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_TEMPAT;

public class DetailKegiatanActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView nameKegiatan, ketKegiatan, tempatKegiatan, tanggalKegiatan, waktuKegiatan, nameMasjid, Posted;
    private ImageView imgKegiatan, imgMasjid;
    private Button btnTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kegiatan);

        imgKegiatan = findViewById(R.id.imageKegiatan);
        nameKegiatan = findViewById(R.id.nameKegiatan);
        ketKegiatan = findViewById(R.id.ketKegiatan);
        tempatKegiatan = findViewById(R.id.tempatKegiatan);
        tanggalKegiatan = findViewById(R.id.tanggalKegiatan);
        waktuKegiatan = findViewById(R.id.waktuKegiatan);
        imgMasjid = findViewById(R.id.imageMasjid);
        nameMasjid = findViewById(R.id.nameMasjid);
        Posted = findViewById(R.id.posted);
        btnTracking = findViewById(R.id.btnTracking);

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

//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setTitle("Detail Kegiatan");
//        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.colorTransparan));

        Intent intent = getIntent();
        String nameK = intent.getStringExtra(EXTRA_NAME);
        String ketK = intent.getStringExtra(EXTRA_DESCIPTION);
        final String tempatK = intent.getStringExtra(EXTRA_TEMPAT);
        String tglK = intent.getStringExtra(EXTRA_TANGGAL);
        String strDari = intent.getStringExtra(EXTRA_DARIWAK);
        String strSampai = intent.getStringExtra(EXTRA_SAMPAIWAK);
        String img = intent.getStringExtra(EXTRA_IMG);
        String posted = intent.getStringExtra(EXTRA_POSTED);
        final String latlong = intent.getStringExtra(EXTRA_LATLONG);
        String nameM = intent.getStringExtra(EXTRA_NAMEM);
        String imgM = intent.getStringExtra(EXTRA_IMGMASK);

        if (img.equals("http://192.168.43.69/masqueapp/assets/img/jadwal/")) {
            imgKegiatan.setVisibility(View.GONE);
        } else {
            Glide.with(this)
                    .load(img)
                    .into(imgKegiatan);
        }
        nameKegiatan.setText(nameK);
        ketKegiatan.setText(ketK);
        tempatKegiatan.setText(tempatK);
        tanggalKegiatan.setText(tglK);
        waktuKegiatan.setText(strDari+ " sampai " + strSampai + " WIB");
        nameMasjid.setText(nameM);
        Glide.with(this)
                .load(imgM)
                .apply(RequestOptions.circleCropTransform())
                .into(imgMasjid);

        if (posted.equals("01")) {
            posted = "Jan";
        } else if (posted.equals("02")) {
            posted = "Feb";
        } else if (posted.equals("03")) {
            posted = "Mar";
        } else if (posted.equals("04")) {
            posted = "Apr";
        } else if (posted.equals("05")) {
            posted = "Mei";
        } else if (posted.equals("06")) {
            posted = "Jun";
        } else if (posted.equals("07")) {
            posted = "Jul";
        } else if (posted.equals("08")) {
            posted = "Agu";
        } else if (posted.equals("09")) {
            posted = "Sep";
        } else if (posted.equals("10")) {
            posted = "Okt";
        } else if (posted.equals("11")) {
            posted = "Nov";
        } else if (posted.equals("12")) {
            posted = "Des";
        }
        Posted.setText(posted);

        btnTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendLok = new Intent(DetailKegiatanActivity.this, LokasiKegiatanActivity.class);
                sendLok.putExtra(EXTRA_TEMPAT, tempatK);
                sendLok.putExtra(EXTRA_LATLONG, latlong);

                startActivity(sendLok);
//                startActivity(new Intent(DetailKegiatanActivity.this, LokasiKegiatanActivity.class));
                // Buat Uri dari intent string. Gunakan hasilnya untuk membuat Intent.
//                gmmIntentUri = Uri.parse("google.navigation:q=" + latlong);

                // Buat Uri dari intent gmmIntentUri. Set action => ACTION_VIEW
//                mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                // Set package Google Maps untuk tujuan aplikasi yang di Intent yaitu google maps
//                mapIntent.setPackage(googleMaps);

//                if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                } else {
//                    Toast.makeText(DetailKegiatanActivity.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
//                            Toast.LENGTH_LONG).show();
//                }
            }
        });
    }
}
