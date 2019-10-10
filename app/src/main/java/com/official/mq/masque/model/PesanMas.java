package com.official.mq.masque.model;

public class PesanMas {

    private String id_pesan;
    private String id_users;
    private String id_masjid;
    private String id_pengguna;
    private String dari;
    private String kepada;
    private String foto;
    private String pesan;
//    private String status;
    private String balasan;
    private String tanggal;
    private String ac_pm;

    public PesanMas(String id_pesan, String id_users, String id_masjid, String id_pengguna, String dari, String kepada,
                    String foto, String pesan, String balasan, String tanggal, String ac_pm) {
        this.id_pesan = id_pesan;
        this.id_users = id_users;
        this.id_masjid = id_masjid;
        this.id_pengguna = id_pengguna;
        this.dari = dari;
        this.kepada = kepada;
        this.foto = foto;
        this.pesan = pesan;
        this.balasan = balasan;
        this.tanggal = tanggal;
        this.ac_pm = ac_pm;
    }

    public String getId_pesan() {
        return id_pesan;
    }

    public String getId_users() {
        return id_users;
    }

    public String getId_masjid() {
        return id_masjid;
    }

    public String getId_pengguna() {
        return id_pengguna;
    }

    public String getDari() {
        return dari;
    }

    public String getKepada() {
        return kepada;
    }

    public String getFoto() {
        return foto;
    }

    public String getPesan() {
        return pesan;
    }

    public String getBalasan() {
        return balasan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getAc_pm() {
        return ac_pm;
    }
}
