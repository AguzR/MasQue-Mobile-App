package com.official.mq.masque.model;

public class Rutin {

    private int id_jadwal;
    private int id_masjid;
    private String nama_jadwal;
    private String deskripsi_jadwal;
    private String tempat;
    private String hari;
    private String dari;
    private String sampai;
    private String foto;
    private String posted;

    public Rutin(int id_jadwal, int id_masjid, String nama_jadwal, String deskripsi_jadwal, String tempat, String hari,
                 String dari, String sampai, String foto, String posted) {
        this.id_jadwal = id_jadwal;
        this.id_masjid = id_masjid;
        this.nama_jadwal = nama_jadwal;
        this.deskripsi_jadwal = deskripsi_jadwal;
        this.tempat = tempat;
        this.hari = hari;
        this.dari = dari;
        this.sampai = sampai;
        this.foto = foto;
        this.posted = posted;
    }

    public int getId_jadwal() {
        return id_jadwal;
    }

    public int getId_masjid() {
        return id_masjid;
    }

    public String getNama_jadwal() {
        return nama_jadwal;
    }

    public String getDeskripsi_jadwal() {
        return deskripsi_jadwal;
    }

    public String getTempat() {
        return tempat;
    }

    public String getHari() {
        return hari;
    }

    public String getDari() {
        return dari;
    }

    public String getSampai() {
        return sampai;
    }

    public String getFoto() {
        return foto;
    }

    public String getPosted() {
        return posted;
    }
}
