/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormDashboard extends JFrame {

    public FormDashboard() {
        setTitle("Dashboard Reservasi Hotel");
        setSize(400, 300);
        setLocationRelativeTo(null); // agar window muncul di tengah
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10)); // layout vertikal dengan spasi

        // Tombol-tombol menu
        JButton btnTamu = new JButton("Kelola Data Tamu");
        JButton btnKamar = new JButton("Kelola Data Kamar");
        JButton btnReservasi = new JButton("Kelola Reservasi");
        JButton btnExit = new JButton("Keluar");

        // Menambahkan tombol ke frame
        add(btnTamu);
        add(btnKamar);
        add(btnReservasi);
        add(btnExit);

        // Aksi tombol
        btnTamu.addActionListener(e -> new FormTamu().setVisible(true));
        btnKamar.addActionListener(e -> new FormKamar().setVisible(true));
        btnReservasi.addActionListener(e -> new FormReservasi().setVisible(true));
        btnExit.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        // Menjalankan dashboard
        SwingUtilities.invokeLater(() -> new FormDashboard().setVisible(true));
    }
}
