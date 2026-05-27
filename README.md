# Pelindo - Mock Up Aplikasi Pelamar

Aplikasi Spring Boot + Thymeleaf untuk:
- Sign up/login (email + password)
- User mengisi biodata pelamar (data pribadi + tabel anak)
- Admin melihat semua data, detail, edit, delete, dan pencarian

## Stack
- Java 17
- Spring Boot 4 (Web MVC, Security, JPA, Validation)
- Thymeleaf + Bootstrap
- Database: H2 atau MySQL (dipilih dari `application.properties` dengan comment/uncomment)

## Akun Default
- Admin:
  - Email: `admin@pelindo.test`
  - Password: `admin123`

## Konfigurasi Database (H2 atau MySQL)
Atur sumber data di `src/main/resources/application.properties`:

- Aktifkan blok H2, nonaktifkan blok MySQL jika ingin pakai H2.
- Aktifkan blok MySQL, nonaktifkan blok H2 jika ingin pakai MySQL.
- Gunakan tanda `#` untuk menonaktifkan baris properti.

Contoh properti yang diubah:
- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `spring.datasource.driver-class-name`

Catatan:
- `spring.jpa.hibernate.ddl-auto=update` akan membuat/memperbarui tabel otomatis saat aplikasi start.
- Untuk MySQL Laragon, sesuaikan user/password dengan konfigurasi lokal Anda.

## Menjalankan Aplikasi
```powershell
./mvnw.cmd spring-boot:run
```

Akses:
- App: `http://localhost:8080`
- H2 Console (hanya jika H2 aktif): `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:pelindo`
  - User: `sa`
  - Password: kosong


## Menjalankan Test
```powershell
./mvnw.cmd test
```

