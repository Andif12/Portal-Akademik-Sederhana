import java.util.HashMap;
import java.util.Map;

public class NilaiMatakuliah {
    private String nimMahasiswa, kodeMataKuliah, nidnDosen;
    private Map<String, Double> nilaiKomponen;

    public NilaiMatakuliah(String nimMahasiswa, String kodeMataKuliah, String nidnDosen) {
        this.nimMahasiswa = nimMahasiswa;
        this.kodeMataKuliah = kodeMataKuliah;
        this.nidnDosen = nidnDosen;
        this.nilaiKomponen = new HashMap<>();
    }

    public String getNimMahasiswa() {
        return nimMahasiswa;
    }

    public String getKodeMataKuliah() {
        return kodeMataKuliah;
    }

    public String getNidnDosen() {
        return nidnDosen;
    }

    public Map<String, Double> getNilaiKomponen() {
        return nilaiKomponen;
    }

    public void tambahNilaiKomponen(String komponen, double nilai) {
        nilaiKomponen.put(komponen, nilai);
    }
}
