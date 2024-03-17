import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class HapusData {
    private static Scanner scanner = new Scanner(System.in);

    public static void hapusDataMahasiswa(Connection connection) {
        System.out.println("\n--- Hapus Data Mahasiswa ---");

        System.out.print("NIM Mahasiswa yang akan dihapus: ");
        String nim = scanner.nextLine();

        try {
            String queryMahasiswa = "DELETE FROM mahasiswa WHERE Nim = ?";
            try (PreparedStatement pstmtMahasiswa = connection.prepareStatement(queryMahasiswa)) {
                pstmtMahasiswa.setString(1, nim);

                int affectedRowsMahasiswa = pstmtMahasiswa.executeUpdate();

                if (affectedRowsMahasiswa > 0) {
                    System.out.println("Data mahasiswa berhasil dihapus dari tabel mahasiswa.");

                    String queryNilaiPerKomponen = "DELETE FROM nilaiPerKomponen WHERE nimMahasiswa = ?";
                    try (PreparedStatement pstmtNilaiPerKomponen = connection.prepareStatement(queryNilaiPerKomponen)) {
                        pstmtNilaiPerKomponen.setString(1, nim);
                        pstmtNilaiPerKomponen.executeUpdate();
                    }

                    String queryKHS = "DELETE FROM KHS WHERE nimMahasiswa = ?";
                    try (PreparedStatement pstmtKHS = connection.prepareStatement(queryKHS)) {
                        pstmtKHS.setString(1, nim);
                        pstmtKHS.executeUpdate();
                    }

                    String queryKrs = "DELETE FROM Krs WHERE nimMahasiswa = ?";
                    try (PreparedStatement pstmtKrs = connection.prepareStatement(queryKrs)) {
                        pstmtKrs.setString(1, nim);
                        pstmtKrs.executeUpdate();
                    }

                    System.out.println("Data terkait mahasiswa berhasil dihapus dari tabel lainnya.");
                } else {
                    System.out.println("Gagal menghapus data mahasiswa. Pastikan NIM valid.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void hapusDataDosen(Connection connection) {
        System.out.println("\n--- Hapus Data Dosen ---");

        System.out.print("NIDN Dosen yang akan dihapus: ");
        String nidn = scanner.nextLine();

        try {
            String queryDosen = "DELETE FROM dosen WHERE NIDN = ?";
            try (PreparedStatement pstmtDosen = connection.prepareStatement(queryDosen)) {
                pstmtDosen.setString(1, nidn);

                int affectedRowsDosen = pstmtDosen.executeUpdate();

                if (affectedRowsDosen > 0) {
                    System.out.println("Data dosen berhasil dihapus dari tabel dosen.");

                    String queryPenilaianDosen = "DELETE FROM penilaianDosen WHERE nidnDosen = ?";
                    try (PreparedStatement pstmtPenilaianDosen = connection.prepareStatement(queryPenilaianDosen)) {
                        pstmtPenilaianDosen.setString(1, nidn);
                        pstmtPenilaianDosen.executeUpdate();
                    }

                    String queryMataKuliahDosen = "DELETE FROM mataKuliahDosen WHERE nidnDosen = ?";
                    try (PreparedStatement pstmtMataKuliahDosen = connection.prepareStatement(queryMataKuliahDosen)) {
                        pstmtMataKuliahDosen.setString(1, nidn);
                        pstmtMataKuliahDosen.executeUpdate();
                    }
                    System.out.println("Data terkait dosen berhasil dihapus dari tabel lainnya.");
                } else {
                    System.out.println("Gagal menghapus data dosen. Pastikan NIDN valid.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void hapusDataMataKuliah(Connection connection) {
        System.out.println("\n--- Hapus Data Mata Kuliah ---");

        System.out.print("Kode Mata Kuliah yang akan dihapus: ");
        String kodeMataKuliah = scanner.nextLine();

        try {
            String query = "DELETE FROM mataKuliah WHERE kodeMataKuliah = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, kodeMataKuliah);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("\nData mata kuliah berhasil dihapus dari database.\n");
                } else {
                    System.out.println("Gagal menghapus data mata kuliah. Pastikan kode mata kuliah valid.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void hapusDataAdmin(Connection connection) {
        System.out.println("\n--- Hapus Data Admin ---");

        System.out.print("ID Admin yang akan dihapus: ");
        String idAdmin = scanner.nextLine();

        try {
            String queryAdmin = "DELETE FROM admin WHERE idAdmin = ?";
            try (PreparedStatement pstmtAdmin = connection.prepareStatement(queryAdmin)) {
                pstmtAdmin.setString(1, idAdmin);

                int affectedRowsAdmin = pstmtAdmin.executeUpdate();

                if (affectedRowsAdmin > 0) {
                    System.out.println("Data admin berhasil dihapus dari tabel admin.");
                } else {
                    System.out.println("Gagal menghapus data admin. Pastikan ID Admin valid.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void hapusMataKuliahDosen(Connection connection) {
        System.out.println("\n--- Hapus Data Mata Kuliah Dosen ---");
        System.out.print("NIDN Dosen: ");
        String nidnDosen = scanner.nextLine();

        System.out.print("Kode Mata Kuliah: ");
        String kodeMataKuliah = scanner.nextLine();

        try {
            String query = "DELETE FROM mataKuliahDosen WHERE nidnDosen = ? AND kodeMataKuliah = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, nidnDosen);
                pstmt.setString(2, kodeMataKuliah);

                int rowCount = pstmt.executeUpdate();
                if (rowCount > 0) {
                    System.out.println("Data mata kuliah dosen berhasil dihapus.\n");
                } else {
                    System.out.println("Data mata kuliah dosen tidak ditemukan.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
