package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnector;
import model.SinhVien;


public class SinhVienDAO {

    public List<SinhVien> getAllSinhVien() {
        List<SinhVien> danhSachSinhVien = new ArrayList<>();
        String sql = "SELECT * FROM SinhVien";

       
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                SinhVien sv = new SinhVien();
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

                danhSachSinhVien.add(sv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachSinhVien;
    }

    public boolean addSinhVien(SinhVien sv) {
        String sql = "INSERT INTO SinhVien (MaSinhVien, HoTen, NgaySinh, GioiTinh, DiaChi, Email, Sdt, CCCD, MaLop) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sv.getMaSinhVien());
            pstmt.setString(2, sv.getHoTen());
            pstmt.setString(3, sv.getNgaySinh());
            pstmt.setString(4, String.valueOf(sv.getGioiTinh()));
            pstmt.setString(5, sv.getDiaChi());
            pstmt.setString(6, sv.getEmail());
            pstmt.setString(7, sv.getSdt());
            pstmt.setString(8, sv.getCCCD());
            pstmt.setString(9, sv.getMaLop());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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
            pstmt.setString(9, sv.getMaSinhVien()); 

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSinhVien(String maSinhVien) {
        String sql = "DELETE FROM SinhVien WHERE MaSinhVien = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maSinhVien);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

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