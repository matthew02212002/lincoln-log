/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.persistence.divvy;

import org.hibernate.Session;
import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.persistence.UnitOfWork;

/**
 * Saves an array of {@link org.schwiet.LincolnLog.divvy.Divvy}
 * @author sethschwiethale
 */
public class SaveDivviesTransaction implements UnitOfWork{
    /*
     * Divvies to be deleted
     */
    private Object[] divvies;

    public void perform(Session session) {
        for(Object obj: divvies){
            if(obj instanceof Divvy){
                session.save(obj);
            }
        }
    }

    public static SaveDivviesTransaction getUnitOfWork(Object[] divvies){
        return new SaveDivviesTransaction(divvies);
    }

    private SaveDivviesTransaction(){}

    private SaveDivviesTransaction(Object[] divvy){
        this.divvies = divvy;
    }

}
