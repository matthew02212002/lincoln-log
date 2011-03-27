/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schwiet.LincolnLog.ui.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import org.schwiet.LincolnLog.ui.color.SelectedCellColors;
import org.schwiet.LincolnLog.ui.color.UnselectedCellColors;
import org.schwiet.LincolnLog.ui.delegates.TransactionTableUI;
import org.schwiet.spill.painter.SchemePainter;
import org.schwiet.spill.painter.colorScheme.ColorScheme;

/**
 * Creates a border for a {@link JViewport} that draws a striped background
 * corresponding to the row positions of the given {@link JTable}.
 */
class TransactionTableViewportBorder extends AbstractBorder implements
        ListSelectionListener, PropertyChangeListener {

    private final JViewport fViewport;
    private final JTable fTable;
    private final Color fStripeColor;
    //TODO
    private boolean paintVerticalLines = false;

    //need this for offset
    private int headerHeight;
    //convenience field
    private int rowHeight;


    TransactionTableViewportBorder(JViewport viewport, JTable table, Color stripeColor) {
        fViewport = viewport;
        fTable = table;
        fStripeColor = stripeColor;
        fTable.getSelectionModel().addListSelectionListener(this);
        fTable.addPropertyChangeListener(this);
        headerHeight = (int)fTable.getTableHeader().getPreferredSize().getHeight();
        rowHeight =fTable.getRowHeight();
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width,
            int height) {
        paintRows(g);
        if (paintVerticalLines) {
            paintVerticalGridLines(g, y, height);
        }
    }

    private void paintRows(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        // get the row index at the top of the clip bounds (the first row
        // to paint).
        Rectangle clip = g.getClipBounds();
        Point viewPosition = fViewport.getViewPosition();
        // TODO figure out how to honor the beginning of clip region.
        // Point viewPostionWithClip = new Point(viewPosition.x + clip.x,
        // viewPosition.y + clip.y);
        int rowAtPoint = fTable.rowAtPoint(viewPosition);
        // get the y coordinate of the first row to paint. if there are no
        // rows in the table, start painting at the top of the supplied
        // clipping bounds.
        int topY = rowAtPoint < 0 ? headerHeight
                : fTable.getCellRect(rowAtPoint, 0, true).y - viewPosition.y - (rowHeight-headerHeight);
        // create a counter variable to hold the current row. if there are no
        // rows in the table, start the counter at 0.
        int currentRow = rowAtPoint < 0 ? 0 : rowAtPoint;
        while (topY < clip.y + clip.height) {
            int bottomY = topY + fTable.getRowHeight();
//                    currentRow == 0 ? topY + fTable.getTableHeader().getHeight()
//                    : topY + fTable.getRowHeight();
            g2.translate(0, topY);
            if (fTable.isRowSelected(currentRow - 1)) {
                TableUtils.getSelectedCellPainter().paint(g2, fViewport, clip.width, bottomY - topY);
                TransactionTableUI ui = (TransactionTableUI) fTable.getUI();
                Border border = ui.getSelectedRowBorder();
                border.paintBorder(fViewport, g2, 0, 0, fViewport.getWidth(),
                        fTable.getRowHeight());
            }else{
                TableUtils.getTableCellPainter().paint(g2, fViewport, clip.width, bottomY - topY);
            }

            g2.translate(0, -topY);
            topY = bottomY;
            currentRow++;
        }
        g2.dispose();
    }

    private Color getRowColor(int row) {
        if (fTable.isRowSelected(row - 1)) {
            return fTable.getSelectionBackground();
        }
        return row % 2 == 0 ? fTable.getBackground() : fStripeColor;
    }

    private void paintVerticalGridLines(Graphics g, int y, int height) {
        // paint the column grid dividers for the non-existent rows.
        int x = 0 - fViewport.getViewPosition().x;
        g.setColor(fTable.getGridColor());
        for (int i = 0; i < fTable.getColumnCount(); i++) {
            TableColumn column = fTable.getColumnModel().getColumn(i);
            // increase the x position by the width of the current column.
            x += column.getWidth();
            // draw the grid line (not sure what the -1 is for, but BasicTableUI
            // also does it.source
            g.drawLine(x - 1, y, x - 1, y + height);
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        fViewport.repaint();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource().equals(fTable)) {
            if (evt.getPropertyName().equals("selectionModel")) {
                ListSelectionModel oldModel = (ListSelectionModel) evt.getOldValue();
                ListSelectionModel newModel = (ListSelectionModel) evt.getNewValue();
                oldModel.removeListSelectionListener(this);
                newModel.addListSelectionListener(this);
            }
        }
    }
}
