package org.injustice.miner;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.api.methods.Logger;
import org.injustice.framework.script.ActiveScript;
import org.injustice.framework.script.job.LoopTask;
import org.injustice.framework.script.node.StateNode;
import org.injustice.miner.data.MinerMaster;
import org.injustice.miner.data.Rock;
import org.injustice.miner.data.RockOption;
import org.injustice.miner.impl.Drop;
import org.injustice.miner.impl.Gems;
import org.injustice.miner.impl.Mine;
import org.injustice.miner.impl.Banking;
import org.injustice.miner.impl.Walk;
import org.injustice.miner.util.MinerGUI;
import org.powerbot.event.MessageEvent;
import org.powerbot.event.MessageListener;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.methods.Environment;
import org.powerbot.script.methods.Skills;
import org.powerbot.script.wrappers.Area;
import org.powerbot.script.wrappers.GameObject;

@Manifest(authors = "Injustice",
        name = "Just Miner",
        description = "Mines it all. Anything, anywhere.",
        version = 1.0,
        topic = 1011643)
public class PowerMiner extends ActiveScript implements PaintListener, MouseListener, MessageListener {

    private Logger l;
    private IMethodContext ctx;
    private ArrayList<StateNode> nodes = new ArrayList<>();
    private int oresMined;
    private int startExp;
    private int startLevel;
    // dynamic sig
    private int addOres;
    private int addExp;
    private int session = Math.round(System.currentTimeMillis() / 1000); 
    private long addon;
    // options
    public Rock selectedRock;
    public RockOption rockOption;
    public boolean guiDone;
    public boolean banking;
    public MinerMaster master;
    public String state;

