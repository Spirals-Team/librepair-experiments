# coljate

[![Travis branch](https://img.shields.io/travis/ollierob/coljate/master.svg)](https://travis-ci.org/ollierob/coljate)
[![Coveralls branch](https://img.shields.io/coveralls/ollierob/coljate/master.svg)](https://coveralls.io/github/ollierob/coljate?branch=master)

Collections library for Java 8+

Inspired by the SLF4J re-working of loggers, this project aims to be functionally equivalent to Java's collection library but offer syntactic improvements by:

  * Classifying collections as one of mutable, immutable, or read-only.
  * Avoiding unsuitable & unsafe methods, such as mutators on immutable collections.
  * Maximizing extensibility and minimizing repetition through use of mixin.
  * Remaining open for extension by users.
  * Using [FindBugs](http://findbugs.sourceforge.net/) & ~~[Checker Framework](http://types.cs.washington.edu/checker-framework/)~~ annotations to assist static anaylysis.

Supported collection types:

 * Lists
 * Queues
 * Arrays
 * Sets
 * Multisets
 * Maps
 * Multimaps
 * Caches
 * Trees
 * Graphs 
 * Tables

Class hierarchy for ordered collections:

```
                                     Queue -------------> ConcurrentQueue
                                      ↑
ImmutableCollection <- Collection -> MutableCollection
 ↓                      ↓             ↓
ImmutableList <------- List -------> MutableList -------> ConcurrentList
 ↓                      ↓             ↓                    ↓
ImmutableArray <------ Array ------> MutableArray ------> ConcurrentArray
```

Class hierarchy for unordered collections:

```
ImmutableCollection <- Collection -> MutableCollection
 ↓                      ↓             ↓
ImmutableSet <-------- Set --------> MutableSet -------> ConcurrentSet
 ↓                      ↓             ↓                   ↓
ImmutableMap <-------- Map --------> MutableMap -------> ConcurrentMap
 |                      ↓             ↓                   ↓
 |                     Cache ------> MutableCache -----> ConcurrentCache
 ↓                      ↓             ↓                   ↓
ImmutableMultimap <--- Multimap ---> MutableMultimap --> ConcurrentMultimap
```
