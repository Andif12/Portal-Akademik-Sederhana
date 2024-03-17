import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Scanner;

public class TampilkanData {
    public static void tampilkanDataMahasiswa(Connection connection) {
        try {
            String query = "SELECT * FROM mahasiswa ORDER BY Nim";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                ResultSet resultSet = pstmt.executeQuery();

                System.out.println("\nData Mahasiswa:");
                System.out.println("-------------------------------------------------------------------------------");
                System.out.printf("|%-15s|%-30s|%-20s|%-10s|\n", "Nim", "Nama", "Prodi", "Angkatan");
                System.out.println("|---------------|------------------------------|--------------------|----------|");

                while (resultSet.next()) {
                    String nim = resultSet.getString("nim");
                    String nama = resultSet.getString("nama");
                    String prodi = resultSet.getString("prodi");
                    int angkatan = resultSet.getInt("angkatan");

                    System.out.printf("|%-15s|%-30s|%-20s|%-10d|\n", nim, nama, prodi, angkatan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void tampilkanDataDosen(Connection connection) {
        try {
            String query = "SELECT * FROM dosen ORDER BY NIDN";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                ResultSet resultSet = pstmt.executeQuery();

                System.out.println("\nData Dosen:");
                System.out.println("------------------------------------------------");
                System.out.printf("|%-15s|%-30s|\n", "NIDN", "Nama");
                System.out.println("|---------------|------------------------------|");

                while (resultSet.next()) {
                    String nidn = resultSet.getString("nidn");
                    String nama = resultSet.getString("nama");

                    System.out.printf("|%-15s|%-30s|\n", nidn, nama);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void tampilkanDataMataKuliah(Connection connection) {
        try {
            String query = "SELECT kodeMataKuliah, namaMataKuliah, sks FROM mataKuliah "
                    + "ORDER BY CAST(SUBSTRING(kodeMataKuliah, 3) AS SIGNED)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                ResultSet resultSet = pstmt.executeQuery();

                System.out.println("\nData Mata Kuliah:");
                System.out.println("----------------------------------------------------------------");
                System.out.printf("|%-15s|%-40s|%-5s|\n", "Kode", "Nama Mata Kuliah", "SKS");
                System.out.println("|---------------|----------------------------------------|-----|");

                while (resultSet.next()) {
                    String kodeMataKuliah = resultSet.getString("kodeMataKuliah");
                    String namaMataKuliah = resultSet.getString("namaMataKuliah");
                    int sks = resultSet.getInt("sks");

                    System.out.printf("|%-15s|%-40s|%-5d|\n", kodeMataKuliah, namaMataKuliah, sks);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void tampilkanDataAdmin(Connection connection) {
        try {
            String query = "SELECT * FROM admin ORDER BY idAdmin";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                ResultSet resultSet = pstmt.executeQuery();

                System.out.println("\nData Admin:");
                System.out.println("------------------------------------------------");
                System.out.printf("|%-15s|%-30s|\n", "ID Admin", "Nama");
                System.out.println("|---------------|------------------------------|");

                while (resultSet.next()) {
                    String idAdmin = resultSet.getString("idAdmin");
                    String nama = resultSet.getString("nama");

                    System.out.printf("|%-15s|%-30s|\n", idAdmin, nama);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void lihatMataKuliahDosen(Connection connection) {
        try {
            String query = "SELECT mkd.kodeMataKuliah, mk.namaMataKuliah, mkd.nidnDosen, d.Nama " +
                    "FROM mataKuliahDosen mkd " +
                    "JOIN mataKuliah mk ON mkd.kodeMataKuliah = mk.kodeMataKuliah " +
                    "JOIN dosen d ON mkd.nidnDosen = d.NIDN " +
                    "ORDER BY CAST(SUBSTRING(mkd.kodeMataKuliah FROM 3) AS UNSIGNED)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                ResultSet resultSet = pstmt.executeQuery();

                System.out.println("\nMata Kuliah yang Diajarkan Dosen");
                System.out.println(
                        "----------------------------------------------------------------------------------------------------");
                System.out.printf("|%-15s|%-35s|%-15s|%-30s|\n", "Kode", "Nama Mata Kuliah", "NIDN", "Nama Dosen");
                System.out.println(
                        "|---------------|-----------------------------------|---------------|------------------------------|");

                while (resultSet.next()) {
                    String kode = resultSet.getString("kodeMataKuliah");
                    String namaMatakuliah = resultSet.getString("namaMataKuliah");
                    String nidnDosen = resultSet.getString("nidnDosen");
                    String namaDosen = resultSet.getString("Nama");

                    System.out.printf("|%-15s|%-35s|%-15s|%-30s|\n", kode, namaMatakuliah, nidnDosen, namaDosen);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}