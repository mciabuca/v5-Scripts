/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.script.job.state;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Injustice
 */
public class Tree {

    private final Queue<Node> nodes = new ConcurrentLinkedQueue<>();
    private final AtomicReference<Node> currentNode = new AtomicReference<>();

    public Tree(Node[] nodes) {
        this.nodes.addAll(Arrays.asList(nodes));
    }

    public final synchronized Node state() {
        Node current = currentNode.get();
        if (current != null) {
            if (current.isAlive()) {
                return null;
            }
            if (current.activate()) {
                return current;
            }
        }

        for (Node next : nodes) {
            if (next != null && next.activate()) {
                return next;
            }
        }
        return null;
    }

    public final void set(Node node) {
        currentNode.set(node);
    }

    public final Node get() {
        return currentNode.get();
    }
}
