/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.persistence;

import org.hibernate.Session;

/**
 * represents a series of operations to carry out against the database together
 * Implementing classes should have one {@link org.hibernate.Session} which it
 * uses to interact with the database
 * @author sethschwiethale
 */
public interface UnitOfWork {

    /**
     * method to perform
     */
    public void perform(Session session);

}
