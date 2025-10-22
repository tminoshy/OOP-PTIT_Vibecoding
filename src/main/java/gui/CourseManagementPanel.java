package gui;

import dao.LopHocPhanDAO;
import dao.MonHocDAO;
import model.LopHocPhan;
import model.MonHoc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for managing courses and course sections
 * Provides CRUD operations for MonHoc and LopHocPhan
 */
public class CourseManagementPanel extends JPanel {
    
    private MonHocDAO monHocDAO;
    private LopHocPhanDAO lopHocPhanDAO;
    private JTabbedPane courseTabbedPane;
    
    public CourseManagementPanel() {
        monHocDAO = new MonHocDAO();
        lopHocPhanDAO = new LopHocPhanDAO();
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        courseTabbedPane = new JTabbedPane();
        
        // Create sub-panels
        MonHocManagementPanel monHocPanel = new MonHocManagementPanel();
        LopHocPhanManagementPanel lopHocPhanPanel = new LopHocPhanManagementPanel();
        
        courseTabbedPane.addTab("Quản lý Môn học", monHocPanel);
        courseTabbedPane.addTab("Quản lý Lớp học phần", lopHocPhanPanel);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        add(courseTabbedPane, BorderLayout.CENTER);
    }
    
    // Inner class for MonHoc management
    private class MonHocManagementPanel extends JPanel {
        private JTable monHocTable;
        private DefaultTableModel tableModel;
        private JTextField txtMaMonHoc, txtTenMonHoc, txtSoTinChi, txtMaKhoa;
        
        public MonHocManagementPanel() {
            initializeComponents();
            setupLayout();
            loadMonHocData();
        }
        
