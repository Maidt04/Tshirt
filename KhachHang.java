/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package khachhang;

/**
 *
 * @author Admin
 */
public class KhachHang {
    private int id;
    private String TênNV, MaNV, GioiTinh, SDT, TrangThai;

    public KhachHang() {
    }

    public KhachHang(String TênNV, String MaNV, String GioiTinh, String SDT, String TrangThai) {
        this.TênNV = TênNV;
        this.MaNV = MaNV;
        this.GioiTinh = GioiTinh;
        this.SDT = SDT;
        this.TrangThai = TrangThai;
    }
    

    public KhachHang(int id, String TênNV, String MaNV, String GioiTinh, String SDT, String TrangThai) {
        this.id = id;
        this.TênNV = TênNV;
        this.MaNV = MaNV;
        this.GioiTinh = GioiTinh;
        this.SDT = SDT;
        this.TrangThai = TrangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTênNV() {
        return TênNV;
    }

    public void setTênNV(String TênNV) {
        this.TênNV = TênNV;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }
    
    
    
    
           

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
