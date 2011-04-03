/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schwiet.LincolnLog.divvy;

import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import org.apache.log4j.Logger;
import org.schwiet.LincolnLog.persistence.PersistenceManager;
import org.schwiet.LincolnLog.transaction.AmountColumnEditorFilter;
import org.schwiet.LincolnLog.transaction.Transaction;
import org.schwiet.LincolnLog.transaction.TransactionTableModel;
import org.schwiet.LincolnLog.transaction.TransactionTableModel.TransactionColumn;
import org.schwiet.LincolnLog.ui.command.CommandDispatch;

/**
 *
 * @author sethschwiethale
 */
public class DivvyManager implements ListSelectionListener {

    private static final DivvyManager INSTANCE = new DivvyManager();
    private TransactionTableModel transactionModel = new TransactionTableModel();
    private ListSelectionModel divvySelectionModel;
    private TableRowSorter<TableModel> sorter;
    private List<RowFilter<Object, Object>> selectionFilters = new LinkedList<RowFilter<Object, Object>>();
    /*
     * transaction table cell renderers
     */
    private JComboBox divvyColumnEditor = new JComboBox();
    private JTextField amountColumnEditor = new JTextField();
    private static final Logger logger = Logger.getLogger(DivvyManager.class);
    /*
     * unmodifiable
     */

    private DivvyManager() {
        //row sorter used on table
        sorter = new TableRowSorter<TableModel>(transactionModel);
        amountColumnEditor.setHorizontalAlignment(JTextField.RIGHT);
        ((AbstractDocument) amountColumnEditor.getDocument()).setDocumentFilter(new AmountColumnEditorFilter(amountColumnEditor));
        /*
         * upon instantiation, load any divvies from the DB
         */
        Runnable loadingTask = new Runnable() {

            public void run() {
                try {
                    PersistenceManager.loadDivvies(INSTANCE);
                } catch (Exception ex) {
                    logger.error("could not load the Divvies");
                }
            }
        };
        //run the loading on CommandDispatch's worker thread
        CommandDispatch.getInstance().performRunnable(loadingTask);
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
     * should only be called at the beginning of the application
     * @param storedDivvies
     */
    public void initialize(List<Divvy> storedDivvies) {
        for (Divvy div : storedDivvies) {
            addDivvy(div);
        }
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
        transactionModel.addTransactions(div.getTransactions());
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
        transactionModel.removeTransactions(div.getTransactions());
    }

    /**
     * give a table the {@code DivvyManager}'s transaction table model
     * @param table
     */
    public void installTransactionTableModel(JTable table) {
        table.setModel(transactionModel);
        table.setRowSorter(sorter);
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
            selectionFilters.clear();
            for (int i = 0; i < data.size(); i++) {
                if (divvySelectionModel.isSelectedIndex(i)) {
                    //Probably need to eventually 
                    selectionFilters.add(RowFilter.regexFilter(((Divvy) data.getElementAt(i)).getName()));
                }
            }
            //if there are any Divvies selected, make their names the transaction
            //tablefilter
            if (selectionFilters.size() > 0) {
                sorter.setRowFilter(RowFilter.orFilter(selectionFilters));
            } else {//setting to null is like removing filter
                sorter.setRowFilter(null);
            }
        }
    }
}
