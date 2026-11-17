package week_02;

/**
 * Cửa sổ chính LAB 2 - mở từng form bài tập. Riêng form này đóng là thoát chương trình.
 */
public class frm_Menu extends javax.swing.JFrame {

    private javax.swing.JButton btn_des;
    private javax.swing.JButton btn_tripledes;
    private javax.swing.JButton btn_aes;
    private javax.swing.JButton btn_rc4;
    private javax.swing.JButton btn_twofish;
    private javax.swing.JButton btn_aesctr;
    private javax.swing.JButton btn_sql;
    private javax.swing.JButton btn_history;

    public frm_Menu() {
        setTitle("LAB 2 - Mã hóa đối xứng hiện đại");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.JLabel lblTitle = new javax.swing.JLabel("LAB 2 - MÃ HÓA ĐỐI XỨNG HIỆN ĐẠI");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 20));
        javax.swing.JLabel lblSub = new javax.swing.JLabel("Chọn một bài để mở form tương ứng:");
        javax.swing.JLabel lblPython = new javax.swing.JLabel(
                "Bài nâng cao 02 (AES-CBC) viết bằng Python: chạy tệp aes_cbc_gui.py.");

        btn_des = button("2.1 DES");
        btn_tripledes = button("2.2 TripleDES");
        btn_aes = button("2.3 AES + Login");
        btn_rc4 = button("2.4 RC4");
        btn_twofish = button("2.5 Twofish");
        btn_aesctr = button("2.6 Nâng cao - AES-CTR");
        btn_sql = button("Kết nối SQL Server");
        btn_history = button("Xem lịch sử");

        btn_des.addActionListener(e -> new frm_DES().setVisible(true));
        btn_tripledes.addActionListener(e -> new frm_TripleDES().setVisible(true));
        btn_aes.addActionListener(e -> new frm_AES().setVisible(true));
        btn_rc4.addActionListener(e -> new frm_RC4().setVisible(true));
        btn_twofish.addActionListener(e -> new frm_Twofish().setVisible(true));
        btn_aesctr.addActionListener(e -> new frm_AES_CTR().setVisible(true));
        btn_sql.addActionListener(e -> new frm_KetNoiSQL().setVisible(true));
        btn_history.addActionListener(e -> new frm_LichSu().setVisible(true));

        // Lưới 4 hàng x 2 cột nút, cách nhau 12 px
        javax.swing.JPanel grid = new javax.swing.JPanel(new java.awt.GridLayout(4, 2, 12, 12));
        grid.add(btn_des);
        grid.add(btn_tripledes);
        grid.add(btn_aes);
        grid.add(btn_rc4);
        grid.add(btn_twofish);
        grid.add(btn_aesctr);
        grid.add(btn_sql);
        grid.add(btn_history);

        javax.swing.JPanel form = new javax.swing.JPanel(new java.awt.GridBagLayout());
        form.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20));
        java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
        c.insets = new java.awt.Insets(6, 6, 6, 6);
        c.anchor = java.awt.GridBagConstraints.WEST;
        c.gridx = 0;

        c.gridy = 0;
        form.add(lblTitle, c);
        c.gridy = 1;
        form.add(lblSub, c);
        c.gridy = 2;
        form.add(grid, c);
        c.gridy = 3;
        form.add(lblPython, c);

        setContentPane(form);
        pack();
        setLocationRelativeTo(null);
    }

    private static javax.swing.JButton button(String text) {
        javax.swing.JButton b = new javax.swing.JButton(text);
        b.setPreferredSize(new java.awt.Dimension(220, 32));
        return b;
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_Menu().setVisible(true));
    }
}