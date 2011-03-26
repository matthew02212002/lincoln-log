/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.transaction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.schwiet.LincolnLog.divvy.Divvy;

/**
 * A Transacion represesnts a money transaction, for practical sense, a receipt
 * @author sethschwiethale
 */
@Entity
public class Transaction {
    private Long id;
    private double amount;
    private long date;
    private String payee;
    private String memo;
    private Divvy owner;

    public Transaction() {
    }

    private Transaction(Divvy owner, String memo, String payee, double amount, long date){
        this.amount = amount;
        this.date = date;
        this.owner = owner;
        this.memo = memo;
        this.payee = payee;
    }

    /**
     * returns the DB index for this {@link org.schwiet.LincolnLog.transaction.Transaction}
     * @return
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @ManyToOne
    @JoinColumn
    public Divvy getOwner() {
        return owner;
    }

    public void setOwner(Divvy owner) {
        this.owner = owner;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public static Transaction getInstance(Divvy owner, String memo, String payee, double amount, long date){
        return new Transaction(owner, memo, payee, amount, date);
    }

}
