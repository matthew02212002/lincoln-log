/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.components;

import info.clearthought.layout.TableLayout;
import org.schwiet.spill.components.PaintedPanel;
import org.schwiet.spill.painter.Painter;

/**
 *
 * @author sethschwiethale
 */
public class BottomBarPanel extends PaintedPanel{
    private static final double[][] DIVS = {{12,24,6,24,110,24,-1},{-1}};
    private static final TableLayout LAYOUT = new TableLayout(DIVS);
    /*
     *
     */
    public static final String ADD_BUTTON = "3,0";
    public static final String REMOVE_BUTTON = "1,0";
    public static final String EDIT_BUTTON = "5,0";

    public BottomBarPanel(Painter painter){
        super(painter);
        this.setLayout(LAYOUT);
    }
}
