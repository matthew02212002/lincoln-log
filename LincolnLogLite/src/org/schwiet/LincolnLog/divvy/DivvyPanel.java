/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.divvy;

import info.clearthought.layout.TableLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.ListCellRenderer;
import org.schwiet.LincolnLog.ui.delegates.LincolnProgressBarUI;
import org.schwiet.LincolnLog.ui.painters.GlassPainter;
import org.schwiet.spill.components.PaintedPanel;
import org.schwiet.spill.plaf.IndentLabelUI;

/**
 *
 * @author sethschwiethale
 */
public class DivvyPanel extends PaintedPanel implements ListCellRenderer{
    private JLabel nameLabel,
                    amountLabel,
                    remainingLabel;
    private JProgressBar gauge;

    private static final int CENTS = 100;
    /*
     *
     */
    Graphics gr;
    Container rp, par;
    int x, y, w, h;

    private DivvyPanel(){
    }

    public DivvyPanel(String name, double amount, double remaining){
        createComponents(name, amount, remaining);
        /*
         * layout
         */
        installComponents();
        /*
         * painter delegate
         */
        this.setBackgroundPainter(new GlassPainter());
    }

    private void installComponents() {
        double[][] spaces = {{5, 100, 5, -1, 5}, {8, 20, 4, 15, 4}};
        this.setLayout(new TableLayout(spaces));
        add(nameLabel, "1,1");
        add(gauge, "1,3,3,3");
        add(remainingLabel, "3,1");
    }

    public void setAmount(double amount) {
        this.gauge.setMaximum((int)(amount*CENTS));
    }

    public void setNameText(String name) {
        this.nameLabel.setText(name);
    }

    public void setRemaining(double remaining) {
        this.remainingLabel.setText("$"+remaining);
        this.gauge.setValue((int)(remaining*CENTS));
        this.redraw();
    }
    /*
     * initialization
     */
    private void createComponents(String name, double amountDollars, double amountLeft) {
        nameLabel = new JLabel(name);
        nameLabel.setUI(new IndentLabelUI());
        nameLabel.setVerticalAlignment(JLabel.BOTTOM);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.PLAIN, 14.0f));
        remainingLabel = new JLabel("$"+amountLeft);
        remainingLabel.setUI(new IndentLabelUI());
        remainingLabel.setHorizontalAlignment(JLabel.RIGHT);
        remainingLabel.setVerticalAlignment(JLabel.BOTTOM);
        remainingLabel.setFont(DivvyUtility.getInstance().getNumberFont().deriveFont(16.0f));
        gauge = new JProgressBar();
        gauge.getModel().setRangeProperties((int)(amountLeft*CENTS), 1, 0, (int)(amountDollars*CENTS), false);
        gauge.setIndeterminate(false);
        gauge.setUI(new LincolnProgressBarUI());
    }
    /**
     * Paint a cell renderer component c on graphics object g.  Before the component
     * is drawn it's reparented to this (if that's necessary), it's bounds
     * are set to w,h and the graphics object is (effectively) translated to x,y.
     * If it's a JComponent, double buffering is temporarily turned off. After
     * the component is painted it's bounds are reset to -w, -h, 0, 0 so that, if
     * it's the last renderer component painted, it will not start consuming input.
     * The Container p is the component we're actually drawing on, typically it's
     * equal to this.getParent(). If shouldValidate is true the component c will be
     * validated before painted.
     */
    public void paintComponent(Graphics g, Container r, Container p, int x, int y, int w, int h, boolean shouldValidate) {
	if (this == null) {
	    if (p != null) {
		Color oldColor = g.getColor();
		g.setColor(p.getBackground());
		g.fillRect(x, y, w, h);
		g.setColor(oldColor);
	    }
	    return;
	}
        /*
         * storing these references so I can call this method to repaint a
         * single cell. it's a total hack, but I think it should be safe for
         * my purposes
         */
        this.gr = g.create();
        this.rp = r;
        this.par = p;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        /*
         * need a parent
         */
	if (this.getParent() != r) {
	    r.add(this);
	}

	this.setBounds(x, y, w, h);

	if(shouldValidate) {
	    this.validate();
	}

	boolean wasDoubleBuffered = false;
	if ((this instanceof JComponent) && ((JComponent)this).isDoubleBuffered()) {
	    wasDoubleBuffered = true;
	    ((JComponent)this).setDoubleBuffered(false);
	}

	Graphics cg = g.create(x, y, w, h);

	try {
	    this.paint(cg);
	}
	finally {
	    cg.dispose();
	}

	if (wasDoubleBuffered && (this instanceof JComponent)) {
	    ((JComponent)this).setDoubleBuffered(true);
	}

	this.setBounds(-w, -h, 0, 0);
    }
    /*
     * use this *only* to determine size constraints at startup
     */
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        return this;
    }

    private void redraw() {
        if(gr != null && rp != null && par != null){
            this.paintComponent(gr, rp, par, x, y, w, h, false);
        }
    }
}
