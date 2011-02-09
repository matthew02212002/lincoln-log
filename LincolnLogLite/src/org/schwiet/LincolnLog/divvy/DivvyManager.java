/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schwiet.LincolnLog.divvy;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;
import org.schwiet.LincolnLog.divvy.DivvyUtility.DivvyType;
import org.schwiet.LincolnLog.transaction.AmountColumnEditorFilter;
import org.schwiet.LincolnLog.transaction.Transaction;
import org.schwiet.LincolnLog.transaction.TransactionTableModel;
import org.schwiet.LincolnLog.transaction.TransactionTableModel.TransactionColumn;

/**
 *
 * @author sethschwiethale
 */
public class DivvyManager implements ListSelectionListener {

    private static final DivvyManager INSTANCE = new DivvyManager();
    private TransactionTableModel transactionModel = new TransactionTableModel();
    private ListSelectionModel divvySelectionModel;
    /*
     * transaction table cell renderers
     */
    private JComboBox divvyColumnEditor = new JComboBox();
    private JTextField amountColumnEditor = new JTextField();
    /*
     * unmodifiable
     */

    private DivvyManager() {
        amountColumnEditor.setHorizontalAlignment(JTextField.RIGHT);
        ((AbstractDocument)amountColumnEditor.getDocument()).setDocumentFilter(new AmountColumnEditorFilter(amountColumnEditor));
        /*
         * TODO: load from DB
         */
        Divvy groc = new Divvy("Groceries", 250.00, DivvyType.EXPENSE);
        addDivvy(groc);
        addDivvy(new Divvy("Restaraunts", 250.00, DivvyType.EXPENSE));
        addDivvy(new Divvy("Gas", 250.00, DivvyType.EXPENSE));
        addDivvy(new Divvy("Utilities", 250.00, DivvyType.EXPENSE));
        groc.addTransaction(Transaction.getInstance(groc, "test 1", "safeway", 32.00, System.currentTimeMillis()));
    }
    /*
     * 
     */
    private DefaultListModel data = new DefaultListModel();

    /**
     *
     * @return
     */
    public void installListModel(JList list) {
        list.setModel(data);
        divvySelectionModel = list.getSelectionModel();
        list.addListSelectionListener(this);
    }

    /**
     * adds {@link div} to the underlying data-model
     * @param div
     */
    public void addDivvy(final Divvy div) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                data.addElement(div);
                divvyColumnEditor.addItem(div);
            }
        });
    }

    /**
     * removes {@link div} to the underlying data-model
     * @param div
     */
    public void removeDivvy(final Divvy div) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                data.removeElement(div);
                divvyColumnEditor.removeItem(div);
            }
        });

    }

    /**
     * give a table the {@code DivvyManager}'s transaction table model
     * @param table
     */
    public void installTransactionTableModel(JTable table) {
        table.setModel(transactionModel);
        /*
         * add cell-editors
         */
        table.getColumn(TransactionColumn.DIVVY.getName()).setCellEditor(new DefaultCellEditor(divvyColumnEditor));
        table.getColumn(TransactionColumn.AMOUNT.getName()).setCellEditor(new DefaultCellEditor(amountColumnEditor));
    }

    /**
     * returns static instance of {@code DivvyManager}
     * @return
     */
    public static DivvyManager getInstance() {
        return INSTANCE;
    }

    /**
     * for new transaction added straight to table by UI
     */
    public void generateTransaction() {
        if (divvySelectionModel != null) {
            int x = divvySelectionModel.getMinSelectionIndex();
            Divvy div = (Divvy) data.getElementAt(x);
            div.addTransaction(Transaction.getInstance(div, "new memo", "payee", 0.0, System.currentTimeMillis()));
        }
    }

    /**
     * listens for changes in selection of the Divvy List in the main UI
     * @param e
     */
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()
                && divvySelectionModel == ((JList) e.getSource()).getSelectionModel()) {
            int a = e.getFirstIndex();
            int b = e.getLastIndex();
            transactionModel.clearAll();
            for (int i = a; i <= b; i++) {
                if (divvySelectionModel.isSelectedIndex(i)) {
                    for (Transaction t : ((Divvy) data.getElementAt(i)).getTransactions()) {
                        transactionModel.addTransaction(t);
                    }
                }
            }
        }
    }
}
