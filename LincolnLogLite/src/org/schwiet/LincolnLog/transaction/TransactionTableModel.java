/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schwiet.LincolnLog.transaction;

import java.lang.reflect.InvocationTargetException;
import java.util.Vector;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.transaction.commands.ChangeTransactionAmountCommand;
import org.schwiet.LincolnLog.transaction.commands.MoveTransactionCommand;
import org.schwiet.LincolnLog.ui.command.CommandDispatch;

/**
 *
 * @author sethschwiethale
 */
public class TransactionTableModel extends AbstractTableModel {

    Vector<Transaction> transactions = new Vector<Transaction>();
    /**
     * field to hold the amount of entries
     */
    private int rowCount;
    /**
     * Root logger for the TableModels
     */
    static Logger logger = Logger.getLogger(TransactionTableModel.class);
    /**
     * for use with getTransaction
     */
    private Transaction tranReturnee = null;
    /**
     * for use with getValueAt
     */
    private Object returnee = null;

    /**
     * returns the number of entries in the table
     * @return
     */
    public int getRowCount() {
        Runnable r = new Runnable() {

            public void run() {
                rowCount = transactions.size(); //make sure size checking happens in sync with any manipulation
            }
        };
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(r);
            } catch (InterruptedException ex) {
                logger.error("could not get number of rows", ex);
            } catch (InvocationTargetException ex) {
                logger.error("could not get number of rows", ex.getTargetException());
            }
        }
        return rowCount;
    }

    /**
     * adds at MoneyTransaction, <code>trans</code> to the Table Model
     * @param trans
     */
    public void addTransaction(final Transaction trans) {
        try {
            Runnable r = new Runnable() {

                @Override
                public void run() {
                    transactions.addElement(trans);
                    final int index = transactions.size() - 1;
                    fireTableRowsInserted(index, index);
                }
            };

            //make sure this is run off the EDT
            if (SwingUtilities.isEventDispatchThread()) {
                r.run();
            } else {
                SwingUtilities.invokeLater(r);
            }
        } catch (Exception e) {
            logger.error("error adding AP record to table: " + e);
            return;
        }

    }
    /**
     * remove MoneyTransaction, <code>trans</code> from the Table Model
     * @param trans
     */
    public void removeTransaction(final Transaction trans) {
        try {
            Runnable r = new Runnable() {

                @Override
                public void run() {
                    transactions.removeElement(trans);
                    final int index = transactions.size() - 1;
                    fireTableDataChanged();
                }
            };

            //make sure this is run off the EDT
            if (SwingUtilities.isEventDispatchThread()) {
                r.run();
            } else {
                SwingUtilities.invokeLater(r);
            }
        } catch (Exception e) {
            logger.error("error adding AP record to table: " + e);
            return;
        }

    }

    /**
     * clears the Transactions table's list, call this when a new MoneyBin has been selected
     */
    public void clearAll() {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                try {
                    transactions.removeAllElements(); //this method nulls all elements and sets the count to zero
                    logger.debug("Transaction Table has been cleared");
                } catch (Exception e) {
                    logger.error("could not clear Transaction table: " + e);
                }
                fireTableDataChanged();
            }
        };

        //make sure this is run off the EDT
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            SwingUtilities.invokeLater(r);
        }
    }

    /**
     * uses TransactionColumn Enum to get the value at <code>row</code>, <code>column</code>
     * @param row
     * @param column
     * @return
     */
    public Object getValueAt(final int row, final int column) {
        Runnable r = new Runnable() {

            public void run() {
                TransactionColumn col = TransactionTableModel.getColumn(column);

                switch (col) {
                    case DATE:
                        returnee = transactions.get(row).getDate();
                        break;
                    case PAYEE:
                        returnee = transactions.get(row).getPayee();
                        break;
                    case MEMO:
                        returnee = transactions.get(row).getMemo();
                        break;
                    case DIVVY:
                        returnee = transactions.get(row).getOwner().getName(); //TODO:
                        break;
                    case AMOUNT:
                        returnee = transactions.get(row).getAmount();
                        break;
                    default:
                        returnee = "unknown";
                        break;
                }

            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(r);
            } catch (InterruptedException ex) {
                logger.error("could not get number of rows", ex);
            } catch (InvocationTargetException ex) {
                logger.error("could not get number of rows", ex.getTargetException());
            }
        }
        return returnee;
    }

    /**
     *  This empty implementation is provided so users don't have to implement
     *  this method if their data model is not editable.
     *
     *  @param  aValue   value to assign to cell
     *  @param  rowIndex   row of cell
     *  @param  columnIndex  column of cell
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //TODO!!! USE COMMAND PATTERN
        if (!SwingUtilities.isEventDispatchThread()||
                rowIndex >= transactions.size()) {
            logger.error("illegal call to setValueAt: row: "+rowIndex+", size: "+transactions.size());
            return;
        }
        boolean changed = false;
        switch (getColumn(columnIndex)) {
            case DIVVY:
                if (aValue instanceof Divvy &&
                        !aValue.equals(transactions.get(rowIndex).getOwner())) {
                    Transaction trans = transactions.get(rowIndex);
                    CommandDispatch.getInstance().performCommand(
                            MoveTransactionCommand.createCommand(this, trans, trans.getOwner(), (Divvy) aValue));
                }
                break;
            case AMOUNT:
                try {
                    double temp = Double.parseDouble((String) aValue);
                    if (temp != transactions.get(rowIndex).getAmount()) {
                        CommandDispatch.getInstance().performCommand(
                                ChangeTransactionAmountCommand.createCommand(this, transactions.get(rowIndex), temp));
                        changed = true;
                    }
                } catch (Exception e) {
                    logger.error("invalid input for amount field");
                }
            default:
                break;
        }
        if (changed) {
            this.fireTableDataChanged();
        }
    }

    /**
     *  Returns false.  This is the default implementation for all cells.
     *
     *  @param  rowIndex  the row being queried
     *  @param  columnIndex the column being queried
     *  @return false
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        boolean returnee;
        switch (getColumn(columnIndex)) {
            case DIVVY:
                returnee = true;
                break;
            case AMOUNT:
                returnee = true;
                break;
            default:
                returnee = false;
                break;
        }
        return returnee;
    }

    /**
     * returns the Transaction at the index in the Table Model
     * @param row
     * @return
     */
    public Transaction getTransaction(final int row) {
        Runnable r = new Runnable() {

            public void run() {
                if (row >= 0 && row < transactions.size()) {
                    tranReturnee = transactions.get(row);
                } else {
                    logger.error("No such transaction");
                    tranReturnee = null;
                }
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(r);
            } catch (InterruptedException ex) {
                logger.error("could not get value", ex);
            } catch (InvocationTargetException ex) {
                logger.error("could not get value", ex.getTargetException());
            }
        }
        return tranReturnee;
    }

    /**
     * returns the number of columns
     * @return
     */
    public int getColumnCount() {
        return TransactionColumn.values().length;
    }

    /**
     * returns the column header String
     * @param columnIndex
     * @return
     */
    @Override
    public String getColumnName(int columnIndex) {
        return getColumn(columnIndex).getName();
    }

    /**
     * returns the Class
     * @param columnIndex
     * @return
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        return getColumn(columnIndex).getType();
    }

    private static TransactionColumn getColumn(int columnIndex) {
        return TransactionColumn.values()[columnIndex];
    }

    /**
     * ENUM
     */
    public enum TransactionColumn {

        DATE("Date", Long.class),
        PAYEE("Payee", String.class),
        MEMO("Memo", String.class),
        DIVVY("Divvy", String.class),
        AMOUNT("Amount", Double.class);

        private TransactionColumn(String name, Class type) {
            this.name = name;
            this.type = type;
        }
        private String name;
        private Class type;

        public String getName() {
            return name;
        }

        public Class getType() {
            return type;
        }
    }
}
