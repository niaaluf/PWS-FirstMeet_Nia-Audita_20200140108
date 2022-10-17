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
@Table(name = "data_pengelolaan_obat")
@NamedQueries({
    @NamedQuery(name = "DataPengelolaanObat.findAll", query = "SELECT d FROM DataPengelolaanObat d"),
    @NamedQuery(name = "DataPengelolaanObat.findByKodePengelolaan", query = "SELECT d FROM DataPengelolaanObat d WHERE d.kodePengelolaan = :kodePengelolaan"),
    @NamedQuery(name = "DataPengelolaanObat.findByNamaObat", query = "SELECT d FROM DataPengelolaanObat d WHERE d.namaObat = :namaObat"),
    @NamedQuery(name = "DataPengelolaanObat.findByJenisObat", query = "SELECT d FROM DataPengelolaanObat d WHERE d.jenisObat = :jenisObat"),
    @NamedQuery(name = "DataPengelolaanObat.findByTglKadaluwarsa", query = "SELECT d FROM DataPengelolaanObat d WHERE d.tglKadaluwarsa = :tglKadaluwarsa"),
    @NamedQuery(name = "DataPengelolaanObat.findByHarga", query = "SELECT d FROM DataPengelolaanObat d WHERE d.harga = :harga"),
    @NamedQuery(name = "DataPengelolaanObat.findByEfekSamping", query = "SELECT d FROM DataPengelolaanObat d WHERE d.efekSamping = :efekSamping"),
    @NamedQuery(name = "DataPengelolaanObat.findByCaraPenggunaan", query = "SELECT d FROM DataPengelolaanObat d WHERE d.caraPenggunaan = :caraPenggunaan")})
public class DataPengelolaanObat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Kode_Pengelolaan")
    private String kodePengelolaan;
    @Basic(optional = false)
    @Column(name = "Nama_Obat")
    private String namaObat;
    @Basic(optional = false)
    @Column(name = "Jenis_Obat")
    private String jenisObat;
    @Basic(optional = false)
    @Column(name = "Tgl_Kadaluwarsa")
    @Temporal(TemporalType.DATE)
    private Date tglKadaluwarsa;
    @Basic(optional = false)
    @Column(name = "Harga")
    private int harga;
    @Basic(optional = false)
    @Column(name = "Efek_Samping")
    private String efekSamping;
    @Basic(optional = false)
    @Column(name = "Cara_Penggunaan")
    private String caraPenggunaan;
    @JoinColumn(name = "Kode_Obat", referencedColumnName = "Kode_Obat")
    @OneToOne(optional = false)
    private Obat kodeObat;
    @JoinColumn(name = "Id_Apoteker", referencedColumnName = "Id_Apoteker")
    @OneToOne(optional = false)
    private Apoteker idApoteker;

    public DataPengelolaanObat() {
    }

    public DataPengelolaanObat(String kodePengelolaan) {
        this.kodePengelolaan = kodePengelolaan;
    }

    public DataPengelolaanObat(String kodePengelolaan, String namaObat, String jenisObat, Date tglKadaluwarsa, int harga, String efekSamping, String caraPenggunaan) {
        this.kodePengelolaan = kodePengelolaan;
        this.namaObat = namaObat;
        this.jenisObat = jenisObat;
        this.tglKadaluwarsa = tglKadaluwarsa;
        this.harga = harga;
        this.efekSamping = efekSamping;
        this.caraPenggunaan = caraPenggunaan;
    }

    public String getKodePengelolaan() {
        return kodePengelolaan;
    }

    public void setKodePengelolaan(String kodePengelolaan) {
        this.kodePengelolaan = kodePengelolaan;
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

    public Date getTglKadaluwarsa() {
        return tglKadaluwarsa;
    }

    public void setTglKadaluwarsa(Date tglKadaluwarsa) {
        this.tglKadaluwarsa = tglKadaluwarsa;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getEfekSamping() {
        return efekSamping;
    }

    public void setEfekSamping(String efekSamping) {
        this.efekSamping = efekSamping;
    }

    public String getCaraPenggunaan() {
        return caraPenggunaan;
    }

    public void setCaraPenggunaan(String caraPenggunaan) {
        this.caraPenggunaan = caraPenggunaan;
    }

    public Obat getKodeObat() {
        return kodeObat;
    }

    public void setKodeObat(Obat kodeObat) {
        this.kodeObat = kodeObat;
    }

    public Apoteker getIdApoteker() {
        return idApoteker;
    }

    public void setIdApoteker(Apoteker idApoteker) {
        this.idApoteker = idApoteker;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodePengelolaan != null ? kodePengelolaan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataPengelolaanObat)) {
            return false;
        }
        DataPengelolaanObat other = (DataPengelolaanObat) object;
        if ((this.kodePengelolaan == null && other.kodePengelolaan != null) || (this.kodePengelolaan != null && !this.kodePengelolaan.equals(other.kodePengelolaan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "websevice.c.toko_obatdb.DataPengelolaanObat[ kodePengelolaan=" + kodePengelolaan + " ]";
    }
    
}
