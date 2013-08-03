/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.powerminer.impl.mine;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.job.state.Node;
import org.injustice.framework.script.node.StateNode;
import org.injustice.powerminer.data.Rock;
import org.injustice.powerminer.data.RockFilter;
import org.powerbot.script.util.Delay;
import org.powerbot.script.wrappers.GameObject;

/**
 *
 * @author Injustice
 */
public class InteractWithRock extends StateNode {
    private Rock rock;
    private final int[] unacceptableItems =  {
        1621, 1617, 1619, 1623, 1625, 
        1627, 1629, 1631, 21345, 6979, 6981, 6983 
    };

    @Override
    public String state() {
        return "Clicking rock";
    }

    public InteractWithRock(IMethodContext ctx, Rock rock) {
        super(ctx);
        this.rock =rock;
    }

    @Override
    public boolean activate() {
        if (ctx.backpack.isFull() || ctx.backpack.containsAnyOf(unacceptableItems)) 
            return false;
        for (GameObject o : ctx.objects.select().select(new RockFilter(rock)).nearest().first()) {
            return o.isOnScreen() && o.isValid();
        }
        return false;
    }

    @Override
    public void execute() {
        for (GameObject o : ctx.objects) {
            if (o.interact("Mine")) {
                for (int i = 0; i < 10 && ctx.players.local().getAnimation() == -1; i++) {
                    Delay.sleep(1000, 1200);
                }
                for (int i = 0; i < 10 && ctx.players.local().getAnimation() != -1; i++) {
                    Delay.sleep(1000, 1200);
                }
            }
        }
    }
}
