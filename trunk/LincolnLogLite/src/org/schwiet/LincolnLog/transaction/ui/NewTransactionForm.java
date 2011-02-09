/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schwiet.LincolnLog.transaction.ui;

import info.clearthought.layout.TableLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;
import org.apache.log4j.Logger;
import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.transaction.AmountColumnEditorFilter;
import org.schwiet.LincolnLog.transaction.Transaction;
import org.schwiet.LincolnLog.transaction.commands.SaveTransactionCommand;
import org.schwiet.LincolnLog.ui.command.CommandDispatch;
import org.schwiet.LincolnLog.ui.components.ComponentFactory;
import org.schwiet.LincolnLog.ui.painters.TransFormPainter;
import org.schwiet.spill.components.PaintedPanel;
import org.schwiet.spill.plaf.IndentLabelUI;

/**
 * Singleton Graphical component used for receiving user-input and creating new
 * {@link Transaction}s
 * @author sethschwiethale
 */
public class NewTransactionForm extends PaintedPanel implements ListSelectionListener,
        ActionListener {

    private static final double[][] DIVS = {{8, 110, -1, 5, 12, 5, 75, 5, 15, 5, 75, 5, 24, 8}, {4, -1, 4}};
    /*
     * JLabels
     */
    private JLabel titleLabel,
            signLabel,
            payeeLabel;
    private JButton addButton;
    private JTextField amountField = new JTextField(),
            payeeField = new JTextField();
    private Divvy divvyRef = null;
    private JTable tableRef = null;
    /**
     * logger for Transaction form
     */
    static Logger logger = Logger.getLogger(NewTransactionForm.class);
    /*
     * non-accessible
     */
    private NewTransactionForm() {
        super();
        this.setLayout(new TableLayout(DIVS));
        this.setBackgroundPainter(new TransFormPainter());
        createComponents();
    }

    public static NewTransactionForm getInstance() {
        return new NewTransactionForm();
    }

    private void createComponents() {
        addButton = ComponentFactory.getIconButton(getClass().getResource("/resources/add_Lite_16.png"));
        titleLabel = new JLabel("New Transaction");
        signLabel = new JLabel("$");
        payeeLabel = new JLabel("to");
        //
        signLabel.setHorizontalAlignment(JLabel.RIGHT);
        payeeLabel.setHorizontalAlignment(JLabel.CENTER);
        amountField.setHorizontalAlignment(JLabel.RIGHT);
        //
        titleLabel.setUI(new IndentLabelUI());
        signLabel.setUI(new IndentLabelUI());
        payeeLabel.setUI(new IndentLabelUI());
        //
        this.add(titleLabel, "1,0,1,2");
        this.add(signLabel, "4,0,4,2");
        this.add(amountField, "6,1");
        this.add(payeeLabel, "8,0,8,2");
        this.add(payeeField, "10,1");
        this.add(addButton, "12,0,12,2");
        /*
         * blocks input other than digits and a dicimal in the right place on
         * amountField
         */
        ((AbstractDocument) amountField.getDocument()).setDocumentFilter(new AmountColumnEditorFilter(amountField));
        /*
         * addButton is only enabled if there is a Divvy Selected
         */
        addButton.setEnabled(false);
        addButton.addActionListener(this);
        amountField.addActionListener(this);
        payeeField.addActionListener(this);
    }

    /**
     * sets a JList as the determinate factor for which Divvy to add the transaction
     * to. We also save a reference to the list so we can use it with our
     * SaveTransactionCommand
     * @param list
     */
    public void installDivvySelector(JList list, JTable table) {
        list.addListSelectionListener(this);
        this.tableRef = table;
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()
                && e.getSource() instanceof JList) {
            try {
                Divvy div = (Divvy) ((JList) e.getSource()).getSelectedValue();
                if (div != null) {
                    divvyRef = div;
                    addButton.setEnabled(true);
                } else {
                    addButton.setEnabled(false);
                }
            } catch (Exception ex) {
                logger.error("error getting Divvy Selection: " + ex);
            }
        }
    }

    private double convertAmount() throws NumberFormatException {
        return Double.parseDouble(amountField.getText());
    }

    public void actionPerformed(ActionEvent e) {
        if (!addButton.isEnabled()) {
            return;
        }
        if (e.getSource().equals(addButton)
                || e.getSource().equals(payeeField)
                || e.getSource().equals(amountField)) {
            try{
            Transaction toSave = Transaction.getInstance(divvyRef, "-", payeeField.getText(), convertAmount(), System.currentTimeMillis());
            CommandDispatch.getInstance().performCommand(SaveTransactionCommand.createCommand(tableRef, toSave, divvyRef));
            }catch(NumberFormatException ex){
                logger.error("could not add transaction, amount field not formatted correctly");
            }

        }
    }
}
