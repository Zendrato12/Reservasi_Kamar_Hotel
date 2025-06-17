/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestKoneksi {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_hotel";
    private static final String USER = "root"; // Username XAMPP default
    private static final String PASS = ""; // Password XAMPP default

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Koneksi berhasil");
        } else {
            System.out.println("Koneksi gagal");
        }
    }
}
