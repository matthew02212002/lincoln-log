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
import org.schwiet.LincolnLog.persistence.divvy.DeleteDivviesTransaction;
import org.schwiet.LincolnLog.persistence.divvy.DeleteDivvyTransaction;
import org.schwiet.LincolnLog.persistence.divvy.LoadDivviesTransaction;
import org.schwiet.LincolnLog.persistence.divvy.SaveDivviesTransaction;
import org.schwiet.LincolnLog.persistence.divvy.SaveDivvyTransaction;
import org.schwiet.LincolnLog.persistence.divvy.UpdateDivvyTransaction;
import org.schwiet.LincolnLog.persistence.transactions.DeleteAllTransTransaction;
import org.schwiet.LincolnLog.persistence.transactions.DeleteTransTransaction;
import org.schwiet.LincolnLog.persistence.transactions.SaveTransTransaction;
import org.schwiet.LincolnLog.persistence.transactions.UpdateTransTransaction;
import org.schwiet.LincolnLog.transaction.Transaction;

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
        }finally{
            sessionFactory.getCurrentSession().close();
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
     * removes the given {@link org.schwiet.LincolnLog.divvy.Divvy} from
     * the embedded db
     * @param divvy
     * @throws HibernateException
     */
    public static void deleteDivvy(Divvy divvy) throws Exception{
        performUnitOfWork(DeleteDivvyTransaction.getUnitOfWork(divvy));
    }

    /**
     * removes the given {@link org.schwiet.LincolnLog.divvy.Divvy} Array
     * the embedded db
     * @param divvy
     * @throws HibernateException
     */
    public static void deleteDivvies(Object[] divvies) throws Exception{
        performUnitOfWork(DeleteDivviesTransaction.getUnitOfWork(divvies));
    }
    /**
     * saves the given {@link org.schwiet.LincolnLog.divvy.Divvy} Array
     * the embedded db
     * @param divvy
     * @throws HibernateException
     */
    public static void saveDivvies(Object[] divvies) throws Exception{
        performUnitOfWork(SaveDivviesTransaction.getUnitOfWork(divvies));
    }
    /**
     * persists any changes made to a {@link org.schwiet.LincolnLog.divvy.Divvy}
     * @param divvy
     * @throws Exception
     */
    public static void updateDivvy(Divvy divvy) throws Exception{
        performUnitOfWork(UpdateDivvyTransaction.getUnitOfWork(divvy));
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

    /**
     * takes a {@link org.schwiet.LincolnLog.transaction.Transaction} and persists
     * it to the embedded db
     * @param trans
     * @throws Exception
     */
    public static void saveTransaction(Transaction trans) throws Exception{
        performUnitOfWork(SaveTransTransaction.getUnitOfWork(trans));
    }

    /**
     * takes a {@link org.schwiet.LincolnLog.transaction.Transaction} and deletes
     * it from the embedded db
     * @param trans
     * @throws Exception
     */
    public static void deleteTransaction(Transaction trans) throws Exception{
        performUnitOfWork(DeleteTransTransaction.getUnitOfWork(trans));
    }

    /**
     * clears the Transaction Database table
     * @throws Exception
     */
    public static void clearTransactions() throws Exception{
        performUnitOfWork(DeleteAllTransTransaction.getUnitOfWork());
    }

    /**
     * takes a {@link org.schwiet.LincolnLog.transaction.Transaction} and persists
     * it to the embedded db
     * <p>
     * assumes the Transaction already exists in the Database
     * </p>
     * @param trans
     * @throws Exception
     */
    public static void updateTransaction(Transaction trans) throws Exception{
        performUnitOfWork(UpdateTransTransaction.getUnitOfWork(trans));
    }
}
