/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schwiet.LincolnLog.ui.command;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;

/**
 *
 * @author sethschwiethale
 */
public class CommandDispatch {

    private static final CommandDispatch INSTANCE = new CommandDispatch();
    private LinkedList<Command> commands;
    /*
     * using a rannableQueue to keep everything executing on the same thread
     */
    private final ExecutorService runQueue;
    private static final int HISTORY_LIMIT = 25;
    /*
     * logger
     */
    /**
     * logger for CommandInvoker
     */
    static Logger logger = Logger.getLogger(CommandDispatch.class);

    private CommandDispatch() {
        runQueue = Executors.newSingleThreadExecutor();
        commands = new LinkedList<Command>();
    }

    /**
     * executes the command and pushes it onto the stack
     * @param command
     */
    public synchronized void performCommand(final Command command) {
        runQueue.submit(new Runnable() {
            public void run() {
                command.execute();
                commands.push(command);
                checksize();
                logger.debug(command.getCommandName()+" - Undo Queue: "+commands.size());
            }
        });
    }

    /**
     * if there are any commands on the stack, this will pop the top command
     * and perform it's undo operations
     */
    public synchronized void undo() {
        if (!commands.isEmpty()) {
            runQueue.submit(new Runnable() {
            public void run() {
                logger.debug("undoing: "+commands.peek().getCommandName());
                commands.pop().undo();
            }
        });
        }
    }

    /**
     * returns true if stack is not empty, false otherwise
     * @return
     */
    public boolean canUndo() {
        return !commands.isEmpty();
    }

    /**
     * singleton
     * @return
     */
    public static CommandDispatch getInstance() {
        return INSTANCE;
    }
    /*
     * utility to loose commands after a certain amount of history
     */

    private void checksize() {
        if (commands.size() >= HISTORY_LIMIT) {
            commands.removeFirst();
        }
    }
}
