/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schwiet.LincolnLog.ui.painters;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.image.BufferedImage;
import org.schwiet.LincolnLog.divvy.DivvyUtility;
import org.schwiet.spill.painter.Painter;

/**
 *
 * @author sethschwiethale
 */
public class GlassPainter implements Painter<Component> {

    private static final Color SHINE_1 = new Color(200, 200, 200, 15);
    private static final Color SHINE_2 = new Color(150, 150, 150, 15);
    private static final Color BASE = new Color(100, 100, 100, 10);
    private static final Color GROOVE = new Color(20, 20, 20);
    private static final Color SHADE = new Color(10, 10, 10, 120);
    private static final Color[] COLORS = {SHINE_1, SHINE_2, BASE};
    private static final float[] RATIOS = {.0f, .4999f, .51f};
    private boolean derpressed = false;
    private boolean changed = false;
    private LinearGradientPaint gradient;
    /*
     * for transparency's sake...
     */
    private BufferedImage buffer;

    public void setDerpressed(boolean depressed) {
        if (this.derpressed != depressed) {
            changed = true;
            this.derpressed = depressed;
        } else {
            changed = false;
        }
    }

    public void paint(Graphics2D graphics, Component objectToPaint, int width, int height) {
        /*
         * if buffer needs to be redrawn
         */
        if (buffer == null || buffer.getWidth() != width || buffer.getHeight() != height || changed) {
            buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buffer.createGraphics();

            gradient = new LinearGradientPaint(0, 0, 0, height, RATIOS, COLORS);
            if (derpressed) {
                if (DivvyUtility.getInstance().getDivvyListTexture() != null) {
                    for (int i = 0; i <= width; i += DivvyUtility.getInstance().getDivvyListTexture().getWidth(null)) {
                        for (int j = 0; j <= height; j += DivvyUtility.getInstance().getDivvyListTexture().getHeight(null)) {
                            g2.drawImage(DivvyUtility.getInstance().getDivvyListTexture(), i, j, null);
                        }
                    }
                }
                g2.setColor(SHADE);
                g2.fillRect(0, 0, width, height);
                g2.setColor(Color.DARK_GRAY);
                g2.drawLine(0, 0, 0, height);
            } else {
                if (DivvyUtility.getInstance().getDivvyPanelTexture() != null) {
                    for (int i = 0; i <= width; i += DivvyUtility.getInstance().getDivvyPanelTexture().getWidth(null)) {
                        for (int j = 0; j <= height; j += DivvyUtility.getInstance().getDivvyPanelTexture().getHeight(null)) {
                            g2.drawImage(DivvyUtility.getInstance().getDivvyPanelTexture(), i, j, null);
                        }
                    }
                }
                g2.setPaint(gradient);
                g2.fillRect(0, 0, width, height);
                g2.drawLine(0, height - 2, width - 1, height - 2);
                g2.setColor(GROOVE);
                g2.drawLine(0, height - 1, width - 1, height - 1);
            }
            g2.dispose();
            changed = false;
        }
        /*
         * stamp image as background
         */
        graphics.drawImage(buffer, 0, 0, null);
    }
}
