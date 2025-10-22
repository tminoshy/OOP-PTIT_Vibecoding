package model;

/**
 * POJO (Plain Old Java Object) representing the BangDiem table.
 * This is our "Model" for the grade sheet.
 * It links directly to a DangKy (Registration) entry.
 */
public class BangDiem {

    private String idBangDiem; // Primary Key
    private String maDangKy; // Foreign key to DangKy (one-to-one)
    private float diemChuyenCan; // Attendance score
    private float diemGiuaKy; // Midterm score
    private float diemCuoiKy; // Final exam score
    private float diemTongKet; // Final calculated score

    /**
     * Default constructor.
     */
    public BangDiem() {
    }

    /**
     * Constructor with all fields.
     * @param idBangDiem Primary key
     * @param maDangKy Foreign key to DangKy
     * @param diemChuyenCan Attendance score
     * @param diemGiuaKy Midterm score
     * @param diemCuoiKy Final exam score
     * @param diemTongKet Final calculated score
     */
    public BangDiem(String idBangDiem, String maDangKy, float diemChuyenCan, float diemGiuaKy, float diemCuoiKy, float diemTongKet) {
        this.idBangDiem = idBangDiem;
        this.maDangKy = maDangKy;
        this.diemChuyenCan = diemChuyenCan;
        this.diemGiuaKy = diemGiuaKy;
        this.diemCuoiKy = diemCuoiKy;
        this.diemTongKet = diemTongKet;
    }

    // --- Getters and Setters ---

    public String getIdBangDiem() {
        return idBangDiem;
    }

    public void setIdBangDiem(String idBangDiem) {
        this.idBangDiem = idBangDiem;
    }

    public String getMaDangKy() {
        return maDangKy;
    }

    public void setMaDangKy(String maDangKy) {
        this.maDangKy = maDangKy;
    }

    public float getDiemChuyenCan() {
        return diemChuyenCan;
    }

    public void setDiemChuyenCan(float diemChuyenCan) {
        this.diemChuyenCan = diemChuyenCan;
    }

    public float getDiemGiuaKy() {
        return diemGiuaKy;
    }

    public void setDiemGiuaKy(float diemGiuaKy) {
        this.diemGiuaKy = diemGiuaKy;
    }

    public float getDiemCuoiKy() {
        return diemCuoiKy;
    }

    public void setDiemCuoiKy(float diemCuoiKy) {
        this.diemCuoiKy = diemCuoiKy;
    }

    public float getDiemTongKet() {
        return diemTongKet;
    }

    public void setDiemTongKet(float diemTongKet) {
        this.diemTongKet = diemTongKet;
    }

    /**
     * Provides a user-friendly string representation.
     */
    @Override
    public String toString() {
        return "Bảng điểm [" + this.idBangDiem + "] for ĐK(" + this.maDangKy +
                "): " + this.diemTongKet;
    }
}
