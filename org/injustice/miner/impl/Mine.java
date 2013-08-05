package org.injustice.miner.impl;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.node.StateNode;
import org.injustice.miner.data.Rock;
import org.injustice.miner.data.RockOption;
import org.powerbot.script.util.Delay;
import org.powerbot.script.wrappers.GameObject;

public class Mine extends StateNode {

    private IMethodContext ctx;
    private RockOption rockOption;
    private Rock rock;
    private String state;

    public Mine(IMethodContext ctx, RockOption rockOption) {
        super(ctx);
        this.rock = rockOption.getRock();
        this.rockOption = rockOption;
    }

    @Override
    public boolean activate() {
        if (ctx.backpack.count() != 28 && !ctx.backpack.containsAnyOf(Rock.gems) && !ctx.backpack.containsAnyOf(Rock.LIMESTONE.getInvId())
                && !ctx.backpack.containsAnyOf(6979, 6981, 6983)) {
            for (GameObject g : ctx.objects.select().id(rockOption.getRock().getIds())
                    .within(rockOption.getStartTile(), rockOption.getRadius()).nearest().first()) {
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
                        if (rockOption.isHover()) {
                        for (GameObject secondRock : ctx.objects.limit(1, 1)) {
                            ctx.mouse.move(secondRock);
                            state = "Hovering next rock";
                        }
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
