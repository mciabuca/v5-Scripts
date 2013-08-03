/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.api.methods;

import java.util.ArrayList;
import java.util.List;
import org.injustice.framework.api.IMethodContext;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Area;
import org.powerbot.script.wrappers.Component;
import org.powerbot.script.wrappers.Locatable;
import org.powerbot.script.wrappers.Tile;

/**
 *
 * @author Suhaib
 */
public class Movement extends org.powerbot.script.methods.Movement {

    private Bank banks;

    public Movement(IMethodContext ctx) {
        super(ctx);
        this.banks = new Bank(ctx);
    }

    public static Tile[] getNeighbours(Tile t) {
        final List<Tile> tiles = new ArrayList<Tile>();
        for (int i = -1; i < 2; i++) {
            for (int z = -1; z < 2; z++) {
                final Tile tile = t.derive(i, z);
                tiles.add(tile);
            }
        }
        return tiles.toArray(new Tile[tiles.size()]);
    }

    public boolean inArea(Area a) {
        return a.contains(ctx.players.local());
    }

    public void setRun() {
        if (ctx.movement.getEnergyLevel() > Random.nextInt(40, 60)) {
            ctx.movement.setRunning(true);
        }
    }

    private Tile getClosestOnMap(Tile tile) {
        if (tile.getMatrix(ctx).isOnMap()) {
            return tile;
        }
        final Tile location = ctx.players.local().getLocation();
        tile = tile.derive(-location.getX(), -location.getY());
        final double angle = Math.atan2(tile.getY(), tile.getX());
        return new Tile(
                location.getX() + (int) (16d * Math.cos(angle)),
                location.getY() + (int) (16d * Math.sin(angle)));
    }

    public boolean walk(Locatable loc) {
        setRun();
        Tile t = loc.getLocation();
        Tile dest = ctx.movement.getDestination();
        if (t != null) {
            if (t.getMatrix(ctx).isOnMap()) {
                if (dest.equals(t) || ctx.players.local().getLocation().equals(t)) {
                    return true;
                }
                return ctx.mouse.click(t.getMatrix(ctx).getMapPoint(), true);
            } else {
                if (dest == null
                        || ctx.players.local().getLocation().distanceTo(ctx.movement.getDestination()) < 5) {
                    getClosestOnMap(t).getMatrix(ctx).click(true);
                }
            }
        }
        return false;
    }

    public void walkBank(boolean depositBox) {
        walk(banks.getClosestBank(depositBox));
    }

    public boolean walkToArea(Area a) {
        if (!inArea(a)) {
            return walk(a.getCentralTile().randomize(5, 5));
        } else {
            return true;
        }
    }

    public void stepTowards(Tile t) {
        Tile winner = null;

        for (Tile tile : getSurroundingTiles()) {
            if (winner == null || tile.distanceTo(t) < winner.distanceTo(t)) {
                winner = tile;
            }
        }
        ctx.movement.stepTowards(winner.randomize(2, 2));
    }

    public ArrayList<Tile> getSurroundingTiles() {
        ArrayList<Tile> l = new ArrayList<Tile>();

        int xTiles = ctx.widgets.get(1465, 12).getScrollWidth() / 10;
        int yTiles = ctx.widgets.get(1465, 12).getScrollHeight() / 10;
        int myX = ctx.players.local().getLocation().getX();
        int myY = ctx.players.local().getLocation().getY();
        int myPlane = ctx.players.local().getLocation().getPlane();

        for (int i = 0; i < xTiles; i++) {
            for (int j = 0; j < yTiles; j++) {
                if (new Tile(myX - i, myY - j, myPlane).getMatrix(ctx).isOnMap()) {
                    l.add(new Tile(myX - i, myY - j, myPlane));
                }
                if (new Tile(myX + i, myY - j, myPlane).getMatrix(ctx).isOnMap()) {
                    l.add(new Tile(myX + i, myY - j, myPlane));
                }
                if (new Tile(myX - i, myY + j, myPlane).getMatrix(ctx).isOnMap()) {
                    l.add(new Tile(myX - i, myY + j, myPlane));
                }
                if (new Tile(myX + i, myY + j, myPlane).getMatrix(ctx).isOnMap()) {
                    l.add(new Tile(myX + i, myY + j, myPlane));
                }
            }
        }
        return l;
    }
}
