package com.abdilahstudio.firebasecrud;

import java.io.Serializable;

public class Dosen implements Serializable {
    private String nik;
    private String nama;
    private String jabatan;
    private String key;

    public Dosen() {
    }

    public Dosen(String nik, String nama, String jabatan) {
        this.nik = nik;
        this.nama = nama;
        this.jabatan = jabatan;
    }

    public Dosen(String nik, String nama, String jabatan, String key) {
        this.nik = nik;
        this.nama = nama;
        this.jabatan = jabatan;
        this.key = key;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return " " + nik + " " + nama + " " + jabatan;
    }
}
