package au.com.rest.test.dao;

import au.com.rest.test.entities.user.UserDetailsEntity;
import au.com.rest.test.entities.user.UserEntity;
import au.com.rest.test.entities.user.UserSecurityEntity;
import au.com.rest.test.enums.KeyValueForSearch;

import javax.persistence.*;

public class PersistenceDAO {

    private final String PU = "NewPersistenceUnit";

    private EntityManagerFactory emf;
    private EntityManager em;


    /**
     * Create the Entity Manager from the Persistence Unit
     */
    public PersistenceDAO() {
        emf = Persistence.createEntityManagerFactory(PU);
        em = emf.createEntityManager();
    }


    /**
     * Retrive UserEntity
     *
     * @param login
     * @return
     */
    public UserEntity getUser(final String login) {
        final Query query = em.createNamedQuery("user.findByLogin");
        query.setParameter("login", login);
        UserEntity toReturn;
        try {
            toReturn = (UserEntity) query.getSingleResult();
        } catch (NoResultException nre) {
            toReturn = null;
        }
        return toReturn;
    }


    /**
     * Retrieve user details
     *
     * @param keyValue
     * @param keyType
     * @return
     */
    public UserDetailsEntity getUserDetails(final String keyValue, final KeyValueForSearch keyType) {
        final String namedQuery;
        if (keyType.equals(KeyValueForSearch.EMAIL)) {
            namedQuery = "userDetails.findByEmail";
        } else if (keyType.equals(KeyValueForSearch.FULL_NAME)) {
            namedQuery = "userDetails.findByName";
        } else if (keyType.equals(KeyValueForSearch.LOGIN)) {
            namedQuery = "userDetails.findByLogin";
        } else {
            namedQuery = null;
        }

        UserDetailsEntity toReturn;
        if (namedQuery == null) {
            toReturn = null;
        } else {
            final Query query = em.createNamedQuery(namedQuery);
            query.setParameter(keyType.getType(), keyValue);
            try {
                toReturn = (UserDetailsEntity) query.getSingleResult();
            } catch (NoResultException nre) {
                toReturn = null;
            }
        }
        return toReturn;
    }


    /**
     * Return user security data
     *
     * @param keyValue
     * @param keyType
     * @return
     */
    public UserSecurityEntity getUserSecurity(final String keyValue, final KeyValueForSearch keyType) {
        final String namedQuery;
        if (keyType.equals(KeyValueForSearch.LOGIN)) {
            namedQuery = "userSecurity.findByLogin";
        } else if (keyType.equals(KeyValueForSearch.ACCOUNT_STATE)) {
            namedQuery = "userSecurity.findByAccountState";
        } else {
            namedQuery = null;
        }

        final UserSecurityEntity toReturn;
        if (namedQuery == null) {
            toReturn = null;
        } else {
            final Query query = em.createNamedQuery(namedQuery);
            query.setParameter(keyType.getType(), keyValue);
            toReturn = (UserSecurityEntity) query.getSingleResult();
        }
        return toReturn;
    }


    /**
     * Generic Persistence
     *
     * @param entity
     * @param <T>
     * @return
     */
    public <T extends Object> T saveData(final T entity) {
        em.getTransaction().begin();
        final T returnEntity = em.merge(entity);
        em.getTransaction().commit();
//        em.flush();
//        em.close();
        return returnEntity;
    }


    /**
     * Close Entity manager and factory
     */
    public void closeEntityManager() {
        if (em != null) {
            em.close();
        }

        if (emf != null) {
            emf.close();
        }
    }
}
