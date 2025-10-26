package gui;

import dao.LopDAO;
import model.Lop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class LopManagementPanel extends JPanel {

    private LopDAO lopDAO;
    private JTable lopTable;
    private DefaultTableModel tableModel;
    private JTextField txtMaLop, txtPhongHocChinh, txtMaKhoa, txtSiSo;

    public LopManagementPanel() {
        lopDAO = new LopDAO();
        initializeComponents();
        setupLayout();
        loadLopData();
    }

    private void initializeComponents() {
        txtMaLop = new JTextField(15);
        txtPhongHocChinh = new JTextField(15);
        txtMaKhoa = new JTextField(15);
        txtSiSo = new JTextField(15);

        String[] columnNames = {"Mã lớp", "Phòng học chính", "Mã khoa", "Sĩ số"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {

                return false;
            }
        };
        lopTable = new JTable(tableModel);
        lopTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        lopTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedLop();
            }
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout());


        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin lớp"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Mã lớp:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtMaLop, gbc);

        gbc.gridx = 2; gbc.gridy = 0;
        inputPanel.add(new JLabel("Phòng học chính:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtPhongHocChinh, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Mã khoa:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtMaKhoa, gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        inputPanel.add(new JLabel("Sĩ số:"), gbc);
        gbc.gridx = 3;
        inputPanel.add(txtSiSo, gbc);



        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới");
        JButton btnRefresh = new JButton("Tải lại");


        btnAdd.addActionListener(e -> addLop());
        btnUpdate.addActionListener(e -> updateLop());
        btnDelete.addActionListener(e -> deleteLop());
        btnClear.addActionListener(e -> clearFields());
        btnRefresh.addActionListener(e -> loadLopData());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);


        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách lớp"));
        JScrollPane scrollPane = new JScrollPane(lopTable);
        scrollPane.setPreferredSize(new Dimension(0, 300));
        tablePanel.add(scrollPane, BorderLayout.CENTER);


        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);
    }


    private void loadLopData() {
        tableModel.setRowCount(0); // Clear existing data
        List<Lop> lopList = lopDAO.getAllLop();

        for (Lop lop : lopList) {
            Object[] row = {
                    lop.getMaLop(),
                    lop.getPhongHocChinh(),
                    lop.getMaKhoa(),
                    lop.getSiSo()
            };
            tableModel.addRow(row);
        }
    }


    private void loadSelectedLop() {
        int selectedRow = lopTable.getSelectedRow();
        if (selectedRow >= 0) {
            txtMaLop.setText((String) tableModel.getValueAt(selectedRow, 0));
            txtPhongHocChinh.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtMaKhoa.setText((String) tableModel.getValueAt(selectedRow, 2));
            txtSiSo.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
        }
    }


    private void addLop() {
        Lop lop = createLopFromInput();
        if (lop != null) {
            if (lopDAO.addLop(lop)) {
                JOptionPane.showMessageDialog(this, "Thêm lớp thành công!");
                loadLopData(); // Refresh table
                clearFields(); // Clear form
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm lớp! Mã lớp có thể đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void updateLop() {
        Lop lop = createLopFromInput();
        if (lop != null) {
            if (lopDAO.updateLop(lop)) {
                JOptionPane.showMessageDialog(this, "Cập nhật lớp thành công!");
                loadLopData(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật lớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void deleteLop() {
        String maLop = txtMaLop.getText().trim();
        if (maLop.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa lớp " + maLop + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (lopDAO.deleteLop(maLop)) {
                JOptionPane.showMessageDialog(this, "Xóa lớp thành công!");
                loadLopData(); // Refresh table
                clearFields(); // Clear form
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa lớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void clearFields() {
        txtMaLop.setText("");
        txtPhongHocChinh.setText("");
        txtMaKhoa.setText("");
        txtSiSo.setText("");
        lopTable.clearSelection();
    }


    private boolean validateInput() {
        if (txtMaLop.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã lớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtPhongHocChinh.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập phòng học chính!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtMaKhoa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khoa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtSiSo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập sĩ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        try {
            Integer.parseInt(txtSiSo.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Sĩ số phải là một số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }


    private Lop createLopFromInput() {
        if (!validateInput()) {
            return null;
        }

        Lop lop = new Lop();
        lop.setMaLop(txtMaLop.getText().trim());
        lop.setPhongHocChinh(txtPhongHocChinh.getText().trim());
        lop.setMaKhoa(txtMaKhoa.getText().trim());
        lop.setSiSo(Integer.parseInt(txtSiSo.getText().trim()));
        return lop;
    }
}
