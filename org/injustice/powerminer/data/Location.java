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
public enum Location {
    
    AL_KHARID(new Area(new Tile[]{new Tile(3297, 3317, 0), new Tile(3291, 3297, 0), new Tile(3295, 3282, 0),
            new Tile(3306, 3287, 0), new Tile(3306, 3317, 0), new Tile(3302, 3320, 0)})),
    LUMBRIDGE_SWAMP_WEST(new Area(new Tile[]{new Tile(3144, 3151, 0), new Tile(3142, 3148, 0), new Tile(3142, 3144, 0),
            new Tile(3144, 3142, 0), new Tile(3148, 3142, 0), new Tile(3151, 3147, 0),
            new Tile(3149, 3151, 0)})),
    LUMBRIDGE_SWAMP_EAST(new Area(new Tile[] { new Tile(3219, 3155, 0), new Tile(3238, 3143, 0) })),
    VARROCK_EAST(new Area(new Tile[] { new Tile(3276, 3373, 0), new Tile(3297, 3353, 0) })),
    VARROCK_WEST(new Area(new Tile[] { new Tile(3174, 3380, 0), new Tile(3184, 3380, 0), new Tile(3191, 3373, 0),
	 new Tile(3178, 3360, 0), new Tile(3170, 3367, 0) })),
    BARB_VILL(new Area(new Tile[] { new Tile(3076, 3403, 0), new Tile(3088, 3394, 0) }));
   
    
    private Area area;
    
    Location(Area area) {
        this.area = area;
    }

    public Area getArea() {
        return this.area;
    }
    
    public static Tile startTile;
    public static int radius;
    
}
