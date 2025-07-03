/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Kelas model untuk entitas Reservasi.
 * Menyimpan informasi reservasi termasuk tamu, kamar, tanggal check-in, dan check-out.
 */
public class Reservasi {
    private int id;             // ID reservasi
    private Tamu tamu;          // Objek tamu yang melakukan reservasi
    private Kamar kamar;        // Objek kamar yang dipesan
    private String tglCheckIn;  // Tanggal check-in
    private String tglCheckOut; // Tanggal check-out

    // Getter dan Setter untuk ID
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    // Getter dan Setter untuk objek tamu
    public Tamu getTamu() {return tamu;}
    public void setTamu(Tamu tamu) {this.tamu = tamu;}
    // Getter dan Setter untuk objek kamar
    public Kamar getKamar() {return kamar;}
    public void setKamar(Kamar kamar) {this.kamar = kamar;}
    // Getter dan Setter untuk tanggal check-in
    public String getTglCheckIn() {return tglCheckIn;}
    public void setTglCheckIn(String tglCheckIn) {this.tglCheckIn = tglCheckIn;}
    // Getter dan Setter untuk tanggal check-out
    public String getTglCheckOut() {return tglCheckOut;}
    public void setTglCheckOut(String tglCheckOut) {this.tglCheckOut = tglCheckOut;}
}
