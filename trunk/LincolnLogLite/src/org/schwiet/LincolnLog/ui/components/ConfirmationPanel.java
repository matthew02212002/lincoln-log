/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.components;

import info.clearthought.layout.TableLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.schwiet.LincolnLog.ui.painters.ToolbarPainter;
import org.schwiet.spill.components.PaintedPanel;

/**
 *
 * @author sethschwiethale
 */
public class ConfirmationPanel extends PaintedPanel{
    private static final double[][] DIVS = {{8, 24, -1, 24, 8, 24, 8},{-1}};
    private JButton confirmButton,
                    cancelButton,
                    moreButton;

    private ConfirmationPanel(){
        super();
        this.setLayout(new TableLayout(DIVS));
        this.setBackgroundPainter(new ToolbarPainter());
        /*
         * create buttons
         */
        confirmButton = ComponentFactory.getIconButton(getClass().getResource("/resources/done_16.png"));
        cancelButton = ComponentFactory.getIconButton(getClass().getResource("/resources/back_16.png"));
        moreButton = ComponentFactory.getIconButton(getClass().getResource("/resources/add_16.png"));
        /*
         * add buttons
         */
        add(confirmButton, "5,0");
        add(cancelButton, "1,0");
        add(moreButton, "3,0");
    }

    public void addConfirmListener(ActionListener l){
        confirmButton.addActionListener(l);
    }

    public void addCancelListener(ActionListener l){
        cancelButton.addActionListener(l);
    }

    public void addMoreListener(ActionListener l){
        moreButton.addActionListener(l);
    }

    public static ConfirmationPanel getInstance(){
        return new ConfirmationPanel();
    }
}
