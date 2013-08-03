package org.injustice.powerminer.node;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.job.state.InnerNode;
import org.injustice.framework.script.job.state.Node;
import org.injustice.powerminer.data.Rock;
import org.injustice.powerminer.data.RockFilter;
import org.powerbot.script.util.Delay;
import org.powerbot.script.wrappers.GameObject;

public class Mine extends Node {

    private IMethodContext ctx;
    private RockFilter filter;
    private Rock rock;

    public Mine(IMethodContext ctx, Rock rock) {
        super(ctx);
        this.rock = rock;
        this.filter = new RockFilter(this.rock);
    }

    @Override
    public boolean activate() {
        if (ctx.backpack.count() != 28 && !ctx.backpack.containsAnyOf(Rock.gems) && !ctx.backpack.containsAnyOf(Rock.LIMESTONE.getInvId())
                && !ctx.backpack.containsAnyOf(6979, 6981, 6983)) {
            for (GameObject g : ctx.objects.select().select(filter).nearest().first()) {
                if (g.isValid()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void execute() {
        final InnerNode[] nodes = new InnerNode[]{new CheckRun(), new TurnToRock(), new Interact()};
        for (InnerNode node : nodes) {
            if (node.activate()) {
                node.execute();
            }
        }
    }

    private final class CheckRun extends InnerNode {

        @Override
        public boolean activate() {
            return !ctx.movement.isRunning();
        }

        @Override
        public void execute() {
            ctx.movement.setRunning(true);
        }
    }

    private final class TurnToRock extends InnerNode {

        @Override
        public boolean activate() {
            for (GameObject o : ctx.objects.select().select(filter).first()) {
                return o.isValid() && o.isOnScreen();
            }
            return false;
        }

        @Override
        public void execute() {
            for (GameObject o : ctx.objects.first()) {
                ctx.camera.turnTo(o);
            }
        }
    }

    private final class Interact extends InnerNode {

        @Override
        public boolean activate() {
            for (GameObject o : ctx.objects.first()) {
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
}
