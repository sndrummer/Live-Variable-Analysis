package edu.byu.yc;

import java.util.HashSet;
import java.util.Set;

public class EntryNode implements Node {

    @Override
    public String toString() {
        return "EntryNode";
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
