/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import DAO.JenisKamarDAO;
import DAO.KamarDAO;
import model.JenisKamar;
import model.Kamar;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

/**
 * FormKamar adalah tampilan GUI untuk mengelola data kamar hotel.
 */
public class FormKamar extends JFrame {
    private JTextField txtNomor;
    private JComboBox<JenisKamar> cmbJenisKamar;
    private final JButton btnSimpan;
    private final JButton btnUbah;
    private final JButton btnHapus;
    private JTable table;
    private final DefaultTableModel tableModel;

    private KamarDAO kamarDAO = new KamarDAO();
    private JenisKamarDAO jenisKamarDAO = new JenisKamarDAO();
    private int idTerpilih = -1;

    public FormKamar() {
        setTitle("Form Kamar");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Label dan input nomor kamar
        JLabel lblNomor = new JLabel("Nomor Kamar:");
        lblNomor.setBounds(20, 20, 100, 25);
        add(lblNomor);

        txtNomor = new JTextField();
        txtNomor.setBounds(130, 20, 200, 25);
        add(txtNomor);

        // Label dan combo box jenis kamar
        JLabel lblJenis = new JLabel("Jenis Kamar:");
        lblJenis.setBounds(20, 60, 100, 25);
        add(lblJenis);

        cmbJenisKamar = new JComboBox<>();
        cmbJenisKamar.setBounds(130, 60, 200, 25);
        add(cmbJenisKamar);

        // Load data jenis kamar ke combobox
        List<JenisKamar> jenisList = jenisKamarDAO.getAll();
        for (JenisKamar jenis : jenisList) {
            cmbJenisKamar.addItem(jenis);
        }

        // Tombol-tombol
        btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(20, 100, 90, 25);
        add(btnSimpan);

        btnUbah = new JButton("Ubah");
        btnUbah.setBounds(120, 100, 90, 25);
        add(btnUbah);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(220, 100, 90, 25);
        add(btnHapus);

        // Tabel untuk menampilkan data kamar
        tableModel = new DefaultTableModel(new String[]{"ID", "Nomor", "Jenis"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 140, 500, 200);
        add(scrollPane);

        tampilkanData();

        // Aksi saat klik tabel
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    idTerpilih = Integer.parseInt(table.getValueAt(row, 0).toString());
                    txtNomor.setText(table.getValueAt(row, 1).toString());

                    // Set jenis kamar terpilih di combo box
                    for (int i = 0; i < cmbJenisKamar.getItemCount(); i++) {
                        JenisKamar jenis = cmbJenisKamar.getItemAt(i);
                        if (jenis.getNama().equals(table.getValueAt(row, 2).toString())) {
                            cmbJenisKamar.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });

        // Aksi tombol simpan
        btnSimpan.addActionListener(e -> {
            if (validateInput()) {
                Kamar kamar = new Kamar();
                kamar.setNomor(txtNomor.getText().trim());
                kamar.setJenis((JenisKamar) cmbJenisKamar.getSelectedItem());
                kamarDAO.insert(kamar);
                kosongkanForm();
                tampilkanData();
            }
        });

        // Aksi tombol ubah
        btnUbah.addActionListener(e -> {
            if (idTerpilih != -1) {
                if (validateInput()) {
                    Kamar kamar = new Kamar(idTerpilih, txtNomor.getText().trim(), (JenisKamar) cmbJenisKamar.getSelectedItem());
                    kamarDAO.update(kamar);
                    kosongkanForm();
                    tampilkanData();
                    idTerpilih = -1;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data kamar yang akan diubah.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Aksi tombol hapus
        btnHapus.addActionListener(e -> {
            if (idTerpilih != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    kamarDAO.delete(idTerpilih);
                    kosongkanForm();
                    tampilkanData();
                    idTerpilih = -1;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data kamar yang akan dihapus.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        setVisible(true);
    }

    // Validasi input form
    private boolean validateInput() {
        String nomor = txtNomor.getText().trim();
        JenisKamar jenis = (JenisKamar) cmbJenisKamar.getSelectedItem();

        if (nomor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nomor kamar tidak boleh kosong.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            txtNomor.requestFocus();
            return false;
        }

        if (jenis == null) {
            JOptionPane.showMessageDialog(this, "Pilih jenis kamar.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            cmbJenisKamar.requestFocus();
            return false;
        }

        return true;
    }

    // Mengosongkan input
    private void kosongkanForm() {
        txtNomor.setText("");
        if (cmbJenisKamar.getItemCount() > 0) {
            cmbJenisKamar.setSelectedIndex(0);
        }
    }

    // Tampilkan data dari database ke tabel
    private void tampilkanData() {
        tableModel.setRowCount(0);
        List<Kamar> list = kamarDAO.getAll();
        for (Kamar k : list) {
            tableModel.addRow(new Object[]{
                k.getId(), k.getNomor(), k.getJenis().getNama()
            });
        }
    }

    public static void main(String[] args) {
        new FormKamar();
    }
}
