/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.api.methods;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.api.IMethodProvider;
import org.powerbot.script.lang.Filter;
import org.powerbot.script.wrappers.Npc;

/**
 *
 * @author Suhaib
 */
public class Combat extends IMethodProvider {

    public Combat(IMethodContext c) {
        super(c);
    }
    
    public double getHP() {
        return 100 * ((31.0 - ctx.widgets.get(748, 5).getHeight()) / 28.0);
    }

    public  boolean isInCombat() {
        return ctx.npcs.select(new Filter<Npc>() {
            @Override
            public boolean accept(Npc npc) {
                return npc.getInteracting() != null &&
                        npc.getInteracting().equals(ctx.players.local()) &&
                        npc.getLevel() > 0 &&
                        npc.getHealthPercent() > 0;
            }
        }) != null;
    }
    
}
