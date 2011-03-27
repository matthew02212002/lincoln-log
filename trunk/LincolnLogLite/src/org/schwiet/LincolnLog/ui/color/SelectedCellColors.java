/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.color;

import java.awt.Color;
import org.schwiet.spill.painter.colorScheme.BasicThemedColorScheme;

/**
 *
 * @author sethschwiethale
 */
public class SelectedCellColors implements BasicThemedColorScheme{

    public static final Color ACT_LITE = new Color(193,220,204);
    public static final Color ACT_DARK = new Color(180,180,180);
    public static final Color ACT_MID = new Color(146,192,164);
    public static final Color DSBLD_DARK = new Color(0x8f8f8f);
    public static final Color DSBLD_LITE = new Color(0xe4e4e4);
    public static final Color DSBLD_MID = new Color(0xd1d1d1);

    

    public Color getActiveDark() {
        return ACT_DARK;
    }

    public Color getActiveMid() {
        return ACT_MID;
    }

    public Color getActiveLite() {
        return ACT_LITE;
    }

    public Color getInactiveDark() {
        return DSBLD_DARK;
    }

    public Color getInactiveMid() {
        return DSBLD_MID;
    }

    public Color getInctiveLite() {
        return DSBLD_LITE;
    }

    public Color getBackground() {
        return Color.WHITE;
    }

    public Color getForeground() {
        return Color.DARK_GRAY;
    }

}
