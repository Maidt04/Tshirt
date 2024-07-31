/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.form;

import app.form.other.CustomerNew;
import app.model.BillDetailModel;
import app.model.BillModel;
import app.model.BrandModel;
import app.model.ColorModel;
import app.model.CustomerModel;
import app.model.ProductDetailModel;
import app.model.SizeModel;
import app.model.StaffModel;
import app.model.VoucherModer;
import app.service.BrandService;
import app.service.ColorService;
import app.service.ProductDetailService;
import app.service.ProductsService;
import app.service.SellService;
import app.service.SizeService;
import app.service.VoucherService;
import app.tabbed.TabbedForm;
import app.tabbed.WindowsTabbed;
import app.utils.Auth;
import java.awt.HeadlessException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import raven.alerts.MessageAlerts;
import raven.popup.component.PopupCallbackAction;
import raven.popup.component.PopupController;
import raven.toast.Notifications;

/**
 *
 * @author ADMIN
 */
public final class Sell extends TabbedForm {

    private DefaultTableModel model = new DefaultTableModel();

    private final ColorService msrs = new ColorService();
    private final SizeService kcrs = new SizeService();
    private final BrandService thrs = new BrandService();
    private final SellService bhrs = new SellService();
    private final ProductsService sprs = new ProductsService();
    private final VoucherService vcrs = new VoucherService();
    private final ProductDetailService ctsprp = new ProductDetailService();
    //Tạo ra các biến cục bộ dùng để lưu trữ giá trị tạm thời
    private String selectedHoaDonID;
    private String selectedThuongHieuID = null;
    private String selectedFilterThuongHieuItem = null;
    private String selectedSizeID = null;
    private String selectedFilterSizeItem = null;
    private String selectedMauSacID = null;
    private String selectedFilterMSItem = null;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final String selectedVoucher = null;
    private final BigDecimal discountAmount = BigDecimal.ZERO;
    private String voucherID;
    private CustomerNew chonKhachHangDialog;
    public void setSelectedHoaDonID(String id){
        if (id!=null && id.isEmpty()) {
            this.selectedHoaDonID=id;
        }
    }
    public String getSelectedHoaDonID() {
        return this.selectedHoaDonID;
    }
    @Override
    public void fromRefresh() {
        // Tải lại dữ liệu cho form 
        txtTenKH.setText("Khách bán lẻ");
        fillTable(bhrs.getAllCTSP());
        fillTable2(bhrs.getHoaDonChoThanhToan());
        txtTenNV.setText(Auth.user.getHoTen());
        initCBOHTTT();
        Cbo_Voucher();
        cleanForm();
    }
      
    public Sell() {
        initComponents();
        txtTenKH.setText("Khách bán lẻ");

        fillTable(bhrs.getAllCTSP());
        fillTable2(bhrs.getHoaDonChoThanhToan());
        txtTenNV.setText(Auth.user.getHoTen());
        initCBOHTTT();
        Cbo_Voucher();
    }

    private void initCBOHTTT() {
        // Định nghĩa mảng chứa các lựa chọn cho combobox
        String[] options = {"Kết hợp", "Tiền mặt", "Chuyển khoản"};

        // Khởi tạo combobox với các giá trị từ mảng options
        cboHTTT.setModel(new DefaultComboBoxModel<>(options));

    }

