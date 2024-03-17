public class Mahasiswa extends User {
    private String nama, nim, prodi;
    private int angkatan;

    public Mahasiswa(String username, String password, String nama, String nim, String prodi, int angkatan) {
        super(username, password, "mahasiswa");
        this.nama = nama;
        this.nim = nim;
        this.prodi = prodi;
        this.angkatan = angkatan;
    }

    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    public String getProdi() {
        return prodi;
    }

    public int getAngkatan() {
        return angkatan;
    }
}