/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schwiet.LincolnLog.ui.delegates;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * <p>
 * A {@link BasicLabelUI} that paints a shadow under the text using the given shadow color, which
 * helps emphasize the text. The UI delegate also provides a facility for drawing a different shadow
 * color when the corresponding label's containing {@link java.awt.Window} is unfocused.
 * </p>
 * While this UI delegate can be directly installed on existing {@code JLabel}s, it is
 * recommended that you use the
 * {@link com.explodingpixels.macwidgets.MacWidgetFactory#createEmphasizedLabel(String)} or
 * {@link com.explodingpixels.macwidgets.MacWidgetFactory#makeEmphasizedLabel(JLabel, Color, Color, Color)}
 * factory methods.
 * <p>
 * Here's a close-up of an emphasized label:
 * <br>
 * <img src="../../../../../graphics/EmphasizedLabelUI.png">
 * </p>
 */
public class GlowLabelUI extends BasicLabelUI {

    private Color fShadowColor;
    private Color fFocusedTextColor;
    private Color fUnfocusedTextColor;
    public static final Color DEFAULT_EMPHASIS_COLOR = new Color(15, 19, 25, 210);
    public static final Color DEFAULT_FOCUSED_FONT_COLOR = new Color(0xDDDDDD);
    public static final Color DEFAULT_UNFOCUSED_FONT_COLOR = new Color(0x3f3f3f);
    public static final Color DEFAULT_DISABLED_FONT_COLOR = new Color(0x3f3f3f);

    /**
     * Creates an {@code EmphasizedLabelUI} using the default colors.
     */
    public GlowLabelUI() {
        this(DEFAULT_FOCUSED_FONT_COLOR, DEFAULT_UNFOCUSED_FONT_COLOR,
                DEFAULT_EMPHASIS_COLOR);
    }

    /**
     * Creates an {@code EmphasizedLabelUI} using the given colors.
     *
     * @param focusedTextColor   the color to draw the text with when the parent
     *                           {@link java.awt.Window} has focus.
     * @param unfocusedTextColor the color to draw the text with when the parent
     *                           {@link java.awt.Window} does not have focus.
     * @param emphasisColor      the color to draw the emphasis text with.
     */
    public GlowLabelUI(Color focusedTextColor, Color unfocusedTextColor, Color emphasisColor) {
        fFocusedTextColor = focusedTextColor;
        fUnfocusedTextColor = unfocusedTextColor;
        fShadowColor = emphasisColor;

    }


    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setOpaque(false);
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        // TODO add uninstallation.
    }

    @Override
    protected void paintEnabledText(JLabel label, Graphics g, String s,
            int textX, int textY) {
//        if (!s.equals(currentText) || enabledBuffer == null||
//                enabledBuffer.getWidth() < g.getClip().getBounds().width ||
//                enabledBuffer.getHeight() < g.getClip().getBounds().height) {
//            enabledBuffer = new BufferedImage(g.getClip().getBounds().width, g.getClip().getBounds().height, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g2 = enabledBuffer.createGraphics();
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g2.setColor(fShadowColor);
//            g2.setFont(label.getFont());
//            BasicGraphicsUtils.drawStringUnderlineCharAt(g2, s, -1, textX, textY + 1);
//            g2.setColor(fFocusedTextColor);
//            BasicGraphicsUtils.drawStringUnderlineCharAt(g2, s, -1, textX, textY);
//            g2.dispose();
//            currentText = s;
//        }
//        g.drawImage(enabledBuffer, 0, 0, null);
        /*
         * may be less efficient, but it is less complicated, will revisit, if issues continue
         */
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(fShadowColor);
        g2.setFont(label.getFont());

        BasicGraphicsUtils.drawStringUnderlineCharAt(g2, s, -1, textX, textY - 1);
        BasicGraphicsUtils.drawStringUnderlineCharAt(g2, s, -1, textX, textY + 1);
        BasicGraphicsUtils.drawStringUnderlineCharAt(g2, s, -1, textX, textY - 2);
        BasicGraphicsUtils.drawStringUnderlineCharAt(g2, s, -1, textX-1, textY);
        BasicGraphicsUtils.drawStringUnderlineCharAt(g2, s, -1, textX+1, textY);
//        BasicGraphicsUtils.drawStringUnderlineCharAt(g2, s, -1, textX-2, textY);
        BasicGraphicsUtils.drawStringUnderlineCharAt(g2, s, -1, textX+2, textY);

        g2.setColor(fFocusedTextColor);
        BasicGraphicsUtils.drawStringUnderlineCharAt(g2, s, -1, textX, textY);
        g2.dispose();
    }

    @Override
    protected void paintDisabledText(JLabel label, Graphics g, String s, int textX, int textY) {
        // TODO do use a diabled color here.
        g.setColor(fShadowColor);
        g.setFont(label.getFont());
        BasicGraphicsUtils.drawStringUnderlineCharAt(g, s, -1, textX, textY + 1);
        g.setColor(DEFAULT_DISABLED_FONT_COLOR);
        BasicGraphicsUtils.drawStringUnderlineCharAt(g, s, -1, textX, textY);
    }

    public enum IndentDirection {

        UP(),
        DOWN();
    }
}
