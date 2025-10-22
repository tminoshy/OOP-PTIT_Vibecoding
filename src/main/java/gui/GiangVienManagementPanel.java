package gui;

import dao.GiangVienDAO;
import model.GiangVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for managing lecturers (GiangVien)
 * Provides CRUD operations for lecturer management
 * (Matches the style of KhoaManagementPanel)
 */
public class GiangVienManagementPanel extends JPanel {

    private GiangVienDAO giangVienDAO;
    private JTable giangVienTable;
    private DefaultTableModel tableModel;
    private JTextField txtMaGiangVien, txtHoVaTen, txtEmail, txtSoDienThoai,
            txtMaKhoa, txtNgaySinh, txtGioiTinh, txtTrinhDo, txtChucVu;

    public GiangVienManagementPanel() {
        giangVienDAO = new GiangVienDAO();
        initializeComponents();
        setupLayout();
        loadGiangVienData();
    }

    private void initializeComponents() {
        txtMaGiangVien = new JTextField(15);
        txtHoVaTen = new JTextField(15);
        txtEmail = new JTextField(15);
        txtSoDienThoai = new JTextField(15);
        txtMaKhoa = new JTextField(15);
        txtNgaySinh = new JTextField(15);
        txtGioiTinh = new JTextField(15);
        txtTrinhDo = new JTextField(15);
        txtChucVu = new JTextField(15);

        String[] columnNames = {"Mã GV", "Họ tên", "Email", "SĐT", "Mã khoa", "Ngày sinh", "Giới tính", "Trình độ", "Chức vụ"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        giangVienTable = new JTable(tableModel);
        giangVienTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        giangVienTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // For wide tables

        // Add a selection listener to the table
        giangVienTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedGiangVien();
            }
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin giảng viên"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.anchor = GridBagConstraints.WEST;

        // Row 1
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Mã GV:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtMaGiangVien, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        inputPanel.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtHoVaTen, gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtEmail, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        inputPanel.add(new JLabel("SĐT:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtSoDienThoai, gbc);

        // Row 3
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Mã khoa:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtMaKhoa, gbc);

        gbc.gridx = 2; gbc.gridy = 2;
        inputPanel.add(new JLabel("Ngày sinh (yyyy-mm-dd):"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtNgaySinh, gbc);

        // Row 4
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Giới tính (M/F/O):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtGioiTinh, gbc);

        gbc.gridx = 2; gbc.gridy = 3;
        inputPanel.add(new JLabel("Trình độ:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtTrinhDo, gbc);

        // Row 5
        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(new JLabel("Chức vụ:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtChucVu, gbc);


        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới");
        JButton btnRefresh = new JButton("Tải lại");

        // Add action listeners
        btnAdd.addActionListener(e -> addGiangVien());
        btnUpdate.addActionListener(e -> updateGiangVien());
        btnDelete.addActionListener(e -> deleteGiangVien());
        btnClear.addActionListener(e -> clearFields());
        btnRefresh.addActionListener(e -> loadGiangVienData());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách giảng viên"));
        JScrollPane scrollPane = new JScrollPane(giangVienTable);
        scrollPane.setPreferredSize(new Dimension(0, 300));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to the main layout
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);
    }

    /**
     * Loads all GiangVien from the database and populates the JTable
     */
    private void loadGiangVienData() {
        tableModel.setRowCount(0); // Clear existing data
        List<GiangVien> giangVienList = giangVienDAO.getAllGiangVien();

        for (GiangVien gv : giangVienList) {
            Object[] row = {
                    gv.getMaGiangVien(),
                    gv.getHoVaTen(),
                    gv.getEmail(),
                    gv.getSoDienThoai(),
                    gv.getMaKhoa(),
                    gv.getNgaySinh(),
                    gv.getGioiTinh(),
                    gv.getHocVi(),
                    gv.getHocHam()
            };
            tableModel.addRow(row);
        }
    }

    /**
     * Populates the text fields when a row in the table is selected
     */
    private void loadSelectedGiangVien() {
        int selectedRow = giangVienTable.getSelectedRow();
        if (selectedRow >= 0) {
            txtMaGiangVien.setText((String) tableModel.getValueAt(selectedRow, 0));
            txtHoVaTen.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtEmail.setText((String) tableModel.getValueAt(selectedRow, 2));
            txtSoDienThoai.setText((String) tableModel.getValueAt(selectedRow, 3));
            txtMaKhoa.setText((String) tableModel.getValueAt(selectedRow, 4));
            txtNgaySinh.setText((String) tableModel.getValueAt(selectedRow, 5));
            txtGioiTinh.setText(String.valueOf(tableModel.getValueAt(selectedRow, 6)));
            txtTrinhDo.setText((String) tableModel.getValueAt(selectedRow, 7));
            txtChucVu.setText((String) tableModel.getValueAt(selectedRow, 8));
        }
    }

    /**
     * Validates input and adds a new GiangVien to the database
     */
    private void addGiangVien() {
        GiangVien gv = createGiangVienFromInput();
        if (gv != null) {
            if (giangVienDAO.addGiangVien(gv)) {
                JOptionPane.showMessageDialog(this, "Thêm giảng viên thành công!");
                loadGiangVienData(); // Refresh table
                clearFields(); // Clear form
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm giảng viên! Mã GV có thể đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Validates input and updates an existing GiangVien in the database
     */
    private void updateGiangVien() {
        GiangVien gv = createGiangVienFromInput();
        if (gv != null) {
            if (giangVienDAO.updateGiangVien(gv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật giảng viên thành công!");
                loadGiangVienData(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật giảng viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Deletes the selected GiangVien from the database
     */
    private void deleteGiangVien() {
        String maGV = txtMaGiangVien.getText().trim();
        if (maGV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giảng viên cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa giảng viên " + maGV + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (giangVienDAO.deleteGiangVien(maGV)) {
                JOptionPane.showMessageDialog(this, "Xóa giảng viên thành công!");
                loadGiangVienData(); // Refresh table
                clearFields(); // Clear form
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa giảng viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Clears all text fields and table selection
     */
    private void clearFields() {
        txtMaGiangVien.setText("");
        txtHoVaTen.setText("");
        txtEmail.setText("");
        txtSoDienThoai.setText("");
        txtMaKhoa.setText("");
        txtNgaySinh.setText("");
        txtGioiTinh.setText("");
        txtTrinhDo.setText("");
        txtChucVu.setText("");
        giangVienTable.clearSelection();
    }

    /**
     * Validates that required fields are not empty and are in the correct format
     * @return true if input is valid, false otherwise
     */
    private boolean validateInput() {
        if (txtMaGiangVien.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã giảng viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtHoVaTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtMaKhoa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khoa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtGioiTinh.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập giới tính!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (txtGioiTinh.getText().trim().length() > 1) {
            JOptionPane.showMessageDialog(this, "Giới tính chỉ nên là 1 ký tự (M, F, O)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Add more validation as needed (e.g., email format, date format)
        return true;
    }

    /**
     * Creates a GiangVien object from the text field inputs
     * @return a new GiangVien object, or null if validation fails
     */
    private GiangVien createGiangVienFromInput() {
        if (!validateInput()) {
            return null; // Return null if validation fails
        }

        GiangVien gv = new GiangVien();
        gv.setMaGiangVien(txtMaGiangVien.getText().trim());
        gv.setHoVaTen(txtHoVaTen.getText().trim());
        gv.setEmail(txtEmail.getText().trim());
        gv.setSoDienThoai(txtSoDienThoai.getText().trim());
        gv.setMaKhoa(txtMaKhoa.getText().trim());
        gv.setNgaySinh(txtNgaySinh.getText().trim());
        gv.setGioiTinh(txtGioiTinh.getText().trim().charAt(0)); // Get first char
        gv.setHocVi(txtTrinhDo.getText().trim());
        gv.setHocHam(txtChucVu.getText().trim());
        return gv;
    }
}