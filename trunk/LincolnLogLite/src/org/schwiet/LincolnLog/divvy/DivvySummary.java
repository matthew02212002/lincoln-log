/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.divvy;

import info.clearthought.layout.TableLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.schwiet.LincolnLog.ui.components.summary.SummaryView;
import org.schwiet.LincolnLog.ui.delegates.GlowLabelUI;

/**
 *
 * @author sethschwiethale
 */
public class DivvySummary implements SummaryView{
    /*
     * contains all the components comprising this view
     */
    private JPanel container = new JPanel();

    private JLabel titleLabel = new JLabel("1/200");

    private static final DivvySummary INSTANCE = new DivvySummary();

    public JPanel getView() {
        return container;
    }

    public static DivvySummary getInstance(){
        return INSTANCE;
    }

    private DivvySummary(){
        double[][] divs = {{-1},{-1}};
        container.setLayout(new TableLayout(divs));
        container.setOpaque(false);
        //add components
        container.add(titleLabel, "0, 0");

        //set look
        titleLabel.setUI(new GlowLabelUI(new Color(187, 203, 255), Color.GRAY, new Color(15,154,248, 35)));
        titleLabel.setVerticalAlignment(JLabel.BOTTOM);
        titleLabel.setFont(DivvyUtility.getInstance().getNumberFont().deriveFont(12.0f));
    }

}
