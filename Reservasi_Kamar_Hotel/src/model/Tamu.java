/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


public class Tamu {
    private int id;             // ID unik tamu (biasanya auto-increment di DB)
    private String nama;        // Nama tamu
    private String alamat;      // Alamat tamu
    private String telepon;     // Nomor telepon tamu

    // Konstruktor tanpa parameter
    public Tamu() {}
    // Konstruktor dengan parameter
    public Tamu(int id, String nama, String alamat, String telepon) {this.id = id;this.nama = nama;this.alamat = alamat;this.telepon = telepon;}
    // Getter dan Setter untuk masing-masing atribut
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getNama() {return nama;}
    public void setNama(String nama) {this.nama = nama;}
    public String getAlamat() {return alamat;}
    public void setAlamat(String alamat) {this.alamat = alamat;}
    public String getTelepon() {return telepon;}
    public void setTelepon(String telepon) {this.telepon = telepon;}
    
}