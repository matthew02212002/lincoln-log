/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schwiet.LincolnLog.persistence;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.divvy.DivvyManager;
import org.schwiet.LincolnLog.persistence.divvy.LoadDivviesTransaction;
import org.schwiet.LincolnLog.persistence.divvy.SaveDivvyTransaction;

/**
 * Handles interactions with the DB through Hibernate {@link org.hibernate.Session}
 * @author sethschwiethale
 */
public class PersistenceManager {

    /**
     * single {@link org.hibernate.SessionFactory}
     */
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static final Logger logger = Logger.getLogger(PersistenceManager.class);


    public static void performUnitOfWork(UnitOfWork unit) throws Exception {
        try {
            //get the current Session
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            
            //do persistent stuff against the current session
            unit.perform(session);

            //commit the session TODO: does this close it?
            session.getTransaction().commit();

        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            logger.error(String.format("Error while persisting data: %1$s", e));
            throw e;
        }
    }
    /**
     * takes a {@link org.schwiet.LincolnLog.divvy.Divvy} and persists it to
     * the embedded db
     * @param divvy
     * @throws HibernateException
     */
    public static void saveDivvy(Divvy divvy) throws Exception{
        performUnitOfWork(SaveDivvyTransaction.getUnitOfWork(divvy));
    }
    /**
     * loads all the {@link org.schwiet.LincolnLog.divvy.Divvy}s that are in
     * the Database and adds them to the
     * {@link org.schwiet.LincolnLog.divvy.DivvyManager}
     * @param mgr
     */
    public static void loadDivvies(DivvyManager mgr) throws Exception{
        performUnitOfWork(LoadDivviesTransaction.getUnitOfWork(mgr));
    }
}
