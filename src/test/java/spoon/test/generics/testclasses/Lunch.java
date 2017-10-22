package spoon.test.generics.testclasses;

import java.util.List;

public class Lunch<A,B> {
	<C> void eatMe(A paramA, B paramB, C paramC, List<C> paramListC){}
}
