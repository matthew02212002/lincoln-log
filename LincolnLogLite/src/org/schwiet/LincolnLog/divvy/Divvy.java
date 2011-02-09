/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.divvy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.schwiet.LincolnLog.divvy.DivvyUtility.DivvyType;
import org.schwiet.LincolnLog.transaction.Transaction;
import org.schwiet.LincolnLog.ui.painters.GlassPainter;

/**
 * Represents a "budgeted item".
 * This class is characterized by:
 * a user-specified name,
 * a budget "amount"
 * a type, which determines some behaviors
 * a list of transactions, which are displayed as deductions from the budget amount
 *
 * additionally, the class provides a Component that is used by a ListUI to
 * render a Divvy in a List
 * @author sethschwiethale
 */
public class Divvy {
    private Long id;
    private String name;
    private double amount;
    private DivvyType type;
    private Set<Transaction> transactions = new HashSet<Transaction>();

    /*
     * non-persistent
     */
    DivvyPanel panel;
    /**
     * for Hibernate
     */
    public Divvy(){}
    
    /**
     * default constructor for persistence
     */
    public Divvy(String name, double amount, DivvyType type){
        this.name = name;
        this.amount = amount;
        this.type = type;
        panel = DivvyUtility.getDivvyCellRenderer(name, amount, amount);
    }
    /**
     * returns the DB index for this {@link Divvy}
     * @return
     */
    public Long getId() {
        return id;
    }
    /**
     * only for use by Hibernate, don't call this
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * returns the "base" amount for this {@link Divvy}. This is the amount allocated to
     * the {@link Divvy} and does not reflect any {@link Transaction}s added
     * to this instance
     * @return
     */
    public double getAmount() {
        return amount;
    }
    /**
     * set's the allowance or "base" amount for this {@link Divvy}
     * @param amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
    /**
     * returns the {@link DivvyType} of this {@link Divvy} determining some of
     * the behaviors of the {@link Divvy}
     * @see DivvyUtility
     * @return
     */
    public DivvyType getType() {
        return type;
    }
    /**
     * sets the {@link DivvyType} of this {@link Divvy} determining some of
     * the behaviors of the {@link Divvy}
     * @see DivvyUtility
     * @param type
     */
    public void setType(DivvyType type) {
        this.type = type;
    }
    /**
     * returns the name for the User to identify this divvy in the UI
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * User specified name for this {@link Divvy}
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * adds a {@link Transaction} to this {@link Divvy}. This does not change
     * the result of calling {@link #getAmount()}, but does effect the "amount left"
     * shown in the UI
     * NOTE: This method should be used in a transaction Command, so it may be undone 
     * and persisted
     * @param t
     * @return
     */
    public boolean addTransaction(Transaction t){
        boolean returnee = transactions.add(t);
        if(returnee){
            recalculate();
        }
        return returnee;
    }
    /**
     * removes the given {@link Transaction} from this {@link Divvy}. This does not change
     * the result of calling {@link #getAmount()}, but does effect the "amount left"
     * shown in the UI
     * NOTE: This method should be used in a transaction Command, so it may be undone
     * and persisted
     * @param t
     * @return
     */
    public boolean removeTransaction(Transaction t){
        boolean returnee =  transactions.remove(t);
        if(returnee){
            recalculate();
        }
        return returnee;
    }
    /**
     * clears all {@link Transaction}s from this divvy. This does not change
     * the result of calling {@link #getAmount()}, but does effect the "amount left"
     * shown in the UI
     * NOTE: This method should be used in a transaction Command, so it may be undone
     * and persisted
     */
    public void removeAllTransaction(){
        transactions.clear();
    }
    /**
     * returns an immutable copy of this {@link Divvy}'s {@link Transactions}
     * @return
     */
    public Set<Transaction> getTransactions(){
        return Collections.unmodifiableSet(transactions);
    }
    /**
     * for use with ListUI
     * @param isSelected
     * @param hasFocus
     * @return
     */
    public JPanel getCellRenderer(boolean isSelected, boolean hasFocus) {
        ((GlassPainter)panel.getBackgroundPainter()).setDerpressed(isSelected);
        return panel;
    }
    /**
     * returns the user-specified "name" of this divvy
     * @return
     */
    @Override
    public String toString(){
        return this.name;
    }
    /**
     * overkill method which causes the "remaining" of amount "left" to be recalculated
     * anytime the transactions or amount is changed
     */
    public void recalculate() {
        double left = amount;
        for(Transaction trans: transactions){
            left -= trans.getAmount();
        }
        final double remaining = left;
        SwingUtilities.invokeLater(new Runnable(){

            public void run() {
                panel.setRemaining(remaining);
            }

        });
    }
    
}
