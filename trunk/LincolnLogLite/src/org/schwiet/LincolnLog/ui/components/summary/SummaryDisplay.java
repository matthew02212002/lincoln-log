/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.components.summary;

import org.schwiet.LincolnLog.ui.components.summary.SummaryView;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 * This class is responsible for displaying information to the user based on
 * publication type and information provided. It will handle transtions and timing
 * <p>
 * This class will be a listener to any views.
 * when a view should be displayed or queued to be displayed, it will fire an event
 * which this class will handle, determining whether to display immediately or
 * queue to show as part of a timed cycle
 * </p>
 * @author sethschwiethale
 */
public class SummaryDisplay {
    /**
     * contains the current view
     */
    private JPanel faderPanel = new JPanel();

    private static final SummaryDisplay INSTANCE = new SummaryDisplay();

    /**
     * causes the display to show the passed view
     * @param view
     */
    public void submitView(SummaryView view){
        //TODO: timer fade-out
        faderPanel.removeAll();
        faderPanel.add(view.getView());
        faderPanel.revalidate();
        //TODO: timer fade-in
    }

    /**
     * sets the layout of the passed JPanel and adds the internal container to it
     * @param container
     */
    private void installDisplay(JPanel container){
        container.setLayout(new BorderLayout());
        container.add(faderPanel, BorderLayout.CENTER);
    }
    /**
     * retunrs an instance of summary display which will install it's own container
     * into the passed JPanel
     * @param container
     * @return
     */
    public static SummaryDisplay Initialize(JPanel container){
        INSTANCE.installDisplay(container);
        return INSTANCE;
    }
    /**
     * non-accessible
     */
    private SummaryDisplay(){
        this.faderPanel.setOpaque(false);
    }
}
