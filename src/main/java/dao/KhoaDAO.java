package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnector;
import model.Khoa;


public class KhoaDAO {

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
        }
        return danhSachKhoa;
    }

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
