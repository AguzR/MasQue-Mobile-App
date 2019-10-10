package com.official.mq.masque;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView name, nameBar;
    SessionManager sessionManager;
    private CardView linkKegiatan, linkMasjid, linkFind, linkBantuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        name = findViewById(R.id.name);
        nameBar = findViewById(R.id.nameBar);
        linkKegiatan = findViewById(R.id.menuKegiatan);
        linkMasjid = findViewById(R.id.menuMasjid);
        linkFind = findViewById(R.id.menuFind);
        linkBantuan = findViewById(R.id.menuBantuan);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.NAME);

        name.setText(mName);
//        nameBar.setText(mName);

        linkKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iKegiatan = new Intent(MainActivity.this, KegiatanActivity.class);
                startActivity(iKegiatan);
            }
        });

        linkMasjid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMasjid = new Intent(MainActivity.this, MasjidActivity.class);
                startActivity(iMasjid);
            }
        });

        linkBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BantuanActivity.class));
            }
        });

        linkFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // function here...
                getLokasi();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        } else if (id == R.id.nav_info) {
            startActivity(new Intent(MainActivity.this, InfoAppActivity.class));
        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(false);
            builder.setMessage("Apakah kamu ingin keluar ?");
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Tunggu ...");
                    progressDialog.show();
                    sessionManager.logout();
                }
            });
            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getLokasi() {
        //              Buat Uri dari intent string. Gunakan hasilnya untuk membuat Intent.
        String latlong = "-6.868624,109.107347";

        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latlong);

//              Buat Uri dari intent gmmIntentUri. Set action => ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

//              Set package Google Maps untuk tujuan aplikasi yang di Intent yaitu google maps
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(MainActivity.this, "Google Maps Belum Terinstal. Install Terlebih dahulu.",
                    Toast.LENGTH_LONG).show();
        }
    }
}
