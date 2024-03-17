import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    public static void createTables(Connection connection) {
        AdminTable(connection);
        DosenTable(connection);
        MahasiswaTable(connection);
        MataKuliahTable(connection);
        KrsTable(connection);
        KhsTable(connection);
        NilaiPerKomponenTable(connection);
        PenilaianDosenTable(connection);
        MataKuliahDosenTable(connection);
    }

    private static void AdminTable(Connection connection) {
        String query = "CREATE TABLE IF NOT EXISTS Admin (" +
                "idAdmin INT(10) PRIMARY KEY," +
                "Username VARCHAR(10) COLLATE utf8mb4_bin," +
                "Password VARCHAR(9) COLLATE utf8mb4_bin," +
                "Nama VARCHAR(100) COLLATE utf8mb4_bin" +
                ")";
        executeQuery(connection, query);
    }

    private static void DosenTable(Connection connection) {
        String query = "CREATE TABLE IF NOT EXISTS Dosen (" +
                "idDosen INT AUTO_INCREMENT PRIMARY KEY," +
                "Username VARCHAR(10) COLLATE utf8mb4_bin," +
                "Password VARCHAR(9) COLLATE utf8mb4_bin," +
                "Nama VARCHAR(100) COLLATE utf8mb4_bin," +
                "NIDN CHAR(10) UNIQUE COLLATE utf8mb4_bin" +
                ")";
        executeQuery(connection, query);
    }

    static void MahasiswaTable(Connection connection) {
        String query = "CREATE TABLE IF NOT EXISTS Mahasiswa (" +
                "idMahasiswa INT AUTO_INCREMENT PRIMARY KEY," +
                "Username VARCHAR(10) COLLATE utf8mb4_bin," +
                "Password VARCHAR(9) COLLATE utf8mb4_bin," +
                "Nama VARCHAR(100) COLLATE utf8mb4_bin," +
                "NIM CHAR(10) UNIQUE COLLATE utf8mb4_bin," +
                "Prodi VARCHAR(50) COLLATE utf8mb4_bin," +
                "Angkatan INT" +
                ")";
        executeQuery(connection, query);
    }

    private static void MataKuliahTable(Connection connection) {
        String query = "CREATE TABLE IF NOT EXISTS mataKuliah (" +
                "idMataKuliah INT AUTO_INCREMENT PRIMARY KEY," +
                "kodeMataKuliah CHAR(4) UNIQUE COLLATE utf8mb4_bin," +
                "namaMataKuliah VARCHAR(100) COLLATE utf8mb4_bin," +
                "sks INT" +
                ")";
        executeQuery(connection, query);
    }

    private static void KrsTable(Connection connection) {
        String query = "CREATE TABLE IF NOT EXISTS Krs (" +
                "idKrs INT AUTO_INCREMENT PRIMARY KEY," +
                "kodeMataKuliah CHAR(4) COLLATE utf8mb4_bin," +
                "nimMahasiswa CHAR(10) COLLATE utf8mb4_bin," +
                "FOREIGN KEY (kodeMataKuliah) REFERENCES mataKuliah(kodeMataKuliah)," +
                "FOREIGN KEY (nimMahasiswa) REFERENCES mahasiswa(NIM)" +
                ") ENGINE=InnoDB;";
        executeQuery(connection, query);
    }

    private static void KhsTable(Connection connection) {
        String query = "CREATE TABLE IF NOT EXISTS KHS (" +
                "idKhs INT AUTO_INCREMENT PRIMARY KEY," +
                "kodeMataKuliah CHAR(4) COLLATE utf8mb4_bin," +
                "nimMahasiswa CHAR(10) COLLATE utf8mb4_bin," +
                "nilaiAkhir DOUBLE," +
                "FOREIGN KEY (kodeMataKuliah) REFERENCES mataKuliah(kodeMataKuliah)," +
                "FOREIGN KEY (nimMahasiswa) REFERENCES mahasiswa(NIM)" +
                ") ENGINE=InnoDB;";
        executeQuery(connection, query);
    }

    private static void NilaiPerKomponenTable(Connection connection) {
        String query = "CREATE TABLE IF NOT EXISTS nilaiPerKomponen (" +
                "idNilaiKomponen INT AUTO_INCREMENT PRIMARY KEY," +
                "nimMahasiswa CHAR(10) COLLATE utf8mb4_bin," +
                "kodeMataKuliah CHAR(4) COLLATE utf8mb4_bin," +
                "nidnDosen CHAR(10) COLLATE utf8mb4_bin," +
                "komponenPenilaian VARCHAR(100) COLLATE utf8mb4_bin," +
                "nilai DOUBLE," +
                "FOREIGN KEY (nimMahasiswa) REFERENCES mahasiswa(NIM)," +
                "FOREIGN KEY (kodeMataKuliah) REFERENCES mataKuliah(kodeMataKuliah)," +
                "FOREIGN KEY (nidnDosen) REFERENCES dosen(NIDN)" +
                ") ENGINE=InnoDB;";
        executeQuery(connection, query);
    }

    private static void PenilaianDosenTable(Connection connection) {
        String query = "CREATE TABLE IF NOT EXISTS penilaianDosen (" +
                "idPenilaian INT AUTO_INCREMENT PRIMARY KEY," +
                "nidnDosen CHAR(10) COLLATE utf8mb4_bin," +
                "komponenPenilaian VARCHAR(100) COLLATE utf8mb4_bin," +
                "persentaseKomponen DOUBLE," +
                "kodeMataKuliah CHAR(4) COLLATE utf8mb4_bin," +
                "FOREIGN KEY (nidnDosen) REFERENCES dosen(NIDN)," +
                "FOREIGN KEY (kodeMataKuliah) REFERENCES mataKuliah(kodeMataKuliah)," +
                "UNIQUE KEY (nidnDosen, kodeMataKuliah, komponenPenilaian)" +
                ") ENGINE=InnoDB;";
        executeQuery(connection, query);
    }

    private static void MataKuliahDosenTable(Connection connection) {
        String query = "CREATE TABLE IF NOT EXISTS mataKuliahDosen (" +
                "idMataKuliahDosen INT AUTO_INCREMENT PRIMARY KEY," +
                "nidnDosen CHAR(10) COLLATE utf8mb4_bin," +
                "kodeMataKuliah CHAR(4) COLLATE utf8mb4_bin," +
                "FOREIGN KEY (nidnDosen) REFERENCES dosen(NIDN)," +
                "FOREIGN KEY (kodeMataKuliah) REFERENCES mataKuliah(kodeMataKuliah)," +
                "UNIQUE KEY (nidnDosen, kodeMataKuliah)" +
                ") ENGINE=InnoDB;";
        executeQuery(connection, query);
    }

    private static void executeQuery(Connection connection, String query) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
