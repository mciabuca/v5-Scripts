/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.powerminer.node.mine;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.node.StateNode;
import org.powerbot.script.wrappers.GameObject;

/**
 *
 * @author Injustice
 */
public class TurnToRock extends StateNode {
    
    @Override
    public String state() {
        return "Turning to rock";
    }

    public TurnToRock(IMethodContext ctx) {
        super(ctx);
    }

    @Override
    public void execute() {
        for (GameObject o : ctx.objects) {
                ctx.camera.turnTo(o);
            }
    }

    @Override
    public boolean activate() {
        for (GameObject o : ctx.objects) {
                return o.isValid() && o.isOnScreen();
            }
            return false;
    }

}
