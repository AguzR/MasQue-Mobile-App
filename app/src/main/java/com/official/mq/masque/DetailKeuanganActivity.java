package com.official.mq.masque;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_BULAN;
import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_JUMLAH;
import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_KPEMASUKAN;
import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_KPENGELUARAN;
import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_PEMASUKAN;
import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_PENGELUARAN;
import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_SALDO;
import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_TAHUN;

public class DetailKeuanganActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView vBulan, vTahun, vJumlah, vSaldo, vPemasukan, vKetPem, vPengeluaran, vKetPeng, vTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_keuangan);

        vBulan = findViewById(R.id.nameBulan);
        vTahun = findViewById(R.id.tahun);
        vJumlah = findViewById(R.id.jumlah);
        vSaldo = findViewById(R.id.saldo);
        vPemasukan = findViewById(R.id.pemasukan);
        vKetPem = findViewById(R.id.ketPemasukan);
        vPengeluaran = findViewById(R.id.pengeluaran);
        vKetPeng = findViewById(R.id.ketPengeluaran);
        vTotal = findViewById(R.id.saatIni);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView)findViewById(R.id.title_bar);
        toolbar.setNavigationIcon(R.drawable.ic_back_dark);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String strBulan = intent.getStringExtra(EXTRA_BULAN);
        String strTahun = intent.getStringExtra(EXTRA_TAHUN);
        String strJumlah = intent.getStringExtra(EXTRA_JUMLAH);
        String strSaldo = intent.getStringExtra(EXTRA_SALDO);
        String strPem = intent.getStringExtra(EXTRA_PEMASUKAN);
        String strKPem = intent.getStringExtra(EXTRA_KPEMASUKAN);
        String strPeng = intent.getStringExtra(EXTRA_PENGELUARAN);
        String strKPeng = intent.getStringExtra(EXTRA_KPENGELUARAN);
        String strTotal = intent.getStringExtra(EXTRA_JUMLAH);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        int SaldoNow = Integer.parseInt(strJumlah);
        int SaldoLalu = Integer.parseInt(strSaldo);
        int Pemasukan = Integer.parseInt(strPem);
        int Pengeluaran = Integer.parseInt(strPeng);

        vBulan.setText(strBulan);
        vTahun.setText(strTahun);
        vJumlah.setText(formatRupiah.format((double) SaldoNow));
        vSaldo.setText(formatRupiah.format((double) SaldoLalu));
        vPemasukan.setText(formatRupiah.format((double) Pemasukan));
        vKetPem.setText(strKPem);
        vPengeluaran.setText(formatRupiah.format((double) Pengeluaran));
        vKetPeng.setText(strKPeng);
        vTotal.setText(formatRupiah.format((double) SaldoNow));
    }
}
