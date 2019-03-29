package edu.byu.yc;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;

public class ASTNodeWrapper implements Node {
    public final ASTNode node;
    public final int number;

    public ASTNodeWrapper(ASTNode n, int num) {
        node = n;
        number = num;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ASTNodeWrapper) {
            ASTNodeWrapper nw = (ASTNodeWrapper) o;
            return node.equals(nw.node);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return node.hashCode();
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }

    @Override
    public Set<String> getDefs() {
        return new HashSet<>();
    }

    @Override
    public Set<String> getUses() {
        return new HashSet<>();
    }

}
