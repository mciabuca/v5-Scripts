/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.api.methods;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.api.IMethodProvider;
import org.powerbot.script.util.Timer;
import org.powerbot.script.wrappers.Component;

/**
 *
 * @author Suhaib
 */
public class Utils extends IMethodProvider {

    public Utils(IMethodContext c) {
        super(c);
    }
    private final int[] freeWorlds = {3, 7, 8, 11, 13, 17, 19, 20, 29,
        33, 34, 38, 41, 43, 57, 61, 80, 81, 108, 120, 135, 136};

    public boolean contains(final int[] array, final int v) {
        for (final int e : array) {
            if (e == v) {
                return true;
            }
        }
        return false;
    }

    public <T> boolean contains(final T[] array, final T v) {
        for (final T e : array) {
            if (e == v || v != null && v.equals(e)) {
                return true;
            }
        }
        return false;
    }

    public boolean isMembersWorld(int world) {
        return Arrays.binarySearch(freeWorlds, world) < 0;
    }

    public int[] removeElement(int[] array, int remove) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i : array) {
            if (i != remove) {
                list.add(i);
            }
        }
        int[] ret = new int[list.size()];
        int i = 0;
        for (Integer e : list) {
            ret[i++] = e.intValue();
        }
        return ret;
    }

    public int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }

    public void previousTeleport() {
        final Component LODESTONE_BUTTON = ctx.widgets.get(1465, 10);
        if (LODESTONE_BUTTON.isValid() && LODESTONE_BUTTON.isVisible()) {
            if (LODESTONE_BUTTON.interact("Previous destination")) {
                sleep(2000, 3000);
                final Timer TIMEOUT = new Timer(15000);
                while (TIMEOUT.isRunning() && ctx.players.local().isInMotion()) {
                    sleep(50, 100);
                }
            }
        }
    }
}
