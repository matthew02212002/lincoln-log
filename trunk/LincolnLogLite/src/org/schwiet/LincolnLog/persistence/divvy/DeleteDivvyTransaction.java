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
public class DeleteDivvyTransaction implements UnitOfWork{
    /*
     * Divvy to be saved
     */
    private Divvy divvy;

    public void perform(Session session) {
        session.delete(divvy);
    }

    public static DeleteDivvyTransaction getUnitOfWork(Divvy divvy){
        return new DeleteDivvyTransaction(divvy);
    }

    private DeleteDivvyTransaction(){}

    private DeleteDivvyTransaction(Divvy divvy){
        this.divvy = divvy;
    }

}
