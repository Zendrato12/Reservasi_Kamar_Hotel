/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Kamar {
    private int id;
    private String nomor;
    private JenisKamar jenis;

    public Kamar() {}

    public Kamar(int id, String nomor, JenisKamar jenis) {this.id = id;this.nomor = nomor;this.jenis = jenis;}
    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomor() { return nomor; }
    public void setNomor(String nomor) { this.nomor = nomor; }
    public JenisKamar getJenis() { return jenis; }
    public void setJenis(JenisKamar jenis) { this.jenis = jenis; }
}
