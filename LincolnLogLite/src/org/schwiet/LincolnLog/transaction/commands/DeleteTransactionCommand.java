/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.transaction.commands;

import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.transaction.Transaction;
import org.schwiet.LincolnLog.ui.command.Command;

/**
 *
 * @author sethschwiethale
 */
public class DeleteTransactionCommand implements Command{
    private Divvy divvy;
    private Transaction trans;
    /*
     * not accessible
     */
    private DeleteTransactionCommand(){}

    private DeleteTransactionCommand(Transaction trans){
        this.trans = trans;
    }

    public void execute() {
        divvy = trans.getOwner();
        if(divvy != null){
            divvy.removeTransaction(trans);
        }
        // TODO: need to remove from database also and from table model...
    }

    public void undo() {
        divvy.addTransaction(trans);
    }

    public static Command createCommand(Transaction trans){
        return new DeleteTransactionCommand(trans);
    }

    public String getCommandName() {
        return "Delete Transaction";
    }
}
