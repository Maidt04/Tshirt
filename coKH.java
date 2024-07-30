/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package khachhang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import khachhang.KhachHang;
/**
 *
 * @author Admin
 */

public class coKH {
    public static final String HOSTNAME = "localhost";
    public static final String PORT = "1433";
    public static final String DBNAME = "DUAN1_TEAM2";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "123";

    public static Connection getConnection() {
        String connectionUrl = "jdbc:sqlserver://" + HOSTNAME + ":" + PORT + ";"
                + "databaseName=" + DBNAME + ";encrypt=true;trustservercertificate=true;";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(connectionUrl, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(System.out);
        }

        return null;
    }
     private static Connection con;

    static void delete(int ma) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public  ArrayList<KhachHang> getListKH() throws SQLException{
        ArrayList<KhachHang> list= new ArrayList<>();
        String sql = "Select * FROM KHACHHANG ";
        try{
            PreparedStatement ps = con.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                KhachHang k = new KhachHang();
                k.setId(rs.getInt("ID"));
                k.setTênNV(rs.getString("Name"));
                k.setMaNV(rs.getString("Mã nhân viên "));
                k.setMaNV(rs.getString("SDT "));
                k.setSDT(rs.getString("SDT"));
                k.setTrangThai(rs.getString("Trang thái "));
                list.add(k);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return list ;
        
    
    }
    public static void main(String[] args) {
        new KhachHang();
    } 
   
         public coKH() {
             
         }
 
    
}



