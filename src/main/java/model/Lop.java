package model;


public class Lop {

    private String maLop;
    private String phongHocChinh;
    private String maKhoa; // Foreign key to Khoa
    private int siSo;


    public Lop() {
    }


    public Lop(String maLop, String phongHocChinh, String maKhoa, int siSo) {
        this.maLop = maLop;
        this.phongHocChinh = phongHocChinh;
        this.maKhoa = maKhoa;
        this.siSo = siSo;
    }


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


    @Override
    public String toString() {
        return this.maLop + " (Khoa: " + this.maKhoa + ")";
    }
}