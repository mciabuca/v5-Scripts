/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.api.methods;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.api.IMethodProvider;
import org.powerbot.script.util.Timer;

/**
 *
 * @author Suhaib
 */
public class Log extends IMethodProvider {
    private Logger log = Logger.getLogger(this.getClass().getSimpleName());

    public Log(IMethodContext ctx) {
        super(ctx);
    }

    public void all(String s) {
        log.log(Level.ALL, "[" + Timer.format(System.currentTimeMillis()) + "]" + s);
    }

    public void severe(String s) {
        log.log(Level.SEVERE, "[" + Timer.format(System.currentTimeMillis()) + "]" + s);
    }

    public void warn(String s) {
        log.log(Level.WARNING, "[" + Timer.format(System.currentTimeMillis()) + "]" + s);
    }

    public void fine(String s) {
        log.log(Level.FINE, "[" + Timer.format(System.currentTimeMillis()) + "]" + s);
    }

    public void finer(String s) {
        log.log(Level.FINER, "[" + Timer.format(System.currentTimeMillis()) + "]" + s);
    }

    public void finest(String s) {
        log.log(Level.FINEST, "[" + Timer.format(System.currentTimeMillis()) + "]" + s);
    }

    public void config(String s) {
        log.log(Level.CONFIG, "[" + Timer.format(System.currentTimeMillis()) + "]" + s);
    }

    public void info(String s) {
        log.log(Level.INFO, "[" + Timer.format(System.currentTimeMillis()) + "]" + s);
    }
}
