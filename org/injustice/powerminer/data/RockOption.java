/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.powerminer.data;

import org.powerbot.script.wrappers.Tile;

/**
 *
 * @author Injustice
 */
public final class RockOption {

    private Rock rock;
    private int radius;
    private Tile startTile;
    private MinerMaster master;
    private boolean hover;

    public RockOption(MinerMaster master, Rock rock, int radius, boolean hover) {
        this.rock = rock;
        this.radius = radius;
        this.master = master;
        this.hover = hover;
    }

    public Rock getRock() {
        return rock;
    }

    public int getRadius() {
        return radius;
    }

    public MinerMaster getMaster() {
        return master;
    }

    public Tile getStartTile() {
        return startTile;
    }

    public void setStartTile(Tile startTile) {
        this.startTile = startTile;
    }
    
    public boolean isHover() {
        return hover;
    }
}
