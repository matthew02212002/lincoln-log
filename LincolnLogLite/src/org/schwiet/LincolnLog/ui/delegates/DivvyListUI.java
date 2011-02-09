/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schwiet.LincolnLog.ui.delegates;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicListUI;
import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.divvy.DivvyPanel;
import org.schwiet.LincolnLog.divvy.DivvyUtility;

/**
 *
 * @author sethschwiethale
 */
public class DivvyListUI extends BasicListUI {
    private BufferedImage buffer;
    /*
     * colors
     */
    private final Color CENTER = new Color (0,0,0,100);
    private final Color OUTER = new Color (0,0,0, 150);
    private final Color DARK_EMPTY = new Color(0,0,0,0);
    private final Color DARK_TRANS = new Color(0,0,0,100);
    private final Color LITE_TRANS = new Color(90, 95, 108, 100);

    private final Color[] COLORS = {DARK_EMPTY, CENTER, OUTER};
    private final Color[] COLORS2 = {LITE_TRANS, DARK_EMPTY};

    private final float[] RATIOS = {0.0f, .5f, .95f};
    private final float[] RATIOS2 = {0.0f, 1.0f};
    /*
     *
     */
    private LinearGradientPaint gradient;
    private RadialGradientPaint top_glow;
    private GradientPaint right, bottom;

    public DivvyListUI(){
        super();
    }
    public DivvyListUI(JList list){
        this();
        list.setCellRenderer(new DivvyPanel("", 0, 0));
    }

    /**
     * Paint the rows that intersect the Graphics objects clipRect.  This
     * method calls paintCell as necessary.  Subclasses
     * may want to override these methods.
     *
     * @see #paintCell
     */
    @Override
    public void paint(Graphics g, JComponent c) {
        paintBackground(g, c);
        super.paint(g, c);
//        System.err.println("Painting List");
    }

    private void paintBackground(Graphics g, JComponent c) {
        /*
         * recreate buffer if necessary
         */
        if (buffer == null || buffer.getWidth() != c.getWidth() || buffer.getHeight() != c.getHeight()) {
            buffer = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buffer.createGraphics();
            /*
             * image tile
             */
            if (DivvyUtility.getInstance().getDivvyListTexture() != null) {
                for (int i = 0; i <= c.getWidth(); i += DivvyUtility.getInstance().getDivvyListTexture().getWidth(null)) {
                    for(int j = 0; j <= c.getHeight(); j += DivvyUtility.getInstance().getDivvyListTexture().getHeight(null))
                    g2.drawImage(DivvyUtility.getInstance().getDivvyListTexture(), i, j, null);
                }
            }
            /*
             * create gradients
             */
            gradient = new LinearGradientPaint(0, 0, 0, c.getHeight(), RATIOS, COLORS);
            top_glow = new RadialGradientPaint(c.getWidth()/2, -c.getWidth()/2, c.getWidth()*(2), RATIOS2, COLORS2);
            bottom = new GradientPaint(0, c.getHeight(), DARK_TRANS, 0, c.getHeight()-6, DARK_EMPTY);
            right = new GradientPaint(c.getWidth(), 0, DARK_TRANS, c.getWidth()-6, 0, DARK_EMPTY);
            /*
             * draw main gradient
             */
            g2.setPaint(gradient);
            g2.fillRect(0, 0, c.getWidth(), c.getHeight());
            /*
             * top line
             */
            g2.setColor(LITE_TRANS);
            g2.drawLine(0, 0, c.getWidth(), 0);
            /*
             * draw dark, bottom gradient
             */
            g2.setPaint(bottom);
            g2.fillRect(0, c.getHeight()-8, c.getWidth(), 8);
            /*
             * draw right shadow
             */
            g2.setPaint(right);
            g2.fillRect(c.getWidth()-10, 0, 10, c.getHeight());
            /*
             * draw radial gradient
             */
            g2.setPaint(top_glow);
            g2.fillRect(0, 0, c.getWidth(), c.getWidth());
            /*
             * border line
             */
            g2.setColor(Color.DARK_GRAY);
            g2.drawLine(0, 0, 0, c.getHeight());
            g2.dispose();
        }
        /*
         * draw buffer to graphics object
         */
        g.drawImage(buffer, 0, 0, null);
    }

    /**
     * Paint one List cell: compute the relevant state, get the special divvy
     * cell renderer component, and then use the CellRendererPane to paint it.
     * Subclasses may want to override this method rather than paint().
     *
     * @see #paint
     */
    protected void paintCell(
        Graphics g,
        int row,
        Rectangle rowBounds,
        ListCellRenderer cellRenderer,
        ListModel dataModel,
        ListSelectionModel selModel,
        int leadIndex)
    {
        Object value = dataModel.getElementAt(row);
        boolean cellHasFocus = list.hasFocus() && (row == leadIndex);
        boolean isSelected = selModel.isSelectedIndex(row);

        Component rendererComponent = ((Divvy)value).getCellRenderer(isSelected, cellHasFocus);
//            cellRenderer.getListCellRendererComponent(list, value, row, isSelected, cellHasFocus);

        int cx = rowBounds.x;
        int cy = rowBounds.y;
        int cw = rowBounds.width;
        int ch = rowBounds.height;
        /*
         * renderPane paints the cells in the right place
         */
//        rendererPane.paintComponent(g, rendererComponent, list, cx, cy, cw, ch, true);
        ((DivvyPanel)rendererComponent).paintComponent(g, rendererPane, list, cx, cy, cw, ch, true);
    }
}
