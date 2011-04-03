/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.divvy.commands;

import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.divvy.DivvyUtility.DivvyType;
import org.schwiet.LincolnLog.persistence.PersistenceManager;
import org.schwiet.LincolnLog.transaction.Transaction;
import org.schwiet.LincolnLog.transaction.TransactionTableModel;
import org.schwiet.LincolnLog.ui.command.Command;

/**
 *
 * @author sethschwiethale
 */
public class ChangeDivvyValsCommand implements Command{
    private Divvy div;
    double newAmount, oldAmount;
    String newName, oldName;
    DivvyType newType, oldType;
    /*
     * not accessible
     */
    private ChangeDivvyValsCommand(){}

    private ChangeDivvyValsCommand(Divvy div, String newName, double newAmnt, DivvyType newType){
        this.div = div;
        this.newAmount = newAmnt;
        this.oldAmount = div.getAmount();

        this.newName = newName;
        this.oldName = div.getName();

        this.newType = newType;
        this.oldType = div.getType();
    }

    public void execute() throws Exception {
        div.setName(newName);
        div.setAmount(newAmount);
        div.setType(newType);
        div.recalculate();
//      persist change to Divvy
        PersistenceManager.updateDivvy(div);
    }

    public void undo() throws Exception {
        div.setName(oldName);
        div.setAmount(oldAmount);
        div.setType(oldType);
        div.recalculate();
//      persist change to Divvy
        PersistenceManager.updateDivvy(div);
    }

    public static Command createCommand(Divvy div, String newName, double newAmnt, DivvyType newType){
        return new ChangeDivvyValsCommand(div, newName, newAmnt, newType);
    }

    public String getCommandName() {
        return "Edit Divvy";
    }
}