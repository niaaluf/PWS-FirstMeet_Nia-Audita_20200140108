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
public class ApotekerJpaController implements Serializable {

    public ApotekerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Apoteker apoteker) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DataPengelolaanObat dataPengelolaanObat = apoteker.getDataPengelolaanObat();
            if (dataPengelolaanObat != null) {
                dataPengelolaanObat = em.getReference(dataPengelolaanObat.getClass(), dataPengelolaanObat.getKodePengelolaan());
                apoteker.setDataPengelolaanObat(dataPengelolaanObat);
            }
            em.persist(apoteker);
            if (dataPengelolaanObat != null) {
                Apoteker oldIdApotekerOfDataPengelolaanObat = dataPengelolaanObat.getIdApoteker();
                if (oldIdApotekerOfDataPengelolaanObat != null) {
                    oldIdApotekerOfDataPengelolaanObat.setDataPengelolaanObat(null);
                    oldIdApotekerOfDataPengelolaanObat = em.merge(oldIdApotekerOfDataPengelolaanObat);
                }
                dataPengelolaanObat.setIdApoteker(apoteker);
                dataPengelolaanObat = em.merge(dataPengelolaanObat);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findApoteker(apoteker.getIdApoteker()) != null) {
                throw new PreexistingEntityException("Apoteker " + apoteker + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Apoteker apoteker) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Apoteker persistentApoteker = em.find(Apoteker.class, apoteker.getIdApoteker());
            DataPengelolaanObat dataPengelolaanObatOld = persistentApoteker.getDataPengelolaanObat();
            DataPengelolaanObat dataPengelolaanObatNew = apoteker.getDataPengelolaanObat();
            List<String> illegalOrphanMessages = null;
            if (dataPengelolaanObatOld != null && !dataPengelolaanObatOld.equals(dataPengelolaanObatNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain DataPengelolaanObat " + dataPengelolaanObatOld + " since its idApoteker field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (dataPengelolaanObatNew != null) {
                dataPengelolaanObatNew = em.getReference(dataPengelolaanObatNew.getClass(), dataPengelolaanObatNew.getKodePengelolaan());
                apoteker.setDataPengelolaanObat(dataPengelolaanObatNew);
            }
            apoteker = em.merge(apoteker);
            if (dataPengelolaanObatNew != null && !dataPengelolaanObatNew.equals(dataPengelolaanObatOld)) {
                Apoteker oldIdApotekerOfDataPengelolaanObat = dataPengelolaanObatNew.getIdApoteker();
                if (oldIdApotekerOfDataPengelolaanObat != null) {
                    oldIdApotekerOfDataPengelolaanObat.setDataPengelolaanObat(null);
                    oldIdApotekerOfDataPengelolaanObat = em.merge(oldIdApotekerOfDataPengelolaanObat);
                }
                dataPengelolaanObatNew.setIdApoteker(apoteker);
                dataPengelolaanObatNew = em.merge(dataPengelolaanObatNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = apoteker.getIdApoteker();
                if (findApoteker(id) == null) {
                    throw new NonexistentEntityException("The apoteker with id " + id + " no longer exists.");
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
            Apoteker apoteker;
            try {
                apoteker = em.getReference(Apoteker.class, id);
                apoteker.getIdApoteker();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The apoteker with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            DataPengelolaanObat dataPengelolaanObatOrphanCheck = apoteker.getDataPengelolaanObat();
            if (dataPengelolaanObatOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Apoteker (" + apoteker + ") cannot be destroyed since the DataPengelolaanObat " + dataPengelolaanObatOrphanCheck + " in its dataPengelolaanObat field has a non-nullable idApoteker field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(apoteker);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Apoteker> findApotekerEntities() {
        return findApotekerEntities(true, -1, -1);
    }

    public List<Apoteker> findApotekerEntities(int maxResults, int firstResult) {
        return findApotekerEntities(false, maxResults, firstResult);
    }

    private List<Apoteker> findApotekerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Apoteker.class));
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

    public Apoteker findApoteker(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Apoteker.class, id);
        } finally {
            em.close();
        }
    }

    public int getApotekerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Apoteker> rt = cq.from(Apoteker.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
