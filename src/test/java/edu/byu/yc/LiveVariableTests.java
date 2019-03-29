package edu.byu.yc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LiveVariableTests {

    /**
     * This test is included to give you a starting point. You will need
     * additional tests in order to create mutation coverage.
     */
    @Test
    public void emptyCFGTest() {
        CFG cfg = Mockito.mock(CFG.class);
        Node exit = ExitNode.getInstance();
        Mockito.when(cfg.exitNode()).thenReturn(exit);
        Mockito.when(cfg.successors(Mockito.any(ASTNode.class)))
                .thenReturn(new HashSet<>());
        Mockito.when(cfg.predecessors(Mockito.any(ASTNode.class)))
                .thenReturn(new HashSet<>());

        LiveVariableAnalysis lva = new LiveVariableAnalysis(cfg) {

            @Override
            public Map<Node, Set<String>> analyze() {
                // TODO Auto-generated method stub
                return null;
            }
        };
        Map<Node, Set<String>> result = lva.analyze();
        assertEquals(0, result.keySet().size());
    }

}
