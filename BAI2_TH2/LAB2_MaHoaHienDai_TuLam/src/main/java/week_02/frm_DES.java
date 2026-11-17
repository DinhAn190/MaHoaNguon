package week_02;

import javax.swing.JOptionPane;

public class frm_DES extends javax.swing.JFrame {

    private javax.swing.JButton btn_decrypt;
    private javax.swing.JButton btn_encrypt;
    private javax.swing.JButton btn_open;
    private javax.swing.JButton btn_save;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JTextArea txt_ciphertext;
    private javax.swing.JTextField txt_key;
    private javax.swing.JTextArea txt_plaintext;

    public frm_DES() {
        setTitle("DES Cipher");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.JLabel lblTitle = new javax.swing.JLabel("MẬT MÃ DES");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));
        javax.swing.JLabel lblHint = new javax.swing.JLabel("Đúng 8 ký tự không dấu (8 byte)");

        txt_plaintext = new javax.swing.JTextArea(3, 40);
        txt_plaintext.setLineWrap(true);
        txt_ciphertext = new javax.swing.JTextArea(3, 40);
        txt_ciphertext.setLineWrap(true);
        txt_key = new javax.swing.JTextField("12345678", 20);

        btn_encrypt = new javax.swing.JButton("Encrypt");
        btn_decrypt = new javax.swing.JButton("Decrypt");
        btn_save = new javax.swing.JButton("Save File");
        btn_open = new javax.swing.JButton("Open File");
        lbl_status = new javax.swing.JLabel("<html>Sẵn sàng.</html>");
        lbl_status.setPreferredSize(new java.awt.Dimension(570, 36));
        lbl_status.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        btn_encrypt.addActionListener(e -> encrypt());
        btn_decrypt.addActionListener(e -> decrypt());
        btn_save.addActionListener(e -> {
            String path = FileTextIO.saveText(this, txt_ciphertext, "des_ciphertext.txt");
            if (path != null) lbl_status.setText("Đã lưu tệp: " + path);
        });
        btn_open.addActionListener(e -> {
            String path = FileTextIO.openText(this, txt_ciphertext);
            if (path != null) lbl_status.setText("Đã mở tệp: " + path);
        });

        javax.swing.JPanel keyRow = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));
        keyRow.add(txt_key);
        keyRow.add(lblHint);

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

        c.gridy = 3; c.gridx = 0; c.anchor = java.awt.GridBagConstraints.NORTHWEST;
        form.add(new javax.swing.JLabel("Ciphertext:"), c);
        c.gridx = 1; c.fill = java.awt.GridBagConstraints.HORIZONTAL; c.weightx = 1;
        form.add(new javax.swing.JScrollPane(txt_ciphertext), c);

        c.gridy = 4; c.gridx = 1; c.fill = java.awt.GridBagConstraints.NONE; c.weightx = 0;
        c.anchor = java.awt.GridBagConstraints.WEST;
        form.add(buttons, c);

        c.gridy = 5;
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
            String ciphertext = DESCipher.encrypt(plaintext, txt_key.getText());
            txt_ciphertext.setText(ciphertext);
            lbl_status.setText("<html>Đã mã hóa DES. " + HistoryDao.tryInsert("DES", "ENCRYPT", null,
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
            String plaintext = DESCipher.decrypt(ciphertext, txt_key.getText());
            txt_plaintext.setText(plaintext);
            lbl_status.setText("<html>Đã giải mã DES. " + HistoryDao.tryInsert("DES", "DECRYPT", null,
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
        java.awt.EventQueue.invokeLater(() -> new frm_DES().setVisible(true));
    }
}