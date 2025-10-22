package gui;

import dao.BangDiemDAO;
import model.BangDiem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for managing grades (BangDiem)
 * Provides CRUD operations for grade management
 */
public class GradeManagementPanel extends JPanel {
    
    private BangDiemDAO bangDiemDAO;
    private JTable gradeTable;
    private DefaultTableModel tableModel;
    private JTextField txtIdBangDiem, txtMaDangKy, txtDiemChuyenCan, txtDiemGiuaKy, txtDiemCuoiKy, txtDiemTongKet;
    
    public GradeManagementPanel() {
        bangDiemDAO = new BangDiemDAO();
        initializeComponents();
        setupLayout();
        loadGradeData();
    }
    
    private void initializeComponents() {
        txtIdBangDiem = new JTextField(15);
        txtMaDangKy = new JTextField(15);
        txtDiemChuyenCan = new JTextField(10);
        txtDiemGiuaKy = new JTextField(10);
        txtDiemCuoiKy = new JTextField(10);
        txtDiemTongKet = new JTextField(10);
        
        String[] columnNames = {"ID Bảng điểm", "Mã đăng ký", "Điểm chuyên cần", "Điểm giữa kỳ", "Điểm cuối kỳ", "Điểm tổng kết"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        gradeTable = new JTable(tableModel);
        gradeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gradeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedGrade();
            }
        });
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin bảng điểm"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("ID Bảng điểm:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtIdBangDiem, gbc);
        
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Mã đăng ký:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtMaDangKy, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Điểm chuyên cần:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtDiemChuyenCan, gbc);
        
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Điểm giữa kỳ:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtDiemGiuaKy, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Điểm cuối kỳ:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtDiemCuoiKy, gbc);
        
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Điểm tổng kết:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtDiemTongKet, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới");
        JButton btnRefresh = new JButton("Tải lại");
        JButton btnCalculate = new JButton("Tính điểm tổng kết");
        
        btnAdd.addActionListener(e -> addGrade());
        btnUpdate.addActionListener(e -> updateGrade());
        btnDelete.addActionListener(e -> deleteGrade());
        btnClear.addActionListener(e -> clearFields());
        btnRefresh.addActionListener(e -> loadGradeData());
        btnCalculate.addActionListener(e -> calculateTotalGrade());
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnCalculate);
        
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách bảng điểm"));
        JScrollPane scrollPane = new JScrollPane(gradeTable);
        scrollPane.setPreferredSize(new Dimension(0, 300));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);
    }
    
    private void loadGradeData() {
        tableModel.setRowCount(0);
        List<BangDiem> grades = bangDiemDAO.getAllBangDiem();
        
        for (BangDiem bd : grades) {
            Object[] row = {
                bd.getIdBangDiem(),
                bd.getMaDangKy(),
                bd.getDiemChuyenCan(),
                bd.getDiemGiuaKy(),
                bd.getDiemCuoiKy(),
                bd.getDiemTongKet()
            };
            tableModel.addRow(row);
        }
    }
    
    private void loadSelectedGrade() {
        int selectedRow = gradeTable.getSelectedRow();
        if (selectedRow >= 0) {
            txtIdBangDiem.setText((String) tableModel.getValueAt(selectedRow, 0));
            txtMaDangKy.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtDiemChuyenCan.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
            txtDiemGiuaKy.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
            txtDiemCuoiKy.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
            txtDiemTongKet.setText(String.valueOf(tableModel.getValueAt(selectedRow, 5)));
        }
    }
    
    private void addGrade() {
        if (validateInput()) {
            BangDiem bd = createGradeFromInput();
            if (bangDiemDAO.addBangDiem(bd)) {
                JOptionPane.showMessageDialog(this, "Thêm bảng điểm thành công!");
                loadGradeData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm bảng điểm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateGrade() {
        if (validateInput()) {
            BangDiem bd = createGradeFromInput();
            if (bangDiemDAO.updateBangDiem(bd)) {
                JOptionPane.showMessageDialog(this, "Cập nhật bảng điểm thành công!");
                loadGradeData();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật bảng điểm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteGrade() {
        String idBangDiem = txtIdBangDiem.getText().trim();
        if (idBangDiem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bảng điểm cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa bảng điểm " + idBangDiem + "?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (bangDiemDAO.deleteBangDiem(idBangDiem)) {
                JOptionPane.showMessageDialog(this, "Xóa bảng điểm thành công!");
                loadGradeData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa bảng điểm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void calculateTotalGrade() {
        try {
            float diemChuyenCan = Float.parseFloat(txtDiemChuyenCan.getText().trim());
            float diemGiuaKy = Float.parseFloat(txtDiemGiuaKy.getText().trim());
            float diemCuoiKy = Float.parseFloat(txtDiemCuoiKy.getText().trim());
            
            // Simple calculation: 20% attendance + 30% midterm + 50% final
            float diemTongKet = (diemChuyenCan * 0.2f) + (diemGiuaKy * 0.3f) + (diemCuoiKy * 0.5f);
            
            txtDiemTongKet.setText(String.format("%.2f", diemTongKet));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearFields() {
        txtIdBangDiem.setText("");
        txtMaDangKy.setText("");
        txtDiemChuyenCan.setText("");
        txtDiemGiuaKy.setText("");
        txtDiemCuoiKy.setText("");
        txtDiemTongKet.setText("");
        gradeTable.clearSelection();
    }
    
    private boolean validateInput() {
        if (txtIdBangDiem.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ID bảng điểm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtMaDangKy.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã đăng ký!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private BangDiem createGradeFromInput() {
        BangDiem bd = new BangDiem();
        bd.setIdBangDiem(txtIdBangDiem.getText().trim());
        bd.setMaDangKy(txtMaDangKy.getText().trim());
        
        try {
            bd.setDiemChuyenCan(Float.parseFloat(txtDiemChuyenCan.getText().trim()));
        } catch (NumberFormatException e) {
            bd.setDiemChuyenCan(0.0f);
        }
        
        try {
            bd.setDiemGiuaKy(Float.parseFloat(txtDiemGiuaKy.getText().trim()));
        } catch (NumberFormatException e) {
            bd.setDiemGiuaKy(0.0f);
        }
        
        try {
            bd.setDiemCuoiKy(Float.parseFloat(txtDiemCuoiKy.getText().trim()));
        } catch (NumberFormatException e) {
            bd.setDiemCuoiKy(0.0f);
        }
        
        try {
            bd.setDiemTongKet(Float.parseFloat(txtDiemTongKet.getText().trim()));
        } catch (NumberFormatException e) {
            bd.setDiemTongKet(0.0f);
        }
        
        return bd;
    }
}
