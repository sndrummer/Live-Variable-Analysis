package edu.byu.yc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LiveVariableAnalyzer extends LiveVariableAnalysis {

    private static Logger logger = LoggerFactory.getLogger(LiveVariableAnalysis.class);
    private CFG cfg;
    private Set<String> liveVariables = new HashSet<>();

    //private Map<Node, Boolean> nodeTraversedMap = new HashMap<>();
    private Set<Node> nodesVisited = new HashSet<>();

    //private Map<Node, Set<String>> nodeToEntrySet;

    public LiveVariableAnalyzer(CFG cfg) {
        super(cfg);
        this.cfg = cfg;
    }

    @Override
    public Map<Node, Set<String>> analyze() {
        Map<Node, Set<String>> nodeToLiveOnEntryVariables = new HashMap<>();
        //Start from the end of the CFG
        Node exitNode = cfg.exitNode();
        nodeToLiveOnEntryVariables.put(exitNode, new HashSet<String>());

        traverseCFGLiveNodes(exitNode);

        return nodeToLiveOnEntryVariables;
    }

//
//    private void traverseCFGLiveNodesAlt(Node node, int predecessorsVisited) {
//        Node currentNode = node;
//
//        while (currentNode != null) {
//            Set<Node> predecessors = getPredecessors(currentNode);
//            if (predecessors == null || predecessors.isEmpty()) {
//                return;
//            }
//            for (Node predecessor : predecessors) {
//                addDefinitions(predecessor.getDefs());
//                addUses(predecessor.getUses());
//            }
//
//            if (!allPredecessorsVisited(currentNode, predecessorsVisited)) {
//                return;
//            }
//
//            for (Node predecessor : predecessors) {
//                predecessorsVisited++;
//                traverseCFGLiveNodesAlt(predecessor, predecessorsVisited);
//            }
//
//            currentNode =
//        }
//
//
//
//
//
//
//        while (currentNode != null) {
//            for (Node predecessor : predecessors) {
//                addDefinitions(predecessor.getDefs());
//                addUses(predecessor.getUses());
//            }
//
//            if (predecessors.size() == 1) {
//                Iterator iter = predecessors.iterator();
//                currentNode = (Node) iter.next();
//            }
//            else {
//
//            }
//
//            nodesVisited.add(currentNode);
//            for (Node predecessor : cfg.predecessors(currentNode)) {
//                if (!nodeVisited(currentNode)) {
//                    currentNode = predecessor;
//                }
//            }
//        }
//    }

    private void traverseCFGLiveNodes(Node node) {
        Node currentNode = node;
        Set<Node> predecessors = getPredecessors(currentNode);
        while (currentNode != null) {
            for (Node predecessor : predecessors) {
                addDefinitions(predecessor.getDefs());
                addUses(predecessor.getUses());
            }

            if (predecessors.size() == 1) {
                Iterator iter = predecessors.iterator();
                currentNode = (Node) iter.next();
            }

            nodesVisited.add(currentNode);
            for (Node predecessor : predecessors) {
                if (!nodeVisited(currentNode)) {
                    currentNode = predecessor;
                }
            }
        }
    }

    private boolean allSuccessorsVisited(Node node, int successorsVisited) {
        int successors = cfg.successors(node).size();
        if (successorsVisited > successors) {
            throw new RuntimeException("Visited more than there are successors");
        }
        return successors == successorsVisited;
    }

    private boolean allPredecessorsVisited(Node node, int predecessorsVisited) {
        int predecessors = getPredecessors(node).size();
        if (predecessorsVisited > predecessors) {
            throw new RuntimeException("Visited more than there are predecessors");
        }
        return predecessors == predecessorsVisited;
    }

    /**
     * Add the definitions and kill any previous live variables
     *
     * @param definitions
     */
    private void addDefinitions(Set<String> definitions) {
        for (String definition : definitions) {
            if (liveVariables.contains(definition)) {
                logger.info("Live Variable: {}, killed", definition);
                liveVariables.remove(definition);
            }
        }
    }

    /**
     * Add the uses to the live variables, since they are used, they must be defined in predecessors
     *
     * @param uses String representations of the variables that are used
     */
    private void addUses(Set<String> uses) {
        liveVariables.addAll(uses);
    }

    private Set<Node> getPredecessors(Node node) {
        return cfg.predecessors(node);
    }

    private boolean nodeVisited(Node node) {
        return nodesVisited.contains(node);
    }
}

