/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websevice.c.toko_obatdb;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author asus
 */
@Entity
@Table(name = "kode_transaksi")
@NamedQueries({
    @NamedQuery(name = "KodeTransaksi.findAll", query = "SELECT k FROM KodeTransaksi k"),
    @NamedQuery(name = "KodeTransaksi.findByKodeTransaksi", query = "SELECT k FROM KodeTransaksi k WHERE k.kodeTransaksi = :kodeTransaksi"),
    @NamedQuery(name = "KodeTransaksi.findByNamaObat", query = "SELECT k FROM KodeTransaksi k WHERE k.namaObat = :namaObat"),
    @NamedQuery(name = "KodeTransaksi.findByTglTransaksi", query = "SELECT k FROM KodeTransaksi k WHERE k.tglTransaksi = :tglTransaksi"),
    @NamedQuery(name = "KodeTransaksi.findByQty", query = "SELECT k FROM KodeTransaksi k WHERE k.qty = :qty"),
    @NamedQuery(name = "KodeTransaksi.findByHargaSatuan", query = "SELECT k FROM KodeTransaksi k WHERE k.hargaSatuan = :hargaSatuan"),
    @NamedQuery(name = "KodeTransaksi.findByTotalHarga", query = "SELECT k FROM KodeTransaksi k WHERE k.totalHarga = :totalHarga")})
public class KodeTransaksi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Kode_Transaksi")
    private String kodeTransaksi;
    @Basic(optional = false)
    @Column(name = "Nama_Obat")
    private String namaObat;
    @Basic(optional = false)
    @Column(name = "Tgl_Transaksi")
    @Temporal(TemporalType.DATE)
    private Date tglTransaksi;
    @Basic(optional = false)
    @Column(name = "Qty")
    private int qty;
    @Basic(optional = false)
    @Column(name = "Harga_Satuan")
    private int hargaSatuan;
    @Basic(optional = false)
    @Column(name = "Total_Harga")
    private int totalHarga;
    @JoinColumn(name = "Id_Pembeli", referencedColumnName = "Id_Pembeli")
    @OneToOne(optional = false)
    private Pembeli idPembeli;
    @JoinColumn(name = "Kode_Obat", referencedColumnName = "Kode_Obat")
    @OneToOne(optional = false)
    private Obat kodeObat;

    public KodeTransaksi() {
    }

    public KodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public KodeTransaksi(String kodeTransaksi, String namaObat, Date tglTransaksi, int qty, int hargaSatuan, int totalHarga) {
        this.kodeTransaksi = kodeTransaksi;
        this.namaObat = namaObat;
        this.tglTransaksi = tglTransaksi;
        this.qty = qty;
        this.hargaSatuan = hargaSatuan;
        this.totalHarga = totalHarga;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    public Date getTglTransaksi() {
        return tglTransaksi;
    }

    public void setTglTransaksi(Date tglTransaksi) {
        this.tglTransaksi = tglTransaksi;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(int hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public Pembeli getIdPembeli() {
        return idPembeli;
    }

    public void setIdPembeli(Pembeli idPembeli) {
        this.idPembeli = idPembeli;
    }

    public Obat getKodeObat() {
        return kodeObat;
    }

    public void setKodeObat(Obat kodeObat) {
        this.kodeObat = kodeObat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodeTransaksi != null ? kodeTransaksi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KodeTransaksi)) {
            return false;
        }
        KodeTransaksi other = (KodeTransaksi) object;
        if ((this.kodeTransaksi == null && other.kodeTransaksi != null) || (this.kodeTransaksi != null && !this.kodeTransaksi.equals(other.kodeTransaksi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "websevice.c.toko_obatdb.KodeTransaksi[ kodeTransaksi=" + kodeTransaksi + " ]";
    }
    
}
