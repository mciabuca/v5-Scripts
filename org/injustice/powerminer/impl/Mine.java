package org.injustice.powerminer.impl;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.node.StateNode;
import org.injustice.powerminer.data.Rock;
import org.injustice.powerminer.data.RockOption;
import org.powerbot.script.util.Delay;
import org.powerbot.script.wrappers.GameObject;

public class Mine extends StateNode {

    private IMethodContext ctx;
    private RockOption filter;
    private Rock rock;
    private String state;

    public Mine(IMethodContext ctx, RockOption rockFilter) {
        super(ctx);
        this.rock = rockFilter.getRock();
        this.filter = rockFilter;
    }

    @Override
    public boolean activate() {
        if (ctx.backpack.count() != 28 && !ctx.backpack.containsAnyOf(Rock.gems) && !ctx.backpack.containsAnyOf(Rock.LIMESTONE.getInvId())
                && !ctx.backpack.containsAnyOf(6979, 6981, 6983)) {
            for (GameObject g : ctx.objects.select().id(filter.getRock().getIds()).within(filter.getRadius()).nearest().first()) {
                if (g.isValid()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void execute() {
        for (GameObject rock : ctx.objects) {
            if (rock.isValid()) {
                if (rock.isOnScreen()) {
                    state = "Clicking rock";
                    if (rock.interact("Mine")) {
                        state = "Sleeping";
                        for (int i = 0; i < 10 && ctx.players.local().getAnimation() == -1; i++) {
                            Delay.sleep(600, 650);
                        }
                        Delay.sleep(600);
                        for (int i = 0; i < 10 && ctx.players.local().getAnimation() != -1; i++) {
                            Delay.sleep(600, 650);
                        }
                    }
                } else {
                    state = "Turning to rock";
                    ctx.camera.turnTo(rock);
                }
            }
        }
    }

    @Override
    public String state() {
        return state;
    }
}
