package edu.byu.yc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;

import java.util.Map;
import java.util.Set;

public class LiveVariableAnalyzer extends LiveVariableAnalysis {

    private static Logger logger = LoggerFactory.getLogger(LiveVariableAnalysis.class);
    private CFG cfg;
    private Map<Node, Set<String>> nodeToLiveVariables = new HashMap<>();

    private Map<Node, Integer> nodeToSuccessorsCovered = new HashMap<>();
    private Set<Node> nodesVisited = new HashSet<>();

    public LiveVariableAnalyzer(CFG cfg) {
        super(cfg);
        this.cfg = cfg;
    }

    @Override
    public Map<Node, Set<String>> analyze() {
        //Start from the end of the CFG
        Node exitNode = cfg.exitNode();
        traverseCFGLiveNodes(exitNode, new HashSet<>());

        return nodeToLiveVariables;
    }

    /**
     * Recursively traverse the CFG backwards to gather information on the live variables
     *
     * @param node          Node to traverse recursively
     * @param liveVariables Set of the live variables from the successor node
     */
    private void traverseCFGLiveNodes(Node node, Set<String> liveVariables) {
        logger.debug("NOW VISITING NODE {}", node);
        Set<Node> predecessors = getPredecessors(node);

        if (node != null) {
            for (Node predecessor : predecessors) {
                addDefinitions(predecessor.getDefs(), liveVariables);
                addUses(predecessor, predecessor.getUses(), liveVariables);
            }

            nodesVisited.add(node);
            for (Node predecessor : predecessors) {
                if (!nodeVisited(predecessor)) {
                    node = predecessor;
                    Set<String> nextLiveVariables = new HashSet<>(nodeToLiveVariables.get(node));
                    if (canVisit(node)) {
                        traverseCFGLiveNodes(node, nextLiveVariables);
                    }

                }
            }
        }
    }

    /**
     * Check that a node should be visited, only visit when all the successors have been visited
     *
     * @param node Node
     * @return boolean whether the node can be visited
     */
    private boolean canVisit(Node node) {
        Integer visits = nodeToSuccessorsCovered.get(node);
        if (visits == null) visits = 0;
        visits++;
        nodeToSuccessorsCovered.put(node, visits);
        int successors = cfg.successors(node).size();
        return visits == successors;
    }

    /**
     * Add the definitions and kill any previous live variables
     *
     * @param definitions   the set of definitions in the node
     * @param liveVariables the set of live variables
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
     * Add the uses of variables and add to the nodeToLiveVariables Map
     *
     * @param node          current node
     * @param uses          The variables that are used at the node
     * @param liveVariables The set of the liveVariables from successors
     */
    private void addUses(Node node, Set<String> uses, Set<String> liveVariables) {
        Set<String> liveVariablesOfNode = new HashSet<>();
        liveVariablesOfNode.addAll(liveVariables);
        liveVariablesOfNode.addAll(uses);
        if (nodeToLiveVariables.get(node) != null) {
            liveVariablesOfNode.addAll(nodeToLiveVariables.get(node));
        }
        nodeToLiveVariables.put(node, liveVariablesOfNode);
        logger.info("Putting Node: {} --> {}", node, nodeToLiveVariables.get(node));
    }


    private Set<Node> getPredecessors(Node node) {
        return cfg.predecessors(node);
    }

    private boolean nodeVisited(Node node) {
        return nodesVisited.contains(node);
    }
}

