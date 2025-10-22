package model;

/**
 * POJO (Plain Old Java Object) representing the DangKy table.
 * This is our "Model" for a single registration.
 * It links a SinhVien (Student) to a LopHocPhan (Course Section).
 */
public class DangKy {

    private String maDangKy; // Primary Key
    private String maSinhVien; // Foreign key to SinhVien
    private String maLop; // Foreign key to LopHocPhan
    private String ngayDangKy; // Registration date (e.g., "YYYY-MM-DD")

    /**
     * Default constructor.
     */
    public DangKy() {
    }

    /**
     * Constructor with all fields.
     * @param maDangKy Primary key
     * @param maSinhVien Foreign key to SinhVien
     * @param maLop Foreign key to LopHocPhan
     * @param ngayDangKy The date of registration
     */
    public DangKy(String maDangKy, String maSinhVien, String maLop, String ngayDangKy) {
        this.maDangKy = maDangKy;
        this.maSinhVien = maSinhVien;
        this.maLop = maLop;
        this.ngayDangKy = ngayDangKy;
    }

    // --- Getters and Setters ---

    public String getMaDangKy() {
        return maDangKy;
    }

    public void setMaDangKy(String maDangKy) {
        this.maDangKy = maDangKy;
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(String ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    /**
     * Provides a user-friendly string representation.
     */
    @Override
    public String toString() {
        return "Đăng ký [" + this.maDangKy + "]: SV(" + this.maSinhVien +
                ") - LHP(" + this.maLop + ") on " + this.ngayDangKy;
    }
}