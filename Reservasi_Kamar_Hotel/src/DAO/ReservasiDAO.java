package DAO;

import java.sql.*;
import java.util.*;
import database.Koneksi;
import model.Reservasi;
import model.Tamu;
import model.Kamar;

/**
 * Kelas DAO (Data Access Object) untuk tabel reservasi.
 * Berfungsi untuk operasi insert dan ambil data dari database.
 */
public class ReservasiDAO {

    // Menyimpan data reservasi ke database
    public void insert(Reservasi r) {
        String sql = "INSERT INTO tbl_reservasi (tamu_id, kamar_id, tgl_checkin, tgl_checkout) VALUES (?, ?, ?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // ID reservasi biasanya auto increment, jadi dihilangkan dari insert
            stmt.setInt(1, r.getTamu().getId());
            stmt.setInt(2, r.getKamar().getId());
            stmt.setString(3, r.getTglCheckIn());
            stmt.setString(4, r.getTglCheckOut());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                System.out.println("Insert reservasi gagal: tidak ada data yang ditambahkan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal insert reservasi: " + e.getMessage());
        }
    }

    // Mengambil semua data reservasi dari database
    public List<Reservasi> getAll() {
        List<Reservasi> list = new ArrayList<>();

        String sql = "SELECT reservasi_id, tamu_id, kamar_id, tgl_checkin, tgl_checkout FROM tbl_reservasi";

        try (Connection conn = Koneksi.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reservasi r = new Reservasi();
                r.setId(rs.getInt("reservasi_id"));

                // Buat dan isi objek Tamu
                Tamu t = new Tamu();
                t.setId(rs.getInt("tamu_id"));
                r.setTamu(t);

                // Buat dan isi objek Kamar
                Kamar k = new Kamar();
                k.setId(rs.getInt("kamar_id"));
                r.setKamar(k);

                r.setTglCheckIn(rs.getString("tgl_checkin"));
                r.setTglCheckOut(rs.getString("tgl_checkout"));

                list.add(r);
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data reservasi: " + e.getMessage());
        }

        return list;
    }
}
