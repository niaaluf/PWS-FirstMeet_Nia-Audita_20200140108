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
public class KodeTransaksiJpaController implements Serializable {

    public KodeTransaksiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("websevice.c_toko_obatdb_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public KodeTransaksiJpaController() {
    }
    
    

    public void create(KodeTransaksi kodeTransaksi) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Pembeli idPembeliOrphanCheck = kodeTransaksi.getIdPembeli();
        if (idPembeliOrphanCheck != null) {
            KodeTransaksi oldKodeTransaksiOfIdPembeli = idPembeliOrphanCheck.getKodeTransaksi();
            if (oldKodeTransaksiOfIdPembeli != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Pembeli " + idPembeliOrphanCheck + " already has an item of type KodeTransaksi whose idPembeli column cannot be null. Please make another selection for the idPembeli field.");
            }
        }
        Obat kodeObatOrphanCheck = kodeTransaksi.getKodeObat();
        if (kodeObatOrphanCheck != null) {
            KodeTransaksi oldKodeTransaksiOfKodeObat = kodeObatOrphanCheck.getKodeTransaksi();
            if (oldKodeTransaksiOfKodeObat != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Obat " + kodeObatOrphanCheck + " already has an item of type KodeTransaksi whose kodeObat column cannot be null. Please make another selection for the kodeObat field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pembeli idPembeli = kodeTransaksi.getIdPembeli();
            if (idPembeli != null) {
                idPembeli = em.getReference(idPembeli.getClass(), idPembeli.getIdPembeli());
                kodeTransaksi.setIdPembeli(idPembeli);
            }
            Obat kodeObat = kodeTransaksi.getKodeObat();
            if (kodeObat != null) {
                kodeObat = em.getReference(kodeObat.getClass(), kodeObat.getKodeObat());
                kodeTransaksi.setKodeObat(kodeObat);
            }
            em.persist(kodeTransaksi);
            if (idPembeli != null) {
                idPembeli.setKodeTransaksi(kodeTransaksi);
                idPembeli = em.merge(idPembeli);
            }
            if (kodeObat != null) {
                kodeObat.setKodeTransaksi(kodeTransaksi);
                kodeObat = em.merge(kodeObat);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKodeTransaksi(kodeTransaksi.getKodeTransaksi()) != null) {
                throw new PreexistingEntityException("KodeTransaksi " + kodeTransaksi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(KodeTransaksi kodeTransaksi) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KodeTransaksi persistentKodeTransaksi = em.find(KodeTransaksi.class, kodeTransaksi.getKodeTransaksi());
            Pembeli idPembeliOld = persistentKodeTransaksi.getIdPembeli();
            Pembeli idPembeliNew = kodeTransaksi.getIdPembeli();
            Obat kodeObatOld = persistentKodeTransaksi.getKodeObat();
            Obat kodeObatNew = kodeTransaksi.getKodeObat();
            List<String> illegalOrphanMessages = null;
            if (idPembeliNew != null && !idPembeliNew.equals(idPembeliOld)) {
                KodeTransaksi oldKodeTransaksiOfIdPembeli = idPembeliNew.getKodeTransaksi();
                if (oldKodeTransaksiOfIdPembeli != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Pembeli " + idPembeliNew + " already has an item of type KodeTransaksi whose idPembeli column cannot be null. Please make another selection for the idPembeli field.");
                }
            }
            if (kodeObatNew != null && !kodeObatNew.equals(kodeObatOld)) {
                KodeTransaksi oldKodeTransaksiOfKodeObat = kodeObatNew.getKodeTransaksi();
                if (oldKodeTransaksiOfKodeObat != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Obat " + kodeObatNew + " already has an item of type KodeTransaksi whose kodeObat column cannot be null. Please make another selection for the kodeObat field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPembeliNew != null) {
                idPembeliNew = em.getReference(idPembeliNew.getClass(), idPembeliNew.getIdPembeli());
                kodeTransaksi.setIdPembeli(idPembeliNew);
            }
            if (kodeObatNew != null) {
                kodeObatNew = em.getReference(kodeObatNew.getClass(), kodeObatNew.getKodeObat());
                kodeTransaksi.setKodeObat(kodeObatNew);
            }
            kodeTransaksi = em.merge(kodeTransaksi);
            if (idPembeliOld != null && !idPembeliOld.equals(idPembeliNew)) {
                idPembeliOld.setKodeTransaksi(null);
                idPembeliOld = em.merge(idPembeliOld);
            }
            if (idPembeliNew != null && !idPembeliNew.equals(idPembeliOld)) {
                idPembeliNew.setKodeTransaksi(kodeTransaksi);
                idPembeliNew = em.merge(idPembeliNew);
            }
            if (kodeObatOld != null && !kodeObatOld.equals(kodeObatNew)) {
                kodeObatOld.setKodeTransaksi(null);
                kodeObatOld = em.merge(kodeObatOld);
            }
            if (kodeObatNew != null && !kodeObatNew.equals(kodeObatOld)) {
                kodeObatNew.setKodeTransaksi(kodeTransaksi);
                kodeObatNew = em.merge(kodeObatNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = kodeTransaksi.getKodeTransaksi();
                if (findKodeTransaksi(id) == null) {
                    throw new NonexistentEntityException("The kodeTransaksi with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KodeTransaksi kodeTransaksi;
            try {
                kodeTransaksi = em.getReference(KodeTransaksi.class, id);
                kodeTransaksi.getKodeTransaksi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kodeTransaksi with id " + id + " no longer exists.", enfe);
            }
            Pembeli idPembeli = kodeTransaksi.getIdPembeli();
            if (idPembeli != null) {
                idPembeli.setKodeTransaksi(null);
                idPembeli = em.merge(idPembeli);
            }
            Obat kodeObat = kodeTransaksi.getKodeObat();
            if (kodeObat != null) {
                kodeObat.setKodeTransaksi(null);
                kodeObat = em.merge(kodeObat);
            }
            em.remove(kodeTransaksi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<KodeTransaksi> findKodeTransaksiEntities() {
        return findKodeTransaksiEntities(true, -1, -1);
    }

    public List<KodeTransaksi> findKodeTransaksiEntities(int maxResults, int firstResult) {
        return findKodeTransaksiEntities(false, maxResults, firstResult);
    }

    private List<KodeTransaksi> findKodeTransaksiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(KodeTransaksi.class));
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

    public KodeTransaksi findKodeTransaksi(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(KodeTransaksi.class, id);
        } finally {
            em.close();
        }
    }

    public int getKodeTransaksiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<KodeTransaksi> rt = cq.from(KodeTransaksi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
