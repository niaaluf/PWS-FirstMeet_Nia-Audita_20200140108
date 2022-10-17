/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websevice.c.toko_obatdb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author asus
 */
@Entity
@Table(name = "obat")
@NamedQueries({
    @NamedQuery(name = "Obat.findAll", query = "SELECT o FROM Obat o"),
    @NamedQuery(name = "Obat.findByKodeObat", query = "SELECT o FROM Obat o WHERE o.kodeObat = :kodeObat"),
    @NamedQuery(name = "Obat.findByNamaObat", query = "SELECT o FROM Obat o WHERE o.namaObat = :namaObat"),
    @NamedQuery(name = "Obat.findByJenisObat", query = "SELECT o FROM Obat o WHERE o.jenisObat = :jenisObat"),
    @NamedQuery(name = "Obat.findByHarga", query = "SELECT o FROM Obat o WHERE o.harga = :harga")})
public class Obat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Kode_Obat")
    private String kodeObat;
    @Basic(optional = false)
    @Column(name = "Nama_Obat")
    private String namaObat;
    @Basic(optional = false)
    @Column(name = "Jenis_Obat")
    private String jenisObat;
    @Basic(optional = false)
    @Column(name = "Harga")
    private int harga;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "kodeObat")
    private KodeTransaksi kodeTransaksi;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "kodeObat")
    private DataPengelolaanObat dataPengelolaanObat;

    public Obat() {
    }

    public Obat(String kodeObat) {
        this.kodeObat = kodeObat;
    }

    public Obat(String kodeObat, String namaObat, String jenisObat, int harga) {
        this.kodeObat = kodeObat;
        this.namaObat = namaObat;
        this.jenisObat = jenisObat;
        this.harga = harga;
    }

    public String getKodeObat() {
        return kodeObat;
    }

    public void setKodeObat(String kodeObat) {
        this.kodeObat = kodeObat;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    public String getJenisObat() {
        return jenisObat;
    }

    public void setJenisObat(String jenisObat) {
        this.jenisObat = jenisObat;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public KodeTransaksi getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(KodeTransaksi kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public DataPengelolaanObat getDataPengelolaanObat() {
        return dataPengelolaanObat;
    }

    public void setDataPengelolaanObat(DataPengelolaanObat dataPengelolaanObat) {
        this.dataPengelolaanObat = dataPengelolaanObat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodeObat != null ? kodeObat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Obat)) {
            return false;
        }
        Obat other = (Obat) object;
        if ((this.kodeObat == null && other.kodeObat != null) || (this.kodeObat != null && !this.kodeObat.equals(other.kodeObat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "websevice.c.toko_obatdb.Obat[ kodeObat=" + kodeObat + " ]";
    }
    
}
