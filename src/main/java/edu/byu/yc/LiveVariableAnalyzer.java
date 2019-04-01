package edu.byu.yc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class LiveVariableAnalyzer extends LiveVariableAnalysis {

    private static Logger logger = LoggerFactory.getLogger(LiveVariableAnalysis.class);
    private CFG cfg;
    private Set<String> liveVariables = new HashSet<>();
    private Map<Node, Set<String>> nodeToLiveOnEntryVariables = new HashMap<>();

    private Map<Node, Integer> nodeToSuccessorsCovered = new HashMap<>();
    private Set<Node> nodesVisited = new HashSet<>();

    private Map<Node, Integer> nodeExitVisits = new HashMap<>();

    //private Map<Node, Set<String>> nodeToEntrySet;

    public LiveVariableAnalyzer(CFG cfg) {
        super(cfg);
        this.cfg = cfg;
    }

    @Override
    public Map<Node, Set<String>> analyze() {

        //Start from the end of the CFG
        Node exitNode = cfg.exitNode();
        traverseCFGLiveNodes(exitNode, new HashSet<>());

        return nodeToLiveOnEntryVariables;
    }

    /**
     * Recursively traverse the CFG backwards to gather information on the live variables
     *
     * @param node
     */
    private void traverseCFGLiveNodes(Node node, Set<String> liveVariables) {
        logger.debug("NOW VISITING NODE {}", node);
        Set<Node> predecessors = getPredecessors(node);
        boolean added = false;
        //logger.debug("Traversing");
        if (node != null) {
            for (Node predecessor : predecessors) {
                addDefinitions(predecessor.getDefs(), liveVariables);
                addUses(predecessor, predecessor.getUses(), liveVariables);
            }

            nodesVisited.add(node);
            for (Node predecessor : predecessors) {
                if (!nodeVisited(predecessor)) {
                    node = predecessor;
                    Set<String> nextLiveVariables = new HashSet<>(nodeToLiveOnEntryVariables.get(node));
                    if (canVisit(node)) {
                        traverseCFGLiveNodes(node, nextLiveVariables);
                    }

                }
            }
        }
    }

    private boolean canVisit(Node node) {

        boolean canVisit = false;
        Integer visits = nodeToSuccessorsCovered.get(node);
        if (visits == null) visits = 0;
        visits++;
        nodeToSuccessorsCovered.put(node, visits);

        int successors = cfg.successors(node).size();

        if (visits > successors) {
            throw new RuntimeException("Visited more than there are successors");
        }

        canVisit = visits == successors;
        if (!canVisit) {
            logger.debug("CANNOT VISIT {} only {} visits out of {}", node, visits, successors);
        }
        return visits == successors;
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
    private void addDefinitions(Set<String> definitions, Set<String> liveVariables) {
        for (String definition : definitions) {
            if (liveVariables.contains(definition)) {
                logger.info("Live Variable: {}, killed", definition);
                liveVariables.remove(definition);
            }
        }
    }

    /**
     * @return boolean whether or not uses have been added
     */
    private void addUses(Node node, Set<String> uses, Set<String> liveVariables) {
        Set<String> liveVariablesOfNode = new HashSet<>();
        liveVariablesOfNode.addAll(liveVariables);
        liveVariablesOfNode.addAll(uses);
        if (nodeToLiveOnEntryVariables.get(node) != null) {
            liveVariablesOfNode.addAll(nodeToLiveOnEntryVariables.get(node));
        }
        nodeToLiveOnEntryVariables.put(node, liveVariablesOfNode);
        logger.info("Putting Node: {} --> {}", node, nodeToLiveOnEntryVariables.get(node));
    }


    private Set<Node> getPredecessors(Node node) {
        return cfg.predecessors(node);
    }

    private boolean nodeVisited(Node node) {
        return nodesVisited.contains(node);
    }
}

