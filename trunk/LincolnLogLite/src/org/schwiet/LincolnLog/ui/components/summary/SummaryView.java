/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.components.summary;

import javax.swing.JPanel;

/**
 * provides a JPanel to be displayed in the {@link SummaryDisplay} all layout
 * and child components are up to the implementation
 * @author sethschwiethale
 */
public interface SummaryView {

    /**
     * returns the panel that will be displayed
     * @return
     */
    public JPanel getView();
}
