-- Drop the database if it already exists to start fresh
DROP DATABASE IF EXISTS quanlysinhvien;

-- Create the new database
CREATE DATABASE quanlysinhvien;

-- Use the newly created database
USE quanlysinhvien;

-- Table: Khoa (Department)
CREATE TABLE Khoa (
                      MaKhoa VARCHAR(20) PRIMARY KEY,
                      TenKhoa VARCHAR(100) NOT NULL
);

-- Table: Lop (Class)
-- Represents a student's main class, which belongs to a Khoa.
CREATE TABLE Lop (
                     MaLop VARCHAR(20) PRIMARY KEY,
                     PhongHocChinh VARCHAR(50),
                     MaKhoa VARCHAR(20),
                     SiSo INT,
                     FOREIGN KEY (MaKhoa) REFERENCES Khoa(MaKhoa)
);

-- Table: SinhVien (Student)
-- Each student belongs to one Lop.
CREATE TABLE SinhVien (
                          MaSinhVien VARCHAR(20) PRIMARY KEY,
                          HoTen VARCHAR(100) NOT NULL,
                          NgaySinh VARCHAR(20), -- Using VARCHAR for simplicity, DATE is also good
                          GioiTinh CHAR(1), -- 'M' (Male), 'F' (Female), 'K' (Khac)
                          DiaChi VARCHAR(255),
                          Email VARCHAR(100),
                          Sdt VARCHAR(15),
                          CCCD VARCHAR(12) UNIQUE,
                          MaLop VARCHAR(20),
                          FOREIGN KEY (MaLop) REFERENCES Lop(MaLop)
);

-- Table: GiangVien (Lecturer)
-- Each lecturer belongs to one Khoa.
CREATE TABLE GiangVien (
                           MaGiangVien VARCHAR(20) PRIMARY KEY,
                           HoVaTen VARCHAR(100) NOT NULL,
                           Email VARCHAR(100),
                           SoDienThoai VARCHAR(15),
                           MaKhoa VARCHAR(20),
                           NgaySinh VARCHAR(20),
                           GioiTinh CHAR(1),
                           HocVi VARCHAR(50),  -- e.g., 'Cử nhân', 'Thạc sĩ', 'Tiến sĩ'
                           HocHam VARCHAR(50), -- e.g., 'GS', 'PGS'
                           FOREIGN KEY (MaKhoa) REFERENCES Khoa(MaKhoa)
);

-- Table: MonHoc (Subject)
-- Each subject is managed by one Khoa.
CREATE TABLE MonHoc (
                        MaMonHoc VARCHAR(20) PRIMARY KEY,
                        MaKhoa VARCHAR(20),
                        TenMonHoc VARCHAR(100) NOT NULL,
                        SoTinChi INT,
                        FOREIGN KEY (MaKhoa) REFERENCES Khoa(MaKhoa)
);

-- Table: LopHocPhan (Course Section)
-- This links a Subject (MonHoc) to a specific Lecturer (GiangVien) for a semester.
CREATE TABLE LopHocPhan (
                            MaLop VARCHAR(20) PRIMARY KEY, -- This is MaLopHocPhan, e.g., "CALC1_SEC1"
                            MaMonHoc VARCHAR(20),
                            MaGiangVien VARCHAR(20),
                            HocKy INT,
                            NamHoc INT,
                            FOREIGN KEY (MaMonHoc) REFERENCES MonHoc(MaMonHoc),
                            FOREIGN KEY (MaGiangVien) REFERENCES GiangVien(MaGiangVien)
);

-- Table: DangKy (Registration)
-- This is the "many-to-many" mapping table.
-- It links a Student (SinhVien) to a Course Section (LopHocPhan).
CREATE TABLE DangKy (
                        MaDangKy VARCHAR(20) PRIMARY KEY,
                        MaSinhVien VARCHAR(20),
                        MaLop VARCHAR(20), -- This is the MaLop from LopHocPhan
                        NgayDangKy VARCHAR(20),
                        FOREIGN KEY (MaSinhVien) REFERENCES SinhVien(MaSinhVien),
                        FOREIGN KEY (MaLop) REFERENCES LopHocPhan(MaLop)
);

-- Table: BangDiem (Grade)
-- Stores the grades for a specific registration.
-- One-to-one relationship with DangKy.
CREATE TABLE BangDiem (
                          IDBangDiem VARCHAR(20) PRIMARY KEY,
                          MaDangKy VARCHAR(20) UNIQUE, -- UNIQUE ensures one grade per registration
                          DiemChuyenCan FLOAT,
                          DiemGiuaKy FLOAT,
                          DiemCuoiKy FLOAT,
                          DiemTongKet FLOAT,
                          FOREIGN KEY (MaDangKy) REFERENCES DangKy(MaDangKy)
);

