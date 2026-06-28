package org.internal.hire.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "biodata")
public class Biodata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @NotBlank
    @Column(nullable = false)
    private String posisiDilamar;

    @NotBlank
    @Column(nullable = false)
    private String nama;

    @Column(unique = true)
    private String noKtp;

    private String tempatLahir;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalLahir;

    private String jenisKelamin;

    private String agama;

    private String golonganDarah;

    private String status;

    @Column(length = 1000)
    private String alamatKtp;

    @Column(length = 1000)
    private String alamatTinggal;

    private String email;

    private String noTelp;

    private String orangTerdekat;

    @Column(length = 1000)
    private String skill;

    private Boolean bersediaDitempatkan;

    private Long penghasilanDiharapkan;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "biodata", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PendidikanTerakhir> pendidikanList = new ArrayList<>();

    @OneToMany(mappedBy = "biodata", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RiwayatPelatihan> pelatihanList = new ArrayList<>();

    @OneToMany(mappedBy = "biodata", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RiwayatPekerjaan> pekerjaanList = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPosisiDilamar() {
        return posisiDilamar;
    }

    public void setPosisiDilamar(String posisiDilamar) {
        this.posisiDilamar = posisiDilamar;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getGolonganDarah() {
        return golonganDarah;
    }

    public void setGolonganDarah(String golonganDarah) {
        this.golonganDarah = golonganDarah;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlamatKtp() {
        return alamatKtp;
    }

    public void setAlamatKtp(String alamatKtp) {
        this.alamatKtp = alamatKtp;
    }

    public String getAlamatTinggal() {
        return alamatTinggal;
    }

    public void setAlamatTinggal(String alamatTinggal) {
        this.alamatTinggal = alamatTinggal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getOrangTerdekat() {
        return orangTerdekat;
    }

    public void setOrangTerdekat(String orangTerdekat) {
        this.orangTerdekat = orangTerdekat;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Boolean getBersediaDitempatkan() {
        return bersediaDitempatkan;
    }

    public void setBersediaDitempatkan(Boolean bersediaDitempatkan) {
        this.bersediaDitempatkan = bersediaDitempatkan;
    }

    public Long getPenghasilanDiharapkan() {
        return penghasilanDiharapkan;
    }

    public void setPenghasilanDiharapkan(Long penghasilanDiharapkan) {
        this.penghasilanDiharapkan = penghasilanDiharapkan;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<PendidikanTerakhir> getPendidikanList() {
        return pendidikanList;
    }

    public void setPendidikanList(List<PendidikanTerakhir> pendidikanList) {
        this.pendidikanList = pendidikanList;
    }

    public List<RiwayatPelatihan> getPelatihanList() {
        return pelatihanList;
    }

    public void setPelatihanList(List<RiwayatPelatihan> pelatihanList) {
        this.pelatihanList = pelatihanList;
    }

    public List<RiwayatPekerjaan> getPekerjaanList() {
        return pekerjaanList;
    }

    public void setPekerjaanList(List<RiwayatPekerjaan> pekerjaanList) {
        this.pekerjaanList = pekerjaanList;
    }
}

