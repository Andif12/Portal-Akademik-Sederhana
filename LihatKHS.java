import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LihatKHS {
    public static void lihatKHS(Mahasiswa mahasiswa, Connection connection) throws SQLException {
        String query = "SELECT * FROM khs WHERE nimMahasiswa = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, mahasiswa.getNim());

            try (ResultSet resultSet = pstmt.executeQuery()) {

                if (!resultSet.next()) {
                    System.out.println("\nKHS dengan Nama " + mahasiswa.getNama() + " dan NIM " + mahasiswa.getNim()
                            + " tidak ditemukan.");
                } else {
                    System.out.println("\nKartu Hasil Studi (KHS)");
                    System.out.println("Nama: " + mahasiswa.getNama());
                    System.out.println("NIM : " + mahasiswa.getNim());
                    System.out.println("----------------------------------------------------------");
                    System.out.format("|%-5s|%-30s|%6s|%12s|%n", "KODE", "MATAKULIAH ", "NILAI",
                            "NILAI HURUF");
                    System.out.println("|-----|------------------------------|------|------------|");
                    do {
                        String kodeMataKuliah = resultSet.getString("kodeMataKuliah");
                        double nilaiAkhir = resultSet.getDouble("nilaiAkhir");

                        String queryMataKuliah = "SELECT namaMataKuliah FROM matakuliah WHERE kodeMataKuliah = ?";
                        try (PreparedStatement pstmtMataKuliah = connection.prepareStatement(queryMataKuliah)) {
                            pstmtMataKuliah.setString(1, kodeMataKuliah);
                            try (ResultSet resultSetMataKuliah = pstmtMataKuliah.executeQuery()) {
                                if (resultSetMataKuliah.next()) {
                                    String namaMataKuliah = resultSetMataKuliah.getString("namaMataKuliah");
                                    System.out.format("|%-5s|%-30s|%6s|%12s|%n", kodeMataKuliah, namaMataKuliah,
                                            nilaiAkhir,
                                            konversiNilaiKeHuruf(nilaiAkhir));
                                }
                            }
                        }
                    } while (resultSet.next());
                }
            }
        }
    }

    private static String konversiNilaiKeHuruf(double nilai) {
        String huruf;
        if (nilai >= 85) {
            huruf = "A";
        } else if (nilai >= 80) {
            huruf = "A-";
        } else if (nilai >= 75) {
            huruf = "B+";
        } else if (nilai >= 70) {
            huruf = "B";
        } else if (nilai >= 65) {
            huruf = "B-";
        } else if (nilai >= 60) {
            huruf = "C+";
        } else if (nilai >= 55) {
            huruf = "C";
        } else if (nilai >= 50) {
            huruf = "C-";
        } else if (nilai >= 40) {
            huruf = "D";
        } else {
            huruf = "E";
        }
        return huruf;
    }
}
