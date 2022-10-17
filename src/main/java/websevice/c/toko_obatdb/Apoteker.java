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
@Table(name = "apoteker")
@NamedQueries({
    @NamedQuery(name = "Apoteker.findAll", query = "SELECT a FROM Apoteker a"),
    @NamedQuery(name = "Apoteker.findByIdApoteker", query = "SELECT a FROM Apoteker a WHERE a.idApoteker = :idApoteker"),
    @NamedQuery(name = "Apoteker.findByNamaApoteker", query = "SELECT a FROM Apoteker a WHERE a.namaApoteker = :namaApoteker"),
    @NamedQuery(name = "Apoteker.findByAlamat", query = "SELECT a FROM Apoteker a WHERE a.alamat = :alamat"),
    @NamedQuery(name = "Apoteker.findByNoTelp", query = "SELECT a FROM Apoteker a WHERE a.noTelp = :noTelp")})
public class Apoteker implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Apoteker")
    private String idApoteker;
    @Basic(optional = false)
    @Column(name = "Nama_Apoteker")
    private String namaApoteker;
    @Basic(optional = false)
    @Column(name = "Alamat")
    private String alamat;
    @Basic(optional = false)
    @Column(name = "No_Telp")
    private String noTelp;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idApoteker")
    private DataPengelolaanObat dataPengelolaanObat;

    public Apoteker() {
    }

    public Apoteker(String idApoteker) {
        this.idApoteker = idApoteker;
    }

    public Apoteker(String idApoteker, String namaApoteker, String alamat, String noTelp) {
        this.idApoteker = idApoteker;
        this.namaApoteker = namaApoteker;
        this.alamat = alamat;
        this.noTelp = noTelp;
    }

    public String getIdApoteker() {
        return idApoteker;
    }

    public void setIdApoteker(String idApoteker) {
        this.idApoteker = idApoteker;
    }

    public String getNamaApoteker() {
        return namaApoteker;
    }

    public void setNamaApoteker(String namaApoteker) {
        this.namaApoteker = namaApoteker;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
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
        hash += (idApoteker != null ? idApoteker.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apoteker)) {
            return false;
        }
        Apoteker other = (Apoteker) object;
        if ((this.idApoteker == null && other.idApoteker != null) || (this.idApoteker != null && !this.idApoteker.equals(other.idApoteker))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "websevice.c.toko_obatdb.Apoteker[ idApoteker=" + idApoteker + " ]";
    }
    
}
