/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.api;

import org.injustice.framework.api.methods.*;
import org.powerbot.script.methods.Bank;
import org.powerbot.script.methods.MethodContext;

/**
 *
 * @author Suhaib
 */
public class IMethodContext extends MethodContext {
    public Backpack backpack;
    public Bank bank;
    public Lodestones lodestones;
    public Movement movement;
    public Camera camera;
    public Combat combat;
    public Conditions conditions;
    public Misc misc;
    public Skills skills;
    public Utils utils;
    public Log log;
    public Sleep sleep;
    
    public IMethodContext(MethodContext ctx) {
        super(ctx.getBot());
    }
    
    @Override
    public void init(MethodContext ctx) {
        super.init(ctx);
        this.movement = new Movement(this);
        this.bank = new Bank(this);
        this.lodestones = new Lodestones(this);
        this.backpack = new Backpack(this);
        this.camera = new Camera(this);
        this.combat = new Combat(this);
        this.conditions = new Conditions(this);
        this.misc = new Misc(this);
        this.skills = new Skills(this);
        this.utils = new Utils(this);
        this.log = new Log(this);
        this.sleep = new Sleep(this);
    }
    
}
