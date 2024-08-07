/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.form;

import app.model.BillDetailModel;
import app.model.BillModel;
import app.service.BillDetailService;
import app.service.BillService;
import app.tabbed.TabbedForm;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.toast.Notifications;

/**
 *
 * @author ADMIN
 */
public final class Bill extends TabbedForm {
    
    @Override
    public void fromRefresh() {
        this.fillTable(hdsr.getAll());
    }

    private DefaultTableModel model = new DefaultTableModel();
    private final BillService hdsr = new BillService();
    private final BillDetailService cthd = new BillDetailService();
    DefaultComboBoxModel<String> dcb1 = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> dcb2 = new DefaultComboBoxModel<>();

    /**
     * Creates new form Bill
     */
    public Bill() {
        initComponents();
        dcb1 = (DefaultComboBoxModel<String>) cboTrangThaiHD.getModel();
        dcb2 = (DefaultComboBoxModel<String>) cboHinhThuc.getModel();
        loadTrangThai();
        loadHinhThuc();
        this.fillTable(hdsr.getAll());
    }

    void loadTrangThai() {
        dcb1.addElement("Tất cả");
        dcb1.addElement("Đã thanh toán");
        dcb1.addElement("Đã hủy");
        dcb1.addElement("Chờ thanh toán");
    }

    void loadHinhThuc() {
        dcb2.addElement("Tất cả");
        dcb2.addElement("Tiền mặt");
        dcb2.addElement("Chuyển khoản");
        dcb2.addElement("Kết hợp");
    }

    private void fillTable(List<BillModel> list) {
        if (list == null) {
            // Xử lý trường hợp list là null (nếu cần)
            return;
        }

        model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        int index = 1;
        for (BillModel hoaDonModel : list) {
            hoaDonModel.setStt(index++);
            model.addRow(hoaDonModel.toData2());
        }
        if (model.getRowCount() > 0) {
            tblHoaDon.scrollRectToVisible(tblHoaDon.getCellRect(0, 0, true));
            tblHoaDon.setRowSelectionInterval(0, 0);
        }
    }


    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTimKiemHD = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        cboTrangThaiHD = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cboHinhThuc = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        dateBatDau = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        dateKetThuc = new com.toedter.calendar.JDateChooser();
        btnLoc = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setPreferredSize(new java.awt.Dimension(881, 238));
        jPanel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPanel2KeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tìm kiếm hóa đơn: ");

        txtTimKiemHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiemHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemHDActionPerformed(evt);
            }
        });
        txtTimKiemHD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemHDKeyReleased(evt);
            }
        });

        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Hóa Đơn", "Ngày Tạo", "Tên Nhân Viên", "Tên Khách Hàng", "Tên Voucher", "Tổng Tiền", "Hình thức thanh toán", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Trạng thái hóa đơn:");

        cboTrangThaiHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTrangThaiHD.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTrangThaiHDItemStateChanged(evt);
            }
        });
        cboTrangThaiHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiHDActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Hình thức thanh toán:");

        cboHinhThuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboHinhThuc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboHinhThucItemStateChanged(evt);
            }
        });
        cboHinhThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHinhThucActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Từ :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Đến :");

        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cboTrangThaiHD, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboHinhThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 485, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(dateBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(dateKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(cboTrangThaiHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(cboHinhThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(dateKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(btnLoc))
                                .addGap(2, 2, 2))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(dateBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(1295, 500));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Hóa Đơn Chi Tiết");

        tblHoaDonChiTiet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MaHDCT", "SanPham", "MauSac", "Size", "ThuongHieu", "ChatLieu", "DonGia", "SoLuong", "ThanhTien"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonChiTiet.setPreferredSize(new java.awt.Dimension(1900, 200));
        tblHoaDonChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonChiTietMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHoaDonChiTiet);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane3))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1697, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1685, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1685, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 762, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                    .addGap(18, 18, 18)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(29, 29, 29)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemHDActionPerformed

    private void txtTimKiemHDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemHDKeyReleased
        // TODO add your handling code here:
        String keyword = txtTimKiemHD.getText().trim();
        List<BillModel> searchResult = hdsr.searchBills(keyword);
        fillTable(searchResult);

        if (searchResult.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Không tìm thấy kết quả phù hợp.");
        }
    }//GEN-LAST:event_txtTimKiemHDKeyReleased

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // Lấy chỉ số hàng được chọn
        int selectedRowIndex = tblHoaDon.getSelectedRow();

        // Đảm bảo có hàng được chọn
        if (selectedRowIndex >= 0) {
            // Lấy giá trị cột "Mã Hóa Đơn" trong hàng được chọn
            String maHoaDon = (String) tblHoaDon.getValueAt(selectedRowIndex, 1);

            // Lấy danh sách chi tiết hóa đơn cho mã hóa đơn đã chọn
            List<BillDetailModel> listCTHD = cthd.searchByHoaDonID(maHoaDon);

            // Xóa dữ liệu hiện có trong mô hình bảng chi tiết hóa đơn
            DefaultTableModel modelCTHD = (DefaultTableModel) tblHoaDonChiTiet.getModel();
            modelCTHD.setRowCount(0);

            // Điền dữ liệu vào bảng chi tiết hóa đơn với các thông tin đã lấy được
            int index = 1;
            for (BillDetailModel cthd : listCTHD) {
                cthd.setStt(index++);
                modelCTHD.addRow(cthd.toData2());
            }

            // Cuộn đến đầu bảng chi tiết hóa đơn
            if (modelCTHD.getRowCount() > 0) {
                tblHoaDonChiTiet.scrollRectToVisible(tblHoaDonChiTiet.getCellRect(0, 0, true));
            }
        } else {
            // Không có hàng nào được chọn, có thể xử lý trường hợp này bằng thông báo hoặc logic khác
            System.out.println("Không có hàng nào được chọn");
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void cboTrangThaiHDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTrangThaiHDItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTrangThaiHDItemStateChanged

    private void cboTrangThaiHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiHDActionPerformed
        // TODO add your handling code here:
        String selectedStatus = (String) cboTrangThaiHD.getSelectedItem();

        if ("Tất cả".equals(selectedStatus)) {
            fillTable(hdsr.getAll());
        } else if ("Đã thanh toán".equals(selectedStatus)) {
            fillTable(hdsr.getDaThanhToanHoaDon());
        } else if ("Đã hủy".equals(selectedStatus)) {
            fillTable(hdsr.getDaHuyHoaDon());
        } else if ("Chờ thanh toán".equals(selectedStatus)) {
            fillTable(hdsr.getHoaDonChoThanhToan());
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Trạng thái không hợp lệ!");
        }
    }//GEN-LAST:event_cboTrangThaiHDActionPerformed

    private void cboHinhThucItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboHinhThucItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHinhThucItemStateChanged

    private void cboHinhThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHinhThucActionPerformed
        // TODO add your handling code here:
        String selecdHinhThuc = (String) cboHinhThuc.getSelectedItem();

        if ("Tất cả".equals(selecdHinhThuc)) {
            fillTable(hdsr.getAll());
        } else if ("Tiền mặt".equals(selecdHinhThuc)) {
            fillTable(hdsr.getHDTienMat());
        } else if ("Chuyển khoản".equals(selecdHinhThuc)) {
            fillTable(hdsr.getHDTienChuyenKhoan());
        } else if ("Kết hợp".equals(selecdHinhThuc)) {
            fillTable(hdsr.getHDKetHop());
        } else {
            JOptionPane.showMessageDialog(this, "Hình thức không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cboHinhThucActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        java.util.Date ngayBD = dateBatDau.getDate();
        java.util.Date ngayKT = dateKetThuc.getDate();

        if (ngayBD != null && ngayKT != null) {
            java.sql.Date sqlNgayBD = new java.sql.Date(ngayBD.getTime());
            java.sql.Date sqlNgayKT = new java.sql.Date(ngayKT.getTime());
            List<BillModel> listHD = hdsr.searchByDateRange(sqlNgayBD, sqlNgayKT);
            fillTable(listHD);

            if (listHD.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Không tìm thấy hóa đơn trong khoảng thời gian này.");
            }
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc");
        }
    }//GEN-LAST:event_btnLocActionPerformed

    private void jPanel2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel2KeyReleased

    private void tblHoaDonChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonChiTietMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoaDonChiTietMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoc;
    private javax.swing.JComboBox<String> cboHinhThuc;
    private javax.swing.JComboBox<String> cboTrangThaiHD;
    private com.toedter.calendar.JDateChooser dateBatDau;
    private com.toedter.calendar.JDateChooser dateKetThuc;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonChiTiet;
    private javax.swing.JTextField txtTimKiemHD;
    // End of variables declaration//GEN-END:variables
}
