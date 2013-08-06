/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.api.methods;

import org.injustice.framework.api.IMethodContext;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Item;

/**
 *
 * @author Suhaib
 */
public class Backpack extends org.powerbot.script.methods.Backpack {

    public Backpack(IMethodContext arg0) {
        super(arg0);
    }
    
    public void useMouseKeys() {
        for (int col = 0; col < 4; col++) {
            int mouseX = (ctx.backpack.getItemAt(col).getComponent().getCenterPoint().x + Random.nextInt(-10, 10));
            int rowOffset = Random.nextInt(-10, 10);
            int mouseY = (ctx.backpack.getItemAt(col).getComponent().getCenterPoint().y + rowOffset);
            ctx.mouse.move(mouseX, mouseY);
            for (int row = 0; row < 7; row++) {
                ctx.mouse.hop(mouseX, ctx.backpack.getItemAt(row * 4 + col).getComponent().getCenterPoint().y + rowOffset);
                this.sleep(85, 100);
                ctx.mouse.click(false);
                while (!ctx.menu.isOpen()) {
                    this.sleep(85, 100);
                }
                String[] acts = ctx.menu.getItems();
                for (int a = 0; a < acts.length; a++) {
                    if (acts[a].contains("Drop")) {
//                        ctx.mouse.hop(mouseX, ctx.menu.getLocation().y + 20 + a * 20);
                        this.sleep(80, 100);
                        ctx.mouse.click(true);
                    }
                }
            }
        }
    }

    /**
     * Checks if inventory has any of array
     * @param id  ids to check
     * @return <tt>true</tt> if inventory contains any of array, <tt>false</tt> otherwise
     */
    public boolean containsAnyOf(int... id) {
        return !ctx.backpack.select().id(id).isEmpty();
    }
    
    public boolean containsAnyOf(int[]... id) {
        return !ctx.backpack.select().id(id).isEmpty();
    }

    /**
     * Checks if inventory has all of array
     * @param id ids to check
     * @return <tt>true</tt> if inventory contains all of array, <tt>false</tt> otherwise
     */
    public boolean containsAll(int... id) {
        int length = id.length;
        int contained = 0;
        for (int i : id) {
            if (containsAnyOf(i)) {
                contained++;
            }
        }
        return contained == length;
    }

    /**
     * Gets the count of ids
     * @param id ids to check
     * @return count of items if items are valid, -1 otherwise
     */
    public int getCount(int... id) {
        if (isValid(id)) {
            return ctx.backpack.select().id(id).count();
        }
        return -1;
    }

    /**
     * Checks if items are all valid
     * @param items items to check
     * @return <tt>true</tt> if all the items are valid, <tt>false</tt> otherwise
     */
    public boolean isValid(Item... items) {
        int count = 0;
        for (Item item : items) {
            if (item.isValid() && this.containsAnyOf(item.getId())) {
                count++;
            }
        }
        return count == items.length;
    }

    /**
     * Checks if items are all valid
     * @param items items to check
     * @return <tt>true</tt> if all the items are valid, <tt>false</tt> otherwise
     */
    public boolean isValid(int... items) {
        int count = 0;
        for (int i : items) {
            for (Item item : ctx.backpack.select().id(items) ) {
                if (item.isValid() && this.containsAnyOf(i)) {
                    count++;
                }
            }
        }
        return count == items.length;
    }

    /**
     * Checks if the inventory is full
     * @return <tt>true</tt> if inventory is full, <tt>false</tt> otherwise
     */
    public boolean isFull() {
        return ctx.backpack.count() == 28;
    }
    
    
}
