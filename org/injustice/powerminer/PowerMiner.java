package org.injustice.powerminer;

import java.util.ArrayList;
import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.ActiveScript;
import org.injustice.framework.script.node.StateNode;
import org.injustice.powerminer.data.MinerFactory;
import org.injustice.powerminer.data.Rock;
import org.injustice.powerminer.impl.Drop;
import org.injustice.powerminer.impl.bank.Banking;
import org.injustice.powerminer.impl.bank.Walk;
import org.injustice.powerminer.impl.mine.InteractWithRock;
import org.injustice.powerminer.impl.mine.TurnToRock;

public class PowerMiner extends ActiveScript {

    private IMethodContext ctx;
    private String state;
    private Rock selectedRock;
    private ArrayList<StateNode> nodes = new ArrayList<>();

    @Override
    public void start() {
        // GUI
        // GUI.getSelectedRock() = selectedRock
        // if banking add new Branch, set MinerFactory
        final MinerFactory factory = MinerFactory.AL_KHARID; // example
        nodes.add(new InteractWithRock(ctx, selectedRock));
        nodes.add(new TurnToRock(ctx, selectedRock));
        nodes.add(new Drop(ctx, selectedRock));
        nodes.add(Walk.createBankPathInstance(factory, ctx));
        nodes.add(Walk.createSitePathInstance(factory, ctx));
        nodes.add(new Banking(ctx, selectedRock, factory));
    }

    @Override
    public int poll() {
        for (StateNode node : nodes) {
            if (node.activate()) {
                node.execute();
                state = node.state();
            }
        }
        return 600;
    }
}
