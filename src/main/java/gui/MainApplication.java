package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;


public class MainApplication extends JFrame {
    
    private JTabbedPane tabbedPane;
    
    public MainApplication() {
        initializeComponents();
        setupLayout();
        setupFrame();
    }
    
    private void initializeComponents() {
        tabbedPane = new JTabbedPane();
        
        StudentManagementPanel studentPanel = new StudentManagementPanel();
        CourseManagementPanel coursePanel = new CourseManagementPanel();
        GradeManagementPanel gradePanel = new GradeManagementPanel();
        RegistrationManagementPanel registrationPanel = new RegistrationManagementPanel();
        KhoaManagementPanel khoaPanel = new KhoaManagementPanel();
        LopHocPhanManagementPanel lopHocPhanPanel = new LopHocPhanManagementPanel();
        LopManagementPanel lopPanel = new LopManagementPanel();
        GiangVienManagementPanel giangVienPanel = new GiangVienManagementPanel();
        
        tabbedPane.addTab("Sinh viên", studentPanel);
        tabbedPane.addTab("Môn học", coursePanel);
        tabbedPane.addTab("Điểm", gradePanel);
        tabbedPane.addTab("Đăng ký", registrationPanel);
        tabbedPane.addTab("Khoa", khoaPanel);
        tabbedPane.addTab("Lớp Học Phần", lopHocPhanPanel);
        tabbedPane.addTab("Lớp", lopPanel);
        tabbedPane.addTab("Giảng Viên", giangVienPanel);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        JLabel statusLabel = new JLabel("Hệ thống quản lý sinh viên - Sẵn sàng");
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void setupFrame() {
        setTitle("Hệ thống Quản lý Sinh viên - PTIT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainApplication().setVisible(true);
        });
    }
}
