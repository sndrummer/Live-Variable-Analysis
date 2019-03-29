package edu.byu.yc;

import java.util.Set;

public interface SetCalculator {
    public abstract Set<SetMember> killSet(Node n);
    public abstract Set<SetMember> genSet(Node n);
}
