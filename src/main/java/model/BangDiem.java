package model;


public class BangDiem {

    private String idBangDiem;
    private String maDangKy;
    private float diemChuyenCan;
    private float diemGiuaKy;
    private float diemCuoiKy;
    private float diemTongKet; 


    public BangDiem() {
    }

    public BangDiem(String idBangDiem, String maDangKy, float diemChuyenCan, float diemGiuaKy, float diemCuoiKy, float diemTongKet) {
        this.idBangDiem = idBangDiem;
        this.maDangKy = maDangKy;
        this.diemChuyenCan = diemChuyenCan;
        this.diemGiuaKy = diemGiuaKy;
        this.diemCuoiKy = diemCuoiKy;
        this.diemTongKet = diemTongKet;
    }


    public String getIdBangDiem() {
        return idBangDiem;
    }

    public void setIdBangDiem(String idBangDiem) {
        this.idBangDiem = idBangDiem;
    }

    public String getMaDangKy() {
        return maDangKy;
    }

    public void setMaDangKy(String maDangKy) {
        this.maDangKy = maDangKy;
    }

    public float getDiemChuyenCan() {
        return diemChuyenCan;
    }

    public void setDiemChuyenCan(float diemChuyenCan) {
        this.diemChuyenCan = diemChuyenCan;
    }

    public float getDiemGiuaKy() {
        return diemGiuaKy;
    }

    public void setDiemGiuaKy(float diemGiuaKy) {
        this.diemGiuaKy = diemGiuaKy;
    }

    public float getDiemCuoiKy() {
        return diemCuoiKy;
    }

    public void setDiemCuoiKy(float diemCuoiKy) {
        this.diemCuoiKy = diemCuoiKy;
    }

    public float getDiemTongKet() {
        return diemTongKet;
    }

    public void setDiemTongKet(float diemTongKet) {
        this.diemTongKet = diemTongKet;
    }

    @Override
    public String toString() {
        return "Bảng điểm [" + this.idBangDiem + "] for ĐK(" + this.maDangKy +
                "): " + this.diemTongKet;
    }
}
