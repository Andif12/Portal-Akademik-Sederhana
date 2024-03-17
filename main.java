// import java.sql.Connection;
// import java.sql.SQLException;
// import java.sql.Statement;

// public class main {
// public static void main(String[] args) throws SQLException {
// // AuthenticationService authService = new AuthenticationService();
// Connection connection = ConnectionDB.getConnection();
// if (connection == null) {
// System.out.println("Tabel gagal dibuat");
// }
// AdminTable(connection);
// DosenTable(connection);
// MahasiswaTable(connection);
// MataKuliahTable(connection);
// KrsTable(connection);
// KhsTable(connection);
// NilaiPerKomponenTable(connection);
// PenilaianDosenTable(connection);
// MataKuliahDosenTable(connection);
// InsertTable.insertTables(connection);
// }

// public static void createTables(Connection connection) {
// AdminTable(connection);
// DosenTable(connection);
// MahasiswaTable(connection);
// MataKuliahTable(connection);
// KrsTable(connection);
// KhsTable(connection);
// NilaiPerKomponenTable(connection);
// PenilaianDosenTable(connection);
// MataKuliahDosenTable(connection);
// }

// private static void AdminTable(Connection connection) {
// String query = "CREATE TABLE IF NOT EXISTS admin (" +
// "idAdmin INT AUTO_INCREMENT PRIMARY KEY," +
// "Username VARCHAR(10)," +
// "Password VARCHAR(9)," +
// "Nama VARCHAR(100)" +
// ")";
// executeQuery(connection, query);
// }

// private static void DosenTable(Connection connection) {
// String query = "CREATE TABLE IF NOT EXISTS dosen (" +
// "idDosen INT AUTO_INCREMENT PRIMARY KEY," +
// "Username VARCHAR(10)," +
// "Password VARCHAR(9)," +
// "Nama VARCHAR(100)," +
// "NIDN CHAR(10) UNIQUE" +
// ")";
// executeQuery(connection, query);
// }

// static void MahasiswaTable(Connection connection) {
// String query = "CREATE TABLE IF NOT EXISTS mahasiswa (" +
// "idMahasiswa INT AUTO_INCREMENT PRIMARY KEY," +
// "Username VARCHAR(10)," +
// "Password VARCHAR(9)," +
// "Nama VARCHAR(100)," +
// "NIM CHAR(10) UNIQUE," +
// "Prodi VARCHAR(50)," +
// "Angkatan INT" +
// ")";
// executeQuery(connection, query);
// }

// private static void MataKuliahTable(Connection connection) {
// String query = "CREATE TABLE IF NOT EXISTS mataKuliah (" +
// "idMataKuliah INT AUTO_INCREMENT PRIMARY KEY," +
// "kodeMataKuliah CHAR(4) UNIQUE," +
// "namaMataKuliah VARCHAR(100)," +
// "sks INT" +
// ")";
// executeQuery(connection, query);
// }

// private static void KrsTable(Connection connection) {
// String query = "CREATE TABLE IF NOT EXISTS Krs (" +
// "idKrs INT AUTO_INCREMENT PRIMARY KEY," +
// "kodeMataKuliah CHAR(4)," +
// "nimMahasiswa CHAR(10)," +
// "FOREIGN KEY (kodeMataKuliah) REFERENCES mataKuliah(kodeMataKuliah)," +
// "FOREIGN KEY (nimMahasiswa) REFERENCES mahasiswa(NIM)" +
// ") ENGINE=InnoDB;";
// executeQuery(connection, query);
// }

// private static void KhsTable(Connection connection) {
// String query = "CREATE TABLE IF NOT EXISTS KHS (" +
// "idKhs INT AUTO_INCREMENT PRIMARY KEY," +
// "kodeMataKuliah CHAR(4)," +
// "nimMahasiswa CHAR(10)," +
// "FOREIGN KEY (kodeMataKuliah) REFERENCES mataKuliah(kodeMataKuliah)," +
// "FOREIGN KEY (nimMahasiswa) REFERENCES mahasiswa(NIM)" +
// ") ENGINE=InnoDB;";
// executeQuery(connection, query);
// }

// private static void NilaiPerKomponenTable(Connection connection) {
// String query = "CREATE TABLE IF NOT EXISTS nilaiPerKomponen (" +
// "idNilaiKomponen INT AUTO_INCREMENT PRIMARY KEY," +
// "nimMahasiswa CHAR(10)," +
// "kodeMataKuliah CHAR(4)," +
// "nidnDosen CHAR(10)," +
// "komponenPenilaian VARCHAR(100)," +
// "nilai DOUBLE," +
// "FOREIGN KEY (nimMahasiswa) REFERENCES mahasiswa(NIM)," +
// "FOREIGN KEY (kodeMataKuliah) REFERENCES mataKuliah(kodeMataKuliah)," +
// "FOREIGN KEY (nidnDosen) REFERENCES dosen(NIDN)" +
// ") ENGINE=InnoDB;";
// executeQuery(connection, query);
// }

// private static void PenilaianDosenTable(Connection connection) {
// String query = "CREATE TABLE IF NOT EXISTS penilaianDosen (" +
// "idPenilaian INT AUTO_INCREMENT PRIMARY KEY," +
// "nidnDosen CHAR(10)," +
// "komponenPenilaian VARCHAR(100)," +
// "persentaseKomponen DOUBLE," +
// "kodeMataKuliah CHAR(4)," +
// "FOREIGN KEY (nidnDosen) REFERENCES dosen(NIDN)," +
// "FOREIGN KEY (kodeMataKuliah) REFERENCES mataKuliah(kodeMataKuliah)," +
// "UNIQUE KEY (nidnDosen, kodeMataKuliah, komponenPenilaian)" +
// ") ENGINE=InnoDB;";
// executeQuery(connection, query);
// }

// private static void MataKuliahDosenTable(Connection connection) {
// String query = "CREATE TABLE IF NOT EXISTS mataKuliahDosen (" +
// "idMataKuliahDosen INT AUTO_INCREMENT PRIMARY KEY," +
// "nidnDosen CHAR(10)," +
// "kodeMataKuliah CHAR(4)," +
// "FOREIGN KEY (nidnDosen) REFERENCES dosen(NIDN)," +
// "FOREIGN KEY (kodeMataKuliah) REFERENCES mataKuliah(kodeMataKuliah)," +
// "UNIQUE KEY (nidnDosen, kodeMataKuliah)" +
// ") ENGINE=InnoDB;";
// executeQuery(connection, query);
// }

// private static void executeQuery(Connection connection, String query) {
// try (Statement statement = connection.createStatement()) {
// statement.executeUpdate(query);
// } catch (SQLException e) {
// e.printStackTrace();
// }
// }

// }
