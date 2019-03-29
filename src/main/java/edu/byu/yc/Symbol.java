package edu.byu.yc;

public class Symbol implements SetMember {
    private final String symb;
    public Symbol(String s) {
        symb = s;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Symbol) {
            Symbol s = (Symbol) o;
            return symb.equals(s.symb);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return symb.hashCode();
    }
    
    @Override
    public String toString() {
        return symb;
    }
}
