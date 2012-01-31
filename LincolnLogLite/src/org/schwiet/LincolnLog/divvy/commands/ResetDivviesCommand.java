/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.divvy.commands;

import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.divvy.DivvyManager;
import org.schwiet.LincolnLog.divvy.DivvyUtility.DivvyType;
import org.schwiet.LincolnLog.persistence.PersistenceManager;
import org.schwiet.LincolnLog.ui.command.Command;

/**
 *
 * @author sethschwiethale
 */
public class ResetDivviesCommand implements Command{
    /*
     * not accessible
     */
    private ResetDivviesCommand(){}



    public void execute() throws Exception {

        // reset all the divvies in the UI
        DivvyManager mgr = DivvyManager.getInstance();
        mgr.resetDivvies();

        Divvy[] divs = mgr.getAllDivvies();
        // persist change to Divvy
        for(Divvy div: divs){
            PersistenceManager.updateDivvy(div);
        }
        // now delete the transactions from the database
        PersistenceManager.clearTransactions();
    }

    public void undo() throws Exception {
//        div.setName(oldName);
//        div.setAmount(oldAmount);
//        div.setType(oldType);
//        div.recalculate();
////      persist change to Divvy
//        PersistenceManager.updateDivvy(div);
    }

    public static Command createCommand(){
        return new ResetDivviesCommand();
    }

    public String getCommandName() {
        return "Reset Divvy";
    }
}