# Objective

* Use [mockito](https://site.mockito.org/) to create *dummy* objects, *fake* objects, *stub* objects, or *mock* objects for unit testing

# Reading

* [Mockito](https://site.mockito.org/)
* [Unit tests with Mockito - Tutorial](https://www.vogella.com/tutorials/Mockito/article.html)
* [Mockito User Docs](http://static.javadoc.io/org.mockito/mockito-core/2.25.0/org/mockito/Mockito.html)

# Outline

* Remove side-effects from other classes when unit testing with

   * *dummy* objects: passed around but never used
   * *fake* objects: simplified working implementations
   * *stub* classes: partial implementation with only responses for the test at hand
   * *mock* object:  a dummy implementation for an interface or a class in which you define the output of certain method calls. Mock objects are configured to perform a certain behavior during a test. They typically record the interaction with the system and tests can validate that.

* Mockito simplifies the process of creating mock objects and is super helpful for classes with external dependencies
* Typical flow: Mock dependencies --> Execute code in class under test --> Validate if the code worked

```xml
	<dependency>
			<groupId>org.mockito</groupId>
			<artifactId>mockito-core</artifactId>
			<version>2.25.0</version>
		</dependency>
```xml

```java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class SymbolTableVisitorTestsWithMock {

	ISymbolTable symbolTable;
	
    @BeforeAll
    public void setup() {
    	symbolTable = mock(ISymbolTable.class);
    	when(symbolTable.getLocalType("a")).thenReturn("int");
    	when(symbolTable.getLocalType("b")).thenReturn("int");
    	when(symbolTable.getLocalType("p")).thenReturn("boolean");
    	
    }
    
    @Test
    @DisplayName("Ensure that fields' types are correct")
    public void test_Field_Types() {	
    	assertEquals("int", symbolTable.getLocalType("a"));
        assertEquals("int", symbolTable.getLocalType("b"));
        assertEquals("boolean", symbolTable.getLocalType("p"));
        verify(symbolTable).getLocalType("b");
    }

    @Test
    @DisplayName("Test the type of Adder.addRight")
    public void test_Adder_addRight() {
        assertEquals("int", symbolTable.getMethodReturnType("Adder.addRight", "addRight"));
    }
    
    @Test
    @DisplayName("Make sure int+bool fails")
    public void test_Adder_addWrong() {
        assertEquals(TypeCheckerAbstractVisitor.UNKNOWN_TYPE, symbolTable.getMethodReturnType("Adder.addWrong", "addWrong"));
    }
}
```

```java
 private void fieldLookup(String name, ASTNode node) {
    	ArrayList<String> c = new ArrayList<>(context);
        while (true) {
            if (symbolTable.fieldExists(String.join(".", c), name)) {
                typeTable.put(node, symbolTable.getFieldType(String.join(".", c), name));
                break;
            }
            if (c.size() > 1) {
                logger.debug("Removing from {}:", c);
                int last = c.size() - 2;
                c.remove(last);
                logger.debug("Removed: {}", c);
            } else {
                logger.debug("Could not find type {} in context {}", name, context);
                typeTable.put(node, UNKNOWN_TYPE);
                break;
            }
        }
    }
```