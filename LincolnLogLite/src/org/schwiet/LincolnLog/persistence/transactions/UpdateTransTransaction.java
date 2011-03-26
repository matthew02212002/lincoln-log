/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.persistence.transactions;

import org.hibernate.Session;
import org.schwiet.LincolnLog.persistence.UnitOfWork;
import org.schwiet.LincolnLog.transaction.Transaction;

/**
 * Persists a change to{@link org.schwiet.LincolnLog.transaction.Transaction} to the
 * embedded db using the given {@link org.hibernate.Session}
 * @author sethschwiethale
 */
public class UpdateTransTransaction implements UnitOfWork{

    private Transaction transaction;

    public void perform(Session session) {
        session.update(transaction);
    }

    public static UpdateTransTransaction getUnitOfWork(Transaction trans){
        return new UpdateTransTransaction(trans);
    }

    private UpdateTransTransaction(){}

    private UpdateTransTransaction(Transaction trans){
        this.transaction = trans;
    }

}
