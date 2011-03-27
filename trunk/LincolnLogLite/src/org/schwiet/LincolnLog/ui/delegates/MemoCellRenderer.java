/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.delegates;

import info.clearthought.layout.TableLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.schwiet.LincolnLog.divvy.DivvyUtility;
import org.schwiet.spill.plaf.IndentLabelUI;

/**
 * Similar to the other cell renderers. I'm giving each column their own
 * renderer, in case I want to add anything to any of them later
 * @author sethschwiethale
 */
public class MemoCellRenderer extends JPanel implements TableCellRenderer{

    private JLabel label = new JLabel();

    private final double[][] DIVS= {{3,-1,3},{5,-1,5}};

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        label.setText(value.toString());
        return this;
    }

    public MemoCellRenderer(){
        this.setOpaque(false);

        label.setUI(new IndentLabelUI(new Color(30,30,30), Color.GRAY, Color.WHITE));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setFont(DivvyUtility.getInstance().getNumberFont().deriveFont(11.0f));
        //layout
        this.setLayout(new TableLayout(DIVS));
        this.add(label, "1,1");
    }

}
