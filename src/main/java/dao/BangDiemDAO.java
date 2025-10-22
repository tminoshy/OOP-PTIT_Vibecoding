package dao;

import db.DatabaseConnector;
import model.BangDiem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class BangDiemDAO {


    public List<BangDiem> getAllBangDiem() {
        List<BangDiem> danhSachBD = new ArrayList<>();
        String sql = "SELECT * FROM BangDiem";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                BangDiem bd = new BangDiem();
                bd.setIdBangDiem(rs.getString("IDBangDiem"));
                bd.setMaDangKy(rs.getString("MaDangKy"));
                bd.setDiemChuyenCan(rs.getFloat("DiemChuyenCan"));
                bd.setDiemGiuaKy(rs.getFloat("DiemGiuaKy"));
                bd.setDiemCuoiKy(rs.getFloat("DiemCuoiKy"));
                bd.setDiemTongKet(rs.getFloat("DiemTongKet"));
                danhSachBD.add(bd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachBD;
    }


    public boolean addBangDiem(BangDiem bd) {
        String sql = "INSERT INTO BangDiem (IDBangDiem, MaDangKy, DiemChuyenCan, DiemGiuaKy, DiemCuoiKy, DiemTongKet) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bd.getIdBangDiem());
            pstmt.setString(2, bd.getMaDangKy());
            pstmt.setFloat(3, bd.getDiemChuyenCan());
            pstmt.setFloat(4, bd.getDiemGiuaKy());
            pstmt.setFloat(5, bd.getDiemCuoiKy());
            pstmt.setFloat(6, bd.getDiemTongKet());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateBangDiem(BangDiem bd) {
        String sql = "UPDATE BangDiem SET MaDangKy = ?, DiemChuyenCan = ?, DiemGiuaKy = ?, DiemCuoiKy = ?, DiemTongKet = ? " +
                "WHERE IDBangDiem = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bd.getMaDangKy());
            pstmt.setFloat(2, bd.getDiemChuyenCan());
            pstmt.setFloat(3, bd.getDiemGiuaKy());
            pstmt.setFloat(4, bd.getDiemCuoiKy());
            pstmt.setFloat(5, bd.getDiemTongKet());
            pstmt.setString(6, bd.getIdBangDiem());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteBangDiem(String idBangDiem) {
        String sql = "DELETE FROM BangDiem WHERE IDBangDiem = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idBangDiem);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public BangDiem getBangDiemById(String idBangDiem) {
        String sql = "SELECT * FROM BangDiem WHERE IDBangDiem = ?";
        BangDiem bd = null;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idBangDiem);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    bd = new BangDiem();
                    bd.setIdBangDiem(rs.getString("IDBangDiem"));
                    bd.setMaDangKy(rs.getString("MaDangKy"));
                    bd.setDiemChuyenCan(rs.getFloat("DiemChuyenCan"));
                    bd.setDiemGiuaKy(rs.getFloat("DiemGiuaKy"));
                    bd.setDiemCuoiKy(rs.getFloat("DiemCuoiKy"));
                    bd.setDiemTongKet(rs.getFloat("DiemTongKet"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bd;
    }


    public BangDiem getBangDiemByMaDangKy(String maDangKy) {
        String sql = "SELECT * FROM BangDiem WHERE MaDangKy = ?";
        BangDiem bd = null;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maDangKy);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    bd = new BangDiem();
                    bd.setIdBangDiem(rs.getString("IDBangDiem"));
                    bd.setMaDangKy(rs.getString("MaDangKy"));
                    bd.setDiemChuyenCan(rs.getFloat("DiemChuyenCan"));
                    bd.setDiemGiuaKy(rs.getFloat("DiemGiuaKy"));
                    bd.setDiemCuoiKy(rs.getFloat("DiemCuoiKy"));
                    bd.setDiemTongKet(rs.getFloat("DiemTongKet"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bd;
    }
}

