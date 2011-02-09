/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.components;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.schwiet.LincolnLog.ui.delegates.FloatingIconButtonUI;
import org.schwiet.LincolnLog.ui.painters.AttentionPanelPainter;
import org.schwiet.LincolnLog.ui.painters.BottomBarPainter;
import org.schwiet.LincolnLog.ui.painters.ControlBarPainter;
import org.schwiet.LincolnLog.ui.painters.DarkGlassHeaderPainter;
import org.schwiet.LincolnLog.ui.painters.GlassPainter;
import org.schwiet.LincolnLog.ui.utils.PlatformUtils;
import org.schwiet.spill.components.PaintedPanel;
import org.schwiet.spill.painter.Painter;

/**
 *
 * @author sethschwiethale
 */
public class ComponentFactory {

    /**
     * returns a new Panel with <code>DarkGlassHeaderPainter</code> set as its
     * background painter
     * @return
     */
    public static JPanel getDarkGlassHeader(){
        Painter painter = new DarkGlassHeaderPainter();
        PaintedPanel panel = new PaintedPanel(painter);
        return panel;
    }

    public static JPanel getBottomBar(){
        Painter painter = new BottomBarPainter();
        PaintedPanel panel = new BottomBarPanel(painter);
        PlatformUtils.initAsStatusBar(panel);
        return panel;
    }

    public static JPanel getDivvyListPanel(){
        Painter painter = new GlassPainter();
        PaintedPanel panel = new PaintedPanel(painter);
        return panel;
    }

    public static JPanel getControlBar(){
        Painter painter = new ControlBarPainter();
        PaintedPanel panel = new PaintedPanel(painter);
        return panel;
    }

    public static JPanel getAttentionPanel(){
        Painter painter = new AttentionPanelPainter();
        PaintedPanel panel = new PaintedPanel(painter);
        return panel;
    }

    public static JButton getIconButton(URL imageURL){
        JButton button = new JButton();
        button.setIcon(new ImageIcon(imageURL));
        button.setUI(new FloatingIconButtonUI());
        return button;
    }
}
