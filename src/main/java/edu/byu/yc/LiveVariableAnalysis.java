package edu.byu.yc;

import java.util.Map;
import java.util.Set;

public abstract class LiveVariableAnalysis {

    private CFG cfg;
    private Map<Node, Set<String>> entryNodeToVariableNames;

    public LiveVariableAnalysis(CFG cfg) {
        this.cfg = cfg;
        this.entryNodeToVariableNames = analyze();
    }

    /**
     *
     * @return map from Node objects to sets of String objects, where each String contains the
     * name of a variable; e.g., "a". This mapping should contain the entry set for each node.
     * This mapping should contain the entry set for each node.
     */
    public abstract Map<Node, Set<String>> analyze();

    public CFG getCfg() {
        return cfg;
    }

    public Map<Node, Set<String>> getEntryNodeToVariableNames() {
        return entryNodeToVariableNames;
    }

}
