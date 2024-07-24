/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.form;

import app.model.ProductDetailModel;
import app.model.StaffModel;
import app.model.VoucherModer;
import app.service.VoucherService;
import app.tabbed.TabbedForm;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import raven.alerts.MessageAlerts;
import raven.popup.component.PopupCallbackAction;
import raven.popup.component.PopupController;
import raven.toast.Notifications;

/**
 *
 * @author ADMIN
 */
public class Voucher extends TabbedForm {

    private int row = -1;
    private DefaultTableModel dtmm;
    private VoucherService sreService;
    private int currentPage = 1;
  private static final int RECORDS_PER_PAGE = 10;

    /**
     * Creates new form Voucher
     */
    public Voucher() {
        initComponents();
        dtmm = new DefaultTableModel();
        sreService = new VoucherService();
        initTable();
        FillTable();
        this.row = -1;
    }

    private void initTable() {
        dtmm = (DefaultTableModel) tblvoucher.getModel();
        String[] columnNames = {"STT", "ID", "tên Voucher", "Số Lượng", "Loại Voucher", "Mức giảm giá", "Ngày Bắt Đầu", "Ngày Kết Thúc", "mô tả", "Trạng Thái"};
        dtmm.setColumnIdentifiers(columnNames);
    }

    private void FillTable() {
        dtmm.setRowCount(0);
        List<VoucherModer> vcModers = sreService.getAllVoucher();
        for (int i = 0; i < vcModers.size(); i++) {
            VoucherModer vcc = vcModers.get(i);
            vcc.setSTT(i + 1);

            dtmm.addRow(new Object[]{
                vcc.getSTT(),
                vcc.getID(),
                vcc.getTenVoucher(),
                vcc.getSoLuong(),
                vcc.getLoaiVoucher(),
                vcc.getMucGiamGia(),
                vcc.getNgayBatDau(),
                vcc.getNgayKetThuc(),
                vcc.getMoTa(),
                vcc.getTrangThai()

            });

        }

    }

    public boolean ValidateVoucher() {
        String tenVC = txtTen.getText().trim();
        String soLuong = txtSoLuong.getText().trim();
        String mucGiamGia = txtMucGG.getText().trim();
        String moTa = taMota.getText().trim();
        String trangthai = comboxTrangThai.getSelectedItem().toString().trim();
        if (tenVC.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập tên voucher");
            return false;
        }
        if (tenVC.length() > 200 || soLuong.length() > 200 || mucGiamGia.length() > 200 || moTa.length() > 200) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Các trường thông tin không được vượt quá 200 ký tự");
            return false;
        }
        
        if( trangthai.equals("Tất cả")){
           Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng Chọn Trạng Thái");
           return false;
        }
        try {
            if (soLuong.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập Số Lượng");
                return false;
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, " so luong phai Là Số");
            return false;
        }

        try {
            if (mucGiamGia.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập Muc giam gia");
                return false;
            }
        } catch (NumberFormatException e) {
            Notifications.getInstance().show(Notifications.Type.WARNING, " Muc Giam gia phai la so");
            return false;
        }

