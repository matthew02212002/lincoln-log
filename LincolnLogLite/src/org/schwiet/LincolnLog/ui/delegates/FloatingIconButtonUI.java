/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.delegates;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author sethschwiethale
 */
public class FloatingIconButtonUI extends BasicButtonUI{
    //colors to use to paint a rectangle over the area of the button if in
    //pressed or unfocused state
    protected static final Color TRANSPARENT_MASK = new Color(0, 0, 0, 0);
    private static final Color PRESSED_MASK_LITE = new Color(50, 235, 255, 128);
    private static final Color PRESSED_MASK_DARK = new Color(0, 30, 250, 128);
    private static final Color SELECTED_MASK = new Color(0, 200, 255, 128);
    private static final Color UNFOCUSED_MASK = new Color(255, 255, 255, 128);
    //
    private static final Color[] COLORS = {PRESSED_MASK_LITE, PRESSED_MASK_DARK};
    private static final float[] RATIOS = {0.0f, 1.0f};

    //BufferedImage used as a back buffer, recreated when size changes
    private BufferedImage backBuffer = null;
    private Graphics2D bufferGraphics = null;
    private RadialGradientPaint gradient;

    @Override
    protected void installDefaults(AbstractButton button){
        super.installDefaults(button);

        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setIconTextGap(0);

        button.setOpaque(false);
        button.setFocusable(false);
        //TODO: something with the font. maybe use droidsans
    }

    @Override
    protected void paintIcon(Graphics g, JComponent c, Rectangle iconRect){
        AbstractButton button = (AbstractButton)c;
        ButtonModel model = button.getModel();

        //create buffer only if size had changed
        if(backBuffer == null ||
           backBuffer.getWidth() != iconRect.width ||
           backBuffer.getHeight() != iconRect.height){
            if(backBuffer != null){
                backBuffer.flush();
                bufferGraphics.dispose();
            }
            backBuffer = new BufferedImage(iconRect.width, iconRect.height, BufferedImage.TYPE_INT_ARGB);
            bufferGraphics = (Graphics2D)backBuffer.getGraphics();
        }
        //clear contents
        bufferGraphics.setComposite(AlphaComposite.Clear);
        bufferGraphics.fillRect(0, 0, iconRect.width, iconRect.height);
        //draw icon
        bufferGraphics.setComposite(AlphaComposite.SrcOver);
        button.getIcon().paintIcon(c, bufferGraphics, 0, 0);
        //set composite on graphics to SrcAtop which blends the src and dst
        bufferGraphics.setComposite(AlphaComposite.SrcAtop);

        //draw appropriate mask
        if(!model.isEnabled()){
            bufferGraphics.setColor(UNFOCUSED_MASK);
        } else if(model.isArmed()){
            gradient = new RadialGradientPaint((int)iconRect.getWidth()/2, (int)iconRect.getHeight(), (int)iconRect.getHeight(), RATIOS, COLORS);
            bufferGraphics.setPaint(gradient);
        }else if (model.isSelected()){
            bufferGraphics.setColor(SELECTED_MASK);
        } else{
            bufferGraphics.setColor(TRANSPARENT_MASK);
        }

        //fill mask rectangle
        bufferGraphics.fillRect(0, 0, iconRect.width, iconRect.height);

        //draw image onto original graphics
        g.drawImage(backBuffer, iconRect.x, iconRect.y, null);
    }

}
