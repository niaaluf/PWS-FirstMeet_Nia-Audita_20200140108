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
import websevice.c.toko_obatdb.exceptions.IllegalOrphanException;
import websevice.c.toko_obatdb.exceptions.NonexistentEntityException;
import websevice.c.toko_obatdb.exceptions.PreexistingEntityException;

/**
 *
 * @author asus
 */
public class PembeliJpaController implements Serializable {

    public PembeliJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pembeli pembeli) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KodeTransaksi kodeTransaksi = pembeli.getKodeTransaksi();
            if (kodeTransaksi != null) {
                kodeTransaksi = em.getReference(kodeTransaksi.getClass(), kodeTransaksi.getKodeTransaksi());
                pembeli.setKodeTransaksi(kodeTransaksi);
            }
            em.persist(pembeli);
            if (kodeTransaksi != null) {
                Pembeli oldIdPembeliOfKodeTransaksi = kodeTransaksi.getIdPembeli();
                if (oldIdPembeliOfKodeTransaksi != null) {
                    oldIdPembeliOfKodeTransaksi.setKodeTransaksi(null);
                    oldIdPembeliOfKodeTransaksi = em.merge(oldIdPembeliOfKodeTransaksi);
                }
                kodeTransaksi.setIdPembeli(pembeli);
                kodeTransaksi = em.merge(kodeTransaksi);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPembeli(pembeli.getIdPembeli()) != null) {
                throw new PreexistingEntityException("Pembeli " + pembeli + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pembeli pembeli) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pembeli persistentPembeli = em.find(Pembeli.class, pembeli.getIdPembeli());
            KodeTransaksi kodeTransaksiOld = persistentPembeli.getKodeTransaksi();
            KodeTransaksi kodeTransaksiNew = pembeli.getKodeTransaksi();
            List<String> illegalOrphanMessages = null;
            if (kodeTransaksiOld != null && !kodeTransaksiOld.equals(kodeTransaksiNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain KodeTransaksi " + kodeTransaksiOld + " since its idPembeli field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (kodeTransaksiNew != null) {
                kodeTransaksiNew = em.getReference(kodeTransaksiNew.getClass(), kodeTransaksiNew.getKodeTransaksi());
                pembeli.setKodeTransaksi(kodeTransaksiNew);
            }
            pembeli = em.merge(pembeli);
            if (kodeTransaksiNew != null && !kodeTransaksiNew.equals(kodeTransaksiOld)) {
                Pembeli oldIdPembeliOfKodeTransaksi = kodeTransaksiNew.getIdPembeli();
                if (oldIdPembeliOfKodeTransaksi != null) {
                    oldIdPembeliOfKodeTransaksi.setKodeTransaksi(null);
                    oldIdPembeliOfKodeTransaksi = em.merge(oldIdPembeliOfKodeTransaksi);
                }
                kodeTransaksiNew.setIdPembeli(pembeli);
                kodeTransaksiNew = em.merge(kodeTransaksiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pembeli.getIdPembeli();
                if (findPembeli(id) == null) {
                    throw new NonexistentEntityException("The pembeli with id " + id + " no longer exists.");
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
            Pembeli pembeli;
            try {
                pembeli = em.getReference(Pembeli.class, id);
                pembeli.getIdPembeli();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pembeli with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            KodeTransaksi kodeTransaksiOrphanCheck = pembeli.getKodeTransaksi();
            if (kodeTransaksiOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pembeli (" + pembeli + ") cannot be destroyed since the KodeTransaksi " + kodeTransaksiOrphanCheck + " in its kodeTransaksi field has a non-nullable idPembeli field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pembeli);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pembeli> findPembeliEntities() {
        return findPembeliEntities(true, -1, -1);
    }

    public List<Pembeli> findPembeliEntities(int maxResults, int firstResult) {
        return findPembeliEntities(false, maxResults, firstResult);
    }

    private List<Pembeli> findPembeliEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pembeli.class));
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

    public Pembeli findPembeli(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pembeli.class, id);
        } finally {
            em.close();
        }
    }

    public int getPembeliCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pembeli> rt = cq.from(Pembeli.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
