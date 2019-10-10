package com.official.mq.masque.model;

public class Masjid {

    private int id_masjid;
    private int id_users;
    private String activated_masjid;
    private String nama_masjid;
    private String tahun_berdiri_masjid;
    private String alamat_masjid;
    private String jenis_masjid;
    private String status_tanah;
    private String deskripsi_masjid;
    private String sejarah_masjid;
    private String nomor_telepon_masjid;
    private String image_masjid;
    private String latlong;

    public Masjid(int id_masjid, int id_users, String activated_masjid, String nama_masjid, String tahun_berdiri_masjid,
                  String alamat_masjid, String jenis_masjid, String status_tanah, String deskripsi_masjid, String sejarah_masjid,
                  String nomor_telepon_masjid, String image_masjid, String latlong) {
        this.id_masjid = id_masjid;
        this.id_users = id_users;
        this.activated_masjid = activated_masjid;
        this.nama_masjid = nama_masjid;
        this.tahun_berdiri_masjid = tahun_berdiri_masjid;
        this.alamat_masjid = alamat_masjid;
        this.jenis_masjid = jenis_masjid;
        this.status_tanah = status_tanah;
        this.deskripsi_masjid = deskripsi_masjid;
        this.sejarah_masjid = sejarah_masjid;
        this.nomor_telepon_masjid = nomor_telepon_masjid;
        this.image_masjid = image_masjid;
        this.latlong = latlong;
    }

    public int getId_masjid() {
        return id_masjid;
    }

    public int getId_users() {
        return id_users;
    }

    public String getNama_masjid() {
        return nama_masjid;
    }

    public String getActivated_masjid() {
        return activated_masjid;
    }

    public String getTahun_berdiri_masjid() {
        return tahun_berdiri_masjid;
    }

    public String getAlamat_masjid() {
        return alamat_masjid;
    }

    public String getJenis_masjid() {
        return jenis_masjid;
    }

    public String getStatus_tanah() {
        return status_tanah;
    }

    public String getDeskripsi_masjid() {
        return deskripsi_masjid;
    }

    public String getSejarah_masjid() {
        return sejarah_masjid;
    }

    public String getNomor_telepon_masjid() {
        return nomor_telepon_masjid;
    }

    public String getImage_masjid() {
        return image_masjid;
    }

    public String getLatlong() {
        return latlong;
    }
}
