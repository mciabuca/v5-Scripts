/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.powerminer.impl.bank;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.node.StateNode;
import org.injustice.powerminer.data.Bank;
import org.injustice.powerminer.data.MinerFactory;
import org.injustice.powerminer.data.Rock;

/**
 *
 * @author Injustice
 */
public class Banking extends StateNode {
    private Rock rock;
    private Bank bank;
    private final int[] pickaxes = {1265, 1267, 1269, 1271, 1273, 1275, 15259};

    public Banking(IMethodContext ctx, Rock rock, MinerFactory factory) {
        super(ctx);
        this.rock = rock;
        this.bank = factory.getBank();
    }

    @Override
    public void execute() {
        if (ctx.bank.open()) {
            if (ctx.backpack.select().id(pickaxes).isEmpty()) {
                ctx.bank.depositInventory();
                ctx.bank.close();                
            } else {
                ctx.bank.deposit(rock.getInvId(), org.powerbot.script.methods.Bank.Amount.ALL);
            }
        }
    }

    @Override
    public boolean activate() {
        return bank.getArea().contains(ctx.players.local());
    }

    @Override
    public String state() {
        return "Banking";
    }

}
