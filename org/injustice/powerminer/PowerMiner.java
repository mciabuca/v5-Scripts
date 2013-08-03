package org.injustice.powerminer;

import java.util.ArrayList;
import java.util.List;
import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.ActiveScript;
import org.injustice.framework.script.job.state.Branch;
import org.injustice.framework.script.job.state.Node;
import org.injustice.framework.script.node.StateNode;
import org.injustice.powerminer.data.Rock;
import org.injustice.powerminer.node.DropBranch;
import org.injustice.powerminer.node.MineBranch;
import org.injustice.powerminer.node.drop.Actionbar;
import org.injustice.powerminer.node.drop.Gems;
import org.injustice.powerminer.node.drop.Granite;
import org.injustice.powerminer.node.drop.Limestone;
import org.injustice.powerminer.node.drop.Mousekeys;
import org.injustice.powerminer.node.drop.Regular;
import org.injustice.powerminer.node.mine.InteractWithRock;
import org.injustice.powerminer.node.mine.TurnToRock;

public class PowerMiner extends ActiveScript {

    private IMethodContext ctx;
    private String state;
    private Rock selectedRock;
    private Branch mineBranch;
    private Branch dropBranch;
    private List<Branch> branches = new ArrayList<>();

    @Override
    public void start() {
        // GUI
        // GUI.getSelectedRock() = selectedRock
        // if banking add new Branch, set MinerFactory
        mineBranch = new MineBranch(ctx, new StateNode[]{
            new InteractWithRock(ctx),
            new TurnToRock(ctx)}, selectedRock);
        dropBranch = new DropBranch(ctx, new StateNode[]{
            new Actionbar(ctx, selectedRock),
            new Gems(ctx),
            new Granite(ctx),
            new Limestone(ctx),
            new Mousekeys(ctx, selectedRock),
            new Regular(ctx, selectedRock)
        }, selectedRock);

    }

    @Override
    public int poll() {
        for (Branch branch : branches) {
            if (branch.activate()) {
                branch.execute();
                state = branch.state();
            }
        }
        return 600;
    }
}
