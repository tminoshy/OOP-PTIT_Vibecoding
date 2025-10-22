package model;

/**
 * POJO (Plain Old Java Object) representing the MonHoc table.
 * This is our "Model" for a subject (e.g., "Calculus 1").
 */
public class MonHoc {

    private String maMonHoc;
    private String maKhoa; // Foreign key to Khoa
    private String tenMonHoc;
    private int soTinChi; // Number of credits

    /**
     * Default constructor.
     */
    public MonHoc() {
    }

    /**
     * Constructor with all fields.
     * @param maMonHoc Primary key (subject ID)
     * @param maKhoa Foreign key to the department
     * @param tenMonHoc Name of the subject
     * @param soTinChi Number of credits for the subject
     */
    public MonHoc(String maMonHoc, String maKhoa, String tenMonHoc, int soTinChi) {
        this.maMonHoc = maMonHoc;
        this.maKhoa = maKhoa;
        this.tenMonHoc = tenMonHoc;
        this.soTinChi = soTinChi;
    }

    // --- Getters and Setters ---

    public String getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(String maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public int getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(int soTinChi) {
        this.soTinChi = soTinChi;
    }

    /**
     * Provides a user-friendly string representation.
     */
    @Override
    public String toString() {
        return this.tenMonHoc + " (" + this.maMonHoc + ") - " + this.soTinChi + " tín chỉ";
    }
}