/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.miner.impl;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.node.StateNode;
import org.injustice.miner.data.Rock;
import org.powerbot.script.wrappers.Item;

/**
 *
 * @author Injustice
 */
public class Gems extends StateNode {
    
    @Override
    public String state() {
        return "Dropping gem";
    }

    public Gems(IMethodContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        for (Item i : ctx.backpack) {
            i.interact("Drop");
            ctx.sleep.sleep(500, 600);
        }
    }

    @Override
    public boolean activate() {
        return !ctx.backpack.select().id(Rock.gems).isEmpty();
    }

}
