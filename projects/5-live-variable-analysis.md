# Intro

In this assignment, you will implement a live variable analysis. There is an abstract **CFG** class that defines the interface and the **TypeChecker** class uses it. However, your task is *not* to implement the **CFG** class. Instead, you will use **Mockito** to mock it.

You will, however, implement, the **LiveVariableAnalysis** class, which needs to know about the **CFG** class and which assumes that it works.

# Implementation

Your **LiveVariableAnalysis** class should be constructed with a single parameter of type **CFG**. A **CFG** object represents the control-flow graph for a method; as such, variable names are used without qualifiers. Write it as if you knew the **CFG** class were fully and correctly implemented. The `analyze()` function should return a map from **Node** objects to sets of **String** objects, where each **String** contains the name of a variable; e.g., `"a"`. This mapping should contain the *entry* set for each node.

Implement tests in the **LiveVariableTests** class. (You may add additional classes if you like, but you'll need to add them to the mutation testing configuration in `pom.xml`.) One test is included for reference. Create mocks for all of the nodes you need as well as for the CFG itself. Then, create an instance of your **LiveVariableAnalysis** class and call its `analyze()` method, running tests to check its results for correctness. If you like, you may remove the example test.

# Rubric

* 15 points for implementing **LiveVariableAnalysis**
* 30 points for implementing **LiveVariableTests** with appropriate mocks.
* 15 points for 100% mutation coverage on **LiveVariableAnalysis** by **LiveVariableTests**
* 15 points for documentation of **LiveVariableAnalysis**
* 15 points for documentation of **LiveVariableTests**
* 10 points for code style (passing SonarLint and legibility)