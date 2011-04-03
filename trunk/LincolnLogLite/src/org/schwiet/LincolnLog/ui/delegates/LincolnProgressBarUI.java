/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schwiet.LincolnLog.ui.delegates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * Only Paints a Determinate Progress Bar, because it will only be used for
 * discrete, known values (moneys)
 * @author sethschwiethale
 */
public class LincolnProgressBarUI extends BasicProgressBarUI {

    LinearGradientPaint backgroundGradient,
            barGradient;
    private static final float[] RATIOS = {.0f, .2f, .9f};
    private static final Color[] COLORS = {new Color(10, 10, 10, 130),new Color(10, 10, 10, 80), new Color(10, 10, 10, 10)};
    private static final float[] BAR_RATIOS = {.0f, .1f, .5f, 1.0f};
    private static final Color[] BAR_COLORS = {new Color(111, 147, 153), new Color(211, 247, 253), new Color(167, 183, 240), new Color(200, 235, 240)};
    private static final int INSET = 1;

    /**
     * Delegates painting to one of two methods:
     * paintDeterminate or paintIndeterminate.
     */
    public void paint(Graphics g, JComponent c) {
        paintBounds(g, c);
        if (progressBar.isIndeterminate()) {
            throw new UnsupportedOperationException("this UI does not support indeterminate types");
        } else {
            paintDeterminate(g, c);
        }
    }

    private void paintBounds(Graphics g, JComponent c) {
        backgroundGradient = new LinearGradientPaint(0, 3, 0, c.getHeight() - 4, RATIOS, COLORS);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(backgroundGradient);
        g2.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 12, 12);
        g2.setPaint(new Color(35, 35, 35));
        g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 12, 12);
        g2.setPaint(new Color(15,15,15));
        g2.drawRoundRect(1, 1, c.getWidth() - 3, c.getHeight() - 3, 11, 11);
        g2.setPaint(new Color(20, 20, 20, 150));
        g2.drawRoundRect(2, 2, c.getWidth() - 5, c.getHeight() - 5, 12, 12);
        g2.dispose();
    }

    protected void paintDeterminate(Graphics g, JComponent c) {
        if (!(g instanceof Graphics2D)) {
            return;
        }

        Insets b = new Insets(INSET,INSET,INSET,INSET); // area for border
        int barRectWidth = progressBar.getWidth() - (b.right + b.left);
        int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }

        int cellLength = getCellLength();
        int cellSpacing = getCellSpacing();
        // amount of progress to draw
        int amountFull = getAmountFull(b, barRectWidth, barRectHeight);


        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
            // draw the progress bar
            barGradient = new LinearGradientPaint(0,0,0,c.getHeight(), BAR_RATIOS,BAR_COLORS);
            /*
             * paint left to right
             */
            g2.setPaint(new Color(197,204,215,(int)(40*progressBar.getPercentComplete())));
            g2.fillRoundRect(INSET, INSET, amountFull,
                    c.getHeight()-(2*INSET), 12, 12);
            g2.setPaint(new Color(87,124,205,(int)(60*progressBar.getPercentComplete())));
            g2.fillRoundRect(INSET+1, INSET+1, amountFull-2,
                    c.getHeight()-(2*INSET)-2, 12, 12);
            g2.setPaint(new Color(15,154,248, 80));
            g2.fillRoundRect(INSET+2, INSET+2, amountFull-4,
                    c.getHeight()-(2*INSET)-4, 10, 10);
            g2.setPaint(barGradient);//new Color(205,210,255,(int)(220*progressBar.getPercentComplete())));
            g2.fillRoundRect(INSET+3, INSET+3, amountFull-6,
                    c.getHeight()-(2*INSET)-6, 10, 10);

        }
        //TODO: handle vertical and string painting if needed
        g2.dispose();
    }
}
