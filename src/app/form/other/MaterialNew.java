/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package app.form.other;

import app.model.MaterialModel;
import app.service.MaterialService;
import raven.toast.Notifications;

/**
 *
 * @author maid
 */
public class MaterialNew extends javax.swing.JDialog {

    private MaterialService clrs = new MaterialService();

    /**
     * Creates new form MaterialNew
     */
    public MaterialNew() {
        initComponents();
        this.setLocationRelativeTo(null);
        setTitle("Form Material");
    }

    private static boolean materialAdded = false;

    public static boolean showDialog() {
        MaterialNew dialog = new MaterialNew();
        dialog.setModal(true); // Đảm bảo dialog là modal
        dialog.setVisible(true);
        boolean result = materialAdded;
        materialAdded = false; // Reset lại sau khi đã sử dụng
        return result;
    }

    private boolean validateFields() {
        String tenChatLieu = txtTenTT.getText().trim();
        String moTa = txtMoTa.getText().trim();

        if (tenChatLieu.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng nhập tên chất liệu!");
            txtTenTT.requestFocus();
            return false;
        }
        if (moTa.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng nhập mô tả chất liệu!");
            txtMoTa.requestFocus();
            return false;
        }

        if (tenChatLieu.length() > 100) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Tên chất liệu tối đa là 100 ký tự!");
            txtTenTT.requestFocus();
            return false;
        }

        if (moTa.length() > 254) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Mô tả tối đa là 254 ký tự!");
            txtMoTa.requestFocus();
            return false;
        }
        if (clrs.checkTrungTen(txtTenTT.getText().trim())) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Tên thuộc tính đã tồn tại!");
            txtTenTT.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        btnChatLieu = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtTenTT = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

        btnChatLieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnChatLieu.setText("Thêm");
        btnChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChatLieuActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tên chất liệu:");

        txtTenTT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Mô tả");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnChatLieu)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(54, 54, 54)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenTT, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTenTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addComponent(btnChatLieu)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChatLieuActionPerformed
        String tenChatLieu = txtTenTT.getText();
        String moTa = txtMoTa.getText();
        if (clrs.checkTrungTen(txtTenTT.getText().trim())) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Tên chất liệu đã tồn tại!");
            txtTenTT.requestFocus();
            return;
        }
        if (!validateFields()) {
            return;
        }
        MaterialService service = new MaterialService();
        String newID = service.getNewIDCL();
        MaterialModel chatLieu = new MaterialModel(newID, tenChatLieu, moTa);

        if (clrs.insert(chatLieu) > 0) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Thêm thành công chất liệu mới!");
            materialAdded = true;
            this.dispose();
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Thêm thất bại!");
        }
    }//GEN-LAST:event_btnChatLieuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MaterialNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MaterialNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MaterialNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MaterialNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MaterialNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChatLieu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenTT;
    // End of variables declaration//GEN-END:variables
}
