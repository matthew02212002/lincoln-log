/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.utils;

/**
 *
 * @author sethschwiethale
 */
public interface ViewSetupManager {

    public void handleViewRequest(View requestedView);

    public void setupNewDivvyView();

    public void setupTransactionView();

    /*
     * 
     */
    public enum View{
        WELCOME("Welcom Message"),
        NEW_DIVVY("Create a new Divvy"),
        EDIT_DIVVY("Edit a Divvy"),
        TRANSACTION("Transaction Listing");

        private String title;

        private View(String title){
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

    }

}
