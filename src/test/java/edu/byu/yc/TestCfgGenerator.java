package edu.byu.yc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TestCfgGenerator generates nodes for a CFG that will be mocked
 */
public class TestCfgGenerator {

    private TestCfgGenerator() {
    }


    /**
     * Generates nodes for the following simple program
     * //entry
     * b = 3         //node1
     * c = 5         //node2
     * a = f(b * c)  //node3
     * //exit
     *
     * @return List of nodes in a simple CFG
     */
    public static List<Node> generateNodesForSimpleCFG() {
        List<Node> nodes = new ArrayList<>();
        Node node1 = new Node() {
            @Override
            public Set<String> getDefs() {
                String b = "b";
                Set<String> defs = new HashSet<>();
                defs.add(b);
                return defs;
            }

            @Override
            public Set<String> getUses() {
                return new HashSet<>();
            }

            @Override
            public String toString() {
                return "node1";
            }
        };

        Node node2 = new Node() {
            @Override
            public Set<String> getDefs() {
                String c = "c";
                Set<String> defs = new HashSet<>();
                defs.add(c);
                return defs;
            }

            @Override
            public Set<String> getUses() {
                return new HashSet<>();
            }

            @Override
            public String toString() {
                return "node2";
            }
        };

        Node node3 = new Node() {
            @Override
            public Set<String> getDefs() {
                String a = "a";
                Set<String> defs = new HashSet<>();
                defs.add(a);
                return defs;
            }

            @Override
            public Set<String> getUses() {
                Set<String> uses = new HashSet<>();
                uses.add("b");
                uses.add("c");
                return uses;
            }

            @Override
            public String toString() {
                return "node3";
            }
        };

        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        return nodes;

    }


