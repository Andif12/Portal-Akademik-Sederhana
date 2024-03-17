public class Admin extends User {
    private String nama, adminId;

    public Admin(String username, String password, String nama, String adminId) {
        super(username, password, "admin");
        this.nama = nama;
        this.adminId = adminId;
    }

    public String getNama() {
        return nama;
    }

    public String getAdminId() {
        return adminId;
    }
}