package week_02;

import javax.swing.JOptionPane;

/**
 * Form Twofish (CBC) - giống frm_DES nhưng có thêm hàng IV (HEX) + nút Generate IV.
 * Save File lưu cả IV (dòng 1) và bản mã (dòng 2); Open File khôi phục cả hai.
 */
public class frm_Twofish extends javax.swing.JFrame {

    private javax.swing.JButton btn_decrypt;
    private javax.swing.JButton btn_encrypt;
    private javax.swing.JButton btn_iv;
    private javax.swing.JButton btn_open;
    private javax.swing.JButton btn_save;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JTextArea txt_ciphertext;
    private javax.swing.JTextField txt_iv;
    private javax.swing.JTextField txt_key;
    private javax.swing.JTextArea txt_plaintext;

    public frm_Twofish() {
        setTitle("Twofish Cipher");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.JLabel lblTitle = new javax.swing.JLabel("MẬT MÃ TWOFISH (CBC)");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));
        javax.swing.JLabel lblHint = new javax.swing.JLabel("Mật khẩu bất kỳ (khóa = SHA-256)");

        txt_plaintext = new javax.swing.JTextArea(3, 40);
        txt_plaintext.setLineWrap(true);
        txt_ciphertext = new javax.swing.JTextArea(3, 40);
        txt_ciphertext.setLineWrap(true);
        txt_key = new javax.swing.JTextField("Twofish@Lab2", 20);
        txt_iv = new javax.swing.JTextField(28);

        btn_iv = new javax.swing.JButton("Generate IV");
        btn_encrypt = new javax.swing.JButton("Encrypt");
        btn_decrypt = new javax.swing.JButton("Decrypt");
        btn_save = new javax.swing.JButton("Save File");
        btn_open = new javax.swing.JButton("Open File");
        lbl_status = new javax.swing.JLabel("<html>Sẵn sàng. Bấm Generate IV trước khi Encrypt.</html>");
        lbl_status.setPreferredSize(new java.awt.Dimension(570, 36));
        lbl_status.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btn_iv.addActionListener(e -> {
            txt_iv.setText(TwofishCipher.newIv());
            lbl_status.setText("Đã tạo IV ngẫu nhiên 16 byte. Giữ nguyên IV này để giải mã.");
        });
        btn_encrypt.addActionListener(e -> encrypt());
        btn_decrypt.addActionListener(e -> decrypt());
        btn_save.addActionListener(e -> save());
        btn_open.addActionListener(e -> open());

        javax.swing.JPanel keyRow = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));
        keyRow.add(txt_key);
        keyRow.add(lblHint);

        javax.swing.JPanel ivRow = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));
        ivRow.add(txt_iv);
        ivRow.add(btn_iv);

        javax.swing.JPanel buttons = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        buttons.add(btn_encrypt);
        buttons.add(btn_decrypt);
        buttons.add(btn_save);
        buttons.add(btn_open);

        javax.swing.JPanel form = new javax.swing.JPanel(new java.awt.GridBagLayout());
        form.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20));
        java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
        c.insets = new java.awt.Insets(6, 6, 6, 6);
        c.anchor = java.awt.GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        form.add(lblTitle, c);
        c.gridwidth = 1;

        c.gridy = 1; c.gridx = 0; c.anchor = java.awt.GridBagConstraints.NORTHWEST;
        form.add(new javax.swing.JLabel("Plaintext:"), c);
        c.gridx = 1; c.fill = java.awt.GridBagConstraints.HORIZONTAL; c.weightx = 1;
        form.add(new javax.swing.JScrollPane(txt_plaintext), c);

        c.gridy = 2; c.gridx = 0; c.fill = java.awt.GridBagConstraints.NONE; c.weightx = 0;
        c.anchor = java.awt.GridBagConstraints.WEST;
        form.add(new javax.swing.JLabel("Key:"), c);
        c.gridx = 1;
        form.add(keyRow, c);

        // Hàng mới: IV (HEX)
        c.gridy = 3; c.gridx = 0;
        form.add(new javax.swing.JLabel("IV (HEX):"), c);
        c.gridx = 1;
        form.add(ivRow, c);

        c.gridy = 4; c.gridx = 0; c.anchor = java.awt.GridBagConstraints.NORTHWEST;
        form.add(new javax.swing.JLabel("Ciphertext:"), c);
        c.gridx = 1; c.fill = java.awt.GridBagConstraints.HORIZONTAL; c.weightx = 1;
        form.add(new javax.swing.JScrollPane(txt_ciphertext), c);

        c.gridy = 5; c.gridx = 1; c.fill = java.awt.GridBagConstraints.NONE; c.weightx = 0;
        c.anchor = java.awt.GridBagConstraints.WEST;
        form.add(buttons, c);
        c.gridy = 6;
        form.add(lbl_status, c);

        setContentPane(form);
        pack();
        setLocationRelativeTo(null);
    }

    private void encrypt() {
        String plaintext = txt_plaintext.getText();
        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
            return;
        }
        try {
            String ciphertext = TwofishCipher.encrypt(plaintext, txt_key.getText(), txt_iv.getText());
            txt_ciphertext.setText(ciphertext);
            lbl_status.setText("<html>Đã mã hóa Twofish. " + HistoryDao.tryInsert("Twofish", "ENCRYPT", null,
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
        String ciphertext = txt_ciphertext.getText();
        if (ciphertext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext trước khi giải mã.");
            return;
        }
        try {
            String plaintext = TwofishCipher.decrypt(ciphertext, txt_key.getText(), txt_iv.getText());
            txt_plaintext.setText(plaintext);
            lbl_status.setText("<html>Đã giải mã Twofish. " + HistoryDao.tryInsert("Twofish", "DECRYPT", null,
                    ciphertext.length(), plaintext.length()) + "</html>");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai Key hoặc bản mã bị sửa.\n" + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void save() {
        if (txt_ciphertext.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Chưa có bản mã để lưu.");
            return;
        }
        String content = TwofishCipher.toFile(txt_iv.getText(), txt_ciphertext.getText());
        String path = FileTextIO.saveString(this, content, "twofish_ciphertext.txt");
        if (path != null) {
            lbl_status.setText("Đã lưu IV + bản mã vào: " + path);
        }
    }

    private void open() {
        String content = FileTextIO.openString(this);
        if (content == null) {
            return;
        }
        try {
            String[] parts = TwofishCipher.fromFile(content);
            txt_iv.setText(parts[0]);
            txt_ciphertext.setText(parts[1]);
            lbl_status.setText("Đã mở tệp, khôi phục IV và bản mã. Nhập đúng Key rồi bấm Decrypt.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Tệp không hợp lệ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_Twofish().setVisible(true));
    }
}