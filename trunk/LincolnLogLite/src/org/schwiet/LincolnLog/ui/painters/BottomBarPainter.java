/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schwiet.LincolnLog.ui.painters;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import org.schwiet.LincolnLog.ui.utils.PlatformUtils;
import org.schwiet.spill.painter.Painter;

/**
 *
 * @author sethschwiethale
 */
public class BottomBarPainter implements Painter<Component> {

    private final float[] RATIOS = new float[]{0.0f, 1.0f};
    private final Color[] ACTIVE = new Color[]{PlatformUtils.MAC_DSBLD_DARK, PlatformUtils.MAC_ACT_DARKEST};
    private final Color[] INACTIVE = new Color[]{PlatformUtils.MAC_DSBLD_DARK, PlatformUtils.MAC_DSBLD_LITE};
    LinearGradientPaint paint;
    GradientPaint gradL, gradR;
    boolean active;

    public void paint(Graphics2D graphics, Component paintTarget, int width, int height) {
        active = PlatformUtils.paintFocused(paintTarget);

        if (active) {
            paint = new LinearGradientPaint(0, 0, 0, height, RATIOS, ACTIVE);
            gradL = new GradientPaint(0, height - 4, PlatformUtils.MAC_ACT_DARKEST, height / 2, 0, PlatformUtils.TRANSPARENT);
            gradR = new GradientPaint(width, height - 4, PlatformUtils.MAC_ACT_DARKEST, width - height / 2, 0, PlatformUtils.TRANSPARENT);
        } else {
            paint = new LinearGradientPaint(0, 0, 0, height, RATIOS, INACTIVE);
        }
        /*
         * transparency
         */
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(0, 0, width, height);
        graphics.setComposite(AlphaComposite.Src);
        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.fillRoundRect(0, -10, width, height + 10,
                10, 10);

        /*
         * do drawing
         */
        graphics.setComposite(AlphaComposite.SrcAtop);
        graphics.setPaint(paint);
        graphics.fillRect(0, 0, width, height);

        if (active) {
            graphics.setColor(PlatformUtils.MAC_DSBLD_DARK);
            graphics.drawLine(0, 1, width, 1);
            graphics.drawLine(0, height - 1, width, height - 1);
            graphics.setColor(Color.GRAY);
            graphics.drawLine(0, 0, width, 0);

            graphics.setPaint(gradL);
            graphics.fillRect(0, 0, height * 2, height);
            graphics.setPaint(gradR);
            graphics.fillRect(width - height * 2, 0, height * 2, height);
        }
    }
}
