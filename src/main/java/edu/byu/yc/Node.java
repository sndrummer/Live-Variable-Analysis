package edu.byu.yc;

import java.util.Set;

public interface Node {

    public abstract Set<String> getDefs();

    public abstract Set<String> getUses();

}
