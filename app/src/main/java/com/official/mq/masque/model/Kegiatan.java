package com.official.mq.masque.model;

public class Kegiatan {

    private int id_jadwal;
    private int id_masjid;
    private String nama_jadwal;
    private String deskripsi_jadwal;
    private String tempat;
    private String tanggal;
    private String dari;
    private String sampai;
    private String foto;
    private String posted;
    private String latlong;
    private String name;
    private String image;

    public Kegiatan (int id_jadwal, int id_masjid, String nama_jadwal,
                     String deskripsi_jadwal, String tempat, String tanggal, String dari, String sampai,
                     String foto, String posted, String latlong, String name, String image) {
        this.id_jadwal = id_jadwal;
        this.id_masjid = id_masjid;
        this.nama_jadwal = nama_jadwal;
        this.deskripsi_jadwal = deskripsi_jadwal;
        this.tempat = tempat;
        this.tanggal = tanggal;
        this.dari = dari;
        this.sampai = sampai;
        this.foto = foto;
        this.posted = posted;
        this.latlong = latlong;
        this.name = name;
        this.image = image;
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

    public String getTanggal() {
        return tanggal;
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

    public String getLatlong() {
        return latlong;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
