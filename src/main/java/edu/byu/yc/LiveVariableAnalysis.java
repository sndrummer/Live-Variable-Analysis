package edu.byu.yc;

import java.util.Map;
import java.util.Set;

public abstract class LiveVariableAnalysis {

    public LiveVariableAnalysis(CFG cfg) {
    }

    public abstract Map<Node, Set<String>> analyze();

}
