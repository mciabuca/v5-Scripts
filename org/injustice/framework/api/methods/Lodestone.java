/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.api.methods;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Delay;
import org.powerbot.script.util.Timer;
import org.powerbot.script.wrappers.Component;

/**
 *
 * @author Suhaib
 */
public enum Lodestone {
    AL_KHARID(40), ARDOUGNE(41), BURTHORPE(42), CATHERBY(43), DRAYNOR(44), EDGEVILLE(
            45), FALADOR(46), LUMBRIDGE(47), LUNAR_ISLE(39), PORT_SARIM(48), SEERS_VILLAGE(
            49), TAVERLY(50), VARROCK(51), YANILLE(52);
    private static final short[][] LOCATIONS = {{3296, 3184},
            {2634, 3348}, {2899, 3544}, {2831, 3451}, {3105, 3298},
            {3067, 3505}, {2967, 3403}, {3233, 3221}, {3011, 3215},
            {2689, 3482}, {2878, 3442}, {3214, 3376}, {2529, 3094},
            {2085, 3915}};
    private byte child;

    private Lodestone(int child) {
        this.child = (byte) child;
    }

    public byte getChild() {
        return child;
    }

    public short[] getLocation() {
        int loc = child - 40;
        if (loc > 0) {
            return LOCATIONS[loc];
        }
        return LOCATIONS[13]; // Lunar isle
    }

    /*
     * Textures 10101 through 10116 are activated lodestones. Textures 10117
     * through 10132 are deactivated lodestones.
     */
    public boolean isActive(MethodContext ctx) {
        return ctx.widgets.get(1092, child).getTextureId() < 10117;
    }

    public void teleport(MethodContext ctx) {
        Component LODESTONE_BUTTON = ctx.widgets.get(1465, 10);
        if (LODESTONE_BUTTON.isValid() && LODESTONE_BUTTON.isVisible())
            if (LODESTONE_BUTTON.interact("Cast"))
                Delay.sleep(800, 1200);
        final Component DESTINATION_CHOOSE = ctx.widgets.get(1092, 0);
        if (DESTINATION_CHOOSE.isValid() && DESTINATION_CHOOSE.isVisible()) {
            final Component DESTINATION_BUTTON = ctx.widgets.get(1092, child);
            if (DESTINATION_BUTTON.isValid() && DESTINATION_BUTTON.isVisible()) {
                if (DESTINATION_BUTTON.interact("Teleport")) {
                    Delay.sleep(2000, 3000);
                    final Timer TIMEOUT = new Timer(15000);
                    while (TIMEOUT.isRunning() && ctx.players.local().isInMotion()) {
                        Delay.sleep(50, 100);
                    }
                }
            }
        }
    }
}
