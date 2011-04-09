/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schwiet.LincolnLog.ui.painters;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import org.schwiet.spill.painter.SchemePainter;
import org.schwiet.spill.painter.colorScheme.BasicThemedColorScheme;
import org.schwiet.spill.painter.colorScheme.ColorScheme;

/**
 *
 * @author sethschwiethale
 */
public class TableCellPainter implements SchemePainter<Component> {

    private BasicThemedColorScheme scheme;
    /*
     * three colors at the three stops
     */
    private Color[] colors = new Color[3];
    /*
     * ratios or stops for gradient
     */
    private static final float[] RATIOS = {0.0f, .5f, 1.0f};
    /*
     *
     */
    private static final int MARGIN = 6;

    /*
     * 
     */
    private LinearGradientPaint bg;
    /*
     * saves from remaking gradient if unnecessary
     */
    private int cachedWidth = -1;
    private boolean paintDivider = true;

    public void setColorScheme(ColorScheme scheme) {
        if (scheme instanceof BasicThemedColorScheme) {
            colors[0] = ((BasicThemedColorScheme) scheme).getActiveMid();
            colors[1] = ((BasicThemedColorScheme) scheme).getActiveLite();
            colors[2] = ((BasicThemedColorScheme) scheme).getActiveMid();
            this.scheme = (BasicThemedColorScheme) scheme;
        } else {
            throw new UnsupportedOperationException("Should be an instance of BasicThemedColorScheme");
        }
    }

    public ColorScheme getColorScheme() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPaintDivider(boolean paintDivider){
        this.paintDivider = paintDivider;
    }

    public void paint(Graphics2D g2, Component objectToPaint, int width, int height) {
        if (bg == null || cachedWidth != width) {
            bg = new LinearGradientPaint(0, 0, width, 0, RATIOS, colors);
            cachedWidth = width;
        }

        g2.setPaint(bg);
        g2.fillRect(0, 1, width, height-1);

        if (paintDivider) {
            g2.setColor(scheme.getActiveDark());
            g2.drawLine(MARGIN, height - 1, width, height - 1);

            g2.setColor(scheme.getBackground());
            g2.drawLine(MARGIN, 0, width-MARGIN, 0);
        }
    }

    public TableCellPainter(BasicThemedColorScheme scheme) {
        setColorScheme(scheme);
    }

    private TableCellPainter() {
    }
}
