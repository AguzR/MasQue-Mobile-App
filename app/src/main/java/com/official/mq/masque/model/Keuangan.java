package com.official.mq.masque.model;

public class Keuangan {

    private int id_keuangan;
    private int id_masjid;
    private String bulan;
    private String tahun;
    private String saldo;
    private String jenis_keuangan;
    private String dana_keuangan;
    private String ket_keuangan;
    private String jumlah;
    private String created_at;

    public Keuangan(int id_keuangan, int id_masjid, String bulan, String tahun, String saldo, String jenis_keuangan,
                    String dana_keuangan, String ket_keuangan, String jumlah, String created_at) {
        this.id_keuangan = id_keuangan;
        this.id_masjid = id_masjid;
        this.bulan = bulan;
        this.tahun = tahun;
        this.saldo = saldo;
        this.jenis_keuangan = jenis_keuangan;
        this.dana_keuangan = dana_keuangan;
        this.ket_keuangan = ket_keuangan;
        this.jumlah = jumlah;
        this.created_at = created_at;
    }

    public int getId_keuangan() {
        return id_keuangan;
    }

    public int getId_masjid() {
        return id_masjid;
    }

    public String getBulan() {
        return bulan;
    }

    public String getTahun() {
        return tahun;
    }

    public String getSaldo() {
        return saldo;
    }

    public String getJenis_keuangan() {
        return jenis_keuangan;
    }

    public String getDana_keuangan() {
        return dana_keuangan;
    }

    public String getKet_keuangan() {
        return ket_keuangan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getCreated_at() {
        return created_at;
    }
}
