/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.persistence.divvy;

import org.hibernate.Session;
import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.persistence.UnitOfWork;

/**
 * Deletes an array of {@link org.schwiet.LincolnLog.divvy.Divvy}
 * @author sethschwiethale
 */
public class DeleteDivviesTransaction implements UnitOfWork{
    /*
     * Divvies to be deleted
     */
    private Object[] divvies;

    public void perform(Session session) {
        for(Object obj: divvies){
            if(obj instanceof Divvy){
                session.delete(obj);
            }
        }
    }

    public static DeleteDivviesTransaction getUnitOfWork(Object[] divvies){
        return new DeleteDivviesTransaction(divvies);
    }

    private DeleteDivviesTransaction(){}

    private DeleteDivviesTransaction(Object[] divvy){
        this.divvies = divvy;
    }

}
