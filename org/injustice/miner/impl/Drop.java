/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.miner.impl;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.node.StateNode;
import org.injustice.miner.data.Rock;
import org.powerbot.script.util.Timer;
import org.powerbot.script.wrappers.Action;
import org.powerbot.script.wrappers.Action.Type;
import org.powerbot.script.wrappers.Item;

/**
 *
 * @author Injustice
 */
public class Drop extends StateNode {

    private IMethodContext ctx;
    private Rock rock;
    private String state;
    private final int[] priorityItems = {
        1621, 1617, 1619, 1623, 1625,
        1627, 1629, 1631, 21345, 6979, 6981, 6983
    };
    private Timer failsafeTimer;

    public Drop(IMethodContext ctx, Rock rock) {
        super(ctx);
        this.rock = rock;
        failsafeTimer = new Timer((rock == Rock.GRANITE) ? 45000 : (rock == Rock.LIMESTONE) ? 35000 : 15000);
    }

    @Override
    public boolean activate() {
        return !ctx.backpack.select().id(priorityItems).isEmpty()
                || (ctx.backpack.isFull() && !ctx.backpack.select().id(rock.getInvId()).isEmpty());
    }

    @Override
    public void execute() {
        if (rock == Rock.GRANITE) {
            failsafeTimer.reset();
            state = "Dropping granite";
            for (Item i : ctx.backpack.select().id(6979, 6981, 6983)) { // Granite has different sizes
                i.interact("Drop");
                ctx.sleep.sleep(500, 600);
                failsafeCheck();
            }
        } else {
            for (Action a : ctx.combatBar.getActions()) {
                if (a.getType() == Type.ITEM && a.getId() == rock.getInvId()) {
                    failsafeTimer.reset();
                    if (rock == Rock.LIMESTONE) {
                        state = "Dropping limestone";
                        a.getComponent().interact("Drop");  // Default action is craft
                        failsafeCheck();
                        ctx.sleep.sleep(500, 600);
                    } else {
                        while (a.getId() == rock.getInvId()) {
                            state = "Dropping " + a.getComponent().getItemName();
                            ctx.keyboard.send(a.getBind());
                            failsafeCheck();
                            ctx.sleep.sleep(150, 300);
                        }
                    }
                }
            }
        }

    }

    @Override
    public String state() {
        return state;
    }

    private void failsafeCheck() {
        if (!failsafeTimer.isRunning()) {
            state = "Failsafe drop";
            for (Item i : ctx.backpack.select().id(rock.getInvId())) {
                i.interact("Drop");
                ctx.sleep.sleep(500, 600);
            }
        }
    }
}
