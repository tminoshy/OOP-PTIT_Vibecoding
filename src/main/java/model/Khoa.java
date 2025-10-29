package model;


public class Khoa {

    private String maKhoa;
    private String tenKhoa;


    public Khoa() {
    }


    public Khoa(String maKhoa, String tenKhoa) {
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
    }

    // --- Getters and Setters ---

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }


    @Override
    public String toString() {
        return this.tenKhoa + " (" + this.maKhoa + ")";
    }
}
