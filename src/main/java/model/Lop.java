package model;

/**
 * POJO (Plain Old Java Object) representing the Lop table.
 * This is our "Model" for a main student class (e.g., "IT Class A").
 */
public class Lop {

    private String maLop;
    private String phongHocChinh;
    private String maKhoa; // Foreign key to Khoa
    private int siSo;

    /**
     * Default constructor.
     */
    public Lop() {
    }

    /**
     * Constructor with all fields.
     * @param maLop Primary key (class ID)
     * @param phongHocChinh Main classroom
     * @param maKhoa Foreign key to the department
     * @param siSo Number of students
     */
    public Lop(String maLop, String phongHocChinh, String maKhoa, int siSo) {
        this.maLop = maLop;
        this.phongHocChinh = phongHocChinh;
        this.maKhoa = maKhoa;
        this.siSo = siSo;
    }

    // --- Getters and Setters ---

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getPhongHocChinh() {
        return phongHocChinh;
    }

    public void setPhongHocChinh(String phongHocChinh) {
        this.phongHocChinh = phongHocChinh;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public int getSiSo() {
        return siSo;
    }

    public void setSiSo(int siSo) {
        this.siSo = siSo;
    }

    /**
     * Provides a user-friendly string representation.
     */
    @Override
    public String toString() {
        return this.maLop + " (Khoa: " + this.maKhoa + ")";
    }
}