package edu.byu.yc;

import java.util.HashSet;
import java.util.Set;

public class ExitNode implements Node {
    private static final ExitNode instance = new ExitNode();

    private ExitNode() {
    }

    public static ExitNode getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "ExitNode";
    }

    @Override
    public Set<String> getDefs() {
        return new HashSet<>();
    }

    @Override
    public Set<String> getUses() {
        return new HashSet<>();
    }
}
