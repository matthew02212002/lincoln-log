/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.schwiet.LincolnLog.divvy.commands;

import org.schwiet.LincolnLog.divvy.Divvy;
import org.schwiet.LincolnLog.divvy.DivvyManager;
import org.schwiet.LincolnLog.ui.command.Command;

/**
 *
 * @author sethschwiethale
 */
public class SaveDivvyCommand implements Command{
    private DivvyManager manager;
    private Divvy divvy;
    /*
     * not accessible
     */
    private SaveDivvyCommand(){}

    private SaveDivvyCommand(DivvyManager manager, Divvy divvy){
        this.manager = manager;
        this.divvy = divvy;
    }

    public void execute() {
        manager.addDivvy(divvy);
    }

    public void undo() {
        manager.removeDivvy(divvy);
    }

    public static Command createCommand(DivvyManager manager, Divvy divvy){
        return new SaveDivvyCommand(manager, divvy);
    }

    public static Command createCommand(Divvy divvy){
        return new SaveDivvyCommand(DivvyManager.getInstance(), divvy);
    }

    public String getCommandName() {
        return "Save "+divvy.getName();
    }
}
