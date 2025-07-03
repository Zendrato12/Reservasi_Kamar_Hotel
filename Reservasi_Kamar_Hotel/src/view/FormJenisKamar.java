/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import DAO.JenisKamarDAO;
import model.JenisKamar;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

/**
 * FormJenisKamar adalah tampilan GUI untuk mengelola data jenis kamar.
 */
public class FormJenisKamar extends JFrame {
    private JTextField txtNama, txtHarga;
    private final JButton btnSimpan;
    private final JButton btnUbah;
    private final JButton btnHapus;
    private JTable table;
    private final DefaultTableModel tableModel;

    private JenisKamarDAO dao = new JenisKamarDAO();
    private int idTerpilih = -1;

    public FormJenisKamar() {
        setTitle("Form Jenis Kamar");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Label dan input nama
        JLabel lblNama = new JLabel("Nama Jenis:");
        lblNama.setBounds(20, 20, 100, 25);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(120, 20, 200, 25);
        add(txtNama);

        // Label dan input harga
        JLabel lblHarga = new JLabel("Harga:");
        lblHarga.setBounds(20, 60, 100, 25);
        add(lblHarga);

        txtHarga = new JTextField();
        txtHarga.setBounds(120, 60, 200, 25);
        add(txtHarga);

        // Tombol Simpan
        btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(20, 100, 90, 25);
        add(btnSimpan);

        // Tombol Ubah
        btnUbah = new JButton("Ubah");
        btnUbah.setBounds(120, 100, 90, 25);
        add(btnUbah);

        // Tombol Hapus
        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(220, 100, 90, 25);
        add(btnHapus);

        // Tabel
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Harga"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 140, 440, 200);
        add(scrollPane);

        // Load data ke tabel
        tampilkanData();

        // Event klik tabel untuk memilih data
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    idTerpilih = Integer.parseInt(table.getValueAt(row, 0).toString());
                    txtNama.setText(table.getValueAt(row, 1).toString());
                    txtHarga.setText(table.getValueAt(row, 2).toString());
                }
            }
        });

        // Event tombol simpan
        btnSimpan.addActionListener(e -> {
            if (validateInput()) {
                JenisKamar jenis = new JenisKamar();
                jenis.setNama(txtNama.getText().trim());
                jenis.setHarga(Double.parseDouble(txtHarga.getText().trim()));
                dao.insert(jenis);
                kosongkanForm();
                tampilkanData();
            }
        });

        // Event tombol ubah
        btnUbah.addActionListener(e -> {
            if (idTerpilih != -1) {
                if (validateInput()) {
                    JenisKamar jenis = new JenisKamar(idTerpilih, txtNama.getText().trim(), Double.parseDouble(txtHarga.getText().trim()));
                    dao.update(jenis);
                    kosongkanForm();
                    tampilkanData();
                    idTerpilih = -1;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data jenis kamar yang akan diubah.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Event tombol hapus
        btnHapus.addActionListener(e -> {
            if (idTerpilih != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dao.delete(idTerpilih);
                    kosongkanForm();
                    tampilkanData();
                    idTerpilih = -1;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data jenis kamar yang akan dihapus.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        setVisible(true);
    }

    // Validasi input form
    private boolean validateInput() {
        String nama = txtNama.getText().trim();
        String hargaStr = txtHarga.getText().trim();

        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama jenis kamar tidak boleh kosong.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            txtNama.requestFocus();
            return false;
        }

        if (hargaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harga tidak boleh kosong.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            txtHarga.requestFocus();
            return false;
        }

        try {
            double harga = Double.parseDouble(hargaStr);
            if (harga <= 0) {
                JOptionPane.showMessageDialog(this, "Harga harus lebih dari nol.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
                txtHarga.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka valid.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            txtHarga.requestFocus();
            return false;
        }

        return true;
    }

    // Mengosongkan form input
    private void kosongkanForm() {
        txtNama.setText("");
        txtHarga.setText("");
    }

    // Menampilkan data dari database ke tabel
    private void tampilkanData() {
        tableModel.setRowCount(0); // clear isi tabel
        List<JenisKamar> list = dao.getAll();
        for (JenisKamar jenis : list) {
            tableModel.addRow(new Object[]{
                jenis.getId(), jenis.getNama(), jenis.getHarga()
            });
        }
    }

    // Method main untuk menjalankan form ini secara mandiri
    public static void main(String[] args) {
        FormJenisKamar form = new FormJenisKamar();
        form.setVisible(true);
    }
}
