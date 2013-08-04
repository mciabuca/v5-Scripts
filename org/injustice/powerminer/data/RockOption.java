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
public final class RockOption {
    private Rock rock;
    private int radius;
    private boolean noRadius;
    
    public RockOption(Rock rock, int radius, boolean noRadius) {
        this.rock = rock;
        this.radius = radius;
        this.noRadius = noRadius;
    }
    
    public Rock getRock(){
        return rock;
    }
    
    public int getRadius() {
        return radius;
    }
    
    public boolean isNoRadius() {
        return noRadius;
    }
}