        private void initializeComponents() {
            txtMaMonHoc = new JTextField(15);
            txtTenMonHoc = new JTextField(20);
            txtSoTinChi = new JTextField(10);
            txtMaKhoa = new JTextField(15);
            
            String[] columnNames = {"Mã môn học", "Tên môn học", "Số tín chỉ", "Mã khoa"};
            tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            monHocTable = new JTable(tableModel);
            monHocTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        
        private void setupLayout() {
            setLayout(new BorderLayout());
            
            // Input panel
            JPanel inputPanel = new JPanel(new GridBagLayout());
            inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin môn học"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            
            gbc.gridx = 0; gbc.gridy = 0;
            inputPanel.add(new JLabel("Mã môn học:"), gbc);
            gbc.gridx = 1;
            inputPanel.add(txtMaMonHoc, gbc);
            
            gbc.gridx = 2;
            inputPanel.add(new JLabel("Tên môn học:"), gbc);
            gbc.gridx = 3;
            inputPanel.add(txtTenMonHoc, gbc);
            
            gbc.gridx = 0; gbc.gridy = 1;
            inputPanel.add(new JLabel("Số tín chỉ:"), gbc);
            gbc.gridx = 1;
            inputPanel.add(txtSoTinChi, gbc);
            
            gbc.gridx = 2;
            inputPanel.add(new JLabel("Mã khoa:"), gbc);
            gbc.gridx = 3;
            inputPanel.add(txtMaKhoa, gbc);
            
            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton btnAdd = new JButton("Thêm");
            JButton btnUpdate = new JButton("Cập nhật");
            JButton btnDelete = new JButton("Xóa");
            JButton btnClear = new JButton("Làm mới");
            JButton btnRefresh = new JButton("Tải lại");
            
            btnAdd.addActionListener(e -> addMonHoc());
            btnUpdate.addActionListener(e -> updateMonHoc());
            btnDelete.addActionListener(e -> deleteMonHoc());
            btnClear.addActionListener(e -> clearMonHocFields());
            btnRefresh.addActionListener(e -> loadMonHocData());
            
            buttonPanel.add(btnAdd);
            buttonPanel.add(btnUpdate);
            buttonPanel.add(btnDelete);
            buttonPanel.add(btnClear);
            buttonPanel.add(btnRefresh);
            
            // Table panel
            JPanel tablePanel = new JPanel(new BorderLayout());
            tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách môn học"));
            JScrollPane scrollPane = new JScrollPane(monHocTable);
            scrollPane.setPreferredSize(new Dimension(0, 300));
            tablePanel.add(scrollPane, BorderLayout.CENTER);
            
            add(inputPanel, BorderLayout.NORTH);
            add(buttonPanel, BorderLayout.CENTER);
            add(tablePanel, BorderLayout.SOUTH);
        }
        
        private void loadMonHocData() {
            tableModel.setRowCount(0);
            List<MonHoc> monHocs = monHocDAO.getAllMonHoc();
            
            for (MonHoc mh : monHocs) {
                Object[] row = {
                    mh.getMaMonHoc(),
                    mh.getTenMonHoc(),
                    mh.getSoTinChi(),
                    mh.getMaKhoa()
                };
                tableModel.addRow(row);
            }
        }
        
        private void addMonHoc() {
            if (validateMonHocInput()) {
                MonHoc mh = createMonHocFromInput();
                if (monHocDAO.addMonHoc(mh)) {
                    JOptionPane.showMessageDialog(this, "Thêm môn học thành công!");
                    loadMonHocData();
                    clearMonHocFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi thêm môn học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        private void updateMonHoc() {
            if (validateMonHocInput()) {
                MonHoc mh = createMonHocFromInput();
                if (monHocDAO.updateMonHoc(mh)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật môn học thành công!");
                    loadMonHocData();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật môn học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        private void deleteMonHoc() {
            String maMonHoc = txtMaMonHoc.getText().trim();
            if (maMonHoc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn xóa môn học " + maMonHoc + "?", 
                "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                if (monHocDAO.deleteMonHoc(maMonHoc)) {
                    JOptionPane.showMessageDialog(this, "Xóa môn học thành công!");
                    loadMonHocData();
                    clearMonHocFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa môn học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        private void clearMonHocFields() {
            txtMaMonHoc.setText("");
            txtTenMonHoc.setText("");
            txtSoTinChi.setText("");
            txtMaKhoa.setText("");
        }
        
        private boolean validateMonHocInput() {
            if (txtMaMonHoc.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã môn học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (txtTenMonHoc.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên môn học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        }
        
        private MonHoc createMonHocFromInput() {
            MonHoc mh = new MonHoc();
            mh.setMaMonHoc(txtMaMonHoc.getText().trim());
            mh.setTenMonHoc(txtTenMonHoc.getText().trim());
            try {
                mh.setSoTinChi(Integer.parseInt(txtSoTinChi.getText().trim()));
            } catch (NumberFormatException e) {
                mh.setSoTinChi(0);
            }
            mh.setMaKhoa(txtMaKhoa.getText().trim());
            return mh;
        }
    }
    
    // Inner class for LopHocPhan management
    private class LopHocPhanManagementPanel extends JPanel {
        private JTable lopHocPhanTable;
        private DefaultTableModel tableModel;
        private JTextField txtMaLop, txtMaMonHoc, txtMaGiangVien, txtHocKy, txtNamHoc;
        
        public LopHocPhanManagementPanel() {
            initializeComponents();
            setupLayout();
            loadLopHocPhanData();
        }
        
        private void initializeComponents() {
            txtMaLop = new JTextField(15);
            txtMaMonHoc = new JTextField(15);
            txtMaGiangVien = new JTextField(15);
            txtHocKy = new JTextField(10);
            txtNamHoc = new JTextField(10);
            
            String[] columnNames = {"Mã lớp", "Mã môn học", "Mã giảng viên", "Học kỳ", "Năm học"};
            tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            lopHocPhanTable = new JTable(tableModel);
            lopHocPhanTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        
        private void setupLayout() {
            setLayout(new BorderLayout());
            
            // Input panel
            JPanel inputPanel = new JPanel(new GridBagLayout());
            inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin lớp học phần"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            
            gbc.gridx = 0; gbc.gridy = 0;
            inputPanel.add(new JLabel("Mã lớp:"), gbc);
            gbc.gridx = 1;
            inputPanel.add(txtMaLop, gbc);
            
            gbc.gridx = 2;
            inputPanel.add(new JLabel("Mã môn học:"), gbc);
            gbc.gridx = 3;
            inputPanel.add(txtMaMonHoc, gbc);
            
            gbc.gridx = 0; gbc.gridy = 1;
            inputPanel.add(new JLabel("Mã giảng viên:"), gbc);
            gbc.gridx = 1;
            inputPanel.add(txtMaGiangVien, gbc);
            
            gbc.gridx = 2;
            inputPanel.add(new JLabel("Học kỳ:"), gbc);
            gbc.gridx = 3;
            inputPanel.add(txtHocKy, gbc);
            
            gbc.gridx = 0; gbc.gridy = 2;
            inputPanel.add(new JLabel("Năm học:"), gbc);
            gbc.gridx = 1;
            inputPanel.add(txtNamHoc, gbc);
            
            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton btnAdd = new JButton("Thêm");
            JButton btnUpdate = new JButton("Cập nhật");
            JButton btnDelete = new JButton("Xóa");
            JButton btnClear = new JButton("Làm mới");
            JButton btnRefresh = new JButton("Tải lại");
            
            btnAdd.addActionListener(e -> addLopHocPhan());
            btnUpdate.addActionListener(e -> updateLopHocPhan());
            btnDelete.addActionListener(e -> deleteLopHocPhan());
            btnClear.addActionListener(e -> clearLopHocPhanFields());
            btnRefresh.addActionListener(e -> loadLopHocPhanData());
            
            buttonPanel.add(btnAdd);
            buttonPanel.add(btnUpdate);
            buttonPanel.add(btnDelete);
            buttonPanel.add(btnClear);
            buttonPanel.add(btnRefresh);
            
            // Table panel
            JPanel tablePanel = new JPanel(new BorderLayout());
            tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách lớp học phần"));
            JScrollPane scrollPane = new JScrollPane(lopHocPhanTable);
            scrollPane.setPreferredSize(new Dimension(0, 300));
            tablePanel.add(scrollPane, BorderLayout.CENTER);
            
            add(inputPanel, BorderLayout.NORTH);
            add(buttonPanel, BorderLayout.CENTER);
            add(tablePanel, BorderLayout.SOUTH);
        }
        
        private void loadLopHocPhanData() {
            tableModel.setRowCount(0);
            List<LopHocPhan> lopHocPhans = lopHocPhanDAO.getAllLopHocPhan();
            
            for (LopHocPhan lhp : lopHocPhans) {
                Object[] row = {
                    lhp.getMaLop(),
                    lhp.getMaMonHoc(),
                    lhp.getMaGiangVien(),
                    lhp.getHocKy(),
                    lhp.getNamHoc()
                };
                tableModel.addRow(row);
            }
        }
        
        private void addLopHocPhan() {
            if (validateLopHocPhanInput()) {
                LopHocPhan lhp = createLopHocPhanFromInput();
                if (lopHocPhanDAO.addLopHocPhan(lhp)) {
                    JOptionPane.showMessageDialog(this, "Thêm lớp học phần thành công!");
                    loadLopHocPhanData();
                    clearLopHocPhanFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi thêm lớp học phần!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        private void updateLopHocPhan() {
            if (validateLopHocPhanInput()) {
                LopHocPhan lhp = createLopHocPhanFromInput();
                if (lopHocPhanDAO.updateLopHocPhan(lhp)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật lớp học phần thành công!");
                    loadLopHocPhanData();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật lớp học phần!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        private void deleteLopHocPhan() {
            String maLop = txtMaLop.getText().trim();
            if (maLop.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học phần cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn xóa lớp học phần " + maLop + "?", 
                "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                if (lopHocPhanDAO.deleteLopHocPhan(maLop)) {
                    JOptionPane.showMessageDialog(this, "Xóa lớp học phần thành công!");
                    loadLopHocPhanData();
                    clearLopHocPhanFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa lớp học phần!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        private void clearLopHocPhanFields() {
            txtMaLop.setText("");
            txtMaMonHoc.setText("");
            txtMaGiangVien.setText("");
            txtHocKy.setText("");
            txtNamHoc.setText("");
        }
        
        private boolean validateLopHocPhanInput() {
            if (txtMaLop.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã lớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (txtMaMonHoc.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã môn học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        }
        
        private LopHocPhan createLopHocPhanFromInput() {
            LopHocPhan lhp = new LopHocPhan();
            lhp.setMaLop(txtMaLop.getText().trim());
            lhp.setMaMonHoc(txtMaMonHoc.getText().trim());
            lhp.setMaGiangVien(txtMaGiangVien.getText().trim());
            try {
                lhp.setHocKy(Integer.parseInt(txtHocKy.getText().trim()));
            } catch (NumberFormatException e) {
                lhp.setHocKy(1);
            }
            try {
                lhp.setNamHoc(Integer.parseInt(txtNamHoc.getText().trim()));
            } catch (NumberFormatException e) {
                lhp.setNamHoc(2024);
            }
            return lhp;
        }
    }
}
