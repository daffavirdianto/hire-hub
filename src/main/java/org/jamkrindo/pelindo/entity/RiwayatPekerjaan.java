package org.jamkrindo.pelindo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "riwayat_pekerjaan")
public class RiwayatPekerjaan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biodata_id", nullable = false)
    private Biodata biodata;

    private String namaPerusahaan;
    private String posisiTerakhir;
    private String pendapatanTerakhir;
    private String tahun;

    public Long getId() {
        return id;
    }

    public Biodata getBiodata() {
        return biodata;
    }

    public void setBiodata(Biodata biodata) {
        this.biodata = biodata;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public void setNamaPerusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
    }

    public String getPosisiTerakhir() {
        return posisiTerakhir;
    }

    public void setPosisiTerakhir(String posisiTerakhir) {
        this.posisiTerakhir = posisiTerakhir;
    }

    public String getPendapatanTerakhir() {
        return pendapatanTerakhir;
    }

    public void setPendapatanTerakhir(String pendapatanTerakhir) {
        this.pendapatanTerakhir = pendapatanTerakhir;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }
}

