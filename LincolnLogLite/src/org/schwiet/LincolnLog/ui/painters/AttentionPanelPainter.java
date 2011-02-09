/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.painters;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import org.schwiet.spill.painter.Painter;

/**
 *
 * @author sethschwiethale
 */
public class AttentionPanelPainter implements Painter<Component>{
    private static final Color OUTER = new Color(160,161,162);
    private static final Color CENTER = new Color(245,245,245);
    /*
     *
     */
    private static final float[] STOPS = {0.0f, 1.0f};
    /*
     *
     */
    private static final Color[] COLORS = {CENTER, OUTER};
    /*
     *
     */
    private RadialGradientPaint gradient;
    private GradientPaint shadow = new GradientPaint(0,0,new Color(40,40,40,200), 0, 5, new Color(40,40,40,0));

    public void paint(Graphics2D graphics, Component objectToPaint, int width, int height) {
        gradient = new RadialGradientPaint(width/2, height, width, STOPS, COLORS);
        graphics.setPaint(gradient);
        graphics.fillRect(0, 0, width, height);
        graphics.setPaint(shadow);
        graphics.fillRect(0, 0, width, 5);
    }

}
