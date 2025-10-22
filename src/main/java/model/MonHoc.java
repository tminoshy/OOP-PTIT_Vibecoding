package model;


public class MonHoc {

    private String maMonHoc;
    private String maKhoa; // Foreign key to Khoa
    private String tenMonHoc;
    private int soTinChi; // Number of credits


    public MonHoc() {
    }


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


    @Override
    public String toString() {
        return this.tenMonHoc + " (" + this.maMonHoc + ") - " + this.soTinChi + " tín chỉ";
    }
}