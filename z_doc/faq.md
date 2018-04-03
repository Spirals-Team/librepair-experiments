# Table of contents

  * [What is the motivation behind jComparison?](#what-is-the-motivation-behind-jcomparison)
  * [What are the goals of jComparison?](#what-are-the-goals-of-jcomparison)
  * [What are the principles of jComparison?](#what-are-the-principles-of-jcomparison)
  * [How does jComparison work in general?](#how-does-jcomparison-work-in-general)
  * [What are the main TODOs yet?](#what-are-the-main-todos-yet)
  * [What are restrictions and limits of jComparison?](#what-are-restrictions-and-limits-of-jcomparison)
  * [How is jComparison licensed?](#how-is-jcomparison-licensed)

## What is the motivation behind jComparison?

All objects are direct or indirect subclasses of the super class "Object" (except lambdas). The class object defines an "equals"-method. This method compares the callee object with a parameter object. If they are regarded as equal, then it returns true, else false. This check can be enough for a programmer. However, the return value does not say anything about the differences. It only says that at least one difference was detected at all.

Whenever programmers want to know more, they have to examine the objects on their own. They need to debug their program or to print out the values and have a sharp look to discover the difference(s). If the objects have too many fields or if they are collections with too many elements, this can become difficult or even impossible. Then they wished comparators to find the differences (and similarities) for them.
If they are only interested in only one 'kind' of difference, they might find a lambda expression or a simple algorithm for their problem.
What about if they are not sure in which difference they are interested? They might program complex diff algorithms.

Finding some or all differences (and/or similarities) can be very hard, tricky and expensive (regarding resources like cpu and memory). It depends on what you compare and how you compare a pair of items.
jComparison is a framework that compares two java objects and gives you a summary of all or selected differences and similarities.

## What are the goals of jComparison?

* jComparison provides basic algorithms to compare objects, strings, arrays and basic collections
* jComparison provides an algorithm that iterates over the fields of two objects, compares each pair of fields and follows references (non-primitive fields) recursively
* jComparison is configurable regarding compared features, filters, stop features and error handling
* jComparison supports primitive values without boxing whenever it is possible

## What are the principles of jComparison?

* Always compare two instances of the same type. The type can be an interface.

Every comparison must make sense anyway. Otherwise you risk getting confusing results.

E.g. compare a TreeSet with a HashSet because both are sets.

E.g. do not compare a double with an int (mixing floating and non-floating numbers)

E.g. do not compare a double with an float (mixing numbers with different precision)

E.g. do not compare a String with a BitSet

E.g. do not compare an int array with a string array

E.g. do not compare a two-dimensional array with a three-dimensional array

* Rather compare contents than meta information (e.g. the implementation type) or aggregated information (e.g. the size of a collection)

* Reduce the number of comparisons by using filters

You can never improve the performance more than with avoiding irrelevant comparisons by filters (no single dirty hack can do that).

* Fail fast

Check whether a comparison makes sense before you trigger it. Checks reveal mistakes and save your time by avoiding the need for debugging sessions.

## How does jComparison work in general?

* jComparison uses a qualitative approach instead of a quantitive approach (e.g. based on metrics like the Levenshtein-distance).
It considers occurences, frequencies and orders of items in collections. See the demos under src/demo for a better understanding.
* jComparison uses a recursive, depth-first-search based algorithm that follows all non-value fields of objects.
We call everything a value where considering its fields makes no sense (e.g. string) or is impossible (e.g. primitive types).
* jComparison can detect cycles in object graphs to avoid infinite recursions
* jComparison offers different ways how to handle failures in comparisons (e.g. stopping comparisons, skipping comparisons, ...)
* jComparison include smart comparison algorithms of java collections like sets, lists and maps.
* jComparison supports many different kinds of filters to reduce the number of comparisons to the minimum
* jComparison enables different priorities considering resources (memory-saving, cpu-time-saving)
* jComparison initializes its collections lazily

## What are the main TODOs yet?
* improve the facade that hides all the construction details
* more demos are needed to show you how useful and powerful and versatile jComparison is
* improve support for primitive values (to avoid boxing)
* improve support for descriptors (e.g. File, URI)
* more tests so that test coverage rises from 56% atm to 70% (that is much more than some people think)
* a formatter framework that creates human readable reports

## What are restrictions and limits of jComparison?
* jComparison uses Java 8 because of lambdas and other Java 8 features

Using modern versions of programming languages always excludes users of older versions or app developers.
However, the code of jComparison would become more complicated if a deprecated version of Java was used.
Moreover, it is a bad choice using products from the beginning that are end-of-life.

* jComparison is sequential (yet).

It is very difficult to decide whether it is worth working sequentially or concurrent/parallel.
You need to know what you compare and how you compare in advance.
If you traverse two object graphs and compare two objects or values step by step,
you will never have that essential information when you need it.
You must know how broad and deep the object graphs are before you can begin traversing and comparing concurrent/parallel.
If you compare less than 10,000 (or even more) primitive values,
a concurrent run might be slower than a sequantial run. Therefore making good decisions can be very hard.
Of course, you can do a "pre-scan" and collect information about how many comparisons you will face on which path.
However, you may not neglect the costs of merging the comparison results at the end.
Nearly all algorithms in jComparison use collections in their comparison results.

* jComparison enables but does not support mutable comparison results

This means jComparison uses immutable objects for its comparison results.
You can (and you should) use them but you can switch to object pools if you need.
However, you must create the mutable classes and the factory functions on your own then.

* jComparison does not support distance-based algorithms and never will

E.g. if you are interested in the Levenshtein-distance of two strings (or arrays), jComparison is the wrong choice.
Neither the API nor the algorithm are made for that. You will hardly be able to extend jComparison with those metric concepts.
They have a "quantitive nature" while jComparison focuses on the "qualitative nature".

* Always rely on the API but never on the implementations

This means: use the facade and do not create the comparators on your own.
I cannot and I will not promise you that implementations will never change.
Bugs will be found and fixed. Benchmarks might imply changes.
Some ideas might reveal as mistakes. There are many reasons why I need to change the code.
I will not ask you then, sorry ;-)
And never access fields of my implementations with reflection!
Do not take a risk.

## How is jComparison licensed?
This question is not finally answered yet. I consider the LGPL3 for the core of jComparison 
so that jComparison can broadly be used in both non-commercial and commercial applications.
However, extensions for support of different file formats will be licensed under LGPL3 for non-commercial, open-source projects 
but not for commercial use.