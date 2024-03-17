import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class inputNilai {
    private static Scanner scanner = new Scanner(System.in);
    public static Object tampilkanMatakuliahDosen;

    public static void aturKomponenPenilaian(Connection connection, Dosen dosen) throws SQLException {
        List<MataKuliah> mataKuliahDosen = periksaMataKuliahDosen(connection, dosen);

        if (!mataKuliahDosen.isEmpty()) {
            tampilkanMatakuliahDosen(mataKuliahDosen);

            System.out.print("Pilih Matakuliah untuk Mengatur Komponen Penilaian: ");
            int pilihanMatakuliah = scanner.nextInt();

            if (pilihanMatakuliah >= 1 && pilihanMatakuliah <= mataKuliahDosen.size()) {
                MataKuliah matakuliah = mataKuliahDosen.get(pilihanMatakuliah - 1);
                System.out.println("Mengatur komponen penilaian " + matakuliah.getNama() + "\n");

                aturKomponenPenilaian(connection, dosen.getNidn(), matakuliah.getKode());
                System.out.println("\nKomponen penilaian berhasil diatur untuk matakuliah " + matakuliah.getNama());
            } else {
                System.out.println("Pilihan matakuliah tidak valid.");
            }
        } else {
            System.out.println("Dosen tidak mengajar matakuliah apapun.");
        }
    }

    static void aturKomponenPenilaian(Connection connection, String nidnDosen, String kodeMataKuliah)
            throws SQLException {
        System.out.print("Masukkan Jumlah Komponen Penilaian untuk Matakuliah ini: ");
        int jumlahKomponen = scanner.nextInt();
        double[] persentaseKomponen = new double[jumlahKomponen];
        double totalPersentase = 0;
        String[] komponenPenilaian = new String[jumlahKomponen];

        for (int i = 0; i < jumlahKomponen; i++) {
            System.out.print("\nMasukkan Nama Komponen Penilaian ke-" + (i + 1) + ": ");
            komponenPenilaian[i] = scanner.next();

            System.out.print("Masukkan Persentase Nilai untuk Komponen Penilaian ke-" + (i + 1) + ": ");
            persentaseKomponen[i] = scanner.nextDouble();

            if (totalPersentase + persentaseKomponen[i] > 100) {
                System.out.println("Total persentase melebihi 100%. Masukkan kembali persentase.");
                i--;
                continue;
            }
            totalPersentase += persentaseKomponen[i];
        }

        if (totalPersentase != 100) {
            System.out.println("Total persentase tidak sama dengan 100%. Pembatalan pengaturan komponen penilaian.");
            return;
        }

        try {
            simpanKomponenPenilaian(connection, nidnDosen, kodeMataKuliah, komponenPenilaian, persentaseKomponen);
        } catch (SQLException e) {
            System.out.println("Gagal menyimpan komponen penilaian ke database. Silakan coba lagi.");
            e.printStackTrace();
        }
    }

    private static void simpanKomponenPenilaian(Connection connection, String nidnDosen, String kodeMataKuliah,
            String[] komponenPenilaian, double[] persentaseKomponen)
            throws SQLException {
        String query = "INSERT INTO penilaianDosen (nidnDosen, kodeMataKuliah, "
                + "komponenPenilaian, persentaseKomponen) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (int i = 0; i < komponenPenilaian.length; i++) {
                pstmt.setString(1, nidnDosen);
                pstmt.setString(2, kodeMataKuliah);
                pstmt.setString(3, komponenPenilaian[i]);
                pstmt.setDouble(4, persentaseKomponen[i]);
                pstmt.executeUpdate();
            }
        }
    }

    private static boolean cekKHS(Connection connection, String nimMahasiswa, String kodeMataKuliah)
            throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM KHS WHERE nimMahasiswa = ? AND kodeMataKuliah = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nimMahasiswa);
            pstmt.setString(2, kodeMataKuliah);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;
    }

    public static List<MataKuliah> periksaMataKuliahDosen(Connection connection, Dosen dosen) throws SQLException {
        List<MataKuliah> mataKuliahList = new ArrayList<>();

        String query = "SELECT mataKuliah.kodeMataKuliah, mataKuliah.namaMataKuliah, dosen.NIDN " +
                "FROM mataKuliahDosen " +
                "JOIN mataKuliah ON mataKuliahDosen.kodeMataKuliah = mataKuliah.kodeMataKuliah " +
                "JOIN dosen ON mataKuliahDosen.nidnDosen = dosen.NIDN " +
                "WHERE mataKuliahDosen.nidnDosen = ?";

        String nidnDosen = dosen.getNidn();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nidnDosen);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    String kodeMataKuliah = resultSet.getString("kodeMataKuliah");
                    String namaMataKuliah = resultSet.getString("namaMataKuliah");

                    MataKuliah mataKuliah = new MataKuliah(kodeMataKuliah, namaMataKuliah, 0);
                    mataKuliahList.add(mataKuliah);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mataKuliahList;
    }

    private static List<Mahasiswa> pilihMahasiswaDariMataKuliah(Connection connection, String kodeMataKuliah)
            throws SQLException {
        List<Mahasiswa> mahasiswaList = new ArrayList<>();

        String query = "SELECT mahasiswa.NIM, mahasiswa.Nama, mahasiswa.Prodi FROM krs "
                + "JOIN mahasiswa ON krs.nimMahasiswa = mahasiswa.NIM WHERE krs.kodeMataKuliah = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, kodeMataKuliah);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    String nimMahasiswa = resultSet.getString("NIM");
                    String namaMahasiswa = resultSet.getString("Nama");
                    String prodiMahasiswa = resultSet.getString("Prodi");
                    mahasiswaList.add(new Mahasiswa("", "", namaMahasiswa, nimMahasiswa, prodiMahasiswa, 0));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mahasiswaList;
    }

    public static void tampilkanMatakuliahDosen(List<MataKuliah> mataKuliahDosen) {
        System.out.println("\nDaftar Mata kuliah yang Diajar:");
        for (int i = 0; i < mataKuliahDosen.size(); i++) {
            System.out.println((i + 1) + ". " + mataKuliahDosen.get(i).getNama());
        }
    }

    public static void inputNilaiMhs(Connection connection, Dosen dosen) throws SQLException {
        List<MataKuliah> mataKuliahDosen = periksaMataKuliahDosen(connection, dosen);

        if (!mataKuliahDosen.isEmpty()) {
            tampilkanMatakuliahDosen(mataKuliahDosen);

            System.out.print("Pilih Mata Kuliah untuk Menginput nilai: ");
            int pilihanMatakuliah = scanner.nextInt();

            if (pilihanMatakuliah >= 1 && pilihanMatakuliah <= mataKuliahDosen.size()) {
                MataKuliah matakuliah = mataKuliahDosen.get(pilihanMatakuliah - 1);

                List<Mahasiswa> mahasiswaList = pilihMahasiswaDariMataKuliah(connection, matakuliah.getKode());
                System.out.println("\nMatakuliah      : " + matakuliah.getNama());
                System.out.println("Jumlah Mahasiswa: " + mahasiswaList.size());

                for (Mahasiswa mahasiswa : mahasiswaList) {
                    inputNilaiMatakuliah(connection, matakuliah.getKode(), dosen.getNidn(), mahasiswa);
                }

                System.out.println("Nilai berhasil disimpan.");
            } else {
                System.out.println("Pilihan matakuliah tidak valid.");
            }
        } else {
            System.out.println("Dosen tidak mengajar matakuliah apapun.");
        }
    }

    public static void inputNilaiMatakuliah(Connection connection, String kodeMataKuliah, String nidnDosen,
            Mahasiswa mahasiswa) throws SQLException {
        List<String> komponenPenilaian = getKomponenPenilaian(connection, nidnDosen, kodeMataKuliah);
        if (komponenPenilaian.isEmpty()) {
            System.out.println("Tidak ada komponen penilaian yang terdaftar untuk matakuliah ini.");
            aturKomponenPenilaian(connection, nidnDosen, kodeMataKuliah);
            return;
        }

        double totalNilai = 0;
        System.out.println("\nNama Mahasiswa: " + mahasiswa.getNama());
        System.out.println("NIM           : " + mahasiswa.getNim());

        for (String komponen : komponenPenilaian) {
            System.out.print("Masukkan Nilai untuk " + komponen + ": ");
            double nilai = scanner.nextDouble();

            simpanNilaiPerKomponen(connection, mahasiswa.getNim(), nidnDosen, kodeMataKuliah, komponen, nilai);

            double persentase = getPersentaseKomponen(connection, nidnDosen, kodeMataKuliah, komponen);
            double nilaiTiapKomponen = nilai * (persentase / 100.0);

            totalNilai += nilaiTiapKomponen;
        }
        double nilaiAkhir = totalNilai;
        simpanNilaiKHS(connection, mahasiswa.getNim(), kodeMataKuliah, nilaiAkhir);
    }

    private static void simpanNilaiKHS(Connection connection, String nimMahasiswa, String kodeMataKuliah,
            double nilaiAkhir) throws SQLException {
        if (cekKHS(connection, nimMahasiswa, kodeMataKuliah)) {
            System.out.println("Nilai akhir untuk matakuliah ini sudah ada di dalam KHS.");
            return;
        }

        String query = "INSERT INTO KHS (nimMahasiswa, kodeMataKuliah, nilaiAkhir) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nimMahasiswa);
            pstmt.setString(2, kodeMataKuliah);
            pstmt.setDouble(3, nilaiAkhir);
            pstmt.executeUpdate();
        }
    }

    private static void simpanNilaiPerKomponen(Connection connection, String nimMahasiswa, String nidnDosen,
            String kodeMataKuliah, String komponenPenilaian, double nilai) throws SQLException {
        String query = "INSERT INTO nilaiPerKomponen (nidnDosen, nimMahasiswa, kodeMataKuliah, komponenPenilaian, nilai) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nidnDosen);
            pstmt.setString(2, nimMahasiswa);
            pstmt.setString(3, kodeMataKuliah);
            pstmt.setString(4, komponenPenilaian);
            pstmt.setDouble(5, nilai);
            pstmt.executeUpdate();
        }
    }

    private static List<String> getKomponenPenilaian(Connection connection, String nidnDosen, String kodeMataKuliah)
            throws SQLException {
        List<String> komponenPenilaianList = new ArrayList<>();

        String query = "SELECT komponenPenilaian FROM penilaianDosen WHERE nidnDosen = ? AND kodeMataKuliah = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nidnDosen);
            pstmt.setString(2, kodeMataKuliah);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    String komponenPenilaian = resultSet.getString("komponenPenilaian");
                    komponenPenilaianList.add(komponenPenilaian);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return komponenPenilaianList;
    }

    public static void lihatKomponenPenilaian(Connection connection, Dosen dosen, MataKuliah mataKuliah)
            throws SQLException {

        List<MataKuliah> mataKuliahDosen = periksaMataKuliahDosen(connection, dosen);

        if (!mataKuliahDosen.isEmpty()) {
            tampilkanMatakuliahDosen(mataKuliahDosen);

            System.out.print("Pilih Matakuliah yang ingin dilihat komponen penilaiannya: ");
            int pilihanMatakuliah = scanner.nextInt();

            if (pilihanMatakuliah >= 1 && pilihanMatakuliah <= mataKuliahDosen.size()) {
                MataKuliah matakuliah = mataKuliahDosen.get(pilihanMatakuliah - 1);

                List<String> komponenPenilaianList = getKomponenPenilaian(connection, dosen.getNidn(),
                        matakuliah.getKode());

                if (!komponenPenilaianList.isEmpty()) {
                    System.out.println("\nKomponen Penilaian untuk Matakuliah: " + matakuliah.getNama());
                    System.out.println("-----------------------------------------------");
                    System.out.format("|%-20s|%-24s|%n", "Komponen Penilaian", "Persentase");
                    System.out.println("-----------------------------------------------");

                    for (String komponen : komponenPenilaianList) {
                        double persentase = getPersentaseKomponen(connection, dosen.getNidn(),
                                matakuliah.getKode(), komponen);
                        System.out.format("|%-20s|%-24.2f|%n", komponen, persentase);
                    }
                    System.out.println("-----------------------------------------------");
                } else {
                    System.out.println("\nBelum ada komponen penilaian yang diatur untuk matakuliah ini.");
                }
            } else {
                System.out.println("Pilihan matakuliah tidak valid.");
            }
        } else {
            System.out.println("Dosen tidak mengajar matakuliah apapun.");
        }
    }

    private static double getPersentaseKomponen(Connection connection, String nidnDosen, String kodeMataKuliah,
            String komponenPenilaian) throws SQLException {
        String query = "SELECT persentaseKomponen FROM penilaianDosen " +
                "WHERE nidnDosen = ? AND kodeMataKuliah = ? AND komponenPenilaian = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nidnDosen);
            pstmt.setString(2, kodeMataKuliah);
            pstmt.setString(3, komponenPenilaian);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("persentaseKomponen");
                }
            }
        }
        return 0;
    }
}
