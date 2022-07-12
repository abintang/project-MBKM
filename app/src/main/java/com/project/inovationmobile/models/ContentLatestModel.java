package com.project.inovationmobile.models;

public class ContentLatestModel {
    int id_inovasi, totalData;
    String nama_inovasi, nama_inovator, kategoriInovasi, urlGambar;

    public void setTotalData(int totalData) {
        this.totalData = totalData;
    }

    public int getTotalData() {
        return totalData;
    }

    public String getUrlGambar() {
        return urlGambar;
    }

    public void setUrlGambar(String urlGambar) {
        this.urlGambar = urlGambar;
    }

    public int getId_inovasi() {
        return id_inovasi;
    }

    public String getKategoriInovasi() {
        return kategoriInovasi;
    }

    public void setKategoriInovasi(String kategoriInovasi) {
        this.kategoriInovasi = kategoriInovasi;
    }

    public void setId_inovasi(int id_inovasi) {
        this.id_inovasi = id_inovasi;
    }

    public String getNama_inovasi() {
        return nama_inovasi;
    }

    public void setNama_inovasi(String nama_inovasi) {
        this.nama_inovasi = nama_inovasi;
    }

    public String getNama_inovator() {
        return nama_inovator;
    }

    public void setNama_inovator(String nama_inovator) {
        this.nama_inovator = nama_inovator;
    }
}
