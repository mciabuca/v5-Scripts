/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.powerminer.data;

import org.powerbot.script.wrappers.Area;
import org.powerbot.script.wrappers.Tile;

/**
 *
 * @author Injustice
 */
public enum Bank {

    DRAYNOR(new Area(new Tile[] { new Tile(3085, 3257, 0), new Tile(3100, 3237, 0)})),
    AL_KHARID(new Area(new Tile[] { new Tile(3085, 3257, 0), new Tile(3100, 3237, 0)})),
    VARROCK_EAST(new Area(new Tile[] { new Tile(3246, 3427, 0), new Tile(3261, 3412, 0)})),
    VARROCK_WEST(new Area(new Tile[] { new Tile(3177, 3448, 0), new Tile(3196, 3430, 0)}));
    private Area bank;

    Bank(Area bank) {
        this.bank = bank;
    }
    
    public Area getArea() {
        return bank;
    }
}
