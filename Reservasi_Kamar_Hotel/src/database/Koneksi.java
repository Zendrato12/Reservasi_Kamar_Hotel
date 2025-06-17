/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    //URL, USER, DAN PASSWORD UNTUK KONEKSI DATABASE
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_hotel";
    private static final String USER = "root";
    private static final String PASS = "";

    // Metode statis untuk mengembalikan objek koneksi (Connection)
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // Untuk pengujian langsung (opsional, bisa dihapus jika tidak perlu)
    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            System.out.println("Koneksi ke database berhasil!");
            conn.close(); // Tutup koneksi setelah digunakan
        } catch (SQLException e) {
            System.out.println("Gagal koneksi: " + e.getMessage());
        }
    }
    
}
