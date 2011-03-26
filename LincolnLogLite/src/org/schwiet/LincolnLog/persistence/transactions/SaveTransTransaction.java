/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.persistence.transactions;

import org.hibernate.Session;
import org.schwiet.LincolnLog.persistence.UnitOfWork;
import org.schwiet.LincolnLog.transaction.Transaction;

/**
 * Persists a {@link org.schwiet.LincolnLog.transaction.Transaction} to the
 * embedded db using the given {@link org.hibernate.Session}
 * @author sethschwiethale
 */
public class SaveTransTransaction implements UnitOfWork{

    private Transaction transaction;

    public void perform(Session session) {
        session.save(transaction);
    }

    public static SaveTransTransaction getUnitOfWork(Transaction trans){
        return new SaveTransTransaction(trans);
    }

    private SaveTransTransaction(){}

    private SaveTransTransaction(Transaction trans){
        this.transaction = trans;
    }

}
