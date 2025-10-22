package gui;

import dao.LopHocPhanDAO;
import model.LopHocPhan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for managing course sections (LopHocPhan)
 * Provides CRUD operations for course section management
 * (Matches the style of KhoaManagementPanel)
 */
public class LopHocPhanManagementPanel extends JPanel {

    private LopHocPhanDAO lopHocPhanDAO;
    private JTable lopHocPhanTable;
    private DefaultTableModel tableModel;
    private JTextField txtMaLop, txtMaMonHoc, txtMaGiangVien, txtHocKy, txtNamHoc;

    public LopHocPhanManagementPanel() {
        lopHocPhanDAO = new LopHocPhanDAO();
        initializeComponents();
        setupLayout();
        loadLopHocPhanData();
    }

    private void initializeComponents() {
        txtMaLop = new JTextField(15);
        txtMaMonHoc = new JTextField(15);
        txtMaGiangVien = new JTextField(15);
        txtHocKy = new JTextField(15);
        txtNamHoc = new JTextField(15);

        String[] columnNames = {"Mã lớp", "Mã môn học", "Mã giảng viên", "Học kỳ", "Năm học"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make table cells not editable
                return false;
            }
        };
        lopHocPhanTable = new JTable(tableModel);
        lopHocPhanTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add a selection listener to the table
        lopHocPhanTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedLopHocPhan();
            }
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin lớp học phần"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Mã lớp:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtMaLop, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        inputPanel.add(new JLabel("Mã môn học:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtMaMonHoc, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Mã giảng viên:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtMaGiangVien, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
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

        // Add action listeners
        btnAdd.addActionListener(e -> addLopHocPhan());
        btnUpdate.addActionListener(e -> updateLopHocPhan());
        btnDelete.addActionListener(e -> deleteLopHocPhan());
        btnClear.addActionListener(e -> clearFields());
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

        // Add panels to the main layout
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);
    }

    /**
     * Loads all LopHocPhan from the database and populates the JTable
     */
    private void loadLopHocPhanData() {
        tableModel.setRowCount(0); // Clear existing data
        List<LopHocPhan> lopHocPhanList = lopHocPhanDAO.getAllLopHocPhan();

        for (LopHocPhan lhp : lopHocPhanList) {
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

    /**
     * Populates the text fields when a row in the table is selected
     */
    private void loadSelectedLopHocPhan() {
        int selectedRow = lopHocPhanTable.getSelectedRow();
        if (selectedRow >= 0) {
            txtMaLop.setText((String) tableModel.getValueAt(selectedRow, 0));
            txtMaMonHoc.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtMaGiangVien.setText((String) tableModel.getValueAt(selectedRow, 2));
            txtHocKy.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
            txtNamHoc.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
        }
    }

    /**
     * Validates input and adds a new LopHocPhan to the database
     */
    private void addLopHocPhan() {
        LopHocPhan lhp = createLopHocPhanFromInput();
        if (lhp != null) {
            if (lopHocPhanDAO.addLopHocPhan(lhp)) {
                JOptionPane.showMessageDialog(this, "Thêm lớp học phần thành công!");
                loadLopHocPhanData(); // Refresh table
                clearFields(); // Clear form
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm lớp học phần! Mã lớp có thể đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Validates input and updates an existing LopHocPhan in the database
     */
    private void updateLopHocPhan() {
        LopHocPhan lhp = createLopHocPhanFromInput();
        if (lhp != null) {
            if (lopHocPhanDAO.updateLopHocPhan(lhp)) {
                JOptionPane.showMessageDialog(this, "Cập nhật lớp học phần thành công!");
                loadLopHocPhanData(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật lớp học phần!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Deletes the selected LopHocPhan from the database
     */
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
                loadLopHocPhanData(); // Refresh table
                clearFields(); // Clear form
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa lớp học phần!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Clears all text fields and table selection
     */
    private void clearFields() {
        txtMaLop.setText("");
        txtMaMonHoc.setText("");
        txtMaGiangVien.setText("");
        txtHocKy.setText("");
        txtNamHoc.setText("");
        lopHocPhanTable.clearSelection();
    }

    /**
     * Validates that required fields are not empty and are in the correct format
     * @return true if input is valid, false otherwise
     */
    private boolean validateInput() {
        if (txtMaLop.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã lớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtMaMonHoc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã môn học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtMaGiangVien.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã giảng viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtHocKy.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập học kỳ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtNamHoc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập năm học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate integer fields
        try {
            Integer.parseInt(txtHocKy.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Học kỳ phải là một số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(txtNamHoc.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Năm học phải là một số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * Creates a LopHocPhan object from the text field inputs
     * @return a new LopHocPhan object, or null if validation fails
     */
    private LopHocPhan createLopHocPhanFromInput() {
        if (!validateInput()) {
            return null; // Return null if validation fails
        }

        LopHocPhan lhp = new LopHocPhan();
        lhp.setMaLop(txtMaLop.getText().trim());
        lhp.setMaMonHoc(txtMaMonHoc.getText().trim());
        lhp.setMaGiangVien(txtMaGiangVien.getText().trim());
        lhp.setHocKy(Integer.parseInt(txtHocKy.getText().trim()));
        lhp.setNamHoc(Integer.parseInt(txtNamHoc.getText().trim()));
        return lhp;
    }
}