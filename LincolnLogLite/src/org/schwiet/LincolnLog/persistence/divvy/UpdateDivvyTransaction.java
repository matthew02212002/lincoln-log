/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.persistence.divvy;

import org.hibernate.Session;
import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.persistence.UnitOfWork;

/**
 * Saves a {@link org.schwiet.LincolnLog.divvy.Divvy}
 * @author sethschwiethale
 */
public class UpdateDivvyTransaction implements UnitOfWork{
    /*
     * Divvy to be saved
     */
    private Divvy divvy;

    public void perform(Session session) {
        session.update(divvy);
    }

    public static UpdateDivvyTransaction getUnitOfWork(Divvy divvy){
        return new UpdateDivvyTransaction(divvy);
    }

    private UpdateDivvyTransaction(){}

    private UpdateDivvyTransaction(Divvy divvy){
        this.divvy = divvy;
    }

}
