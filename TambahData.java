import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class TambahData {
    private static Scanner scanner = new Scanner(System.in);

    public static void tambahDataMahasiswa(Connection connection) {
        System.out.println("\n--- Tambah Data Mahasiswa ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Nama    : ");
        String nama = scanner.nextLine();

        System.out.print("NIM     : ");
        String nim = scanner.nextLine();

        System.out.print("Prodi   : ");
        String prodi = scanner.nextLine();

        System.out.print("Angkatan: ");
        int angkatan = scanner.nextInt();

        try {
            String query = "INSERT INTO mahasiswa (username, password, Nama, Nim, Prodi, angkatan) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, nama);
                pstmt.setString(4, nim);
                pstmt.setString(5, prodi);
                pstmt.setInt(6, angkatan);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Data mahasiswa berhasil ditambahkan ke database.\n");
                } else {
                    System.out.println("Gagal menambahkan data mahasiswa.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void tambahDataDosen(Connection connection) {
        System.out.println("\n--- Tambah Data Dosen ---");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("NIDN    : ");
        String nidn = scanner.nextLine();

        System.out.print("Nama    : ");
        String nama = scanner.nextLine();

        try {
            String query = "INSERT INTO dosen (username, password, NIDN, Nama) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, nidn);
                pstmt.setString(4, nama);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Data dosen berhasil ditambahkan ke database.");
                } else {
                    System.out.println("Gagal menambahkan data dosen.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void tambahDataMataKuliah(Connection connection) {
        System.out.println("\n--- Tambah Data Mata Kuliah ---");
        System.out.print("Kode Mata Kuliah: ");
        String kodeMataKuliah = scanner.nextLine();

        System.out.print("Nama Mata Kuliah: ");
        String namaMataKuliah = scanner.nextLine();

        System.out.print("SKS             : ");
        int sks = scanner.nextInt();

        try {
            String query = "INSERT INTO mataKuliah (kodeMataKuliah, namaMataKuliah, sks) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, kodeMataKuliah);
                pstmt.setString(2, namaMataKuliah);
                pstmt.setInt(3, sks);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Data matakuliah berhasil ditambahkan ke database.");
                } else {
                    System.out.println("Gagal menambahkan data mata kuliah.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void tambahDataAdmin(Connection connection) {
        System.out.println("\n--- Tambah Data Admin ---");

        System.out.print("Id Admin: ");
        String idAdmin = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Nama    : ");
        String nama = scanner.nextLine();

        try {
            String query = "INSERT INTO admin (idAdmin, username, password, Nama) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, idAdmin);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                pstmt.setString(4, nama);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Data Admin baru berhasil ditambahkan ke database.");
                } else {
                    System.out.println("Gagal menambahkan data Admin.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void tambahMataKuliahDosen(Connection connection) {
        System.out.println("\n--- Tambah Data Mata Kuliah Dosen ---");
        System.out.print("NIDN Dosen: ");
        String nidnDosen = scanner.nextLine();

        System.out.print("Kode Mata Kuliah: ");
        String kodeMataKuliah = scanner.nextLine();

        try {
            String query = "INSERT INTO mataKuliahDosen (nidnDosen, kodeMataKuliah) VALUES (?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, nidnDosen);
                pstmt.setString(2, kodeMataKuliah);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Data mata kuliah dosen berhasil ditambahkan ke database.\n");
                } else {
                    System.out.println("Gagal menambahkan data mata kuliah dosen.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