        if (moTa.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập Mô tả");
            return false;
        }
        Notifications.getInstance().show(Notifications.Type.WARNING, "du lieu hop le");
        return true;
        
        
    }

    VoucherModer getVoucherFromInput() {
        VoucherModer voucher = new VoucherModer();
        String loaiVoucher = "VND";
        voucher.setTenVoucher(txtTen.getText());
        voucher.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));
        voucher.setLoaiVoucher(loaiVoucher);
        voucher.setMucGiamGia(new BigDecimal(txtMucGG.getText().trim()));
        voucher.setMoTa(taMota.getText().trim());
        voucher.setNgayBatDau(new java.sql.Date(JDBD.getDate().getTime()));
        voucher.setNgayKetThuc(new java.sql.Date(jdKT.getDate().getTime()));
        voucher.setTrangThai((String) comboxTrangThai.getSelectedItem());

        return voucher;
    }

    public void cleanForm() {
        txtMucGG.setText("");
        txtSoLuong.setText("");
        txtTen.setText("");
        txtID.setText("");
        taMota.setText("");
    }

    private void updateTable(List<VoucherModer> voucherModers) {
        dtmm.setRowCount(0);
        for (int i = 0; i < voucherModers.size(); i++) {
            VoucherModer voucherModer = voucherModers.get(i);
            voucherModer.setSTT(i + 1);
            dtmm.addRow(voucherModer.toData());
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        JDBD = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblvoucher = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taMota = new javax.swing.JTextArea();
        jdKT = new com.toedter.calendar.JDateChooser();
        txtTen = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtMucGG = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        comboxTrangThai = new javax.swing.JComboBox<>();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(JDBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, -1, -1));

        jLabel1.setText("Tên voucher");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel2.setText("Số Lượng");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel3.setText("Mức giảm Giá");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        tblvoucher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11"
            }
        ));
        tblvoucher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblvoucherMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblvoucher);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 317, 1200, 410));

        jLabel5.setText("Ngày bắt Đầu");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, -1, -1));

        jLabel6.setText("Ngày Kết Thúc");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, -1, -1));

        jLabel7.setText("Trạng Thái");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 270, -1, -1));

        jLabel8.setText("mô tả");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, -1, -1));

        taMota.setColumns(20);
        taMota.setRows(5);
        jScrollPane2.setViewportView(taMota);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, -1, -1));
        add(jdKT, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, -1, -1));
        add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 170, -1));
        add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 170, -1));

        txtMucGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMucGGActionPerformed(evt);
            }
        });
        add(txtMucGG, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 170, -1));

        jLabel4.setText("id");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        txtID.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });
        add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 170, -1));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Tìm Kiếm");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, -1, -1));

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });
        add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, 220, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSua.setBackground(new java.awt.Color(51, 153, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(51, 153, 255));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 153, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 153, 255));
        jButton4.setText("Làm mới");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton1.setText("Kết Thúc");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnThem)
                .addGap(18, 18, 18)
                .addComponent(btnSua)
                .addGap(18, 18, 18)
                .addComponent(btnXoa)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 20, 140, 230));

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 910, 240));

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 1200, 430));

        comboxTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đang Hoạt Động", "Không Hoạt Động" }));
        comboxTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboxTrangThaiActionPerformed(evt);
            }
        });
        add(comboxTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 270, 120, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txtMucGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMucGGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMucGGActionPerformed

    void showdata(int index) {
        txtID.setText(tblvoucher.getValueAt(index, 1).toString().trim());
        txtTen.setText(tblvoucher.getValueAt(index, 2).toString().trim());
        txtMucGG.setText(tblvoucher.getValueAt(index, 5).toString().trim());
        txtSoLuong.setText(tblvoucher.getValueAt(index, 3).toString().trim());
        taMota.setText(tblvoucher.getValueAt(index, 8).toString().trim());
        String trangthai = String.valueOf(tblvoucher.getValueAt(index, 9)).trim();
        comboxTrangThai.setSelectedItem(trangthai);
        Object dateObj = tblvoucher.getValueAt(index, 6);
        try {
            if (dateObj instanceof java.sql.Date) {
                JDBD.setDate(new java.util.Date(((java.sql.Date) dateObj).getTime()));
            } else if (dateObj instanceof java.util.Date) {
                JDBD.setDate((java.util.Date) dateObj);
            } else {
                JDBD.setDate(null);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            JDBD.setDate(null);
        }

        // Xử lý ngày kết thúc
        Object dateObj2 = tblvoucher.getValueAt(index, 7);
        try {
            if (dateObj2 instanceof java.sql.Date) {
                jdKT.setDate(new java.util.Date(((java.sql.Date) dateObj2).getTime()));
            } else if (dateObj2 instanceof java.util.Date) {
                jdKT.setDate((java.util.Date) dateObj2);
            } else {
                jdKT.setDate(null);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            jdKT.setDate(null);
        }

    }
    private void tblvoucherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblvoucherMouseClicked
//        int row1 = tblvoucher.getSelectedRow();
//        if (row1 >= 0) {
//        }
//        if (evt.getClickCount() == 1) {
//            txtID.setText(tblvoucher.getValueAt(row1, 1).toString().trim());
//            txtTen.setText(tblvoucher.getValueAt(row1, 2).toString().trim());
//            txtMucGG.setText(tblvoucher.getValueAt(row1, 5).toString().trim());
//            txtSoLuong.setText(tblvoucher.getValueAt(row1, 3).toString().trim());
//            taMota.setText(tblvoucher.getValueAt(row1, 8).toString().trim());
//            String trangthai = String.valueOf(tblvoucher.getValueAt(row1, 9)).trim();
//            comboxTrangThai.setSelectedItem(trangthai);
//            Object dateObj = tblvoucher.getValueAt(row1, 6);
//            try {
//                if (dateObj instanceof java.sql.Date) {
//                    JDBD.setDate(new java.util.Date(((java.sql.Date) dateObj).getTime()));
//                } else if (dateObj instanceof java.util.Date) {
//                    JDBD.setDate((java.util.Date) dateObj);
//                } else {
//                    JDBD.setDate(null);
//                }
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//                JDBD.setDate(null);
//            }
//
//            // Xử lý ngày kết thúc
//            Object dateObj2 = tblvoucher.getValueAt(row1, 7);
//            try {
//                if (dateObj2 instanceof java.sql.Date) {
//                    jdKT.setDate(new java.util.Date(((java.sql.Date) dateObj2).getTime()));
//                } else if (dateObj2 instanceof java.util.Date) {
//                    jdKT.setDate((java.util.Date) dateObj2);
//                } else {
//                    jdKT.setDate(null);
//                }
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//                jdKT.setDate(null);
//            }
//
//        }

          int index = -1;
          index =tblvoucher.getSelectedRow();
          this.showdata(index);
    }//GEN-LAST:event_tblvoucherMouseClicked

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed

    }//GEN-LAST:event_txtIDActionPerformed

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        String keyword = txtTimKiem.getText().trim();
        List<VoucherModer> searchResult = sreService.searchVoucherByName(keyword);
        updateTable(searchResult);
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        cleanForm();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        String id = txtID.getText();
        if (!id.isEmpty()) {
            MessageAlerts.getInstance().showMessage("Xác nhận xóa Voucher",
                    "Bạn có chắc muốn xóa Voucher này?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION,
                    new PopupCallbackAction() {
                @Override
                public void action(PopupController pc, int option) {
                    if (option == MessageAlerts.YES_OPTION) {
                        if (sreService.delete(id) > 0) {
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Xóa Voucher thành công");
                            FillTable();
                            cleanForm();
                        } else {
                            Notifications.getInstance().show(Notifications.Type.ERROR, "Xóa voucher thất bại");
                        }
                    }
                }
            });
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn Voucher cần xóa!");
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (ValidateVoucher()) {
            String newID = sreService.getNewVoucherID();
            VoucherModer voucherModer = this.getVoucherFromInput();
            voucherModer.setID(newID);

            MessageAlerts.getInstance().showMessage("Xác nhận thêm voucher",
                    "Bạn có chắc muốn thêm Voucher này?",
                    MessageAlerts.MessageType.SUCCESS,
                    MessageAlerts.YES_NO_OPTION,
                    new PopupCallbackAction() {
                @Override
                public void action(PopupController pc, int option) {
                    if (option == MessageAlerts.YES_OPTION) {
                        if (sreService.insert(voucherModer) > 0) {
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Thêm VouCher thành công");

                            FillTable(); // Cập nhật lại bảng dữ liệu
                            cleanForm();
                        } else {
                            Notifications.getInstance().show(Notifications.Type.ERROR, "Thêm Voucher thất bại");
                        }
                    }
                }
            });
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

        if (ValidateVoucher()) {
            VoucherModer voucherModer = getVoucherFromInput();
            String id = txtID.getText();

            MessageAlerts.getInstance().showMessage("Xác nhận cập nhật nhân viên",
                    "Bạn có chắc muốn cập nhật thông tin nhân viên này?",
                    MessageAlerts.MessageType.WARNING,
                    MessageAlerts.YES_NO_OPTION,
                    new PopupCallbackAction() {
                @Override
                public void action(PopupController pc, int option) {
                    if (option == MessageAlerts.YES_OPTION) {
                        if (sreService.update(voucherModer, id) > 0) {
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cập nhật nhân viên thành công!");
                            FillTable();
                            cleanForm();
                        } else {
                            Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật nhân viên thất bại!");
                        }
                    }
                }
            });

        }
    }//GEN-LAST:event_btnSuaActionPerformed

    void fillTablef(List<VoucherModer> voucherModers) {
        dtmm = (DefaultTableModel) tblvoucher.getModel();
        dtmm.setRowCount(0);

        int startIndex = (currentPage - 1) * RECORDS_PER_PAGE;
        int endIndex = Math.min(startIndex + RECORDS_PER_PAGE, voucherModers.size());

        int index = startIndex + 1;

        for (int i = startIndex; i < endIndex; i++) {
            VoucherModer vc = voucherModers.get(i);
            vc.setSTT(index++);
            dtmm.addRow(vc.toData());
        }
    }
    
    private void comboxTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboxTrangThaiActionPerformed
//        String selectedTrangThai = (String) comboxTrangThai.getSelectedItem();
//        List<VoucherModer> voucherModer;
//
//        if (selectedTrangThai.equals("Đang Hoạt Động")) {
//            voucherModer = sreService.getAllVouCherHoatdong();
//        } else if (selectedTrangThai.equals("Không Hoạt Động")) {
//            voucherModer = sreService.getAllVouCherKetthuc(); 
//           
//        } else {
//            voucherModer = sreService.getAllVoucher();
//        }
//
//        fillTablef(voucherModer);
    }//GEN-LAST:event_comboxTrangThaiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser JDBD;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> comboxTrangThai;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser jdKT;
    private javax.swing.JTextArea taMota;
    private javax.swing.JTable tblvoucher;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtMucGG;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
