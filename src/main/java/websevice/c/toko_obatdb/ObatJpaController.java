/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websevice.c.toko_obatdb;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import websevice.c.toko_obatdb.exceptions.IllegalOrphanException;
import websevice.c.toko_obatdb.exceptions.NonexistentEntityException;
import websevice.c.toko_obatdb.exceptions.PreexistingEntityException;

/**
 *
 * @author asus
 */
public class ObatJpaController implements Serializable {

    public ObatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("websevice.c_toko_obatdb_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ObatJpaController() {
    }
    
    

    public void create(Obat obat) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KodeTransaksi kodeTransaksi = obat.getKodeTransaksi();
            if (kodeTransaksi != null) {
                kodeTransaksi = em.getReference(kodeTransaksi.getClass(), kodeTransaksi.getKodeTransaksi());
                obat.setKodeTransaksi(kodeTransaksi);
            }
            DataPengelolaanObat dataPengelolaanObat = obat.getDataPengelolaanObat();
            if (dataPengelolaanObat != null) {
                dataPengelolaanObat = em.getReference(dataPengelolaanObat.getClass(), dataPengelolaanObat.getKodePengelolaan());
                obat.setDataPengelolaanObat(dataPengelolaanObat);
            }
            em.persist(obat);
            if (kodeTransaksi != null) {
                Obat oldKodeObatOfKodeTransaksi = kodeTransaksi.getKodeObat();
                if (oldKodeObatOfKodeTransaksi != null) {
                    oldKodeObatOfKodeTransaksi.setKodeTransaksi(null);
                    oldKodeObatOfKodeTransaksi = em.merge(oldKodeObatOfKodeTransaksi);
                }
                kodeTransaksi.setKodeObat(obat);
                kodeTransaksi = em.merge(kodeTransaksi);
            }
            if (dataPengelolaanObat != null) {
                Obat oldKodeObatOfDataPengelolaanObat = dataPengelolaanObat.getKodeObat();
                if (oldKodeObatOfDataPengelolaanObat != null) {
                    oldKodeObatOfDataPengelolaanObat.setDataPengelolaanObat(null);
                    oldKodeObatOfDataPengelolaanObat = em.merge(oldKodeObatOfDataPengelolaanObat);
                }
                dataPengelolaanObat.setKodeObat(obat);
                dataPengelolaanObat = em.merge(dataPengelolaanObat);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findObat(obat.getKodeObat()) != null) {
                throw new PreexistingEntityException("Obat " + obat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Obat obat) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Obat persistentObat = em.find(Obat.class, obat.getKodeObat());
            KodeTransaksi kodeTransaksiOld = persistentObat.getKodeTransaksi();
            KodeTransaksi kodeTransaksiNew = obat.getKodeTransaksi();
            DataPengelolaanObat dataPengelolaanObatOld = persistentObat.getDataPengelolaanObat();
            DataPengelolaanObat dataPengelolaanObatNew = obat.getDataPengelolaanObat();
            List<String> illegalOrphanMessages = null;
            if (kodeTransaksiOld != null && !kodeTransaksiOld.equals(kodeTransaksiNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain KodeTransaksi " + kodeTransaksiOld + " since its kodeObat field is not nullable.");
            }
            if (dataPengelolaanObatOld != null && !dataPengelolaanObatOld.equals(dataPengelolaanObatNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain DataPengelolaanObat " + dataPengelolaanObatOld + " since its kodeObat field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (kodeTransaksiNew != null) {
                kodeTransaksiNew = em.getReference(kodeTransaksiNew.getClass(), kodeTransaksiNew.getKodeTransaksi());
                obat.setKodeTransaksi(kodeTransaksiNew);
            }
            if (dataPengelolaanObatNew != null) {
                dataPengelolaanObatNew = em.getReference(dataPengelolaanObatNew.getClass(), dataPengelolaanObatNew.getKodePengelolaan());
                obat.setDataPengelolaanObat(dataPengelolaanObatNew);
            }
            obat = em.merge(obat);
            if (kodeTransaksiNew != null && !kodeTransaksiNew.equals(kodeTransaksiOld)) {
                Obat oldKodeObatOfKodeTransaksi = kodeTransaksiNew.getKodeObat();
                if (oldKodeObatOfKodeTransaksi != null) {
                    oldKodeObatOfKodeTransaksi.setKodeTransaksi(null);
                    oldKodeObatOfKodeTransaksi = em.merge(oldKodeObatOfKodeTransaksi);
                }
                kodeTransaksiNew.setKodeObat(obat);
                kodeTransaksiNew = em.merge(kodeTransaksiNew);
            }
            if (dataPengelolaanObatNew != null && !dataPengelolaanObatNew.equals(dataPengelolaanObatOld)) {
                Obat oldKodeObatOfDataPengelolaanObat = dataPengelolaanObatNew.getKodeObat();
                if (oldKodeObatOfDataPengelolaanObat != null) {
                    oldKodeObatOfDataPengelolaanObat.setDataPengelolaanObat(null);
                    oldKodeObatOfDataPengelolaanObat = em.merge(oldKodeObatOfDataPengelolaanObat);
                }
                dataPengelolaanObatNew.setKodeObat(obat);
                dataPengelolaanObatNew = em.merge(dataPengelolaanObatNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = obat.getKodeObat();
                if (findObat(id) == null) {
                    throw new NonexistentEntityException("The obat with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Obat obat;
            try {
                obat = em.getReference(Obat.class, id);
                obat.getKodeObat();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The obat with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            KodeTransaksi kodeTransaksiOrphanCheck = obat.getKodeTransaksi();
            if (kodeTransaksiOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Obat (" + obat + ") cannot be destroyed since the KodeTransaksi " + kodeTransaksiOrphanCheck + " in its kodeTransaksi field has a non-nullable kodeObat field.");
            }
            DataPengelolaanObat dataPengelolaanObatOrphanCheck = obat.getDataPengelolaanObat();
            if (dataPengelolaanObatOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Obat (" + obat + ") cannot be destroyed since the DataPengelolaanObat " + dataPengelolaanObatOrphanCheck + " in its dataPengelolaanObat field has a non-nullable kodeObat field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(obat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Obat> findObatEntities() {
        return findObatEntities(true, -1, -1);
    }

    public List<Obat> findObatEntities(int maxResults, int firstResult) {
        return findObatEntities(false, maxResults, firstResult);
    }

    private List<Obat> findObatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Obat.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Obat findObat(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Obat.class, id);
        } finally {
            em.close();
        }
    }

    public int getObatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Obat> rt = cq.from(Obat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
