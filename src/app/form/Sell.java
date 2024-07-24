/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.form;

import app.model.BillDetailModel;
import app.model.BillModel;
import app.model.BrandModel;
import app.model.ColorModel;
import app.model.ProductDetailModel;
import app.model.SizeModel;
import app.model.VoucherModer;
import app.service.BrandService;
import app.service.ColorService;
import app.service.SellService;
import app.service.SizeService;
import app.tabbed.TabbedForm;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import jdk.internal.org.jline.terminal.Size;

/**
 *
 * @author ADMIN
 */
public class Sell extends TabbedForm {

    private DefaultTableModel model = new DefaultTableModel();
    private ColorService msrs = new ColorService();
    private SizeService kcrs = new SizeService();
    private BrandService thrs = new BrandService();
    private SellService bhrs = new SellService();
    
    private String selectedHoaDonID;

    /**
     * Creates new form Sell
     */
    public Sell() {
        initComponents();
        txtTenKH.setText("Khách bán lẻ");
        fillTable(bhrs.getAllCTSP());
        fillTable2(bhrs.getHoaDonChoThanhToan());
        
        initCBOHTTT();
    }
    
    private void initCBOHTTT() {
        // Định nghĩa mảng chứa các lựa chọn cho combobox
        String[] options = {"Kết hợp", "Tiền mặt", "Chuyển khoản"};

        // Khởi tạo combobox với các giá trị từ mảng options
        cboHTTT.setModel(new DefaultComboBoxModel<>(options));

    }
    
//    void Cbo_Voucher() {
//        List<VoucherModer> listVC = vcrs.getAllVoucherActive();
//        String[] cbo = new String[listVC.size()];
//
//        for (int i = 0; i < listVC.size(); i++) {
//            VoucherModer voucher = listVC.get(i);
//            cbo[i] = voucher.getTenVoucher(); // Sử dụng tên voucher để hiển thị trên combobox
//        }
//
//        cboVoucher.setModel(new DefaultComboBoxModel<>(cbo));
//    }

