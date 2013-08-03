/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.powerminer.data;

import org.injustice.framework.api.IMethodContext;
import org.powerbot.script.wrappers.Tile;
import org.powerbot.script.wrappers.TilePath;

/**
 *
 * @author Injustice
 */
public enum Path {
    
    AL_KHARID(new Tile[] { new Tile(3298, 3293, 0), new Tile(3298, 3285, 0), new Tile(3299, 3277, 0),
	 new Tile(3299, 3269, 0), new Tile(3298, 3260, 0), new Tile(3295, 3253, 0),
	 new Tile(3293, 3245, 0), new Tile(3292, 3238, 0), new Tile(3288, 3231, 0),
	 new Tile(3283, 3225, 0), new Tile(3283, 3216, 0), new Tile(3282, 3208, 0),
	 new Tile(3281, 3200, 0), new Tile(3281, 3192, 0), new Tile(3280, 3184, 0),
	 new Tile(3276, 3175, 0), new Tile(3276, 3167, 0), new Tile(3271, 3165, 0)}),
    LUMBRIDGE_SWAMP_WEST(new Tile[] { new Tile(3146, 3147, 0), new Tile(3146, 3154, 0), new Tile(3145, 3163, 0),
	 new Tile(3143, 3170, 0), new Tile(3140, 3178, 0), new Tile(3139, 3186, 0),
	 new Tile(3137, 3194, 0), new Tile(3133, 3201, 0), new Tile(3128, 3208, 0),
	 new Tile(3123, 3215, 0), new Tile(3118, 3221, 0), new Tile(3112, 3228, 0),
	 new Tile(3109, 3235, 0), new Tile(3104, 3242, 0), new Tile(3101, 3249, 0),
	 new Tile(3094, 3248, 0) }),
    LUMBRIDGE_SWAMP_EAST(new Tile[] { new Tile(3231, 3151, 0), new Tile(3235, 3158, 0), new Tile(3239, 3165, 0),
	 new Tile(3245, 3169, 0), new Tile(3254, 3173, 0), new Tile(3263, 3173, 0),
	 new Tile(3271, 3170, 0)}),
    VARROCK_EAST(new Tile[] { new Tile(3292, 3373, 0), new Tile(3292, 3381, 0), new Tile(3292, 3390, 0),
	 new Tile(3291, 3397, 0), new Tile(3290, 3406, 0), new Tile(3287, 3414, 0),
	 new Tile(3284, 3421, 0), new Tile(3279, 3428, 0), new Tile(3270, 3428, 0),
	 new Tile(3262, 3428, 0), new Tile(3255, 3425, 0), new Tile(3254, 3421, 0) }),
    VARROCK_WEST(new Tile[] { new Tile(3176, 3378, 0), new Tile(3174, 3385, 0), new Tile(3171, 3393, 0),
	 new Tile(3170, 3402, 0), new Tile(3170, 3409, 0), new Tile(3170, 3417, 0),
	 new Tile(3172, 3426, 0), new Tile(3179, 3429, 0), new Tile(3184, 3435, 0) }),
    BARB_VILL(new Tile[] { new Tile(3087, 3401, 0), new Tile(3094, 3405, 0), new Tile(3098, 3413, 0),
	 new Tile(3103, 3419, 0), new Tile(3111, 3420, 0), new Tile(3119, 3419, 0),
	 new Tile(3127, 3418, 0), new Tile(3135, 3417, 0), new Tile(3143, 3416, 0),
	 new Tile(3151, 3417, 0), new Tile(3159, 3420, 0), new Tile(3166, 3425, 0),
	 new Tile(3174, 3427, 0), new Tile(3182, 3429, 0), new Tile(3184, 3436, 0) });
    
    private Tile[] path;
    
    Path(Tile[] path) {
        this.path = path;
    }
    
    public Tile[] getPath() {
        return path;
    }
    
    
    

}
