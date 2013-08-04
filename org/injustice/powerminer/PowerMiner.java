package org.injustice.powerminer;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import org.injustice.powerminer.util.GUI;
import org.powerbot.event.MessageEvent;
import org.powerbot.event.MessageListener;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.lang.Filter;
import org.powerbot.script.wrappers.GameObject;

@Manifest(authors = "Injustice",
        name = "Just Miner",
        description = "Mines it all. Anything, anywhere.",
        version = 1.0,
        topic = 1011643)

public class PowerMiner extends ActiveScript implements PaintListener, MouseListener, MessageListener {

    private IMethodContext ctx;
    private ArrayList<StateNode> nodes = new ArrayList<>();
    private int startExp;
    private int startLevel;
    protected Rock selectedRock;
    protected RockOption rockOption;
    protected boolean guiDone;
    protected boolean banking;
    protected MinerMaster master;
    protected String state;

    @Override
    public void start() {
        ctx.log.info("Initialising.");
        ctx.log.config("GUI");
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new GUI().frame.setVisible(true);
                } catch(Exception e) {
                    state = "GUI error";
                    e.printStackTrace();
                }
            }
        });
        while(!guiDone) {
            ctx.sleep.waitFor(guiDone);
        }
        ctx.log.info("GUI complete.");
        ctx.log.config("Rock selected: " + selectedRock);
        if (banking) {
            ctx.log.config("Banking enabled.");
            nodes.add(Walk.createBankPathInstance(master, ctx));
            nodes.add(Walk.createSitePathInstance(master, ctx));
            nodes.add(new Banking(ctx, selectedRock, master));
            ctx.log.config("Location selected: " + master.getLocation());
            ctx.log.config("Banking at: " + master.getBank());
        }
        ctx.log.config("Hover next rock: " + rockOption.isHover());
        rockOption.setStartTile(ctx.players.local().getLocation());
        nodes.add(new Mine(ctx, rockOption));
        nodes.add(new Drop(ctx, selectedRock));
        nodes.add(new Gems(ctx));
    }

    @Override
    public int poll() {
        for (StateNode node : nodes) {
            if (node.activate()) {
                node.execute();
                state = node.state();
            }
        }
        if (!ctx.movement.isRunning()) {
            state = "Enabling run";
            ctx.movement.setRunning(true);
        }
        return 600;
    }
    
     private int getRocks(final int radius) {
        return ctx.objects.select().id(selectedRock.getIds()).within(radius).size();
    }

    @Override
    public void repaint(Graphics grphcs) {
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void messaged(MessageEvent me) {
    }
}
