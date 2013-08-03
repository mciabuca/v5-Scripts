/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.powerminer.impl.drop;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.job.state.Node;
import org.injustice.framework.script.node.StateNode;
import org.injustice.powerminer.data.Rock;
import org.powerbot.script.util.Delay;

/**
 *
 * @author Injustice
 */
public class Actionbar extends StateNode {
    
    @Override
    public String state() {
        return "Dropping";
    }

    private Rock rock;

    public Actionbar(IMethodContext ctx, Rock rock) {
        super(ctx);
        this.rock = rock;
    }

    @Override
    public void execute() {
        while (!ctx.backpack.select().id(rock.getInvId()).isEmpty()) {
            ctx.keyboard.send("1");
            Delay.sleep(150, 300);
        }

    }

    @Override
    public boolean activate() {
        return ctx.widgets.get(640).getComponent(32).getIndex() == rock.getInvId();
    }
}
