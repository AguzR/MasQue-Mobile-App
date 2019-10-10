package com.official.mq.masque;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_ALAMAT;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_LATLONG;

public class LokasiMasjidActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar toolbar;
    Double lat,longs, latlongs, kosongs;
    String name, lokasi;

    private GoogleMap mMap;
    private SupportMapFragment mapFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_masjid);

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

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapViewInfo);

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapViewInfo);


        mapFrag.getMapAsync(this);

        Intent intent = getIntent();
        name = intent.getStringExtra(EXTRA_ALAMAT);
        lokasi = intent.getStringExtra(EXTRA_LATLONG);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        String latlong = "-6.8852996 109.1348533";

//        String[] latlo = latlong.split("\\s");

        String[] latlo = lokasi.split("\\,");

        String lat1 = latlo[0];
        String lat2 = latlo[1];

        lat = Double.valueOf(lat1);
        longs = Double.valueOf(lat2);
        LatLng latLng = new LatLng(lat, longs);

        mMap.addMarker(new MarkerOptions().position(latLng).title(name).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
