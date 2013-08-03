/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.api.methods;

import java.util.Arrays;
import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.api.IMethodProvider;
import org.powerbot.script.methods.DepositBox;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;
import org.powerbot.script.wrappers.Tile;

/**
 *
 * @author Suhaib
 */
public class Bank extends org.powerbot.script.methods.Bank {
private Utils utils;
    private static final Tile[] BANK_TILES = new Tile[]{
            new Tile(3149, 3478, 0), new Tile(3187, 3439, 0),
            new Tile(3252, 3420, 0), new Tile(3095, 3493, 0),
            new Tile(2945, 3369, 0), new Tile(3014, 3354, 0),
            new Tile(3093, 3243, 0), new Tile(3269, 3168, 0),
            new Tile(3307, 3120, 0), new Tile(2851, 2953, 0),
            new Tile(2401, 2841, 0), new Tile(2557, 2839, 0),
            new Tile(2666, 2650, 0), new Tile(2212, 2859, 0),
            new Tile(2612, 3093, 0), new Tile(2662, 3159, 0),
            new Tile(2446, 3081, 0), new Tile(2352, 3163, 0),
            new Tile(2656, 3284, 0), new Tile(2616, 3334, 0),
            new Tile(2450, 3479, 0), new Tile(2446, 3422, 0),
            new Tile(2330, 3685, 0), new Tile(2839, 3538, 0),
            new Tile(2892, 3528, 0), new Tile(2724, 3492, 0),
            new Tile(2583, 3420, 0), new Tile(2099, 3919, 0),
            new Tile(2335, 3807, 0), new Tile(2416, 3799, 0),
            new Tile(2619, 3893, 0), new Tile(3446, 3719, 0),
            new Tile(3612, 3371, 0), new Tile(3510, 3479, 0),
            new Tile(3495, 3210, 0), new Tile(3688, 3466, 0),
            new Tile(3381, 3268, 0), new Tile(3429, 2891, 0),
            new Tile(3679, 2981, 0), new Tile(2872, 3417, 0),
            new Tile(2808, 3441, 0)};
    private static final Tile[] BOTH_TILES = {new Tile(3149, 3478, 0),
            new Tile(3187, 3439, 0), new Tile(3252, 3420, 0),
            new Tile(3095, 3493, 0), new Tile(2945, 3369, 0),
            new Tile(3014, 3354, 0), new Tile(3093, 3243, 0),
            new Tile(3269, 3168, 0), new Tile(3307, 3120, 0),
            new Tile(2851, 2953, 0), new Tile(2401, 2841, 0),
            new Tile(2557, 2839, 0), new Tile(2666, 2650, 0),
            new Tile(2212, 2859, 0), new Tile(2612, 3093, 0),
            new Tile(2662, 3159, 0), new Tile(2446, 3081, 0),
            new Tile(2352, 3163, 0), new Tile(2656, 3284, 0),
            new Tile(2616, 3334, 0), new Tile(2450, 3479, 0),
            new Tile(2446, 3422, 0), new Tile(2330, 3685, 0),
            new Tile(2839, 3538, 0), new Tile(2892, 3528, 0),
            new Tile(2724, 3492, 0), new Tile(2583, 3420, 0),
            new Tile(2099, 3919, 0), new Tile(2335, 3807, 0),
            new Tile(2416, 3799, 0), new Tile(2619, 3893, 0),
            new Tile(3446, 3719, 0), new Tile(3612, 3371, 0),
            new Tile(3510, 3479, 0), new Tile(3495, 3210, 0),
            new Tile(3688, 3466, 0), new Tile(3381, 3268, 0),
            new Tile(3429, 2891, 0), new Tile(3679, 2981, 0),
            new Tile(2872, 3417, 0), new Tile(2808, 3441, 0),
            new Tile(3046, 3235, 0)};
    private static final Tile[] DEPOSIT_BOX_TILES = {new Tile(3046, 3235, 0)};
    private Movement movement = null;

    public Bank(MethodContext ctx) {
        super(ctx);
    }

    /**
     * Checks whether closest bank is deposit box.
     *
     * @return <tt>true</tt> if closest bank is deposit box, else <tt>false</tt>
     */
    public boolean isDepositBox() {
        return utils.contains(DEPOSIT_BOX_TILES, getClosestBank(true));
    }

    public boolean depositAllExcept(boolean depositBox, final int... ids) {
        if (depositBox ? !ctx.depositBox.isOpen() : !ctx.bank.isOpen()) {
            return false;
        }
        Arrays.sort(ids);
        for (int i : ids) {
            if (Arrays.binarySearch(ids, i) < 0) {
                final boolean b = depositBox ? ctx.depositBox.deposit(i, 0) : ctx.bank.deposit(i, 0);
                if (!b) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Gets tile of closest ctx.bank.
     *
     * @param depositBox Include deposit boxes in bank search
     * @return tile of closest bank
     */
    public Tile getClosestBank(boolean depositBox) {
        Tile[] banks = BANK_TILES;
        Tile closestTile = null;
        double closestDistance = Double.MAX_VALUE;
        if (depositBox)
            banks = BOTH_TILES;
        for (Tile tile : banks) {
            double distance = tile.distanceTo(ctx.players.local());
            if (distance < closestDistance) {
                closestTile = tile;
                closestDistance = distance;
            }
        }
        return closestTile;
    }

    /**
     * Checks whether player is at a ctx.bank.
     *
     * @param depositBox Include deposit boxes in bank search
     * @return <tt>true</tt> if player is near bank, otherwise <tt>false</tt>
     */
    public boolean atBank(boolean depositBox) {
        return ctx.bank.isPresent()
                || (depositBox && ctx.objects.id(DepositBox.DEPOSIT_BOX_IDS).getNil() != null);
    }
}
