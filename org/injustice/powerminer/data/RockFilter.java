/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.powerminer.data;

import org.powerbot.script.lang.Filter;
import org.powerbot.script.wrappers.GameObject;
/**
 *
 * @author Injustice
 */
public final class RockFilter implements Filter<GameObject> {
    private Rock rock;

    @Override
    public boolean accept(GameObject o) {
        if (o.isValid()) {
            for (int id : rock.getIds()) {
                if (o.getId() == id) {
                    if (o.getLocation().distanceTo(Location.startTile) < Location.radius) {
                        for (int i : Rock.depletedGemRocks) {
                            if (o.getId() != i) {
                                return true;
                            }
                        }

                    }
                }
            }
        }
        return false;
    }
    
    public RockFilter(Rock rock) {
        this.rock = rock;
    }
}
