package model;

/**
 * POJO (Plain Old Java Object) representing the GiangVien table.
 * This is our "Model" for a lecturer.
 */
public class GiangVien {

    private String maGiangVien;
    private String hoVaTen;
    private String email;
    private String soDienThoai;
    private String maKhoa; // Foreign key to Khoa
    private String ngaySinh;
    private char gioiTinh; // 'M' (Male), 'F' (Female), 'O' (Other)
    private String hocVi; // Degree: e.g., "Cử nhân", "Thạc sĩ", "Tiến sĩ"
    private String hocHam; // Academic Rank: e.g., "GS", "PGS"

    /**
     * Default constructor.
     */
    public GiangVien() {
    }

    /**
     * Constructor with all fields.
     */
    public GiangVien(String maGiangVien, String hoVaTen, String email, String soDienThoai,
                     String maKhoa, String ngaySinh, char gioiTinh, String hocVi, String hocHam) {
        this.maGiangVien = maGiangVien;
        this.hoVaTen = hoVaTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.maKhoa = maKhoa;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.hocVi = hocVi;
        this.hocHam = hocHam;
    }

    // --- Getters and Setters ---

    public String getMaGiangVien() {
        return maGiangVien;
    }

    public void setMaGiangVien(String maGiangVien) {
        this.maGiangVien = maGiangVien;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public char getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(char gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getHocVi() {
        return hocVi;
    }

    public void setHocVi(String hocVi) {
        this.hocVi = hocVi;
    }

    public String getHocHam() {
        return hocHam;
    }

    public void setHocHam(String hocHam) {
        this.hocHam = hocHam;
    }

    /**
     * Provides a user-friendly string representation.
     */
    @Override
    public String toString() {
        String rank = (hocHam != null && !hocHam.isEmpty()) ? hocHam + " " : "";
        String degree = (hocVi != null && !hocVi.isEmpty()) ? hocVi + " " : "";
        return rank + degree + hoVaTen + " (" + maGiangVien + ")";
    }
}
