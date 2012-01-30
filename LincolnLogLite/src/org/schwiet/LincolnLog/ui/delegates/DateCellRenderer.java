/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schwiet.LincolnLog.ui.delegates;

import info.clearthought.layout.TableLayout;
import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.schwiet.LincolnLog.divvy.DivvyUtility;
import org.schwiet.spill.plaf.IndentLabelUI;

/**
 *
 * @author sethschwiethale
 */
public class DateCellRenderer extends JPanel implements TableCellRenderer {

    private JLabel label = new JLabel();
    private final double[][] DIVS = {{3, -1, 3}, {5, -1, 5}};
    //for displaying date
    private DateFormat dateFormat;

    private final static Color LITE = Color.WHITE,
            DARK = new Color(30,30,30);

    private IndentLabelUI ui = new IndentLabelUI(DARK, Color.GRAY, LITE);

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if(isSelected){
            ui.setLook(IndentLabelUI.IndentDirection.DOWN, DARK, LITE);
        }
        else{
            ui.setLook(IndentLabelUI.IndentDirection.UP, LITE, DARK);
        }
        label.setText(dateFormat.format(new Date((Long)value)));
        return this;
    }

    public DateCellRenderer() {
        this.setOpaque(false);

        label.setUI(ui);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setFont(DivvyUtility.getInstance().getNumberFont().deriveFont(12.0f));

        dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        //layout
        this.setLayout(new TableLayout(DIVS));
        this.add(label, "1,1");
    }
}
