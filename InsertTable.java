import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertTable {

    public static void insertTables(Connection connection) {
        try {
            // Memasukkan data dosen
            insertDosen(connection, "Dosen", "NIDN123", "Dosen 1", "NIDN123");
            insertDosen(connection, "Dosen", "NIDN456", "Dosen 2", "NIDN456");
            insertDosen(connection, "Dosen", "NIDN789", "Dosen 3", "NIDN789");
            insertDosen(connection, "Dosen", "NIDN101", "Dosen 4", "NIDN101");
            insertDosen(connection, "Dosen", "NIDN202", "Dosen 5", "NIDN202");

            // Memasukkan data mahasiswa
            insertMahasiswa(connection, "Mahasiswa", "NIM123", "Mahasiswa 1", "NIM123", "Teknik Informatika", 2023);
            insertMahasiswa(connection, "Mahasiswa", "NIM456", "Mahasiswa 2", "NIM456", "Sistem Informasi", 2022);
            insertMahasiswa(connection, "Mahasiswa", "NIM789", "Mahasiswa 3", "NIM789", "Ilmu Komputer", 2022);
            insertMahasiswa(connection, "Mahasiswa", "NIM101", "Mahasiswa 4", "NIM101", "Teknik Elektro", 2022);
            insertMahasiswa(connection, "Mahasiswa", "NIM202", "Mahasiswa 5", "NIM202", "Teknik Mesin", 2023);
            insertMahasiswa(connection, "Mahasiswa", "NIM303", "Mahasiswa 6", "NIM303", "Matematika", 2022);

            // Memasukkan data admin
            insertAdmin(connection, "12345", "Admin", "pass1", "Nama admin1");
            insertAdmin(connection, "67891", "Admin", "pass2", "Nama admin2");

            // Memasukkan data mata kuliah
            insertMataKuliah(connection, "MK1", "Aljabar Linear", 3);
            insertMataKuliah(connection, "MK2", "Interaksi Manusia dan Komputer", 3);
            insertMataKuliah(connection, "MK3", "Pemrograman Berorientasi Objek", 3);
            insertMataKuliah(connection, "MK4", "Sistem Basis Data", 3);
            insertMataKuliah(connection, "MK5", "Sistem Operasi", 3);
            insertMataKuliah(connection, "MK6", "Jaringan Komputer", 3);
            insertMataKuliah(connection, "MK7", "Technopreneurship", 2);
            insertMataKuliah(connection, "MK8", "Riset teknologi informasi", 2);
            insertMataKuliah(connection, "MK9", "Transformasi Digital", 3);
            insertMataKuliah(connection, "MK10", "Supply Chain Management", 2);
            insertMataKuliah(connection, "MK11", "Teori Peluang", 3);
            insertMataKuliah(connection, "MK12", "Matematika Diskrit", 3);
            insertMataKuliah(connection, "MK13", "Persamaan Diferensial Biasa", 3);
            insertMataKuliah(connection, "MK14", "Analisis Suku Bunga Berbasis Web", 3);
            insertMataKuliah(connection, "MK15", "Matematika Bisnis dan Teknologi", 3);
            insertMataKuliah(connection, "MK16", "Kalkulus lanjutan", 3);

            // Memasukkan data Mata Kuliah yang Diajarkan Dosen
            insertMataKuliahDosen(connection, "NIDN123", "MK1");
            insertMataKuliahDosen(connection, "NIDN456", "MK2");
            insertMataKuliahDosen(connection, "NIDN789", "MK3");
            insertMataKuliahDosen(connection, "NIDN101", "MK4");
            insertMataKuliahDosen(connection, "NIDN202", "MK5");
            insertMataKuliahDosen(connection, "NIDN123", "MK6");
            insertMataKuliahDosen(connection, "NIDN456", "MK7");
            insertMataKuliahDosen(connection, "NIDN789", "MK8");
            insertMataKuliahDosen(connection, "NIDN101", "MK9");
            insertMataKuliahDosen(connection, "NIDN202", "MK10");
            insertMataKuliahDosen(connection, "NIDN123", "MK12");
            insertMataKuliahDosen(connection, "NIDN456", "MK11");
            insertMataKuliahDosen(connection, "NIDN789", "MK13");
            insertMataKuliahDosen(connection, "NIDN101", "MK14");
            insertMataKuliahDosen(connection, "NIDN202", "MK15");
            insertMataKuliahDosen(connection, "NIDN123", "MK16");

            System.out.println("Data berhasil dimasukkan ke dalam tabel.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertDosen(Connection connection, String username, String password, String nama, String nidn)
            throws SQLException {
        if (!isDataExist(connection, "dosen", "NIDN", nidn)) {
            String query = "INSERT INTO dosen (Username, Password, Nama, NIDN ) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, nama);
                pstmt.setString(4, nidn);
                pstmt.executeUpdate();
            }
        }
    }

    private static void insertMahasiswa(Connection connection, String Username, String Password, String nama,
            String nim, String prodi, int angkatan) throws SQLException {
        if (!isDataExist(connection, "mahasiswa", "NIM", nim)) {
            String query = "INSERT INTO mahasiswa (Username, Password, Nama, NIM, Prodi, Angkatan) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, Username);
                pstmt.setString(2, Password);
                pstmt.setString(3, nama);
                pstmt.setString(4, nim);
                pstmt.setString(5, prodi);
                pstmt.setInt(6, angkatan);
                pstmt.executeUpdate();
            }
        }
    }

    private static void insertAdmin(Connection connection, String idAdmin, String username, String password,
            String nama) throws SQLException {
        if (!isDataExist(connection, "admin", "idAdmin", idAdmin)) {
            String query = "INSERT INTO admin (idAdmin, Username, Password, Nama) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, idAdmin);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                pstmt.setString(4, nama);
                pstmt.executeUpdate();
            }
        }
    }

    private static void insertMataKuliah(Connection connection, String kodeMataKuliah,
            String namaMataKuliah, int sks) throws SQLException {
        if (!isDataExist(connection, "mataKuliah", "KodeMataKuliah", kodeMataKuliah)) {
            String query = "INSERT INTO mataKuliah (KodeMataKuliah, NamaMataKuliah, SKS) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, kodeMataKuliah);
                pstmt.setString(2, namaMataKuliah);
                pstmt.setInt(3, sks);
                pstmt.executeUpdate();
            }
        }
    }

    private static void insertMataKuliahDosen(Connection connection, String nidnDosen, String kodeMataKuliah)
            throws SQLException {
        String query = "INSERT INTO mataKuliahDosen (nidnDosen, kodeMataKuliah) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nidnDosen);
            pstmt.setString(2, kodeMataKuliah);
            pstmt.executeUpdate();
        }
    }

    // memeriksa apakah data sudah ada dalam tabel
    private static boolean isDataExist(Connection connection, String tableName, String columnName, String value)
            throws SQLException {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, value);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
}
