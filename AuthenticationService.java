import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationService {
    public User authenticateWithDatabase(Connection connection, String username, String password) {
        try {
            Connection connectionn = ConnectionDB.getConnection();

            User Mahasiswa = authenticateMahasiswa(connectionn, username, password);
            if (Mahasiswa != null) {
                return Mahasiswa;
            }

            User dosen = authenticateDosen(connectionn, username, password);
            if (dosen != null) {
                return dosen;
            }

            User admin = authenticateAdmin(connectionn, username, password);
            if (admin != null) {
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Mahasiswa authenticateMahasiswa(Connection connection, String username, String password)
            throws SQLException {
        String mahasiswaQuery = "SELECT * FROM mahasiswa WHERE Username = ? AND Password = ?";
        try (PreparedStatement mahasiswaPstmt = connection.prepareStatement(mahasiswaQuery)) {
            mahasiswaPstmt.setString(1, username);
            mahasiswaPstmt.setString(2, password);
            ResultSet mahasiswaResultSet = mahasiswaPstmt.executeQuery();

            if (mahasiswaResultSet.next()) {
                String nim = mahasiswaResultSet.getString("Nim");
                String prodi = mahasiswaResultSet.getString("Prodi");
                int angkatan = mahasiswaResultSet.getInt("Angkatan");
                String nama = mahasiswaResultSet.getString("Nama");

                return new Mahasiswa(username, password, nama, nim, prodi, angkatan);
            }
        }
        return null;
    }

    private Dosen authenticateDosen(Connection connection, String username, String password) throws SQLException {
        String dosenQuery = "SELECT * FROM dosen WHERE username = ? AND password = ?";
        try (PreparedStatement dosenPstmt = connection.prepareStatement(dosenQuery)) {
            dosenPstmt.setString(1, username);
            dosenPstmt.setString(2, password);
            ResultSet dosenResultSet = dosenPstmt.executeQuery();

            if (dosenResultSet.next()) {
                String nidn = dosenResultSet.getString("nidn");
                String nama = dosenResultSet.getString("nama");

                return new Dosen(username, password, nama, nidn);
            }
        }
        return null;
    }

    private Admin authenticateAdmin(Connection connection, String username, String password) throws SQLException {
        String adminQuery = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (PreparedStatement adminPstmt = connection.prepareStatement(adminQuery)) {
            adminPstmt.setString(1, username);
            adminPstmt.setString(2, password);
            ResultSet adminResultSet = adminPstmt.executeQuery();

            if (adminResultSet.next()) {
                String IdAdmin = adminResultSet.getString("IdAdmin");
                String namaAdmin = adminResultSet.getString("Nama");

                return new Admin(username, password, namaAdmin, IdAdmin);
            }
        }
        return null;
    }
}
