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
    private Set<String> liveVariables = new HashSet<>();
    private Map<Node, Set<String>> nodeToLiveOnEntryVariables = new HashMap<>();

    private Map<Node, Integer> nodeToSuccessorsCovered = new HashMap<>();

    private Set<Node> nodesVisited = new HashSet<>();

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
        Set<Node> predecessors = getPredecessors(node);
        //logger.debug("Traversing");
        if (node != null) {
            for (Node predecessor : predecessors) {
                addDefinitions(predecessor.getDefs(), liveVariables);
                addUses(predecessor, predecessor.getUses(), liveVariables);
            }

            //Here's what you need to do
            /*
            1. You need to do what you are doing so far but then
            2. Check if the prev node has multiple successors, if it does just increment a value
            3. You could maybe make a Node to successor's covered map? that would be weird but it might work
            So the key would be the node and the value would be how many successors have been covered,
            Use node 4 from the HW as an example.
            4. Make a condition that you ONLY traverse a node with multiple predecessors once you have seen them all!!
            5. So make something like if successors equals amtSeen then prevNode = curNode, you actually
            probably don't want recursive in this case because you want it to be iterative!!!! That is true huh
            6. So make that condition
             */


            nodesVisited.add(node);
            for (Node predecessor : predecessors) {
                if (!nodeVisited(predecessor)) {
                    node = predecessor;
                    traverseCFGLiveNodes(node, liveVariables);
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
    private void addDefinitions(Set<String> definitions, Set<String> liveVariables) {
        for (String definition : definitions) {
            if (liveVariables.contains(definition)) {
                logger.info("Live Variable: {}, killed", definition);
                liveVariables.remove(definition);
            }
        }
    }

    /**
     * @param uses
     * @param liveVariables
     */
    private void addUses(Node node, Set<String> uses, Set<String> liveVariables) {
        Set<String> liveVariablesOfNode = new HashSet<>();
        liveVariablesOfNode.addAll(liveVariables);
        liveVariablesOfNode.addAll(uses);
        liveVariables.addAll(liveVariablesOfNode);
        nodeToLiveOnEntryVariables.put(node, liveVariablesOfNode);
        logger.info("Node: {} --> {}", node, nodeToLiveOnEntryVariables.get(node));
    }


    private Set<Node> getPredecessors(Node node) {
        return cfg.predecessors(node);
    }

    private boolean nodeVisited(Node node) {
        return nodesVisited.contains(node);
    }
}

