package spoon.test.prettyprinter.testclasses;

import java.util.ArrayList;
import java.util.List;

/**
 * The content of this file 
 * 

 * 		should not be changed
 * Because DJPP should print only modified content again 
 */
public
@Deprecated
abstract class /* even this comment stays here together with all SPACES and EOLs*/ ToBeChanged<T, K> /*before extends*/ 
	extends ArrayList<T> implements List<T>,
	Cloneable
{
	
	
	/**/
	final
	//
	private String string = "a"
			+ "b" + "c"+"d";
	
	//and spaces here are wanted too
	
	
	void andSomeOtherMethod(
			int param1,
			String param2)
	{
		System.out.println("aaa"
				+ "xyz");
	}
}

//and what about this comment? 