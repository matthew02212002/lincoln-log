/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.components;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.schwiet.LincolnLog.transaction.TransactionTableModel;
import org.schwiet.LincolnLog.ui.delegates.AmountCellRenderer;
import org.schwiet.LincolnLog.ui.delegates.DateCellRenderer;
import org.schwiet.LincolnLog.ui.delegates.DivvyCellRenderer;
import org.schwiet.LincolnLog.ui.delegates.MemoCellRenderer;
import org.schwiet.LincolnLog.ui.delegates.PayeeCellRenderer;
import org.schwiet.LincolnLog.ui.utils.TableUtils;

/**
 *
 * @author sethschwiethale
 */
public class TransactionTable extends JTable{

    private TableCellRenderer amountRenderer = new AmountCellRenderer(),
            divvyRenderer = new DivvyCellRenderer(),
            memoRenderer = new MemoCellRenderer(),
            dateRenderer = new DateCellRenderer(),
            payeeRenderer = new PayeeCellRenderer();

    public TableCellRenderer getCellRenderer(int row, int column) {
        switch(TransactionTableModel.getColumn(this.convertColumnIndexToModel(column))){
            case AMOUNT:
                return amountRenderer;
            case MEMO:
                return memoRenderer;
            case DATE:
                return dateRenderer;
            case PAYEE:
                return payeeRenderer;
            case DIVVY:
                return divvyRenderer;
            default:
                return super.getCellRenderer(row, column);
        }
    }
}
