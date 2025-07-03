/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.*;
import java.util.*;
import database.TestKoneksi;
import model.JenisKamar;

/**
 * JenisKamarDAO adalah class yang menangani operasi database
 * untuk entitas JenisKamar seperti ambil semua data, simpan, ubah, dan hapus.
 */
public class JenisKamarDAO {

    /**
     * Mengambil semua data jenis kamar dari tabel tbl_jenis_kamar.
     * @return List berisi objek JenisKamar.
     */
    public List<JenisKamar> getAll() {
        List<JenisKamar> list = new ArrayList<>();
        String sql = "SELECT id_jenis_kamar, nama, harga FROM tbl_jenis_kamar";

        try (Connection conn = TestKoneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                JenisKamar jenis = new JenisKamar();
                jenis.setId(rs.getInt("id_jenis_kamar"));
                jenis.setNama(rs.getString("nama"));
                jenis.setHarga(rs.getDouble("harga"));
                list.add(jenis);
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data JenisKamar: " + e.getMessage());
        }

        return list;
    }

    /**
     * Menyimpan data jenis kamar baru ke database.
     * @param jenis
     */
    public void insert(JenisKamar jenis) {
        String sql = "INSERT INTO tbl_jenis_kamar (nama, harga) VALUES (?, ?)";

        try (Connection conn = TestKoneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, jenis.getNama());
            stmt.setDouble(2, jenis.getHarga());
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Insert JenisKamar gagal: tidak ada data yang ditambahkan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal menambahkan JenisKamar: " + e.getMessage());
        }
    }

    /**
     * Mengupdate data jenis kamar berdasarkan ID.
     * @param jenis
     */
    public void update(JenisKamar jenis) {
        String sql = "UPDATE tbl_jenis_kamar SET nama=?, harga=? WHERE id_jenis_kamar=?";

        try (Connection conn = TestKoneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, jenis.getNama());
            stmt.setDouble(2, jenis.getHarga());
            stmt.setInt(3, jenis.getId());
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Update JenisKamar gagal: data dengan ID " + jenis.getId() + " tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal memperbarui JenisKamar: " + e.getMessage());
        }
    }

    /**
     * Menghapus data jenis kamar berdasarkan ID.
     * @param id
     */
    public void delete(int id) {
        String sql = "DELETE FROM tbl_jenis_kamar WHERE id_jenis_kamar=?";

        try (Connection conn = TestKoneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Delete JenisKamar gagal: data dengan ID " + id + " tidak ditemukan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal menghapus JenisKamar: " + e.getMessage());
        }
    }
}

