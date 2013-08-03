/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.api.methods;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.api.IMethodProvider;
import org.powerbot.script.util.SkillData;
import org.powerbot.script.util.Timer;

/**
 *  
 * @author Suhaib
 */
public class Skills extends org.powerbot.script.methods.Skills {
    private SkillData s;

    public Skills(IMethodContext c) {
        super(c);
        s = new SkillData(c);
    }

    public int getPercentToNextLevel(final int index) {
        final int lvl = ctx.skills.getRealLevel(index);
        return getPercentToLevel(index, lvl + 1);
    }

    public int getPercentToLevel(final int index, final int endLvl) {
        final int lvl = ctx.skills.getRealLevel(index);
        if (lvl == 99) {
            return 0;
        }
        final int xpNeeded = org.powerbot.script.methods.Skills.XP_TABLE[endLvl] - org.powerbot.script.methods.Skills.XP_TABLE[lvl];
        if (xpNeeded == 0) {
            return 0;
        }
        final int xpDone = ctx.skills.getExperience(index)
                - org.powerbot.script.methods.Skills.XP_TABLE[lvl];
        return 100 * xpDone / xpNeeded;
    }

    public String getTimeToNextLevel(final int skill) {
        return Timer.format(s.timeToLevel(SkillData.Rate.HOUR, skill));
    }
}
