package gui;

import dao.SinhVienDAO;
import model.SinhVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class StudentManagementPanel extends JPanel {
    
    private SinhVienDAO sinhVienDAO;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField txtMaSinhVien, txtHoTen, txtNgaySinh, txtDiaChi, txtEmail, txtSdt, txtCCCD, txtMaLop;
    private JComboBox<String> cmbGioiTinh;
    
    public StudentManagementPanel() {
        sinhVienDAO = new SinhVienDAO();
        initializeComponents();
        setupLayout();
        loadStudentData();
    }
    
    private void initializeComponents() {
        txtMaSinhVien = new JTextField(15);
        txtHoTen = new JTextField(20);
        txtNgaySinh = new JTextField(15);
        txtDiaChi = new JTextField(25);
        txtEmail = new JTextField(20);
        txtSdt = new JTextField(15);
        txtCCCD = new JTextField(15);
        txtMaLop = new JTextField(15);
        
        cmbGioiTinh = new JComboBox<>(new String[]{"M", "F", "K"});
        
        String[] columnNames = {"Mã SV", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ", "Email", "SĐT", "CCCD", "Mã lớp"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedStudent();
            }
        });
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel tablePanel = createTablePanel();
        
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Mã sinh viên:"), gbc);
        gbc.gridx = 1;
        panel.add(txtMaSinhVien, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 3;
        panel.add(txtHoTen, gbc);

        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Ngày sinh:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNgaySinh, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 3;
        panel.add(cmbGioiTinh, gbc);
        

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        panel.add(txtDiaChi, gbc);
        

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("SĐT:"), gbc);
        gbc.gridx = 3;
        panel.add(txtSdt, gbc);
        

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("CCCD:"), gbc);
        gbc.gridx = 1;
        panel.add(txtCCCD, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Mã lớp:"), gbc);
        gbc.gridx = 3;
        panel.add(txtMaLop, gbc);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới");
        JButton btnRefresh = new JButton("Tải lại");
        
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearFields());
        btnRefresh.addActionListener(e -> loadStudentData());
        
        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);
        panel.add(btnRefresh);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Danh sách sinh viên"));
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setPreferredSize(new Dimension(0, 300));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadStudentData() {
        tableModel.setRowCount(0);
        List<SinhVien> students = sinhVienDAO.getAllSinhVien();
        
        for (SinhVien sv : students) {
            Object[] row = {
                sv.getMaSinhVien(),
                sv.getHoTen(),
                sv.getNgaySinh(),
                sv.getGioiTinh(),
                sv.getDiaChi(),
                sv.getEmail(),
                sv.getSdt(),
                sv.getCCCD(),
                sv.getMaLop()
            };
            tableModel.addRow(row);
        }
    }
    
    private void loadSelectedStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            txtMaSinhVien.setText((String) tableModel.getValueAt(selectedRow, 0));
            txtHoTen.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtNgaySinh.setText((String) tableModel.getValueAt(selectedRow, 2));
            cmbGioiTinh.setSelectedItem(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
            txtDiaChi.setText((String) tableModel.getValueAt(selectedRow, 4));
            txtEmail.setText((String) tableModel.getValueAt(selectedRow, 5));
            txtSdt.setText((String) tableModel.getValueAt(selectedRow, 6));
            txtCCCD.setText((String) tableModel.getValueAt(selectedRow, 7));
            txtMaLop.setText((String) tableModel.getValueAt(selectedRow, 8));
        }
    }
    
    private void addStudent() {
        if (validateInput()) {
            SinhVien sv = createStudentFromInput();
            if (sinhVienDAO.addSinhVien(sv)) {
                JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công!");
                loadStudentData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm sinh viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateStudent() {
        if (validateInput()) {
            SinhVien sv = createStudentFromInput();
            if (sinhVienDAO.updateSinhVien(sv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật sinh viên thành công!");
                loadStudentData();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật sinh viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteStudent() {
        String maSinhVien = txtMaSinhVien.getText().trim();
        if (maSinhVien.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa sinh viên " + maSinhVien + "?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (sinhVienDAO.deleteSinhVien(maSinhVien)) {
                JOptionPane.showMessageDialog(this, "Xóa sinh viên thành công!");
                loadStudentData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa sinh viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void clearFields() {
        txtMaSinhVien.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        cmbGioiTinh.setSelectedIndex(0);
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSdt.setText("");
        txtCCCD.setText("");
        txtMaLop.setText("");
        studentTable.clearSelection();
    }
    
    private boolean validateInput() {
        if (txtMaSinhVien.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sinh viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtHoTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private SinhVien createStudentFromInput() {
        SinhVien sv = new SinhVien();
        sv.setMaSinhVien(txtMaSinhVien.getText().trim());
        sv.setHoTen(txtHoTen.getText().trim());
        sv.setNgaySinh(txtNgaySinh.getText().trim());
        sv.setGioiTinh(((String) cmbGioiTinh.getSelectedItem()).charAt(0));
        sv.setDiaChi(txtDiaChi.getText().trim());
        sv.setEmail(txtEmail.getText().trim());
        sv.setSdt(txtSdt.getText().trim());
        sv.setCCCD(txtCCCD.getText().trim());
        sv.setMaLop(txtMaLop.getText().trim());
        return sv;
    }
}