    private void fillTable2(List<BillModel> list) {
        model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0); // Xóa tất cả các dòng cũ trước khi điền dữ liệu mới
        if (list != null && !list.isEmpty()) { // Kiểm tra list không rỗng
            for (int i = 0; i < list.size(); i++) {
                BillModel hoaDonModel = list.get(i);
                Object[] rowData = {
                    i + 1, // Số thứ tự (STT)
                    hoaDonModel.getID(),
                    hoaDonModel.getNgayTao(),
                    hoaDonModel.getTenNV().getHoTen(),
                    hoaDonModel.getTenKH().getTen(),
                    hoaDonModel.getTenVoucher().getTenVoucher(),
                    hoaDonModel.getTongTien(),
                    hoaDonModel.getHinhThucThanhToan(),
                    hoaDonModel.getTrangThai()
                };
                model.addRow(rowData); // Thêm dòng mới vào bảng
            }
        }
        return;
    }
    
    private void fillToTable(List<BillDetailModel> chiTietHoaDons) {
        model = (DefaultTableModel) tblGioHang.getModel();
        model.setRowCount(0);

        for (BillDetailModel chiTietHoaDon : chiTietHoaDons) {
            Object[] rowData = {
                chiTietHoaDon.getMactsp().getID(),
                chiTietHoaDon.getTenSP().getTenSP(),
                chiTietHoaDon.getDonGia().getGiaBan(),
                chiTietHoaDon.getSoLuong(),
                chiTietHoaDon.getThanhTien()

            };
            model.addRow(rowData);
        }
    }
    
    // Hàm để điền dữ liệu vào bảng chi tiết hóa đơn dựa trên ID hóa đơn được chọn
    private void fillChiTietHoaDonTable(String idHoaDon) {
        List<BillDetailModel> chiTietHoaDons = bhrs.searchByHoaDonID(idHoaDon);
        fillToTable(chiTietHoaDons);
        System.out.println("Đã chạy qua hàm fill");
    }

    private void fillTable(List<ProductDetailModel> listCTSP) {
        model = (DefaultTableModel) tblCTSP.getModel();
        model.setRowCount(0);
        Cbo_MauSac();
        Cbo_KichCo();
        Cbo_ThuongHieu();
        for (ProductDetailModel ctsp : listCTSP) {
            model.addRow(ctsp.toData2());
        }
    }

    private void Cbo_MauSac() {
        List<ColorModel> listMS = msrs.getALLMauSac();
        String[] cbo = new String[listMS.size()];
        for (int i = 0; i < listMS.size(); i++) {
            cbo[i] = listMS.get(i).getTenMS();
        }
        cboMauSac.setModel(new DefaultComboBoxModel<>(cbo));

    }

    private void Cbo_KichCo() {
        List<SizeModel> listKC = kcrs.getALLKichCo();
        String[] cbo = new String[listKC.size()];
        for (int i = 0; i < listKC.size(); i++) {
            cbo[i] = listKC.get(i).getTenSize();
        }
        cboSize.setModel(new DefaultComboBoxModel<>(cbo));
    }

    private void Cbo_ThuongHieu() {
        List<BrandModel> listTH = thrs.getALLThuongHieu();
        String[] cbo = new String[listTH.size()];
        for (int i = 0; i < listTH.size(); i++) {
            cbo[i] = listTH.get(i).getTenTH();
        }
        cboHang.setModel(new DefaultComboBoxModel<>(cbo));
    }
    
    private void showData(int index) {
        String maHD = String.valueOf(tblHoaDon.getValueAt(index, 1)).trim(); // Lấy mã hóa đơn từ cột 0
        String tenNV = String.valueOf(tblHoaDon.getValueAt(index, 3)).trim(); // Lấy tên nhân viên từ cột 2
        String tenKH = String.valueOf(tblHoaDon.getValueAt(index, 4)).trim(); // Lấy tên khách hàng từ cột 3
        String tenVC = String.valueOf(tblHoaDon.getValueAt(index, 5)).trim(); // Lấy tên voucher từ cột 5
        BigDecimal tongTien = (BigDecimal) tblHoaDon.getValueAt(index, 6); // Lấy tổng tiền từ cột 6
        String HTTT = String.valueOf(tblHoaDon.getValueAt(index, 7)).trim(); // Lấy hình thức thanh toán từ cột 7

        // Hiển thị thông tin lên các thành phần giao diện
        txtMaHD.setText(maHD);
        txtTenKH.setText(tenKH);
        txtTenNV.setText(tenNV);
        txtThanhToan.setText(tongTien.toString()); // Định dạng BigDecimal thành chuỗi để hiển thị

//        // Chọn voucher tương ứng trong combobox
//        for (int i = 0; i < cboVoucher.getItemCount(); i++) {
//            if (cboVoucher.getItemAt(i).toString().equals(tenVC)) {
//                cboVoucher.setSelectedIndex(i);
//                break;
//            }
//        }

        // Chọn hình thức thanh toán tương ứng trong combobox
        for (int i = 0; i < cboHTTT.getItemCount(); i++) {
            if (cboHTTT.getItemAt(i).toString().equals(HTTT)) {
                cboHTTT.setSelectedIndex(i);
                break;
            }
        }

        txtTongTien.setText(tongTien.toString()); // Định dạng BigDecimal thành chuỗi để hiển thị
    }
    
    
    private void cleanForm() {
        txtMaHD.setText(null);
        // txtMaKH.setText(null);
        txtTenKH.setText("Khách bán lẻ");
        txtTongTien.setText(null);
        txtThanhToan.setText(null);
        txtTienMat.setText(null);
        txtTienCK.setText(null);
        txtTienThua.setText(null);
        txtTimSDT.setText(null);
        fillTable(bhrs.getAllCTSP());
        btnOpenKH.setEnabled(true);
        selectedHoaDonID = null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        cboTrangThai = new javax.swing.JComboBox<>();
        btnHuyDon = new javax.swing.JButton();
        btnTaoHD = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        selectAll = new javax.swing.JCheckBox();
        btnDeleteGH = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCTSP = new javax.swing.JTable();
        cboMauSac = new javax.swing.JComboBox<>();
        cboSize = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cboHang = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTimSDT = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        btnOpenKH = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        txtMaHD = new javax.swing.JTextField();
        txtTienThua = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cboHTTT = new javax.swing.JComboBox<>();
        cboVoucher = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtThanhToan = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtTienMat = new javax.swing.JTextField();
        txtTienCK = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnSuccesHoaDon = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Hóa Đơn", "Ngày Tạo", "Nhân viên", "Khách hàng", "Voucher", "Tổng tiền", "Hình thức thanh toán", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, false
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

        cboTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chờ thanh toán", "Tất cả", "Đã thanh toán", "Đã hủy" }));
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        btnHuyDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHuyDon.setText("Hủy đơn");
        btnHuyDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyDonActionPerformed(evt);
            }
        });

        btnTaoHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTaoHD.setText("Tạo hóa đơn");
        btnTaoHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHDActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 204, 0));
        btnClear.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/png/clean.png"))); // NOI18N
        btnClear.setText("Làm mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnTaoHD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHuyDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96)
                        .addComponent(btnClear)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyDon)
                    .addComponent(btnTaoHD)
                    .addComponent(btnClear))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm chi tiết", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGioHang);

        selectAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        selectAll.setText("Tất cả");
        selectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllActionPerformed(evt);
            }
        });

        btnDeleteGH.setBackground(new java.awt.Color(255, 51, 0));
        btnDeleteGH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDeleteGH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/png/trash.png"))); // NOI18N
        btnDeleteGH.setText("Xóa");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(selectAll)
                .addGap(63, 63, 63)
                .addComponent(btnDeleteGH)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1371, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectAll)
                    .addComponent(btnDeleteGH))
                .addGap(0, 207, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblCTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã CTSP", "Tên sản phẩm", "Màu sắc", "Size", "Chất liệu", "Hãng", "Giá bán", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCTSPMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblCTSP);

        cboMauSac.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauSacActionPerformed(evt);
            }
        });

        cboSize.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSizeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Size");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Hãng");

        cboHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHangActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Tìm kiếm");

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Màu sắc");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(563, 563, 563)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(cboHang, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1371, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cboMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(cboHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(42, 42, 42)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin đơn hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Tìm khách hàng");

        txtTimSDT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimSDT.setToolTipText("Nhập số điện thoại khách hàng");

        btnTimKiem.setBackground(new java.awt.Color(0, 204, 204));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/png/computer.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Tên khách hàng");

        txtTenKH.setEditable(false);
        txtTenKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenKH.setToolTipText("Nhập số điện thoại khách hàng");

        btnOpenKH.setBackground(new java.awt.Color(102, 102, 255));
        btnOpenKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnOpenKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/png/customer.png"))); // NOI18N
        btnOpenKH.setText("Chọn");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTimKiem))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOpenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTimSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem))
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpenKH))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Tên nhân viên");

        txtTenNV.setEditable(false);
        txtTenNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenNV.setEnabled(false);
        txtTenNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNVjTextField4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTenNV)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        txtMaHD.setEditable(false);
        txtMaHD.setBackground(new java.awt.Color(255, 255, 255));
        txtMaHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaHDActionPerformed(evt);
            }
        });

        txtTienThua.setEditable(false);
        txtTienThua.setBackground(new java.awt.Color(255, 255, 255));
        txtTienThua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Tổng tiền(VNĐ)");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Tiền thừa(VNĐ)");

        cboHTTT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboHTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboHTTT.setToolTipText("");
        cboHTTT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboHTTTItemStateChanged(evt);
            }
        });

        cboVoucher.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboVoucher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboVoucher.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboVoucherItemStateChanged(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Voucher");

        txtTongTien.setEditable(false);
        txtTongTien.setBackground(new java.awt.Color(255, 255, 255));
        txtTongTien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Thanh toán(VNĐ)");

        txtThanhToan.setEditable(false);
        txtThanhToan.setBackground(new java.awt.Color(255, 255, 255));
        txtThanhToan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThanhToanActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Hình thức thanh toán");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Tiền mặt(VNĐ)");

        txtTienMat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTienCK.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Mã hóa đơn");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("Tiền chuyển khoản(VNĐ)");

        btnSuccesHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSuccesHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/png/succes.png"))); // NOI18N
        btnSuccesHoaDon.setText("Xác nhận thanh toán");
        btnSuccesHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuccesHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(41, 41, 41)
                        .addComponent(txtMaHD))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(28, 28, 28)
                        .addComponent(txtTienThua))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(txtTienCK))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTienMat, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSuccesHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboVoucher, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTongTien)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtThanhToan))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(cboHTTT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(30, 30, 30)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cboVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cboHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtTienMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTienCK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(btnSuccesHoaDon)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
       // Lấy chỉ số hàng được chọn
        int selectedRow = tblHoaDon.getSelectedRow();

        // Kiểm tra hàng được chọn có hợp lệ không
        if (selectedRow >= 0) {
            this.showData(selectedRow);
            btnOpenKH.setEnabled(false);
            // Lấy giá trị voucher và hình thức thanh toán của hàng được chọn
            selectedHoaDonID = tblHoaDon.getValueAt(selectedRow, 1).toString();
            String selectedVoucher = tblHoaDon.getValueAt(selectedRow, 5).toString();
            String selectedHTTT = tblHoaDon.getValueAt(selectedRow, 7).toString();
            // Kiểm tra trạng thái của hoá đơn
            String trangThai = tblHoaDon.getValueAt(selectedRow, 8).toString().trim();
            if (trangThai.equals("Đã thanh toán") || trangThai.equals("Đã hủy")) {
                // Nếu trạng thái là "Đã thanh toán" hoặc "Đã hủy", tắt đi các nút
                btnHuyDon.setEnabled(false);
                btnSuccesHoaDon.setEnabled(false);
                btnDeleteGH.setEnabled(false);
                
            } else {
                // Nếu trạng thái không phải là "Đã thanh toán" hoặc "Đã hủy", bật lại các nút
                btnHuyDon.setEnabled(true);
                btnSuccesHoaDon.setEnabled(true);
                btnDeleteGH.setEnabled(true);
            }
            System.out.println("BẠN ĐÃ NHẤN:  " + selectedHoaDonID);
            // Cập nhật lại bảng tblGioHang với dữ liệu của hóa đơn đã chọn
            fillChiTietHoaDonTable(selectedHoaDonID);
            // Chọn voucher tương ứng trong combobox
            for (int i = 0; i < cboVoucher.getItemCount(); i++) {
                if (cboVoucher.getItemAt(i).toString().equals(selectedVoucher)) {
                    cboVoucher.setSelectedIndex(i);
                    break;
                }
            }

            // Chọn hình thức thanh toán tương ứng trong combobox
            for (int i = 0; i < cboHTTT.getItemCount(); i++) {
                if (cboHTTT.getItemAt(i).toString().equals(selectedHTTT)) {
                    cboHTTT.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            // Nếu không có hoá đơn nào được chọn, làm sạch form và tắt các nút
            cleanForm();
            btnHuyDon.setEnabled(true);
            btnSuccesHoaDon.setEnabled(true);
            btnDeleteGH.setEnabled(true);
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        String selectedTrangThai = (String) cboTrangThai.getSelectedItem();
        List<BillModel> listHD;

        if (selectedTrangThai.equals("Tất cả")) {
            listHD = bhrs.getAllHD1();
        } else if (selectedTrangThai.equals("Đã thanh toán")) {
            listHD = bhrs.getDaThanhToanHoaDon();
        } else if (selectedTrangThai.equals("Đã hủy")) {
            listHD = bhrs.getDaHuyHoaDon();
        } else if (selectedTrangThai.equals("Chờ thanh toán")) {
            listHD = bhrs.getHoaDonChoThanhToan();
        } else {
            listHD = bhrs.getAllHD1();
        }

        fillTable2(listHD);
    }//GEN-LAST:event_cboTrangThaiActionPerformed

    private void btnHuyDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHuyDonActionPerformed

    private void btnTaoHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTaoHDActionPerformed

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblGioHangMouseClicked

    private void selectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectAllActionPerformed

    private void tblCTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCTSPMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblCTSPMouseClicked

    private void cboMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauSacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMauSacActionPerformed

    private void cboSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSizeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSizeActionPerformed

    private void cboHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHangActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtTenNVjTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNVjTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNVjTextField4ActionPerformed

    private void txtMaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHDActionPerformed

    private void cboHTTTItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboHTTTItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHTTTItemStateChanged

    private void cboVoucherItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboVoucherItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboVoucherItemStateChanged

    private void txtThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThanhToanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThanhToanActionPerformed

    private void btnSuccesHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuccesHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuccesHoaDonActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        cleanForm();
    }//GEN-LAST:event_btnClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDeleteGH;
    private javax.swing.JButton btnHuyDon;
    private javax.swing.JButton btnOpenKH;
    private javax.swing.JButton btnSuccesHoaDon;
    private javax.swing.JButton btnTaoHD;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cboHTTT;
    private javax.swing.JComboBox<String> cboHang;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JComboBox<String> cboSize;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JComboBox<String> cboVoucher;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JCheckBox selectAll;
    private javax.swing.JTable tblCTSP;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtThanhToan;
    private javax.swing.JTextField txtTienCK;
    private javax.swing.JTextField txtTienMat;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTimSDT;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
