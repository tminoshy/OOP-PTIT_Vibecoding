package dao;

import db.DatabaseConnector;
import model.MonHoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the MonHoc (Subject) table.
 * Handles all database operations for MonHoc.
 */
public class MonHocDAO {

    /**
     * Retrieves a list of all MonHoc from the database.
     * @return A list of MonHoc objects.
     */
    public List<MonHoc> getAllMonHoc() {
        List<MonHoc> danhSachMH = new ArrayList<>();
        String sql = "SELECT * FROM MonHoc";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MonHoc mh = new MonHoc();
                mh.setMaMonHoc(rs.getString("MaMonHoc"));
                mh.setMaKhoa(rs.getString("MaKhoa"));
                mh.setTenMonHoc(rs.getString("TenMonHoc"));
                mh.setSoTinChi(rs.getInt("SoTinChi"));
                danhSachMH.add(mh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachMH;
    }

    /**
     * Adds a new MonHoc to the database.
     * @param mh The MonHoc object to add.
     * @return true if successful, false otherwise.
     */
    public boolean addMonHoc(MonHoc mh) {
        String sql = "INSERT INTO MonHoc (MaMonHoc, MaKhoa, TenMonHoc, SoTinChi) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mh.getMaMonHoc());
            pstmt.setString(2, mh.getMaKhoa());
            pstmt.setString(3, mh.getTenMonHoc());
            pstmt.setInt(4, mh.getSoTinChi());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing MonHoc in the database.
     * @param mh The MonHoc object with updated information.
     * @return true if successful, false otherwise.
     */
    public boolean updateMonHoc(MonHoc mh) {
        String sql = "UPDATE MonHoc SET MaKhoa = ?, TenMonHoc = ?, SoTinChi = ? WHERE MaMonHoc = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mh.getMaKhoa());
            pstmt.setString(2, mh.getTenMonHoc());
            pstmt.setInt(3, mh.getSoTinChi());
            pstmt.setString(4, mh.getMaMonHoc());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a MonHoc from the database.
     * @param maMonHoc The ID of the MonHoc to delete.
     * @return true if successful, false otherwise.
     */
    public boolean deleteMonHoc(String maMonHoc) {
        String sql = "DELETE FROM MonHoc WHERE MaMonHoc = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maMonHoc);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a single MonHoc by its ID.
     * @param maMonHoc The ID of the MonHoc.
     * @return A MonHoc object, or null if not found.
     */
    public MonHoc getMonHocById(String maMonHoc) {
        String sql = "SELECT * FROM MonHoc WHERE MaMonHoc = ?";
        MonHoc mh = null;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maMonHoc);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    mh = new MonHoc();
                    mh.setMaMonHoc(rs.getString("MaMonHoc"));
                    mh.setMaKhoa(rs.getString("MaKhoa"));
                    mh.setTenMonHoc(rs.getString("TenMonHoc"));
                    mh.setSoTinChi(rs.getInt("SoTinChi"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mh;
    }
}
