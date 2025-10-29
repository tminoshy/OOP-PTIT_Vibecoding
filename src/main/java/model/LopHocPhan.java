package model;


public class LopHocPhan {

    private String maLop; 
    private String maMonHoc; 
    private String maGiangVien; 
    private int hocKy; 
    private int namHoc; 

    public LopHocPhan() {
    }


    public LopHocPhan(String maLop, String maMonHoc, String maGiangVien, int hocKy, int namHoc) {
        this.maLop = maLop;
        this.maMonHoc = maMonHoc;
        this.maGiangVien = maGiangVien;
        this.hocKy = hocKy;
        this.namHoc = namHoc;
    }


    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(String maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public String getMaGiangVien() {
        return maGiangVien;
    }

    public void setMaGiangVien(String maGiangVien) {
        this.maGiangVien = maGiangVien;
    }

    public int getHocKy() {
        return hocKy;
    }

    public void setHocKy(int hocKy) {
        this.hocKy = hocKy;
    }

    public int getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(int namHoc) {
        this.namHoc = namHoc;
    }


    @Override
    public String toString() {

        return "LHP: [" + this.maMonHoc + "] (GV: " + this.maGiangVien +
                ") - HK" + this.hocKy + " " + this.namHoc + "-" + (this.namHoc + 1);
    }
}

