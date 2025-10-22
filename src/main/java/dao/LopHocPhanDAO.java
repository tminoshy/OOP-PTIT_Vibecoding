package dao;

import db.DatabaseConnector;
import model.LopHocPhan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class LopHocPhanDAO {


    public List<LopHocPhan> getAllLopHocPhan() {
        List<LopHocPhan> danhSachLHP = new ArrayList<>();
        String sql = "SELECT * FROM LopHocPhan";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LopHocPhan lhp = new LopHocPhan();
                lhp.setMaLop(rs.getString("MaLop"));
                lhp.setMaMonHoc(rs.getString("MaMonHoc"));
                lhp.setMaGiangVien(rs.getString("MaGiangVien"));
                lhp.setHocKy(rs.getInt("HocKy"));
                lhp.setNamHoc(rs.getInt("NamHoc"));
                danhSachLHP.add(lhp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachLHP;
    }


    public boolean addLopHocPhan(LopHocPhan lhp) {
        String sql = "INSERT INTO LopHocPhan (MaLop, MaMonHoc, MaGiangVien, HocKy, NamHoc) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, lhp.getMaLop());
            pstmt.setString(2, lhp.getMaMonHoc());
            pstmt.setString(3, lhp.getMaGiangVien());
            pstmt.setInt(4, lhp.getHocKy());
            pstmt.setInt(5, lhp.getNamHoc());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateLopHocPhan(LopHocPhan lhp) {
        String sql = "UPDATE LopHocPhan SET MaMonHoc = ?, MaGiangVien = ?, HocKy = ?, NamHoc = ? WHERE MaLop = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, lhp.getMaMonHoc());
            pstmt.setString(2, lhp.getMaGiangVien());
            pstmt.setInt(3, lhp.getHocKy());
            pstmt.setInt(4, lhp.getNamHoc());
            pstmt.setString(5, lhp.getMaLop());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteLopHocPhan(String maLop) {
        String sql = "DELETE FROM LopHocPhan WHERE MaLop = ?";

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


    public LopHocPhan getLopHocPhanById(String maLop) {
        String sql = "SELECT * FROM LopHocPhan WHERE MaLop = ?";
        LopHocPhan lhp = null;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maLop);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    lhp = new LopHocPhan();
                    lhp.setMaLop(rs.getString("MaLop"));
                    lhp.setMaMonHoc(rs.getString("MaMonHoc"));
                    lhp.setMaGiangVien(rs.getString("MaGiangVien"));
                    lhp.setHocKy(rs.getInt("HocKy"));
                    lhp.setNamHoc(rs.getInt("NamHoc"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lhp;
    }
}
