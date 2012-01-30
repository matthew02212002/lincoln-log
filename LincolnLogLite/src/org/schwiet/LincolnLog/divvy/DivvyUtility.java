/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.divvy;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JList;
import org.apache.log4j.Logger;
import org.schwiet.LincolnLog.ui.delegates.DivvyListUI;

/**
 *
 * @author sethschwiethale
 */
public class DivvyUtility {
    /*
     * used in DivvyPanel
     */
    private static Image METAL_TILE_256;
    private final URL METAL_URL = getClass().getResource("/resources/metal_256.png");
    /*
     * used in DivvyListUI and DivvyPanel
     */
    private static Image METAL_TILE_200;
    private final URL METAL2_URL = getClass().getResource("/resources/metal_200.png");
    /*
     * Font
     */
    private final String FONT_LOCATION = "font/Futura.ttc";
    private final String FONT2_LOCATION = "font/Sansation_Regular.ttf";
    private Font numberFont, sensationFont;
    /*
     * single instance
     */
    private static final DivvyUtility INSTANCE = new DivvyUtility();
    /*
     * 
     */
    static Logger logger = Logger.getLogger(DivvyUtility.class);
    /*
     * unmodifiable
     */
    private DivvyUtility(){
        try {
            numberFont = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_LOCATION));
            sensationFont = Font.createFont(Font.TRUETYPE_FONT, new File(FONT2_LOCATION));
        } catch (FontFormatException ex) {
            logger.error("Bad Font format: "+FONT_LOCATION);
        } catch (IOException ex) {
            logger.error("IO Exception: "+ex);
        }
        if(numberFont == null){
            numberFont = (new JLabel()).getFont().deriveFont(1, 16.0f);
        }
    }
    /**
     * creates a JPanel that has it's own components appropriate for representing
     * a {@code Divvy} in a list
     */
    protected static DivvyPanel getDivvyCellRenderer(String name, double amount, double amountLeft){
        return new DivvyPanel(name, amount, amountLeft);
    }
    /**
     * sets {@link list}'s ui delegate to a new {@code DivvyListUI}
     * @param list
     */
    public static void installListUI(JList list){
        list.setUI(new DivvyListUI(list));
    }
    /**
     * get the image used to paint non-selected DivvyPanel's texture
     * @return
     */
    public Image getDivvyPanelTexture(){
        if (METAL_TILE_256 == null){
            try {
            METAL_TILE_256 = ImageIO.read(METAL_URL);
        } catch (IOException ex) {
            logger.error("could not load texture tile"+METAL_URL.toExternalForm());
        }
        }
        return METAL_TILE_256;
    }
    /**
     * returns the image used to draw the Divvy JList's texture
     * @return
     */
    public Image getDivvyListTexture(){
        if (METAL_TILE_200 == null){
            try {
            METAL_TILE_200 = ImageIO.read(METAL2_URL);
        } catch (IOException ex) {
            logger.error("could not load texture tile"+METAL2_URL.toExternalForm());
        }
        }
        return METAL_TILE_200;
    }

    public Font getNumberFont() {
        return numberFont;
    }

    public Font getThinFont(){
        return sensationFont;
    }
    
    /**
     * get the only instance of DivvyUtility
     * @return
     */
    public static DivvyUtility getInstance(){
        return INSTANCE;
    }

    public enum DivvyType{
        EXPENSE("Expense"),
        BIN("Allowance");

        private String name;

        private DivvyType(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString(){
            return getName();
        }
        
    }

}
