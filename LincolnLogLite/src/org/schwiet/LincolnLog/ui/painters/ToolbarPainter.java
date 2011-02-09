/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.painters;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import org.schwiet.spill.painter.Painter;

/**
 *
 * @author sethschwiethale
 */
public class ToolbarPainter implements Painter<Component>{
     private static final Color OUTER = new Color(130,131,132);
    private static final Color CENTER = new Color(185,185,185);
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
    private LinearGradientPaint gradient;
    private GradientPaint shadow = new GradientPaint(0,0,new Color(40,40,40,120), 0, 4, new Color(240,240,240,0));

    public void paint(Graphics2D graphics, Component objectToPaint, int width, int height) {
        gradient = new LinearGradientPaint(0, 0, 0, height, STOPS, COLORS);
        graphics.setPaint(gradient);
        graphics.fillRect(0, 0, width, height);
        graphics.setPaint(shadow);
        graphics.fillRect(0, 0, width, 5);
    }
}
