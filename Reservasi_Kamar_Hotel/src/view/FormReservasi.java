package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import DAO.ReservasiDAO;
import model.Reservasi;
import model.Tamu;
import model.Kamar;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Form GUI untuk manajemen data reservasi.
 */
public class FormReservasi extends JFrame {
    private JTextField tfCheckIn, tfCheckOut, tfId, tfIdTamu, tfIdKamar;
    private JTable table;
    private DefaultTableModel tableModel;
    private ReservasiDAO reservasiDAO = new ReservasiDAO();  // DAO untuk akses database

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public FormReservasi() {
        setTitle("Form Reservasi");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Label dan field
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20, 20, 100, 25);
        add(lblId);
        tfId = new JTextField();
        tfId.setBounds(130, 20, 150, 25);
        tfId.setEnabled(false); // ID auto-generated
        add(tfId);

        JLabel lblIdTamu = new JLabel("ID Tamu:");
        lblIdTamu.setBounds(20, 55, 100, 25);
        add(lblIdTamu);
        tfIdTamu = new JTextField();
        tfIdTamu.setBounds(130, 55, 150, 25);
        add(tfIdTamu);

        JLabel lblIdKamar = new JLabel("ID Kamar:");
        lblIdKamar.setBounds(20, 90, 100, 25);
        add(lblIdKamar);
        tfIdKamar = new JTextField();
        tfIdKamar.setBounds(130, 90, 150, 25);
        add(tfIdKamar);

        JLabel lblCheckIn = new JLabel("Check-In:");
        lblCheckIn.setBounds(20, 125, 100, 25);
        add(lblCheckIn);
        tfCheckIn = new JTextField();
        tfCheckIn.setBounds(130, 125, 150, 25);
        add(tfCheckIn);

        JLabel lblCheckOut = new JLabel("Check-Out:");
        lblCheckOut.setBounds(20, 160, 100, 25);
        add(lblCheckOut);
        tfCheckOut = new JTextField();
        tfCheckOut.setBounds(130, 160, 150, 25);
        add(tfCheckOut);

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(130, 195, 150, 25);
        add(btnSimpan);

        btnSimpan.addActionListener(e -> {
            if (validateInput()) {
                Reservasi r = new Reservasi();

                // ID auto-generated, no need to set
                // r.setId(0);

                // Set objek Tamu
                Tamu tamu = new Tamu();
                tamu.setId(Integer.parseInt(tfIdTamu.getText().trim()));
                r.setTamu(tamu);

                // Set objek Kamar
                Kamar kamar = new Kamar();
                kamar.setId(Integer.parseInt(tfIdKamar.getText().trim()));
                r.setKamar(kamar);

                r.setTglCheckIn(tfCheckIn.getText().trim());
                r.setTglCheckOut(tfCheckOut.getText().trim());

                reservasiDAO.insert(r);
                tampilkanData();
                resetForm();
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
            }
        });

        // Tabel data reservasi
        tableModel = new DefaultTableModel(new String[]{"ID", "ID Tamu", "ID Kamar", "Check-In", "Check-Out"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 230, 540, 120);
        add(scrollPane);

        tampilkanData();

        setVisible(true);
    }

    // Validasi input
    private boolean validateInput() {
        String idTamuStr = tfIdTamu.getText().trim();
        String idKamarStr = tfIdKamar.getText().trim();
        String checkInStr = tfCheckIn.getText().trim();
        String checkOutStr = tfCheckOut.getText().trim();

        if (idTamuStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID Tamu tidak boleh kosong.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            tfIdTamu.requestFocus();
            return false;
        }
        if (idKamarStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID Kamar tidak boleh kosong.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            tfIdKamar.requestFocus();
            return false;
        }
        try {
            Integer.parseInt(idTamuStr);
            Integer.parseInt(idKamarStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID Tamu dan ID Kamar harus berupa angka valid.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (checkInStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tanggal Check-In tidak boleh kosong.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            tfCheckIn.requestFocus();
            return false;
        }
        if (checkOutStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tanggal Check-Out tidak boleh kosong.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            tfCheckOut.requestFocus();
            return false;
        }

        LocalDate checkInDate;
        LocalDate checkOutDate;
        try {
            checkInDate = LocalDate.parse(checkInStr, dateFormatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal Check-In harus yyyy-MM-dd.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            tfCheckIn.requestFocus();
            return false;
        }
        try {
            checkOutDate = LocalDate.parse(checkOutStr, dateFormatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal Check-Out harus yyyy-MM-dd.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            tfCheckOut.requestFocus();
            return false;
        }

        if (!checkOutDate.isAfter(checkInDate)) {
            JOptionPane.showMessageDialog(this, "Tanggal Check-Out harus setelah Check-In.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            tfCheckOut.requestFocus();
            return false;
        }

        return true;
    }

    private void tampilkanData() {
        tableModel.setRowCount(0);
        List<Reservasi> list = reservasiDAO.getAll();
        for (Reservasi r : list) {
            Object[] row ={r.getId(), r.getTamu().getId(),r.getKamar().getId(),r.getTglCheckIn(), r.getTglCheckOut()};
            tableModel.addRow(row);
            //tableModel.addRow(new Object[] {
                //r.getId(),
                //r.getTamu().getId(),
                //r.getKamar().getId(),
                //r.getTglCheckIn(),
                //r.getTglCheckOut()
            //});
        }
    }

    private void resetForm() {
        tfIdTamu.setText("");
        tfIdKamar.setText("");
        tfCheckIn.setText("");
        tfCheckOut.setText("");
    }

    public static void main(String[] args) {
        new FormReservasi();
    }
}
