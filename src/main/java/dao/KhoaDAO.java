package dao;

import db.DatabaseConnector;
import model.Khoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the Khoa (Department) table.
 * Handles all database operations for Khoa.
 */
public class KhoaDAO {

    /**
     * Retrieves a list of all Khoa from the database.
     * @return A list of Khoa objects.
     */
    public List<Khoa> getAllKhoa() {
        List<Khoa> danhSachKhoa = new ArrayList<>();
        String sql = "SELECT * FROM Khoa";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Khoa khoa = new Khoa();
                khoa.setMaKhoa(rs.getString("MaKhoa"));
                khoa.setTenKhoa(rs.getString("TenKhoa"));
                danhSachKhoa.add(khoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately (e.g., logging, custom exceptions)
        }
        return danhSachKhoa;
    }

    /**
     * Adds a new Khoa to the database.
     * @param khoa The Khoa object to add.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean addKhoa(Khoa khoa) {
        String sql = "INSERT INTO Khoa (MaKhoa, TenKhoa) VALUES (?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, khoa.getMaKhoa());
            pstmt.setString(2, khoa.getTenKhoa());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing Khoa in the database.
     * @param khoa The Khoa object with updated information.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean updateKhoa(Khoa khoa) {
        String sql = "UPDATE Khoa SET TenKhoa = ? WHERE MaKhoa = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, khoa.getTenKhoa());
            pstmt.setString(2, khoa.getMaKhoa());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a Khoa from the database.
     * @param maKhoa The ID of the Khoa to delete.
     * @return true if the operation was successful, false otherwise.
     */
    public boolean deleteKhoa(String maKhoa) {
        String sql = "DELETE FROM Khoa WHERE MaKhoa = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maKhoa);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle foreign key constraint violations, etc.
            return false;
        }
    }

    /**
     * Retrieves a single Khoa by its ID.
     * @param maKhoa The ID of the Khoa.
     * @return A Khoa object, or null if not found.
     */
    public Khoa getKhoaById(String maKhoa) {
        String sql = "SELECT * FROM Khoa WHERE MaKhoa = ?";
        Khoa khoa = null;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maKhoa);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    khoa = new Khoa();
                    khoa.setMaKhoa(rs.getString("MaKhoa"));
                    khoa.setTenKhoa(rs.getString("TenKhoa"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return khoa;
    }
}
