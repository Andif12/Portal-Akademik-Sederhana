import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PortalAkademik {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        AuthenticationService authService = new AuthenticationService();
        Connection connection = ConnectionDB.getConnection();
        // CreateTable.createTables(connection);
        // InsertTable.insertTables(connection);
        try {
            connection = ConnectionDB.getConnection();
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            User authenticatedUser = authService.authenticateWithDatabase(connection, username, password);

            if (authenticatedUser != null) {
                System.out.println("\nAnda berhasil login sebagai " + authenticatedUser.getRole());

                if (authenticatedUser instanceof Mahasiswa) {
                    Mahasiswa mahasiswa = (Mahasiswa) authenticatedUser;
                    System.out.println("\nNama    : " + mahasiswa.getNama());
                    System.out.println("NIM     : " + mahasiswa.getNim());
                    System.out.println("Prodi   : " + mahasiswa.getProdi());
                    System.out.println("Angkatan: " + mahasiswa.getAngkatan());

                    System.out.println("\nSelamat datang, " + mahasiswa.getNama());
                    System.out.println("Silahkan pilih salah satu menu berikut:");
                    System.out.println("1. Melihat KRS");
                    System.out.println("2. Lihat KHS");
                    System.out.println("3. Pilih KRS");
                    System.out.println("4. Logout");
                    int menuChoice = getMenuChoice();
                    boolean logout = false;
                    while (!logout) {
                        switch (menuChoice) {
                            case 1:
                                BacaKRSDariDatabase(mahasiswa, connection);
                                break;
                            case 2:
                                LihatKHS.lihatKHS(mahasiswa, connection);
                                break;
                            case 3:
                                pilihKRS(mahasiswa, connection);
                                break;
                            case 4:
                                System.out.println("\nAnda berhasil logout.");
                                logout = true;
                                break;
                            default:
                                System.out.println("Pilihan tidak valid.");
                        }
                        if (!logout) {
                            System.out.println("\nSilahkan pilih salah satu menu berikut:");
                            System.out.println("1. Melihat KRS");
                            System.out.println("2. Lihat KHS");
                            System.out.println("3. Pilih KRS");
                            System.out.println("4. Logout");
                            menuChoice = getMenuChoice();
                        }
                    }
                } else if (authenticatedUser instanceof Dosen) {
                    Dosen dosen = (Dosen) authenticatedUser;
                    System.out.println("\nNama: " + dosen.getNama());
                    System.out.println("NIDN: " + dosen.getNidn());

                    System.out.println("\nSelamat datang, " + dosen.getNama());
                    System.out.println("\nSilahkan pilih salah satu menu berikut:");
                    System.out.println("1. Lihat daftar mata kuliah yang diajar");
                    System.out.println("2. Atur Komponen Penilaian");
                    System.out.println("3. Lihat Komponen Penilaian");
                    System.out.println("4. Input nilai mahasiswa");
                    System.out.println("5. Logout");

                    int menuChoice = getMenuChoice();
                    boolean logout = false;
                    while (!logout) {

                        switch (menuChoice) {
                            case 1:
                                tampilkanDaftarMataKuliahDosen(dosen, connection);
                                break;
                            case 2:
                                inputNilai.aturKomponenPenilaian(connection, dosen);
                                break;
                            case 3:
                                inputNilai.lihatKomponenPenilaian(connection, dosen, null);
                                break;
                            case 4:
                                inputNilai.inputNilaiMhs(connection, dosen);
                                break;
                            case 5:
                                System.out.println("\nAnda berhasil logout.");
                                logout = true;
                                break;
                            default:
                                System.out.println("Pilihan tidak valid.");
                        }
                        if (!logout) {
                            System.out.println("\nSilahkan pilih salah satu menu berikut:");
                            System.out.println("1. Lihat daftar mata kuliah yang diajar");
                            System.out.println("2. Atur Komponen Penilaian");
                            System.out.println("3. Lihat Komponen Penilaian");
                            System.out.println("4. Input nilai mahasiswa");
                            System.out.println("5. Logout");
                            menuChoice = getMenuChoice();
                        }
                    }
                } else if (authenticatedUser instanceof Admin) {
                    Admin admin = (Admin) authenticatedUser;
                    System.out.println("\nNama    : " + admin.getNama());
                    System.out.println("ID Admin: " + admin.getAdminId());

                    System.out.println("\nSelamat datang, " + admin.getNama());
                    System.out.println("Silahkan pilih salah satu menu berikut:");
                    System.out.println("1. Tampilkan data");
                    System.out.println("2. Tambahkan data");
                    System.out.println("3. Hapus data");
                    System.out.println("4. Logout");
                    int menuChoice = getMenuChoice();
                    boolean logout = false;
                    while (!logout) {
                        switch (menuChoice) {
                            case 1:
                                menuTampilkanData(connection);
                                break;
                            case 2:
                                menuTambahData(connection);
                                break;
                            case 3:
                                menuHapusData(connection);
                                break;
                            case 4:
                                System.out.println("\nAnda berhasil logout.");
                                logout = true;
                                break;
                            default:
                                System.out.println("Pilihan tidak valid.");
                        }
                        if (!logout) {
                            System.out.println("\nSilahkan pilih salah satu menu berikut:");
                            System.out.println("1. Tampilkan data");
                            System.out.println("2. Tambahkan data");
                            System.out.println("3. Hapus data");
                            System.out.println("4. Logout");
                            menuChoice = getMenuChoice();
                        }
                    }
                }

            } else {
                System.out.println("Login gagal. Periksa kembali username dan password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            scanner.close();
        }
    }

    private static int getMenuChoice() {
        int menuChoice = -1;
        try {
            System.out.print("Masukkan pilihan Anda: ");
            menuChoice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Masukkan pilihan yang valid.");
            scanner.nextLine();
        }
        return menuChoice;
    }

    private static void tampilkanDaftarMataKuliahDosen(Dosen dosen, Connection connection) {
        try {
            String query = "SELECT mataKuliahDosen.kodeMataKuliah, mataKuliah.namaMataKuliah " +
                    "FROM mataKuliahDosen " +
                    "JOIN mataKuliah ON mataKuliahDosen.kodeMataKuliah = mataKuliah.kodeMataKuliah " +
                    "WHERE mataKuliahDosen.nidnDosen = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, dosen.getNidn());
                ResultSet resultSet = pstmt.executeQuery();

                if (!resultSet.next()) {
                    System.out
                            .println("\nDosen dengan Nama " + dosen.getNama() + " tidak mengajar mata kuliah apapun.");
                } else {
                    System.out.println("\nDaftar Mata Kuliah yang Diajar oleh " + dosen.getNidn() + "\n");
                    System.out.println("-----------------------------------------------------------");
                    System.out.printf("|%-15s| %-40s|\n", "Kode", "Nama Mata Kuliah");
                    System.out.println("|---------------|-----------------------------------------|");
                    do {
                        String kodeMataKuliah = resultSet.getString("kodeMataKuliah");
                        String namaMataKuliah = resultSet.getString("namaMataKuliah");

                        System.out.printf("|%-15s| %-40s|\n", kodeMataKuliah, namaMataKuliah);
                    } while (resultSet.next());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void pilihKRS(Mahasiswa mahasiswa, Connection connection) {
        List<MataKuliah> krsMahasiswa = new ArrayList<>();
        int totalSKS = 0;

        tampilkanDataMataKuliah(connection);

        while (true) {
            System.out.println("\nTotal SKS: " + totalSKS);
            System.out.print("Pilih mata kuliah (0/n/no untuk berhenti): ");
            String kodeMataKuliah = scanner.next();

            if (kodeMataKuliah.equals("0") || kodeMataKuliah.equalsIgnoreCase("n")
                    || kodeMataKuliah.equalsIgnoreCase("no")) {
                break;
            }

            MataKuliah mataKuliah = cariMataKuliah(connection, kodeMataKuliah);

            if (mataKuliah != null) {
                if (!mataKuliahSudahDipilih(krsMahasiswa, kodeMataKuliah)) {
                    if (totalSKS + mataKuliah.getSks() <= 24) {
                        krsMahasiswa.add(mataKuliah);
                        totalSKS += mataKuliah.getSks();
                        System.out.println("Mata kuliah berhasil ditambahkan.");
                    } else {
                        System.out.println("Jumlah SKS melebihi batas maksimal.");
                    }
                } else {
                    System.out.println("Mata kuliah dengan kode " + kodeMataKuliah + " sudah ada di KRS.");
                }
            } else {
                System.out.println("Mata kuliah dengan kode " + kodeMataKuliah + " tidak ditemukan.");
            }
        }

        simpanKRSKeDatabase(mahasiswa, krsMahasiswa, connection);

        System.out.println("Terima kasih! Daftar mata kuliah Anda telah disimpan.");
    }

    private static boolean mataKuliahSudahDipilih(List<MataKuliah> krsMahasiswa, String kodeMataKuliah) {
        for (MataKuliah mataKuliah : krsMahasiswa) {
            if (mataKuliah.getKode().equals(kodeMataKuliah)) {
                return true;
            }
        }
        return false;
    }

    private static List<MataKuliah> ambilDataMataKuliahDariDatabase(Connection connection) {
        List<MataKuliah> daftarMataKuliah = new ArrayList<>();
        String query = "SELECT kodeMataKuliah, namaMataKuliah, sks FROM mataKuliah ORDER BY CAST(SUBSTRING(kodeMataKuliah, 3) AS SIGNED)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String kodeMataKuliah = resultSet.getString("kodeMataKuliah");
                String namaMataKuliah = resultSet.getString("namaMataKuliah");
                int sks = resultSet.getInt("sks");
                daftarMataKuliah.add(new MataKuliah(kodeMataKuliah, namaMataKuliah, sks));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarMataKuliah;
    }

    private static void tampilkanDataMataKuliah(Connection connection) {
        List<MataKuliah> daftarMataKuliah = ambilDataMataKuliahDariDatabase(connection);
        System.out.println("\nDaftar Mata Kuliah:");
        for (MataKuliah mataKuliah : daftarMataKuliah) {
            System.out.println(
                    mataKuliah.getKode() + " - " + mataKuliah.getNama() + " (SKS: " + mataKuliah.getSks() + ")");
        }
    }

    private static MataKuliah cariMataKuliah(Connection connection, String kodeMataKuliah) {
        String query = "SELECT kodeMataKuliah, namaMataKuliah, sks FROM mataKuliah WHERE kodeMataKuliah = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, kodeMataKuliah);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                String namaMataKuliah = resultSet.getString("namaMataKuliah");
                int sks = resultSet.getInt("sks");
                return new MataKuliah(kodeMataKuliah, namaMataKuliah, sks);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void BacaKRSDariDatabase(Mahasiswa mahasiswa, Connection connection) {
        System.out.println("\nKartu Rencana Studi " + mahasiswa.getNama());

        try {
            String query = "SELECT * FROM krs " +
                    "INNER JOIN mataKuliah ON krs.kodeMataKuliah = mataKuliah.kodeMataKuliah " +
                    "WHERE krs.nimMahasiswa = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, mahasiswa.getNim());
                ResultSet resultSet = pstmt.executeQuery();

                if (!resultSet.next()) {
                    System.out.println("KRS dengan nim " + mahasiswa.getNim() + " tidak ditemukan.");

                    System.out.print("\nIngin memilih KRS? (y/n): ");
                    String pilih = scanner.next();
                    if (pilih.equalsIgnoreCase("y")) {
                        pilihKRS(mahasiswa, connection);
                        return;
                    }
                } else {
                    System.out.printf("\n%-15s %-40s %s\n", "Kode", "Nama Mata Kuliah", "SKS");

                    int totalSKS = 0;

                    do {
                        String kodeMataKuliah = resultSet.getString("kodeMataKuliah");
                        String namaMataKuliah = resultSet.getString("namaMataKuliah");
                        int sks = resultSet.getInt("SKS");

                        System.out.printf("%-15s %-40s %s \n", kodeMataKuliah, namaMataKuliah, sks);

                        totalSKS += sks;
                    } while (resultSet.next());

                    System.out.printf("\t\t\t\t\t Jumlah SKS: " + totalSKS + " SKS\n");

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void simpanKRSKeDatabase(Mahasiswa mahasiswa, List<MataKuliah> krsMahasiswa, Connection connection) {
        try {
            connection.setAutoCommit(false);

            for (MataKuliah mataKuliah : krsMahasiswa) {
                simpanMataKuliahKRS(connection, mahasiswa.getNim(), mataKuliah);
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void simpanMataKuliahKRS(Connection connection, String nim, MataKuliah mataKuliah)
            throws SQLException {
        String query = "INSERT INTO krs (nimMahasiswa, kodeMataKuliah) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nim);
            pstmt.setString(2, mataKuliah.getKode());
            pstmt.executeUpdate();
        }
    }

    private static void menuTampilkanData(Connection connection) {
        System.out.println("\nMenu Tampilkan data");
        System.out.println("1. Tampilkan Data Mahasiswa");
        System.out.println("2. Tampilkan Data Dosen");
        System.out.println("3. Tampilkan Data Admin");
        System.out.println("4. Tampilkan Data Mata Kuliah");
        System.out.println("5. Tampilkan Mata Kuliah yang Diajarkan Dosen");
        int subMenuChoice = getMenuChoice();

        switch (subMenuChoice) {
            case 1:
                TampilkanData.tampilkanDataMahasiswa(connection);
                break;
            case 2:
                TampilkanData.tampilkanDataDosen(connection);
                break;
            case 3:
                TampilkanData.tampilkanDataAdmin(connection);
                break;
            case 4:
                TampilkanData.tampilkanDataMataKuliah(connection);
                break;
            case 5:
                TampilkanData.lihatMataKuliahDosen(connection);
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    private static void menuTambahData(Connection connection) {
        System.out.println("\nMenu tambah data");
        System.out.println("1. Tambah Data Mahasiswa");
        System.out.println("2. Tambah Data Dosen");
        System.out.println("3. Tambah Data Admin");
        System.out.println("4. Tambah Data Mata Kuliah");
        System.out.println("5. Tambah Mata Kuliah yang Diajarkan Dosen");

        int subMenuChoice = getMenuChoice();

        switch (subMenuChoice) {
            case 1:
                TambahData.tambahDataMahasiswa(connection);
                break;
            case 2:
                TambahData.tambahDataDosen(connection);
                break;
            case 3:
                TambahData.tambahDataAdmin(connection);
                break;
            case 4:
                TambahData.tambahDataMataKuliah(connection);
                break;
            case 5:
                TambahData.tambahMataKuliahDosen(connection);
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    private static void menuHapusData(Connection connection) {
        System.out.println("\nMenu untuk Menghapus Data");
        System.out.println("1. Hapus Data Mahasiswa");
        System.out.println("2. Hapus Data Dosen");
        System.out.println("3. Hapus Data Admin");
        System.out.println("4. Hapus Data Mata Kuliah");
        System.out.println("5. Hapus Data Mata Kuliah yang Diajarkan Dosen");

        int subMenuChoice = getMenuChoice();

        switch (subMenuChoice) {
            case 1:
                HapusData.hapusDataMahasiswa(connection);
                break;
            case 2:
                HapusData.hapusDataDosen(connection);
                break;
            case 3:
                HapusData.hapusDataAdmin(connection);
                break;
            case 4:
                HapusData.hapusDataMataKuliah(connection);
                break;
            case 5:
                HapusData.hapusMataKuliahDosen(connection);
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }
}