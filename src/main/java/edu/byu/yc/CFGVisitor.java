package edu.byu.yc;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTVisitor;

public class CFGVisitor extends ASTVisitor {

    public Set<CFG> getCFGs() {
        return new HashSet<>();
    }
}
