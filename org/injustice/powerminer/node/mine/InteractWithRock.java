/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.powerminer.node.mine;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.job.state.Node;
import org.injustice.framework.script.node.StateNode;
import org.injustice.powerminer.data.Rock;
import org.powerbot.script.util.Delay;
import org.powerbot.script.wrappers.GameObject;

/**
 *
 * @author Injustice
 */
public class InteractWithRock extends StateNode {
    
    @Override
    public String state() {
        return "Clicking rock";
    }

    public InteractWithRock(IMethodContext ctx) {
        super(ctx);
    }

    @Override
        public boolean activate() {
            for (GameObject o : ctx.objects) {
                return o.isOnScreen() && o.isValid();
            }
            return false;
        }

        @Override
        public void execute() {
            for (GameObject o : ctx.objects) {
                if (o.interact("Mine")) {
                    for (int i = 0; i < 10 && ctx.players.local().getAnimation() == -1; i++)
                        Delay.sleep(1000, 1200);
                    for (int i = 0; i < 10 && ctx.players.local().getAnimation() != -1; i++)
                        Delay.sleep(1000, 1200);
                }
            }
        }

}
