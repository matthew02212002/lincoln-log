/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.divvy;

import info.clearthought.layout.TableLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import org.apache.log4j.Logger;
import org.schwiet.LincolnLog.divvy.DivvyUtility.DivvyType;
import org.schwiet.LincolnLog.divvy.commands.ChangeDivvyValsCommand;
import org.schwiet.LincolnLog.divvy.commands.SaveDivvyCommand;
import org.schwiet.LincolnLog.transaction.AmountColumnEditorFilter;
import org.schwiet.LincolnLog.ui.command.CommandDispatch;
import org.schwiet.LincolnLog.ui.utils.ViewSetupManager.View;
import org.schwiet.spill.plaf.IndentLabelUI;

/**
 *
 * @author sethschwiethale
 */
public class DivvyForm extends JPanel{
    /**
     * logger for DivvyForm
     */
    static Logger logger = Logger.getLogger(CommandDispatch.class);
    /**
     * call this to generate a new {@link Divvy} based on the values entered in
     * this form
     * @return
     */
    public boolean generateDivvy() {
        try{
            Divvy divvy = new Divvy(nameField.getText(), Double.parseDouble(amountField.getText()), (DivvyType)typeChooser.getSelectedItem());
            CommandDispatch.getInstance().performCommand(SaveDivvyCommand.createCommand(divvy));
        }catch(Exception e){
            logger.error("error trying to generate Divvy: "+e);
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * call this to update the passed {@link Divvy} with the values in this form
     * @param div
     * @return
     */
    public boolean updateDivvy(Divvy div){
        try{
            CommandDispatch.getInstance().performCommand(ChangeDivvyValsCommand.createCommand(div,
                    nameField.getText(),
                    Double.parseDouble(amountField.getText()),
                    (DivvyType)typeChooser.getSelectedItem()));
        }catch(Exception e){
            logger.error("error updating Divvy: "+div+", "+e);
            return false;
        }
        return true;
    }
    /*
     *
     */
    JTextField nameField = new JTextField("enter name");
    JTextField amountField = new JTextField("enter amount");
    /*
     * JLabels
     */
    JLabel titleLabel = new JLabel(View.NEW_DIVVY.getTitle());
    JLabel nameLabel = new JLabel(" Divvy Name");
    JLabel amountLabel = new JLabel(" Amount");
    /*
     * ComboBox
     */
    JComboBox typeChooser = new JComboBox();
    /*
     *
     */
    private static final double[][] NEW_DIVVY_DIVS = {{-1, 150, 5, 100, -1},{-1, 24, 10, 18, 2, 25, 6, 24, -1}};
    /*
     *
     */
    private static final TableLayout NEW_DIVVY_LAYOUT = new TableLayout(NEW_DIVVY_DIVS);

    private static final DivvyForm INSTANCE = new DivvyForm();

    private DivvyForm(){
        super();
        setOpaque(false);
        /*
         * indented label look
         */
        titleLabel.setUI(new IndentLabelUI(new Color(80,81,82), Color.GRAY, Color.WHITE));
        nameLabel.setUI(new IndentLabelUI(Color.BLACK, Color.GRAY, Color.WHITE));
        amountLabel.setUI(new IndentLabelUI(Color.BLACK, Color.GRAY, Color.WHITE));
        /*
         * title font should be bigger
         */
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16.0f));
        /*
         * add components
         */
        this.setLayout(NEW_DIVVY_LAYOUT);
        this.add(this.titleLabel, "1,1,3,1");
        this.add(this.nameLabel, "1,3");
        this.add(this.amountLabel, "3,3");
        this.add(this.nameField, "1,5");
        this.add(this.amountField, "3,5");
        this.add(this.typeChooser, "1,7");
        /*
         * blocks input other than digits and a dicimal in the right place on
         * amountField
         */
        ((AbstractDocument)amountField.getDocument()).setDocumentFilter(new AmountColumnEditorFilter(amountField));
        /*
         *
         */
        this.typeChooser.addItem(DivvyType.EXPENSE);
        this.typeChooser.addItem(DivvyType.BIN);
    }

    public static DivvyForm getInstance(){
        return INSTANCE;
    }

    public void refreshForm() {
        this.nameField.setText("enter name");
        this.amountField.setText("0.00");
    }

    public void loadForm(Divvy div){
        this.nameField.setText(div.getName());
        this.amountField.setText(div.getAmount()+"");
        this.typeChooser.setSelectedItem(div.getType());
    }

}
