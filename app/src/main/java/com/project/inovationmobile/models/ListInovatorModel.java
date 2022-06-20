package com.project.inovationmobile.models;

public class ListInovatorModel {
    int id_inovator;
    String nama_inovator, alamat_inovator, kategoriInovator, fotoInovator;

    public void setFotoInovator(String fotoInovator) {
        this.fotoInovator = fotoInovator;
    }

    public String getFotoInovator() {
        return fotoInovator;
    }

    public int getId_inovator() {
        return id_inovator;
    }

    public void setId_inovator(int id_inovator) {
        this.id_inovator = id_inovator;
    }

    public String getNama_inovator() {
        return nama_inovator;
    }

    public void setNama_inovator(String nama_inovator) {
        this.nama_inovator = nama_inovator;
    }

    public String getAlamat_inovator() {
        return alamat_inovator;
    }

    public void setAlamat_inovator(String alamat_inovator) {
        this.alamat_inovator = alamat_inovator;
    }

    public String getKategoriInovator() {
        return kategoriInovator;
    }

    public void setKategoriInovator(String kategoriInovator) {
        this.kategoriInovator = kategoriInovator;
    }
}
