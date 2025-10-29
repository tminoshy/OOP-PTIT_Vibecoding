package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import dao.DangKyDAO;
import model.DangKy;


public class RegistrationManagementPanel extends JPanel {
    
    private DangKyDAO dangKyDAO;
    private JTable registrationTable;
    private DefaultTableModel tableModel;
    private JTextField txtMaDangKy, txtMaSinhVien, txtMaLop, txtNgayDangKy;
    
    public RegistrationManagementPanel() {
        dangKyDAO = new DangKyDAO();
        initializeComponents();
        setupLayout();
        loadRegistrationData();
    }
    
    private void initializeComponents() {
        txtMaDangKy = new JTextField(15);
        txtMaSinhVien = new JTextField(15);
        txtMaLop = new JTextField(15);
        txtNgayDangKy = new JTextField(15);
        
        String[] columnNames = {"Mã đăng ký", "Mã sinh viên", "Mã lớp", "Ngày đăng ký"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        registrationTable = new JTable(tableModel);
        registrationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        registrationTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedRegistration();
            }
        });
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin đăng ký"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Mã đăng ký:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtMaDangKy, gbc);
        
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Mã sinh viên:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtMaSinhVien, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Mã lớp:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtMaLop, gbc);
        
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Ngày đăng ký:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtNgayDangKy, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới");
        JButton btnRefresh = new JButton("Tải lại");
        
        btnAdd.addActionListener(e -> addRegistration());
        btnUpdate.addActionListener(e -> updateRegistration());
        btnDelete.addActionListener(e -> deleteRegistration());
        btnClear.addActionListener(e -> clearFields());
        btnRefresh.addActionListener(e -> loadRegistrationData());
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách đăng ký"));
        JScrollPane scrollPane = new JScrollPane(registrationTable);
        scrollPane.setPreferredSize(new Dimension(0, 300));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);
    }
    
    private void loadRegistrationData() {
        tableModel.setRowCount(0);
        List<DangKy> registrations = dangKyDAO.getAllDangKy();
        
        for (DangKy dk : registrations) {
            Object[] row = {
                dk.getMaDangKy(),
                dk.getMaSinhVien(),
                dk.getMaLop(),
                dk.getNgayDangKy()
            };
            tableModel.addRow(row);
        }
    }
    
    private void loadSelectedRegistration() {
        int selectedRow = registrationTable.getSelectedRow();
        if (selectedRow >= 0) {
            txtMaDangKy.setText((String) tableModel.getValueAt(selectedRow, 0));
            txtMaSinhVien.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtMaLop.setText((String) tableModel.getValueAt(selectedRow, 2));
            txtNgayDangKy.setText((String) tableModel.getValueAt(selectedRow, 3));
        }
    }
    
    private void addRegistration() {
        if (validateInput()) {
            DangKy dk = createRegistrationFromInput();
            if (dangKyDAO.addDangKy(dk)) {
                JOptionPane.showMessageDialog(this, "Thêm đăng ký thành công!");
                loadRegistrationData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm đăng ký!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void updateRegistration() {
        if (validateInput()) {
            DangKy dk = createRegistrationFromInput();
            if (dangKyDAO.updateDangKy(dk)) {
                JOptionPane.showMessageDialog(this, "Cập nhật đăng ký thành công!");
                loadRegistrationData();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật đăng ký!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteRegistration() {
        String maDangKy = txtMaDangKy.getText().trim();
        if (maDangKy.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đăng ký cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa đăng ký " + maDangKy + "?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (dangKyDAO.deleteDangKy(maDangKy)) {
                JOptionPane.showMessageDialog(this, "Xóa đăng ký thành công!");
                loadRegistrationData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa đăng ký!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void clearFields() {
        txtMaDangKy.setText("");
        txtMaSinhVien.setText("");
        txtMaLop.setText("");
        txtNgayDangKy.setText("");
        registrationTable.clearSelection();
    }
    
    private boolean validateInput() {
        if (txtMaDangKy.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã đăng ký!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtMaSinhVien.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sinh viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtMaLop.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã lớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private DangKy createRegistrationFromInput() {
        DangKy dk = new DangKy();
        dk.setMaDangKy(txtMaDangKy.getText().trim());
        dk.setMaSinhVien(txtMaSinhVien.getText().trim());
        dk.setMaLop(txtMaLop.getText().trim());
        dk.setNgayDangKy(txtNgayDangKy.getText().trim());
        return dk;
    }
}
