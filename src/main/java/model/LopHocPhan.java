package model;

/**
 * POJO (Plain Old Java Object) representing the LopHocPhan table.
 * This is our "Model" for a specific course section.
 * It links a MonHoc (Subject) to a GiangVien (Lecturer) for a specific semester.
 */
public class LopHocPhan {

    private String maLop; // Primary Key
    private String maMonHoc; // Foreign key to MonHoc
    private String maGiangVien; // Foreign key to GiangVien
    private int hocKy; // Semester (e.g., 1, 2, or 3 for summer)
    private int namHoc; // Starting year of the academic year (e.g., 2023 for 2023-2024)

    /**
     * Default constructor.
     */
    public LopHocPhan() {
    }

    /**
     * Constructor with all fields.
     * @param maLop Primary key (course section ID)
     * @param maMonHoc Foreign key to MonHoc
     * @param maGiangVien Foreign key to GiangVien
     * @param hocKy Semester
     * @param namHoc Academic year
     */
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

    /**
     * Provides a user-friendly string representation.
     */
    @Override
    public String toString() {
        // e.g., "LHP: [MA001] (GV: GV001) - HK1 2023-2024"
        return "LHP: [" + this.maMonHoc + "] (GV: " + this.maGiangVien +
                ") - HK" + this.hocKy + " " + this.namHoc + "-" + (this.namHoc + 1);
    }
}