    void Cbo_Voucher() {
        List<VoucherModer> listVC = vcrs.getAllVoucherActive();
        String[] cbo = new String[listVC.size()];

        for (int i = 0; i < listVC.size(); i++) {
            VoucherModer voucher = listVC.get(i);
            cbo[i] = voucher.getTenVoucher(); // Sử dụng tên voucher để hiển thị trên combobox
        }

        cboVoucher.setModel(new DefaultComboBoxModel<>(cbo));
    }

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
        if (idHoaDon == null || idHoaDon.trim().isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.ERROR,"ID hóa đơn không hợp lệ: "+ idHoaDon );
            return;
        }
        List<BillDetailModel> chiTietHoaDons = bhrs.searchByHoaDonID(idHoaDon);
        fillToTable(chiTietHoaDons);
        System.out.println("Đã chạy qua hàm fill");
    }
    private void clearChiTietHoaDonTable(){
        DefaultTableModel model= (DefaultTableModel) tblGioHang.getModel();
        model.setRowCount(0);
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

        // Chọn voucher tương ứng trong combobox
        for (int i = 0; i < cboVoucher.getItemCount(); i++) {
            if (cboVoucher.getItemAt(i).equals(tenVC)) {
                cboVoucher.setSelectedIndex(i);
                break;
            }
        }
        // Chọn hình thức thanh toán tương ứng trong combobox
        for (int i = 0; i < cboHTTT.getItemCount(); i++) {
            if (cboHTTT.getItemAt(i).equals(HTTT)) {
                cboHTTT.setSelectedIndex(i);
                break;
            }
        }

        txtTongTien.setText(tongTien.toString()); // Định dạng BigDecimal thành chuỗi để hiển thị
    }

    public BillModel read() {
        BillModel hdm = new BillModel();
        hdm.setID(txtMaHD.getText().trim());
        hdm.setTenNV(new StaffModel(txtTenNV.getText().trim()));
        hdm.setTenKH(new CustomerModel(txtTenKH.getText().trim()));

        // Lấy tổng tiền từ txtTongTien
        BigDecimal tongTien = BigDecimal.ZERO;
        try {
            tongTien = new BigDecimal(txtTongTien.getText().trim());
        } catch (NumberFormatException ex) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập tổng tiền hợp lệ.");
            return null; // Trả về null nếu nhập tổng tiền không hợp lệ
        }

        // Chuyển đổi tổng tiền sang kiểu int
        int tongTienInt = tongTien.intValue();

        // Kiểm tra nếu tổng tiền bằng 0 thì không thể áp dụng voucher
        if (tongTienInt == 0) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Không thể áp dụng voucher với hóa đơn trống.");
            return null; // Trả về null nếu tổng tiền bằng 0
        }

        // Lấy ID của voucher dựa vào tên đã chọn từ cboVoucher
        String selectedVoucher = cboVoucher.getSelectedItem().toString();
        String voucherId = bhrs.getIdByTenVoucher(selectedVoucher);

        if (voucherId != null) {
            hdm.setTenVoucher(new VoucherModer(voucherId)); // Lưu ID voucher vào HoaDonModel
            System.out.println("Voucher ID: " + voucherId); // Kiểm tra xem voucherId có giá trị hay là null
            // Xác định loại voucher (Giảm theo phần trăm hoặc Giảm theo giá tiền)
            String loaiVoucher = bhrs.getLoaiVoucherByTenVoucher(selectedVoucher);

            // Lấy mức giảm giá từ voucher để tính tổng tiền sau khi giảm giá
            BigDecimal discountAmount;
            discountAmount = bhrs.getMucGiamGiaByTenVoucher(selectedVoucher);
            if (discountAmount != null) {
                // Áp dụng logic tính tổng tiền sau khi giảm giá theo loại voucher
                if ("Giảm theo phần trăm".equals(loaiVoucher)) {
                    BigDecimal percentDiscount = discountAmount.divide(BigDecimal.valueOf(100));
                    BigDecimal amountToDeduct = tongTien.subtract(tongTien.multiply(percentDiscount));
                    int tongTienSauGiamGiaInt = amountToDeduct.intValue();
                    hdm.setTongTien(BigDecimal.valueOf(tongTienSauGiamGiaInt));
                    // Format tổng tiền sau khi giảm giá thành chuỗi string và set vào txtThanhToan
                    txtThanhToan.setText(String.valueOf(tongTienSauGiamGiaInt));
                } else if ("Giảm theo giá tiền".equals(loaiVoucher)) {
                    BigDecimal tongTienSauGiamGia = tongTien.subtract(discountAmount);
                    int tongTienSauGiamGiaInt = tongTienSauGiamGia.intValue();
                    hdm.setTongTien(BigDecimal.valueOf(tongTienSauGiamGiaInt));
                    // Format tổng tiền sau khi giảm giá thành chuỗi string và set vào txtThanhToan
                    txtThanhToan.setText(String.valueOf(tongTienSauGiamGiaInt));
                }
            } else {
                hdm.setTongTien(tongTien); // Không có giảm giá, giữ nguyên tổng tiền
                // Format tổng tiền thành chuỗi string và set vào txtThanhToan
                txtThanhToan.setText(String.valueOf(tongTienInt));
            }
        } else {
            hdm.setTenVoucher(null);
            hdm.setTongTien(tongTien); // Không có voucher, giữ nguyên tổng tiền
            // Format tổng tiền thành chuỗi string và set vào txtThanhToan
            txtThanhToan.setText(String.valueOf(tongTienInt));
        }
        return hdm;
    }

    private void cleanForm() {
        txtMaHD.setText(null);
        txtTenNV.setText(Auth.user.getHoTen());
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
        cboVoucher.setSelectedIndex(0);
    }

    private void refreshGioHangTable() {
        String currentHoaDonID = getSelectedHoaDonID();
        if (currentHoaDonID != null && !currentHoaDonID.trim().isEmpty()) {
            int rowIndex= -1;
            for (int i = 0; i < tblHoaDon.getRowCount(); i++) {
                if (tblHoaDon.getValueAt(i, 1).toString().equals(currentHoaDonID)) {
                    rowIndex = i;
                    break;
                }
            }
        
        if (rowIndex >= 0) {
            List<BillDetailModel> chiTietHoaDons = bhrs.searchByHoaDonID(currentHoaDonID);
            fillToTable(chiTietHoaDons);
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING,"Hóa đơn đã chọn không còn trong bảng");
            setSelectedHoaDonID(null);
            cleanForm();
        }
        
      }
        else{
            Notifications.getInstance().show(Notifications.Type.WARNING,"Vui lòng chọn 1 hóa đơn");
            cleanForm();
        }
    }

    // Hàm cập nhật lại thông tin trên form sau khi thay đổi
    private void refreshFormData() {
        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow >= 0) {
            String hoaDonID= tblHoaDon.getValueAt(selectedRow, 1).toString();
            setSelectedHoaDonID(hoaDonID);
           
            // Cập nhật lại bảng chi tiết hoá đơn
            fillChiTietHoaDonTable(getSelectedHoaDonID());

            // Cập nhật lại bảng hoá đơn 
            fillTable2(bhrs.getHoaDonChoThanhToan());
            fillTable(bhrs.getAllCTSP());

            // Hiển thị thông tin chi tiết của hoá đơn lên form
            showData(selectedRow);
        } else {
            // Nếu không có hoá đơn nào được chọn, làm sạch form và tắt các nút
            txtMaHD.setText(null);
            txtTenNV.setText(Auth.user.getHoTen());
            txtTenKH.setText("Khách bán lẻ");
            txtTongTien.setText(null);
            txtThanhToan.setText(null);
            txtTienMat.setText(null);
            txtTienCK.setText(null);
            txtTienThua.setText(null);
            txtTimSDT.setText(null);
            fillTable(bhrs.getAllCTSP());
            btnOpenKH.setEnabled(true);
            cboVoucher.setSelectedIndex(0);
            btnHuyDon.setEnabled(true);
            btnSuccesHoaDon.setEnabled(true);
            btnDeleteGH.setEnabled(true);
        }
    }

    private void updateVoucherStatusByQuantity() {
        int updatedCount = vcrs.updateVoucherStatusByQuantity();
        if (updatedCount > 0) {
            System.out.println("Đã cập nhật trạng thái của " + updatedCount + " voucher thành Không hoạt động.");
            Cbo_Voucher();
        }
    }

    private void updateHoaDonTrangThai(String hoaDonID, BigDecimal tienThua) {
        if (hoaDonID == null || hoaDonID.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng cung cấp ID hóa đơn hợp lệ.");
            return;
        }

        try {
            int updated = bhrs.updateBillStatus(hoaDonID, "Đã thanh toán");
            if (updated > 0) {
                // Định dạng tiền thừa thành tiền VNĐ
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String formattedTienThua = currencyFormatter.format(tienThua);

                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Hóa đơn đã được chuyển sang trạng thái 'Đã thanh toán'");
 MessageAlerts.getInstance().showMessage("Thông báo thành công",
                        "Số tiền thừa của quý khách là: " + formattedTienThua,
                        MessageAlerts.MessageType.SUCCESS,
                        MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                            if (i == 0) { // Sử dụng giá trị thực tế cho nút Close
                                cleanForm(); // Gọi hàm làm sạch biểu mẫu
                                fillTable(bhrs.getAllCTSP());
                                fillTable2(bhrs.getHoaDonChoThanhToan());
                                clearChiTietHoaDonTable();
                                System.out.println("Click Close");
                            } else {
                                System.out.println("Other button clicked: " + i);
                            }
                        });
                // Lấy thông tin hóa đơn
                BillModel hoaDon = bhrs.getHoaDonByID(hoaDonID);
                String tenVoucher = hoaDon.getTenVoucher().getTenVoucher().trim();
                // Cập nhật số lượng voucher
                bhrs.updateSoLuongVoucher(tenVoucher);
                updateVoucherStatusByQuantity();
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Không có hóa đơn nào được cập nhật.");
            }
        } catch (HeadlessException ex) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Đã xảy ra lỗi: " + ex.getMessage());
        }
    }

    private boolean validateTienMat() {
        String tienMatStr = txtTienMat.getText().trim();

        // Kiểm tra trường rỗng
        if (tienMatStr.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập số tiền mặt.");
            txtTienMat.requestFocus();
            return false;
        }

        // Kiểm tra độ dài không vượt quá 20 ký tự
        if (tienMatStr.length() > 20) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Số tiền mặt không được vượt quá 20 ký tự.");
            txtTienMat.requestFocus();
            return false;
        }

        // Kiểm tra xem chỉ chứa các ký tự số và không chứa ký tự "-"
        if (!tienMatStr.matches("^\\d+$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Số tiền mặt phải là số và không được chứa ký tự đặc biệt.");
            txtTienMat.requestFocus();
            return false;
        }

        // Chuyển đổi thành số BigDecimal để kiểm tra số không âm
        BigDecimal tienMat = new BigDecimal(tienMatStr);
        if (tienMat.compareTo(BigDecimal.ZERO) < 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Số tiền mặt không được là số âm.");
            txtTienMat.requestFocus();

            return false;
        }

        return true;
    }

    private boolean validateTienCK() {
        String tienCKStr = txtTienCK.getText().trim();

        // Kiểm tra trường rỗng
        if (tienCKStr.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập số tiền chuyển khoản.");
            txtTienCK.requestFocus();
            return false;
        }

        // Kiểm tra độ dài không vượt quá 20 ký tự
        if (tienCKStr.length() > 20) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Số tiền chuyển khoản không được vượt quá 20 ký tự.");
            txtTienCK.requestFocus();
            return false;
        }

        // Kiểm tra xem chỉ chứa các ký tự số và không chứa ký tự "-"
        if (!tienCKStr.matches("^\\d+$")) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Số tiền chuyển khoản phải là số và không được chứa ký tự đặc biệt.");
            txtTienCK.requestFocus();
            return false;
        }

        // Chuyển đổi thành số BigDecimal để kiểm tra số không âm
        BigDecimal tienCK = new BigDecimal(tienCKStr);
        if (tienCK.compareTo(BigDecimal.ZERO) < 0) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Số tiền chuyển khoản không được là số âm.");
            txtTienCK.requestFocus();
            return false;
        }

        return true;
    }

    private void xoaMemHD(String hoaDonID) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 1).equals(hoaDonID)) {
                model.removeRow(i);
                break; // Sau khi loại bỏ, thoát khỏi vòng lặp
            }
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
        btnDeleteGH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteGHActionPerformed(evt);
            }
        });

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
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(292, 292, 292)
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
        btnOpenKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenKHActionPerformed(evt);
            }
        });

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
                if (cboVoucher.getItemAt(i).equals(selectedVoucher)) {
                    cboVoucher.setSelectedIndex(i);
                    break;
                }
            }

            // Chọn hình thức thanh toán tương ứng trong combobox
            for (int i = 0; i < cboHTTT.getItemCount(); i++) {
                if (cboHTTT.getItemAt(i).equals(selectedHTTT)) {
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

        if (selectedTrangThai.equals("Chờ thanh toán")) {
                        listHD = bhrs.getHoaDonChoThanhToan();
        } else {
            listHD = bhrs.getHoaDonChoThanhToan();
   
        }

        fillTable2(listHD);
    }//GEN-LAST:event_cboTrangThaiActionPerformed

    private void btnHuyDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyDonActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnHuyDonActionPerformed

    private void btnTaoHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHDActionPerformed
         if (Auth.isLogin()) {
            StaffModel nhanVien = Auth.user;
            String idNhanVien = nhanVien.getId();

            // Lấy tên khách hàng từ ô txtTenKH
            String tenKH = txtTenKH.getText().trim();

            // Tìm ID khách hàng dựa trên tên khách hàng
            String idKhachHang = bhrs.getIDByTen(tenKH);

            // Nếu không tìm thấy ID từ tên khách hàng, sử dụng ID mặc định là "KH00"
            if (idKhachHang == null || idKhachHang.isEmpty()) {
                idKhachHang = "KH000";
            }

            // Gọi phương thức tạo hóa đơn với ID nhân viên và ID khách hàng
            int result = bhrs.taoHoaDon(idNhanVien, idKhachHang);

            // Xử lý kết quả
            if (result == 1) {
                fillTable2(bhrs.getHoaDonChoThanhToan());
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Tạo hóa đơn thành công");
            } else {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Không thể tạo hóa đơn");
            }
        } else {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng đăng nhập");
        }
    }//GEN-LAST:event_btnTaoHDActionPerformed

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblGioHangMouseClicked

    private void selectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model= (DefaultTableModel) tblGioHang.getModel();
        int rowCount= model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            model.setValueAt(selectAll.isSelected(), i, 5);
        
        }
    }//GEN-LAST:event_selectAllActionPerformed

    private void tblCTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCTSPMouseClicked
        int selectedRow = tblCTSP.getSelectedRow();
        if (selectedHoaDonID == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn một hoá đơn trước khi thêm sản phẩm vào giỏ hàng!");
            return;
        }

        if (selectedRow == -1) {
            return; // Không có hàng nào được chọn
        }

        String productID = tblCTSP.getValueAt(selectedRow, 0).toString();
        BigDecimal unitPrice = bhrs.getGiaBanByMaCTSP(productID);

        if (unitPrice == null) {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Không tìm thấy giá bán cho sản phẩm này!");
            return;
        }

        JTextField txtSoLuong = new JTextField();
        Object[] message = {"Nhập số lượng:", txtSoLuong};
        txtSoLuong.requestFocus();

        int option = JOptionPane.showConfirmDialog(this, message, "Nhập số lượng", JOptionPane.OK_CANCEL_OPTION);
        txtSoLuong.requestFocus();

        if (option == JOptionPane.OK_OPTION) {
            String soLuongStr = txtSoLuong.getText();

            // Validate số lượng
            if (soLuongStr.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập số lượng!");
                txtSoLuong.requestFocus();
                return;
            }

            if (soLuongStr.contains("-")) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Số lượng không được là số âm !");
                txtSoLuong.requestFocus();
                return;
            }

            try {
                int quantity = Integer.parseInt(soLuongStr);

                if (quantity <= 0) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, "Số lượng phải lớn hơn 0!");
                    txtSoLuong.requestFocus();
                    return;
                }
                if (soLuongStr.length() > 20) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, "Số lượng không được quá 20 ký tự!");
                    txtSoLuong.requestFocus();
                    return;
                }

                int currentQuantity = bhrs.laySoLuongTonByID(productID);

                if (quantity > currentQuantity) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, "Số lượng nhập vào vượt quá số lượng tồn của sản phẩm!");
                    txtSoLuong.requestFocus();
                    return;
                }

                // Tiếp tục xử lý logic khi số lượng hợp lệ
                BillDetailModel existingCTHD = bhrs.kiemTraTrungSanPhamChiTiet(productID, selectedHoaDonID);

                if (existingCTHD != null) {
                    int newQuantity = existingCTHD.getSoLuong() + quantity;
                    BigDecimal newTotal = unitPrice.multiply(BigDecimal.valueOf(newQuantity));
                    int updatedRows = bhrs.updateSoLuongVaThanhTienHoaDonChiTiet(existingCTHD.getID(), newQuantity,
                            newTotal);

                    if (updatedRows > 0) {
                        int remainingQuantity = currentQuantity - quantity;
                        bhrs.updateSoLuongTon(productID, remainingQuantity);
                        refreshGioHangTable();
                        boolean updated = bhrs.capNhatTongTienHoaDon(selectedHoaDonID);
                        fillTable2(bhrs.getHoaDonChoThanhToan());
                        fillTable(bhrs.getAllCTSP());
                        if (updated) {
                            fillChiTietHoaDonTable(selectedHoaDonID);
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cập nhật tổng tiền hóa đơn thành công!");
                            fillTable2(bhrs.getHoaDonChoThanhToan());

                        } else {
                            Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật tổng tiền hóa đơn thất bại!");
                        }
                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật số lượng và thành tiền thất bại!");
                    }
                } else {
                    BillDetailModel chiTietHoaDon = new BillDetailModel();
                    chiTietHoaDon.setMactsp(new ProductDetailModel(productID));
                    chiTietHoaDon.setSoLuong(quantity);
                    chiTietHoaDon.setThanhTien(unitPrice.multiply(BigDecimal.valueOf(quantity)));

                    int result = bhrs.themSPGioHang(chiTietHoaDon, selectedHoaDonID);
                    if (result > 0) {
                        int remainingQuantity = currentQuantity - quantity;
                        bhrs.updateSoLuongTon(productID, remainingQuantity);

                        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Thêm sản phẩm vào giỏ hàng thành công!");
                        boolean updated = bhrs.capNhatTongTienHoaDon(selectedHoaDonID);
                        refreshGioHangTable();
                        if (updated) {
                            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Cập nhật tổng tiền hóa đơn thành công!");
                        } else {
                            Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật tổng tiền hóa đơn thất bại!");
                        }
                    } else {
                        Notifications.getInstance().show(Notifications.Type.ERROR, "Thêm sản phẩm vào giỏ hàng thất bại!");
                    }
                }
            } catch (NumberFormatException ex) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Số lượng không hợp lệ!");
            }
        }
        refreshFormData();
    }//GEN-LAST:event_tblCTSPMouseClicked

    private void cboMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauSacActionPerformed
        selectedFilterMSItem = (String) cboMauSac.getSelectedItem();
        selectedMauSacID = selectedFilterMSItem;
        List<ColorModel> listMS = msrs.getIDByTenMS(selectedMauSacID);
        if (!listMS.isEmpty()) {
            selectedMauSacID = listMS.get(0).getID();
            List<ProductDetailModel> listCTSP = bhrs.getAllCTSPByColorID(selectedMauSacID);
            fillTable(listCTSP);
            cboMauSac.setSelectedItem(selectedFilterMSItem);
        } else {
            selectedMauSacID = null;
            fillTable(ctsprp.getAllCTSP());
            cboMauSac.setSelectedItem(selectedFilterMSItem);
        }
    }//GEN-LAST:event_cboMauSacActionPerformed

    private void cboSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSizeActionPerformed
        selectedFilterSizeItem = (String) cboSize.getSelectedItem();
        selectedSizeID = selectedFilterSizeItem;
        List<SizeModel> listKC = kcrs.getIDByTenKC(selectedSizeID);
        if (!listKC.isEmpty()) {
            selectedSizeID = listKC.get(0).getID();
            List<ProductDetailModel> listCTSP = bhrs.getAllCTSPBySizeID(selectedSizeID);
            fillTable(listCTSP);
            cboSize.setSelectedItem(selectedFilterSizeItem);
        } else {
            selectedMauSacID = null;
            fillTable(ctsprp.getAllCTSP());
            cboSize.setSelectedItem(selectedFilterSizeItem);
        }
    }//GEN-LAST:event_cboSizeActionPerformed

    private void cboHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHangActionPerformed
        selectedFilterThuongHieuItem = (String) cboHang.getSelectedItem();
        selectedThuongHieuID = selectedFilterThuongHieuItem;
        List<BrandModel> listTH = thrs.getIDByTenTH(selectedThuongHieuID);
        if (!listTH.isEmpty()) {
            selectedThuongHieuID = listTH.get(0).getID();
            List<ProductDetailModel> listCTSP = bhrs.getAllCTSPByBrandID(selectedThuongHieuID);
            fillTable(listCTSP);
            cboHang.setSelectedItem(selectedFilterThuongHieuItem);
        } else {
            selectedThuongHieuID = null;
            fillTable(ctsprp.getAllCTSP());
            cboHang.setSelectedItem(selectedFilterThuongHieuItem); // Thiết lập lại giá trị của combobox
        }
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
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String selectedOption = (String) cboHTTT.getSelectedItem();

            if (selectedOption.equals("Tiền mặt")) {
                txtTienCK.setText(null);
                txtTienCK.setEnabled(false); // Tắt ô txtTienCK
                txtTienMat.setEnabled(true);
            } else if (selectedOption.equals("Chuyển khoản")) {
                txtTienMat.setText(null);
                txtTienMat.setEnabled(false); // Tắt ô txtTienMat
                txtTienCK.setEnabled(true);
            } else if (selectedOption.equals("Kết hợp")) {
                txtTienMat.setEnabled(true);
                txtTienCK.setEnabled(true);
            }
        }
    }//GEN-LAST:event_cboHTTTItemStateChanged

    private void cboVoucherItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboVoucherItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String selectedVoucher = (String) cboVoucher.getSelectedItem();

            if (!txtTongTien.getText().isEmpty()) {
                BigDecimal tongTien = new BigDecimal(txtTongTien.getText());

                // Lấy ID của voucher từ tên voucher đã chọn
                String voucherId = bhrs.getIdByTenVoucher(selectedVoucher);

                // Lấy loại voucher từ tên voucher đã chọn
                String loaiVoucher = bhrs.getLoaiVoucherByTenVoucher(selectedVoucher);

                // Lấy mức giảm giá từ voucher để tính lại tổng tiền sau khi giảm giá
                BigDecimal discountAmount;
                discountAmount = bhrs.getMucGiamGiaByTenVoucher(selectedVoucher);
                if (discountAmount != null) {
                    if ("Giảm theo phần trăm".equals(loaiVoucher)) {
                        BigDecimal phanTramGiam = discountAmount.divide(BigDecimal.valueOf(100));
                        BigDecimal tienGiam = tongTien.multiply(phanTramGiam);
                        BigDecimal thanhToan = tongTien.subtract(tienGiam);
                        txtThanhToan.setText(String.valueOf(thanhToan.intValue()));
                    } else if ("Giảm theo giá tiền".equals(loaiVoucher)) {
                        BigDecimal thanhToan = tongTien.subtract(discountAmount);
                        txtThanhToan.setText(String.valueOf(thanhToan.intValue()));
                    } else {
                        JOptionPane.showMessageDialog(this, "Loại voucher không hợp lệ.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Mức giảm giá của voucher không hợp lệ.");
                }
            }
        }
    }//GEN-LAST:event_cboVoucherItemStateChanged

    private void txtThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThanhToanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThanhToanActionPerformed

    private void btnSuccesHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuccesHoaDonActionPerformed
        // Đọc dữ liệu từ giao diện và tạo đối tượng HoaDonModel
        BillModel hdm = read();
        if (hdm == null) {
            return; // Trả về nếu dữ liệu hóa đơn không hợp lệ
        }

        // Lấy tên voucher từ combo box
        String selectedVoucher = cboVoucher.getSelectedItem().toString();

        // Lấy ID voucher từ tên voucher đã chọn
        String voucherId = bhrs.getIdByTenVoucher(selectedVoucher);

        // Kiểm tra nếu voucherId không null (tức là đã tìm thấy ID cho tên voucher đã chọn)
        if (voucherId != null) {
            // Thiết lập ID voucher cho hóa đơn
            VoucherModer voucher = new VoucherModer(voucherId);
            hdm.setTenVoucher(voucher);

            // Lấy tên voucher từ ID voucher
            String tenVoucher = bhrs.getTenByIDVoucher(voucherId);

            // Tiếp tục xử lý hình thức thanh toán và các bước khác như trước
            BigDecimal tienMat = BigDecimal.ZERO;
            BigDecimal chuyenKhoan = BigDecimal.ZERO;

            String hinhThucThanhToan = cboHTTT.getSelectedItem().toString();

            if (hinhThucThanhToan.equals("Tiền mặt")) {
                if (validateTienMat()) {
                    tienMat = new BigDecimal(txtTienMat.getText().trim());
                } else {
                    return;
                }
            } else if (hinhThucThanhToan.equals("Chuyển khoản")) {
                if (validateTienCK()) {
                    chuyenKhoan = new BigDecimal(txtTienCK.getText().trim());
                } else {
                    return;
                }
            } else if (hinhThucThanhToan.equals("Kết hợp")) {
                if (validateTienMat() && validateTienCK()) {
                    tienMat = new BigDecimal(txtTienMat.getText().trim());
                    chuyenKhoan = new BigDecimal(txtTienCK.getText().trim());
                } else {
                    return;
                }
            }

            // Tính tiền thừa
            BigDecimal tongTienSauGiamGia = hdm.getTongTien();
            BigDecimal tienThua = tienMat.add(chuyenKhoan).subtract(tongTienSauGiamGia);

            // Hiển thị tiền thừa lên giao diện
            txtTienThua.setText(tienThua.toString());

            // Cập nhật cột voucher trong bảng nếu có hóa đơn được chọn
            int selectedRow = tblHoaDon.getSelectedRow();
            if (selectedRow >= 0) {
                // Hiển thị tên voucher trên bảng thay vì ID voucher
                tblHoaDon.setValueAt(tenVoucher, selectedRow, 5); // Cập nhật cột voucher bằng tên voucher
                tblHoaDon.setValueAt(hinhThucThanhToan, selectedRow, 7); // Cập nhật cột hình thức thanh toán
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn một hóa đơn để thanh toán.");
                return;
            }

            // Cập nhật ID nhân viên của hóa đơn thành ID nhân viên hiện tại
            if (selectedHoaDonID != null && !selectedHoaDonID.isEmpty()) {
                // Lấy ID nhân viên hiện tại từ Auth.user
                String idNhanVien = Auth.user.getId();

                // Cập nhật ID nhân viên cho hóa đơn
                int updateResult = bhrs.updateIdNhanVienChoHoaDon(selectedHoaDonID, idNhanVien);
                if (updateResult == 0) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật thông tin nhân viên cho  hóa đơn không thành công!");
                    return;
                }

                // Cập nhật hình thức thanh toán và ID voucher vào cơ sở dữ liệu
                boolean updateVoucher = bhrs.updateVoucherHoaDon(selectedHoaDonID, voucherId);
                boolean updated = bhrs.updateHTTTHoaDon(selectedHoaDonID, hinhThucThanhToan);

                if (!updated) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật hình thức thanh toán không thành công!");
                    return;
                } else if (!updateVoucher) {
                    Notifications.getInstance().show(Notifications.Type.ERROR, "Cập nhật voucher không thành công!");
                    return;
                }

                // Nếu tiền thừa không nhỏ hơn 0, cập nhật trạng thái của hóa đơn
                if (tienThua.compareTo(BigDecimal.ZERO) >= 0) {
                    updateHoaDonTrangThai(selectedHoaDonID, tienThua);
                    xoaMemHD(selectedHoaDonID); // Xóa hóa đơn đã thanh toán khỏi bảng
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, "Số tiền nhập vào không được nhỏ hơn số tiền phải thanh toán.");

                    txtTienThua.setText("0");
                    txtTienThua.requestFocus();
                    return;
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn một hóa đơn để thanh toán.");
                return;
            }

            // Hiển thị kết quả
            txtThanhToan.setText(tongTienSauGiamGia.toString());
            txtTienThua.setText(tienThua.toString());

            // Cập nhật bảng sau khi thanh toán
            fillTable2(bhrs.getHoaDonChoThanhToan());
        } else {
            Notifications.getInstance().show(Notifications.Type.ERROR, "Không tìm thấy ID cho voucher đã chọn.");
        }
    }//GEN-LAST:event_btnSuccesHoaDonActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        int rowIndex = tblHoaDon.getSelectedRow();
        if (rowIndex >= 0) {
            // Tắt sự kiện "Select All"
            selectAll.setSelected(false);
            tblHoaDon.removeRowSelectionInterval(rowIndex, rowIndex);
            DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
            model.setRowCount(0);
            selectedHoaDonID = null;
            fillTable(bhrs.getAllCTSP());
            fillTable2(bhrs.getHoaDonChoThanhToan());
        } else {
            DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
            model.setRowCount(0);
        }
        cleanForm();
    }//GEN-LAST:event_btnClearActionPerformed

    
    private void btnOpenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenKHActionPerformed
       chonKhachHangDialog.setVisible(true);
    }//GEN-LAST:event_btnOpenKHActionPerformed

    private void btnDeleteGHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteGHActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelHDCT = (DefaultTableModel) tblGioHang.getModel();
        int rowCount = modelHDCT.getRowCount();
        boolean isSelectAllChecked = selectAll.isSelected();
        System.out.println("Select All activated: " + isSelectAllChecked);

        String tempSelectedHoaDonID = getSelectedHoaDonID();

        if (tempSelectedHoaDonID != null && isSelectAllChecked) {
            System.out.println("Xóa tất cả các mục");

            List<BillDetailModel> chiTietHoaDons = bhrs.searchByHoaDonID(tempSelectedHoaDonID);

            for (BillDetailModel chiTietHoaDon : chiTietHoaDons) {
                String maSanPhamChiTiet = chiTietHoaDon.getMactsp().getID();
                int soLuong = chiTietHoaDon.getSoLuong();
                int soLuongTonMoi = bhrs.laySoLuongTonByID(maSanPhamChiTiet) + soLuong;
                bhrs.updateSoLuongTon(maSanPhamChiTiet, soLuongTonMoi);
            }

            int deleteResult = bhrs.xoaToanBoHoaDonChiTiet(tempSelectedHoaDonID);
            if (deleteResult <= 0) {
                Notifications.getInstance().show(Notifications.Type.ERROR, "Không thể xóa toàn bộ chi tiết hóa đơn!");
                return;
            }

            boolean isBillUpdated = bhrs.updateBillWhileDeleteALL(tempSelectedHoaDonID);

            // Khôi phục lại giá trị của selectedHoaDonID sau khi xóa
            selectedHoaDonID = tempSelectedHoaDonID;

            fillChiTietHoaDonTable(selectedHoaDonID);
            refreshFormData();
            fillTable2(bhrs.getHoaDonChoThanhToan());

        } else {
            System.out.println("XChọn sản phẩm để xóa");

            List<String> selectedProductDetailIds = new ArrayList<>();

            for (int i = rowCount - 1; i >= 0; i--) {
                Boolean isSelected = (Boolean) modelHDCT.getValueAt(i, 5); // Cột 5 là cột "Chọn"
                if (isSelected != null && isSelected) {
                    String maSanPhamChiTiet = modelHDCT.getValueAt(i, 0).toString();
                    System.out.println("Selected Product Detail ID: " + maSanPhamChiTiet);
                    selectedProductDetailIds.add(maSanPhamChiTiet);
                }
            }

            if (selectedProductDetailIds.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn sản phẩm để xóa.");
                return;
            }

            for (String maCTSP : selectedProductDetailIds) {
                BillDetailModel hdct = bhrs.getHDCT_BY_Id_HD_Id_SPCT(tempSelectedHoaDonID, maCTSP);
                ProductDetailModel spct = bhrs.get_SPCT_BY_Id_SPCT(maCTSP);
                int updatedQuantity = hdct.getSoLuong() + spct.getSoLuongTon();
                int updateQuantityResult = bhrs.updateSoLuongTon(maCTSP, updatedQuantity);
                int deleteHDCTResult = bhrs.xoaHoaDonChiTiet(maCTSP, tempSelectedHoaDonID);
            }

            bhrs.updateBillWhileDeleteOne(tempSelectedHoaDonID);

        }

        refreshFormData();
        refreshGioHangTable();
        Notifications.getInstance().show(Notifications.Type.SUCCESS, "Xóa thành công!");
        selectAll.setSelected(false);
    }//GEN-LAST:event_btnDeleteGHActionPerformed
     

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
