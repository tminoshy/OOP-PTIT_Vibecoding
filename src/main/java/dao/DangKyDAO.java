package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnector;
import model.DangKy;


public class DangKyDAO {


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
