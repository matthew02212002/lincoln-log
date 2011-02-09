/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.transaction;

import org.schwiet.LincolnLog.divvy.Divvy;

/**
 *
 * @author sethschwiethale
 */
public class Transaction {
    private double amount;
    private long date;
    private String payee;
    private String memo;
    private Divvy owner;

    private Transaction(){}

    private Transaction(Divvy owner, String memo, String payee, double amount, long date){
        this.amount = amount;
        this.date = date;
        this.owner = owner;
        this.memo = memo;
        this.payee = payee;
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