    /**
     * This method generates the nodes to create a more complex CFG based off the following program:
     * Look at the png with the graph for visual reference
     * <p>
     * ```public static void dnfsort(int[] arr) {
     * int a = 0;              //1
     * int b = 0;              //2
     * int c = arr.length;     //3
     * while (b < c) {         //4
     * if (arr[b] == 1) {  //5
     * arr[b] = arr[a]; //6
     * arr[a] = 1;      //7
     * a = a + 1;       //8
     * b = b + 1;       //9
     * }
     * else { //10
     * if (arr[b] == 2) { //11
     * b = b + 1;      //13
     * }
     * else // 12
     * {
     * c = c - 1; //14
     * arr[b] = arr[c]; //15
     * arr[c] = 3; //16
     * }
     * }* 		}
     * }```
     *
     * @return List of nodes in a complexCFG
     */
    public static List<Node> generateNodesForComplexCFG() {
        List<Node> nodes = new ArrayList<>();

        Node node1 = new Node() {
            @Override
            public Set<String> getDefs() {
                String a = "a";
                Set<String> defs = new HashSet<>();
                defs.add(a);
                return defs;
            }

            @Override
            public Set<String> getUses() {
                return new HashSet<>();
            }

            @Override
            public String toString() {
                return "node1";
            }
        };

        Node node2 = new Node() {
            @Override
            public Set<String> getDefs() {
                String b = "b";
                Set<String> defs = new HashSet<>();
                defs.add(b);
                return defs;
            }

            @Override
            public Set<String> getUses() {
                return new HashSet<>();
            }

            @Override
            public String toString() {
                return "node2";
            }
        };

        Node node3 = new Node() {
            @Override
            public Set<String> getDefs() {
                String c = "c";
                Set<String> defs = new HashSet<>();
                defs.add(c);
                return defs;
            }

            @Override
            public Set<String> getUses() {
                Set<String> uses = new HashSet<>();
                uses.add("arr");
                return uses;
            }

            @Override
            public String toString() {
                return "node3";
            }
        };

        Node node4 = new Node() {
            @Override
            public Set<String> getDefs() {
                return new HashSet<>();
            }

            @Override
            public Set<String> getUses() {
                Set<String> uses = new HashSet<>();
                uses.add("b");
                uses.add("c");
                return uses;
            }

            @Override
            public String toString() {
                return "node4";
            }
        };

        Node node5 = new Node() {
            @Override
            public Set<String> getDefs() {
                return new HashSet<>();
            }

            @Override
            public Set<String> getUses() {
                return new HashSet<>();
            }

            @Override
            public String toString() {
                return "node5";
            }
        };

        Node node6 = new Node() {
            @Override
            public Set<String> getDefs() {
                String arr = "arr";
                Set<String> defs = new HashSet<>();
                defs.add(arr);
                return defs;
            }

            @Override
            public Set<String> getUses() {
                Set<String> uses = new HashSet<>();
                uses.add("arr");
                uses.add("a");
                return uses;
            }

            @Override
            public String toString() {
                return "node6";
            }
        };

        Node node7 = new Node() {
            @Override
            public Set<String> getDefs() {
                Set<String> defs = new HashSet<>();
                defs.add("arr");
                return defs;
            }

            @Override
            public Set<String> getUses() {
                return new HashSet<>();
            }

            @Override
            public String toString() {
                return "node7";
            }
        };

        Node node8 = new Node() {
            @Override
            public Set<String> getDefs() {
                Set<String> defs = new HashSet<>();
                defs.add("a");
                return defs;
            }

            @Override
            public Set<String> getUses() {
                Set<String> uses = new HashSet<>();
                uses.add("a");
                return uses;
            }

            @Override
            public String toString() {
                return "node8";
            }
        };

        Node node9 = new Node() {
            @Override
            public Set<String> getDefs() {
                Set<String> defs = new HashSet<>();
                defs.add("b");
                return defs;
            }

            @Override
            public Set<String> getUses() {
                Set<String> uses = new HashSet<>();
                uses.add("b");
                return uses;
            }

            @Override
            public String toString() {
                return "node9";
            }
        };

        Node node10 = new Node() {
            @Override
            public Set<String> getDefs() {
                return new HashSet<>();
            }

            @Override
            public Set<String> getUses() {
                return new HashSet<>();
            }

            @Override
            public String toString() {
                return "node10";
            }
        };

        Node node11 = new Node() {
            @Override
            public Set<String> getDefs() {
                return new HashSet<>();
            }

            @Override
            public Set<String> getUses() {
                Set<String> uses = new HashSet<>();
                uses.add("b");
                uses.add("arr");
                return uses;
            }

            @Override
            public String toString() {
                return "node11";
            }
        };

        Node node12 = new Node() {
            @Override
            public Set<String> getDefs() {
                return new HashSet<>();
            }

            @Override
            public Set<String> getUses() {
                return new HashSet<>();
            }

            @Override
            public String toString() {
                return "node12";
            }
        };

        Node node13 = new Node() {
            @Override
            public Set<String> getDefs() {
                Set<String> defs = new HashSet<>();
                defs.add("b");
                return defs;
            }

            @Override
            public Set<String> getUses() {
                Set<String> uses = new HashSet<>();
                uses.add("b");
                return uses;
            }

            @Override
            public String toString() {
                return "node13";
            }
        };

        Node node14 = new Node() {
            @Override
            public Set<String> getDefs() {
                Set<String> defs = new HashSet<>();
                defs.add("c");
                return defs;
            }

            @Override
            public Set<String> getUses() {
                Set<String> uses = new HashSet<>();
                uses.add("c");
                return uses;
            }

            @Override
            public String toString() {
                return "node14";
            }
        };

        Node node15 = new Node() {
            @Override
            public Set<String> getDefs() {
                Set<String> defs = new HashSet<>();
                defs.add("arr");
                return defs;
            }

            @Override
            public Set<String> getUses() {
                Set<String> uses = new HashSet<>();
                uses.add("arr");
                uses.add("c");
                return uses;
            }

            @Override
            public String toString() {
                return "node15";
            }
        };

        Node node16 = new Node() {
            @Override
            public Set<String> getDefs() {
                Set<String> defs = new HashSet<>();
                defs.add("arr");
                return defs;
            }

            @Override
            public Set<String> getUses() {
                return new HashSet<>();
            }

            @Override
            public String toString() {
                return "node16";
            }
        };

        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);
        nodes.add(node5);
        nodes.add(node6);
        nodes.add(node7);
        nodes.add(node8);
        nodes.add(node9);
        nodes.add(node10);
        nodes.add(node11);
        nodes.add(node12);
        nodes.add(node13);
        nodes.add(node14);
        nodes.add(node15);
        nodes.add(node16);

        return nodes;

    }


}
