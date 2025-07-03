/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import DAO.TamuDAO;
import model.Tamu;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * FormTamu adalah JFrame untuk mengelola data tamu hotel
 */
public class FormTamu extends JFrame {
    private JTextField tfNama, tfAlamat, tfTelepon;
    private final JButton btnSimpan;
    private final JButton btnUbah;
    private final JButton btnHapus;
    private final JButton btnReset;
    private JTable table;
    private DefaultTableModel tableModel;
    private int idTamuTerpilih = -1;
    private final TamuDAO tamuDAO = new TamuDAO(); // DAO untuk operasi database

    public FormTamu() {
        setTitle("Manajemen Data Tamu");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Komponen input
        JLabel lblNama = new JLabel("Nama:");
        lblNama.setBounds(20, 20, 80, 25);
        add(lblNama);

        tfNama = new JTextField();
        tfNama.setBounds(100, 20, 200, 25);
        add(tfNama);

        JLabel lblAlamat = new JLabel("Alamat:");
        lblAlamat.setBounds(20, 60, 80, 25);
        add(lblAlamat);

        tfAlamat = new JTextField();
        tfAlamat.setBounds(100, 60, 200, 25);
        add(tfAlamat);

        JLabel lblTelepon = new JLabel("Telepon:");
        lblTelepon.setBounds(20, 100, 80, 25);
        add(lblTelepon);

        tfTelepon = new JTextField();
        tfTelepon.setBounds(100, 100, 200, 25);
        add(tfTelepon);

        // Tombol aksi
        btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(320, 20, 100, 25);
        add(btnSimpan);

        btnUbah = new JButton("Ubah");
        btnUbah.setBounds(320, 60, 100, 25);
        add(btnUbah);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(320, 100, 100, 25);
        add(btnHapus);

        btnReset = new JButton("Reset");
        btnReset.setBounds(430, 20, 100, 25);
        add(btnReset);

        // Tabel
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Alamat", "Telepon"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 150, 550, 200);
        add(scrollPane);

        // Load data dari database ke tabel
        tampilkanData();

        // Event saat klik baris tabel
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    idTamuTerpilih = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    tfNama.setText(tableModel.getValueAt(row, 1).toString());
                    tfAlamat.setText(tableModel.getValueAt(row, 2).toString());
                    tfTelepon.setText(tableModel.getValueAt(row, 3).toString());
                }
            }
        });

        // Event tombol
        btnSimpan.addActionListener(e -> {
            if (validateInput()) {
                simpanTamu();
            }
        });
        btnUbah.addActionListener(e -> {
            if (validateInput()) {
                ubahTamu();
            }
        });
        btnHapus.addActionListener(e -> hapusTamu());
        btnReset.addActionListener(e -> resetForm());

        setVisible(true);
    }

    // Validasi input form
    private boolean validateInput() {
        String nama = tfNama.getText().trim();
        String alamat = tfAlamat.getText().trim();
        String telepon = tfTelepon.getText().trim();

        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            tfNama.requestFocus();
            return false;
        }
        if (alamat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Alamat tidak boleh kosong.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            tfAlamat.requestFocus();
            return false;
        }
        if (telepon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Telepon tidak boleh kosong.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            tfTelepon.requestFocus();
            return false;
        }
        // Cek format telepon: hanya angka, 7-15 digit
        if (!Pattern.matches("\\d{7,15}", telepon)) {
            JOptionPane.showMessageDialog(this, "Telepon harus berupa angka dengan 7 sampai 15 digit.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            tfTelepon.requestFocus();
            return false;
        }
        return true;
    }

    // Menyimpan tamu baru ke database
    private void simpanTamu() {
        Tamu t = new Tamu();
        t.setNama(tfNama.getText().trim());
        t.setAlamat(tfAlamat.getText().trim());
        t.setTelepon(tfTelepon.getText().trim());

        tamuDAO.insert(t);
        tampilkanData();
        resetForm();
    }

    // Mengubah data tamu
    private void ubahTamu() {
        if (idTamuTerpilih != -1) {
            Tamu t = new Tamu();
            t.setId(idTamuTerpilih);
            t.setNama(tfNama.getText().trim());
            t.setAlamat(tfAlamat.getText().trim());
            t.setTelepon(tfTelepon.getText().trim());

            tamuDAO.update(t);
            tampilkanData();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data tamu yang akan diubah.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Menghapus data tamu
    private void hapusTamu() {
        if (idTamuTerpilih != -1) {
            int confirm = JOptionPane.showConfirmDialog
        (this, "Yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tamuDAO.delete(idTamuTerpilih);
                tampilkanData();
                resetForm();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data tamu yang akan dihapus.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Membersihkan form input
    private void resetForm() {
        tfNama.setText("");
        tfAlamat.setText("");
        tfTelepon.setText("");
        idTamuTerpilih = -1;
        table.clearSelection();
    }

    // Menampilkan semua data tamu ke dalam tabel
    private void tampilkanData() {
        tableModel.setRowCount(0); // Hapus baris lama
        List<Tamu> daftar = tamuDAO.getAll();
        for (Tamu t : daftar) {
            Object[] row = {t.getId(), t.getNama(), t.getAlamat(), t.getTelepon()};
            tableModel.addRow(row);
        }
    }

    // Method main untuk menjalankan form ini secara independen
    public static void main(String[] args) {
        FormTamu form = new FormTamu();
        form.setVisible(true);
    }
}
