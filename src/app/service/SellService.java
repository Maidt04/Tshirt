/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import java.sql.*;
import app.connect.DBConnect;
import app.form.DetailProduct;
import app.model.BillDetailModel;
import app.model.BillModel;
import app.model.BrandModel;
import app.model.ColorModel;
import app.model.CustomerModel;
import app.model.MaterialModel;
import app.model.ProductDetailModel;
import app.model.ProductsModel;
import app.model.SizeModel;
import app.model.StaffModel;
import app.model.VoucherModer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class SellService {

    private ProductDetailService cthdsv = new ProductDetailService();
    List<BillDetailModel> listCTHD = new ArrayList<>();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;
    
    public List<BillModel> getAllHD1() {
        String sql = "SELECT DISTINCT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, HOADON.TongTien, HOADON.HinhThucThanhToan, HOADON.TrangThai\n"
                + "FROM HOADON\n"
                + "INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID ORDER BY NgayTao DESC";

        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            List<BillModel> listHD = new ArrayList<>();
            while (rs.next()) {
                BillModel hdModel = new BillModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new StaffModel(rs.getString(3)),
                        new CustomerModel(rs.getString(4)),
                        rs.getBigDecimal(6),
                        new VoucherModer(rs.getString(5)),
                        rs.getString(7),
                        rs.getString(8));
                listHD.add(hdModel);
            }
            return listHD;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<BillModel> getDaThanhToanHoaDon() {
        String sql = " SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang,\n"
                + "VOUCHER.TenVoucher,\n"
                + "HOADON.TongTien,\n"
                + "HOADON.HinhThucThanhToan, \n"
                + "HOADON.TrangThai\n"
                + "                FROM HOADON\n"
                + "                INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "                INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "                LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "               \n"
                + "                WHERE HOADON.TrangThai = N'Đã thanh toán'\n"
                + "                GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai,HOADON.TongTien ORDER BY NgayTao DESC";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<BillModel> listHD = new ArrayList<>();
            while (rs.next()) {
                BillModel hdModel = new BillModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new StaffModel(rs.getString(3)),
                        new CustomerModel(rs.getString(4)),
                        rs.getBigDecimal(6),
                        new VoucherModer(rs.getString(5)),
                        rs.getString(7),
                        rs.getString(8));
                listHD.add(hdModel);
            }
            return listHD;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<BillModel> getHoaDonChoThanhToan() {
        String sql
                = "SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang,\n"
                + "VOUCHER.TenVoucher,\n"
                + "HOADON.TongTien,\n"
                + "HOADON.HinhThucThanhToan, \n"
                + "HOADON.TrangThai\n"
                + "                FROM HOADON\n"
                + "                INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "                INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "                LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "               \n"
                + "                WHERE HOADON.TrangThai = N'Chờ thanh toán'\n"
                + "                GROUP BY HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen, VOUCHER.TenVoucher, HOADON.HinhThucThanhToan, HOADON.TrangThai,HOADON.TongTien ORDER BY NgayTao DESC";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<BillModel> listHD = new ArrayList<>();
            while (rs.next()) {
                BillModel hdModel = new BillModel(
                        rs.getString("ID"),
                        rs.getDate("NgayTao"),
                        new StaffModel(rs.getString("HoTen")),
                        new CustomerModel(rs.getString("TenKhachHang")),
                        rs.getBigDecimal("TongTien"),
                        new VoucherModer(rs.getString("TenVoucher")),
                        rs.getString("HinhThucThanhToan"),
                        rs.getString("TrangThai"));
                listHD.add(hdModel);
            }
            return listHD;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<BillModel> getDaHuyHoaDon() {
        String sql = "SELECT HOADON.ID, HOADON.NgayTao, NHANVIEN.HoTen, KHACHHANG.HoTen AS TenKhachHang, VOUCHER.TenVoucher, HOADON.TongTien AS TongTien, HOADON.HinhThucThanhToan, HOADON.TrangThai\n"
                + "FROM HOADON\n"
                + "INNER JOIN NHANVIEN ON HOADON.ID_NhanVien = NHANVIEN.ID\n"
                + "INNER JOIN KHACHHANG ON HOADON.ID_KhachHang = KHACHHANG.ID\n"
                + "LEFT JOIN VOUCHER ON HOADON.ID_Voucher = VOUCHER.ID\n"
                + "WHERE HOADON.TrangThai = N'Đã hủy'";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<BillModel> listHD = new ArrayList<>();
            while (rs.next()) {
                BillModel hdModel = new BillModel(
                        rs.getString(1),
                        rs.getDate(2),
                        new StaffModel(rs.getString(3)),
                        new CustomerModel(rs.getString(4)),
                        rs.getBigDecimal(6),
                        new VoucherModer(rs.getString(5)),
                        rs.getString(7),
                        rs.getString(8));
                listHD.add(hdModel);
            }
            return listHD;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<BillDetailModel> searchByHoaDonID(String idHoaDon) {
        List<BillDetailModel> chiTietHoaDons = new ArrayList<>();
        String sql1 = "SELECT SANPHAMCHITIET.ID AS MaSanPhamChiTiet, SANPHAM.TenSanPham AS TenSanPham, SANPHAMCHITIET.GiaBan AS DonGia, HOADONCHITIET.SoLuong AS SoLuong, HOADONCHITIET.ThanhTien AS ThanhTien "
                + "FROM HOADONCHITIET "
                + "INNER JOIN SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID "
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID "
                + "WHERE HOADONCHITIET.ID_HoaDon = ? AND HOADONCHITIET.ID_HoaDon IN (SELECT ID FROM HOADON)";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql1);
            ps.setString(1, idHoaDon.trim());
            rs = ps.executeQuery();
            while (rs.next()) {
                BillDetailModel chiTietHoaDon = new BillDetailModel();
                chiTietHoaDon.setMactsp(new ProductDetailModel(rs.getString("MaSanPhamChiTiet")));
                chiTietHoaDon.setTenSP(new ProductsModel(rs.getString("TenSanPham")));
                chiTietHoaDon.setDonGia(new ProductDetailModel(rs.getBigDecimal("DonGia")));
                chiTietHoaDon.setSoLuong(rs.getInt("SoLuong"));
                chiTietHoaDon.setThanhTien(rs.getBigDecimal("ThanhTien"));
                chiTietHoaDons.add(chiTietHoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTietHoaDons;
    }
    
    public List<BillDetailModel> getAllCTHD() {
        sql = "SELECT        HOADONCHITIET.ID, SANPHAM.TenSanPham AS TenSP, MAUSAC.TenMau AS TenMS, SIZE.Ten AS TenSize, THUONGHIEU.Ten AS TenTT, CHATLIEU.Ten AS TenCL, SANPHAMCHITIET.GiaBan AS DonGia, \n"
                + "                         HOADONCHITIET.SoLuong, HOADONCHITIET.ThanhTien\n"
                + "FROM            HOADONCHITIET INNER JOIN\n"
                + "                          SANPHAMCHITIET ON HOADONCHITIET.ID_SanPhamChiTiet = SANPHAMCHITIET.ID INNER JOIN\n"
                + "                         SANPHAM ON SANPHAM.ID = SANPHAMCHITIET.ID_SanPham INNER JOIN\n"
                + "                         MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID INNER JOIN\n"
                + "                         SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID INNER JOIN\n"
                + "                         THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID INNER JOIN\n"
                + "                         CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                BillDetailModel cthh = new BillDetailModel(
                        rs.getString(1),
                        new ProductsModel(rs.getString(2)),
                        new ColorModel(rs.getString(3)),
                        new SizeModel(rs.getString(4)),
                        new MaterialModel(rs.getString(5)),
                        new BrandModel(rs.getString(6)),
                        new ProductDetailModel(rs.getBigDecimal(7)),
                        rs.getInt(8),
                        rs.getBigDecimal(9)
                );
                listCTHD.add(cthh);
            }
            return listCTHD;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductDetailModel> getAllCTSP() {
        sql = "SELECT SANPHAMCHITIET.ID, SANPHAM.TenSanPham, MAUSAC.TenMau AS TenMau, SIZE.Ten AS TenKichCo, CHATLIEU.Ten AS TenChatLieu, THUONGHIEU.Ten AS TenThuongHieu, SANPHAMCHITIET.GiaBan, SANPHAMCHITIET.SoLuongTon, MAUSAC.MoTa\n"
                + "FROM SANPHAMCHITIET\n"
                + "INNER JOIN SANPHAM ON SANPHAMCHITIET.ID_SanPham = SANPHAM.ID\n"
                + "INNER JOIN MAUSAC ON SANPHAMCHITIET.ID_MauSac = MAUSAC.ID\n"
                + "INNER JOIN SIZE ON SANPHAMCHITIET.ID_Size = SIZE.ID\n"
                + "INNER JOIN CHATLIEU ON SANPHAMCHITIET.ID_ChatLieu = CHATLIEU.ID\n"
                + "INNER JOIN THUONGHIEU ON SANPHAMCHITIET.ID_ThuongHieu = THUONGHIEU.ID\n"
                + "WHERE SANPHAM.TrangThai <> N'Ngừng kinh doanh'\n"
                + "AND SANPHAMCHITIET.SoLuongTon > 0";

        List<ProductDetailModel> listCTSP = new ArrayList<>();
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductDetailModel ctsp = new ProductDetailModel(
                        rs.getString(1), // ID
                        new ProductsModel(rs.getString(2)), // TenSP
                        new ColorModel(rs.getString(3)), // MauSac
                        new SizeModel(rs.getString(4)), // Size
                        new MaterialModel(rs.getString(5)), // ChatLieu
                        new BrandModel(rs.getString(6)), // ThuongHieu
                        rs.getBigDecimal(7), // GiaBan
                        rs.getInt(8), // SoLuongTon
                        rs.getString(9)); // MoTa
                listCTSP.add(ctsp);
            }
            return listCTSP;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }

    }
}
