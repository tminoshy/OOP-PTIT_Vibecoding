package model;

/**
 * POJO (Plain Old Java Object) representing the Khoa table.
 * This is our "Model" for a department.
 */
public class Khoa {

    private String maKhoa;
    private String tenKhoa;

    /**
     * Default constructor.
     */
    public Khoa() {
    }

    /**
     * Constructor with all fields.
     * @param maKhoa The primary key (department ID).
     * @param tenKhoa The name of the department.
     */
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

    /**
     * Provides a user-friendly string representation.
     * This is very useful for displaying Khoa objects in a JComboBox.
     */
    @Override
    public String toString() {
        return this.tenKhoa + " (" + this.maKhoa + ")";
    }
}
