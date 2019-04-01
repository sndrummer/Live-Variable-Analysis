package edu.byu.yc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Implementation of the LiveVariableAnalysis that traverses through the CFG to determine the live
 * nodes at each stage
 */
public class LiveVariableAnalyzer extends LiveVariableAnalysis {

    private static Logger logger = LoggerFactory.getLogger(LiveVariableAnalysis.class);
    private CFG cfg;

    /**
     * Comparator for nodes that uses their toString names to order in reverse order, just for testing
     * convenience
     */
    private Comparator<Node> nodeComparator = (node1, node2) -> {
        if (node1.toString().contains("Entry")) {
            return node2.toString().compareTo(node1.toString());
        }
        String str1 = node1.toString();
        String[] arr1 = str1.split("node");
        Integer node1Num = Integer.parseInt(arr1[1]);

        String str2 = node2.toString();
        String[] arr2 = str2.split("node");
        Integer node2Num = Integer.parseInt(arr2[1]);

        return node2Num.compareTo(node1Num);
    };
    private Map<Node, Set<String>> nodeToLiveVariables = new TreeMap<>(nodeComparator);

    private Map<Node, Integer> nodeToSuccessorsCovered = new HashMap<>();
    private Set<Node> nodesVisited = new HashSet<>();

    public LiveVariableAnalyzer(CFG cfg) {
        super(cfg);
        this.cfg = cfg;
    }

    /**
     * Analyze the live nodes of the CFG by using recursion and going backwards through the graph
     * to determine what variables are live
     *
     * @return the node to live variables map that show what variables were live at a specific node
     */
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
        Set<Node> predecessors = getPredecessors(node);
        if (predecessors == null) return;

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
    }


    /**
     * Get all the predecessors of a node
     * @param node Node
     * @return list of predecessors
     */
    private Set<Node> getPredecessors(Node node) {
        return cfg.predecessors(node);
    }

    /**
     * This checks to see if a node has already been visited
     * @param node Node
     * @return boolean, true if it has been visited
     */
    private boolean nodeVisited(Node node) {
        return nodesVisited.contains(node);
    }
}

