package edu.byu.yc;

import java.util.HashSet;
import java.util.Set;

public class BasicNode implements Node {

    private String name;
    private Set<String> definitions;
    private Set<String> uses;

    public BasicNode(String name, Set<String> definitions, Set<String> uses) {
        this.name = name;
        this.definitions = definitions;
        this.uses = uses;
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public Set<String> getDefs() {
        return definitions;
    }

    @Override
    public Set<String> getUses() {
        return uses;
    }
}
