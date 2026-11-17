package week_02;

import javax.swing.JOptionPane;

public class frm_KetNoiSQL extends javax.swing.JFrame {

    private javax.swing.JTextField txt_server;
    private javax.swing.JTextField txt_port;
    private javax.swing.JTextField txt_database;
    private javax.swing.JTextField txt_user;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JButton btn_test;
    private javax.swing.JButton btn_history;
    private javax.swing.JLabel lbl_status;

    public frm_KetNoiSQL() {
        setTitle("Kết nối Microsoft SQL Server");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.JLabel lblTitle = new javax.swing.JLabel("KẾT NỐI MICROSOFT SQL SERVER");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));

        txt_server = new javax.swing.JTextField("127.0.0.1", 25);
        txt_port = new javax.swing.JTextField("14333", 8);
        txt_database = new javax.swing.JTextField("LAB2_MaHoaHienDai", 25);
        txt_user = new javax.swing.JTextField("lab2_user", 25);
        txt_password = new javax.swing.JPasswordField(25);
        btn_test = new javax.swing.JButton("Test Connection");
        btn_history = new javax.swing.JButton("Xem lịch sử");
        lbl_status = new javax.swing.JLabel("<html>Chưa kết nối.</html>");
        lbl_status.setPreferredSize(new java.awt.Dimension(420, 40));
        lbl_status.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btn_test.addActionListener(e -> testConnection());
        btn_history.addActionListener(e -> new frm_LichSu().setVisible(true));

        javax.swing.JPanel form = new javax.swing.JPanel(new java.awt.GridBagLayout());
        form.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20));
        java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
        c.insets = new java.awt.Insets(6, 6, 6, 6);
        c.anchor = java.awt.GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        form.add(lblTitle, c);
        c.gridwidth = 1;

        String[] labels = {"Server:", "Port:", "Database:", "User:", "Password:"};
        java.awt.Component[] fields = {txt_server, txt_port, txt_database, txt_user, txt_password};
        for (int i = 0; i < labels.length; i++) {
            c.gridy = i + 1; c.gridx = 0;
            form.add(new javax.swing.JLabel(labels[i]), c);
            c.gridx = 1;
            form.add(fields[i], c);
        }

        javax.swing.JPanel buttons = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        buttons.add(btn_test);
        buttons.add(btn_history);
        c.gridy = 6; c.gridx = 1;
        form.add(buttons, c);

        c.gridy = 7;
        form.add(lbl_status, c);

        setContentPane(form);
        pack();
        setLocationRelativeTo(null);
    }

    private void testConnection() {
        try {
            int port = Integer.parseInt(txt_port.getText().trim());
            DbConfig.set(txt_server.getText().trim(), port, txt_database.getText().trim(),
                    txt_user.getText().trim(), txt_password.getPassword());
            lbl_status.setText("<html>" + DbConfig.test() + "</html>");
        } catch (Exception ex) {
            DbConfig.clear();
            lbl_status.setText("<html>Kết nối thất bại: " + ex.getMessage() + "</html>");
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Không kết nối được SQL Server", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_KetNoiSQL().setVisible(true));
    }
}