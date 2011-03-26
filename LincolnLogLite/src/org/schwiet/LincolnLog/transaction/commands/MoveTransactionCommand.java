/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.transaction.commands;

import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.persistence.PersistenceManager;
import org.schwiet.LincolnLog.transaction.Transaction;
import org.schwiet.LincolnLog.transaction.TransactionTableModel;
import org.schwiet.LincolnLog.ui.command.Command;

/**
 *
 * @author sethschwiethale
 */
public class MoveTransactionCommand extends TransactionCommand{
    private Divvy originalDivvy;
    private Divvy newDivvy;
    private Transaction trans;
    /*
     * not accessible
     */
    private MoveTransactionCommand(){}

    private MoveTransactionCommand(TransactionTableModel tm, Transaction trans, Divvy from, Divvy to){
        this.model = tm;
        this.trans = trans;
        this.originalDivvy = from;
        this.newDivvy = to;
    }

    public void execute() throws Exception {
        if(this.originalDivvy != this.newDivvy){
            this.originalDivvy.removeTransaction(trans);
            this.newDivvy.addTransaction(trans);
            trans.setOwner(newDivvy);
//          now that that worked, update ui
            updateTable();
//          update transaction in Database
            PersistenceManager.updateTransaction(trans);
        }
    }

    public void undo() {
        this.newDivvy.removeTransaction(trans);
        this.originalDivvy.addTransaction(trans);
        trans.setOwner(originalDivvy);
        updateTable();
    }

    public static Command createCommand(TransactionTableModel tm, Transaction trans, Divvy from, Divvy to){
        return new MoveTransactionCommand(tm, trans, from, to);
    }

    public String getCommandName() {
        return "Move Transaction";
    }
}