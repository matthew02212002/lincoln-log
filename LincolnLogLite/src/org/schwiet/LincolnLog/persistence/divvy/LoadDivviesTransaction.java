/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.persistence.divvy;

import java.util.List;
import org.hibernate.Session;
import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.divvy.DivvyManager;
import org.schwiet.LincolnLog.persistence.UnitOfWork;

/**
 * Used at the beginning of the application to load all the Divvies from the
 * database into the {@link org.schwiet.LincolnLog.divvy.DivvyManager}
 * @author sethschwiethale
 */
public class LoadDivviesTransaction implements UnitOfWork{

    private DivvyManager manager;

    public void perform(Session session) {
        List<Divvy> list = (List<Divvy>)session.createQuery("from Divvy").list();
        manager.initialize(list);
    }

    public static LoadDivviesTransaction getUnitOfWork(DivvyManager manager){
        return new LoadDivviesTransaction(manager);
    }

    private LoadDivviesTransaction(){}

    private LoadDivviesTransaction(DivvyManager manager){
        this.manager = manager;
    }

}
