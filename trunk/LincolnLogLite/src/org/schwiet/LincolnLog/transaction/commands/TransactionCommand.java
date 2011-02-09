/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.transaction.commands;

import javax.swing.SwingUtilities;
import org.schwiet.LincolnLog.transaction.TransactionTableModel;
import org.schwiet.LincolnLog.ui.command.Command;

/**
 *
 * @author sethschwiethale
 */
public abstract class TransactionCommand implements Command{
    protected TransactionTableModel model;
    
    protected void updateTable(){
        SwingUtilities.invokeLater(new Runnable(){

            public void run() {
                model.fireTableDataChanged();
            }

        });

    }

}
