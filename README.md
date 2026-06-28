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
  - Email: `admin@hire-hub.test`
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
  - JDBC URL: `jdbc:h2:mem:hire-hub`
  - User: `sa`
  - Password: kosong

## application.properties
```powershell
spring.application.name=pelindo

# Local default profile uses H2 for quick setup.
#spring.datasource.url=jdbc:h2:mem:pelindo;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
#spring.datasource.username=sa
#spring.datasource.password=
#spring.datasource.driver-class-name=org.h2.Driver

# MySQL configuration
spring.datasource.url=jdbc:mysql://localhost:3306/pelindo?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Jakarta
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

```
