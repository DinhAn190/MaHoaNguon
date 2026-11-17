package week_02;

import javax.swing.JOptionPane;

/**
 * Bài nâng cao 01 - AES-256 chế độ CTR - viết tay bằng code (giống frm_DES).
 */
public class frm_AES_CTR extends javax.swing.JFrame {

    private javax.swing.JTextArea txt_plaintext;
    private javax.swing.JTextField txt_key;
    private javax.swing.JButton btn_genkey;
    private javax.swing.JTextField txt_nonce;
    private javax.swing.JButton btn_gennonce;
    private javax.swing.JTextArea txt_ciphertext;
    private javax.swing.JButton btn_encrypt;
    private javax.swing.JButton btn_decrypt;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_open;
    private javax.swing.JLabel lbl_status;

    public frm_AES_CTR() {
        setTitle("AES-CTR Cipher");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.JLabel lblTitle = new javax.swing.JLabel("BÀI NÂNG CAO 01 - AES-256 CHẾ ĐỘ CTR");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));

        txt_plaintext = new javax.swing.JTextArea(3, 40);
        txt_plaintext.setLineWrap(true);
        txt_key = new javax.swing.JTextField(34);
        btn_genkey = new javax.swing.JButton("Generate Key");
        txt_nonce = new javax.swing.JTextField(34);
        btn_gennonce = new javax.swing.JButton("Generate Nonce");
        txt_ciphertext = new javax.swing.JTextArea(3, 40);
        txt_ciphertext.setLineWrap(true);

        btn_encrypt = new javax.swing.JButton("Encrypt");
        btn_decrypt = new javax.swing.JButton("Decrypt");
        btn_save = new javax.swing.JButton("Save File");
        btn_open = new javax.swing.JButton("Open File");
        lbl_status = new javax.swing.JLabel("<html>Sẵn sàng. Bấm Generate Key và Generate Nonce trước.</html>");
        lbl_status.setPreferredSize(new java.awt.Dimension(570, 36));
        lbl_status.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        // ---- Sự kiện ----
        btn_genkey.addActionListener(e -> {
            txt_key.setText(AESCTRCipher.newKey());
            lbl_status.setText("Đã tạo khóa ngẫu nhiên 256 bit.");
        });
        btn_gennonce.addActionListener(e -> {
            txt_nonce.setText(AESCTRCipher.newNonce());
            lbl_status.setText("Đã tạo nonce ngẫu nhiên 96 bit. Mỗi lần mã hóa nên tạo nonce mới.");
        });
        btn_encrypt.addActionListener(e -> encrypt());
        btn_decrypt.addActionListener(e -> decrypt());
        btn_save.addActionListener(e -> save());
        btn_open.addActionListener(e -> open());

        // ---- Các hàng gộp ----
        javax.swing.JPanel keyRow = flow(5);
        keyRow.add(txt_key);
        keyRow.add(btn_genkey);

        javax.swing.JPanel nonceRow = flow(5);
        nonceRow.add(txt_nonce);
        nonceRow.add(btn_gennonce);

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

        addRow(form, c, 1, "Plaintext:", new javax.swing.JScrollPane(txt_plaintext), true);
        addRow(form, c, 2, "Key (Base64):", keyRow, false);
        addRow(form, c, 3, "Nonce (Base64):", nonceRow, false);
        addRow(form, c, 4, "Ciphertext:", new javax.swing.JScrollPane(txt_ciphertext), true);
        addRow(form, c, 5, "", buttons, false);
        addRow(form, c, 6, "", lbl_status, false);

        setContentPane(form);
        pack();
        setLocationRelativeTo(null);
    }

    private static javax.swing.JPanel flow(int gap) {
        return new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, gap, 0));
    }

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

    private void encrypt() {
        String plaintext = txt_plaintext.getText();
        if (plaintext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
            return;
        }
        try {
            String ciphertext = AESCTRCipher.encrypt(plaintext, txt_key.getText(), txt_nonce.getText());
            txt_ciphertext.setText(ciphertext);
            lbl_status.setText("<html>Đã mã hóa AES-CTR. " + HistoryDao.tryInsert("AES-CTR", "ENCRYPT", null,
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
            String plaintext = AESCTRCipher.decrypt(ciphertext, txt_key.getText(), txt_nonce.getText());
            txt_plaintext.setText(plaintext);
            lbl_status.setText("<html>Đã giải mã AES-CTR. " + HistoryDao.tryInsert("AES-CTR", "DECRYPT", null,
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
        String content = AESCTRCipher.toFile(txt_nonce.getText(), txt_ciphertext.getText());
        String path = FileTextIO.saveString(this, content, "aes_ctr_ciphertext.txt");
        if (path != null) {
            lbl_status.setText("Đã lưu nonce + bản mã (không lưu Key) vào: " + path);
        }
    }

    private void open() {
        String content = FileTextIO.openString(this);
        if (content == null) {
            return;
        }
        try {
            String[] parts = AESCTRCipher.fromFile(content);
            txt_nonce.setText(parts[0]);
            txt_ciphertext.setText(parts[1]);
            lbl_status.setText("Đã mở tệp. Nhập đúng Key rồi bấm Decrypt.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Tệp không hợp lệ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_AES_CTR().setVisible(true));
    }
}