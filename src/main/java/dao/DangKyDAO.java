package dao;

import db.DatabaseConnector;
import model.DangKy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the DangKy (Registration) table.
 * Handles all database operations for DangKy.
 */
public class DangKyDAO {

    /**
     * Retrieves a list of all DangKy from the database.
     * @return A list of DangKy objects.
     */
    public List<DangKy> getAllDangKy() {
        List<DangKy> danhSachDK = new ArrayList<>();
        String sql = "SELECT * FROM DangKy";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DangKy dk = new DangKy();
                dk.setMaDangKy(rs.getString("MaDangKy"));
                dk.setMaSinhVien(rs.getString("MaSinhVien"));
                dk.setMaLop(rs.getString("MaLop"));
                dk.setNgayDangKy(rs.getString("NgayDangKy"));
                danhSachDK.add(dk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachDK;
    }

    /**
     * Adds a new DangKy to the database.
     * @param dk The DangKy object to add.
     * @return true if successful, false otherwise.
     */
    public boolean addDangKy(DangKy dk) {
        String sql = "INSERT INTO DangKy (MaDangKy, MaSinhVien, MaLop, NgayDangKy) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dk.getMaDangKy());
            pstmt.setString(2, dk.getMaSinhVien());
            pstmt.setString(3, dk.getMaLop());
            pstmt.setString(4, dk.getNgayDangKy());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing DangKy in the database.
     * @param dk The DangKy object with updated information.
     * @return true if successful, false otherwise.
     */
    public boolean updateDangKy(DangKy dk) {
        String sql = "UPDATE DangKy SET MaSinhVien = ?, MaLop = ?, NgayDangKy = ? WHERE MaDangKy = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dk.getMaSinhVien());
            pstmt.setString(2, dk.getMaLop());
            pstmt.setString(3, dk.getNgayDangKy());
            pstmt.setString(4, dk.getMaDangKy());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a DangKy from the database.
     * @param maDangKy The ID of the DangKy to delete.
     * @return true if successful, false otherwise.
     */
    public boolean deleteDangKy(String maDangKy) {
        String sql = "DELETE FROM DangKy WHERE MaDangKy = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maDangKy);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a single DangKy by its ID.
     * @param maDangKy The ID of the DangKy.
     * @return A DangKy object, or null if not found.
     */
    public DangKy getDangKyById(String maDangKy) {
        String sql = "SELECT * FROM DangKy WHERE MaDangKy = ?";
        DangKy dk = null;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maDangKy);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    dk = new DangKy();
                    dk.setMaDangKy(rs.getString("MaDangKy"));
                    dk.setMaSinhVien(rs.getString("MaSinhVien"));
                    dk.setMaLop(rs.getString("MaLop"));
                    dk.setNgayDangKy(rs.getString("NgayDangKy"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dk;
    }
}
