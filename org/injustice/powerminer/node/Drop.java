/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.injustice.powerminer.node;

import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.script.job.state.InnerNode;
import org.injustice.framework.script.job.state.Node;

/**
 *
 * @author Injustice
 */
public class Drop extends Node {

    private IMethodContext ctx;
    
    public Drop(IMethodContext ctx) {
        super(ctx);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.isFull();
    }

    @Override
    public void execute() {
        
    }
    
    private final class Actionbar extends InnerNode {

        @Override
        public boolean activate() {
            return false;
        }

        @Override
        public void execute() {
        }
        
    }
    
    private final class Mousekeys extends InnerNode {

        @Override
        public boolean activate() {
            return false;
        }

        @Override
        public void execute() {
        }
        
    }
    
    private final class Regular extends InnerNode {

        @Override
        public boolean activate() {
            return false;
        }

        @Override
        public void execute() {
        }
    }
    private final class Gems extends InnerNode {

        @Override
        public boolean activate() {
            return false;
        }

        @Override
        public void execute() {
        }
    }
    private final class Granite extends InnerNode {

        @Override
        public boolean activate() {
            return false;
        }

        @Override
        public void execute() {
        }
    }
    private final class Limestone extends InnerNode {

        @Override
        public boolean activate() {
            return false;
        }

        @Override
        public void execute() {
        }
    }
    private final class NoRocks extends InnerNode {

        @Override
        public boolean activate() {
            return false;
        }

        @Override
        public void execute() {
        }
    }

}
