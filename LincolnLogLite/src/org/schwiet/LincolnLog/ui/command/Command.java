/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.ui.command;

/**
 *
 * @author sethschwiethale
 */
public interface Command {
    /**
     * should perform appropriate method calls of implementing receiver class
     */
    public void execute() throws Exception;
    /**
     * should do the opposite of whatever execute did
     */
    public void undo() throws Exception;
    /**
     * return a short name for the command
     * @return
     */
    public String getCommandName();
}
