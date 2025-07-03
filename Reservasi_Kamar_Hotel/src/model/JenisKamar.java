/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Kelas JenisKamar merepresentasikan entitas jenis kamar di sistem.
 * Berisi atribut id, nama jenis, dan harga per malam.
 */
public class JenisKamar {
    private int id;         // ID unik jenis kamar
    private String nama;    // Nama jenis kamar (misalnya: Deluxe, Standard)
    private double harga;   // Harga kamar per malam
    // Konstruktor tanpa parameter
    public JenisKamar() {
    }
    // Konstruktor dengan parameter untuk inisialisasi objek
    public JenisKamar(int id, String nama, double harga) {this.id = id;this.nama = nama;this.harga = harga;}
    public JenisKamar(int aInt, String string) {this.id =id;this.nama = nama;}
    // Getter dan Setter untuk ID
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    // Getter dan Setter untuk nama jenis kamar
    public String getNama() {return nama;}
    public void setNama(String nama) {this.nama = nama;}
    // Getter dan Setter untuk harga
    public double getHarga() {return harga;}
    public void setHarga(double harga) {this.harga = harga;}

}