    @Override
    public void start() {
        l = new Logger(font3);
        l.log("Initialising.");
        l.log("GUI");
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MinerGUI(PowerMiner.this).setVisible(true);
                } catch (Exception e) {
                    state = "GUI error";
                    e.printStackTrace();
                }
            }
        });
        while (!guiDone) {
            ctx.sleep.waitFor(guiDone);
        }
        l.log("GUI complete.");
        l.log("Rock selected: " + selectedRock);
        if (banking) {
            l.log("Banking enabled.");
            nodes.add(Walk.createBankPathInstance(master, ctx));
            nodes.add(Walk.createSitePathInstance(master, ctx));
            nodes.add(new Banking(ctx, selectedRock, master));
            l.log("Location selected: " + master.getLocation());
            l.log("Banking at: " + master.getBank());
        }
        l.log("Hover next rock: " + rockOption.isHover());
        rockOption.setStartTile(ctx.players.local().getLocation());
        nodes.add(new Mine(ctx, rockOption));
        nodes.add(new Drop(ctx, selectedRock));
        nodes.add(new Gems(ctx));
        l.log("Start tile: " + rockOption.getStartTile());
        startExp = ctx.skills.getExperience(Skills.MINING);
        l.log("Start exp: " + startExp);
        startLevel = ctx.skills.getRealLevel(Skills.MINING);
        l.log("Start level: " + startLevel);
        l.log("Rocks loaded: " + getRocks(200));
        l.log("Rocks in radius (" + rockOption.getRadius() + "): " + getRocks(rockOption.getRadius()));
    }

    @Override
    public int poll() {
        getContainer().submit(new StateUpdater(ctx));
        for (StateNode node : nodes) {
            if (node.activate()) {
                node.execute();
                state = node.state();
            }
        }
        getContainer().submit(new LoopTask(ctx) {
            @Override
            public int loop() {
                updateDynamicSig(getRuntime() - addon,
                        oresMined - addOres,
                        (ctx.skills.getExperience(Skills.MINING) - startExp) - addExp,
                        session);
                return 1000 * 60 * 15;
            }
        });


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
    public void stop() {
        l.log("Stopping");
        l.log("Updating dynamic sig");
        updateDynamicSig(this.getRuntime() - addon,
                oresMined - addOres,
                (ctx.skills.getExperience(Skills.MINING) - startExp) - addExp,
                session);
        l.log("Please post proggies");
        ctx.sleep.sleep(3000);
        l.remove();
    }

    @Override
    public void repaint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
//        if (guiDone) {
        drawTiles(g);
//        }
        int expGain = ctx.skills.getExperience(Skills.MINING) - startExp;
        int lvlGain = ctx.skills.getRealLevel(Skills.MINING) - startLevel;
        int oresHr = (int) (oresMined * 3600000d / getRuntime());
        int expHr = (int) (expGain * 3600000d / getRuntime());

        g.setStroke(stroke1);
        g.setColor(grey);
        g.fillRoundRect(5, 178, 190, 135, 7, 7);
        g.setColor(Color.GREEN);
        g.setStroke(stroke1);
        g.drawRoundRect(5, 178, 190, 135, 7, 7);
        g.setFont(font1);
        g.drawRect(5, 178, 190, 21);

        g.drawString("Just Mine by Injustice", 8, 196);
        g.setFont(font4);
        if (guiDone) {
            g.drawString("" + rockOption.getRock().name().replace("_", ""), loc.x, loc.y - 10);
        }
        g.setFont(font2);
        g.drawString("v" + this.getClass().getAnnotation(Manifest.class).version(), 176, 196);
        g.setFont(font3);
        g.drawString("Ores mined: " + ctx.misc.format(oresMined, 2), loc.x, loc.y);
        g.drawString("Ores PH: " + ctx.misc.format(oresHr, 2), loc.x + 100, loc.y);
        g.drawString("Level: " + ctx.skills.getRealLevel(Skills.MINING) + " (+" + lvlGain + ")", loc.x + 100, loc.y + 15);
        g.drawString("Time TNL: " + ctx.skills.getTimeToNextLevel(Skills.MINING), loc.x, loc.y + 15);
        g.drawString("Exp gain: " + ctx.misc.format(expGain, 2), loc.x, loc.y + 30);
        g.drawString("Exp PH: " + ctx.misc.format(expHr, 2), loc.x + 100, loc.y + 30);
        g.drawString("Runtime: " + getRuntime(), loc.x, loc.y + 60);
        g.drawString("Status: " + state, loc.x, loc.y + 75);
        g.drawString("Radius: ", 10, 308);
        g.drawString("1  2  3  4  5  6  7  8  9  10  11", 48, 308);
        g.drawString("Toggle debug", loc.x + 100, loc.y + 60);
        g.setColor(grey);
        g.draw(rDebug);
        for (Rectangle r : allRects) {
            if (r != null) {
                g.draw(r);
            }
        }
        g.setColor(Color.GREEN);
        if (getSelectedRectangle() != null) {
            g.draw(getSelectedRectangle());
        }
        g.setColor(green);
        drawMouse(g);
        ctx.misc.percBar(false, loc.x, loc.y + 35, 180, 13,
                ctx.skills.getPercentToNextLevel(Skills.MINING), green, blue, stroke1, g);
        g.setColor(Color.BLACK);
        g.drawString("" + ctx.skills.getPercentToNextLevel(Skills.MINING) + "%", 88, 265);
    }

    private void drawMouse(Graphics g1) {
        Point p = ctx.mouse.getLocation();
        g1.setColor(gradient[0]);
        g1.drawLine(p.x + circleRadius, p.y, p.x + 2000, p.y);
        g1.drawLine(p.x - 2000, p.y, p.x - circleRadius, p.y);
// Vertical
        g1.drawLine(p.x, p.y + circleRadius, p.x, p.y + 2000);
        g1.drawLine(p.x, p.y - 2000, p.x, p.y - circleRadius);
        for (int r = gradient.length - 1; r > 0; r--) {
            int steps = 200 / ((gradient.length - 1) * 2);
            for (int i = steps; i > 0; i--) {
                float ratio = (float) i / (float) steps;
                int red = (int) (gradient[r].getRed() * ratio + gradient[r - 1]
                        .getRed() * (1 - ratio));
                int green = (int) (gradient[r].getGreen() * ratio + gradient[r - 1]
                        .getGreen() * (1 - ratio));
                int blue = (int) (gradient[r].getBlue() * ratio + gradient[r - 1]
                        .getBlue() * (1 - ratio));
                Color stepColor = new Color(red, green, blue);
                g1.setColor(stepColor);
// Horizontal
                g1.drawLine(p.x + circleRadius, p.y, p.x
                        + ((i * 5) + (100 * r)), p.y);
                g1.drawLine(p.x - ((i * 5) + (100 * r)), p.y, p.x
                        - circleRadius, p.y);
// Vertical
                g1.drawLine(p.x, p.y + circleRadius, p.x, p.y
                        + ((i * 5) + (100 * r)));
                g1.drawLine(p.x, p.y - ((i * 5) + (100 * r)), p.x, p.y
                        - circleRadius);
            }
        }
        g1.setColor(outerCircle);
        final long mpt = System.currentTimeMillis() - ctx.mouse.getPressTime();
        if (ctx.mouse.getPressTime() == -1 || mpt >= 200) {
            g1.drawOval(p.x - circleRadius / 3, p.y - circleRadius / 3,
                    circleDiameter / 3, circleDiameter / 3);
        }
        if (mpt < 200) {
            g1.drawLine(p.x - circleRadius, p.y + circleRadius, p.x
                    + circleRadius, p.y - circleRadius);
            g1.drawLine(p.x - circleRadius, p.y - circleRadius, p.x
                    + circleRadius, p.y + circleRadius);
        }
        g1.setColor(outerCircle);
        g1.drawOval(p.x - circleRadius, p.y - circleRadius, circleDiameter,
                circleDiameter);
    }

    private void drawTiles(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        Area area = ctx.misc.makeArea(rockOption.getRadius(), rockOption.getStartTile());
        g.setColor(new Color(0, 0, 0, 50));
//        for (Tile t : area) {
//            t.getMatrix(ctx).draw(g);
//        }
        for (GameObject badRock : ctx.objects.select().id(rockOption.getRock().getIds())) {
            if (badRock != null) {
                if (badRock.isOnScreen()) {
                    g.setColor(Color.RED);
                    badRock.getLocation().getMatrix(ctx).draw(g);
                }
            }
        }
        for (GameObject rock : ctx.objects.within(rockOption.getStartTile(), rockOption.getRadius())) {
            if (rock.isOnScreen()) {
                g.setColor(Color.GREEN);
                rock.getLocation().getMatrix(ctx).draw(g);
            }
        }
        for (GameObject closestRock : ctx.objects.nearest().first()) {
            g.setColor(Color.BLUE);
            closestRock.getLocation().getMatrix(ctx).draw(g);
        }
        g.setColor(Color.BLUE);
        rockOption.getStartTile().getMatrix(ctx).draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int radius = rockOption.getRadius();
        Point p = e.getPoint();
        for (int i = 0; i < allRects.length; i++) {
            if (allRects[i].contains(p) && radius == i + 1) {
                radius = 250;
            } else if (allRects[i].contains(p)) {
                radius = i + 1;
            }
        }
        if (rAll.contains(p)) {
            l.log("[SETTINGS] Rocks in radius (" + radius + "): " + getRocks(radius));
        }
        rockOption.setRadius(radius);
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
        if (me.getMessage().toLowerCase().contains("manage")) {
            oresMined++;
        }
    }

    private void updateDynamicSig(long runtime, int oresMined, int expGained, int session) {
        addOres = this.oresMined;
        addExp = ctx.skills.getExperience(Skills.MINING) - startExp;
        addon = this.getRuntime();
        try {
            URL submit = new URL(
                    "http://injusticescripts.sekaihosting.com/just_mine/input.php?username="
                    + Environment.getDisplayName() + "&runtime=" + runtime / 1000
                    + "&expgain=" + expGained
                    + "&oresmined=" + oresMined
                    + "&key=dfsahucvih7vba484a112348g8nb8ads6vbxv8hn7sfy723qg"
                    + "&session=" + session);
            URLConnection con = submit.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            final BufferedReader rd = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                if (line.toLowerCase().contains("param")) {
                    ctx.log.severe("Dynamic sig failed");
                } else {
                    ctx.log.fine("Dynamic sig succesful");
                }
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class StateUpdater extends LoopTask {

        private String lastState;

        public StateUpdater(IMethodContext ctx) {
            super(ctx);
        }

        @Override
        public int loop() {
            if (!state.equals(lastState)) {
                lastState = state;
                l.log(state);
                return 100;
            }
            return 0;
        }
    }
    // Paint vars
    private final Font font1 = new Font("Tahoma", 0, 15);
    private final Font font2 = new Font("Tahoma", 0, 10);
    private final Font font3 = new Font("Tahoma", 0, 11);
    private final Font font4 = new Font("Tahoma", Font.ITALIC, 9);
    private final Color grey = new Color(0, 0, 0, 100);
    private final BasicStroke stroke1 = new BasicStroke(1);
    private final Point loc = new Point(10, 220);
    private final Color green = new Color(20, 126, 59, 150);
    private final Color blue = new Color(10, 50, 255, 100);
    private final Rectangle rDebug = new Rectangle(110, 269, 65, 13);
    private final Rectangle rAll = new Rectangle(46, 298, 141, 11);
    private final Rectangle r1 = new Rectangle(46, 298, 10, 11);
    private final Rectangle r2 = new Rectangle(56, 298, 12, 11);
    private final Rectangle r3 = new Rectangle(68, 298, 12, 11);
    private final Rectangle r4 = new Rectangle(80, 298, 12, 11);
    private final Rectangle r5 = new Rectangle(92, 298, 12, 11);
    private final Rectangle r6 = new Rectangle(104, 298, 12, 11);
    private final Rectangle r7 = new Rectangle(116, 298, 12, 11);
    private final Rectangle r8 = new Rectangle(128, 298, 12, 11);
    private final Rectangle r9 = new Rectangle(140, 298, 12, 11);
    private final Rectangle r10 = new Rectangle(152, 298, 20, 11);
    private final Rectangle r11 = new Rectangle(172, 298, 15, 11);
    private final Rectangle[] allRects = new Rectangle[]{r1, r2, r3, r4, r5, r6,
        r7, r8, r9, r10, r11
    };
    // Mouse vars
    private final Color[] gradient = new Color[]{new Color(255, 0, 0),
        new Color(255, 0, 255), new Color(0, 0, 255),
        new Color(0, 255, 255), new Color(0, 255, 0),
        new Color(255, 255, 0), new Color(255, 0, 0)};
    private final Color outerCircle = gradient[0];
    private final int circleRadius = 7;
    private final int circleDiameter = circleRadius * 2;

    private Rectangle getSelectedRectangle() {
        for (int i = 0; i < allRects.length; i++) {
            if (rockOption.getRadius() == i + 1) {
                return allRects[i];
            }
        }
        return null;
    }
}
