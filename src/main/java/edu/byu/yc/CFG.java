package edu.byu.yc;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public abstract class CFG {

    public abstract ASTNodeWrapper wrap(ASTNode n);

    public CFG(final MethodDeclaration md) {
    }

    public abstract String getMethodName();

    public abstract Set<Node> successors(final ASTNode n);

    public abstract Set<Node> successors(final Node from);

    public abstract Set<Node> predecessors(final ASTNode n);

    public abstract Set<Node> predecessors(final Node to);

    /**
     * These three overloads are included to suggest helpers that can be useful.
     * 
     * @param from ASTNode
     * @param to ASTNode
     */
    public abstract void addEdge(final ASTNode from, final ASTNode to);
    public abstract void addEdge(final ASTNode from, final Node to);
    public abstract void addEdge(final Node from, final ASTNode to);

    public abstract void addEdge(final Node from, final Node to);

    public abstract void removeEdge(final Node from, final Node to);

    public abstract void succeeds(final Node from, final Node to);

    public abstract Node entryNode();

    public abstract Node exitNode();

    public abstract String buildOverOrderedKeys(Function<List<Node>, String> object);

    public abstract List<Node> getNodes();

}