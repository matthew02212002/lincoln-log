/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.persistence.transactions;

import org.hibernate.Query;
import org.hibernate.Session;
import org.schwiet.LincolnLog.divvy.DivvyManager;
import org.schwiet.LincolnLog.persistence.UnitOfWork;

/**
 * Used at the beginning of the application to load all the Divvies from the
 * database into the {@link org.schwiet.LincolnLog.divvy.DivvyManager}
 * @author sethschwiethale
 */
public class DeleteAllTransTransaction implements UnitOfWork{

    public void perform(Session session) {
//        List<Transaction> list = (List<Transaction>)session.createQuery("from Transaction").list();
        Query q = session.createQuery("DELETE FROM Transaction");
        q.executeUpdate();
    }

    public static DeleteAllTransTransaction getUnitOfWork(){
        return new DeleteAllTransTransaction();
    }

    private DeleteAllTransTransaction(){}

}
