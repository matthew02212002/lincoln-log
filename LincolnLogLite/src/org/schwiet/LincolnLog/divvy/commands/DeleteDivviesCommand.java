/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.divvy.commands;

import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.divvy.DivvyManager;
import org.schwiet.LincolnLog.persistence.PersistenceManager;
import org.schwiet.LincolnLog.ui.command.Command;

/**
 *
 * @author sethschwiethale
 */
public class DeleteDivviesCommand implements Command{
    private DivvyManager manager;
    private Object[] divvies;
    /*
     * not accessible
     */
    private DeleteDivviesCommand(){}

    private DeleteDivviesCommand(DivvyManager manager, Object[] divvies){
        this.manager = manager;
        this.divvies = divvies;
    }

    public void execute() throws Exception {
        for(Object div: divvies){
            if(div instanceof Divvy){
                manager.removeDivvy((Divvy)div);
            }
        }
//      persist changes
        PersistenceManager.deleteDivvies(divvies);
    }

    public void undo() throws Exception {
        for(Object div: divvies){
            if(div instanceof Divvy){
                manager.addDivvy((Divvy)div);
            }
        }
        //      persist changes
        PersistenceManager.saveDivvies(divvies);
    }

    public static Command createCommand(DivvyManager manager, Object[] divvy){
        return new DeleteDivviesCommand(manager, divvy);
    }

    public static Command createCommand(Object[] divvies){
        return new DeleteDivviesCommand(DivvyManager.getInstance(), divvies);
    }

    public String getCommandName() {
        return "Delete Divvies";
    }
}