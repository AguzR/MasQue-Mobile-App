package com.official.mq.masque;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BantuanActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView HideLog, HideReg, HideLLok, HideTLok, HidePesan, HideHis, HideRut, HideMyP;
    private ImageView ALog, AReg, ALLok, ATLok, APesan, AHis, ARut, AMyP;
    private CardView QLogin, QRegist, QLLokasi, QTLok, QPesan, QHis, QRut, QMyP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);

        HideLog = findViewById(R.id.hLogin);
        QLogin = findViewById(R.id.QLogin);
        ALog = findViewById(R.id.ALogin);
        ALog.setVisibility(View.GONE);
        HideLog.setVisibility(View.GONE);

        HideReg = findViewById(R.id.hRegist);
        QRegist = findViewById(R.id.QRegist);
        AReg = findViewById(R.id.ARegist);
        AReg.setVisibility(View.GONE);
        HideReg.setVisibility(View.GONE);

        HideLLok = findViewById(R.id.hLLokasi);
        QLLokasi = findViewById(R.id.QLLokasi);
        ALLok = findViewById(R.id.ALLokasi);
        ALLok.setVisibility(View.GONE);
        HideLLok.setVisibility(View.GONE);

        HideTLok = findViewById(R.id.hTLokasi);
        QTLok = findViewById(R.id.QTlokasi);
        ATLok = findViewById(R.id.ATLokasi);
        ATLok.setVisibility(View.GONE);
        HideTLok.setVisibility(View.GONE);

        HidePesan = findViewById(R.id.hPesan);
        QPesan = findViewById(R.id.QPesan);
        APesan = findViewById(R.id.APesan);
        APesan.setVisibility(View.GONE);
        HidePesan.setVisibility(View.GONE);

        HideHis = findViewById(R.id.hHistori);
        QHis = findViewById(R.id.QHistori);
        AHis = findViewById(R.id.AHistori);
        AHis.setVisibility(View.GONE);
        HideHis.setVisibility(View.GONE);

        HideRut = findViewById(R.id.hRutin);
        QRut = findViewById(R.id.QRutin);
        ARut = findViewById(R.id.ARutin);
        ARut.setVisibility(View.GONE);
        HideRut.setVisibility(View.GONE);

        HideMyP = findViewById(R.id.hMyPes);
        QMyP = findViewById(R.id.QMyPes);
        AMyP = findViewById(R.id.AMyPes);
        AMyP.setVisibility(View.GONE);
        HideMyP.setVisibility(View.GONE);

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

        QLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ALog.setVisibility(View.VISIBLE);
                HideLog.setVisibility(View.VISIBLE);
            }
        });
        HideLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ALog.setVisibility(View.GONE);
                HideLog.setVisibility(View.GONE);
            }
        });

        QRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AReg.setVisibility(View.VISIBLE);
                HideReg.setVisibility(View.VISIBLE);
            }
        });
        HideReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AReg.setVisibility(View.GONE);
                HideReg.setVisibility(View.GONE);
            }
        });

        QLLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ALLok.setVisibility(View.VISIBLE);
                HideLLok.setVisibility(View.VISIBLE);
            }
        });
        HideLLok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ALLok.setVisibility(View.GONE);
                HideLLok.setVisibility(View.GONE);
            }
        });

        QTLok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ATLok.setVisibility(View.VISIBLE);
                HideTLok.setVisibility(View.VISIBLE);
            }
        });
        HideTLok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ATLok.setVisibility(View.GONE);
                HideTLok.setVisibility(View.GONE);
            }
        });

        QPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APesan.setVisibility(View.VISIBLE);
                HidePesan.setVisibility(View.VISIBLE);
            }
        });
        HidePesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APesan.setVisibility(View.GONE);
                HidePesan.setVisibility(View.GONE);
            }
        });

        QHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AHis.setVisibility(View.VISIBLE);
                HideHis.setVisibility(View.VISIBLE);
            }
        });
        HideHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AHis.setVisibility(View.GONE);
                HideHis.setVisibility(View.GONE);
            }
        });

        QRut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARut.setVisibility(View.VISIBLE);
                HideRut.setVisibility(View.VISIBLE);
            }
        });
        HideRut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARut.setVisibility(View.GONE);
                HideRut.setVisibility(View.GONE);
            }
        });

        QMyP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AMyP.setVisibility(View.VISIBLE);
                HideMyP.setVisibility(View.VISIBLE);
            }
        });
        HideMyP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AMyP.setVisibility(View.GONE);
                HideMyP.setVisibility(View.GONE);
            }
        });
    }
}
