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
public class DeleteDivvyCommand implements Command{
    private DivvyManager manager;
    private Divvy divvy;
    /*
     * not accessible
     */
    private DeleteDivvyCommand(){}

    private DeleteDivvyCommand(DivvyManager manager, Divvy divvy){
        this.manager = manager;
        this.divvy = divvy;
    }

    public void execute() {
        manager.removeDivvy(divvy);
    }

    public void undo() {
        manager.addDivvy(divvy);
    }

    public static Command createCommand(DivvyManager manager, Divvy divvy){
        return new DeleteDivvyCommand(manager, divvy);
    }

    public static Command createCommand(Divvy divvy){
        return new DeleteDivvyCommand(DivvyManager.getInstance(), divvy);
    }

    public String getCommandName() {
        return "Delete "+divvy.getName();
    }
}
