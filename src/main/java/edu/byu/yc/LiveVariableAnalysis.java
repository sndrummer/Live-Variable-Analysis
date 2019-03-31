package edu.byu.yc;

import java.util.Map;
import java.util.Set;

/**
 *
 * TODO Delete this
 * Things to remember the CFG is for a method!! Variable names are used without qualifiers, this
 * means that there is no int or type given for the names
 *
 *
 *
 */
public abstract class LiveVariableAnalysis {

    private CFG cfg;


    public LiveVariableAnalysis(CFG cfg) {
        this.cfg = cfg;
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



}
