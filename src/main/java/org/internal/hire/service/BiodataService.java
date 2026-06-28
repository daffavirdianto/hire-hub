package org.internal.hire.service;

import java.util.ArrayList;
import java.util.List;
import org.internal.hire.entity.Biodata;
import org.internal.hire.entity.PendidikanTerakhir;
import org.internal.hire.entity.RiwayatPekerjaan;
import org.internal.hire.entity.RiwayatPelatihan;
import org.internal.hire.entity.User;
import org.internal.hire.repository.BiodataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BiodataService {

    private static final Logger log = LoggerFactory.getLogger(BiodataService.class);

    private static final int MAX_PENDIDIKAN_ROWS = 3;
    private static final int MAX_PELATIHAN_ROWS = 3;
    private static final int MAX_PEKERJAAN_ROWS = 3;

    private final BiodataRepository biodataRepository;

    public BiodataService(BiodataRepository biodataRepository) {
        this.biodataRepository = biodataRepository;
    }

    public Biodata emptyBiodata(User user) {
        Biodata biodata = new Biodata();
        biodata.setUser(user);
        biodata.setEmail(user.getEmail());
        initializeChildrenSlots(biodata);
        return biodata;
    }

    public Biodata findByUserId(Long userId) {
        Biodata biodata = biodataRepository.findByUserId(userId).orElse(null);
        log.info("Find biodata by userId={}, found={}", userId, biodata != null);
        return biodata;
    }

    public List<Biodata> findAll(String search) {
        List<Biodata> result = biodataRepository.searchUser(search == null ? null : search.trim());
        log.info("Admin list biodata, search='{}', total={}", search, result.size());
        return result;
    }

    public Biodata findByIdOrThrow(Long id) {
        return biodataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Data biodata tidak ditemukan."));
    }

     @Transactional
     public Biodata saveForUser(Biodata form, User user) {
         Biodata existing = biodataRepository.findByUserId(user.getId()).orElse(null);
         Biodata target = existing == null ? new Biodata() : existing;
         if (existing == null) {
             target.setUser(user);
         }
         copyForm(form, target, user.getEmail());
        Biodata saved = biodataRepository.save(target);
        log.info("Save biodata by userId={}, biodataId={}", user.getId(), saved.getId());
        return saved;
    }

     @Transactional
     public Biodata saveForAdmin(Long biodataId, Biodata form) {
         Biodata target = findByIdOrThrow(biodataId);
         copyForm(form, target, target.getUser().getEmail());
         Biodata saved = biodataRepository.save(target);
         log.info("Admin save biodata id={}", saved.getId());
         return saved;
     }

     @Transactional
     public void deleteById(Long id) {
        biodataRepository.deleteById(id);
        log.warn("Delete biodata id={}", id);
    }

    public void initializeChildrenSlots(Biodata biodata) {
        ensurePendidikanSize(biodata, MAX_PENDIDIKAN_ROWS);
        ensurePelatihanSize(biodata, MAX_PELATIHAN_ROWS);
        ensurePekerjaanSize(biodata, MAX_PEKERJAAN_ROWS);
    }

    private void copyForm(Biodata source, Biodata target, String fallbackEmail) {
        target.setPosisiDilamar(source.getPosisiDilamar());
        target.setNama(source.getNama());
        target.setNoKtp(source.getNoKtp());
        target.setTempatLahir(source.getTempatLahir());
        target.setTanggalLahir(source.getTanggalLahir());
        target.setJenisKelamin(source.getJenisKelamin());
        target.setAgama(source.getAgama());
        target.setGolonganDarah(source.getGolonganDarah());
        target.setStatus(source.getStatus());
        target.setAlamatKtp(source.getAlamatKtp());
        target.setAlamatTinggal(source.getAlamatTinggal());
        target.setNoTelp(source.getNoTelp());
        target.setOrangTerdekat(source.getOrangTerdekat());
        target.setSkill(source.getSkill());
        target.setBersediaDitempatkan(source.getBersediaDitempatkan());
        target.setPenghasilanDiharapkan(source.getPenghasilanDiharapkan());
        target.setEmail(source.getEmail() == null || source.getEmail().isBlank() ? fallbackEmail : source.getEmail());

        List<PendidikanTerakhir> pendidikanList = filterValidPendidikan(source.getPendidikanList());
        List<RiwayatPelatihan> pelatihanList = filterValidPelatihan(source.getPelatihanList());
        List<RiwayatPekerjaan> pekerjaanList = filterValidPekerjaan(source.getPekerjaanList());

        target.getPendidikanList().clear();
        for (PendidikanTerakhir item : pendidikanList) {
            item.setBiodata(target);
            target.getPendidikanList().add(item);
        }

        target.getPelatihanList().clear();
        for (RiwayatPelatihan item : pelatihanList) {
            item.setBiodata(target);
            target.getPelatihanList().add(item);
        }

        target.getPekerjaanList().clear();
        for (RiwayatPekerjaan item : pekerjaanList) {
            item.setBiodata(target);
            target.getPekerjaanList().add(item);
        }
    }

    private List<PendidikanTerakhir> filterValidPendidikan(List<PendidikanTerakhir> source) {
        List<PendidikanTerakhir> result = new ArrayList<>();
        if (source == null) {
            return result;
        }
        for (PendidikanTerakhir item : source) {
            if (item != null && hasText(item.getJenjang(), item.getInstitusi(), item.getJurusan(), item.getTahunLulus(), item.getIpk())) {
                result.add(item);
            }
        }
        return result;
    }

    private List<RiwayatPelatihan> filterValidPelatihan(List<RiwayatPelatihan> source) {
        List<RiwayatPelatihan> result = new ArrayList<>();
        if (source == null) {
            return result;
        }
        for (RiwayatPelatihan item : source) {
            if (item != null && hasText(item.getNamaKursus(), item.getSertifikat(), item.getTahun())) {
                result.add(item);
            }
        }
        return result;
    }

    private List<RiwayatPekerjaan> filterValidPekerjaan(List<RiwayatPekerjaan> source) {
        List<RiwayatPekerjaan> result = new ArrayList<>();
        if (source == null) {
            return result;
        }
        for (RiwayatPekerjaan item : source) {
            if (item != null && hasText(item.getNamaPerusahaan(), item.getPosisiTerakhir(), item.getPendapatanTerakhir(), item.getTahun())) {
                result.add(item);
            }
        }
        return result;
    }

    private boolean hasText(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return true;
            }
        }
        return false;
    }

    private void ensurePendidikanSize(Biodata biodata, int size) {
        if (biodata.getPendidikanList() == null) {
            biodata.setPendidikanList(new ArrayList<>());
        }
        while (biodata.getPendidikanList().size() < size) {
            biodata.getPendidikanList().add(new PendidikanTerakhir());
        }
    }

    private void ensurePelatihanSize(Biodata biodata, int size) {
        if (biodata.getPelatihanList() == null) {
            biodata.setPelatihanList(new ArrayList<>());
        }
        while (biodata.getPelatihanList().size() < size) {
            biodata.getPelatihanList().add(new RiwayatPelatihan());
        }
    }

    private void ensurePekerjaanSize(Biodata biodata, int size) {
        if (biodata.getPekerjaanList() == null) {
            biodata.setPekerjaanList(new ArrayList<>());
        }
        while (biodata.getPekerjaanList().size() < size) {
            biodata.getPekerjaanList().add(new RiwayatPekerjaan());
        }
    }
}

