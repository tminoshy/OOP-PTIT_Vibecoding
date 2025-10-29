package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnector;
import model.MonHoc;

public class MonHocDAO {


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
