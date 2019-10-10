package com.official.mq.masque.model;

public class Structure {

    private int id_struktur;
    private int id_masjid;
    private String nama_anggota;
    private String jabatan;
    private String tahun_kerja;

    public Structure(int id_struktur, int id_masjid, String nama_anggota, String jabatan, String tahun_kerja) {
        this.id_struktur = id_struktur;
        this.id_masjid = id_masjid;
        this.nama_anggota = nama_anggota;
        this.jabatan = jabatan;
        this.tahun_kerja = tahun_kerja;
    }

    public int getId_struktur() {
        return id_struktur;
    }

    public int getId_masjid() {
        return id_masjid;
    }

    public String getNama_anggota() {
        return nama_anggota;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getTahun_kerja() {
        return tahun_kerja;
    }
}
