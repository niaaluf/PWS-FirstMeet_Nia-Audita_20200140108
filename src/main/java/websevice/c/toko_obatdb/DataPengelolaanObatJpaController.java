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
public class DataPengelolaanObatJpaController implements Serializable {

    public DataPengelolaanObatJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("websevice.c_toko_obatdb_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DataPengelolaanObatJpaController() {
    }
    
    

    public void create(DataPengelolaanObat dataPengelolaanObat) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Obat kodeObatOrphanCheck = dataPengelolaanObat.getKodeObat();
        if (kodeObatOrphanCheck != null) {
            DataPengelolaanObat oldDataPengelolaanObatOfKodeObat = kodeObatOrphanCheck.getDataPengelolaanObat();
            if (oldDataPengelolaanObatOfKodeObat != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Obat " + kodeObatOrphanCheck + " already has an item of type DataPengelolaanObat whose kodeObat column cannot be null. Please make another selection for the kodeObat field.");
            }
        }
        Apoteker idApotekerOrphanCheck = dataPengelolaanObat.getIdApoteker();
        if (idApotekerOrphanCheck != null) {
            DataPengelolaanObat oldDataPengelolaanObatOfIdApoteker = idApotekerOrphanCheck.getDataPengelolaanObat();
            if (oldDataPengelolaanObatOfIdApoteker != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Apoteker " + idApotekerOrphanCheck + " already has an item of type DataPengelolaanObat whose idApoteker column cannot be null. Please make another selection for the idApoteker field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Obat kodeObat = dataPengelolaanObat.getKodeObat();
            if (kodeObat != null) {
                kodeObat = em.getReference(kodeObat.getClass(), kodeObat.getKodeObat());
                dataPengelolaanObat.setKodeObat(kodeObat);
            }
            Apoteker idApoteker = dataPengelolaanObat.getIdApoteker();
            if (idApoteker != null) {
                idApoteker = em.getReference(idApoteker.getClass(), idApoteker.getIdApoteker());
                dataPengelolaanObat.setIdApoteker(idApoteker);
            }
            em.persist(dataPengelolaanObat);
            if (kodeObat != null) {
                kodeObat.setDataPengelolaanObat(dataPengelolaanObat);
                kodeObat = em.merge(kodeObat);
            }
            if (idApoteker != null) {
                idApoteker.setDataPengelolaanObat(dataPengelolaanObat);
                idApoteker = em.merge(idApoteker);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDataPengelolaanObat(dataPengelolaanObat.getKodePengelolaan()) != null) {
                throw new PreexistingEntityException("DataPengelolaanObat " + dataPengelolaanObat + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DataPengelolaanObat dataPengelolaanObat) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DataPengelolaanObat persistentDataPengelolaanObat = em.find(DataPengelolaanObat.class, dataPengelolaanObat.getKodePengelolaan());
            Obat kodeObatOld = persistentDataPengelolaanObat.getKodeObat();
            Obat kodeObatNew = dataPengelolaanObat.getKodeObat();
            Apoteker idApotekerOld = persistentDataPengelolaanObat.getIdApoteker();
            Apoteker idApotekerNew = dataPengelolaanObat.getIdApoteker();
            List<String> illegalOrphanMessages = null;
            if (kodeObatNew != null && !kodeObatNew.equals(kodeObatOld)) {
                DataPengelolaanObat oldDataPengelolaanObatOfKodeObat = kodeObatNew.getDataPengelolaanObat();
                if (oldDataPengelolaanObatOfKodeObat != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Obat " + kodeObatNew + " already has an item of type DataPengelolaanObat whose kodeObat column cannot be null. Please make another selection for the kodeObat field.");
                }
            }
            if (idApotekerNew != null && !idApotekerNew.equals(idApotekerOld)) {
                DataPengelolaanObat oldDataPengelolaanObatOfIdApoteker = idApotekerNew.getDataPengelolaanObat();
                if (oldDataPengelolaanObatOfIdApoteker != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Apoteker " + idApotekerNew + " already has an item of type DataPengelolaanObat whose idApoteker column cannot be null. Please make another selection for the idApoteker field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (kodeObatNew != null) {
                kodeObatNew = em.getReference(kodeObatNew.getClass(), kodeObatNew.getKodeObat());
                dataPengelolaanObat.setKodeObat(kodeObatNew);
            }
            if (idApotekerNew != null) {
                idApotekerNew = em.getReference(idApotekerNew.getClass(), idApotekerNew.getIdApoteker());
                dataPengelolaanObat.setIdApoteker(idApotekerNew);
            }
            dataPengelolaanObat = em.merge(dataPengelolaanObat);
            if (kodeObatOld != null && !kodeObatOld.equals(kodeObatNew)) {
                kodeObatOld.setDataPengelolaanObat(null);
                kodeObatOld = em.merge(kodeObatOld);
            }
            if (kodeObatNew != null && !kodeObatNew.equals(kodeObatOld)) {
                kodeObatNew.setDataPengelolaanObat(dataPengelolaanObat);
                kodeObatNew = em.merge(kodeObatNew);
            }
            if (idApotekerOld != null && !idApotekerOld.equals(idApotekerNew)) {
                idApotekerOld.setDataPengelolaanObat(null);
                idApotekerOld = em.merge(idApotekerOld);
            }
            if (idApotekerNew != null && !idApotekerNew.equals(idApotekerOld)) {
                idApotekerNew.setDataPengelolaanObat(dataPengelolaanObat);
                idApotekerNew = em.merge(idApotekerNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = dataPengelolaanObat.getKodePengelolaan();
                if (findDataPengelolaanObat(id) == null) {
                    throw new NonexistentEntityException("The dataPengelolaanObat with id " + id + " no longer exists.");
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
            DataPengelolaanObat dataPengelolaanObat;
            try {
                dataPengelolaanObat = em.getReference(DataPengelolaanObat.class, id);
                dataPengelolaanObat.getKodePengelolaan();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dataPengelolaanObat with id " + id + " no longer exists.", enfe);
            }
            Obat kodeObat = dataPengelolaanObat.getKodeObat();
            if (kodeObat != null) {
                kodeObat.setDataPengelolaanObat(null);
                kodeObat = em.merge(kodeObat);
            }
            Apoteker idApoteker = dataPengelolaanObat.getIdApoteker();
            if (idApoteker != null) {
                idApoteker.setDataPengelolaanObat(null);
                idApoteker = em.merge(idApoteker);
            }
            em.remove(dataPengelolaanObat);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DataPengelolaanObat> findDataPengelolaanObatEntities() {
        return findDataPengelolaanObatEntities(true, -1, -1);
    }

    public List<DataPengelolaanObat> findDataPengelolaanObatEntities(int maxResults, int firstResult) {
        return findDataPengelolaanObatEntities(false, maxResults, firstResult);
    }

    private List<DataPengelolaanObat> findDataPengelolaanObatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DataPengelolaanObat.class));
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

    public DataPengelolaanObat findDataPengelolaanObat(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DataPengelolaanObat.class, id);
        } finally {
            em.close();
        }
    }

    public int getDataPengelolaanObatCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DataPengelolaanObat> rt = cq.from(DataPengelolaanObat.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
