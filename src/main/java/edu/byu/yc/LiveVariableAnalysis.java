package edu.byu.yc;

import java.util.Map;
import java.util.Set;

public abstract class LiveVariableAnalysis {

    public LiveVariableAnalysis(CFG cfg) {
    }

    /**
     *
     * @return map from Node objects to sets of String objects, where each String contains the
     * name of a variable; e.g., "a". This mapping should contain the entry set for each node.
     */
    public abstract Map<Node, Set<String>> analyze();

}
