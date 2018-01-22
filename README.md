# jComparison
*A Java framework that compares strings, arrays, maps, collections and objects smarter than equals() within Java code*

**Please try it out but do not use it in production yet.
Its API is still unstable and its implementations have not been tested enough yet.
It is still in progress. Milestone and release 1.0.0 will follow.**

## Short introduction
This little introduction shows you how useful jComparison.    

### Motivation
You can wonder: Why do I want to know the difference (or similarity)? Isn't equals() enough?    
Of course, there are many situations in which equals() is enough.    
What about equals() returns false and you are astonished because you did not expect it though?    
Indeed, you can find the difference with your own eyes by debugging in small data samples.    
What if you face a big data sample? Do you think you still find the difference(s)?    
What if you want to show the differences between two objects that you expected to be equal in a test case?    
What if differences indicate an error you want to log?    
What if ... ? There are also many other use cases where you want to know the difference.    

### Simple example
Let's start with a simple example.    
Imagine you have two lists of ints:    
* 1, 2, 4
* 1, 1, 4, 6

... and you compare them by equals:

```Java
    System.out.println(Arrays.asList(1, 2, 4).equals(Arrays.asList(1, 1, 4, 6))); // prints out false
```

They are not equal (apparently). What is the difference though?     
Ok, you can see it but the computer cannot.    
You can look or ask for a diff algorithm in the internet or start programming your own one.     
Or you can use jComparison ;-)    
How?   

```Java
class FindDifferencesDuplicatesListComparisonDemo {
	public static void main(String[] args)
			throws IllegalArgumentException, ComparisonFailedException {
		final List<Integer> leftList = Arrays.asList(1, 2, 4);
		final List<Integer> rightList = Arrays.asList(1, 1, 4, 6);

		System.out.println("Left list :" + leftList);
		System.out.println("Right list :" + rightList);
		System.out.println();

		findDifferencesAndSimilaritiesInLists(leftList, rightList);
	}

	private static void findDifferencesInLists(List<Integer> leftList, List<Integer> rightList)
			throws IllegalArgumentException, ComparisonFailedException {
		final DuplicatesListComparator<Integer> duplicatesListComparator = DefaultComparators.
				<Integer>createDefaultDuplicatesListComparatorBuilder().
				findDifferencesOnly().
				build();

		final DuplicatesListComparisonResult<Integer> duplicatesListComparisonResult = 
		        duplicatesListComparator.compare(leftList, rightList);

		final DuplicatesListDifference<Integer> differences = duplicatesListComparisonResult.getDifference();
		
		System.out.println("Differences:");
		System.out.println("Only in left list :  " + differences.getElementsOnlyInLeftList());
		System.out.println("Changed in both lists :" + differences.getDifferentElements());
		System.out.println("Only in right list :" + differences.getElementsOnlyInRightList());
		System.out.println("Changed frequencies :" + differences.getDifferentFrequencies());
	}
}
```

The output is:
```
1: Left list :[1, 2, 4]
2: Right list :[1, 1, 4, 6]
3:
4: Differences:
5: Additional in left list :  {}
6: Changed in both lists :{1=(leftInt=2, rightInt=1)}
7: Additional in right list :{3=6}
8: Changed frequencies :{1=(leftInt=1, rightInt=2), 2=(leftInt=1, rightInt=0), 6=ImmutableIntPair(leftInt=0, rightInt=1)}
```
Legend:   
* For lines 5-7:     
The keys are the positions in the lists and the values are the elements
* For line 8:     
The key is the element in the lists and the values are the frequencies

What does the output say?
* Line 5:    
The left list has no additional value.
* Line 6:    
says that the second element (the key/index "1") in the left list "2" ("leftInt=2") turned into a "1" in the right list ("rightInt=1")
* Line 7:    
says the right list has the additional element "6" at the index "3"
* Line 8:    
    - says the element "1" occurs only once in the left list (leftInt=1) but twice in the right list (rightInt=2)
    - says the element "2" occurs only once in the left list (leftInt=1) but not at all in the right list (rightInt=0)
    - says the element "6" occurs not at all in the left list (leftInt=0) but only once in the right list (rightInt=1)
    
Of course, Java program do not only include lists but primitive variables, strings, maps, collections, arrays and above all: objects.    
jComparison is a bunch of qualitative diff algorithms which can be combined!    
    
Are you interested now to find out more about this project?
* Then a have look on the [demos](https://github.com/mmirwaldt/jcomparison/tree/master/core-demos "Some demos of jComparison").
* Then read the [FAQ](https://github.com/mmirwaldt/jcomparison/blob/master/doc/faq.md "FAQ of jComparison") for more background