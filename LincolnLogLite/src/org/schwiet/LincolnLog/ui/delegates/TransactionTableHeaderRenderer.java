/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.delegates;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author sethschwiethale
 */
public class TransactionTableHeaderRenderer extends JLabel implements TableCellRenderer{
    private JTable tableRef;

    public TransactionTableHeaderRenderer(JTable table){
        tableRef = table;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
