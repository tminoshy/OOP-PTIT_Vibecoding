package model;


public class LopHocPhan {

    private String maLop; // Primary Key
    private String maMonHoc; // Foreign key to MonHoc
    private String maGiangVien; // Foreign key to GiangVien
    private int hocKy; // Semester (e.g., 1, 2, or 3 for summer)
    private int namHoc; // Starting year of the academic year (e.g., 2023 for 2023-2024)


    public LopHocPhan() {
    }


    public LopHocPhan(String maLop, String maMonHoc, String maGiangVien, int hocKy, int namHoc) {
        this.maLop = maLop;
        this.maMonHoc = maMonHoc;
        this.maGiangVien = maGiangVien;
        this.hocKy = hocKy;
        this.namHoc = namHoc;
    }

    // --- Getters and Setters ---

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

