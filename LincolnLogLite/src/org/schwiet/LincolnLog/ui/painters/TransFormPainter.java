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
public class TransFormPainter implements Painter<Component> {

    private static final Color OUTER = new Color(30,31,32);
    private static final Color CENTER = new Color(85,85,85);
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
    private GradientPaint shadow = new GradientPaint(0, 0, new Color(10, 10, 10, 120), 0, 4, new Color(89, 81, 89, 0));

    public void paint(Graphics2D graphics, Component objectToPaint, int width, int height) {
        gradient = new LinearGradientPaint(0, 0, 0, height, STOPS, COLORS);
        graphics.setPaint(gradient);
        graphics.fillRect(0, 0, width, height);
        graphics.setPaint(shadow);
        graphics.fillRect(0, 0, width, 5);
    }
}
