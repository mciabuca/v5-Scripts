package org.injustice.powerminer;

import java.util.ArrayList;
import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.ActiveScript;
import org.injustice.framework.script.node.StateNode;
import org.injustice.powerminer.data.MinerMaster;
import org.injustice.powerminer.data.Rock;
import org.injustice.powerminer.data.RockOption;
import org.injustice.powerminer.impl.Drop;
import org.injustice.powerminer.impl.Gems;
import org.injustice.powerminer.impl.Mine;
import org.injustice.powerminer.impl.Banking;
import org.injustice.powerminer.impl.Walk;

public class PowerMiner extends ActiveScript {

    private IMethodContext ctx;
    private String state;
    private Rock selectedRock;
    private ArrayList<StateNode> nodes = new ArrayList<>();
    private RockOption rockFilter;

    @Override
    public void start() {
        // GUI
        // GUI.getSelectedRock() = selectedRock
        // if banking add new Branch, set MinerFactory
        int radius = 0;
        boolean noRadius = false;
        final MinerMaster master = MinerMaster.AL_KHARID; // example
        rockFilter = new RockOption(selectedRock, radius, noRadius);
        nodes.add(new Mine(ctx, rockFilter));
        nodes.add(new Drop(ctx, selectedRock));
        nodes.add(new Gems(ctx));
        nodes.add(Walk.createBankPathInstance(master, ctx));
        nodes.add(Walk.createSitePathInstance(master, ctx));
        nodes.add(new Banking(ctx, selectedRock, master));
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
