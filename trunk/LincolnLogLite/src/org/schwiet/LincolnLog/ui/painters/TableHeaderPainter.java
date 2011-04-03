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
public class TableHeaderPainter implements SchemePainter<Component> {

    private BasicThemedColorScheme scheme;
    /*
     * three colors at the three stops
     */
    private Color[] colors = new Color[2];
    /*
     * ratios or stops for gradient
     */
    private static final float[] RATIOS = {0.0f, 1.0f};
    /*
     * 
     */
    private LinearGradientPaint bg;
    /*
     * saves from remaking gradient if unnecessary
     */
    private int cachedHeight = -1;

    public void setColorScheme(ColorScheme scheme) {
        if (scheme instanceof BasicThemedColorScheme) {
            colors[1] = ((BasicThemedColorScheme)scheme).getActiveDark();
            colors[0] = ((BasicThemedColorScheme)scheme).getActiveMid();
            this.scheme = (BasicThemedColorScheme)scheme;
        }else{
            throw new UnsupportedOperationException("Should be an instance of BasicThemedColorScheme");
        }
    }

    public ColorScheme getColorScheme() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void paint(Graphics2D g2, Component objectToPaint, int width, int height) {
        if(bg == null || cachedHeight != height){
            bg = new LinearGradientPaint(0,0, 0, height, RATIOS, colors);
            cachedHeight = height;
        }

        g2.setPaint(bg);
        g2.fillRect(0, 0, width, height);

        g2.setColor(scheme.getActiveMid());
        g2.drawLine(0, height-1, width, height-1);
        g2.drawLine(width-1, 0, width-1, height);

        g2.setColor(scheme.getActiveLite());
        g2.drawLine(0, 0, width, 0);
    }

    public TableHeaderPainter(BasicThemedColorScheme scheme) {
        setColorScheme(scheme);
    }

    private TableHeaderPainter() {
    }
}
