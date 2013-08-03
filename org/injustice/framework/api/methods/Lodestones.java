/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.api.methods;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.api.IMethodProvider;
import org.powerbot.script.wrappers.Tile;

/**
 *
 * @author Suhaib
 */
public class Lodestones extends IMethodProvider {

    public Lodestones(IMethodContext ctx) {
        super(ctx);
    }

    private double getDistance(short[] location, int x, int y) {
        int xdiff = x - location[0];
        int ydiff = y - location[1];
        return Math.sqrt(xdiff * xdiff + ydiff * ydiff);
    }

    private double getDistance(Tile p, int x, int y) {
        int xdiff = x - p.getX();
        int ydiff = y - p.getY();
        return Math.sqrt(xdiff * xdiff + ydiff * ydiff);
    }

    private double distance(final int x1, final int y1, final int x2, final int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public Lodestone getNearestLodestone(final Tile t) {
        Lodestone[] lodestones = Lodestone.values();
        Lodestone closest = null;
        double closestDistance = Double.MAX_VALUE;
        for (Lodestone stone : lodestones) {
            if (stone.isActive(ctx)) {
                double distance = distance(stone.getLocation()[0],
                        stone.getLocation()[1], t.getX(), t.getY());
                if (distance < closestDistance) {
                    closest = stone;
                    closestDistance = distance;
                } else {
                    continue;
                }
            }
        }
        if (closestDistance < ctx.players.local().getLocation().distanceTo(t))
            return closest;
        return null;
    }

    /**
     * Returns the first available lodestone based on the order given. E.g.
     * Lodestones.getMethod(new Lodestone[] { Lodestone.LUMBRIDGE,
     * Lodestone.YANILLE, Lodestone.EDGEVILLE });
     *
     * @param tile   Destination tile
     * @param stones Lodestones to check
     * @return First available lodestone. If no lodestones are available, i.e.
     *         player is closer to destination than lodestone, returns
     *         <tt>null</tt>
     */
    public Lodestone getNearestLodestone(final Tile tile,
                                         Lodestone[] stones) {
        int x = tile.getX();
        int y = tile.getY();
        double myDistance = getDistance(ctx.players.local().getLocation(), x, y) - 54.0;
        for (Lodestone stone : stones) {
            if (stone.isActive(ctx)) {
                double distance = getDistance(stone.getLocation(), x, y);
                if (distance > myDistance) {
                    return null; // You're closer.
                }
                return stone;
            }
        }
        return null; 
    }
}
