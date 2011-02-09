/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.painters;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import org.schwiet.spill.painter.Painter;

/**
 *
 * @author sethschwiethale
 */
public class ControlBarPainter implements Painter<Component>{
    /*
     * colors
     */
    private final Color MID_LOW = new Color(200, 200, 200);
    private final Color TOP = new Color(245, 245, 245);
    private final Color MID_TOP = new Color(215, 215, 215);
    private final Color BTM = new Color(210, 210, 210);
    /*
     * gradient stuff
     */
    private final float[] RATIOS = {0.0f, 0.4999f, 0.5f, 1.0f};
    private final Color[] COLORS = {TOP, MID_TOP, MID_LOW, BTM};
    private final float[] MINOR_RATIOS = {0.0f, 1.0f};
    private final Color[] MINOR_COLORS = {new Color(120,120,120), new Color(130, 135, 148)};
    //
    private LinearGradientPaint fill;
    private LinearGradientPaint stroke;
    /**
     *
     * @param graphics
     * @param objectToPaint
     * @param width
     * @param height
     */
    public void paint(Graphics2D graphics, Component objectToPaint, int width, int height) {
        /*
         *
         */
        fill = new LinearGradientPaint(0,2,0,height-4,RATIOS,COLORS);
        stroke = new LinearGradientPaint(0,1,0,height-2,MINOR_RATIOS,MINOR_COLORS);
        /*
         * this goes behind
         */
        graphics.setPaint(stroke);
        graphics.fillRect(0, 0, width, height);
        /*
         * this goes in front
         */
        graphics.setPaint(fill);
        graphics.fillRect(1, 2, width-2, height-2);
        /*
         * finally
         */
        graphics.setColor(TOP);
        graphics.drawLine(1, 1, width-2, 1);
        graphics.setColor(MID_TOP);
        graphics.drawLine(1, height-2, width-2, height-2);
    }

}