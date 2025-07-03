package DAO;

import database.TestKoneksi;
import model.Kamar;
import model.JenisKamar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk operasi database pada tabel kamar.
 */
public class KamarDAO {

    // Ambil semua data kamar dari tabel tbl_kamar dan join dengan tbl_jenis_kamar
    public List<Kamar> getAll() {
        List<Kamar> list = new ArrayList<>();
        String sql = "SELECT k.id_kamar, k.nomor_kamar, j.id_jenis_kamar, j.nama " +
                     "FROM tbl_kamar k " +
                     "INNER JOIN tbl_jenis_kamar j ON k.id_jenis_kamar = j.id_jenis_kamar";
        try (Connection conn = TestKoneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Buat objek jenis kamar
                JenisKamar jenis = new JenisKamar(rs.getInt("id_jenis_kamar"), rs.getString("nama"));

                // Buat objek kamar dengan jenis kamar terkait
                Kamar kamar = new Kamar(rs.getInt("id_kamar"), rs.getString("nomor_kamar"), jenis);

                list.add(kamar);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data kamar: " + e.getMessage());
        }
        return list;
    }

    // Insert data kamar baru
    public void insert(Kamar kamar) {
        String sql = "INSERT INTO tbl_kamar (nomor_kamar, id_jenis_kamar) VALUES (?, ?)";
        try (Connection conn = TestKoneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kamar.getNomor());
            stmt.setInt(2, kamar.getJenis().getId());
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Insert kamar gagal: tidak ada data yang ditambahkan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal insert kamar: " + e.getMessage());
        }
    }

    // Update data kamar berdasarkan id
    public void update(Kamar kamar) {
        String sql = "UPDATE tbl_kamar SET nomor_kamar = ?, id_jenis_kamar = ? WHERE id_kamar = ?";
        try (Connection conn = TestKoneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kamar.getNomor());
            stmt.setInt(2, kamar.getJenis().getId());
            stmt.setInt(3, kamar.getId());
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Update kamar gagal: data dengan ID " + kamar.getId() + " tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal update kamar: " + e.getMessage());
        }
    }

    // Hapus data kamar berdasarkan id
    public void delete(int id) {
        String sql = "DELETE FROM tbl_kamar WHERE id_kamar = ?";
        try (Connection conn = TestKoneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Delete kamar gagal: data dengan ID " + id + " tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal hapus kamar: " + e.getMessage());
        }
    }
}
