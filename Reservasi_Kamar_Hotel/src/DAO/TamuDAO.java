/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Tamu;
import database.TestKoneksi;

// TamuDAO.java - CRUD entitas tamu
/**
 * Class inidigunakan untuk melakukan operassi database terkait entitas tamu
 * @author adven
 */
public class TamuDAO {
    
    // Method untuk menambahkan data tamu baru ke dalam database
    public void insert(Tamu tamu) {
        String sql = "INSERT INTO tbl_tamu (nama, alamat, telepon) VALUES (?, ?, ?)";
        try (Connection conn = TestKoneksi.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Menyisipkan nilai ke dalam parameter query SQL
            stmt.setString(1, tamu.getNama());
            stmt.setString(2, tamu.getAlamat());
            stmt.setString(3, tamu.getTelepon());
            int rows = stmt.executeUpdate(); // Eksekusi query INSERT
            if (rows == 0) {
                System.out.println("Insert tamu gagal: tidak ada data yang ditambahkan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal insert tamu: " + e.getMessage());
        }
    }

    // Method untuk mengambil semua data tamu dari database
    public List<Tamu> getAll() {
        List<Tamu> list = new ArrayList<>();
        String sql = "SELECT * FROM tbl_tamu";

        try (Connection conn = TestKoneksi.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {

            // Menelusuri hasil query dan mengonversi menjadi objek Tamu
            while (rs.next()) {
                Tamu t = new Tamu();
                t.setId(rs.getInt("id_tamu"));
                t.setNama(rs.getString("nama"));
                t.setAlamat(rs.getString("alamat"));
                t.setTelepon(rs.getString("telepon"));
                list.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data tamu: " + e.getMessage());
        }

        return list;
    }

    // Method untuk memperbarui data tamu berdasarkan ID
    public void update(Tamu tamu) {
        String sql = "UPDATE tbl_tamu SET nama=?, alamat=?, telepon=? WHERE id_tamu=?";
        try (Connection conn = TestKoneksi.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tamu.getNama());
            stmt.setString(2, tamu.getAlamat());
            stmt.setString(3, tamu.getTelepon());
            stmt.setInt(4, tamu.getId());
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Update tamu gagal: data dengan ID " + tamu.getId() + " tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal update tamu: " + e.getMessage());
        }
    }

    // Method untuk menghapus data tamu berdasarkan ID
    public void delete(int idTamu) {
        String sql = "DELETE FROM tbl_tamu WHERE id_tamu=?";
        try (Connection conn = TestKoneksi.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTamu);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Delete tamu gagal: data dengan ID " + idTamu + " tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal delete tamu: " + e.getMessage());
        }
    }
}

