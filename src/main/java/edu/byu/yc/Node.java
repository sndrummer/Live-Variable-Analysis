package edu.byu.yc;

import java.util.Set;

public interface Node {

    /**
     * Get the variables that are being defined
     * @return Set of strings of the variables that are being defined
     */
    public abstract Set<String> getDefs();

    /**
     * Returns the variables that are used at the particular Node
     * @return Set of Strings of the used variables
     */
    public abstract Set<String> getUses();

}
