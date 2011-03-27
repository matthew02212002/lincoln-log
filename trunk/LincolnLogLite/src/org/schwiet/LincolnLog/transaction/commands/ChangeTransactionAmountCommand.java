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
public class ChangeTransactionAmountCommand extends TransactionCommand{
    private Transaction trans;
    double newAmount, oldAmount;
    /*
     * not accessible
     */
    private ChangeTransactionAmountCommand(){}

    private ChangeTransactionAmountCommand(TransactionTableModel tm, Transaction trans, double amount){
        this.model = tm;
        this.trans = trans;
        this.newAmount = amount;
        this.oldAmount = trans.getAmount();
    }

    public void execute() throws Exception {
        Divvy divvy = trans.getOwner();
        trans.setAmount(newAmount);
        if(divvy != null){
            divvy.recalculate();
        }
        updateTable();
//      persist change to Transaction
        PersistenceManager.updateTransaction(trans);
    }

    public void undo() throws Exception {
        Divvy divvy = trans.getOwner();
        trans.setAmount(oldAmount);
        if(divvy != null){
            divvy.recalculate();
        }
        updateTable();
//      persist change to Transaction
        PersistenceManager.updateTransaction(trans);
    }

    public static Command createCommand(TransactionTableModel tm, Transaction trans, double newAmnt){
        return new ChangeTransactionAmountCommand(tm, trans, newAmnt);
    }

    public String getCommandName() {
        return "Edit Transaction";
    }
}