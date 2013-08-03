/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.powerminer.impl.mine;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.node.StateNode;
import org.injustice.powerminer.data.Rock;
import org.injustice.powerminer.data.RockFilter;
import org.powerbot.script.wrappers.GameObject;

/**
 *
 * @author Injustice
 */
public class TurnToRock extends StateNode {
    private Rock rock;
    
    @Override
    public String state() {
        return "Turning to rock";
    }

    public TurnToRock(IMethodContext ctx, Rock rock) {
        super(ctx);
        this.rock = rock;
    }

    @Override
    public void execute() {
        for (GameObject o : ctx.objects) {
                ctx.camera.turnTo(o);
            }
    }

    @Override
    public boolean activate() {
        for (GameObject o : ctx.objects.select().select(new RockFilter(rock)).nearest().first()) {
                return o.isValid() && o.isOnScreen();
            }
            return false;
    }

}
