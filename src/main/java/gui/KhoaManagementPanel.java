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

import dao.KhoaDAO;
import model.Khoa;


public class KhoaManagementPanel extends JPanel {

    private KhoaDAO khoaDAO;
    private JTable khoaTable;
    private DefaultTableModel tableModel;
    private JTextField txtMaKhoa, txtTenKhoa;

    public KhoaManagementPanel() {
        khoaDAO = new KhoaDAO();
        initializeComponents();
        setupLayout();
        loadKhoaData();
    }

    private void initializeComponents() {
        txtMaKhoa = new JTextField(15);
        txtTenKhoa = new JTextField(15);

        String[] columnNames = {"Mã khoa", "Tên khoa"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        khoaTable = new JTable(tableModel);
        khoaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        khoaTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedKhoa();
            }
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khoa"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Mã khoa:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtMaKhoa, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Tên khoa:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtTenKhoa, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới");
        JButton btnRefresh = new JButton("Tải lại");

        btnAdd.addActionListener(e -> addKhoa());
        btnUpdate.addActionListener(e -> updateKhoa());
        btnDelete.addActionListener(e -> deleteKhoa());
        btnClear.addActionListener(e -> clearFields());
        btnRefresh.addActionListener(e -> loadKhoaData());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách khoa"));
        JScrollPane scrollPane = new JScrollPane(khoaTable);
        scrollPane.setPreferredSize(new Dimension(0, 300));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);
    }


    private void loadKhoaData() {
        tableModel.setRowCount(0); 
        List<Khoa> khoaList = khoaDAO.getAllKhoa();

        for (Khoa k : khoaList) {
            Object[] row = {
                    k.getMaKhoa(),
                    k.getTenKhoa()
            };
            tableModel.addRow(row);
        }
    }


    private void loadSelectedKhoa() {
        int selectedRow = khoaTable.getSelectedRow();
        if (selectedRow >= 0) {
            txtMaKhoa.setText((String) tableModel.getValueAt(selectedRow, 0));
            txtTenKhoa.setText((String) tableModel.getValueAt(selectedRow, 1));
        }
    }

    private void addKhoa() {
        if (validateInput()) {
            Khoa k = createKhoaFromInput();
            if (khoaDAO.addKhoa(k)) {
                JOptionPane.showMessageDialog(this, "Thêm khoa thành công!");
                loadKhoaData();
                clearFields(); 
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm khoa! Mã khoa có thể đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void updateKhoa() {
        if (validateInput()) {
            Khoa k = createKhoaFromInput();
            if (khoaDAO.updateKhoa(k)) {
                JOptionPane.showMessageDialog(this, "Cập nhật khoa thành công!");
                loadKhoaData();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật khoa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void deleteKhoa() {
        String maKhoa = txtMaKhoa.getText().trim();
        if (maKhoa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khoa cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa khoa " + maKhoa + "?\n(Tất cả sinh viên, lớp, giảng viên... thuộc khoa này có thể bị ảnh hưởng)",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (khoaDAO.deleteKhoa(maKhoa)) {
                JOptionPane.showMessageDialog(this, "Xóa khoa thành công!");
                loadKhoaData(); 
                clearFields(); 
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa khoa! (Kiểm tra xem có dữ liệu ràng buộc không)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void clearFields() {
        txtMaKhoa.setText("");
        txtTenKhoa.setText("");
        khoaTable.clearSelection();
    }


    private boolean validateInput() {
        if (txtMaKhoa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khoa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtTenKhoa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khoa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private Khoa createKhoaFromInput() {
        Khoa k = new Khoa();
        k.setMaKhoa(txtMaKhoa.getText().trim());
        k.setTenKhoa(txtTenKhoa.getText().trim());
        return k;
    }
}
