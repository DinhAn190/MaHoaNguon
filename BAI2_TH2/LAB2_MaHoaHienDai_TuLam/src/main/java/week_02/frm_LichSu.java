package week_02;

public class frm_LichSu extends javax.swing.JFrame {

    private javax.swing.JTable tbl_history;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JLabel lbl_status;

    public frm_LichSu() {
        setTitle("Lịch sử thao tác (SQL Server)");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.JLabel lblTitle = new javax.swing.JLabel("LỊCH SỬ THAO TÁC - BẢNG DBO.CRYPTO_HISTORY");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));

        tbl_history = new javax.swing.JTable(HistoryDao.emptyModel());
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(tbl_history);
        scroll.setPreferredSize(new java.awt.Dimension(760, 260));

        btn_refresh = new javax.swing.JButton("Tải lại");
        lbl_status = new javax.swing.JLabel("Đang tải...");
        btn_refresh.addActionListener(e -> loadData());

        javax.swing.JPanel bottom = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
        bottom.add(btn_refresh);
        bottom.add(lbl_status);

        javax.swing.JPanel form = new javax.swing.JPanel(new java.awt.BorderLayout(0, 10));
        form.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20));
        form.add(lblTitle, java.awt.BorderLayout.NORTH);
        form.add(scroll, java.awt.BorderLayout.CENTER);
        form.add(bottom, java.awt.BorderLayout.SOUTH);

        setContentPane(form);
        pack();
        setLocationRelativeTo(null);
        loadData();
    }

    /** Đọc 50 dòng mới nhất từ SQL Server và hiện lên JTable. */
    private void loadData() {
        try {
            tbl_history.setModel(HistoryDao.loadLatest(50));
            lbl_status.setText("Đã tải " + tbl_history.getRowCount() + " dòng mới nhất từ dbo.crypto_history.");
        } catch (java.sql.SQLException ex) {
            lbl_status.setText("Không đọc được lịch sử: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_LichSu().setVisible(true));
    }
}