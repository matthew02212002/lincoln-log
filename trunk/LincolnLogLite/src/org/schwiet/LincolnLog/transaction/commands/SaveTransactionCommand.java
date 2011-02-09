/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.transaction.commands;

import javax.swing.JList;
import javax.swing.JTable;
import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.transaction.Transaction;
import org.schwiet.LincolnLog.transaction.TransactionTableModel;
import org.schwiet.LincolnLog.ui.command.Command;

/**
 *
 * @author sethschwiethale
 */
public class SaveTransactionCommand extends TransactionCommand{
    private Divvy divvy;
    private Transaction trans;
    /*
     * not accessible
     */
    private SaveTransactionCommand(){}

    private SaveTransactionCommand(JTable table, Transaction trans, Divvy divvy){
        this.trans = trans;
        this.divvy = divvy;
        this.model = (TransactionTableModel) table.getModel();
    }

    public void execute() {
        divvy.addTransaction(trans);
        trans.setOwner(divvy);
        model.addTransaction(trans);
        updateTable();
    }

    public void undo() {
        model.removeTransaction(trans);
        divvy.removeTransaction(trans);
        trans.setOwner(null);
        updateTable();
    }

    public static Command createCommand(JTable table, Transaction trans, Divvy divvy){
        return new SaveTransactionCommand(table, trans, divvy);
    }

    public String getCommandName() {
        return "Save Transaction";
    }
}
