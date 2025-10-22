package dao;

import db.DatabaseConnector;
import model.SinhVien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the SinhVien (Student) table.
 * Handles all database operations (CRUD) for students.
 */
public class SinhVienDAO {

    /**
     * Retrieves a list of all students from the database.
     * @return A list of SinhVien objects.
     */
    public List<SinhVien> getAllSinhVien() {
        List<SinhVien> danhSachSinhVien = new ArrayList<>();
        // SQL query to select all students
        String sql = "SELECT * FROM SinhVien";

        // Use try-with-resources for automatic resource management
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Loop through the result set and create SinhVien objects
            while (rs.next()) {
                SinhVien sv = new SinhVien();
                sv.setMaSinhVien(rs.getString("MaSinhVien"));
                sv.setHoTen(rs.getString("HoTen"));
                sv.setNgaySinh(rs.getString("NgaySinh"));
                // Handle char
                String gioiTinhStr = rs.getString("GioiTinh");
                if (gioiTinhStr != null && !gioiTinhStr.isEmpty()) {
                    sv.setGioiTinh(gioiTinhStr.charAt(0));
                }
                sv.setDiaChi(rs.getString("DiaChi"));
                sv.setEmail(rs.getString("Email"));
                sv.setSdt(rs.getString("Sdt"));
                sv.setCCCD(rs.getString("CCCD"));
                sv.setMaLop(rs.getString("MaLop"));

                danhSachSinhVien.add(sv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // In a real app, you'd have better error handling (logging, custom exceptions)
        }
        return danhSachSinhVien;
    }

    /**
     * Adds a new student to the database.
     * @param sv The SinhVien object to add.
     * @return true if the student was added successfully, false otherwise.
     */
    public boolean addSinhVien(SinhVien sv) {
        String sql = "INSERT INTO SinhVien (MaSinhVien, HoTen, NgaySinh, GioiTinh, DiaChi, Email, Sdt, CCCD, MaLop) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sv.getMaSinhVien());
            pstmt.setString(2, sv.getHoTen());
            pstmt.setString(3, sv.getNgaySinh());
            pstmt.setString(4, String.valueOf(sv.getGioiTinh())); // Convert char to String
            pstmt.setString(5, sv.getDiaChi());
            pstmt.setString(6, sv.getEmail());
            pstmt.setString(7, sv.getSdt());
            pstmt.setString(8, sv.getCCCD());
            pstmt.setString(9, sv.getMaLop());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Returns true if one row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing student's information in the database.
     * @param sv The SinhVien object with updated information.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateSinhVien(SinhVien sv) {
        String sql = "UPDATE SinhVien SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, DiaChi = ?, " +
                "Email = ?, Sdt = ?, CCCD = ?, MaLop = ? WHERE MaSinhVien = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sv.getHoTen());
            pstmt.setString(2, sv.getNgaySinh());
            pstmt.setString(3, String.valueOf(sv.getGioiTinh()));
            pstmt.setString(4, sv.getDiaChi());
            pstmt.setString(5, sv.getEmail());
            pstmt.setString(6, sv.getSdt());
            pstmt.setString(7, sv.getCCCD());
            pstmt.setString(8, sv.getMaLop());
            pstmt.setString(9, sv.getMaSinhVien()); // Set the MaSinhVien for the WHERE clause

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a student from the database.
     * @param maSinhVien The ID of the student to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public boolean deleteSinhVien(String maSinhVien) {
        String sql = "DELETE FROM SinhVien WHERE MaSinhVien = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maSinhVien);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // You should also handle foreign key constraints here
            // (e.g., if a student has registrations, deletion might fail)
            return false;
        }
    }

    /**
     * Retrieves a single student by their ID.
     * @param maSinhVien The ID of the student.
     * @return A SinhVien object, or null if not found.
     */
    public SinhVien getSinhVienById(String maSinhVien) {
        String sql = "SELECT * FROM SinhVien WHERE MaSinhVien = ?";
        SinhVien sv = null;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maSinhVien);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    sv = new SinhVien();
                    sv.setMaSinhVien(rs.getString("MaSinhVien"));
                    sv.setHoTen(rs.getString("HoTen"));
                    sv.setNgaySinh(rs.getString("NgaySinh"));
                    String gioiTinhStr = rs.getString("GioiTinh");
                    if (gioiTinhStr != null && !gioiTinhStr.isEmpty()) {
                        sv.setGioiTinh(gioiTinhStr.charAt(0));
                    }
                    sv.setDiaChi(rs.getString("DiaChi"));
                    sv.setEmail(rs.getString("Email"));
                    sv.setSdt(rs.getString("Sdt"));
                    sv.setCCCD(rs.getString("CCCD"));
                    sv.setMaLop(rs.getString("MaLop"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sv;
    }
}