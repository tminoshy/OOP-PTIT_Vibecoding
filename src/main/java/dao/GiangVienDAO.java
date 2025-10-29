package dao;

import db.DatabaseConnector;
import model.GiangVien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class GiangVienDAO {

    public List<GiangVien> getAllGiangVien() {
        List<GiangVien> danhSachGV = new ArrayList<>();
        String sql = "SELECT * FROM GiangVien";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                GiangVien gv = new GiangVien();
                gv.setMaGiangVien(rs.getString("MaGiangVien"));
                gv.setHoVaTen(rs.getString("HoVaTen"));
                gv.setEmail(rs.getString("Email"));
                gv.setSoDienThoai(rs.getString("SoDienThoai"));
                gv.setMaKhoa(rs.getString("MaKhoa"));
                gv.setNgaySinh(rs.getString("NgaySinh"));
                gv.setGioiTinh(rs.getString("GioiTinh").charAt(0));
                gv.setHocVi(rs.getString("HocVi"));
                gv.setHocHam(rs.getString("HocHam"));
                danhSachGV.add(gv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachGV;
    }


    public boolean addGiangVien(GiangVien gv) {
        String sql = "INSERT INTO GiangVien (MaGiangVien, HoVaTen, Email, SoDienThoai, MaKhoa, NgaySinh, GioiTinh, HocVi, HocHam) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, gv.getMaGiangVien());
            pstmt.setString(2, gv.getHoVaTen());
            pstmt.setString(3, gv.getEmail());
            pstmt.setString(4, gv.getSoDienThoai());
            pstmt.setString(5, gv.getMaKhoa());
            pstmt.setString(6, gv.getNgaySinh());
            pstmt.setString(7, String.valueOf(gv.getGioiTinh()));
            pstmt.setString(8, gv.getHocVi());
            pstmt.setString(9, gv.getHocHam());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateGiangVien(GiangVien gv) {
        String sql = "UPDATE GiangVien SET HoVaTen = ?, Email = ?, SoDienThoai = ?, MaKhoa = ?, " +
                "NgaySinh = ?, GioiTinh = ?, HocVi = ?, HocHam = ? WHERE MaGiangVien = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, gv.getHoVaTen());
            pstmt.setString(2, gv.getEmail());
            pstmt.setString(3, gv.getSoDienThoai());
            pstmt.setString(4, gv.getMaKhoa());
            pstmt.setString(5, gv.getNgaySinh());
            pstmt.setString(6, String.valueOf(gv.getGioiTinh()));
            pstmt.setString(7, gv.getHocVi());
            pstmt.setString(8, gv.getHocHam());
            pstmt.setString(9, gv.getMaGiangVien());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteGiangVien(String maGiangVien) {
        String sql = "DELETE FROM GiangVien WHERE MaGiangVien = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maGiangVien);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public GiangVien getGiangVienById(String maGiangVien) {
        String sql = "SELECT * FROM GiangVien WHERE MaGiangVien = ?";
        GiangVien gv = null;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maGiangVien);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    gv = new GiangVien();
                    gv.setMaGiangVien(rs.getString("MaGiangVien"));
                    gv.setHoVaTen(rs.getString("HoVaTen"));
                    gv.setEmail(rs.getString("Email"));
                    gv.setSoDienThoai(rs.getString("SoDienThoai"));
                    gv.setMaKhoa(rs.getString("MaKhoa"));
                    gv.setNgaySinh(rs.getString("NgaySinh"));
                    gv.setGioiTinh(rs.getString("GioiTinh").charAt(0));
                    gv.setHocVi(rs.getString("HocVi"));
                    gv.setHocHam(rs.getString("HocHam"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gv;
    }
}