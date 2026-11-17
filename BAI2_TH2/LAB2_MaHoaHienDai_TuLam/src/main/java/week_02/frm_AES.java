package week_02;

import javax.swing.JOptionPane;

/**
 * Form AES-GCM + Register / Login (SQL Server) - viết tay bằng code (giống frm_DES).
 */
public class frm_AES extends javax.swing.JFrame {

    /** Tên người dùng đã Login thành công; null = chưa đăng nhập. */
    private String currentUser = null;

    private javax.swing.JTextField txt_username;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JButton btn_sql;
    private javax.swing.JButton btn_register;
    private javax.swing.JButton btn_login;
    private javax.swing.JTextField txt_ticket;
    private javax.swing.JTextArea txt_plaintext;
    private javax.swing.JTextField txt_key;
    private javax.swing.JTextArea txt_ciphertext;
    private javax.swing.JButton btn_encrypt;
    private javax.swing.JButton btn_decrypt;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_open;
    private javax.swing.JLabel lbl_status;

    public frm_AES() {
        setTitle("AES Cipher - Register / Login");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.JLabel lblTitle = new javax.swing.JLabel("AES-GCM - ĐĂNG KÝ / ĐĂNG NHẬP (SQL SERVER)");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));

        txt_username = new javax.swing.JTextField("sinhvien01", 20);
        txt_password = new javax.swing.JPasswordField(20);
        btn_sql = new javax.swing.JButton("Kết nối SQL Server");
        btn_register = new javax.swing.JButton("Register");
        btn_login = new javax.swing.JButton("Login");
        txt_ticket = new javax.swing.JTextField(34);
        txt_ticket.setEditable(false);

        txt_plaintext = new javax.swing.JTextArea(3, 40);
        txt_plaintext.setLineWrap(true);
        txt_key = new javax.swing.JTextField(24);
        txt_ciphertext = new javax.swing.JTextArea(3, 40);
        txt_ciphertext.setLineWrap(true);

        btn_encrypt = new javax.swing.JButton("Encrypt");
        btn_decrypt = new javax.swing.JButton("Decrypt");
        btn_save = new javax.swing.JButton("Save File");
        btn_open = new javax.swing.JButton("Open File");
        lbl_status = new javax.swing.JLabel("<html>Sẵn sàng.</html>");
        lbl_status.setPreferredSize(new java.awt.Dimension(570, 36));
        lbl_status.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        // ---- Sự kiện ----
        btn_sql.addActionListener(e -> new frm_KetNoiSQL().setVisible(true));
        btn_register.addActionListener(e -> register());
        btn_login.addActionListener(e -> login());
        btn_encrypt.addActionListener(e -> encrypt());
        btn_decrypt.addActionListener(e -> decrypt());
        btn_save.addActionListener(e -> {
            String path = FileTextIO.saveText(this, txt_ciphertext, "aes_ciphertext.txt");
            if (path != null) lbl_status.setText("Đã lưu tệp: " + path);
        });
        btn_open.addActionListener(e -> {
            String path = FileTextIO.openText(this, txt_ciphertext);
            if (path != null) lbl_status.setText("Đã mở tệp: " + path);
        });

        // ---- Các hàng gộp nhiều điều khiển ----
        javax.swing.JPanel passRow = flow(5);
        passRow.add(txt_password);
        passRow.add(new javax.swing.JLabel("(ít nhất 8 ký tự)"));

        javax.swing.JPanel accountButtons = flow(10);
        accountButtons.add(btn_sql);
        accountButtons.add(btn_register);
        accountButtons.add(btn_login);

        javax.swing.JPanel keyRow = flow(5);
        keyRow.add(txt_key);
        keyRow.add(new javax.swing.JLabel("Mật khẩu để tạo khóa AES-256"));

        javax.swing.JPanel buttons = flow(10);
        buttons.add(btn_encrypt);
        buttons.add(btn_decrypt);
        buttons.add(btn_save);
        buttons.add(btn_open);

        // ---- Bố cục ----
        javax.swing.JPanel form = new javax.swing.JPanel(new java.awt.GridBagLayout());
        form.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20));
        java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
        c.insets = new java.awt.Insets(6, 6, 6, 6);
        c.anchor = java.awt.GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        form.add(lblTitle, c);
        c.gridwidth = 1;

        addRow(form, c, 1, "Username:", txt_username, false);
        addRow(form, c, 2, "Password:", passRow, false);
        addRow(form, c, 3, "", accountButtons, false);
        addRow(form, c, 4, "Ticket:", txt_ticket, false);
        addRow(form, c, 5, "Plaintext:", new javax.swing.JScrollPane(txt_plaintext), true);
        addRow(form, c, 6, "Key AES:", keyRow, false);
        addRow(form, c, 7, "Ciphertext:", new javax.swing.JScrollPane(txt_ciphertext), true);
        addRow(form, c, 8, "", buttons, false);
        addRow(form, c, 9, "", lbl_status, false);

        setContentPane(form);
        pack();
        setLocationRelativeTo(null);
    }

    /** Panel xếp ngang từ trái sang phải. */
    private static javax.swing.JPanel flow(int gap) {
        return new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, gap, 0));
    }

    /** Thêm một hàng: cột 0 là nhãn, cột 1 là điều khiển (wide = giãn ngang cho Text Area). */
    private static void addRow(javax.swing.JPanel form, java.awt.GridBagConstraints c, int row,
            String label, java.awt.Component comp, boolean wide) {
        c.gridy = row;
        c.gridx = 0;
        c.fill = java.awt.GridBagConstraints.NONE;
        c.weightx = 0;
        c.anchor = wide ? java.awt.GridBagConstraints.NORTHWEST : java.awt.GridBagConstraints.WEST;
        form.add(new javax.swing.JLabel(label), c);
        c.gridx = 1;
        if (wide) {
            c.fill = java.awt.GridBagConstraints.HORIZONTAL;
            c.weightx = 1;
        }
        form.add(comp, c);
    }

    private void register() {
        try {
            String user = txt_username.getText().trim();
            UserDao.register(user, txt_password.getPassword());
            HistoryDao.tryInsert("AES", "REGISTER", user, 0, 0);
            lbl_status.setText("Register thành công: " + user + ". Bấm Login để đăng nhập.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi SQL Server: " + ex.getMessage(),
                    "Lỗi SQL Server", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void login() {
        try {
            String user = txt_username.getText().trim();
            if (UserDao.login(user, txt_password.getPassword())) {
                currentUser = user;
                txt_ticket.setText(UserDao.newTicket());
                HistoryDao.tryInsert("AES", "LOGIN", user, 0, 0);
                lbl_status.setText("Login thành công: " + user + ". Có thể dùng Encrypt/Decrypt.");
            } else {
                currentUser = null;
                txt_ticket.setText("");
                JOptionPane.showMessageDialog(this, "Sai username hoặc mật khẩu.",
                        "Login thất bại", JOptionPane.ERROR_MESSAGE);
            }
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi SQL Server: " + ex.getMessage(),
                    "Lỗi SQL Server", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void encrypt() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Hãy Login trước khi dùng AES.");
            return;
        }
        String plaintext = txt_plaintext.getText();
        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
            return;
        }
        try {
            String ciphertext = AESCipher.encrypt(plaintext, txt_key.getText());
            txt_ciphertext.setText(ciphertext);
            lbl_status.setText("<html>Đã mã hóa AES. " + HistoryDao.tryInsert("AES", "ENCRYPT", currentUser,
                    plaintext.length(), ciphertext.length()) + "</html>");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Mã hóa thất bại:\n" + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void decrypt() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Hãy Login trước khi dùng AES.");
            return;
        }
        String ciphertext = txt_ciphertext.getText();
        if (ciphertext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext trước khi giải mã.");
            return;
        }
        try {
            String plaintext = AESCipher.decrypt(ciphertext, txt_key.getText());
            txt_plaintext.setText(plaintext);
            lbl_status.setText("<html>Đã giải mã AES. " + HistoryDao.tryInsert("AES", "DECRYPT", currentUser,
                    ciphertext.length(), plaintext.length()) + "</html>");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai Key hoặc bản mã bị sửa.\n" + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_AES().setVisible(true));
    }
}