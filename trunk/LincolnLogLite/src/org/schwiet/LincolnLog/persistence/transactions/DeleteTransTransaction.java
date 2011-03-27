/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.persistence.transactions;

import org.hibernate.Session;
import org.schwiet.LincolnLog.persistence.UnitOfWork;
import org.schwiet.LincolnLog.transaction.Transaction;

/**
 * Removes a {@link org.schwiet.LincolnLog.transaction.Transaction} from the
 * embedded db using the given {@link org.hibernate.Session}
 * @author sethschwiethale
 */
public class DeleteTransTransaction implements UnitOfWork{

    private Transaction transaction;

    public void perform(Session session) {
        session.delete(transaction);
    }

    public static DeleteTransTransaction getUnitOfWork(Transaction trans){
        return new DeleteTransTransaction(trans);
    }

    private DeleteTransTransaction(){}

    private DeleteTransTransaction(Transaction trans){
        this.transaction = trans;
    }

}
