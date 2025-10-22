package dao;

import db.DatabaseConnector;
import model.Lop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the Lop (Class) table.
 * Handles all database operations for Lop.
 */
public class LopDAO {

    /**
     * Retrieves a list of all Lop from the database.
     * @return A list of Lop objects.
     */
    public List<Lop> getAllLop() {
        List<Lop> danhSachLop = new ArrayList<>();
        String sql = "SELECT * FROM Lop";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Lop lop = new Lop();
                lop.setMaLop(rs.getString("MaLop"));
                lop.setPhongHocChinh(rs.getString("PhongHocChinh"));
                lop.setMaKhoa(rs.getString("MaKhoa"));
                lop.setSiSo(rs.getInt("SiSo"));
                danhSachLop.add(lop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachLop;
    }

    /**
     * Adds a new Lop to the database.
     * @param lop The Lop object to add.
     * @return true if successful, false otherwise.
     */
    public boolean addLop(Lop lop) {
        String sql = "INSERT INTO Lop (MaLop, PhongHocChinh, MaKhoa, SiSo) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, lop.getMaLop());
            pstmt.setString(2, lop.getPhongHocChinh());
            pstmt.setString(3, lop.getMaKhoa());
            pstmt.setInt(4, lop.getSiSo());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an existing Lop in the database.
     * @param lop The Lop object with updated information.
     * @return true if successful, false otherwise.
     */
    public boolean updateLop(Lop lop) {
        String sql = "UPDATE Lop SET PhongHocChinh = ?, MaKhoa = ?, SiSo = ? WHERE MaLop = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, lop.getPhongHocChinh());
            pstmt.setString(2, lop.getMaKhoa());
            pstmt.setInt(3, lop.getSiSo());
            pstmt.setString(4, lop.getMaLop());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a Lop from the database.
     * @param maLop The ID of the Lop to delete.
     * @return true if successful, false otherwise.
     */
    public boolean deleteLop(String maLop) {
        String sql = "DELETE FROM Lop WHERE MaLop = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maLop);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a single Lop by its ID.
     * @param maLop The ID of the Lop.
     * @return A Lop object, or null if not found.
     */
    public Lop getLopById(String maLop) {
        String sql = "SELECT * FROM Lop WHERE MaLop = ?";
        Lop lop = null;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maLop);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    lop = new Lop();
                    lop.setMaLop(rs.getString("MaLop"));
                    lop.setPhongHocChinh(rs.getString("PhongHocChinh"));
                    lop.setMaKhoa(rs.getString("MaKhoa"));
                    lop.setSiSo(rs.getInt("SiSo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lop;
    }
}
