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
    private static final double[][] DIVS = {{-1, 8, 24, 8, 24, 8},{-1}};
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
        add(cancelButton, "2,0");
        add(moreButton, "4,0");
    }

    /**
     * sets up the component such that it contains the back button and an add
     * or more button that should apply some values and the reset, not leaving
     * the current view
     */
    public void setUpAddView(){
        this.removeAll();
        this.add(cancelButton, "2,0");
        this.add(moreButton, "4,0");
        this.revalidate();
    }
    /**
     * sets up the component such that it contains the back button and the apply
     * or confirmation button
     */
    public void setUpChangeView(){
        this.removeAll();
        this.add(cancelButton, "2,0");
        this.add(confirmButton, "4,0");
        this.revalidate();
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
