/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.powerminer.node.drop;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.job.state.Node;
import org.injustice.framework.script.node.StateNode;
import org.powerbot.script.util.Delay;
import org.powerbot.script.wrappers.Item;

/**
 *
 * @author Injustice
 */
public class Granite extends StateNode {
    
    @Override
    public String state() {
        return "Dropping granite";
    }

    public Granite(IMethodContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        while(ctx.backpack.containsAnyOf(6979, 6981, 6983)) {
            for (Item i : ctx.backpack.select().id(6979, 6981, 6983)) {
                i.interact("Drop");
                Delay.sleep(600);
            }
        }
    }

    @Override
    public boolean activate() {
        return ctx.backpack.containsAnyOf(6979, 6981, 6983);
    }

}
