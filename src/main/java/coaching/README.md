# Java Coaching Topics
| [Automation](src/main/java/automation "Automated Testing with Java") | [Java Coaching](src/main/java/coaching "Coaching Java Idioms") | [Java Patterns](src/main/java/patterns "Design Patterns in Java") |

 * [Arrays](arrays/)
 * [Associations](associations/)
 * [Bags](bags/)
 * [Collections](collections/)
 * [Configuration](config/)
 * [Context](context/)
 * [CSV resources](csv/)
 * [Delegation](delegation/)
 * [Exceptions](exceptions/)
 * [Hollywood principle](hollywood/)
 * [Idioms](idioms/)
 * [JDBC](jdbc/)
 * [Model](model/)
 * [Money](money/)
 * [Polymorphism](polymorphism/)
 * [Pools](pool/)
 * [Resources](resources/)
 * [Rules engines](rules/)
 * [SOLID](solid/)
 * [Template](template/)
 * [Thread](thread/)
 * [Tuples](tuples/)
 * [Types](types/)
 * [Xml](xml/)

## Programming Fundamentals
All programming statements are formed on three basic constructs
### Sequence
One instruction follows another e.g.

```
  foo()
  bar()
```

### Selection
An Instruction is performed if a condition is true e.g.

~~~~
	If Then Else  
~~~~

~~~~
	Switch
~~~~

### Iteration
An Instruction is performed repeatedly in a loop. e.g.

#### for loop

~~~~
	for( i=1 ; i<10 ; i++) { ... }
~~~~

#### while loop

~~~~
	while(notFinished) { doWork(); }
~~~~

#### do while loop

~~~~
	do { work(); } while(notFinished);
~~~~

#### recursion

~~~~
	work() { while(notFinished) { work() } }
~~~~

## Programming Fundamentals
 * Variables, Constants, Scope and Types
 * What are Coupling and Cohesion
 * Why decoupling and high cohesion are are important
 * What are Algorithms, Data Structures, Patterns and idioms, why the distinction is important.
 * https://www.quora.com/What-is-a-call-stack

## Java Fundamentals
 * Java class Design : Interfaces, Abstract Classes, Final Classes
 * Using inBuilt Classes, Types, Strings, Dates etc.
 * String Processing and Formatting
 * Collections & Generics
 * Java Regular expressions
 * Recursion
 * Input and Output
 * Exceptions and Assertions	
 * Sorting and Searching
 * Polymorphism

## Different Programming Paradigms
### Imperative Programming
 * https://en.wikipedia.org/wiki/Imperative_programming
### Declarative & Functional programming
 * https://en.wikipedia.org/wiki/Declarative_programming 
### Object Oriented Programming		
 * Why Declarative programming is more appropriate for FDD/BDD than Imperative programming 

## Practical Coding Topics
 * Unit Testing with JUnit 
 * Using slf4j/log4j for logging
 * Java Strings, Comparing, Formatting, StringBuffer
 * Pojo, Memento, Parameter objects
 * Exceptions, Handing exceptions, writing custom exceptions
 * Assertions, Checked & UnChecked
 * Java Collections (Generics)
 * Loading resources from classpath vs absolute paths

### Cucumber
 * Behaviour Driven Development
 * Feature files
 * * Tagging
 * Steps
 * * Regular Expressions
 * System Model
 * Runners

### Apache Maven
 * settings.xml
 * pom.xml
 * Maven life cycle
 * Maven Plugins
 
### Selenium WebDriver
 * WebDriver API
 * Browser Factory
 * Page Objects
 * Page Object Factory
 * Locator Strategy / css vs xPath
 * Selenium Grid
 * Practical CI
 
## Online Training Java Tutorials
 * http://docs.oracle.com/javase/tutorial/index.html		
 * https://www.udemy.com/java-tutorial/#curriculum
 * https://www.udemy.com/java-the-complete-java-developer-course/#curriculum

## Software Engineering
 * Fluent Interfaces
 * Recursion
 * Separation of Concerns
 * Low Coupling and High Cohesion
 * Law of Demeter
 * Tell don't Ask
 * Hollywood Principle, "Don't call me, I'll call you"	

### JUnit
 * Assume vs Assert (rule of thumb)
 * Assumptions are for use as Pre-conditions.
 * Failures indicate the test offers no meaningful result.
 * e.g. When testing eligibility, 
 * "Given Customer exists" is a pre-condition.
	* Test should be considered skipped, not a failure.
		* Change is still like to be necessary.
	* Asserting are for use as Acceptance Criteria/Post
		* Failure is a failure of the system under test.
		* Also Exceptions, likely failure of Test Suite.

### SOLID principles 
 * Single Responsibility
 * Open for extension, Closed to modification
 * Liskov Substitution principle
 * Interface Segregation principle
 * Dependency Inversion principle
	https://en.wikipedia.org/wiki/SOLID_(object-oriented_design)
	http://www.oodesign.com/design-principles.html

### Code to a Contract / Design by Contract
	http://wiki.c2.com/?DesignByContract 

## Data Driven Testing		

## Advanced Programming Topics
###	Big-O Complexity
	https://rob-bell.net/2009/06/a-beginners-guide-to-big-o-notation/

### Design Patterns

	https://en.wikipedia.org/wiki/Design_Patterns

#### Builder Pattern
#### Composite Pattern
#### Command Pattern
#### Factory Method
#### Singleton

## Computer Science Topics

### Type Theory

	https://en.wikipedia.org/wiki/Type_theory

### Set Theory

### Taxonomy

	https://en.wikipedia.org/wiki/Taxonomy_(general)

### Ontology

	https://en.wikipedia.org/wiki/Ontology_(information_science)
