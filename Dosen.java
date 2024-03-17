public class Dosen extends User {
    private String nama, nidn;

    public Dosen(String username, String password, String nama, String nidn) {
        super(username, password, "dosen");
        this.nama = nama;
        this.nidn = nidn;
    }

    public String getNama() {
        return nama;
    }

    public String getNidn() {
        return nidn;
    }
}
