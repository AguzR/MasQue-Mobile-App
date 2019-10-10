package com.official.mq.masque;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView name;
    private Menu action;
    SessionManager sessionManager;
    private ImageView toProfile;
    private CardView linkInfoApps, linkKegiatan, linkMasjid, linkReport, linkBantuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toProfile = findViewById(R.id.toProfile);
        name = findViewById(R.id.name);
        linkInfoApps = findViewById(R.id.infoapps);
        linkKegiatan = findViewById(R.id.menuKegiatan);
        linkMasjid = findViewById(R.id.menuMasjid);
        linkReport = findViewById(R.id.menuReport);
        linkBantuan = findViewById(R.id.menuBantuan);

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // Menghapus title default
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Mengambil akses TextView yang ada di dalam Toolbar
//        TextView mTitle = (TextView) toolbar.findViewById(R.id.title_bar);
//        toolbar.setLogo(R.drawable.ic_mqstyle);
//        ImageView img = (ImageView) toolbar.findViewById(R.id.imgMQ);


        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.NAME);

        name.setText(mName);

        linkInfoApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, InfoAppActivity.class));
            }
        });

        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });

        linkKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iKegiatan = new Intent(HomeActivity.this, KegiatanActivity.class);
                startActivity(iKegiatan);
            }
        });

        linkMasjid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMasjid = new Intent(HomeActivity.this, MasjidActivity.class);
                startActivity(iMasjid);
            }
        });

//        linkReport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, ReportActivity.class));
//            }
//        });

        linkBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, BantuanActivity.class));
            }
        });

//        printKeyHash();

    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.official.mq.masque", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update((signature.toByteArray()));
                Log.d("KEYHASH", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.option_menu, menu);
//
//        action = menu;
//        action.findItem(R.id.profile).setVisible(true);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId()==R.id.profile){
//            action.findItem(R.id.profile).setVisible(true);
//            startActivity(new Intent(this, ProfileActivity.class));
//        }
////        else if (item.getItemId()==R.id.logout) {
////            sessionManager.logout();
////        }
//
//        return true;
//    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Apakah kamu ingin keluar ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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
}
