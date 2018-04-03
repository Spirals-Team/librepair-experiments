
![Build status](https://travis-ci.org/mmirwaldt/jcomparison.svg?branch=master "")

# jComparison
*A Java framework that compares strings, arrays, sets, lists, maps and objects smarter than equals() within Java code*



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
class FindDifferencesListComparisonDemo {
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
		final ListComparator<Integer> listComparator = DefaultComparators.
		    <Integer>createDefaultListComparatorBuilder()
		    .findDifferencesOnly()
		    .build();

		final ListComparisonResult<Integer> listComparisonResult = 
		    listComparator.compare(leftList, rightList);

		final ListDifference<Integer> differences = listComparisonResult.getDifference();		
		System.out.println("Differences:");
		System.out.println("Only in left list :  " + differences.getElementsOnlyInLeftList());
		System.out.println("Different in both lists :" + differences.getDifferentElements());
		System.out.println("Only in right list :" + differences.getElementsOnlyInRightList());
		System.out.println("Different frequencies :" + differences.getDifferentFrequencies());
		System.out.println("Different positions :" + differences.getDifferentPositions());
		System.out.println();
	}
}
```

The output is:
```
Differences:
1: Only in left list :  {2=[1]}
2: Different in both lists :{1=(leftInt=2,rightInt=1)}
3: Only in right list :{6=[3]}
4: Different frequencies :{1=(leftInt=1,rightInt=2), 2=(leftInt=1,rightInt=0), 6=(leftInt=0,rightInt=1)}
5: Different positions :{1=(left=[],right=[1]), 2=(left=[1],right=[]), 6=(left=[],right=[3])}
```
Legend:   
* For line 2:   
The keys are the positions in the lists and the values are the elements
* For lines 1,3,4,5:       
The key is the element in the lists and the values are the frequencies/positions

What does the output say?
* Line 1:    
says the left list has the element "2" at the index "1"
* Line 2:    
says that the second element (the key/index "1") in the left list "2" ("leftInt=2") turned into a "1" in the right list ("rightInt=1")
* Line 3:    
says the right list has the additional element "6" at the index "3"
* Line 4:    
    - says the element "1" occurs only once in the left list (leftInt=1) but twice in the right list (rightInt=2)
    - says the element "2" occurs only once in the left list (leftInt=1) but not at all in the right list (rightInt=0)
    - says the element "6" occurs not at all in the left list (leftInt=0) but only once in the right list (rightInt=1)
* Line 5:    
    - says the element "1" can be found at the index "1" in the right list (right=[1]) but not in the left list at index "1" (left=[])
    - says the element "2" can be found at the index "1" in the left list (left=[1]) but not in the right list at index "2" (right=[])
    - says the element "6" can be found at the index "3" in the right list (right=[3]) but not in the left list at index "3" (left=[])
    
Maybe you are not interested in the differences but in the similarities.    
What about similarities?    
jComparison has the word *comparison* in his name which implies both differences and similarities.    
Therefore jComparison can also show you the similarities of both lists:    
 
```Java
class FindSimilaritiesListComparisonDemo {
	public static void main(String[] args)
			throws IllegalArgumentException, ComparisonFailedException {
		final List<Integer> leftList = Arrays.asList(1, 2, 4);
		final List<Integer> rightList = Arrays.asList(1, 1, 4, 6);

		System.out.println("Left list :" + leftList);
		System.out.println("Right list :" + rightList);
		System.out.println();

		findDifferencesAndSimilaritiesInLists(leftList, rightList);
	}

	private static void findSimilaritiesInLists(List<Integer> leftList, List<Integer> rightList)
			throws IllegalArgumentException, ComparisonFailedException {
		final ListComparator<Integer> listComparator = DefaultComparators
				.<Integer>createDefaultListComparatorBuilder()
				.findSimilaritiesOnly()
				.build();

		final ListComparisonResult<Integer> listComparisonResult = 
		        listComparator.compare(leftList, rightList);

		final ListSimilarity<Integer> similarities = listComparisonResult.getSimilarity();
		System.out.println("Similarities:");
		System.out.println("Similar elements: " + similarities.getSimilarElements());
		System.out.println("Similar frequencies: " + similarities.getSimilarFrequencies());
		System.out.println("Similar positions :" + similarities.getSimilarPositions());
	}
} 
```

The output is:
```
1: Similarities:
2: Similar elements: {0=1, 2=4}
3: Similar frequencies: {4=1}
4: Similar positions :{1=[0], 4=[2]}
```
Legend:   
* For line 2:   
The keys are the positions in the lists and the values are the elements    
* For lines 3,4:       
The key is the element in the lists and the values are the frequencies/positions    
     
What does the output say?   
* Line 2:    
    - says the element "1" occurs at the index "0" in both lists ("0=1")
    - says the element "4" occurs at the index "2" in both lists ("2=4")
* Line 3:    
says the element "4" occurs once in both lists ("4=1")
* Line 4:    
    - says the element "1" can be found at the index "0" in both lists (1=[0])
    - says the element "4" can be found at the index "2" in both lists (4=[2])

Remark: the similar positions have lists as values because lists can have duplicates.

Of course, Java programs do not only include lists but primitive variables, strings, maps, collections, arrays and above all: objects.    
jComparison is a bunch of qualitative diff algorithms which can be combined!    
    
Are you interested now to find out more about this project?
* Then a have look on the [demos](https://github.com/mmirwaldt/jcomparison/tree/master/core-demos "Some demos of jComparison").
* Then read the [FAQ](https://github.com/mmirwaldt/jcomparison/blob/master/z_doc/faq.md "FAQ of jComparison") for more background
