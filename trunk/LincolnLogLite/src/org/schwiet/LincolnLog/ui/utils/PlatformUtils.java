/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.lang.ref.WeakReference;
import javax.swing.FocusManager;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import org.schwiet.spill.components.PaintedPanel;

/**
 *
 * @author sethschwiethale
 */
public class PlatformUtils {
    public static final Color MAC_ACT_LITE = new Color(0xbcbcbc);
    public static final Color MAC_ACT_DARK = new Color(0xaaaaaa);
    public static final Color MAC_ACT_MID = new Color(0xb0b0b0);
    public static final Color MAC_ACT_DARKEST = new Color(0x8f8f8f);
    public static final Color MAC_DSBLD_LITE = new Color(0xe4e4e4);
    public static final Color MAC_DSBLD_DARK = new Color(0xd1d1d1);
    public static final Color TRANSPARENT = new Color(150, 150, 150, 0);

    public static boolean paintFocused(Component component) {
        Window window = SwingUtilities.getWindowAncestor(component);
        if (window == null) {
            return false;
        }
        return window.isFocused();
    }

    private final static boolean MAC_LAF = System.getProperty("os.name").toLowerCase().startsWith("mac");


    public static boolean isMac(){
        return MAC_LAF;
    }

    /**
     * installs the platform dependent painter for this area as well as creates
     * a focus listener for {@code commandBar}'s parent to repaint {@code commandBar}
     * when the parent's focus changes
     * @param commandBar
     */
    public static void initAsUnifiedToolBar(PaintedPanel commandBar){
        //set painter
//        setApproptriateCommandBarPainter(commandBar);
        //add focus listener to parent window, so commandBar will repaint better
        installParentWindowFocusListener(commandBar);
    }

    public static void initAsStatusBar(PaintedPanel statusBar){
//        setAppropriateStatusBarPainter(statusBar);
        //
        installParentWindowFocusListener(statusBar);
    }

    public static void initAsSpacer(PaintedPanel spacer){
//        setAppropriateSpacerPainter(spacer);
        //
        installParentWindowFocusListener(spacer);
    }

//    private static void setApproptriateCommandBarPainter(EmptyPanel commandBar){
//        if(MAC_LAF){
//            commandBar.setPainter(PainterFactory.createContinuousTitleBarPainter());
//        }
//        //TODO: Windows Substance implementation
//    }
//
//    private static void setAppropriateStatusBarPainter(EmptyPanel statusBar) {
//        if(MAC_LAF){
//            statusBar.setPainter(PainterFactory.createMacStatusBarPainter());
//        }
//        //TODO: Windows Substance implementation
//    }
//
//    private static void setAppropriateSpacerPainter(EmptyPanel spacer) {
//        if(MAC_LAF){
//            spacer.setPainter(PainterFactory.createMacSpacerPainter());
//        }
//        //TODO: Windows Substance implementation
//    }

    /*
     * create new WindowFocusListener and add it to component's parent if present.
     *
     */
    private static void installParentWindowFocusListener(JComponent component) {
        //create window focus listener
        WindowFocusListener tempListener = createParentWindowFocusListener(component);
        //if component already has a parent
        if(SwingUtilities.getWindowAncestor(component) != null){
            SwingUtilities.getWindowAncestor(component).addWindowFocusListener(tempListener);
        }
        //create weak reference
//        WindowFocusListener repaintOnFocusListener = createWeakParentWindowFocusListener(tempListener);
        //create ancestorListener
        AncestorListener ancestorListener = createAncestorListener(component, tempListener);
        //finally add ancestor listener to component
        component.addAncestorListener(ancestorListener);
    }

    /*
     * creates a windowFocuslistener that causes {@code component} to repaint when focus
     * is lost of gained
     */
    private static WindowFocusListener createParentWindowFocusListener(final JComponent component){
        return new WindowFocusListener(){

            public void windowGainedFocus(WindowEvent we) {
               component.repaint();
            }

            public void windowLostFocus(WindowEvent we) {
                component.repaint();
            }

        };
    }

    /*
     * needed for if/when {@code tempListener}'s component's parent changes
     */
    private static WindowFocusListener createWeakParentWindowFocusListener(WindowFocusListener tempListener) {
        final WeakReference<WindowFocusListener> weakReference = new WeakReference<WindowFocusListener>(tempListener);
        return new WindowFocusListener(){

            public void windowGainedFocus(WindowEvent we) {
               if(weakReference.get() != null){
                   weakReference.get().windowGainedFocus(we);
               }
               //TODO: else
            }

            public void windowLostFocus(WindowEvent we) {
                if(weakReference.get() != null){
                   weakReference.get().windowLostFocus(we);
               }
                //TODO: else
            }

        };
    }

    /**
     * uses the weak reference of {@code repaintOnFocusListener} {@link WindowFocusListener}
     * for when {@code component}'s parent changes
     * @param component
     * @param repaintOnFocusListener
     * @return
     */
    private static AncestorListener createAncestorListener(JComponent component, final WindowFocusListener repaintOnFocusListener) {
        final WeakReference<JComponent> weakReference = new WeakReference<JComponent>(component);
        return new AncestorListener(){

            public void ancestorAdded(AncestorEvent ae) {
                if(weakReference.get() != null){
                    final Window window = SwingUtilities.getWindowAncestor(weakReference.get());
                    window.removeWindowFocusListener(repaintOnFocusListener);
                    //SETH, this line isn't working for weakReference???
                    window.addWindowFocusListener(repaintOnFocusListener);
                    /*
                     *syncs up listener with windows state
                     */
                    fireInitialFocusEvent(repaintOnFocusListener, window);
                }
            }

            public void ancestorRemoved(AncestorEvent ae) {
                if(weakReference.get() != null){
                    SwingUtilities.getWindowAncestor(weakReference.get()).removeWindowFocusListener(repaintOnFocusListener);
                }
            }

            public void ancestorMoved(AncestorEvent ae) {
                //shouldn't need to handle this
            }

            private void fireInitialFocusEvent(WindowFocusListener repaintOnFocusListener, Window window) {
                Window focusWindow = FocusManager.getCurrentManager().getFocusedWindow();

                //fire off dummy event to force sync of window state and component state
                if (window == focusWindow) {
                    repaintOnFocusListener.windowGainedFocus(
                            new WindowEvent(window, WindowEvent.WINDOW_GAINED_FOCUS));
                } else {
                    repaintOnFocusListener.windowGainedFocus(
                            new WindowEvent(window, WindowEvent.WINDOW_LOST_FOCUS));
                }

            }

        };
    }

}
